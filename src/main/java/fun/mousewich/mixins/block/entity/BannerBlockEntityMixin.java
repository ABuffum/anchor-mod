package fun.mousewich.mixins.block.entity;

import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.banners.ModBannerPattern;
import fun.mousewich.util.banners.ModBannerPatternContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.Iterator;

@Mixin(BannerBlockEntity.class)
public abstract class BannerBlockEntityMixin extends BlockEntity implements Nameable, ModBannerPatternContainer.MixinHelper {
	@Unique private NbtList modBannerPatternsNbt = new NbtList();

	public BannerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public NbtList getModBannerPatternsNbt() { return modBannerPatternsNbt; }
	@Override
	public void setModBannerPatternsNbt(NbtList tag) {
		modBannerPatternsNbt = tag;
		if (modBannerPatternsNbt != null) {
			for (Iterator<NbtElement> itr = modBannerPatternsNbt.iterator(); itr.hasNext(); ) {
				NbtCompound element = (NbtCompound) itr.next();
				ModBannerPattern pattern = ModBannerPattern.byId(element.getString(ModNbtKeys.PATTERN));
				int colorId = element.getInt(ModNbtKeys.COLOR);
				int index = element.getInt(ModNbtKeys.INDEX);
				if (pattern == null) itr.remove();
				else {
					int rtColorId = ModDyeColor.byId(colorId).getId();
					if (rtColorId != colorId) element.putInt(ModNbtKeys.COLOR, rtColorId);
					if (index < 0) element.putInt(ModNbtKeys.INDEX, 0);
				}
			}
			modBannerPatternsNbt.sort(Comparator.comparingInt(t -> ((NbtCompound)t).getInt(ModNbtKeys.INDEX)));
		}
	}

	@Inject(method = "getPatternCount", at = @At("RETURN"), cancellable = true)
	private static void modifyPatternCount(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		NbtCompound beTag = stack.getSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
		if (beTag != null && beTag.contains(ModNbtKeys.MOD_BANNER_PATTERNS)) {
			int count = beTag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10).size();
			cir.setReturnValue(cir.getReturnValue() + count);
		}
	}

	@Inject(method = "loadFromItemStack", at = @At("HEAD"), cancellable = true)
	private static void cleanBppLoomPattern(ItemStack stack, CallbackInfo ci) {
		NbtCompound beTag = stack.getSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
		if (beTag != null) {
			NbtList modBannerPatterns = beTag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10);
			NbtList patterns = beTag.getList(ModNbtKeys.PATTERNS, 10);
			boolean cleaned = false;
			if (!modBannerPatterns.isEmpty()) {
				int lastIndex = modBannerPatterns.getCompound(modBannerPatterns.size() - 1).getInt(ModNbtKeys.INDEX);
				if (lastIndex >= patterns.size()) {
					modBannerPatterns.remove(modBannerPatterns.size() - 1);
					cleaned = true;
				}
			}
			if (!cleaned && !patterns.isEmpty()) patterns.remove(patterns.size() - 1);
			if (modBannerPatterns.isEmpty()) {
				if (patterns.isEmpty()) stack.removeSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
				else beTag.remove(ModNbtKeys.MOD_BANNER_PATTERNS);
			}
			else if (patterns.isEmpty()) beTag.remove(ModNbtKeys.PATTERNS);
		}
		ci.cancel();
	}

	@Inject(method = "writeNbt", at = @At("RETURN"))
	private void addBppPatternData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
		if (nbt != null) nbt.put(ModNbtKeys.MOD_BANNER_PATTERNS, modBannerPatternsNbt);
	}
	@Inject(method = "readNbt", at = @At("RETURN"))
	private void readBppPatternData(NbtCompound tag, CallbackInfo ci) {
		setModBannerPatternsNbt(tag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10));
	}
}
