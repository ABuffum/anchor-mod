package haven.materials.metal;

import haven.HavenMod;
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

public class IronMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider,
		LanternProvider, SoulLanternProvider,
		ChainProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final HavenPair lantern;
	public HavenPair getLantern() { return lantern; }
	private final Block unlit_lantern;
	public Block getUnlitLantern() { return unlit_lantern; }
	private final HavenPair soul_lantern;
	public HavenPair getSoulLantern() { return soul_lantern; }
	private final Block unlit_soul_lantern;
	public Block getUnlitSoulLantern() { return unlit_soul_lantern; }
	private final HavenPair chain;
	public HavenPair getChain() { return chain; }
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

	public IronMaterial() {
		super("iron", false);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.IRON_FLAME, ItemSettings());
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(15)).nonOpaque()), ItemSettings());
		unlit_lantern = new LanternBlock(HavenMod.UnlitLanternSettings().dropsLike(lantern.BLOCK));
		soul_lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(10)).nonOpaque()), ItemSettings());
		unlit_soul_lantern = new LanternBlock(HavenMod.UnlitLanternSettings().dropsLike(soul_lantern.BLOCK));
		chain = new HavenPair(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(Blocks.IRON_BLOCK), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), ItemSettings());
		cut_pillar = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block) || containsLantern(block) || containsSoulLantern(block)
				|| block == chain.BLOCK || block == wall.BLOCK
				|| block == cut.BLOCK || block == cut_pillar.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK
				|| block == cut_wall.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| item == lantern.ITEM || item == soul_lantern.ITEM || item == chain.ITEM || item == wall.ITEM
				|| item == cut.ITEM || item == cut_pillar.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM
				|| item == cut_wall.ITEM || super.contains(item);
	}
}
