package fun.mousewich.block.torch;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class LightableWallTorchBlock extends WallTorchBlock {
	public LightableWallTorchBlock(Settings settings, ParticleEffect particleEffect) {
		super(settings, particleEffect);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.LIT, true));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(Properties.LIT); }

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING))).with(Properties.LIT, state.get(Properties.LIT));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING, Properties.LIT); }

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(Properties.LIT)) super.randomDisplayTick(state, world, pos, random);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		boolean flint, lava = false;
		if ((flint = itemStack.isOf(Items.FLINT_AND_STEEL)) || (lava = itemStack.isOf(ModBase.LAVA_BOTTLE)) || itemStack.isOf(Items.FIRE_CHARGE)) {
			if (!state.get(Properties.LIT)) {
				SoundEvent sound = flint ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.ITEM_FIRECHARGE_USE;
				world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, true));
				world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
				if (flint) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				else {
					itemStack.decrement(1);
					if (lava) player.giveItemStack(new ItemStack(ModBase.LAVA_BOTTLE.getRecipeRemainder()));
				}
				return ActionResult.SUCCESS;
			}
		}
		else if (itemStack.isOf(Items.POTION)) {
			if (state.get(Properties.LIT)) {
				world.playSound(player, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
				world.setBlockState(pos, state.with(Properties.LIT, false));
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				if (!world.isClient) {
					ServerWorld serverWorld = (ServerWorld)world;
					for (int i = 0; i < 5; ++i) {
						serverWorld.spawnParticles(ParticleTypes.SPLASH, pos.getX() + world.random.nextDouble(), pos.getY() + 1, pos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
					}
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
