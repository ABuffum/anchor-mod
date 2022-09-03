package haven.entities;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.HavenMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.Random;
import java.util.function.Consumer;

public class CrimsonMooshroomEntity extends CowEntity implements Shearable {
	public CrimsonMooshroomEntity(EntityType<? extends CrimsonMooshroomEntity> entityType, World world) {
		super(entityType, world);
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(Blocks.CRIMSON_NYLIUM) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	public static boolean canSpawn(EntityType<CrimsonMooshroomEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getBlockState(pos.down()).isOf(Blocks.CRIMSON_NYLIUM) && world.getBaseLightLevel(pos, 0) > 8;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BOWL) && !this.isBaby()) {
			boolean bl = false;
			ItemStack itemStack3 = new ItemStack(ItemsRegistry.NETHER_SALAD.get());
			ItemStack itemStack4 = ItemUsage.exchangeStack(itemStack, player, itemStack3, false);
			player.setStackInHand(hand, itemStack4);
			SoundEvent soundEvent2;
			if (bl) {
				soundEvent2 = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
			} else {
				soundEvent2 = SoundEvents.ENTITY_MOOSHROOM_MILK;
			}
			this.playSound(soundEvent2, 1.0F, 1.0F);
			return ActionResult.success(this.world.isClient);
		}
		else if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.world.isClient) {
				itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> {
					((LivingEntity)playerx).sendToolBreakStatus(hand);
				}));
			}
			return ActionResult.success(this.world.isClient);
		}
		else {
			return super.interactMob(player, hand);
		}
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		if (!this.world.isClient()) {
			((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
			this.discard();
			CowEntity cowEntity = (CowEntity)EntityType.COW.create(this.world);
			cowEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
			cowEntity.setHealth(this.getHealth());
			cowEntity.bodyYaw = this.bodyYaw;
			if (this.hasCustomName()) {
				cowEntity.setCustomName(this.getCustomName());
				cowEntity.setCustomNameVisible(this.isCustomNameVisible());
			}

			if (this.isPersistent()) {
				cowEntity.setPersistent();
			}

			cowEntity.setInvulnerable(this.isInvulnerable());
			this.world.spawnEntity(cowEntity);

			for(int i = 0; i < 5; ++i) {
				this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(Items.CRIMSON_FUNGUS)));
			}
		}

	}

	public boolean isShearable() {
		return this.isAlive() && !this.isBaby();
	}

	public CrimsonMooshroomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		CrimsonMooshroomEntity crimsonMooshroomEntity = (CrimsonMooshroomEntity)HavenMod.CRIMSON_MOOSHROOM_ENTITY.create(serverWorld);
		return crimsonMooshroomEntity;
	}
}