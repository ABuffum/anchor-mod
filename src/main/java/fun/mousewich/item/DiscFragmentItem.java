package fun.mousewich.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DiscFragmentItem extends Item {
	public DiscFragmentItem(Settings settings) { super(settings); }
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(this.getDescription().formatted(Formatting.GRAY));
	}
	public MutableText getDescription() { return new TranslatableText("item.minecraft.disc_fragment_5.desc"); }
}
