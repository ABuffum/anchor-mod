package haven.anchors;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SubstituteAnchorBlock extends Block {
	public SubstituteAnchorBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(AnchorBlock.OWNER, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(AnchorBlock.OWNER); }

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		int owner = state.get(AnchorBlock.OWNER);
		double x = pos.getX(), y = pos.getY(), z = pos.getZ();
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
			if (random.nextInt(100) == 0) {
				world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			double d = (double)pos.getX() + 0.5 + (0.5 - random.nextDouble());
			double e = (double)pos.getY() + 1.0;
			double f = (double)pos.getZ() + 0.5 + (0.5 - random.nextDouble());
			double g = (double)random.nextFloat() * 0.04;
			world.addParticle(ParticleTypes.REVERSE_PORTAL, d, e, f, 0.0, g, 0.0);
		}
	}

	public static int getLightLevel(BlockState state, int maxLevel) {
		return MathHelper.floor(0.5F * (float)maxLevel);
	}
}
