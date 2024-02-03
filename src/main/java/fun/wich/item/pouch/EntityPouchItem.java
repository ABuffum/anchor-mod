package fun.wich.item.pouch;

import fun.wich.ModBase;
import fun.wich.entity.Pouchable;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EntityPouchItem extends PouchItem {
	private final EntityType<?> entityType;

	public EntityPouchItem(EntityType<?> type, Settings settings) {
		super(settings);
		this.entityType = type;
	}
	public EntityPouchItem(EntityType<?> type, SoundEvent emptyingSound, Settings settings) {
		super(emptyingSound, settings);
		this.entityType = type;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = this.place(new ItemPlacementContext(context));
		if (!actionResult.isAccepted() && this.isFood()) {
			ActionResult actionResult2 = this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
			return actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
		}
		return actionResult;
	}
	public ActionResult place(ItemPlacementContext context) {
		if (!context.canPlace()) return ActionResult.FAIL;
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		ItemStack stack = context.getStack();
		this.onEmptied(player, world, stack, context.getBlockPos());
		if (player != null && !player.getAbilities().creativeMode) {
			player.setStackInHand(context.getHand(), getEmptiedStack(stack, player));
		}
		return ActionResult.success(world.isClient);
	}

	@Override
	public void onEmptied(PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
		if (world instanceof ServerWorld) {
			this.spawnEntity((ServerWorld)world, stack, pos);
			playEmptyingSound(player, world, pos, stack);
			world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
		}
	}

	private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
		Entity entity = this.entityType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
		if (entity instanceof Pouchable pouchable) {
			pouchable.copyDataFromNbt(stack.getOrCreateNbt());
			pouchable.setFromPouch(true);
		}
	}

	public EntityPouchItem dispensible() {
		DispenserBlock.registerBehavior(this, dispenserBehavior);
		return this;
	}

	private static final ItemDispenserBehavior dispenserBehavior = new ItemDispenserBehavior(){
		private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

		@Override
		public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
			if (stack.getItem() instanceof EntityPouchItem pouch) {
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				ServerWorld world = pointer.getWorld();
				FluidState fluidState = world.getFluidState(blockPos);
				if (fluidState.isEmpty() || fluidState.isIn(FluidTags.WATER)) {
					pouch.onEmptied(null, world, stack, blockPos);
					return new ItemStack(ModBase.POUCH);
				}
			}
			return this.fallbackBehavior.dispense(pointer, stack);
		}
	};
}