package haven.materials.wood;

import haven.ModBase;
import haven.blocks.BookshelfBlock;
import haven.blocks.ChiseledBookshelfBlock;
import haven.blocks.RowBlock;
import haven.blocks.WoodcutterBlock;
import haven.blocks.basic.*;
import haven.containers.BoatContainer;
import haven.containers.SignContainer;
import haven.containers.TorchContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.sounds.ModBlockSoundGroups;
import haven.sounds.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class BambooMaterial extends BaseMaterial implements
		PlanksProvider, StairsProvider, SlabProvider, FenceProvider, FenceGateProvider, DoorProvider, TrapdoorProvider,
		PressurePlateProvider, ButtonProvider, BookshelfProvider, ChiseledBookshelfProvider, LadderProvider, WoodcutterProvider, SignProvider, BoatProvider,
		StrippedBundleProvider, RowProvider,
		TorchProvider, EnderTorchProvider, SoulTorchProvider, CampfireProvider, EnderCampfireProvider, SoulCampfireProvider,
		StrippedLogProvider, StrippedWoodProvider {
	private final BlockContainer planks;
	public BlockContainer getPlanks() { return planks; }
	private final BlockContainer stairs;
	public BlockContainer getStairs() { return stairs; }
	private final BlockContainer slab;
	public BlockContainer getSlab() { return slab; }
	private final BlockContainer fence;
	public BlockContainer getFence() { return fence; }
	private final BlockContainer fence_gate;
	public BlockContainer getFenceGate() { return fence_gate; }
	private final BlockContainer door;
	public BlockContainer getDoor() { return door; }
	private final BlockContainer trapdoor;
	public BlockContainer getTrapdoor() { return trapdoor; }
	private final BlockContainer pressure_plate;
	public BlockContainer getPressurePlate() { return pressure_plate; }
	private final BlockContainer button;
	public BlockContainer getButton() { return button; }
	private final BlockContainer bookshelf;
	public BlockContainer getBookshelf() { return bookshelf; }
	private final BlockContainer chiseled_bookshelf;
	public BlockContainer getChiseledBookshelf() { return chiseled_bookshelf; }
	private final BlockContainer ladder;
	public BlockContainer getLadder() { return ladder; }
	private final BlockContainer woodcutter;
	public BlockContainer getWoodcutter() { return woodcutter; }
	private final SignContainer sign;
	public SignContainer getSign() { return sign; }
	private final BoatContainer boat;
	public BoatContainer getBoat() { return boat; }
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
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
	private final BlockContainer ender_campfire;
	public BlockContainer getEnderCampfire() { return ender_campfire; }
	private final BlockContainer soul_campfire;
	public BlockContainer getSoulCampfire() { return soul_campfire; }
	private final BlockContainer bundle;
	public BlockContainer getBundle() { return bundle; }
	private final BlockContainer stripped_bundle;
	public BlockContainer getStrippedBundle() { return stripped_bundle; }
	private final BlockContainer row;
	public BlockContainer getRow() { return row; }

	public BambooMaterial(String name, MapColor mapColor) {
		super(name, true);
		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		stairs = new BlockContainer(new ModStairsBlock(planks.getBlock()), ItemSettings());
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence_gate = new BlockContainer(new HavenFenceGateBlock(AbstractBlock.Settings.copy(planks.getBlock()), ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE), ItemSettings());
		door = new BlockContainer(new ModDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).strength(3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD).nonOpaque(), ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE), ItemSettings());
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false), ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE), ItemSettings());
		pressure_plate = new BlockContainer(HavenPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).noCollision().strength(0.5F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		bookshelf = new BlockContainer(new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		chiseled_bookshelf = new BlockContainer(new ChiseledBookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(ModBlockSoundGroups.CHISELED_BOOKSHELF)), ItemSettings());
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()), ItemSettings());
		woodcutter = new BlockContainer(new WoodcutterBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.5F)), ItemSettings());
		sign = new SignContainer(name, Material.WOOD, ModBlockSoundGroups.BAMBOO_WOOD);
		boat = new BoatContainer(name, planks.getBlock(), !isFlammable());
		torch = MakeTorch(ModBase.LUMINANCE_14, ModBlockSoundGroups.BAMBOO_WOOD, ParticleTypes.FLAME);
		ender_torch = MakeTorch(ModBase.LUMINANCE_12, ModBlockSoundGroups.BAMBOO_WOOD, ModBase.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(ModBase.LUMINANCE_10, ModBlockSoundGroups.BAMBOO_WOOD, ParticleTypes.SOUL_FIRE_FLAME);
		log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		stripped_log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		stripped_wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		campfire = MakeCampfire(15, 1, mapColor, BlockSoundGroup.BAMBOO, true);
		ender_campfire = MakeCampfire(13, 3, mapColor, BlockSoundGroup.BAMBOO, false);
		soul_campfire = MakeCampfire(10, 2, mapColor, BlockSoundGroup.BAMBOO, false);
		bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.BAMBOO)), ItemSettings());
		stripped_bundle = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(bundle.getBlock())), ItemSettings());
		row = new BlockContainer(new RowBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.0F).sounds(BlockSoundGroup.BAMBOO)), ItemSettings());
	}

	public boolean contains(Block block) {
		return planks.contains(block) || stairs.contains(block) || slab.contains(block) || fence.contains(block) || fence_gate.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || pressure_plate.contains(block) || button.contains(block)
				|| bookshelf.contains(block) || ladder.contains(block) || woodcutter.contains(block) || sign.contains(block)
				|| log.contains(block) || stripped_log.contains(block) || wood.contains(block) || stripped_wood.contains(block)
				|| torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| campfire.contains(block) || ender_campfire.contains(block) || soul_campfire.contains(block)
				|| bundle.contains(block) || stripped_bundle.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return planks.contains(item) || stairs.contains(item) || slab.contains(item) || fence.contains(item) || fence_gate.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || pressure_plate.contains(item) || button.contains(item)
				|| bookshelf.contains(item) || ladder.contains(item) || woodcutter.contains(item) || sign.contains(item) || item == boat.getItem()
				|| log.contains(item) || stripped_log.contains(item) || wood.contains(item) || stripped_wood.contains(item)
				|| torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| campfire.contains(item) || ender_campfire.contains(item) || soul_campfire.contains(item)
				|| bundle.contains(item) || stripped_bundle.contains(item) || super.contains(item);
	}
}
