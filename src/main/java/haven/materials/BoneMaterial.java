package haven.materials;

import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BoneMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider {
	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }

	public BoneMaterial() {
		super("bone", false);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.BONE), ParticleTypes.SOUL_FIRE_FLAME);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item) || super.contains(item);
	}

}
