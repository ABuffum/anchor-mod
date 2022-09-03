package haven.boats;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import java.util.List;

import haven.HavenMod;
import haven.mixins.BoatEntityAccessor;
import net.minecraft.block.*;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.*;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BoatPaddleStateC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.*;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class HavenBoatEntity extends BoatEntity {
	private static final TrackedData<Integer> BOAT_TYPE;

	public HavenBoatEntity(EntityType<? extends BoatEntity> type, World world) {
		super(type, world);
	}

	public HavenBoatEntity(World worldIn, double x, double y, double z) {
		this(HavenMod.BOAT_ENTITY, worldIn);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BOAT_TYPE, 0);
	}
	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("HavenType", this.getHavenBoatType().getName());
	}
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("HavenType", 8)) {
			this.setHavenBoatType(HavenBoatType.getType(nbt.getString("HavenType")));
		}
	}

	public void setHavenBoatType(HavenBoatType type) {
		this.dataTracker.set(BOAT_TYPE, type.ordinal());
	}

	public HavenBoatType getHavenBoatType() {
		return HavenBoatType.getType((Integer)this.dataTracker.get(BOAT_TYPE));
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		((BoatEntityAccessor)this).setFallVelocity(this.getVelocity().y);
		if (!this.hasVehicle()) {
			if (onGround) {
				if (this.fallDistance > 3.0F) {
					if (((BoatEntityAccessor)this).getLocation() != BoatEntity.Location.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}

					this.handleFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.world.isClient && !this.isRemoved()) {
						this.kill();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							int j;
							for(j = 0; j < 3; ++j) {
								this.dropItem(this.getHavenBoatType().getBaseBlock());
							}

							for(j = 0; j < 2; ++j) {
								this.dropItem(Items.STICK);
							}
						}
					}
				}

				this.fallDistance = 0.0F;
			} else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0D) {
				this.fallDistance = (float)((double)this.fallDistance - heightDifference);
			}

		}
	}

	@Override
	public Item asItem() {
		return getHavenBoatType().GetItem();
	}

	static {
		BOAT_TYPE = DataTracker.registerData(HavenBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
