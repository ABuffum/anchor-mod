package haven.blocks.anchors;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SubstituteAnchorBlock extends BlockWithEntity {
	public SubstituteAnchorBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(AnchorBlock.OWNER, 0));
	}

	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(Blocks.RESPAWN_ANCHOR);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AnchorBlock.OWNER); }

	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new SubstituteAnchorBlockEntity(pos, state); }

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		int owner = state.get(AnchorBlock.OWNER);
		double x = pos.getX() + 0.5, y = pos.getY(), z = pos.getZ() + 0.5;
		ItemStack anchorStack = new ItemStack(Blocks.RESPAWN_ANCHOR, 1);
		ItemEntity anchorStackEntity = new ItemEntity(player.world, x, y, z, anchorStack);
		player.world.spawnEntity(anchorStackEntity);
		if (HavenMod.ANCHOR_MAP.containsKey(owner)) {
			ItemStack otherStack = new ItemStack(HavenMod.ANCHOR_CORES.get(owner), 1);
			ItemEntity itemEntity = new ItemEntity(player.world, x, y, z, otherStack);
			player.world.spawnEntity(itemEntity);
		}
	}

	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(AnchorBlock.OWNER) != 0) {
			if (random.nextInt(5) == 0) {
				Direction direction = Direction.random(random);
				if (direction != Direction.UP) {
					BlockPos blockPos = pos.offset(direction);
					BlockState blockState = world.getBlockState(blockPos);
					if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
						double d = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetX() * 0.6D;
						double e = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetY() * 0.6D;
						double f = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5D + (double)direction.getOffsetZ() * 0.6D;
						world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + f, 0.0D, 0.0D, 0.0D);
					}
				}
			}
			if (random.nextInt(100) == 0) {
				world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			double d = (double)pos.getX() + 0.5D + (0.5D - random.nextDouble());
			double e = (double)pos.getY() + 1.0D;
			double f = (double)pos.getZ() + 0.5D + (0.5D - random.nextDouble());
			double g = (double)random.nextFloat() * 0.04D;
			world.addParticle(ParticleTypes.REVERSE_PORTAL, d, e, f, 0.0D, g, 0.0D);
		}
	}

	public static int getLightLevel(BlockState state, int maxLevel) {
		return MathHelper.floor(0.5F * (float)maxLevel);
	}
}
