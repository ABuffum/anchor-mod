package haven.util;

import haven.HavenMod;
import haven.blocks.*;
import haven.blocks.basic.HavenTorchBlock;
import haven.blocks.basic.HavenWallTorchBlock;
import haven.blocks.oxidizable.OxidizableTorchBlock;
import haven.blocks.oxidizable.OxidizableUnlitTorchBlock;
import haven.blocks.oxidizable.OxidizableUnlitWallTorchBlock;
import haven.blocks.oxidizable.OxidizableWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;

import java.util.HashMap;
import java.util.Map;

public class HavenTorch extends WalledBlock {
	public final Unlit UNLIT;
	public HavenTorch(AbstractBlock.Settings settings, ParticleEffect particle) {
		this(settings, particle, HavenMod.ItemSettings());
	}

	public HavenTorch(AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new HavenTorchBlock(blockSettings, particle), particle, itemSettings);
	}
	private HavenTorch(Block block, ParticleEffect particle, Item.Settings itemSettings) {
		this(block, new HavenWallTorchBlock(AbstractBlock.Settings.copy(block).dropsLike(block), particle), itemSettings);
	}

	private HavenTorch(Block block, Block wallBlock, Item.Settings itemSettings) {
		super(block, wallBlock, new WallStandingBlockItem(block, wallBlock, itemSettings));
		UNLIT = new Unlit(BLOCK, WALL_BLOCK);
	}
	private HavenTorch(Block block, Block wallBlock, Item.Settings itemSettings, Unlit unlit) {
		super(block, wallBlock, new WallStandingBlockItem(block, wallBlock, itemSettings));
		UNLIT = unlit;
	}

	public static HavenTorch Oxidizable(Oxidizable.OxidizationLevel level, AbstractBlock.Settings blockSettings, ParticleEffect particle) {
		Block block = new OxidizableTorchBlock(level, blockSettings, particle);
		Block wallBlock = new OxidizableWallTorchBlock(level, AbstractBlock.Settings.copy(block).dropsLike(block), particle);
		Block unlit = new OxidizableUnlitTorchBlock(level, block);
		Block unlitWall = new OxidizableUnlitWallTorchBlock(level, wallBlock);
		return new HavenTorch(block, wallBlock, HavenMod.ItemSettings(), new Unlit(block, wallBlock, unlit, unlitWall));
	}

	public static HavenTorch Waterloggable(AbstractBlock.Settings blockSettings, ParticleEffect particle) {
		Block block = new UnderwaterTorchBlock(blockSettings, particle);
		Block wallBlock = new UnderwaterWallTorchBlock(AbstractBlock.Settings.copy(block).dropsLike(block), particle);
		Block unlit = new UnderwaterUnlitTorchBlock(block);
		Block unlitWall = new UnderwaterUnlitWallTorchBlock(wallBlock);
		return new HavenTorch(block, wallBlock, HavenMod.ItemSettings(), new Unlit(block, wallBlock, unlit, unlitWall));
	}

	public static Map<Block, Block> UNLIT_TORCHES = new HashMap<Block, Block>();
	public static Map<Block, Block> UNLIT_WALL_TORCHES = new HashMap<Block, Block>();

	public boolean contains(Block block) {
		return block == UNLIT.UNLIT || block == UNLIT.UNLIT_WALL || super.contains(block);
	}

	public static class Unlit {
		public final Block LIT;
		public final Block LIT_WALL;
		public final Block UNLIT;
		public final Block UNLIT_WALL;
		public Unlit(Block lit, Block litWall) {
			UNLIT = new UnlitTorchBlock(LIT = lit);
			UNLIT_WALL = new UnlitWallTorchBlock(LIT_WALL = litWall);
		}
		public Unlit(Block lit, Block litWall, Block unlit, Block unlitWall) {
			LIT = lit;
			LIT_WALL = litWall;
			UNLIT = unlit;
			UNLIT_WALL = unlitWall;
		}
	}
}
