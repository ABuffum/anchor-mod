package fun.wich.entity.passive.cow;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class BlueMooshroomEntity extends CowEntity implements Shearable, EntityWithBloodType {

	public BlueMooshroomEntity(EntityType<? extends BlueMooshroomEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, MooshroomEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, BlueMooshroomEntity.class));
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(Blocks.MYCELIUM) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	public static boolean canSpawn(EntityType<BlueMooshroomEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getBlockState(pos.down()).isOf(Blocks.MYCELIUM) && world.getBaseLightLevel(pos, 0) > 8;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BOWL) && !this.isBaby()) {
			player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, new ItemStack(Items.MUSHROOM_STEM), false));
			this.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
			return ActionResult.success(this.world.isClient);
		}
		else if (itemStack.getItem() instanceof ShearsItem && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.world.isClient) itemStack.damage(1, (LivingEntity)player, (p -> p.sendToolBreakStatus(hand)));
			return ActionResult.success(this.world.isClient);
		}
		else return super.interactMob(player, hand);
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		if (!this.world.isClient()) {
			((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
			this.discard();
			CowEntity cowEntity = EntityType.COW.create(this.world);
			cowEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
			cowEntity.setHealth(this.getHealth());
			cowEntity.bodyYaw = this.bodyYaw;
			if (this.hasCustomName()) {
				cowEntity.setCustomName(this.getCustomName());
				cowEntity.setCustomNameVisible(this.isCustomNameVisible());
			}
			if (this.isPersistent()) cowEntity.setPersistent();
			cowEntity.setInvulnerable(this.isInvulnerable());
			this.world.spawnEntity(cowEntity);
			for (int i = 0; i < 5; ++i) {
				this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(ModBase.BLUE_MUSHROOM.asItem())));
			}
		}
	}
	public boolean isShearable() { return this.isAlive() && !this.isBaby(); }
	public BlueMooshroomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModBase.BLUE_MOOSHROOM_ENTITY.create(serverWorld);
	}
	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass() && otherClass != MooshroomEntity.class) return false;
		else return this.isInLove() && other.isInLove();
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.MOOSHROOM_BLOOD_TYPE; }
}
