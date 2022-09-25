package haven.items.syringe;

import haven.HavenMod;
import haven.blood.BloodType;
import haven.entities.cloud.ConfettiCloudEntity;
import haven.entities.cloud.DragonBreathCloudEntity;
import haven.mixins.blocks.BeehiveBlockInvoker;
import haven.mixins.blocks.TurtleEggBlockInvoker;
import haven.damage.HavenDamageSource;
import haven.sounds.HavenSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class EmptySyringeItem extends BaseSyringeItem {
	public EmptySyringeItem(Settings settings) { super(settings); }

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		if (user == entity) entity.damage(HavenDamageSource.Injected("blood_taken", null), 1);
		else entity.damage(HavenDamageSource.Injected("blood_taken", user), 1);
	}

	@Override
	public Item GetReplacementSyringe(PlayerEntity user, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType != BloodType.NONE) return BloodType.GetSyringe(bloodType);
		else return super.GetReplacementSyringe(user, entity);
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		//Fill with dragon's breath (effect cloud)
		List<AreaEffectCloudEntity> dblist = world.getEntitiesByClass(AreaEffectCloudEntity.class, user.getBoundingBox().expand(2.0D), (entity) -> {
			return entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragonEntity;
		});
		if (!dblist.isEmpty()) {
			AreaEffectCloudEntity areaEffectCloudEntity = (AreaEffectCloudEntity)dblist.get(0);
			areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5F);
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
			ReplaceSyringe(user, hand, HavenMod.DRAGON_BREATH_SYRINGE);
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		//Fill with dragon's breath (captain's death)
		List<DragonBreathCloudEntity> dlist = world.getEntitiesByClass(DragonBreathCloudEntity.class, user.getBoundingBox().expand(4.0D), (entity) -> {
			return entity != null && entity.isAlive();
		});
		if (!dlist.isEmpty()) {
			for(DragonBreathCloudEntity cloud : dlist) {
				cloud.kill();
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				ReplaceSyringe(user, hand, HavenMod.DRAGON_BREATH_SYRINGE);
				return TypedActionResult.consume(user.getStackInHand(hand));
			}
		}
		//Fill with confetti
		List<ConfettiCloudEntity> clist = world.getEntitiesByClass(ConfettiCloudEntity.class, user.getBoundingBox().expand(4.0D), (entity) -> {
			return entity != null && entity.isAlive();
		});
		if (!clist.isEmpty()) {
			for(ConfettiCloudEntity cloud : clist) {
				cloud.kill();
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				ReplaceSyringe(user, hand, HavenMod.CONFETTI_SYRINGE);
				return TypedActionResult.consume(user.getStackInHand(hand));
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		PlayerEntity player = context.getPlayer();
		if (block == Blocks.SLIME_BLOCK) {
			ReplaceSyringe(player, context.getHand(), HavenMod.SLIME_SYRINGE);
			return ActionResult.CONSUME;
		}
		else if (block == Blocks.HONEY_BLOCK) {
			ReplaceSyringe(player, context.getHand(), HavenMod.HONEY_SYRINGE);
			return ActionResult.CONSUME;
		}
		else if (block instanceof BeehiveBlock beehive) {
			int i = state.get(BeehiveBlock.HONEY_LEVEL);
			if (i >= 5) {
				ReplaceSyringe(player, context.getHand(), HavenMod.HONEY_SYRINGE);
				BeehiveBlockInvoker bbi = (BeehiveBlockInvoker)beehive;
				if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
					if (bbi.InvokeHasBees(world, pos)) {
						bbi.InvokeAngerNearbyBees(world, pos);
					}
					beehive.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
				}
				return ActionResult.CONSUME;
			}
		}
		else if (HavenMod.BLOOD_BLOCKS.contains(block) || HavenMod.BLOOD_MATERIAL.contains(block) || HavenMod.DRIED_BLOOD_MATERIAL.contains(block)) {
			ReplaceSyringe(player, context.getHand(), HavenMod.BLOOD_SYRINGE);
			return ActionResult.CONSUME;
		}
		else if (block == Blocks.SNOW || block == Blocks.SNOW_BLOCK || block == Blocks.POWDER_SNOW || block == Blocks.WATER
				|| block == Blocks.ICE || block == Blocks.BLUE_ICE || block == Blocks.FROSTED_ICE || block == Blocks.PACKED_ICE) {
			ReplaceSyringe(player, context.getHand(), HavenMod.WATER_SYRINGE);
			return ActionResult.CONSUME;
		}
		else if (block == Blocks.MAGMA_BLOCK) {
			ReplaceSyringe(player, context.getHand(), HavenMod.MAGMA_CREAM_SYRINGE);
			return ActionResult.CONSUME;
		}
		else if (block instanceof TurtleEggBlock turtleEgg) {
			((TurtleEggBlockInvoker)turtleEgg).InvokeTryBreakEgg(world, state, pos, player, 100);
			ReplaceSyringe(player, context.getHand(), HavenMod.TURTLE_BLOOD_SYRINGE);
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}
}
