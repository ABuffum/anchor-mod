package fun.wich.entity.projectile;

import fun.wich.ModBase;
import fun.wich.ModId;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class BoneArrowEntity extends ModArrowEntity {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/projectiles/bone_arrow.png");
	public BoneArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) {
		super(entityType, world);
		this.setDamage(this.getDamage() * 1.25);
	}
	public BoneArrowEntity(World world, LivingEntity owner) {
		super(ModBase.BONE_ARROW.getEntityType(), world, owner);
		this.setDamage(this.getDamage() * 1.25);
	}
	public BoneArrowEntity(World world, double x, double y, double z) {
		super(ModBase.BONE_ARROW.getEntityType(), world, x, y, z);
		this.setDamage(this.getDamage() * 1.25);
	}

	@Override
	public ItemStack asItemStack() { return new ItemStack(ModBase.BONE_ARROW); }
	@Override
	public ItemStack getRecycledStack() { return new ItemStack(ModBase.BONE_ARROW); }
	@Override
	public Identifier getTexture() { return TEXTURE; }

	@Override
	public void onEntityHit(EntityHitResult entityHitResult) {
		//Bone Arrows do half the damage of a regular arrow vs skeletons
		Entity entity = entityHitResult.getEntity();
		EntityType<?> type = entity.getType();
		if (type.isIn(EntityTypeTags.SKELETONS) || type == EntityType.SKELETON_HORSE
				|| type == ModBase.BONE_SPIDER_ENTITY || ModBase.IS_SKELETON_POWER.isActive(entity)) {
			this.setDamage(this.getDamage() * 0.4);
		}
		super.onEntityHit(entityHitResult);
	}
}
