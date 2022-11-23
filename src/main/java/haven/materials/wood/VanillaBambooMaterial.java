package haven.materials.wood;

import haven.blocks.basic.*;
import haven.containers.BlockContainer;
import haven.containers.SignContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.sounds.ModBlockSoundGroups;
import haven.sounds.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class VanillaBambooMaterial extends BaseMaterial implements
	PlanksProvider, DoorProvider, TrapdoorProvider, SignProvider, //HangingSignProvider, RaftProvider
	SlabProvider, StairsProvider, FenceProvider, FenceGateProvider, ButtonProvider, PressurePlateProvider {
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

	public VanillaBambooMaterial(String name, MapColor mapColor) {
		super("minecraft:bamboo", true);
		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(planks.getBlock()), ItemSettings());
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence_gate = new BlockContainer(new HavenFenceGateBlock(AbstractBlock.Settings.copy(planks.getBlock()), ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE), ItemSettings());
		door = new BlockContainer(new ModDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).strength(3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD).nonOpaque(), ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN), ItemSettings());
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.0F).sounds(ModBlockSoundGroups.BAMBOO_WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false), ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN), ItemSettings());
		pressure_plate = new BlockContainer(new HavenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).noCollision().strength(0.5F).sounds(ModBlockSoundGroups.BAMBOO_WOOD), ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF), ItemSettings());
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(ModBlockSoundGroups.BAMBOO_WOOD), ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, ModSoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF), ItemSettings());
		sign = new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD);
	}

	public boolean contains(Block block) {
		return planks.contains(block) || stairs.contains(block) || slab.contains(block) || fence.contains(block) || fence_gate.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || pressure_plate.contains(block) || button.contains(block)
				|| sign.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return planks.contains(item) || stairs.contains(item) || slab.contains(item) || fence.contains(item) || fence_gate.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || pressure_plate.contains(item) || button.contains(item)
				|| sign.contains(item) || super.contains(item);
	}
}
