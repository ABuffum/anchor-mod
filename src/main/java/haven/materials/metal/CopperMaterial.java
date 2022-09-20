package haven.materials.metal;

import haven.HavenMod;
import haven.items.buckets.*;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.util.HavenArmorMaterials;
import haven.util.HavenToolMaterials;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;

public class CopperMaterial extends ToolArmorHorseMaterial implements
		NuggetProvider, ShearsProvider, BucketProvider {
	private final Item nugget;
	public Item getNugget() { return nugget; }
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

	public CopperMaterial() {
		super("copper", false, HavenToolMaterials.COPPER,
				6, -3, -1, -2, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.COPPER, 6);
		nugget = new Item(ItemSettings());
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

	public boolean contains(Item item) {
		return item == nugget || item == shears || containsBucket(item) || super.contains(item);
	}
}
