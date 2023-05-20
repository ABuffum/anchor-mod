package fun.mousewich.item.bucket;

import fun.mousewich.ModBase;
import fun.mousewich.ModFactory;
import fun.mousewich.container.IContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public abstract class BucketProvider implements IContainer {
	//Base Item
	public abstract Item getBucket();
	//Vanilla Fluids
	public abstract Item getWaterBucket();
	public abstract Item getLavaBucket();
	public abstract Item getPowderSnowBucket();
	//Mod Fluids
	public abstract Item getBloodBucket();
	public abstract Item getMudBucket();
	//Milk
	public abstract Item getMilkBucket();
	public abstract Item getChocolateMilkBucket();
	public abstract Item getCoffeeMilkBucket();
	public abstract Item getStrawberryMilkBucket();
	public abstract Item getVanillaMilkBucket();

	public Item.Settings FilledBucketSettings() { return FilledBucketSettings(false); }
	public Item.Settings FilledBucketSettings(boolean netherite) {
		Item.Settings settings = ModFactory.FilledBucketSettings(this::getBucket);
		if (netherite) settings.fireproof();
		return settings;
	}


	@Override
	public boolean contains(Block block) { return false; }
	@Override
	public boolean contains(Item item) {
		if (item == null) return false;
		return item == getBucket()
				|| item == getWaterBucket() || item == getLavaBucket() || item == getPowderSnowBucket()
				|| item == getBloodBucket() || item == getMudBucket()
				|| item == getMilkBucket()
				|| item == getChocolateMilkBucket() || item == getCoffeeMilkBucket() || item == getStrawberryMilkBucket() || item == getVanillaMilkBucket();
	}
	public static BucketProvider getProvider(Item item) {
		if (item == null) return null;
		if (item instanceof BucketProvided bucket) return bucket.getBucketProvider();
		else if (DEFAULT_PROVIDER.contains(item)) return DEFAULT_PROVIDER;
		else return null;
	}
	public ItemStack bucketInMaterial(ItemStack itemStack) {
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
			else if (item == bp.getVanillaMilkBucket()) outItem = getVanillaMilkBucket();
			//Unknown
			else return itemStack;
		}
		else return itemStack;
		return new ItemStack(outItem, itemStack.getCount());
	}

	public static final BucketProvider DEFAULT_PROVIDER = new DefaultBucketProvider();

	private static class DefaultBucketProvider extends BucketProvider {
		@Override
		public Item getBucket() { return Items.BUCKET; }
		@Override
		public Item getWaterBucket() { return Items.WATER_BUCKET; }
		@Override
		public Item getLavaBucket() { return Items.LAVA_BUCKET; }
		@Override
		public Item getPowderSnowBucket() { return Items.POWDER_SNOW_BUCKET; }
		@Override
		public Item getBloodBucket() { return ModBase.BLOOD_BUCKET; }
		@Override
		public Item getMudBucket() { return ModBase.MUD_BUCKET; }
		@Override
		public Item getMilkBucket() { return Items.MILK_BUCKET; }
		@Override
		public Item getChocolateMilkBucket() { return ModBase.CHOCOLATE_MILK_BUCKET; }
		@Override
		public Item getCoffeeMilkBucket() { return ModBase.COFFEE_MILK_BUCKET; }
		@Override
		public Item getStrawberryMilkBucket() { return ModBase.STRAWBERRY_MILK_BUCKET; }
		@Override
		public Item getVanillaMilkBucket() { return ModBase.VANILLA_MILK_BUCKET; }
		@Override
		public ItemStack bucketInMaterial(ItemStack itemStack) { return itemStack; }
		protected DefaultBucketProvider() { }
	}
}