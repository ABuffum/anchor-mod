package fun.wich.entity.vehicle;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.RideableInventory;
import fun.wich.mixins.entity.vehicle.BoatEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ChestBoatEntity extends BoatEntity implements RideableInventory, VehicleInventory {
	private static final int INVENTORY_SIZE = 27;
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
	private Identifier lootTableId;
	private long lootTableSeed;

	public ChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) { super(entityType, world); }

	public ChestBoatEntity(World world, double d, double e, double f) {
		this(ModEntityType.CHEST_BOAT_ENTITY, world);
		this.setPosition(d, e, f);
		this.prevX = d;
		this.prevY = e;
		this.prevZ = f;
	}
	@Override
	public World getWorld() { return world;}
	@Override
	public void updatePassengerPosition(Entity passenger) {
		BoatEntityAccessor bea = (BoatEntityAccessor)this;
		if (!this.hasPassenger(passenger)) return;
		float f = this.getPassengerHorizontalOffset();
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
		if (passenger instanceof AnimalEntity && this.getPassengerList().size() == this.getMaxPassengers()) {
			int j = passenger.getId() % 2 == 0 ? 90 : 270;
			passenger.setBodyYaw(((AnimalEntity)passenger).bodyYaw + (float)j);
			passenger.setHeadYaw(passenger.getHeadYaw() + (float)j);
		}
	}
	@Override
	protected boolean canAddPassenger(Entity passenger) { return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER); }
	protected float getPassengerHorizontalOffset() { return 0.15f; }
	protected int getMaxPassengers() { return 1; }

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		this.writeInventoryToNbt(nbt);
	}
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.readInventoryFromNbt(nbt);
	}
	public void dropItems(DamageSource source) {
		this.dropItem(this.asItem());
		Entity entity;
		if (!world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) return;
		ItemScatterer.spawn(this.world, this, this);
		if (!world.isClient && (entity = source.getSource()) != null && entity.getType() == EntityType.PLAYER) {
			PiglinBrain.onGuardedBlockInteracted((PlayerEntity)entity, true);
		}
	}
	@Override
	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		if (this.world.isClient || this.isRemoved()) return true;
		this.setDamageWobbleSide(-this.getDamageWobbleSide());
		this.setDamageWobbleTicks(10);
		this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
		this.scheduleVelocityUpdate();
		this.emitGameEvent(GameEvent.ENTITY_DAMAGED, source.getAttacker());
		boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).getAbilities().creativeMode;
		if (bl || this.getDamageWobbleStrength() > 40.0f) {
			if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) this.dropItems(source);
			this.discard();
		}
		return true;
	}
	@Override
	public void remove(RemovalReason reason) {
		if (!this.world.isClient && reason.shouldDestroy()) ItemScatterer.spawn(this.world, this, this);
		super.remove(reason);
	}
	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (!this.canAddPassenger(player) || player.shouldCancelInteraction()) return this.open(this::emitGameEvent, player);
		return super.interact(player, hand);
	}
	@Override
	public void openInventory(PlayerEntity player) {
		player.openHandledScreen(this);
		if (!player.world.isClient) {
			this.emitGameEvent(GameEvent.CONTAINER_OPEN, player);
			PiglinBrain.onGuardedBlockInteracted(player, true);
		}
	}
	@Override
	public Item asItem() {
		return switch (this.getBoatType()) {
			case SPRUCE -> ModBase.SPRUCE_CHEST_BOAT;
			case BIRCH -> ModBase.BIRCH_CHEST_BOAT;
			case JUNGLE -> ModBase.JUNGLE_CHEST_BOAT;
			case ACACIA -> ModBase.ACACIA_CHEST_BOAT;
			case DARK_OAK -> ModBase.DARK_OAK_CHEST_BOAT;
			default -> ModBase.OAK_CHEST_BOAT;
		};
	}
	@Override
	public void clear() {
		this.generateInventoryLoot(null);
		this.getInventory().clear();
	}
	@Override
	public int size() { return 27; }
	@Override
	public ItemStack getStack(int slot) { return this.getInventoryStack(slot); }
	@Override
	public ItemStack removeStack(int slot, int amount) { return this.removeInventoryStack(slot, amount); }
	@Override
	public ItemStack removeStack(int slot) { return this.removeInventoryStack(slot); }
	@Override
	public void setStack(int slot, ItemStack stack) { this.setInventoryStack(slot, stack); }
	@Override
	public StackReference getStackReference(int mappedIndex) { return this.getInventoryStackReference(mappedIndex); }
	@Override
	public void markDirty() { }
	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return !this.isRemoved() && this.getPos().isInRange(player.getPos(), 8.0);
	}
	@Override
	public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		if (this.lootTableId == null || !playerEntity.isSpectator()) {
			this.generateLoot(playerInventory.player);
			return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, this);
		}
		return null;
	}
	public void generateLoot(PlayerEntity player) { this.generateInventoryLoot(player); }
	@Override
	public Identifier getLootTableId() { return this.lootTableId; }
	@Override
	public void setLootTableId(Identifier lootTableId) { this.lootTableId = lootTableId; }
	@Override
	public long getLootTableSeed() { return this.lootTableSeed; }
	@Override
	public void setLootTableSeed(long lootTableSeed) { this.lootTableSeed = lootTableSeed; }
	@Override
	public DefaultedList<ItemStack> getInventory() { return this.inventory; }
	@Override
	public void resetInventory() { this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY); }
}
