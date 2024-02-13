package fun.wich.entity.passive.cow;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.variants.NetherMooshroomVariant;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class NetherMooshroomEntity extends CowEntity implements Shearable, EntityWithBloodType {

	public NetherMooshroomEntity(EntityType<? extends NetherMooshroomEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, NetherMooshroomEntity.class));
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isIn(BlockTags.NYLIUM) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	public static boolean canSpawn(EntityType<NetherMooshroomEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getBlockState(pos.down()).isIn(BlockTags.NYLIUM) && world.getBaseLightLevel(pos, 0) > 8;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BOWL) && !this.isBaby()) {
			player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, new ItemStack(ItemsRegistry.NETHER_SALAD.get()), false));
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
			for(int i = 0; i < 5; ++i) {
				NetherMooshroomVariant variant = getVariant();
				this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(variant.item)));
			}
		}
	}

	public boolean isShearable() { return this.isAlive() && !this.isBaby(); }

	private void setVariant(NetherMooshroomVariant type) { NetherMooshroomVariant.setVariant(this, type.ordinal()); }
	public NetherMooshroomVariant getVariant() { return NetherMooshroomVariant.get(this); }
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(NetherMooshroomVariant.VARIANT, 0);
	}
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", NetherMooshroomVariant.getVariant(this));
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Variant")) NetherMooshroomVariant.setVariant(this, nbt.getInt("Variant"));
	}

	public NetherMooshroomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		NetherMooshroomEntity child = ModEntityType.NETHER_MOOSHROOM_ENTITY.create(serverWorld);
		child.setVariant(this.random.nextBoolean() ? this.getVariant() : ((NetherMooshroomEntity)passiveEntity).getVariant());
		return child;
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.MOOSHROOM_BLOOD_TYPE; }
}