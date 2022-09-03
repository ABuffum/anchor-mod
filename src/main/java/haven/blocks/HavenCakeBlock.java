package haven.blocks;

import haven.HavenMod;
import haven.util.Flavor;

import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class HavenCakeBlock extends CakeBlock {
	public static final AbstractBlock.Settings SETTINGS = AbstractBlock.Settings.copy(Blocks.CAKE);
	private final Flavor flavor;
	public Flavor getFlavor() { return flavor; }
	public HavenCakeBlock(Flavor flavor) {
		this(flavor, SETTINGS);
	}
	public HavenCakeBlock(Flavor flavor, Settings settings) {
		super(settings);
		this.flavor = flavor;
	}

	@Override
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
//					if (flavor == 1) output = HavenMod.CHOCOLATE_CANDLE_CAKE_BLOCK;
//					else if (flavor == 2) output = HavenMod.STRAWBERRY_CANDLE_CAKE_BLOCK;
//					else if (flavor == 3) output = HavenMod.COFFEE_CANDLE_CAKE_BLOCK;
//					else
						output = Blocks.CANDLE_CAKE;
				}
				else {
					DyeColor color = GetCandleColor((CandleBlock)block);
//					if (flavor == 1) output = HavenMod.CHOCOLATE_CANDLE_CAKE_BLOCKS.get(color);
//					else if (flavor == 2) output = HavenMod.STRAWBERRY_CANDLE_CAKE_BLOCKS.get(color);
//					else if (flavor == 2) output = HavenMod.COFFEE_CANDLE_CAKE_BLOCKS.get(color);
//					else
						output = CandleCakeBlock.getCandleCakeFromCandle(block).getBlock();
				}
				world.setBlockState(pos, output.getDefaultState());
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				return ActionResult.SUCCESS;
			}
		}

		if (world.isClient) {
			if (tryEat(world, pos, state, player).isAccepted()) {
				return ActionResult.SUCCESS;
			}

			if (itemStack.isEmpty()) {
				return ActionResult.CONSUME;
			}
		}

		return tryEat(world, pos, state, player, flavor);
	}

	private static DyeColor GetCandleColor(CandleBlock block) {
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

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, Flavor flavor) {
		if (!player.canConsume(false)) {
			return ActionResult.PASS;
		} else {
			player.incrementStat(Stats.EAT_CAKE_SLICE);
			player.getHungerManager().add(2, 0.1F);
			int i = (Integer)state.get(BITES);
			world.emitGameEvent(player, GameEvent.EAT, (BlockPos)pos);
			if (flavor == Flavor.COFFEE) { //Coffee Cake
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
}
