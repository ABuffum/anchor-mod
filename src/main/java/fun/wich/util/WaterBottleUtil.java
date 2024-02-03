package fun.wich.util;

import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WaterBottleUtil {
	public static void useOnLightable(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack itemStack, SoundEvent extinguishSound) {
		if (extinguishSound != null) {
			world.playSound(player, pos, extinguishSound, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
		}
		world.setBlockState(pos, state.with(Properties.LIT, false));
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		//Alter inventory
		if (!player.getAbilities().creativeMode) itemStack.decrement(1);
		player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
		player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
		//Play splash & trigger event
		makeSplash(world, pos);
	}

	public static void makeSplash(World world, BlockPos pos) {
		if (!world.isClient) {
			ServerWorld serverWorld = (ServerWorld)world;
			for (int i = 0; i < 5; ++i) {
				serverWorld.spawnParticles(ParticleTypes.SPLASH, pos.getX() + world.random.nextDouble(), pos.getY() + 1, pos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
			}
		}
	}

	public static ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		PlayerEntity player = context.getPlayer();
		ItemStack itemStack = context.getStack();
		BlockState state = world.getBlockState(pos);
		if (PotionUtil.getPotion(itemStack) == Potions.WATER) {
			Block block = state.getBlock();
			BlockState outState = state;
			boolean bl = true, first, playSplash = false;
			SoundEvent sound = SoundEvents.ENTITY_GENERIC_SPLASH;
			if ((first = block == Blocks.TORCH) || block == Blocks.SOUL_TORCH) {
				outState = (first ? ModBase.UNLIT_TORCH : ModBase.UNLIT_SOUL_TORCH).asBlock().getDefaultState();
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, state.get(Properties.WATERLOGGED));
			}
			else if ((first = block == Blocks.WALL_TORCH) || block == Blocks.SOUL_WALL_TORCH) {
				outState = (first ? ModBase.UNLIT_TORCH : ModBase.UNLIT_SOUL_TORCH).getWallBlock().getDefaultState()
						.with(WallTorchBlock.FACING, state.get(WallTorchBlock.FACING));
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, state.get(Properties.WATERLOGGED));
			}
			else if ((first = block == Blocks.LANTERN) || block == Blocks.SOUL_LANTERN) {
				outState = (first ? ModBase.UNLIT_LANTERN : ModBase.UNLIT_SOUL_LANTERN).getDefaultState()
						.with(LanternBlock.HANGING, state.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, state.get(LanternBlock.WATERLOGGED));
			}
			else if (block instanceof AbstractCandleBlock && state.get(AbstractCandleBlock.LIT)) {
				AbstractCandleBlock.extinguish(player, state, world, pos);
				return ActionResult.success(world.isClient);
			}
			else if (block instanceof CampfireBlock && state.get(CampfireBlock.LIT)) {
				CampfireBlock.extinguish(player, world, pos, state);
				outState = state.with(CampfireBlock.LIT, false);
				playSplash = true;
			}
			else if (context.getSide() != Direction.DOWN && state.isIn(ModBlockTags.CONVERTIBLE_TO_MUD)) {
				outState = ModBase.MUD.asBlock().getDefaultState();
				playSplash = true;
			}
			else bl = false;
			if (bl) {
				if (sound != null) world.playSound(null, pos, sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
				if (player != null) {
					if (!player.getAbilities().creativeMode) context.getStack().decrement(1);
					player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
					player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
				}
				makeSplash(world, pos);
				if (playSplash) {
					world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				}
				world.setBlockState(pos, outState);
				world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				return ActionResult.success(world.isClient);
			}
		}
		return ActionResult.PASS;
	}
}
