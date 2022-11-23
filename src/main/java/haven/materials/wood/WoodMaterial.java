package haven.materials.wood;

import haven.blocks.BookshelfBlock;
import haven.blocks.ChiseledBookshelfBlock;
import haven.blocks.WoodcutterBlock;
import haven.blocks.basic.*;
import haven.containers.BoatContainer;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.SignContainer;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public abstract class WoodMaterial extends BaseMaterial implements
		PlanksProvider, StairsProvider, SlabProvider, FenceProvider, FenceGateProvider, DoorProvider, TrapdoorProvider,
		PressurePlateProvider, ButtonProvider, BookshelfProvider, ChiseledBookshelfProvider, LadderProvider, WoodcutterProvider, SignProvider, BoatProvider {
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

	public WoodMaterial(String name, MapColor mapColor, boolean flammable, BlockSoundGroup sounds) {
		super(name, flammable);
		planks = new BlockContainer(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(sounds)), ItemSettings());
		stairs = new BlockContainer(new HavenStairsBlock(planks.getBlock()), ItemSettings());
		slab = new BlockContainer(new SlabBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence = new BlockContainer(new FenceBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		fence_gate = new BlockContainer(new FenceGateBlock(AbstractBlock.Settings.copy(planks.getBlock())), ItemSettings());
		door = new BlockContainer(new ModDoorBlock(AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).strength(3.0F).sounds(sounds).nonOpaque(), SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE), ItemSettings());
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.0F).sounds(sounds).nonOpaque().allowsSpawning((a, b, c, d) -> false), SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE), ItemSettings());
		pressure_plate = new BlockContainer(HavenPressurePlateBlock.Wooden(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, planks.getBlock().getDefaultMapColor()).noCollision().strength(0.5F).sounds(sounds)), ItemSettings());
		button = new BlockContainer(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(sounds)), ItemSettings());
		bookshelf = new BlockContainer(new BookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(sounds)), ItemSettings());
		chiseled_bookshelf = new BlockContainer(new ChiseledBookshelfBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(1.5F).sounds(sounds)), ItemSettings());
		ladder = new BlockContainer(new HavenLadderBlock(AbstractBlock.Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.LADDER).nonOpaque()), ItemSettings());
		woodcutter = new BlockContainer(new WoodcutterBlock(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(3.5F)), ItemSettings());
		sign = new SignContainer(name, Material.WOOD, sounds);
		boat = new BoatContainer(name, planks.getBlock(), !isFlammable());
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
				|| bookshelf.contains(item) || ladder.contains(item) || woodcutter.contains(item) || sign.contains(item) || item == boat.getItem()
				|| super.contains(item);
	}
}
