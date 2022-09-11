package haven.materials;

import haven.blocks.basic.*;
import haven.boats.HavenBoat;
import haven.util.HavenPair;
import haven.util.HavenSign;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;

public class WoodMaterial {
	private final String name;
	public String getName() { return name; }

	public final boolean isFlammable;

	public final HavenPair PLANKS;
	public final HavenPair STAIRS;
	public final HavenPair SLAB;
	public final HavenPair FENCE;
	public final HavenPair FENCE_GATE;
	public final HavenPair DOOR;
	public final HavenPair TRAPDOOR;
	public final HavenPair PRESSURE_PLATE;
	public final HavenPair BUTTON;

	public final HavenSign SIGN;

	public final HavenBoat BOAT;

	public WoodMaterial(String name, MapColor mapColor) {
		this(name, mapColor, true);
	}
	public WoodMaterial(String name, MapColor mapColor, boolean isFlammable) {
		this.name = name;
		this.isFlammable = isFlammable;

		PLANKS = new HavenPair(new Block(AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
		STAIRS = new HavenPair(new HavenStairsBlock(PLANKS.BLOCK));
		SLAB = new HavenPair(new SlabBlock(AbstractBlock.Settings.copy(PLANKS.BLOCK)));
		FENCE = new HavenPair(new FenceBlock(AbstractBlock.Settings.copy(PLANKS.BLOCK)));
		FENCE_GATE = new HavenPair(new FenceGateBlock(AbstractBlock.Settings.copy(PLANKS.BLOCK)));
		DOOR = new HavenPair(new HavenDoorBlock(AbstractBlock.Settings.of(Material.WOOD, PLANKS.BLOCK.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque()));
		TRAPDOOR = new HavenPair(new HavenTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((a, b, c, d) -> false)));
		PRESSURE_PLATE = new HavenPair(new HavenPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, PLANKS.BLOCK.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));
		BUTTON = new HavenPair(new HavenWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD)));

		SIGN = new HavenSign(name, Material.WOOD, BlockSoundGroup.WOOD);

		BOAT = new HavenBoat(name, PLANKS.BLOCK, !isFlammable);
	}
}
