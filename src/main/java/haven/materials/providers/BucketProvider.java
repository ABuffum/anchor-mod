package haven.materials.providers;

import haven.HavenMod;
import haven.items.buckets.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public interface BucketProvider {
	//Base Item
	public Item getBucket();
	//Vanilla Fluids
	public Item getWaterBucket();
	public Item getLavaBucket();
	public Item getPowderSnowBucket();
	//Mod Fluids
	public Item getBloodBucket();
	public Item getMudBucket();
	//Milk
	public Item getMilkBucket();
	public Item getChocolateMilkBucket();
	public Item getCoffeeMilkBucket();
	public Item getStrawberryMilkBucket();
	public Item getVanillaMilkBucket();
	public Item getCottageCheeseBucket();

	public default Item.Settings BucketSettings() { return HavenMod.ItemSettings().maxCount(16); }
	public default Item.Settings FilledBucketSettings() { return HavenMod.ItemSettings().recipeRemainder(getBucket()).maxCount(1); }

	public default boolean containsBucket(Item item) {
		if (item == null) return false;
		return item == getBucket()
				|| item == getWaterBucket() || item == getLavaBucket() || item == getPowderSnowBucket()
				|| item == getBloodBucket() || item == getMudBucket()
				|| item == getMilkBucket() || item == getChocolateMilkBucket() || item == getCoffeeMilkBucket() || item == getStrawberryMilkBucket() || item == getCottageCheeseBucket();
	}

	public static BucketProvider getProvider(Item item) {
		if (item == null) return null;
		if (item instanceof BucketProvided bucket) return bucket.getBucketProvider();
		else if (DEFAULT_PROVIDER.containsBucket(item)) return DEFAULT_PROVIDER;
		else return null;
	}

	public default ItemStack bucketInMaterial(ItemStack itemStack) {
		if (itemStack == null || itemStack.isEmpty()) return itemStack;
		Item item = itemStack.getItem(), outItem = item;
		BucketProvider bp = getProvider(item);
		if (bp != null) {
			//Base Item
			if (item == bp.getBucket()) outItem = getBucket();
			//Vanilla Fluids
			else if (item == bp.getWaterBucket()) outItem = getWaterBucket();
			else if (item == bp.getLavaBucket()) outItem = getLavaBucket();
			else if (item == bp.getPowderSnowBucket()) outItem = getPowderSnowBucket();
			//Mod Fluids
			else if (item == bp.getBloodBucket()) outItem = getBloodBucket();
			else if (item == bp.getMudBucket()) outItem = getMudBucket();
			//Milk
			else if (item == bp.getMilkBucket()) outItem = getMilkBucket();
			else if (item == bp.getChocolateMilkBucket()) outItem = getChocolateMilkBucket();
			else if (item == bp.getCoffeeMilkBucket()) outItem = getCoffeeMilkBucket();
			else if (item == bp.getStrawberryMilkBucket()) outItem = getStrawberryMilkBucket();
			else if (item == bp.getCottageCheeseBucket()) outItem = getCottageCheeseBucket();
			//Unknown
			else return itemStack;
		}
		else return itemStack;
		return new ItemStack(outItem, itemStack.getCount());
	}

	public static final BucketProvider DEFAULT_PROVIDER = new DefaultBucketProvider();

	static class DefaultBucketProvider implements BucketProvider {
		@Override
		public Item getBucket() { return Items.BUCKET; }
		@Override
		public Item getWaterBucket() { return Items.WATER_BUCKET; }
		@Override
		public Item getLavaBucket() { return Items.LAVA_BUCKET; }
		@Override
		public Item getPowderSnowBucket() { return Items.POWDER_SNOW_BUCKET; }
		@Override
		public Item getBloodBucket() { return HavenMod.BLOOD_BUCKET; }
		@Override
		public Item getMudBucket() { return HavenMod.MUD_BUCKET; }
		@Override
		public Item getMilkBucket() { return Items.MILK_BUCKET; }
		@Override
		public Item getChocolateMilkBucket() { return HavenMod.CHOCOLATE_MILK_BUCKET; }
		@Override
		public Item getCoffeeMilkBucket() { return HavenMod.COFFEE_MILK_BUCKET; }
		@Override
		public Item getStrawberryMilkBucket() { return HavenMod.STRAWBERRY_MILK_BUCKET; }
		@Override
		public Item getVanillaMilkBucket() { return HavenMod.VANILLA_MILK_BUCKET; }
		@Override
		public Item getCottageCheeseBucket() { return HavenMod.COTTAGE_CHEESE_BUCKET; }
		@Override
		public ItemStack bucketInMaterial(ItemStack itemStack) { return itemStack; }

		protected DefaultBucketProvider() { }
	}
}
