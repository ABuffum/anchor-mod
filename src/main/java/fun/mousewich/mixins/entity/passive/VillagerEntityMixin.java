package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer, EntityWithBloodType {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.VILLAGER_BLOOD_TYPE; }
}
