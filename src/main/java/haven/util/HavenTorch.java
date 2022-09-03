package haven.util;

import haven.HavenMod;
import haven.blocks.HavenTorchBlock;
import haven.blocks.HavenWallTorchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HavenTorch {
	public final Identifier ID;
	public final Block BLOCK;
	public final Item ITEM;
	public final Identifier WALL_ID;
	public final Block WALL_BLOCK;

	public HavenTorch(String path, String wallPath, AbstractBlock.Settings settings, ParticleEffect particle) {
		this(path, wallPath, settings, particle, HavenMod.ITEM_SETTINGS);
	}

	public HavenTorch(String path, String wallPath, AbstractBlock.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		ID = HavenMod.ID(path);
		BLOCK = new HavenTorchBlock(blockSettings, particle);
		WALL_ID = HavenMod.ID(wallPath);
		WALL_BLOCK = new HavenWallTorchBlock(blockSettings, particle);
		ITEM = new WallStandingBlockItem(BLOCK, WALL_BLOCK, itemSettings);
	}

	public void Register() {
		Registry.register(Registry.BLOCK, ID, BLOCK);
		Registry.register(Registry.BLOCK, WALL_ID, WALL_BLOCK);
		Registry.register(Registry.ITEM, ID, ITEM);
	}
}
