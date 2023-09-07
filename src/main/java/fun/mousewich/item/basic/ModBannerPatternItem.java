package fun.mousewich.item.basic;

import fun.mousewich.util.banners.ModBannerPattern;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBannerPatternItem extends Item implements ModBannerPattern.Provider {
	private final ModBannerPattern pattern;

	public ModBannerPatternItem(ModBannerPattern pattern, Settings settings) {
		super(settings);
		this.pattern = pattern;
	}

	@Override
	public ModBannerPattern getPattern() { return pattern; }
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(this.getDescription().formatted(Formatting.GRAY));
	}
	public MutableText getDescription() {
		return new TranslatableText(this.getTranslationKey() + ".desc");
	}
}
