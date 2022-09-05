package haven.util;

import haven.HavenMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PottedBlock {
	public final Block BLOCK;
	public final Item ITEM;
	public final Block POTTED;

	public static final Block.Settings SETTINGS = AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque();

	public PottedBlock(Block block) {
		BLOCK = block;
		ITEM = new BlockItem(BLOCK, HavenMod.ITEM_SETTINGS);
		POTTED = new FlowerPotBlock(BLOCK, SETTINGS);
	}
}
