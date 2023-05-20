package fun.mousewich.block.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

public abstract class ModTntBlock extends Block {
	public ModTntBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(Properties.UNSTABLE, false));
	}
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock())) {
			if (world.isReceivingRedstonePower(pos)) {
				primeTnt(world, pos);
				world.removeBlock(pos, false);
			}

		}
	}
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (world.isReceivingRedstonePower(pos)) {
			primeTnt(world, pos);
			world.removeBlock(pos, false);
		}
	}
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient() && !player.isCreative() && state.get(Properties.UNSTABLE)) primeTnt(world, pos);
		super.onBreak(world, pos, state, player);
	}
	public abstract void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion);
	public void primeTnt(World world, BlockPos pos) { primeTnt(world, pos, null); }
	protected abstract void primeTnt(World world, BlockPos pos, LivingEntity igniter);
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!itemStack.isOf(Items.FLINT_AND_STEEL) && !itemStack.isOf(Items.FIRE_CHARGE)) return super.onUse(state, world, pos, player, hand, hit);
		else {
			primeTnt(world, pos, player);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
			Item item = itemStack.getItem();
			if (!player.isCreative()) {
				if (itemStack.isOf(Items.FLINT_AND_STEEL)) itemStack.damage(1, player, p -> p.sendToolBreakStatus(hand));
				else itemStack.decrement(1);
			}
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			return ActionResult.success(world.isClient);
		}
	}
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		if (!world.isClient) {
			BlockPos blockPos = hit.getBlockPos();
			Entity entity = projectile.getOwner();
			if (projectile.isOnFire() && projectile.canModifyAt(world, blockPos)) {
				primeTnt(world, blockPos, entity instanceof LivingEntity ? (LivingEntity) entity : null);
				world.removeBlock(blockPos, false);
			}
		}
	}
	public boolean shouldDropItemsOnExplosion(Explosion explosion) { return false; }
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.UNSTABLE); }
	public interface TntEntityProvider {
		ModTntEntity Make(World world, double x, double y, double z, LivingEntity igniter, Block block);
	}

	public static ItemDispenserBehavior DispenserBehavior(TntEntityProvider provider) {
		return new ItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				ModTntEntity tntEntity = provider.Make(world, (double) blockPos.getX() + 0.5D, blockPos.getY(), (double) blockPos.getZ() + 0.5D, null, ((BlockItem)stack.getItem()).getBlock());
				world.spawnEntity(tntEntity);
				if (tntEntity.shouldMakeSound()) {
					world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				world.emitGameEvent(null, GameEvent.ENTITY_PLACE, blockPos);
				stack.decrement(1);
				return stack;
			}
		};
	}
}