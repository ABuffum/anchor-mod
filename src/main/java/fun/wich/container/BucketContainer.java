package fun.wich.container;

import fun.wich.ModBase;
import fun.wich.ModFactory;
import fun.wich.block.fluid.BloodCauldronBlock;
import fun.wich.block.fluid.MudCauldronBlock;
import fun.wich.gen.data.ModDatagen;
import fun.wich.item.bucket.*;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public class BucketContainer extends BucketProvider {
	//Base Item
	protected final Item bucket;
	public Item getBucket() { return bucket; }
	//Vanilla Fluids
	protected final Item water_bucket;
	public Item getWaterBucket() { return water_bucket; }
	protected final Item lava_bucket;
	public Item getLavaBucket() { return lava_bucket; }
	protected final Item powder_snow_bucket;
	public Item getPowderSnowBucket() { return powder_snow_bucket; }
	//Mod Fluids
	protected final Item blood_bucket;
	public Item getBloodBucket() { return blood_bucket; }
	protected final Item mud_bucket;
	public Item getMudBucket() { return mud_bucket; }
	//Milk
	protected final Item milk_bucket;
	public Item getMilkBucket() { return milk_bucket; }
	protected final Item chocolate_milk_bucket;
	public Item getChocolateMilkBucket() { return chocolate_milk_bucket; }
	protected final Item coffee_milk_bucket;
	public Item getCoffeeMilkBucket() { return coffee_milk_bucket; }
	protected final Item strawberry_milk_bucket;
	public Item getStrawberryMilkBucket() { return strawberry_milk_bucket; }
	protected final Item vanilla_milk_bucket;
	public Item getVanillaMilkBucket() { return vanilla_milk_bucket; }

	public BucketContainer() { this(false); }
	public BucketContainer(boolean netherite) { this(netherite, true); }
	public BucketContainer(boolean netherite, boolean lavabucket) {
		bucket = new ModBucketItem(Fluids.EMPTY, netherite ? ModFactory.BucketSettings().fireproof() : ModFactory.BucketSettings(), this);
		water_bucket = new ModBucketItem(Fluids.WATER, FilledBucketSettings(netherite), this);
		lava_bucket = lavabucket ? new ModBucketItem(Fluids.LAVA, FilledBucketSettings(netherite), this) : null;
		powder_snow_bucket = new ModPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, FilledBucketSettings(netherite), this);
		blood_bucket = new ModBucketItem(ModBase.STILL_BLOOD_FLUID, FilledBucketSettings(netherite), this);
		BloodCauldronBlock.BEHAVIOR.put(blood_bucket, MudCauldronBlock.FillFromBucket(bucket));
		BloodCauldronBlock.BEHAVIOR.put(bucket, MudCauldronBlock.EmptyToBucket(blood_bucket));
		mud_bucket = new ModBucketItem(ModBase.STILL_MUD_FLUID, FilledBucketSettings(netherite), this);
		MudCauldronBlock.BEHAVIOR.put(mud_bucket, MudCauldronBlock.FillFromBucket(bucket));
		MudCauldronBlock.BEHAVIOR.put(bucket, MudCauldronBlock.EmptyToBucket(mud_bucket));
		milk_bucket = new ModMilkBucketItem(FilledBucketSettings(netherite), this);
		chocolate_milk_bucket = new ModMilkBucketItem(FilledBucketSettings(netherite), this);
		coffee_milk_bucket = new CoffeeMilkBucketItem(FilledBucketSettings(netherite), this);
		strawberry_milk_bucket = new ModMilkBucketItem(FilledBucketSettings(netherite), this);
		vanilla_milk_bucket = new ModMilkBucketItem(FilledBucketSettings(netherite), this);
		//Datagen
		ModDatagen.Cache.Models.GENERATED.addAll(GetAll());
	}

	public List<Item> GetAll() {
		List<Item> items = new ArrayList<>(List.of(bucket, water_bucket, powder_snow_bucket,
				blood_bucket, mud_bucket,
				milk_bucket, chocolate_milk_bucket, coffee_milk_bucket, strawberry_milk_bucket, vanilla_milk_bucket));
		if (lava_bucket != null) items.add(lava_bucket);
		return items;
	}
}
