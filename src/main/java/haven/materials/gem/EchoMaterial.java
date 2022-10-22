package haven.materials.gem;

import haven.HavenMod;
import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.containers.BlockContainer;
import haven.items.echo.*;
import haven.materials.HavenToolMaterials;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class EchoMaterial extends BaseMaterial implements
		BlockProvider, SlabProvider, StairsProvider, WallProvider,
		AxeProvider, HoeProvider, PickaxeProvider, ShovelProvider, SwordProvider, KnifeProvider {
	private final BlockContainer block;
	public BlockContainer getBlock() { return block; }
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
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
		block = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.SCULK).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()));
		slab = new BlockContainer(new HavenSlabBlock(block.BLOCK), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(block.BLOCK), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(block.BLOCK), ItemSettings());
		axe = new EchoAxeItem(HavenToolMaterials.ECHO, 5, -3, knockback, ItemSettings());
		hoe = new EchoHoeItem(HavenToolMaterials.ECHO, -4, 0, knockback, ItemSettings());
		pickaxe = new EchoPickaxeItem(HavenToolMaterials.ECHO, 1, -2.8F, knockback, ItemSettings());
		shovel = new EchoShovelItem(HavenToolMaterials.ECHO, 1.5F, -3, knockback, ItemSettings());
		sword = new EchoSwordItem(HavenToolMaterials.ECHO, 3, -2.4F, knockback, ItemSettings());
		knife = new EchoKnifeItem(HavenToolMaterials.ECHO, 1, ItemSettings());
	}

	public boolean contains(Block block) {
		return slab.contains(block) || stairs.contains(block) || wall.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return slab.contains(item) || stairs.contains(item) || wall.contains(item)
				|| item == axe || item == hoe || item == pickaxe || item == shovel || item == sword || item == knife || super.contains(item);
	}
}
