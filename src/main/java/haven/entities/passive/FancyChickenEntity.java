package haven.entities.passive;

import haven.HavenMod;
import haven.entities.passive.cow.CowfeeEntity;
import haven.entities.passive.cow.StrawbovineEntity;
import haven.mixins.entities.ChickenEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class FancyChickenEntity extends ChickenEntity {
	public FancyChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, ChickenEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, FancyChickenEntity.class));
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		this.eggLayTime = 6000;
	}

	public FancyChickenEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return HavenMod.FANCY_CHICKEN_ENTITY.create(serverWorld);
	}
}
