package fun.wich.mixins.screen;

import fun.wich.entity.ModNbtKeys;
import fun.wich.item.basic.ModDyeItem;
import fun.wich.util.banners.ModBannerPattern;
import fun.wich.util.banners.ModBannerPatternLimitModifier;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LoomScreenHandler.class)
public abstract class LoomScreenHandlerMixin extends ScreenHandler {
	@Shadow @Final Property selectedPattern;
	@Shadow @Final Slot bannerSlot;
	@Shadow @Final Slot dyeSlot;
	@Shadow @Final private Slot patternSlot;
	@Shadow @Final private Slot outputSlot;

	@Unique private PlayerEntity player;
	@Unique private int patternLimit;

	protected LoomScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) { super(type, syncId); }

	@Shadow private native void updateOutputSlot();

	@Inject(method="<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at=@At("RETURN"))
	private void bppSavePlayer(int capacity, PlayerInventory playerInventory, ScreenHandlerContext ctx, CallbackInfo ci) {
		player = playerInventory.player;
	}

	@Inject(method = "onButtonClick", at = @At("HEAD"), cancellable = true)
	private void ModBannerPatternOnClick(PlayerEntity entity, int clicked, CallbackInfoReturnable<Boolean> info) {
		int vanillaCount = BannerPattern.LOOM_APPLICABLE_COUNT;
		if (clicked >= vanillaCount) {
			int i = clicked - vanillaCount;
			List<ModBannerPattern> patterns = ModBannerPattern.valuesWithoutItem();
			if (i < patterns.size()) {
				this.selectedPattern.set(patterns.get(i).getOrdinal());
				this.updateOutputSlot();
				info.setReturnValue(true);
			}
		}
	}

	@Inject(method = "onContentChanged", at = @At("HEAD"))
	private void EnforceModBannerPatternLimit(CallbackInfo info) {
		patternLimit = ModBannerPatternLimitModifier.EVENT.invoker().computePatternLimit(6, player);
	}

	@Redirect(method="onContentChanged", at=@At(value="INVOKE", target="Lnet/minecraft/screen/Property;get()I"))
	private int AddModBannerPatternCondition(Property self) {
		int res = self.get();
		return res < 0 ? 1 : res;
	}

	@ModifyConstant(method="onContentChanged", constant=@Constant(intValue=6))
	private int SkipPatternLimitCheck(int limit) { return Integer.MAX_VALUE; }

	@ModifyVariable(method="onContentChanged", at=@At(value="LOAD", ordinal=0), ordinal=0)
	private boolean AddModBannerPatternsToFullCondition(boolean original) {
		ItemStack banner = this.bannerSlot.getStack();
		return original || BannerBlockEntity.getPatternCount(banner) >= patternLimit;
	}

	@Inject(method="onContentChanged", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal=4))
	private void OnContentChanged(CallbackInfo info) {
		ItemStack banner = this.bannerSlot.getStack();
		ItemStack patternStack = this.patternSlot.getStack();
		if (!patternStack.isEmpty() && patternStack.getItem() instanceof ModBannerPattern.Provider provider) {
			boolean overfull = BannerBlockEntity.getPatternCount(banner) >= patternLimit;
			if (!overfull) {
				ModBannerPattern pattern = provider.getPattern();
				this.selectedPattern.set(pattern.getOrdinal());
			}
			else this.selectedPattern.set(0);
		}
		else if (this.selectedPattern.get() < 0) {
			this.selectedPattern.set(0);
			this.outputSlot.setStack(ItemStack.EMPTY);
		}
	}

	@Inject(method = "updateOutputSlot", at = @At("HEAD"))
	private void AddModBannerPatternToOutput(CallbackInfo info) {
		ItemStack bannerStack = this.bannerSlot.getStack();
		ItemStack dyeStack = this.dyeSlot.getStack();
		if (this.selectedPattern.get() < 0 && !bannerStack.isEmpty() && !dyeStack.isEmpty()) {
			int rawId = this.selectedPattern.get();
			ModBannerPattern pattern = ModBannerPattern.byOrdinal(rawId);
			int color;
			if (dyeStack.getItem() instanceof ModDyeItem modDye) color = modDye.getColor().getId();
			else color = ((DyeItem)dyeStack.getItem()).getColor().getId();
			ItemStack output = bannerStack.copy();
			output.setCount(1);
			NbtCompound beTag = output.getOrCreateSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
			NbtList loomPatterns;
			if (beTag.contains(ModNbtKeys.MOD_BANNER_PATTERNS, 9)) loomPatterns = beTag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10);
			else {
				loomPatterns = new NbtList();
				beTag.put(ModNbtKeys.MOD_BANNER_PATTERNS, loomPatterns);
			}
			int vanillaPatternCount = beTag.getList(ModNbtKeys.PATTERNS, 10).size();
			NbtCompound patternTag = new NbtCompound();
			patternTag.putString(ModNbtKeys.PATTERN, pattern.getId());
			patternTag.putInt(ModNbtKeys.COLOR, color);
			patternTag.putInt(ModNbtKeys.INDEX, vanillaPatternCount);
			loomPatterns.add(patternTag);
			if (!ItemStack.areEqual(output, this.outputSlot.getStack())) {
				this.outputSlot.setStack(output);
			}
		}
	}

	@Inject(method="transferSlot", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal=0, shift=At.Shift.BEFORE), cancellable = true)
	private void TransferModBannerPatternItemSlot(PlayerEntity player, int slotIdx, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack stack = this.slots.get(slotIdx).getStack();
		if (stack.getItem() instanceof ModBannerPattern.Provider) {
			if (!this.insertItem(stack, this.patternSlot.id, this.patternSlot.id + 1, false)) {
				cir.setReturnValue(ItemStack.EMPTY);
			}
		}
	}
}
