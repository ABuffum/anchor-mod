package haven.util;

import haven.HavenMod;
import haven.blocks.HavenTorchBlock;
import haven.blocks.HavenWallTorchBlock;
import haven.blocks.oxidizable.OxidizableTorchBlock;
import haven.blocks.oxidizable.OxidizableWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;

public class HavenTorch {
	public final Block BLOCK;
	public final Item ITEM;
	public final Block WALL_BLOCK;

	public HavenTorch(AbstractBlock.Settings settings, ParticleEffect particle) {
		this( settings, particle, HavenMod.ITEM_SETTINGS);
	}

	public HavenTorch(AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new HavenTorchBlock(blockSettings, particle), new HavenWallTorchBlock(blockSettings, particle), itemSettings);
	}

	private HavenTorch(Block block, Block wallBlock, Item.Settings itemSettings) {
		BLOCK = block;
		WALL_BLOCK = wallBlock;
		ITEM = new WallStandingBlockItem(BLOCK, WALL_BLOCK, itemSettings);
	}

	public static HavenTorch Oxidizable(Oxidizable.OxidizationLevel level, AbstractBlock.Settings settings, ParticleEffect particle) {
		return Oxidizable(level, settings, particle, HavenMod.ITEM_SETTINGS);
	}
	public static HavenTorch Oxidizable(Oxidizable.OxidizationLevel level, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		Block block = new OxidizableTorchBlock(level, blockSettings, particle);
		Block wallBlock = new OxidizableWallTorchBlock(level, blockSettings, particle);
		return new HavenTorch(block, wallBlock, itemSettings);
	}
}
