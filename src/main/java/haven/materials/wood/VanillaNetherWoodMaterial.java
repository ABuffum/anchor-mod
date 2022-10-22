package haven.materials.wood;

import haven.HavenMod;
import haven.blocks.BookshelfBlock;
import haven.blocks.basic.HavenLadderBlock;
import haven.boats.HavenBoat;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class VanillaNetherWoodMaterial extends BaseMaterial implements
		TorchProvider, EnderTorchProvider, SoulTorchProvider,
		CampfireProvider, EnderCampfireProvider, SoulCampfireProvider,
		BookshelfProvider, LadderProvider, BoatProvider {
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
	private final BlockContainer bookshelf;
	public BlockContainer getBookshelf() { return bookshelf; }
	private final BlockContainer ladder;
	public BlockContainer getLadder() { return ladder; }
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public VanillaNetherWoodMaterial(String name, MapColor mapColor, Block baseBlock, boolean isFlammable) {
		super(name, isFlammable);
		torch = MakeTorch(14, BlockSoundGroup.WOOD, ParticleTypes.FLAME);
		ender_torch = MakeTorch(12, BlockSoundGroup.WOOD, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.WOOD, ParticleTypes.SOUL_FIRE_FLAME);
		campfire = MakeCampfire(15, 1, mapColor, true);
		ender_campfire = MakeCampfire(13, 3, mapColor, false);
		soul_campfire = MakeCampfire(10, 2, mapColor, false);
		bookshelf = new BlockContainer(new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings());
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()), ItemSettings());
		boat = new HavenBoat(name, baseBlock, !isFlammable(), ItemSettings().maxCount(1));
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || ender_campfire.contains(block) || soul_campfire.contains(block)
				|| bookshelf.contains(block) || ladder.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || ender_campfire.contains(item) || soul_campfire.contains(item)
				|| bookshelf.contains(item) || ladder.contains(item) || item == boat.ITEM || super.contains(item);
	}
}
