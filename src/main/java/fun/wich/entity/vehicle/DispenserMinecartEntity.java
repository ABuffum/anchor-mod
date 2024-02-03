package fun.wich.entity.vehicle;

import fun.wich.ModBase;
import fun.wich.block.DispenserMinecartPointer;
import fun.wich.entity.ModNbtKeys;
import fun.wich.mixins.block.DispenserBlockInvoker;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

public class DispenserMinecartEntity extends StorageMinecartEntity {
	protected int cooldownTicks = -1;
	protected boolean outward = false;

	public DispenserMinecartEntity(EntityType<? extends DispenserMinecartEntity> entityType, World world) {
		super(entityType, world);
	}
	public DispenserMinecartEntity(World world, double x, double y, double z) {
		super(ModBase.DISPENSER_MINECART_ENTITY, x, y, z, world);
	}
	@Override
	public void dropItems(DamageSource damageSource) {
		super.dropItems(damageSource);
		if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) this.dropItem(Items.DISPENSER);
	}
	@Override
	public int size() { return 9; }

	@Override
	public BlockState getDefaultContainedBlock() {
		Direction d = getHorizontalFacing();
		return Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, outward ? d : d.getOpposite());
	}
	public BlockState getContainedBlockForRender() {
		return Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, outward ? Direction.EAST : Direction.WEST);
	}

	@Override
	public Type getMinecartType() { return null; }
	@Override
	public ItemStack getPickBlockStack() { return new ItemStack(ModBase.DISPENSER_MINECART_ITEM); }
	@Override
	public int getDefaultBlockOffset() { return 8; }
	@Override
	public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
	}
	@Override
	public void tick() {
		super.tick();
		if (this.cooldownTicks >= 0) this.cooldownTicks--;
	}
	public int chooseNonEmptySlot() {
		int i = -1;
		int j = 1;
		for (int k = 0; k < this.size(); ++k) {
			if (this.getStack(k).isEmpty() || random.nextInt(j++) != 0) continue;
			i = k;
		}
		return i;
	}
	public int addToFirstFreeSlot(ItemStack stack) {
		for (int i = 0; i < this.size(); ++i) {
			if (!this.getStack(i).isEmpty()) continue;
			this.setStack(i, stack);
			return i;
		}
		return -1;
	}
	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.isSneaking()) {
			outward = !outward;
			return ActionResult.SUCCESS;
		}
		return super.interact(player, hand);
	}
	public static void OverrideDispenserBehaviors() {
		DispenserBehavior BUCKET = ((DispenserBlockInvoker)Blocks.DISPENSER).InvokeGetBehaviorForItem(Items.BUCKET.getDefaultStack());
		DispenserBlock.registerBehavior(Items.BUCKET, new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				if (!(pointer instanceof DispenserMinecartPointer minecartPointer)) return BUCKET.dispense(pointer, stack);
				ItemStack itemStack;
				BlockPos blockPos;
				ServerWorld worldAccess = minecartPointer.getWorld();
				BlockState blockState = worldAccess.getBlockState(blockPos = minecartPointer.getPos().offset(minecartPointer.getBlockState().get(DispenserBlock.FACING)));
				Block block = blockState.getBlock();
				if (block instanceof FluidDrainable fluidDrainable) {
					itemStack = fluidDrainable.tryDrainFluid(worldAccess, blockPos, blockState);
					if (itemStack.isEmpty()) return super.dispenseSilently(minecartPointer, stack);
				}
				else return super.dispenseSilently(minecartPointer, stack);
				worldAccess.emitGameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
				Item item = itemStack.getItem();
				stack.decrement(1);
				if (stack.isEmpty()) return new ItemStack(item);
				if (minecartPointer.getEntity().addToFirstFreeSlot(new ItemStack(item)) < 0) {
					this.fallbackBehavior.dispense(minecartPointer, new ItemStack(item));
				}
				return stack;
			}
		});
		DispenserBehavior GLASS_BOTTLE = ((DispenserBlockInvoker)Blocks.DISPENSER).InvokeGetBehaviorForItem(Items.GLASS_BOTTLE.getDefaultStack());
		DispenserBlock.registerBehavior(Items.GLASS_BOTTLE.asItem(), new FallibleItemDispenserBehavior(){
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

			private ItemStack tryPutFilledBottle(DispenserMinecartPointer minecartPointer, ItemStack emptyBottleStack, ItemStack filledBottleStack) {
				emptyBottleStack.decrement(1);
				if (emptyBottleStack.isEmpty()) {
					minecartPointer.getWorld().emitGameEvent(null, GameEvent.FLUID_PICKUP, minecartPointer.getPos());
					return filledBottleStack.copy();
				}
				if (minecartPointer.getEntity().addToFirstFreeSlot(filledBottleStack.copy()) < 0) {
					this.fallbackBehavior.dispense(minecartPointer, filledBottleStack.copy());
				}
				return emptyBottleStack;
			}

			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				if (!(pointer instanceof DispenserMinecartPointer minecartPointer)) return GLASS_BOTTLE.dispense(pointer, stack);
				this.setSuccess(false);
				ServerWorld serverWorld = minecartPointer.getWorld();
				BlockPos blockPos = minecartPointer.getPos().offset(minecartPointer.getBlockState().get(DispenserBlock.FACING));
				BlockState blockState = serverWorld.getBlockState(blockPos);
				if (blockState.isIn(BlockTags.BEEHIVES, state -> state.contains(BeehiveBlock.HONEY_LEVEL) && state.getBlock() instanceof BeehiveBlock) && blockState.get(BeehiveBlock.HONEY_LEVEL) >= 5) {
					((BeehiveBlock)blockState.getBlock()).takeHoney(serverWorld, blockState, blockPos, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
					this.setSuccess(true);
					return this.tryPutFilledBottle(minecartPointer, stack, new ItemStack(Items.HONEY_BOTTLE));
				}
				if (serverWorld.getFluidState(blockPos).isIn(FluidTags.WATER)) {
					this.setSuccess(true);
					return this.tryPutFilledBottle(minecartPointer, stack, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
				}
				return super.dispenseSilently(minecartPointer, stack);
			}
		});
	}
	protected void dispense(double velocity) {
		if (!this.world.isClient) {
			BlockPos pos = getBlockPos();
			BlockPointer pointer = new DispenserMinecartPointer((ServerWorld)world, this);
			int i = chooseNonEmptySlot();
			if (i < 0) {
				world.syncWorldEvent(WorldEvents.DISPENSER_FAILS, pos, 0);
				world.emitGameEvent(GameEvent.DISPENSE_FAIL, pos);
				return;
			}
			ItemStack itemStack = getStack(i);
			DispenserBehavior dispenserBehavior = ((DispenserBlockInvoker)Blocks.DISPENSER).InvokeGetBehaviorForItem(itemStack);
			if (dispenserBehavior != DispenserBehavior.NOOP) {
				ItemStack stack = itemStack;
				boolean worked = true;
				try {
					stack = dispenserBehavior.dispense(pointer, itemStack);
				}
				catch (NullPointerException e) {
					worked = false;
				}
				if (worked) setStack(i, stack);
			}
		}
	}
	@Override
	public void onActivatorRail(int x, int y, int z, boolean powered) {
		if (powered && this.cooldownTicks < 0) {
			this.cooldownTicks = 8;
			this.dispense(this.getVelocity().horizontalLengthSquared());
		}
	}
	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains(ModNbtKeys.COOLDOWN, 99)) this.cooldownTicks = nbt.getInt(ModNbtKeys.COOLDOWN);
		if (nbt.contains(ModNbtKeys.OUTWARD)) this.outward = nbt.getBoolean(ModNbtKeys.OUTWARD);
	}
	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt(ModNbtKeys.COOLDOWN, this.cooldownTicks);
		nbt.putBoolean(ModNbtKeys.OUTWARD, this.outward);
	}
}
