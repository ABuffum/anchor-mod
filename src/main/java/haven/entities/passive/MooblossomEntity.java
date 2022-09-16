package haven.entities.passive;

import haven.HavenMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import java.util.function.Consumer;

public class MooblossomEntity extends CowEntity implements Shearable {
	public MooblossomEntity(EntityType<? extends MooblossomEntity> entityType, World world) {
		super(entityType, world);
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	public static boolean canSpawn(EntityType<MooblossomEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) && world.getBaseLightLevel(pos, 0) > 8;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof ShearsItem && this.isShearable()) {
			this.sheared(SoundCategory.PLAYERS);
			this.emitGameEvent(GameEvent.SHEAR, player);
			if (!this.world.isClient) {
				itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> {
					((LivingEntity)playerx).sendToolBreakStatus(hand);
				}));
			}

			return ActionResult.success(this.world.isClient);
		} else {
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
				this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(HavenMod.MAGENTA_TULIP.ITEM)));
			}
		}

	}

	public boolean isShearable() {
		return this.isAlive() && !this.isBaby();
	}

	public MooblossomEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		MooblossomEntity mooblossomEntity = (MooblossomEntity)HavenMod.MOOBLOSSOM_ENTITY.create(serverWorld);
		return mooblossomEntity;
	}
}