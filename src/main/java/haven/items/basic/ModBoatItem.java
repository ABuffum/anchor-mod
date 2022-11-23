package haven.items.basic;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import haven.entities.vehicle.ModBoatEntity;
import haven.entities.vehicle.ModBoatType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

public class ModBoatItem extends Item {
	private static final Predicate<Entity> RIDERS;
	private final ModBoatType type;

	public ModBoatItem(ModBoatType type, Item.Settings settings) {
		super(settings);
		this.type = type;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			Vec3d vec3d = user.getRotationVec(1.0F);
			double d = 5.0D;
			List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), RIDERS);
			if (!list.isEmpty()) {
				Vec3d vec3d2 = user.getEyePos();
				Iterator var11 = list.iterator();

				while(var11.hasNext()) {
					Entity entity = (Entity)var11.next();
					Box box = entity.getBoundingBox().expand((double)entity.getTargetingMargin());
					if (box.contains(vec3d2)) {
						return TypedActionResult.pass(itemStack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				ModBoatEntity boatEntity = new ModBoatEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
				boatEntity.setHavenBoatType(this.type);
				boatEntity.setYaw(user.getYaw());
				if (!world.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox().expand(-0.1D))) {
					return TypedActionResult.fail(itemStack);
				} else {
					if (!world.isClient) {
						world.spawnEntity(boatEntity);
						world.emitGameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getPos()));
						if (!user.getAbilities().creativeMode) {
							itemStack.decrement(1);
						}
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.success(itemStack, world.isClient());
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
	}

	static {
		RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
	}
}
