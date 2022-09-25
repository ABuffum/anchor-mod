package haven.materials.wood;

import haven.blocks.basic.*;
import haven.boats.HavenBoat;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.SignContainer;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public abstract class WoodMaterial extends BaseMaterial implements
		PlanksProvider, StairsProvider, SlabProvider, FenceProvider, FenceGateProvider, DoorProvider, TrapdoorProvider,
		PressurePlateProvider, ButtonProvider, SignProvider, BoatProvider {
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
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public WoodMaterial(String name, MapColor mapColor, boolean flammable) {
		super(name, flammable);
		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		stairs = new BlockContainer(new HavenStairsBlock(planks.BLOCK));
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		fence_gate = new BlockContainer(new FenceGateBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		door = new BlockContainer(new HavenDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false)));
		pressure_plate = new BlockContainer(new HavenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
		sign = new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD);
		boat = new HavenBoat(name, planks.BLOCK, !isFlammable());
	}

	public boolean contains(Block block) {
		return planks.contains(block) || stairs.contains(block) || slab.contains(block) || fence.contains(block) || fence_gate.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || pressure_plate.contains(block) || button.contains(block)
				|| sign.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return planks.contains(item) || stairs.contains(item) || slab.contains(item) || fence.contains(item) || fence_gate.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || pressure_plate.contains(item) || button.contains(item)
				|| sign.contains(item) || item == boat.ITEM || super.contains(item);
	}
}
