package fun.mousewich.entity.vehicle;

import fun.mousewich.ModBase;
import fun.mousewich.registry.ModBambooRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;

public class ModChestBoatEntity extends ChestBoatEntity {
	private static final TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(ModChestBoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public ModChestBoatEntity(EntityType<? extends BoatEntity> type, World world) { super(type, world); }
	public ModChestBoatEntity(World worldIn, double x, double y, double z) {
		this(ModBase.MOD_CHEST_BOAT_ENTITY, worldIn);
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
	public World getWorld() { return world;}
	public void setModBoatType(ModBoatType type) { this.dataTracker.set(BOAT_TYPE, type.ordinal()); }
	public ModBoatType getModBoatType() { return ModBoatType.getType(this.dataTracker.get(BOAT_TYPE)); }
	@Override
	public double getMountedHeightOffset() { return this.getModBoatType() == ModBambooRegistry.BAMBOO_RAFT.getType() ? 0.3 : -0.1; }

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
	public Item asItem() { return getModBoatType().GetChestBoatItem(); }

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER) && !this.isSubmergedIn(FluidTags.LAVA);
	}
}
