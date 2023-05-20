package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.util.ItemUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PufferfishEntity.class)
public abstract class PufferfishEntityMixin extends FishEntity implements EntityWithBloodType {
	public PufferfishEntityMixin(EntityType<? extends FishEntity> entityType, World world) { super(entityType, world); }

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.GLASS_BOTTLE) && !this.isBaby()) {
			player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0f, 1.0f);
			ItemStack itemStack2 = ItemUtil.getConsumableRemainder(itemStack, player, ModBase.PUFFERFISH_POISON_VIAL);
			player.setStackInHand(hand, itemStack2);
			return ActionResult.success(this.world.isClient);
		}
		return super.interactMob(player, hand);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.PUFFERFISH_BLOOD_TYPE; }
}
