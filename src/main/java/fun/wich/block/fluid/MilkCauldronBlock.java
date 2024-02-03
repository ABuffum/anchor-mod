package fun.wich.block.fluid;

import fun.wich.ModBase;
import fun.wich.item.bucket.BucketProvider;
import fun.wich.util.BucketUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class MilkCauldronBlock extends Block {
	private final int curdling;
	public MilkCauldronBlock(int curdling, Settings settings) {
		super(settings);
		this.curdling = curdling;
	}
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return curdling < 2 ? OUTLINE_SHAPE : super.getOutlineShape(state, world, pos, context);
	}
	private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),
			VoxelShapes.union(
					createCuboidShape(0, 0, 4, 16, 3, 12),
					createCuboidShape(4, 0, 0, 12, 3, 16),
					createCuboidShape(2, 0, 2, 14, 3, 14), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);

	public static CauldronBehavior FillFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
				-> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				ModBase.MILK_CAULDRON.getDefaultState(),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	public static CauldronBehavior FillCheeseFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
			-> BucketUtil.fillCauldron(world, pos, player, hand, stack,
				ModBase.COTTAGE_CHEESE_CAULDRON.getDefaultState(),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return Items.CAULDRON.getDefaultStack(); }

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextFloat() < 0.025F) {
			Block block = state.getBlock();
			if (block instanceof MilkCauldronBlock cauldron) {
				if (cauldron.curdling < 1) world.setBlockState(pos, ModBase.COTTAGE_CHEESE_CAULDRON.getDefaultState());
				else world.setBlockState(pos, ModBase.CHEESE_CAULDRON.getDefaultState());
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();
		if (curdling < 2) {
			ItemStack newStack = null;
			if (item == Items.BOWL) newStack = new ItemStack(curdling < 1 ? ModBase.MILK_BOWL : ModBase.COTTAGE_CHEESE_BOWL);
			else {
				BucketProvider provider = BucketProvider.getProvider(item);
				if (provider != null && curdling < 1) newStack = new ItemStack(provider.getMilkBucket());
			}
			if (newStack != null) {
				if (!player.getAbilities().creativeMode) {
					player.getStackInHand(hand).decrement(1);
					if (player.getStackInHand(hand).isEmpty()) player.setStackInHand(hand, newStack);
					else player.getInventory().insertStack(newStack);
				}
				else player.getInventory().insertStack(newStack);
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
				return ActionResult.SUCCESS;
			}
		}
		else {
			if (item instanceof ShovelItem) {
				stack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				dropStack(world, pos, new ItemStack(ModBase.CHEESE, player.getRandom().nextInt(3) + 1));
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	public boolean hasRandomTicks(BlockState state) { return curdling < 2; }
}
