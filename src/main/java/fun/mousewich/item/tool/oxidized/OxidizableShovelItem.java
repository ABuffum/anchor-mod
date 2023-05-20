package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.ModShovelItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidizationLevel;

public class OxidizableShovelItem extends ModShovelItem implements OxidizableItem {
	private final OxidizationLevel level;
	public OxidizableShovelItem(OxidizationLevel level, ModToolMaterials material) { this(level, material, material.getShovelDamage(), material.getShovelSpeed()); }
	public OxidizableShovelItem(OxidizationLevel level, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public OxidizationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
