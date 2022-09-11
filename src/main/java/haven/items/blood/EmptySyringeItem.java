package haven.items.blood;

import haven.HavenMod;
import haven.mixins.blocks.BeehiveBlockInvoker;
import haven.mixins.blocks.TurtleEggBlockInvoker;
import haven.util.BloodType;
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
		BloodType type = null;
		if (block == Blocks.SLIME_BLOCK) type = BloodType.SLIME;
		else if (block == Blocks.HONEY_BLOCK) type = BloodType.HONEY;
		else if (block instanceof BeehiveBlock beehive) {
			int i = state.get(BeehiveBlock.HONEY_LEVEL);
			if (i >= 5) {
				type = BloodType.HONEY;
				BeehiveBlockInvoker bbi = (BeehiveBlockInvoker)beehive;
				if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
					if (bbi.InvokeHasBees(world, pos)) {
						bbi.InvokeAngerNearbyBees(world, pos);
					}
					beehive.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
				}
			}
		}
		else if (HavenMod.BLOOD_BLOCKS.contains(block)) type = BloodType.PLAYER;
		else if (block == Blocks.SNOW || block == Blocks.SNOW_BLOCK || block == Blocks.POWDER_SNOW || block == Blocks.WATER) type = BloodType.WATER;
		else if (block == Blocks.MAGMA_BLOCK) type = BloodType.MAGMA;
		else if (block instanceof TurtleEggBlock turtleEgg) {
			type = BloodType.TURTLE;
			((TurtleEggBlockInvoker)turtleEgg).InvokeTryBreakEgg(world, state, pos, player, 100);
		}
		if (type != null) {
			ReplaceSyringe(player, context.getHand(), BloodType.GetSyringe(type));
			return ActionResult.CONSUME;
		}
		else return ActionResult.PASS;
	}
}
