package fun.wich.entity.projectile;

import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AmethystTridentEntity extends JavelinEntity {
	public AmethystTridentEntity(EntityType<? extends JavelinEntity> entityType, World world) {
		super(entityType, world);
		this.item = ModBase.AMETHYST_TRIDENT;
	}
	public AmethystTridentEntity(World world, LivingEntity owner, ItemStack stack) {
		super(ModEntityType.AMETHYST_TRIDENT_ENTITY, world, owner, stack, ModBase.AMETHYST_TRIDENT);
	}
}
