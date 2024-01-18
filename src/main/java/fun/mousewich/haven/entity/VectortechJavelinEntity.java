package fun.mousewich.haven.entity;

import fun.mousewich.entity.projectile.JavelinEntity;
import fun.mousewich.haven.HavenMod;
import fun.mousewich.item.ConditionalLoreItem;
import fun.mousewich.origins.power.PowersUtil;
import io.github.apace100.origins.origin.Origin;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

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
