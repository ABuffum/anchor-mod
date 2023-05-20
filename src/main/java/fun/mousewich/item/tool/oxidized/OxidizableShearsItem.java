package fun.mousewich.item.tool.oxidized;

import fun.mousewich.ModFactory;
import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModShearsItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
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
