package haven.materials.gem;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import haven.items.echo.*;
import haven.items.basic.*;
import haven.materials.HavenToolMaterials;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;

public class EchoMaterial extends BaseMaterial implements
		AxeProvider, HoeProvider, PickaxeProvider, ShovelProvider, SwordProvider, KnifeProvider {
	private final Item axe;
	public Item getAxe() { return axe; }
	private final Item hoe;
	public Item getHoe() { return hoe; }
	private final Item pickaxe;
	public Item getPickaxe() { return pickaxe; }
	private final Item shovel;
	public Item getShovel() { return shovel; }
	private final Item sword;
	public Item getSword() { return sword; }
	private final Item knife;
	public Item getKnife() { return knife; }

	public EchoMaterial(float knockback) {
		super("echo", false);
		axe = new EchoAxeItem(HavenToolMaterials.ECHO, 5, -3, knockback, ItemSettings());
		hoe = new EchoHoeItem(HavenToolMaterials.ECHO, -4, 0, knockback, ItemSettings());
		pickaxe = new EchoPickaxeItem(HavenToolMaterials.ECHO, 1, -2.8F, knockback, ItemSettings());
		shovel = new EchoShovelItem(HavenToolMaterials.ECHO, 1.5F, -3, knockback, ItemSettings());
		sword = new EchoSwordItem(HavenToolMaterials.ECHO, 3, -2.4F, knockback, ItemSettings());
		knife = new EchoKnifeItem(HavenToolMaterials.ECHO, 1, ItemSettings());
	}

	public boolean contains(Block block) {
		return super.contains(block);
	}
	public boolean contains(Item item) {
		return item == axe || item == hoe || item == pickaxe || item == shovel || item == sword || item == knife || super.contains(item);
	}
}
