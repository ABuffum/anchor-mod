package fun.wich.mixins.client;

import fun.wich.entity.ModNbtKeys;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.banners.ModBannerPatternContainer;
import fun.wich.util.banners.ModBannerPatternConversions;
import fun.wich.util.banners.ModBannerPatternData;
import fun.wich.util.dye.ModDyedBanner;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(BannerBlockEntity.class)
public abstract class BannerBlockEntityClientMixin extends BlockEntity implements ModBannerPatternContainer, ModDyedBanner {
	@Shadow private List<?> patterns;
	@Shadow private @Nullable NbtList patternListTag;
	@Shadow private boolean patternListTagRead;
	@Shadow private @Nullable Text customName;

	public BannerBlockEntityClientMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Unique private List<ModBannerPatternData> modBannerPatterns = Collections.emptyList();
	private ModDyeColor modDyeBaseColor;


	@Override
	public List<ModBannerPatternData> getModBannerPatterns() {
		if (this.patterns == null && this.patternListTagRead) {
			NbtList nbt = ((MixinHelper)this).getModBannerPatternsNbt();
			modBannerPatterns = ModBannerPatternConversions.makeModBannerPatternData(nbt);
		}
		return Collections.unmodifiableList(modBannerPatterns);
	}

	/**
	 * Reads Banner++ loom pattern data from an item stack.
	 */
	@Inject(method = "readFrom(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DyeColor;)V", at = @At("RETURN"))
	private void ReadPatternFromItemStack(ItemStack stack, DyeColor color, CallbackInfo info) {
		((MixinHelper)this).setModBannerPatternsNbt(ModBannerPatternConversions.getModBannerPatternNbt(stack));
	}

	@Override
	public void ReadFrom(ItemStack stack, ModDyeColor baseColor) {
		this.modDyeBaseColor = baseColor;
		this.patternListTag = BannerBlockEntity.getPatternListTag(stack);
		this.patterns = null;
		this.patternListTagRead = true;
		this.customName = stack.hasCustomName() ? stack.getName() : null;
		((MixinHelper)this).setModBannerPatternsNbt(ModBannerPatternConversions.getModBannerPatternNbt(stack));
	}

	/**
	 * Adds Banner++ loom pattern data to the pick block stack.
	 */
	@Inject(method = "getPickStack", at = @At("RETURN"))
	private void PutPatternsInPickStack(CallbackInfoReturnable<ItemStack> info) {
		NbtList nbt = ((MixinHelper)this).getModBannerPatternsNbt();
		if (nbt != null) info.getReturnValue().getOrCreateSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG).put(ModNbtKeys.MOD_BANNER_PATTERNS, nbt);
	}
}
