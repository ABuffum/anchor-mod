package fun.mousewich.item.tool.oxidized;

import fun.mousewich.item.OxidizableItem;
import fun.mousewich.item.tool.HammerItem;
import fun.mousewich.material.ModToolMaterials;
import fun.mousewich.util.OxidationScale;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.Optional;

import net.minecraft.block.Oxidizable.OxidizationLevel;

public class OxidizableHammerItem extends HammerItem implements OxidizableItem {
	private final OxidizationLevel level;
	public OxidizableHammerItem(OxidizationLevel level, ModToolMaterials material) { this(level, material, material.getHammerDamage(), material.getHammerSpeed()); }
	public OxidizableHammerItem(OxidizationLevel level, ToolMaterial material, float attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed);
		this.level = level;
	}
	@Override
	public OxidizationLevel getDegradationLevel() { return level; }
	@Override
	public Optional<Item> getDegradationResult() { return OxidationScale.getIncreasedItem(this); }
}
