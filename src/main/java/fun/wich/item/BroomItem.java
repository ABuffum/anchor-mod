package fun.wich.item;

import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.item.tool.ExtraKnockbackItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BroomItem extends Item implements ExtraKnockbackItem {
	private final double knockback;

	public BroomItem(double knockback, Settings settings) {
		super(settings);
		this.knockback = knockback;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (knockback > 0) target.takeKnockback(knockback, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
		return true;
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		Direction dir = context.getPlayerFacing();
		World world = context.getWorld();
		BlockState state = world.getBlockState(pos);
		if (state.isIn(ModBlockTags.BROOM_SWEEPS)) {
			BlockPos newPos = pos.offset(dir);
			boolean waterloggable = state.contains(Properties.WATERLOGGED);
			if (world.isAir(newPos)) {
				world.setBlockState(newPos, state);
				if (waterloggable && state.get(Properties.WATERLOGGED)) world.setBlockState(pos, Blocks.WATER.getDefaultState());
				else world.setBlockState(pos, Blocks.AIR.getDefaultState());
				return ActionResult.SUCCESS;
			}
			else if (world.isWater(newPos)) {
				if (waterloggable) state = state.with(Properties.WATERLOGGED, true);
				world.setBlockState(newPos, state);
				if (waterloggable && state.get(Properties.WATERLOGGED)) world.setBlockState(pos, Blocks.WATER.getDefaultState());
				else world.setBlockState(pos, Blocks.AIR.getDefaultState());
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (knockback > 0) {
			entity.takeKnockback(knockback, user.getX() - entity.getX(), user.getZ() - entity.getZ());
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public int getExtraKnockback() { return (int)(1 + knockback); }
}
