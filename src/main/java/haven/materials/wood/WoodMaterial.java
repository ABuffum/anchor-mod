package haven.materials.wood;

import haven.blocks.basic.*;
import haven.boats.HavenBoat;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenPair;
import haven.util.HavenSign;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public abstract class WoodMaterial extends BaseMaterial implements
		PlanksProvider, StairsProvider, SlabProvider, FenceProvider, FenceGateProvider, DoorProvider, TrapdoorProvider,
		PressurePlateProvider, ButtonProvider, SignProvider, BoatProvider {
	private final HavenPair planks;
	public HavenPair getPlanks() { return planks; }
	private final HavenPair stairs;
	public HavenPair getStairs() { return stairs; }
	private final HavenPair slab;
	public HavenPair getSlab() { return slab; }
	private final HavenPair fence;
	public HavenPair getFence() { return fence; }
	private final HavenPair fence_gate;
	public HavenPair getFenceGate() { return fence_gate; }
	private final HavenPair door;
	public HavenPair getDoor() { return door; }
	private final HavenPair trapdoor;
	public HavenPair getTrapdoor() { return trapdoor; }
	private final HavenPair pressure_plate;
	public HavenPair getPressurePlate() { return pressure_plate; }
	private final HavenPair button;
	public HavenPair getButton() { return button; }
	private final HavenSign sign;
	public HavenSign getSign() { return sign; }
	private final HavenBoat boat;
	public HavenBoat getBoat() { return boat; }

	public WoodMaterial(String name, MapColor mapColor, boolean flammable) {
		super(name, flammable);
		planks = new HavenPair(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		stairs = new HavenPair(new HavenStairsBlock(planks.BLOCK));
		slab = new HavenPair(new SlabBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		fence = new HavenPair(new FenceBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		fence_gate = new HavenPair(new FenceGateBlock(AbstractBlock.Settings.copy(planks.BLOCK)));
		door = new HavenPair(new HavenDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
		trapdoor = new HavenPair(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false)));
		pressure_plate = new HavenPair(new HavenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
		button = new HavenPair(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
		sign = new HavenSign(name, Material.WOOD, BlockSoundGroup.WOOD);
		boat = new HavenBoat(name, planks.BLOCK, !isFlammable());
	}

	public boolean contains(Block block) {
		return block == planks.BLOCK || block == stairs.BLOCK || block == slab.BLOCK || block == fence.BLOCK || block == fence_gate.BLOCK
				|| block == door.BLOCK || block == trapdoor.BLOCK || block == pressure_plate.BLOCK || block == button.BLOCK
				|| block == sign.BLOCK || block == sign.WALL_BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return item == planks.ITEM || item == stairs.ITEM || item == slab.ITEM || item == fence.ITEM || item == fence_gate.ITEM
				|| item == door.ITEM || item == trapdoor.ITEM || item == pressure_plate.ITEM || item == button.ITEM
				|| item == sign.ITEM || item == boat.ITEM || super.contains(item);
	}
}
