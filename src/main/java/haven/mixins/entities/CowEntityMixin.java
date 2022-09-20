package haven.mixins.entities;

import haven.HavenMod;
import haven.items.buckets.BucketProvided;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
	protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method="interactMob", at = @At("HEAD"), cancellable = true)
	public void InteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack != null && !itemStack.isEmpty() && !this.isBaby()) {
			Item item = itemStack.getItem(), outItem = null;
			if (item instanceof BucketProvided bp) outItem = bp.getBucketProvider().getMilkBucket();
			else if (item == Items.BOWL) outItem = HavenMod.MILK_BOWL;
			if (outItem != null) {
				player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
				ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, outItem.getDefaultStack());
				player.setStackInHand(hand, itemStack2);
				cir.setReturnValue(ActionResult.success(this.world.isClient));
			}
		}
	}
}
