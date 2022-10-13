package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.UnlitLanternBlock;
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
		TorchProvider, EnderTorchProvider, SoulTorchProvider, LanternProvider, EnderLanternProvider, SoulLanternProvider,
		ButtonProvider, ChainProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer lantern;
	public BlockContainer getLantern() { return lantern; }
	private final Block unlit_lantern;
	public Block getUnlitLantern() { return unlit_lantern; }
	private final BlockContainer ender_lantern;
	public BlockContainer getEnderLantern() { return ender_lantern; }
	private final Block unlit_ender_lantern;
	public Block getUnlitEnderLantern() { return unlit_ender_lantern; }
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
		torch = MakeTorch(14, BlockSoundGroup.METAL, HavenMod.IRON_FLAME_PARTICLE);
		ender_torch = MakeTorch(10, BlockSoundGroup.METAL, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.METAL, ParticleTypes.SOUL_FIRE_FLAME);
		lantern = MakeLantern(15);
		unlit_lantern = new UnlitLanternBlock(this::getLantern);
		ender_lantern = MakeLantern(10);
		unlit_ender_lantern = new UnlitLanternBlock(this::getEnderLantern);
		soul_lantern = MakeLantern(10);
		unlit_soul_lantern = new UnlitLanternBlock(this::getSoulLantern);
		button = new BlockContainer(new MetalButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(1.5F).sounds(BlockSoundGroup.METAL)));
		chain = new BlockContainer(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()));
		wall = new BlockContainer(new HavenWallBlock(Blocks.IRON_BLOCK));
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
		cut_pillar = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)));
		cut_slab = new BlockContainer(new HavenSlabBlock(cut.BLOCK));
		cut_stairs = new BlockContainer(new HavenStairsBlock(cut.BLOCK));
		cut_wall = new BlockContainer(new HavenWallBlock(cut.BLOCK));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| containsLantern(block) || containsEnderLantern(block) || containsSoulLantern(block)
				|| chain.contains(block) || wall.contains(block)
				|| cut.contains(block) || cut_pillar.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block)
				|| cut_wall.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| lantern.contains(item) || ender_lantern.contains(item) || soul_lantern.contains(item) || chain.contains(item) || wall.contains(item)
				|| cut.contains(item) || cut_pillar.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item)
				|| cut_wall.contains(item) || super.contains(item);
	}
}
