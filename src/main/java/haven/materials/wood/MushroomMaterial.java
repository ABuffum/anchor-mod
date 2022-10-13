package haven.materials.wood;

import haven.HavenMod;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class MushroomMaterial extends WoodMaterial implements
		TorchProvider, EnderTorchProvider, SoulTorchProvider,
		CampfireProvider, EnderCampfireProvider, SoulCampfireProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer ender_campfire;
	public BlockContainer getEnderCampfire() { return ender_campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }
	public MushroomMaterial(String name, MapColor mapColor, boolean flammable) {
		super(name, mapColor, flammable);
		torch = MakeTorch(14, BlockSoundGroup.WOOD, ParticleTypes.FLAME);
		ender_torch = MakeTorch(12, BlockSoundGroup.WOOD, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.WOOD, ParticleTypes.SOUL_FIRE_FLAME);
		campfire = MakeCampfire(15, 1, mapColor);
		ender_campfire = MakeCampfire(13, 3, mapColor);
		soul_campfire = MakeCampfire(10, 2, mapColor);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || ender_campfire.contains(block) || soul_campfire.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || ender_campfire.contains(item) || soul_campfire.contains(item)
				|| super.contains(item);
	}
}
