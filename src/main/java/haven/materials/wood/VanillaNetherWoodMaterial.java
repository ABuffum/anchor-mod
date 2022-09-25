package haven.materials.wood;

import haven.boats.BoatMaterial;
import haven.boats.HavenBoat;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class VanillaNetherWoodMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider,
		CampfireProvider, SoulCampfireProvider,
		BoatProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public VanillaNetherWoodMaterial(String name, MapColor mapColor, Block baseBlock, boolean isFlammable) {
		super(name, isFlammable);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME);
		boat = new HavenBoat(name, baseBlock, !isFlammable(), ItemSettings().maxCount(1));
		campfire = new BlockContainer(new CampfireBlock(true, 1, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(15)).nonOpaque()));
		soul_campfire = new BlockContainer(new CampfireBlock(false, 2, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || soul_campfire.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || soul_campfire.contains(item)
				|| item == boat.ITEM || super.contains(item);
	}
}
