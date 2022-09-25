package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.basic.HavenSlabBlock;
import haven.blocks.basic.HavenStairsBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.blocks.MetalButtonBlock;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class IronMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider, LanternProvider, SoulLanternProvider,
		ButtonProvider, ChainProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer lantern;
	public BlockContainer getLantern() { return lantern; }
	private final Block unlit_lantern;
	public Block getUnlitLantern() { return unlit_lantern; }
	private final BlockContainer soul_lantern;
	public BlockContainer getSoulLantern() { return soul_lantern; }
	private final Block unlit_soul_lantern;
	public Block getUnlitSoulLantern() { return unlit_soul_lantern; }
	private final BlockContainer button;
	public BlockContainer getButton() { return button; }
	private final BlockContainer chain;
	public BlockContainer getChain() { return chain; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer cut;
	public BlockContainer getCut() { return cut; }
	private final BlockContainer cut_pillar;
	public BlockContainer getCutPillar() { return cut_pillar; }
	private final BlockContainer cut_slab;
	public BlockContainer getCutSlab() { return cut_slab; }
	private final BlockContainer cut_stairs;
	public BlockContainer getCutStairs() { return cut_stairs; }
	private final BlockContainer cut_wall;
	public BlockContainer getCutWall() { return cut_wall; }

	public IronMaterial() {
		super("iron", false);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.IRON_FLAME_PARTICLE, ItemSettings());
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		lantern = new BlockContainer(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(15)).nonOpaque()), ItemSettings());
		unlit_lantern = new LanternBlock(HavenMod.UnlitLanternSettings());
		soul_lantern = new BlockContainer(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(10)).nonOpaque()), ItemSettings());
		unlit_soul_lantern = new LanternBlock(HavenMod.UnlitLanternSettings());
		button = new BlockContainer(new MetalButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(1.5F).sounds(BlockSoundGroup.METAL)));
		chain = new BlockContainer(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(Blocks.IRON_BLOCK), ItemSettings());
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), ItemSettings());
		cut_pillar = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new BlockContainer(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new BlockContainer(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new BlockContainer(new HavenWallBlock(cut.BLOCK), ItemSettings());
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block) || containsLantern(block) || containsSoulLantern(block)
				|| chain.contains(block) || wall.contains(block)
				|| cut.contains(block) || cut_pillar.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block)
				|| cut_wall.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| lantern.contains(item) || soul_lantern.contains(item) || chain.contains(item) || wall.contains(item)
				|| cut.contains(item) || cut_pillar.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item)
				|| cut_wall.contains(item) || super.contains(item);
	}
}
