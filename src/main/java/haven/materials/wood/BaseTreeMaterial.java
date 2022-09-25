package haven.materials.wood;

import haven.blocks.basic.HavenLeavesBlock;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public abstract class BaseTreeMaterial extends WoodMaterial implements
		TorchProvider, SoulTorchProvider, CampfireProvider, SoulCampfireProvider,
		StrippedLogProvider, StrippedWoodProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer log;
	public BlockContainer getLog() { return log; }
	private final BlockContainer stripped_log;
	public BlockContainer getStrippedLog() { return stripped_log; }
	private final BlockContainer wood;
	public BlockContainer getWood() { return wood; }
	private final BlockContainer stripped_wood;
	public BlockContainer getStrippedWood() { return stripped_wood; }
	private final BlockContainer campfire;
	public BlockContainer getCampfire() { return campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }

	public BaseTreeMaterial(String name, MapColor mapColor, boolean isFlammable) {
		super(name, mapColor, isFlammable);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME);
		log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)));
		stripped_log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		stripped_wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.BLOCK)));
		campfire = new BlockContainer(new CampfireBlock(true, 1, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(15)).nonOpaque()));
		soul_campfire = new BlockContainer(new CampfireBlock(false, 2, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(10)).nonOpaque()));
	}

	public boolean contains(Block block) {
		return log.contains(block) || stripped_log.contains(block) || wood.contains(block) || stripped_wood.contains(block)
				|| torch.contains(block) || soul_torch.contains(block) || campfire.contains(block) || soul_campfire.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return log.contains(item) || stripped_log.contains(item) || wood.contains(item) || stripped_wood.contains(item)
				|| torch.contains(item) || soul_torch.contains(item) || campfire.contains(item) || soul_campfire.contains(item)
				|| super.contains(item);
	}
}
