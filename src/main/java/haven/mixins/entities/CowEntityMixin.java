package haven.mixins.entities;

import haven.HavenMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
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
		if (itemStack.isOf(HavenMod.WOOD_BUCKET) && !this.isBaby()) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, HavenMod.WOOD_MILK_BUCKET.getDefaultStack());
			player.setStackInHand(hand, itemStack2);
			cir.setReturnValue(ActionResult.success(this.world.isClient));
		}
		else if (itemStack.isOf(Items.BOWL) && !this.isBaby()) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, HavenMod.MILK_BOWL.getDefaultStack());
			player.setStackInHand(hand, itemStack2);
			cir.setReturnValue(ActionResult.success(this.world.isClient));
		}
	}
}
