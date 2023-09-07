package fun.mousewich.entity.vehicle;

import fun.mousewich.ModBase;
import fun.mousewich.mixins.entity.vehicle.BoatEntityAccessor;
import fun.mousewich.registry.ModBambooRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ModBoatEntity extends BoatEntity {
	private static final TrackedData<Integer> BOAT_TYPE;
	public ModBoatEntity(EntityType<? extends BoatEntity> type, World world) {
		super(type, world);
	}
	public ModBoatEntity(World worldIn, double x, double y, double z) {
		this(ModBase.MOD_BOAT_ENTITY, worldIn);
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
		nbt.putString("ModBoatType", this.getModBoatType().getName());
	}
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("ModBoatType", 8)) {
			this.setModBoatType(ModBoatType.getType(nbt.getString("ModBoatType")));
		}
	}
	@Override
	public double getMountedHeightOffset() { return this.getModBoatType() == ModBambooRegistry.BAMBOO_RAFT.getType() ? 0.3 : super.getMountedHeightOffset(); }
	@Override
	public void updatePassengerPosition(Entity passenger) {
		BoatEntityAccessor bea = (BoatEntityAccessor)this;
		if (!this.hasPassenger(passenger)) return;
		float f = 0.0f;
		float g = (float)((this.isRemoved() ? (double)0.01f : this.getMountedHeightOffset()) + passenger.getHeightOffset());
		if (this.getPassengerList().size() > 1) {
			int i = this.getPassengerList().indexOf(passenger);
			f = i == 0 ? 0.2f : -0.6f;
			if (passenger instanceof AnimalEntity) f += 0.2f;
		}
		Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateY(-this.getYaw() * ((float)Math.PI / 180) - 1.5707964f);
		passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
		passenger.setYaw(passenger.getYaw() + bea.getYawVelocity());
		passenger.setHeadYaw(passenger.getHeadYaw() + bea.getYawVelocity());
		this.copyEntityData(passenger);
		if (passenger instanceof AnimalEntity && this.getPassengerList().size() == 2) {
			int j = passenger.getId() % 2 == 0 ? 90 : 270;
			passenger.setBodyYaw(((AnimalEntity)passenger).bodyYaw + (float)j);
			passenger.setHeadYaw(passenger.getHeadYaw() + (float)j);
		}
	}
	public void setModBoatType(ModBoatType type) { this.dataTracker.set(BOAT_TYPE, type.ordinal()); }
	public ModBoatType getModBoatType() { return ModBoatType.getType(this.dataTracker.get(BOAT_TYPE)); }
	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		((BoatEntityAccessor)this).setFallVelocity(this.getVelocity().y);
		if (!this.hasVehicle()) {
			if (onGround) {
				if (this.fallDistance > 3.0F) {
					if (((BoatEntityAccessor)this).getLocation() != Location.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}
					this.handleFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
					if (!this.world.isClient && !this.isRemoved()) {
						this.kill();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							for(int j = 0; j < 3; ++j) this.dropItem(this.getModBoatType().getBaseBlock());
							for(int j = 0; j < 2; ++j) this.dropItem(Items.STICK);
						}
					}
				}
				this.fallDistance = 0.0F;
			}
			else {
				FluidState state = this.world.getFluidState(this.getBlockPos().down());
				if (!state.isIn(FluidTags.WATER) && heightDifference < 0.0D) {
					if (!state.isIn(FluidTags.LAVA)) {
						this.fallDistance = (float)((double)this.fallDistance - heightDifference);
					}
				}
			}
		}
	}

	public boolean floatsOnLava() { return getModBoatType().floatsOnLava; }

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (floatsOnLava()) {
			//TODO: Nonflammable boats should float on lava but until that's working, they should be destroyed
			//if (damageSource.isFire() || damageSource == DamageSource.LAVA) return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean doesRenderOnFire() { return !floatsOnLava() && super.doesRenderOnFire(); }

	@Override
	public Item asItem() { return getModBoatType().GetItem(); }

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < 2 && !this.isSubmergedIn(FluidTags.WATER) && !this.isSubmergedIn(FluidTags.LAVA);
	}

	static {
		BOAT_TYPE = DataTracker.registerData(ModBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
}
