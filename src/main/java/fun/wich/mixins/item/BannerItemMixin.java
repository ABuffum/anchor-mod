package fun.wich.mixins.item;

import fun.wich.ModId;
import fun.wich.entity.ModNbtKeys;
import fun.wich.util.banners.ModBannerPattern;
import net.minecraft.block.Block;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BannerItem.class)
public abstract class BannerItemMixin extends WallStandingBlockItem {
	private static final String PREFIX = "block." + ModId.NAMESPACE + ".banner.";
	@Unique private static NbtList modBannerPatterns;
	@Unique private static int nextModBannerPatternIndex;

	public BannerItemMixin(Block standingBlock, Block wallBlock, Settings settings) { super(standingBlock, wallBlock, settings); }

	@Inject(method="appendBannerTooltip", at=@At("HEAD"))
	private static void AppendBannerTooltipBefore(ItemStack stack, List<Text> tooltip, CallbackInfo info) {
		nextModBannerPatternIndex = 0;
		NbtCompound beTag = stack.getSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
		if (beTag != null && beTag.contains(ModNbtKeys.MOD_BANNER_PATTERNS)) {
			modBannerPatterns = beTag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10);
		}
	}
	@Inject(method="appendBannerTooltip", at=@At(value="INVOKE", target="Lnet/minecraft/nbt/NbtList;getCompound(I)Lnet/minecraft/nbt/NbtCompound;", ordinal=0))
	private static void AppendBannerTooltipInline(ItemStack stack, List<Text> tooltip, CallbackInfo info) {
		int nextIndex = tooltip.size() - 1;
		if (modBannerPatterns != null) {
			while (nextModBannerPatternIndex < modBannerPatterns.size()) {
				NbtCompound data = modBannerPatterns.getCompound(nextModBannerPatternIndex);
				if (data.getInt(ModNbtKeys.INDEX) == nextIndex) {
					addModBannerPatternLine(data, tooltip);
					nextModBannerPatternIndex++;
				}
				else break;
			}
		}
	}
	@Inject(method = "appendBannerTooltip", at = @At("RETURN"))
	private static void AppendBannerTooltipAfter(ItemStack stack, List<Text> tooltip, CallbackInfo ci) {
		if (modBannerPatterns != null) {
			for (int i = nextModBannerPatternIndex; i < modBannerPatterns.size(); i++) {
				NbtCompound data = modBannerPatterns.getCompound(i);
				addModBannerPatternLine(data, tooltip);
			}
			modBannerPatterns = null;
		}
	}
	@Unique
	private static void addModBannerPatternLine(NbtCompound data, List<Text> tooltip) {
		ModBannerPattern pattern = ModBannerPattern.byId(data.getString(ModNbtKeys.PATTERN));
		DyeColor color = DyeColor.byId(data.getInt(ModNbtKeys.COLOR));
		if (pattern != null) tooltip.add(new TranslatableText(PREFIX + pattern.getName() + "." + color.getName()).formatted(Formatting.GRAY));
	}
}
