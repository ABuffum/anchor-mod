package fun.mousewich.item;

import fun.mousewich.block.ragdoll.RagdollBlock;
import fun.mousewich.block.ragdoll.RagdollBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RagdollWandItem extends Item {
	public RagdollWandItem(Settings settings) { super(settings); }

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		if (!world.isClient) this.use(miner, world, pos, false, miner.getStackInHand(Hand.MAIN_HAND));
		return false;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos blockPos = context.getBlockPos();
		PlayerEntity playerEntity = context.getPlayer();
		World world = context.getWorld();
		if (!world.isClient && playerEntity != null && !this.use(playerEntity, world, blockPos, true, context.getStack())) {
			return ActionResult.FAIL;
		}
		return ActionResult.success(world.isClient);
	}

	private static final String NBT_KEY = "ControlProperty";

	private boolean use(PlayerEntity player, World world, BlockPos pos, boolean update, ItemStack stack) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		BlockEntity entity = world.getBlockEntity(pos);
		if (block instanceof RagdollBlock && entity instanceof RagdollBlockEntity ragdoll) {
			NbtCompound nbt = stack.getOrCreateNbt();
			int index;
			if (!nbt.contains(NBT_KEY, NbtElement.INT_TYPE)) nbt.putInt(NBT_KEY, 0);
			index = nbt.getInt(NBT_KEY);
			if (update) {
				if (index == 0) ragdoll.nextRagdollPose(player.isSneaking() ? -1 : 1);
				else if (index == 1) ragdoll.nextRagdollPitch(player.isSneaking() ? -1 : 1);
				else if (index == 2) ragdoll.nextRagdollRoll(player.isSneaking() ? -1 : 1);
				else if (index == 3) ragdoll.nextRagdollYaw(player.isSneaking() ? -1 : 1);
				else if (index == 4) ragdoll.nextRagdollX(player.isSneaking() ? -1 : 1);
				else if (index == 5) ragdoll.nextRagdollY(player.isSneaking() ? 1 : -1);
				else if (index == 6) ragdoll.nextRagdollZ(player.isSneaking() ? -1 : 1);
				else if (index == 7) ragdoll.toggleSlim();
				else if (index == 8) ragdoll.toggleHead();
				else if (index == 9) ragdoll.toggleBody();
				else if (index == 10) ragdoll.toggleLeftArm();
				else if (index == 11) ragdoll.toggleRightArm();
				else if (index == 12) ragdoll.toggleLeftLeg();
				else if (index == 13) ragdoll.toggleRightLeg();
				else {
					sendMessage(player, Text.of("Could not change unknown property: " + index));
					return false;
				}
				sendMessage(player, Text.of("Modified " + RagdollProperty(index) + " with new value " + RagdollValue(index, ragdoll)));
				ragdoll.markDirty();
				world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
			}
			else {
				index += player.isSneaking() ? -1 : 1;
				if (index > 13) index = 0;
				else if (index < 0) index = 13;
				nbt.putInt(NBT_KEY, index);
				sendMessage(player, Text.of("Now modifying " + RagdollProperty(index) + " with value " + RagdollValue(index, ragdoll)));
			}
			return true;
		}
		else sendMessage(player, Text.of("Expected ragdoll entity"));
		return false;
	}

	private static String RagdollProperty(int index) {
		return switch (index) {
			case 0 -> "Pose";
			case 1 -> "Pitch";
			case 2 -> "Roll";
			case 3 -> "Yaw";
			case 4 -> "X";
			case 5 -> "Y";
			case 6 -> "Z";
			case 7 -> "Slim";
			case 8 -> "Render Head";
			case 9 -> "Render Body";
			case 10 -> "Render Left Arm";
			case 11 -> "Render Right Arm";
			case 12 -> "Render Left Leg";
			case 13 -> "Render Right Leg";
			default -> null;
		};
	}
	private static String RagdollValue(int index, RagdollBlockEntity ragdoll) {
		return switch (index) {
			case 0 -> ragdoll.getRagdollPose().toString();
			case 1 -> "" + ragdoll.getRagdollPitch();
			case 2 -> "" + ragdoll.getRagdollRoll();
			case 3 -> "" + ragdoll.getRagdollYaw();
			case 4 -> "" + ragdoll.getRagdollX();
			case 5 -> "" + -ragdoll.getRagdollY();
			case 6 -> "" + ragdoll.getRagdollZ();
			case 7 -> "" + ragdoll.isSlim();
			case 8 -> "" + ragdoll.renderHead();
			case 9 -> "" + ragdoll.renderBody();
			case 10 -> "" + ragdoll.renderLeftArm();
			case 11 -> "" + ragdoll.renderRightArm();
			case 12 -> "" + ragdoll.renderLeftLeg();
			case 13 -> "" + ragdoll.renderRightLeg();
			default -> "Unmapped";
		};
	}

	private static void sendMessage(PlayerEntity player, Text message) {
		((ServerPlayerEntity)player).sendMessage(message, MessageType.GAME_INFO, Util.NIL_UUID);
	}
}
