package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.basic.*;
import haven.materials.base.BaseMaterial;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenPair;
import haven.util.HavenToolMaterials;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class DarkIronMaterial extends BaseMaterial implements
		NuggetProvider,
		TorchProvider, SoulTorchProvider,
		BarsProvider, BlockProvider, WallProvider, DoorProvider, TrapdoorProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {

	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final HavenPair bars;
	public HavenPair getBars() { return bars; }
	private final HavenPair block;
	public HavenPair getBlock() { return block; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	private final HavenPair door;
	public HavenPair getDoor() { return door; }
	private final HavenPair trapdoor;
	public HavenPair getTrapdoor() { return trapdoor; }
	private final HavenPair cut;
	public HavenPair getCut() { return cut; }
	private final HavenPair cut_pillar;
	public HavenPair getCutPillar() { return cut_pillar; }
	private final HavenPair cut_slab;
	public HavenPair getCutSlab() { return cut_slab; }
	private final HavenPair cut_stairs;
	public HavenPair getCutStairs() { return cut_stairs; }
	private final HavenPair cut_wall;
	public HavenPair getCutWall() { return cut_wall; }

	public DarkIronMaterial() {
		super("dark_iron", false);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.IRON_FLAME, ItemSettings());
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		nugget = new Item(ItemSettings());
		bars = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()), ItemSettings());
		block = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(block.BLOCK), ItemSettings());
		door = new HavenPair(new HavenDoorBlock(Blocks.IRON_DOOR), ItemSettings());
		trapdoor = new HavenPair(new HavenTrapdoorBlock(Blocks.IRON_TRAPDOOR), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(block.BLOCK)), ItemSettings());
		cut_pillar = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| block == cut.BLOCK || block == cut_pillar.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK
				|| block == cut_wall.BLOCK || block == bars.BLOCK || block == this.block.BLOCK || block == wall.BLOCK
				|| block == door.BLOCK || block == trapdoor.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| item == cut.ITEM || item == cut_pillar.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM
				|| item == cut_wall.ITEM || item == bars.ITEM || item == block.ITEM || item == wall.ITEM
				|| item == door.ITEM || item == trapdoor.ITEM || item == nugget || super.contains(item);
	}
}
