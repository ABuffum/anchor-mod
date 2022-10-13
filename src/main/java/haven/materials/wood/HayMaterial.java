package haven.materials.wood;

import haven.HavenMod;
import haven.containers.TorchContainer;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class HayMaterial extends WoodMaterial implements TorchProvider, EnderTorchProvider, SoulTorchProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	public HayMaterial(String name, MapColor mapColor) {
		super(name, mapColor, true);
		torch = MakeTorch(14, BlockSoundGroup.GRASS, ParticleTypes.FLAME);
		ender_torch = MakeTorch(12, BlockSoundGroup.GRASS, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.GRASS, ParticleTypes.SOUL_FIRE_FLAME);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item) || super.contains(item);
	}
}
