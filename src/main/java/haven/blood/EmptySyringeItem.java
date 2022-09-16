package haven.blood;

import haven.HavenMod;
import haven.mixins.blocks.BeehiveBlockInvoker;
import haven.mixins.blocks.TurtleEggBlockInvoker;
import haven.damage.HavenDamageSource;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EmptySyringeItem extends BaseSyringeItem {
	public EmptySyringeItem(Settings settings) { super(settings); }

	@Override
	public void ApplyEffect(PlayerEntity user, LivingEntity entity) {
		entity.damage(HavenDamageSource.SYRINGE_BLOOD_LOSS,1);
	}

	@Override
	public Item GetReplacementSyringe(PlayerEntity user, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType != BloodType.NONE) return BloodType.GetSyringe(bloodType);
		else return super.GetReplacementSyringe(user, entity);
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
