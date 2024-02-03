package fun.wich.block;

import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GrapeVinesBodyBlock extends AbstractPlantBlock implements Fertilizable, ModVines {
	public GrapeVinesBodyBlock(Settings settings) {
		super(settings, Direction.DOWN, SHAPE, false);
		this.setDefaultState((this.stateManager.getDefaultState()).with(Properties.BERRIES, false));
	}

	@Override
	protected AbstractPlantStemBlock getStem() { return (AbstractPlantStemBlock)ModBase.GRAPE_VINES; }
	@Override
	protected boolean canAttachTo(BlockState state) { return state.isIn(ModBlockTags.GRAPE_GROWABLE); }
	@Override
	protected BlockState copyState(BlockState from, BlockState to) { return to.with(Properties.BERRIES, from.get(Properties.BERRIES)); }
	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return new ItemStack(ModBase.GRAPES); }
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return ModVines.pick(player, state, world, pos, new ItemStack(ModBase.GRAPES));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.BERRIES); }
	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return !state.get(Properties.BERRIES); }
	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) { world.setBlockState(pos, state.with(Properties.BERRIES, true), Block.NOTIFY_LISTENERS); }
}