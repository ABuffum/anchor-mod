package fun.mousewich.item.syringe;

import fun.mousewich.ModBase;
import fun.mousewich.block.fluid.BloodFluid;
import fun.mousewich.block.fluid.MudFluid;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.cloud.ConfettiCloudEntity;
import fun.mousewich.entity.cloud.DragonBreathCloudEntity;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.mixins.block.BeehiveBlockInvoker;
import fun.mousewich.mixins.block.TurtleEggBlockInvoker;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
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
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		if (user == entity) entity.damage(ModDamageSource.Injected("blood_taken", null), 1);
		else entity.damage(ModDamageSource.Injected("blood_taken", user), 1);
	}
	@Override
	public ItemStack GetReplacementSyringe(PlayerEntity user, LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType != BloodType.NONE) {
			if (BloodType.BLOOD_TYPE_TO_SYRINGE.containsKey(bloodType)) return new ItemStack(BloodType.BLOOD_TYPE_TO_SYRINGE.get(bloodType));
			else return BloodSyringeItem.getForBloodType(bloodType);
		}
		else return super.GetReplacementSyringe(user, entity);
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		//Fill with dragon's breath (effect cloud)
		List<AreaEffectCloudEntity> dblist = world.getEntitiesByClass(AreaEffectCloudEntity.class, user.getBoundingBox().expand(2.0D), e -> e != null && e.isAlive() && e.getOwner() instanceof EnderDragonEntity);
		if (!dblist.isEmpty()) {
			AreaEffectCloudEntity areaEffectCloudEntity = dblist.get(0);
			areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5F);
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
			ReplaceSyringe(user, hand, new ItemStack(ModBase.DRAGON_BREATH_SYRINGE));
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		//Fill with dragon's breath (captain's death)
		List<DragonBreathCloudEntity> dlist = world.getEntitiesByClass(DragonBreathCloudEntity.class, user.getBoundingBox().expand(4.0D), e -> e != null && e.isAlive());
		if (!dlist.isEmpty()) {
			for(DragonBreathCloudEntity cloud : dlist) {
				cloud.kill();
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				ReplaceSyringe(user, hand, new ItemStack(ModBase.DRAGON_BREATH_SYRINGE));
				return TypedActionResult.consume(user.getStackInHand(hand));
			}
		}
		//Fill with confetti
		List<ConfettiCloudEntity> clist = world.getEntitiesByClass(ConfettiCloudEntity.class, user.getBoundingBox().expand(4.0D), e -> e != null && e.isAlive());
		if (!clist.isEmpty()) {
			for(ConfettiCloudEntity cloud : clist) {
				cloud.kill();
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				ReplaceSyringe(user, hand, new ItemStack(ModBase.CONFETTI_SYRINGE));
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
		FluidState fluidState = world.getFluidState(pos);
		Fluid fluid = fluidState.getFluid();
		BloodType bloodType = BloodType.Get(state);
		if (bloodType != BloodType.NONE) {
			ReplaceSyringe(player, context.getHand(), bloodType);
			return ActionResult.CONSUME;
		}
		else if (block instanceof BeehiveBlock beehive) {
			int i = state.get(BeehiveBlock.HONEY_LEVEL);
			if (i >= 5) {
				ReplaceSyringe(player, context.getHand(), BloodType.HONEY);
				if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
					BeehiveBlockInvoker bbi = (BeehiveBlockInvoker)beehive;
					if (bbi.InvokeHasBees(world, pos)) {
						bbi.InvokeAngerNearbyBees(world, pos);
					}
					beehive.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
				}
				return ActionResult.CONSUME;
			}
		}
		else if (state.isIn(ModBlockTags.OVERWORLD_NATURAL_LOGS)) {
			ReplaceSyringe(player, context.getHand(), BloodType.SAP);
			return ActionResult.CONSUME;
		}
		else if (fluid instanceof BloodFluid) {
			ReplaceSyringe(player, context.getHand(), BloodType.PLAYER);
			return ActionResult.CONSUME;
		}
		else if (fluid instanceof MudFluid) {
			ReplaceSyringe(player, context.getHand(), BloodType.MUD);
			return ActionResult.CONSUME;
		}
		else if (block instanceof TurtleEggBlock turtleEgg) {
			((TurtleEggBlockInvoker)turtleEgg).InvokeTryBreakEgg(world, state, pos, player, 100);
			ReplaceSyringe(player, context.getHand(), ModBase.TURTLE_BLOOD_TYPE);
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}
}
