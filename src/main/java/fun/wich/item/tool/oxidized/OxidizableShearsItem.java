package fun.wich.item.tool.oxidized;

import fun.wich.ModFactory;
import fun.wich.item.OxidizableItem;
import fun.wich.item.tool.ModShearsItem;
import fun.wich.material.ModToolMaterials;
import fun.wich.util.OxidationScale;
import net.minecraft.item.Item;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidizationLevel;

public class OxidizableShearsItem extends ModShearsItem implements OxidizableItem {
	private final OxidizationLevel level;
	public OxidizableShearsItem(OxidizationLevel level, ModToolMaterials material) { this(level, material, ModFactory.ItemSettings()); }
	public OxidizableShearsItem(OxidizationLevel level, ModToolMaterials material, Settings settings) {
		super(material, settings);
		this.level = level;
	}
	@Override
	public OxidizationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
