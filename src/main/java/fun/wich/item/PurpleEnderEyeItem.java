package fun.wich.item;

import fun.wich.ModBase;
import fun.wich.entity.PurpleEyeOfEnderEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.feature.StructureFeature;

public class PurpleEnderEyeItem extends Item {
	public PurpleEnderEyeItem(Settings settings) { super(settings); }

	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOf(Blocks.END_PORTAL_FRAME) && !(Boolean)blockState.get(EndPortalFrameBlock.EYE)) {
			if (world.isClient) return ActionResult.SUCCESS;
			else {
				BlockState blockState2 = ModBase.PURPLE_EYE_END_PORTAL_FRAME.getDefaultState()
						.with(EndPortalFrameBlock.FACING, blockState.get(EndPortalFrameBlock.FACING));
				world.playSound(null, blockPos, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Block.pushEntitiesUpBeforeBlockChange(blockState, blockState2, world, blockPos);
				world.setBlockState(blockPos, blockState2, Block.NOTIFY_LISTENERS);
				world.updateComparators(blockPos, ModBase.PURPLE_EYE_END_PORTAL_FRAME);
				context.getStack().decrement(1);
				return ActionResult.CONSUME;
			}
		}
		else return ActionResult.PASS;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockPos blockPos;
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult hitResult = PurpleEnderEyeItem.raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK && world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.END_PORTAL_FRAME)) {
			return TypedActionResult.pass(itemStack);
		}
		user.setCurrentHand(hand);
		BlockPos userPos = user.getBlockPos();
		if (world instanceof ServerWorld serverWorld && (blockPos = serverWorld.getChunkManager().getChunkGenerator().locateStructure((ServerWorld)world, StructureFeature.STRONGHOLD, user.getBlockPos(), 100, false)) != null) {
			PurpleEyeOfEnderEntity eyeOfEnderEntity = new PurpleEyeOfEnderEntity(world, user.getX(), user.getBodyY(0.5), user.getZ());
			eyeOfEnderEntity.setItem(itemStack);
			BlockPos oppositePos = new BlockPos(userPos.getX() - blockPos.getX(), userPos.getY() - blockPos.getY(), userPos.getZ() - blockPos.getZ());
			eyeOfEnderEntity.initTargetPos(oppositePos);
			world.spawnEntity(eyeOfEnderEntity);
			if (user instanceof ServerPlayerEntity serverPlayer) Criteria.USED_ENDER_EYE.trigger(serverPlayer, blockPos);
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
			world.syncWorldEvent(null, WorldEvents.EYE_OF_ENDER_LAUNCHES, user.getBlockPos(), 0);
			if (!user.getAbilities().creativeMode) itemStack.decrement(1);
			user.incrementStat(Stats.USED.getOrCreateStat(this));
			user.swingHand(hand, true);
			return TypedActionResult.success(itemStack);
		}
		return TypedActionResult.consume(itemStack);
	}
}
