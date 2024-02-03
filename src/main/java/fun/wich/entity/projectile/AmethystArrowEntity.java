package fun.wich.entity.projectile;

import fun.wich.ModBase;
import fun.wich.ModId;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AmethystArrowEntity extends ModArrowEntity {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/projectiles/amethyst_arrow.png");
	public AmethystArrowEntity(EntityType<? extends ModArrowEntity> entityType, World world) { super(entityType, world); }
	public AmethystArrowEntity(World world, LivingEntity owner) { super(ModBase.AMETHYST_ARROW.getEntityType(), world, owner); }
	public AmethystArrowEntity(World world, double x, double y, double z) { super(ModBase.AMETHYST_ARROW.getEntityType(), world, x, y, z); }

	@Override
	public ItemStack asItemStack() { return new ItemStack(ModBase.AMETHYST_ARROW); }
	@Override
	public ItemStack getRecycledStack() { return new ItemStack(ModBase.AMETHYST_ARROW); }
	@Override
	public Identifier getTexture() { return TEXTURE; }
}
