package fun.mousewich.item.bucket;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ModPowderSnowBucketItem extends PowderSnowBucketItem implements BucketProvided {
	private final BucketProvider bucketProvider;
	public BucketProvider getBucketProvider() { return bucketProvider; }
	public ModPowderSnowBucketItem(Block block, SoundEvent placeSound, Settings settings, BucketProvider bucketProvider) {
		super(block, placeSound, settings);
		this.bucketProvider = bucketProvider;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = super.useOnBlock(context);
		PlayerEntity playerEntity = context.getPlayer();
		if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
			Hand hand = context.getHand();
			playerEntity.setStackInHand(hand, bucketProvider.getBucket().getDefaultStack());
		}
		return actionResult;
	}

	@Override
	public ItemStack getDefaultStack() { return new ItemStack(bucketProvider.getPowderSnowBucket()); }
}
