package haven.materials.wood;

import haven.blocks.basic.HavenLeavesBlock;
import haven.containers.BlockContainer;
import haven.materials.providers.LeavesProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class LeavedTreeMaterial extends BaseTreeMaterial implements LeavesProvider {
	private final BlockContainer leaves;
	public BlockContainer getLeaves() { return leaves; }

	public LeavedTreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, boolean isFlammable) {
		super(name, mapColor, isFlammable, BlockSoundGroup.WOOD);
		leaves = new BlockContainer(new HavenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(leafSounds).nonOpaque().allowsSpawning(BaseTreeMaterial::canSpawnOnLeaves).suffocates(BaseTreeMaterial::never).blockVision(BaseTreeMaterial::never)), ItemSettings());
	}

	public boolean contains(Block block) {
		return leaves.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return leaves.contains(item) || super.contains(item);
	}
}
