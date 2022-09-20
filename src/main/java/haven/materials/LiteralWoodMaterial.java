package haven.materials;

import haven.HavenMod;
import haven.items.buckets.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.BucketProvider;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;

public class LiteralWoodMaterial extends BaseMaterial implements BucketProvider {
	//Base Item
	private final Item bucket;
	public Item getBucket() { return bucket; }
	//Vanilla Fluids
	private final Item water_bucket;
	public Item getWaterBucket() { return water_bucket; }
	public Item getLavaBucket() { return null; }
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

	public LiteralWoodMaterial() {
		super("wood", false);
		bucket = new HavenBucketItem(Fluids.EMPTY, BucketSettings(), this);
		water_bucket = new HavenBucketItem(Fluids.WATER, FilledBucketSettings(), this);
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
		return containsBucket(item) || super.contains(item);
	}
}
