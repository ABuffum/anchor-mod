package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.util.WaterBottleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LightableLanternBlock extends LanternBlock {
	public LightableLanternBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(HANGING, false).with(WATERLOGGED, false).with(Properties.LIT, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(HANGING, WATERLOGGED, Properties.LIT); }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		boolean flint;
		if ((flint = itemStack.isOf(Items.FLINT_AND_STEEL)) || itemStack.isOf(ModBase.LAVA_BOTTLE)) {
			if (!state.get(Properties.LIT)) {
				SoundEvent sound = flint ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.ITEM_FIRECHARGE_USE;
				world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, true));
				world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
				if (flint) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				else {
					itemStack.decrement(1);
					player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
				}
				return ActionResult.SUCCESS;
			}
		}
		else if (itemStack.isOf(Items.POTION) && PotionUtil.getPotion(itemStack) == Potions.WATER) {
			if (state.get(Properties.LIT)) {
				WaterBottleUtil.useOnLightable(state, world, pos, player, itemStack, SoundEvents.ENTITY_GENERIC_SPLASH);
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
