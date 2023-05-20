package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.variants.SnowGolemVariant;
import fun.mousewich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity implements Shearable, RangedAttackMob, EntityWithBloodType {
	protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at = @At("TAIL"))
	protected void initGoals(CallbackInfo ci) {
		this.targetSelector.add(1, MobHostilityPower.makeHostilityGoal(this, EntityType.SNOW_GOLEM));
	}

	@Inject(method="sheared", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/SnowGolemEntity;dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;"), cancellable=true)
	protected void DropPumpkin(SoundCategory shearedSoundCategory, CallbackInfo ci) {
		SnowGolemVariant variant = SnowGolemVariant.get((SnowGolemEntity)(Object)this);
		if (variant != SnowGolemVariant.DEFAULT) {
			this.dropStack(new ItemStack(variant.item), 1.7f);
			ci.cancel();
		}
	}

	@Override public BloodType GetDefaultBloodType() { return BloodType.WATER; }
}
