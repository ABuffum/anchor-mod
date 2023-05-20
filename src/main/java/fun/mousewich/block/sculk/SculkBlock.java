package fun.mousewich.block.sculk;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class SculkBlock extends Block implements SculkSpreadable {
	public SculkBlock(Settings settings) { super(settings); }
	@Override
	public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
		int i = cursor.getCharge();
		if (i == 0 || random.nextInt(spreadManager.getSpreadChance()) != 0) {
			return i;
		}
		BlockPos blockPos = cursor.getPos();
		boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
		if (bl || !SculkBlock.shouldNotDecay(world, blockPos)) {
			if (random.nextInt(spreadManager.getDecayChance()) != 0) return i;
			return i - (bl ? 1 : SculkBlock.getDecay(spreadManager, blockPos, catalystPos, i));
		}
		int j = spreadManager.getExtraBlockChance();
		if (random.nextInt(j) < i) {
			BlockPos blockPos2 = blockPos.up();
			BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());
			world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
			world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
		}
		return Math.max(0, i - j);
	}
	private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
		int i = spreadManager.getMaxDistance();
		float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
		int j = MathHelper.square(24 - i);
		float g = Math.min(1.0f, f / (float)j);
		return Math.max(1, (int)((float)charge * g * 0.5f));
	}
	private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, Random random, boolean allowShrieker) {
		BlockState blockState = random.nextInt(11) == 0 ? ModBase.SCULK_SHRIEKER.asBlock().getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker) : Blocks.SCULK_SENSOR.getDefaultState();
		if (blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty()) {
			return blockState.with(Properties.WATERLOGGED, true);
		}
		return blockState;
	}
	private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.up());
		if (!(blockState.isAir() || blockState.isOf(Blocks.WATER) && blockState.getFluidState().getFluid() == Fluids.WATER)) return false;
		int i = 0;
		for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4))) {
			BlockState blockState2 = world.getBlockState(blockPos);
			if (blockState2.isOf(ModBase.SCULK_SHRIEKER.asBlock()) || blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SENSOR)) ++i;
			if (i <= 2) continue;
			return false;
		}
		return true;
	}
	@Override
	public boolean shouldConvertToSpreadable() { return false; }
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.onStacksDropped(state, world, pos, stack);
		if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) this.dropExperience(world, pos, 1);
	}
}
