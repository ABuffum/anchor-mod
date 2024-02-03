package fun.wich.entity.hostile.zombie;

import fun.wich.ModBase;
import fun.wich.effect.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SlimeZombieEntity extends ZombieEntity {
	public SlimeZombieEntity(EntityType<? extends SlimeZombieEntity> entityType, World world) {
		super(entityType, world);
	}
	public SlimeZombieEntity(World world) { super(ModBase.SLIME_ZOMBIE_ENTITY, world); }

	@Override
	protected void initCustomGoals() {
		this.goalSelector.add(2, new ZombieAttackGoal(this, 1.0, false));
		this.goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ZombifiedPiglinEntity.class));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.add(5, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
	}

	@Override
	public void applyDamageEffects(LivingEntity attacker, Entity target) {
		super.applyDamageEffects(attacker, target);
		if (target instanceof LivingEntity livingEntity) {
			livingEntity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600), attacker);
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200), attacker);
		}
	}

	@Override
	protected ItemStack getSkull() { return ItemStack.EMPTY; }
}
