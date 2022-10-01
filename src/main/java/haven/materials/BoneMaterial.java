package haven.materials;

import haven.blocks.RowBlock;
import haven.blocks.basic.HavenLadderBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BoneMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider, LadderProvider, RowProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer ladder;
	public BlockContainer getLadder() { return ladder; }
	private final BlockContainer row;
	public BlockContainer getRow() { return row; }

	public BoneMaterial() {
		super("bone", false);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.BONE), ParticleTypes.SOUL_FIRE_FLAME);
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.BONE).nonOpaque()));
		row = new BlockContainer(new RowBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(BlockSoundGroup.BONE).nonOpaque()));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| ladder.contains(block) || row.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| ladder.contains(item) || row.contains(item)
				|| super.contains(item);
	}

}
