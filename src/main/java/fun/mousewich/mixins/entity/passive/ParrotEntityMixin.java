package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.variants.ParrotVariant;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity implements Flutterer, EntityWithBloodType {
	@Shadow @Final static Map<EntityType<?>, SoundEvent> MOB_SOUNDS;
	@Shadow public abstract void setVariant(int variant);
	@Shadow private static SoundEvent getSound(EntityType<?> imitate) { return null; }

	@Shadow @Final private static TrackedData<Integer> VARIANT;

	protected ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) { super(entityType, world); }

	private static final Predicate<MobEntity> CAN_IMITATE_NEW = new Predicate<>(){
		@Override
		public boolean test(MobEntity mobEntity) {
			if (mobEntity == null) return false;
			EntityType<?> type = mobEntity.getType();
			if (type == ModBase.WARDEN_ENTITY) {
				if (!MOB_SOUNDS.containsKey(ModBase.WARDEN_ENTITY)) {
					MOB_SOUNDS.put(ModBase.WARDEN_ENTITY, ModSoundEvents.ENTITY_PARROT_IMITATE_WARDEN);
				}
			}
			if (type == ModBase.RED_PHANTOM_ENTITY) {
				if (!MOB_SOUNDS.containsKey(ModBase.RED_PHANTOM_ENTITY)) {
					MOB_SOUNDS.put(ModBase.RED_PHANTOM_ENTITY, SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM);
				}
			}
			return MOB_SOUNDS.containsKey(type);
		}
	};

	@Redirect(method="initialize", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/ParrotEntity;setVariant(I)V"))
	private void swapInVariant(ParrotEntity instance, int variant) { this.setVariant(this.random.nextInt(ParrotVariant.values().length)); }
	@Inject(method="getVariant", at=@At("HEAD"), cancellable=true)
	public void GetModVariant(CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(this.dataTracker.get(VARIANT)); }

	@Inject(method="imitateNearbyMob", at=@At("HEAD"), cancellable=true)
	private static void TryImitations(World world, Entity parrot, CallbackInfoReturnable<Boolean> cir) {
		MobEntity mobEntity;
		if (parrot.isAlive() && !parrot.isSilent() && world.random.nextInt(2) == 0) {
			List<MobEntity> list = world.getEntitiesByClass(MobEntity.class, parrot.getBoundingBox().expand(20.0), CAN_IMITATE_NEW);
			if (!list.isEmpty() && !(mobEntity = list.get(world.random.nextInt(list.size()))).isSilent()) {
				SoundEvent soundEvent = getSound(mobEntity.getType());
				world.playSound(null, parrot.getX(), parrot.getY(), parrot.getZ(), soundEvent, parrot.getSoundCategory(), 0.7f, ParrotEntity.getSoundPitch(world.random));
				cir.setReturnValue(true);
			}
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		ParrotVariant variant = ParrotVariant.get((ParrotEntity)(Object)this);
		if (variant == ParrotVariant.GOLDEN) this.dropItem(Items.GOLD_NUGGET);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.PARROT_BLOOD_TYPE; }
}
