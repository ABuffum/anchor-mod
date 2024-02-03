package fun.wich.trim;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Optional;

public enum ArmorTrimMaterial {
	QUARTZ("quartz", () -> Items.QUARTZ, 14931140),
	IRON("iron", () -> Items.IRON_INGOT, 0xECECEC),
	NETHERITE("netherite", () -> Items.NETHERITE_INGOT, 6445145),
	REDSTONE("redstone", () -> Items.REDSTONE, 9901575),
	COPPER("copper", () -> Items.COPPER_INGOT, 11823181),
	GOLD("gold", () -> Items.GOLD_INGOT, 14594349),
	EMERALD("emerald", () -> Items.EMERALD, 1155126),
	DIAMOND("diamond", () -> Items.DIAMOND, 7269586),
	LAPIS("lapis", () -> Items.LAPIS_LAZULI, 4288151),
	AMETHYST("amethyst", () -> Items.AMETHYST_SHARD, 10116294);

	private final String name;
	public final ItemConvertible itemProvider;
	private final int color;
	private final Text description;
	ArmorTrimMaterial(String name, ItemConvertible itemProvider, int color) {
		this.name = name;
		this.itemProvider = itemProvider;
		this.color = color;
		this.description = new TranslatableText("trim_material." + name).fillStyle(Style.EMPTY.withColor(color));
	}
	public String getName() { return name; }
	public Item asItem() { return itemProvider.asItem(); }
	public int getColor() { return color; }
	public Text getDescription() { return description; }

	public static Optional<ArmorTrimMaterial> get(ItemStack stack) {
		for (ArmorTrimMaterial material : values()) {
			if (stack.isOf(material.asItem())) return Optional.of(material);
		}
		return Optional.empty();
	}
}
