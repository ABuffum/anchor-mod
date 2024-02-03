package fun.wich.entity.projectile;

import fun.wich.ModBase;
import fun.wich.ModId;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BlindingArrowEntity extends ModArrowEntity {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/projectiles/blinding_arrow.png");
	private int duration = 200;

	public BlindingArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public BlindingArrowEntity(World world, LivingEntity owner) { super(ModBase.BLINDING_ARROW.getEntityType(), world, owner); }
	public BlindingArrowEntity(World world, double x, double y, double z) { super(ModBase.BLINDING_ARROW.getEntityType(), world, x, y, z); }
	@Override
	public ItemStack asItemStack() { return new ItemStack(ModBase.BLINDING_ARROW); }
	@Override
	public ItemStack getRecycledStack() { return new ItemStack(ModBase.BLINDING_ARROW); }
	@Override
	public Identifier getTexture() { return TEXTURE; }

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.BLINDNESS, this.duration, 0);
		target.addStatusEffect(statusEffectInstance, this.getEffectCause());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Duration")) this.duration = nbt.getInt("Duration");
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Duration", this.duration);
	}
}
