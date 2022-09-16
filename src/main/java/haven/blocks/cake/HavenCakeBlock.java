package haven.blocks.cake;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class HavenCakeBlock extends Block {
	public static final AbstractBlock.Settings SETTINGS = AbstractBlock.Settings.copy(Blocks.CAKE);

	public static final int MAX_BITES = 6;
	public static final IntProperty BITES;
	public static final int DEFAULT_COMPARATOR_OUTPUT;
	protected static final VoxelShape[] BITES_TO_SHAPE;
	private final CakeFlavor flavor;
	public CakeFlavor getFlavor() { return flavor; }
	public HavenCakeBlock(CakeFlavor flavor) {
		this(flavor, SETTINGS);
	}
	public HavenCakeBlock(CakeFlavor flavor, Settings settings) {
		super(settings);
		this.flavor = flavor;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BITES_TO_SHAPE[(Integer)state.get(BITES)];
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isIn(ItemTags.CANDLES) && (Integer)state.get(BITES) == 0) {
			Block block = Block.getBlockFromItem(item);
			if (block instanceof CandleBlock) {
				if (!player.isCreative()) {
					itemStack.decrement(1);
				}
				world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				Block output;
				if (block == Blocks.CANDLE) {
					if (flavor == CakeFlavor.CHOCOLATE) output = HavenMod.CHOCOLATE_CANDLE_CAKE;
					else if (flavor == CakeFlavor.STRAWBERRY) output = HavenMod.STRAWBERRY_CANDLE_CAKE;
					else if (flavor == CakeFlavor.COFFEE) output = HavenMod.COFFEE_CANDLE_CAKE;
					else if (flavor == CakeFlavor.CARROT) output = HavenMod.CARROT_CANDLE_CAKE;
					else if (flavor == CakeFlavor.CONFETTI) output = HavenMod.CONFETTI_CANDLE_CAKE;
					else output = Blocks.CANDLE_CAKE;
				}
				else {
					DyeColor color = GetCandleColor(block);
					if (flavor == CakeFlavor.CHOCOLATE) output = HavenMod.CHOCOLATE_CANDLE_CAKES.get(color);
					else if (flavor == CakeFlavor.STRAWBERRY) output = HavenMod.STRAWBERRY_CANDLE_CAKES.get(color);
					else if (flavor == CakeFlavor.COFFEE) output = HavenMod.COFFEE_CANDLE_CAKES.get(color);
					else if (flavor == CakeFlavor.CARROT) output = HavenMod.CARROT_CANDLE_CAKES.get(color);
					else if (flavor == CakeFlavor.CONFETTI) output = HavenMod.CONFETTI_CANDLE_CAKES.get(color);
					else output = CandleCakeBlock.getCandleCakeFromCandle(block).getBlock();
				}
				world.setBlockState(pos, output.getDefaultState());
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				return ActionResult.SUCCESS;
			}
		}

		if (world.isClient) {
			if (tryEat(world, pos, state, player, flavor).isAccepted()) {
				return ActionResult.SUCCESS;
			}

			if (itemStack.isEmpty()) {
				return ActionResult.CONSUME;
			}
		}

		return tryEat(world, pos, state, player, flavor);
	}

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, CakeFlavor flavor) {
		if (!player.canConsume(false)) {
			return ActionResult.PASS;
		} else {
			player.incrementStat(Stats.EAT_CAKE_SLICE);
			player.getHungerManager().add(2, 0.1F);
			int i = (Integer)state.get(BITES);
			world.emitGameEvent(player, GameEvent.EAT, (BlockPos)pos);
			if (flavor == CakeFlavor.COFFEE) { //Coffee Cake
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 0));
			}
			if (i < 6) {
				world.setBlockState(pos, (BlockState)state.with(BITES, i + 1), Block.NOTIFY_ALL);
			} else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, (BlockPos)pos);
			}

			return ActionResult.SUCCESS;
		}
	}

	private static DyeColor GetCandleColor(Block block) {
		if (block == Blocks.BLACK_CANDLE) return DyeColor.BLACK;
		else if (block == Blocks.BLUE_CANDLE) return DyeColor.BLUE;
		else if (block == Blocks.BROWN_CANDLE) return DyeColor.BROWN;
		else if (block == Blocks.CYAN_CANDLE) return DyeColor.CYAN;
		else if (block == Blocks.GRAY_CANDLE) return DyeColor.GRAY;
		else if (block == Blocks.GREEN_CANDLE) return DyeColor.GREEN;
		else if (block == Blocks.LIGHT_BLUE_CANDLE) return DyeColor.LIGHT_BLUE;
		else if (block == Blocks.LIGHT_GRAY_CANDLE) return DyeColor.LIGHT_GRAY;
		else if (block == Blocks.LIME_CANDLE) return DyeColor.LIME;
		else if (block == Blocks.MAGENTA_CANDLE) return DyeColor.MAGENTA;
		else if (block == Blocks.ORANGE_CANDLE) return DyeColor.ORANGE;
		else if (block == Blocks.PINK_CANDLE) return DyeColor.PINK;
		else if (block == Blocks.PURPLE_CANDLE) return DyeColor.PURPLE;
		else if (block == Blocks.RED_CANDLE) return DyeColor.RED;
		else if (block == Blocks.WHITE_CANDLE) return DyeColor.WHITE;
		else if (block == Blocks.YELLOW_CANDLE) return DyeColor.YELLOW;
		else return DyeColor.WHITE;
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).getMaterial().isSolid();
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BITES);
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return getComparatorOutput((Integer)state.get(BITES));
	}

	public static int getComparatorOutput(int bites) {
		return (7 - bites) * 2;
	}

	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	static {
		BITES = Properties.BITES;
		DEFAULT_COMPARATOR_OUTPUT = getComparatorOutput(0);
		BITES_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.createCuboidShape(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};
	}
}
