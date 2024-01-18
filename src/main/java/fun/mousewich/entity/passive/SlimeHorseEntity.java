package fun.mousewich.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.effect.ModStatusEffects;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.mixins.entity.passive.HorseBaseEntityInvoker;
import fun.mousewich.sound.IdentifiedSounds;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SlimeHorseEntity extends HorseBaseEntity implements EntityWithBloodType {

	public SlimeHorseEntity(EntityType<? extends SlimeHorseEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createSlimeHorseAttributes() {
		return SlimeHorseEntity.createBaseHorseAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
	}

	@Override
	protected void initAttributes() {
		this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(this.getChildHealthBonus());
		this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus());
		this.getAttributeInstance(EntityAttributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus());
	}

	@Override
	protected void initCustomGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.LEATHER, Items.ROTTEN_FLESH), false));
	}
	@Override
	public boolean isBreedingItem(ItemStack stack) { return stack.isOf(Items.LEATHER) || stack.isOf(Items.ROTTEN_FLESH); }

	@Override
	protected boolean receiveFood(PlayerEntity player, ItemStack item) {
		boolean bl = false;
		float f = 0.0f;
		int i = 0;
		int j = 0;
		if (item.isOf(Items.LEATHER)) {
			f = 2.0f;
			i = 20;
			j = 3;
		} else if (item.isOf(Items.ROTTEN_FLESH)) {
			f = 1.0f;
			i = 30;
			j = 3;
		} else if (item.isOf(Items.LEATHER_HELMET)) {
			f = 20.0f;
			i = 180;
		} else if (item.isOf(Items.LEATHER_BOOTS)) {
			f = 3.0f;
			i = 60;
			j = 3;
		} else if (item.isOf(Items.LEATHER_LEGGINGS)) {
			f = 4.0f;
			i = 60;
			j = 5;
			if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
				bl = true;
				this.lovePlayer(player);
			}
		} else if (item.isOf(Items.LEATHER_CHESTPLATE)) {
			f = 10.0f;
			i = 240;
			j = 10;
			if (!this.world.isClient && this.isTame() && this.getBreedingAge() == 0 && !this.isInLove()) {
				bl = true;
				this.lovePlayer(player);
			}
		}
		if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
			this.heal(f);
			bl = true;
		}
		if (this.isBaby() && i > 0) {
			this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
			if (!this.world.isClient) {
				this.growUp(i);
			}
			bl = true;
		}
		if (j > 0 && (bl || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
			bl = true;
			if (!this.world.isClient) {
				this.addTemper(j);
			}
		}
		if (bl) {
			((HorseBaseEntityInvoker)this).InvokePlayEatingAnimation();
			this.emitGameEvent(GameEvent.EAT, this.getCameraBlockPos());
		}
		return bl;
	}

	@Override
	protected SoundEvent getEatSound() { return SoundEvents.ENTITY_HORSE_EAT; }

	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.ENTITY_HORSE_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.ENTITY_HORSE_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		super.getHurtSound(source);
		return SoundEvents.ENTITY_HORSE_HURT;
	}
	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.ENTITY_HORSE_ANGRY;
	}

	@Override
	protected void playWalkSound(BlockSoundGroup group) {
		super.playWalkSound(group);
		if (this.random.nextInt(10) == 0) {
			this.playSound(SoundEvents.ENTITY_HORSE_BREATHE, group.getVolume() * 0.6f, group.getPitch());
		}
	}

	@Override
	public void onInventoryChanged(Inventory sender) {
		boolean bl = this.isSaddled();
		this.updateSaddle();
		if (this.age > 20 && !bl && this.isSaddled()) this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5f, 1.0f);
	}

	@Override
	@Nullable
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return ModBase.SLIME_HORSE_ENTITY.create(world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.isBaby()) {
			if (this.isTame() && player.shouldCancelInteraction()) {
				this.openInventory(player);
				return ActionResult.success(this.world.isClient);
			}
			if (this.hasPassengers()) {
				return super.interactMob(player, hand);
			}
		}
		if (!itemStack.isEmpty()) {
			if (this.isBreedingItem(itemStack)) return this.interactHorse(player, itemStack);
			ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
			if (actionResult.isAccepted()) return actionResult;
			if (!this.isTame()) {
				this.playAngrySound();
				return ActionResult.success(this.world.isClient);
			}
			if (itemStack.isOf(Items.SADDLE) && !this.isSaddled()) {
				this.openInventory(player);
				return ActionResult.success(this.world.isClient);
			}
		}
		if (this.isBaby()) return super.interactMob(player, hand);
		this.putPlayerOnBack(player);
		return ActionResult.success(this.world.isClient);
	}
	@Override
	public void tickMovement() {
		super.tickMovement();
		if (this.hasPassengers()) {
			for (Entity entity : this.getPassengersDeep()) {
				if (entity instanceof LivingEntity living) {
					living.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 200));
				}
			}
		}
	}

	@Override
	public BloodType GetDefaultBloodType() { return ModBase.SLIME_BLOOD_TYPE; }
}
