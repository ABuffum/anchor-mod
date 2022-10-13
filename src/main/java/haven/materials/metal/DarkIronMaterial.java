package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.MetalButtonBlock;
import haven.blocks.basic.*;
import haven.items.buckets.*;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.materials.HavenArmorMaterials;
import haven.containers.BlockContainer;
import haven.materials.HavenToolMaterials;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class DarkIronMaterial extends ToolArmorHorseMaterial implements
		NuggetProvider, ShearsProvider, BucketProvider,
		TorchProvider, EnderTorchProvider, SoulTorchProvider,
		ButtonProvider, BarsProvider, BlockProvider, WallProvider, DoorProvider, TrapdoorProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {

	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer button;
	public BlockContainer getButton() { return button; }
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final BlockContainer bars;
	public BlockContainer getBars() { return bars; }
	private final BlockContainer block;
	public BlockContainer getBlock() { return block; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer door;
	public BlockContainer getDoor() { return door; }
	private final BlockContainer trapdoor;
	public BlockContainer getTrapdoor() { return trapdoor; }
	private final BlockContainer cut;
	public BlockContainer getCut() { return cut; }
	private final BlockContainer cut_pillar;
	public BlockContainer getCutPillar() { return cut_pillar; }
	private final BlockContainer cut_slab;
	public BlockContainer getCutSlab() { return cut_slab; }
	private final BlockContainer cut_stairs;
	public BlockContainer getCutStairs() { return cut_stairs; }
	private final BlockContainer cut_wall;
	public BlockContainer getCutWall() { return cut_wall; }

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
	private final Item vanilla_milk_bucket;
	public Item getVanillaMilkBucket() { return vanilla_milk_bucket; }
	private final Item cottage_cheese_bucket;
	public Item getCottageCheeseBucket() { return cottage_cheese_bucket; }

	public DarkIronMaterial() {
		super("dark_iron", false, HavenToolMaterials.DARK_IRON,
				6, -3.1F, -2, -1, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.DARK_IRON, 5);
		torch = MakeTorch(14, BlockSoundGroup.METAL, HavenMod.IRON_FLAME_PARTICLE);
		ender_torch = MakeTorch(10, BlockSoundGroup.METAL, HavenMod.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(10, BlockSoundGroup.METAL, ParticleTypes.SOUL_FIRE_FLAME);
		nugget = new Item(ItemSettings());
		button = new BlockContainer(new MetalButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(1.5F).sounds(BlockSoundGroup.METAL)));
		bars = new BlockContainer(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
		block = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
		wall = new BlockContainer(new HavenWallBlock(block.BLOCK));
		door = new BlockContainer(new HavenDoorBlock(Blocks.IRON_DOOR));
		trapdoor = new BlockContainer(new HavenTrapdoorBlock(Blocks.IRON_TRAPDOOR));
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(block.BLOCK)));
		cut_pillar = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)));
		cut_slab = new BlockContainer(new HavenSlabBlock(cut.BLOCK));
		cut_stairs = new BlockContainer(new HavenStairsBlock(cut.BLOCK));
		cut_wall = new BlockContainer(new HavenWallBlock(cut.BLOCK));

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
		vanilla_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		cottage_cheese_bucket = new CottageCheeseBucketItem(HavenMod.COTTAGE_CHEESE_BLOCK, FilledBucketSettings().food(HavenMod.COTTAGE_CHEESE_FOOD_COMPONENT), this);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| cut.contains(block) || cut_pillar.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block)
				|| cut_wall.contains(block) || bars.contains(block) || this.block.contains(block) || wall.contains(block)
				|| door.contains(block) || trapdoor.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| cut.contains(item) || cut_pillar.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item)
				|| cut_wall.contains(item) || bars.contains(item) || block.contains(item) || wall.contains(item)
				|| door.contains(item) || trapdoor.contains(item) || item == nugget || item == shears || containsBucket(item) || super.contains(item);
	}
}
