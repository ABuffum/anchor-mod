package fun.mousewich.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface ConditionalLoreItem {
	void ApplyConditionalLore(ItemStack stack, PlayerEntity player, TooltipContext context, List<Text> list);

	default Style GetStyle(int color, boolean italic) { return Style.EMPTY.withColor(color).withItalic(italic); }
	default Style GetStyle(Formatting color, boolean italic) { return Style.EMPTY.withColor(color).withItalic(italic); }

	default Text MakeLoreText(String text, int color) { return MakeLoreText(text, color, true); }
	default Text MakeLoreText(String text, int color, boolean italic) { return MakeLoreText(text, GetStyle(color, italic)); }
	default Text MakeLoreText(String text, Formatting color) { return MakeLoreText(text, color, true); }
	default Text MakeLoreText(String text, Formatting color, boolean italic) { return MakeLoreText(text, GetStyle(color, italic)); }

	default Text MakeLoreText(String text, Style style) { return new LiteralText(text).setStyle(style); }
}
