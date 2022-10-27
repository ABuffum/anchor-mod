package haven.materials.wood;

import haven.blocks.BookshelfBlock;
import haven.blocks.WoodcutterBlock;
import haven.blocks.basic.*;
import haven.containers.BlockContainer;
import haven.containers.SignContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.sounds.HavenBlockSoundGroups;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class NewBambooMaterial extends BaseMaterial implements
	PlanksProvider, DoorProvider, TrapdoorProvider, SignProvider, //HangingSignProvider,
	SlabProvider, StairsProvider, FenceProvider, FenceGateProvider, ButtonProvider, PressurePlateProvider, //RaftProvider,
	BookshelfProvider, LadderProvider, WoodcutterProvider {
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
	private final BlockContainer ladder;
	public BlockContainer getLadder() { return ladder; }
	private final BlockContainer woodcutter;
	public BlockContainer getWoodcutter() { return woodcutter; }
	private final SignContainer sign;
	public SignContainer getSign() { return sign; }

	public NewBambooMaterial(String name, MapColor mapColor) {
		super("new_bamboo", true);
		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(planks.BLOCK), ItemSettings());
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.BLOCK)), ItemSettings());
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.BLOCK)), ItemSettings());
		fence_gate = new BlockContainer(new FenceGateBlock(AbstractBlock.Settings.copy(planks.BLOCK)), ItemSettings());
		door = new BlockContainer(new HavenDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).strength(3.0F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD).nonOpaque()), ItemSettings());
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.0F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false)), ItemSettings());
		pressure_plate = new BlockContainer(new HavenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.BLOCK.getDefaultMapColor()).noCollision().strength(0.5F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		bookshelf = new BlockContainer(new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(HavenBlockSoundGroups.BAMBOO_WOOD)), ItemSettings());
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()), ItemSettings());
		woodcutter = new BlockContainer(new WoodcutterBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.5F)), ItemSettings());
		sign = new SignContainer(name, Material.WOOD, BlockSoundGroup.WOOD);
	}

	public boolean contains(Block block) {
		return planks.contains(block) || stairs.contains(block) || slab.contains(block) || fence.contains(block) || fence_gate.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || pressure_plate.contains(block) || button.contains(block)
				|| bookshelf.contains(block) || ladder.contains(block) || woodcutter.contains(block) || sign.contains(block)
				|| super.contains(block);
	}
	public boolean contains(Item item) {
		return planks.contains(item) || stairs.contains(item) || slab.contains(item) || fence.contains(item) || fence_gate.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || pressure_plate.contains(item) || button.contains(item)
				|| bookshelf.contains(item) || ladder.contains(item) || woodcutter.contains(item) || sign.contains(item)
				|| super.contains(item);
	}
}
