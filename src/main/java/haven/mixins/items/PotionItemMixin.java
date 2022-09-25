package haven.mixins.items;

import haven.HavenMod;
import haven.HavenTags;
import haven.containers.TorchContainer;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {
	public PotionItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack itemStack = context.getStack();
		BlockState blockState = world.getBlockState(blockPos);
		if (PotionUtil.getPotion(itemStack) == Potions.WATER) {
			Block block = blockState.getBlock();
			BlockState outState = blockState;
			boolean bl = true, consume = false;
			SoundEvent sound = SoundEvents.ENTITY_GENERIC_SPLASH;
			if (TorchContainer.UNLIT_TORCHES.containsKey(block)) {
				outState = TorchContainer.UNLIT_TORCHES.get(block).getDefaultState();
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
			}
			else if (TorchContainer.UNLIT_WALL_TORCHES.containsKey(block)) {
				outState = TorchContainer.UNLIT_WALL_TORCHES.get(block).getDefaultState().with(WallTorchBlock.FACING, blockState.get(WallTorchBlock.FACING));
				sound = SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT;
				if (block instanceof Waterloggable) outState = outState.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
			}
			else if (HavenMod.UNLIT_LANTERNS.containsKey(block)) {
				outState = HavenMod.UNLIT_LANTERNS.get(block).getDefaultState().with(LanternBlock.HANGING, blockState.get(LanternBlock.HANGING)).with(LanternBlock.WATERLOGGED, blockState.get(LanternBlock.WATERLOGGED));
			}
			else if (block instanceof AbstractCandleBlock && blockState.get(AbstractCandleBlock.LIT)) {
				AbstractCandleBlock.extinguish(playerEntity, blockState, world, blockPos);
				return ActionResult.success(world.isClient);
			}
			else if (block instanceof CampfireBlock && blockState.get(CampfireBlock.LIT)) {
				CampfireBlock.extinguish(playerEntity, world, blockPos, blockState);
				outState = blockState.with(CampfireBlock.LIT, false);
				consume = true;
			}
			else if (context.getSide() != Direction.DOWN && blockState.isIn(HavenTags.Blocks.CONVERTIBLE_TO_MUD)) {
				outState = HavenMod.MUD.BLOCK.getDefaultState();
				consume = true;
			}
			else bl = false;
			if (bl) {
				if (sound != null) {
					world.playSound(null, blockPos, sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
				}
				if (consume) {
					playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
				}
				playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
				if (!world.isClient) {
					ServerWorld serverWorld = (ServerWorld)world;
					for (int i = 0; i < 5; ++i) {
						serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + world.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
					}
				}
				if (consume) {
					world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
				}
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
				world.setBlockState(blockPos, outState);
				return ActionResult.success(world.isClient);
			}
		}
		return ActionResult.PASS;
	}
}
