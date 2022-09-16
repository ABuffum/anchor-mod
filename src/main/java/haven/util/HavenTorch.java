package haven.util;

import haven.HavenMod;
import haven.blocks.basic.HavenTorchBlock;
import haven.blocks.basic.HavenWallTorchBlock;
import haven.blocks.oxidizable.OxidizableTorchBlock;
import haven.blocks.oxidizable.OxidizableWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;

public class HavenTorch extends WalledBlock {

	public HavenTorch(AbstractBlock.Settings settings, ParticleEffect particle) {
		this(settings, particle, HavenMod.ItemSettings());
	}

	public HavenTorch(AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new HavenTorchBlock(blockSettings, particle), new HavenWallTorchBlock(blockSettings, particle), itemSettings);
	}

	private HavenTorch(Block block, Block wallBlock, Item.Settings itemSettings) {
		super(block, wallBlock, new WallStandingBlockItem(block, wallBlock, itemSettings));
	}

	public static HavenTorch Oxidizable(Oxidizable.OxidizationLevel level, AbstractBlock.Settings settings, ParticleEffect particle) {
		return Oxidizable(level, settings, particle, HavenMod.ItemSettings());
	}
	public static HavenTorch Oxidizable(Oxidizable.OxidizationLevel level, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		Block block = new OxidizableTorchBlock(level, blockSettings, particle);
		Block wallBlock = new OxidizableWallTorchBlock(level, blockSettings, particle);
		return new HavenTorch(block, wallBlock, itemSettings);
	}
}
