package fun.mousewich.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class ModArrowEntity extends PersistentProjectileEntity implements WeakeningEnchantmentCarrier {
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world, LivingEntity owner) { super(entityType, owner, world); }
	public ModArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world, double x, double y, double z) { super(entityType, x, y, z, world); }
	@Override
	public void tick() { super.tick(); }
	@Override
	protected void onHit(LivingEntity target) { super.onHit(target); }
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) { super.readCustomDataFromNbt(nbt); }
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) { super.writeCustomDataToNbt(nbt); }
	public abstract ItemStack asItemStack();
	public abstract ItemStack getRecycledStack();
	public abstract Identifier getTexture();

	private int weakeningLevel = 0;
	@Override public int getWeakeningLevel() { return this.weakeningLevel; }
	@Override public void setWeakeningLevel(int level) { this.weakeningLevel = level; }
}
