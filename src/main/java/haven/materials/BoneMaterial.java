package haven.materials;

import haven.HavenMod;
import haven.blocks.RowBlock;
import haven.blocks.basic.HavenLadderBlock;
import haven.containers.BlockContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.TorchContainer;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BoneMaterial extends BaseMaterial implements
		TorchProvider, EnderTorchProvider, SoulTorchProvider, LadderProvider, RowProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer ladder;
	public BlockContainer getLadder() { return ladder; }
	private final BlockContainer row;
	public BlockContainer getRow() { return row; }

	public BoneMaterial() {
		super("bone", false);
		torch = MakeTorch(14, BlockSoundGroup.BONE, ParticleTypes.FLAME);
		ender_torch = MakeTorch(12, BlockSoundGroup.BONE, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.BONE, ParticleTypes.SOUL_FIRE_FLAME);
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.BONE).nonOpaque()), ItemSettings());
		row = new BlockContainer(new RowBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(BlockSoundGroup.BONE).nonOpaque()), ItemSettings());
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| ladder.contains(block) || row.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| ladder.contains(item) || row.contains(item)
				|| super.contains(item);
	}

}
