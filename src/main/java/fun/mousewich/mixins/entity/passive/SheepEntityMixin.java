package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.sheep.MossySheepEntity;
import fun.mousewich.entity.passive.sheep.RainbowSheepEntity;
import fun.mousewich.util.ColorUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements Shearable, EntityWithBloodType {
	@Shadow public abstract DyeColor getColor();

	protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/SheepEntity;", at=@At("RETURN"))
	public void SetChildColor(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<SheepEntity> cir) {
		SheepEntity child = cir.getReturnValue();
		if (passiveEntity instanceof MossySheepEntity) child.setColor(getColor());
		else if (passiveEntity instanceof RainbowSheepEntity) child.setColor(ColorUtil.GetRandomColor(random));
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		EntityType<?> type = other.getType();
		if (!(type == ModBase.MOSSY_SHEEP_ENTITY || type == ModBase.RAINBOW_SHEEP_ENTITY || type == getType())) return false;
		else return this.isInLove() && other.isInLove();
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.SHEEP_BLOOD_TYPE; }
}
