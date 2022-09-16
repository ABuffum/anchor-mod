package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.basic.HavenPaneBlock;
import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenPair;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class GoldMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider,
		LanternProvider, SoulLanternProvider,
		ChainProvider, BarsProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final HavenPair lantern;
	public HavenPair getLantern() { return lantern; }
	private final HavenPair soul_lantern;
	public HavenPair getSoulLantern() { return soul_lantern; }
	private final HavenPair chain;
	public HavenPair getChain() { return chain; }
	private final HavenPair bars;
	public HavenPair getBars() { return bars; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
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

	public GoldMaterial() {
		super("gold", false);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(HavenMod.luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.GOLD_FLAME, ItemSettings());
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(HavenMod.luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(HavenMod.luminance(15)).nonOpaque()), ItemSettings());
		soul_lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(HavenMod.luminance(10)).nonOpaque()), ItemSettings());
		chain = new HavenPair(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		bars = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(Blocks.GOLD_BLOCK), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)), ItemSettings());
		cut_pillar = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return block == torch.BLOCK || block == torch.WALL_BLOCK || block == soul_torch.BLOCK || block == soul_torch.WALL_BLOCK
				|| block == lantern.BLOCK || block == soul_lantern.BLOCK || block == chain.BLOCK || block == wall.BLOCK
				|| block == cut.BLOCK || block == cut_pillar.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK
				|| block == cut_wall.BLOCK || block == bars.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == torch.ITEM || item == torch.ITEM || item == soul_torch.ITEM || item == soul_torch.ITEM
				|| item == lantern.ITEM || item == soul_lantern.ITEM || item == chain.ITEM || item == wall.ITEM
				|| item == cut.ITEM || item == cut_pillar.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM
				|| item == cut_wall.ITEM || item == bars.ITEM || super.contains(item);
	}
}
