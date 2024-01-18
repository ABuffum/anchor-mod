package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.LastJavelinStoring;
import fun.mousewich.item.JavelinItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class JavelinEntity extends PersistentProjectileEntity {
	private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(JavelinEntity.class, TrackedDataHandlerRegistry.BYTE);
	private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(JavelinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private ItemStack stack = new ItemStack(ModBase.JAVELIN);
	private boolean dealtDamage;
	public int returnTimer;
	protected JavelinItem item = ModBase.JAVELIN;
	public Identifier getTexture() { return item.getTexture(); }

	public JavelinEntity(EntityType<? extends JavelinEntity> entityType, World world) { super(entityType, world); }

	public JavelinEntity(World world, LivingEntity owner, ItemStack stack, JavelinItem item) {
		this(ModBase.JAVELIN_ENTITY, world, owner, stack, item);
	}
	protected JavelinEntity(EntityType<? extends JavelinEntity> entityType, World world, LivingEntity owner, ItemStack stack, JavelinItem item) {
		super(entityType, owner, world);
		this.stack = stack.copy();
		this.item = item;
		this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(stack));
		this.dataTracker.set(ENCHANTED, stack.hasGlint());
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(LOYALTY, (byte)0);
		this.dataTracker.startTracking(ENCHANTED, false);
	}

	@Override
	public void tick() {
		if (this.inGroundTime > 4) this.dealtDamage = true;
		Entity entity = this.getOwner();
		byte i = this.dataTracker.get(LOYALTY);
		if (i > 0 && (this.dealtDamage || this.isNoClip()) && entity != null) {
			if (!this.isOwnerAlive()) {
				if (!this.world.isClient && this.pickupType == PickupPermission.ALLOWED) {
					this.dropStack(this.asItemStack(), 0.1f);
				}
				this.discard();
			}
			else {
				this.setNoClip(true);
				Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
				this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double)i, this.getZ());
				if (this.world.isClient) this.lastRenderY = this.getY();
				double d = 0.05 * (double)i;
				this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
				if (this.returnTimer == 0) this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0f, 1.0f);
				++this.returnTimer;
			}
		}
		super.tick();
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity == null || !entity.isAlive()) return false;
		return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
	}

	@Override
	public ItemStack asItemStack() { return this.stack.copy(); }

	public boolean isEnchanted() { return this.dataTracker.get(ENCHANTED); }

	@Override
	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		if (this.dealtDamage) return null;
		return super.getEntityCollision(currentPosition, nextPosition);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		BlockPos blockPos;
		Entity entity2;
		Entity entity = entityHitResult.getEntity();
		float f = 8.0f;
		if (entity instanceof LivingEntity livingEntity) {
			f += EnchantmentHelper.getAttackDamage(this.stack, livingEntity.getGroup());
		}
		DamageSource damageSource = DamageSource.trident(this, (entity2 = this.getOwner()) == null ? this : entity2);
		this.dealtDamage = true;
		SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) return;
			if (entity instanceof LivingEntity livingEntity2) {
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity2);
				}
				this.onHit(livingEntity2);
			}
		}
		this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
		float g = 1.0f;
		if (this.world instanceof ServerWorld && this.world.isThundering() && this.hasChanneling() && this.world.isSkyVisible(blockPos = entity.getBlockPos())) {
			LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.world);
			lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
			lightningEntity.setChanneler(entity2 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity2 : null);
			this.world.spawnEntity(lightningEntity);
			soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER;
			g = 5.0f;
		}
		this.playSound(soundEvent, g, 1.0f);
	}

	public boolean hasChanneling() { return EnchantmentHelper.hasChanneling(this.stack); }

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		ItemStack stack = this.asItemStack();
		boolean decrementWhenThrown = !(stack.getItem() instanceof JavelinItem javelin) || javelin.decrementWhenThrown();
		return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && decrementWhenThrown && player.getInventory().insertStack(stack);
	}

	@Override
	protected SoundEvent getHitSound() { return SoundEvents.ITEM_TRIDENT_HIT_GROUND; }

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if (this.isOwner(player) || this.getOwner() == null) super.onPlayerCollision(player);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Javelin", 10)) {
			this.stack = ItemStack.fromNbt(nbt.getCompound("Javelin"));
			this.item = this.stack.getItem() instanceof JavelinItem javelin ? javelin : ModBase.JAVELIN;
		}
		this.dealtDamage = nbt.getBoolean("DealtDamage");
		this.dataTracker.set(LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.stack));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put("Javelin", this.stack.writeNbt(new NbtCompound()));
		nbt.putBoolean("DealtDamage", this.dealtDamage);
	}

	@Override
	public void age() {
		byte i = this.dataTracker.get(LOYALTY);
		if (this.pickupType != PickupPermission.ALLOWED || i <= 0) super.age();
	}

	@Override
	protected float getDragInWater() { return 0.99f; }

	@Override
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) { return true; }

	@Override
	public void remove(Entity.RemovalReason reason) {
		if (this.getOwner() instanceof LastJavelinStoring lastJavelinStore) {
			if (lastJavelinStore.getLastJavelin() == this) lastJavelinStore.setLastJavelin(null);
		}
		super.remove(reason);
	}

	public boolean shouldStoreLast() { return false; }
}