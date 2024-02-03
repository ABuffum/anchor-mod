package fun.wich.haven.entity;

import fun.wich.entity.projectile.JavelinEntity;
import fun.wich.haven.HavenMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class VectortechJavelinEntity extends JavelinEntity {
	public VectortechJavelinEntity(EntityType<? extends JavelinEntity> entityType, World world) {
		super(entityType, world);
		this.item = HavenMod.VECTORTECH_JAVELIN;
	}

	public VectortechJavelinEntity(World world, LivingEntity owner, ItemStack stack) {
		super(HavenMod.VECTORTECH_JAVELIN_ENTITY, world, owner, stack, HavenMod.VECTORTECH_JAVELIN);
	}

	@Override
	public boolean shouldStoreLast() { return true; }
}
