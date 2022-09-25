package haven.blocks;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.HavenMod;
import haven.materials.providers.BucketProvider;
import haven.util.BucketUtils;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;
import java.util.function.Consumer;

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
	protected static final VoxelShape OUTLINE_SHAPE;
	static {
		OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);
	}

	public static CauldronBehavior FillFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
				-> BucketUtils.fillCauldron(world, pos, player, hand, stack,
				HavenMod.MILK_CAULDRON.getDefaultState(),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	public static CauldronBehavior FillCheeseFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack,
				HavenMod.COTTAGE_CHEESE_CAULDRON.getDefaultState(),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return Items.CAULDRON.getDefaultStack(); }

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextFloat() < 0.025F) {
			Block block = state.getBlock();
			if (block instanceof MilkCauldronBlock cauldron) {
				if (cauldron.curdling < 1) world.setBlockState(pos, HavenMod.COTTAGE_CHEESE_CAULDRON.getDefaultState());
				else world.setBlockState(pos, HavenMod.CHEESE_CAULDRON.getDefaultState());
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();
		if (curdling < 2) {
			ItemStack newStack = null;
			if (item == Items.BOWL) newStack = new ItemStack(curdling < 1 ? HavenMod.MILK_BOWL : HavenMod.COTTAGE_CHEESE_BOWL);
			else {
				BucketProvider provider = BucketProvider.getProvider(item);
				if (provider != null) newStack = new ItemStack(curdling < 1 ? provider.getMilkBucket() : provider.getCottageCheeseBucket());
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
				stack.damage(1, (LivingEntity)player, (Consumer)((p) -> {
					((LivingEntity)p).sendToolBreakStatus(hand);
				}));
				dropStack(world, pos, new ItemStack(HavenMod.CHEESE, player.getRandom().nextInt(3) + 1));
				world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}

	public boolean hasRandomTicks(BlockState state) { return curdling < 2; }
}
