package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.basic.*;
import haven.items.buckets.*;
import haven.materials.base.BaseMaterial;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenPair;
import haven.util.HavenToolMaterials;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class DarkIronMaterial extends ToolArmorHorseMaterial implements
		NuggetProvider, ShearsProvider, BucketProvider,
		TorchProvider, SoulTorchProvider,
		BarsProvider, BlockProvider, WallProvider, DoorProvider, TrapdoorProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {

	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final HavenPair bars;
	public HavenPair getBars() { return bars; }
	private final HavenPair block;
	public HavenPair getBlock() { return block; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	private final HavenPair door;
	public HavenPair getDoor() { return door; }
	private final HavenPair trapdoor;
	public HavenPair getTrapdoor() { return trapdoor; }
	private final HavenPair cut;
	public HavenPair getCut() { return cut; }
	private final HavenPair cut_pillar;
	public HavenPair getCutPillar() { return cut_pillar; }
	private final HavenPair cut_slab;
	public HavenPair getCutSlab() { return cut_slab; }
	private final HavenPair cut_stairs;
	public HavenPair getCutStairs() { return cut_stairs; }
	private final HavenPair cut_wall;
	public HavenPair getCutWall() { return cut_wall; }

	private final Item shears;
	public Item getShears() { return shears; }

	//Base Item
	private final Item bucket;
	public Item getBucket() { return bucket; }
	//Vanilla Fluids
	private final Item water_bucket;
	public Item getWaterBucket() { return water_bucket; }
	private final Item lava_bucket;
	public Item getLavaBucket() { return lava_bucket; }
	private final Item powder_snow_bucket;
	public Item getPowderSnowBucket() { return powder_snow_bucket; }
	//Mod Fluids
	private final Item blood_bucket;
	public Item getBloodBucket() { return blood_bucket; }
	private final Item mud_bucket;
	public Item getMudBucket() { return mud_bucket; }
	//Milk
	private final Item milk_bucket;
	public Item getMilkBucket() { return milk_bucket; }
	private final Item chocolate_milk_bucket;
	public Item getChocolateMilkBucket() { return chocolate_milk_bucket; }
	private final Item coffee_milk_bucket;
	public Item getCoffeeMilkBucket() { return coffee_milk_bucket; }
	private final Item strawberry_milk_bucket;
	public Item getStrawberryMilkBucket() { return strawberry_milk_bucket; }
	private final Item cottage_cheese_bucket;
	public Item getCottageCheeseBucket() { return cottage_cheese_bucket; }

	public DarkIronMaterial() {
		super("dark_iron", false, HavenToolMaterials.DARK_IRON,
				6, -3.1F, -2, -1, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.DARK_IRON, 5);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.IRON_FLAME, ItemSettings());
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		nugget = new Item(ItemSettings());
		bars = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()), ItemSettings());
		block = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(block.BLOCK), ItemSettings());
		door = new HavenPair(new HavenDoorBlock(Blocks.IRON_DOOR), ItemSettings());
		trapdoor = new HavenPair(new HavenTrapdoorBlock(Blocks.IRON_TRAPDOOR), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(block.BLOCK)), ItemSettings());
		cut_pillar = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());

		shears = new ShearsItem(ItemSettings().maxDamage(238));

		bucket = new HavenBucketItem(Fluids.EMPTY, BucketSettings(), this);
		water_bucket = new HavenBucketItem(Fluids.WATER, FilledBucketSettings(), this);
		lava_bucket = new HavenBucketItem(Fluids.LAVA, FilledBucketSettings(), this);
		powder_snow_bucket = new HavenPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, FilledBucketSettings(), this);
		blood_bucket = new HavenBucketItem(HavenMod.STILL_BLOOD_FLUID, FilledBucketSettings(), this);
		mud_bucket = new HavenBucketItem(HavenMod.STILL_MUD_FLUID, FilledBucketSettings(), this);
		milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		chocolate_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		coffee_milk_bucket = new CoffeeMilkBucketItem(FilledBucketSettings(), this);
		strawberry_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		cottage_cheese_bucket = new CottageCheeseBucketItem(HavenMod.COTTAGE_CHEESE_BLOCK, FilledBucketSettings().food(HavenMod.COTTAGE_CHEESE_FOOD_COMPONENT), this);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| block == cut.BLOCK || block == cut_pillar.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK
				|| block == cut_wall.BLOCK || block == bars.BLOCK || block == this.block.BLOCK || block == wall.BLOCK
				|| block == door.BLOCK || block == trapdoor.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| item == cut.ITEM || item == cut_pillar.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM
				|| item == cut_wall.ITEM || item == bars.ITEM || item == block.ITEM || item == wall.ITEM
				|| item == door.ITEM || item == trapdoor.ITEM || item == nugget || item == shears || containsBucket(item) || super.contains(item);
	}
}
