package haven.materials.wood;

import haven.blocks.PropaguleBlock;
import haven.blocks.basic.*;
import haven.containers.BoatContainer;
import haven.containers.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class MangroveMaterial extends BaseMaterial implements
		StrippedLogProvider, StrippedWoodProvider, LeavesProvider, PropaguleProvider,
		PlanksProvider, DoorProvider, TrapdoorProvider, SignProvider, BoatProvider, //HangingSignProvider
		SlabProvider, StairsProvider, FenceProvider, FenceGateProvider, ButtonProvider, PressurePlateProvider {

	private final BlockContainer log;
	public BlockContainer getLog() { return log; }
	private final BlockContainer stripped_log;
	public BlockContainer getStrippedLog() { return stripped_log; }
	private final BlockContainer wood;
	public BlockContainer getWood() { return wood; }
	private final BlockContainer stripped_wood;
	public BlockContainer getStrippedWood() { return stripped_wood; }
	private final BlockContainer leaves;
	public BlockContainer getLeaves() { return leaves; }

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
	private final SignContainer sign;
	public SignContainer getSign() { return sign; }
	private final BoatContainer boat;
	public BoatContainer getBoat() { return boat; }

	private final PottedBlockContainer propagule;
	public PottedBlockContainer getPropagule() { return propagule; }

	public MangroveMaterial(String name, MapColor mapColor) {
		super(name, true);
		log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD)), ItemSettings());
		stripped_log = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		stripped_wood = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(log.getBlock())), ItemSettings());
		leaves = new BlockContainer(new HavenLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BaseTreeMaterial::canSpawnOnLeaves).suffocates(BaseTreeMaterial::never).blockVision(BaseTreeMaterial::never)), ItemSettings());

		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(planks.getBlock()), ItemSettings());
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence_gate = new BlockContainer(new FenceGateBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		door = new BlockContainer(new ModDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE), ItemSettings());
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false), SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE), ItemSettings());
		pressure_plate = new BlockContainer(HavenPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings());
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)), ItemSettings());
		sign = new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD);
		boat = new BoatContainer(name, planks.getBlock(), false);

		propagule = new PottedBlockContainer(new PropaguleBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));
	}

	public boolean contains(Block block) {
		return log.contains(block) || stripped_log.contains(block) || wood.contains(block) || stripped_wood.contains(block)
				|| leaves.contains(block) || propagule.contains(block)
				|| planks.contains(block) || stairs.contains(block) || slab.contains(block) || fence.contains(block) || fence_gate.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || pressure_plate.contains(block) || button.contains(block)
				|| sign.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return log.contains(item) || stripped_log.contains(item) || wood.contains(item) || stripped_wood.contains(item)
				|| leaves.contains(item) || propagule.contains(item)
				|| planks.contains(item) || stairs.contains(item) || slab.contains(item) || fence.contains(item) || fence_gate.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || pressure_plate.contains(item) || button.contains(item)
				|| sign.contains(item) || item == boat.getItem() || super.contains(item);
	}
}
