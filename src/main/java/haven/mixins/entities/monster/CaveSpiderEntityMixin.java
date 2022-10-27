package haven.mixins.entities.monster;

import haven.entities.passive.HedgehogEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntity {
	public CaveSpiderEntityMixin(EntityType<? extends SpiderEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(5, new FleeEntityGoal<>(this, HedgehogEntity.class, 16.0F, 1.6D, 1.4D));
	}
}
