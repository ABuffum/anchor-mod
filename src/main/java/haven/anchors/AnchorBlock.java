package haven.anchors;

import haven.HavenMod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

import java.util.UUID;

public class AnchorBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final IntProperty OWNER = IntProperty.of("owner", 0, 50);
	
	public AnchorBlock(Settings settings) {
		super(settings.nonOpaque());
		setDefaultState(getStateManager().getDefaultState().with(OWNER, 0));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OWNER);
    }
	
	@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1.5f, 1f);
    }
	@Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 1.5f, 1f);
    }
	
	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnchorBlockEntity(pos, state);
    }
	
	@Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }
	
	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		int owner = state.get(OWNER);
		super.afterBreak(world, player, pos, state, blockEntity, stack);
		if (HavenMod.ANCHOR_MAP.containsKey(owner)) {
			ItemStack otherStack = new ItemStack(HavenMod.ANCHOR_CORES.get(owner), 1);
			ItemEntity itemEntity = new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), otherStack);
		    player.world.spawnEntity(itemEntity);
		}
		LOGGER.log(Level.ALL, "[Haven] The loot table id is: " + HavenMod.BONE_TORCH_BLOCK.getLootTableId().toString());
		MinecraftClient mc = MinecraftClient.getInstance();
		UUID uuid = mc.player.getUuid();
		String message;
		message = "[Haven] The id is: " + new Identifier(HavenMod.NAMESPACE, "bone_torch").toString();
		mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(message), uuid);
		message = "[Haven] The toString is: " + HavenMod.BONE_TORCH_BLOCK.toString();
		mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(message), uuid);
		message = "[Haven] The loot table id is: " + HavenMod.BONE_TORCH_BLOCK.getLootTableId().toString();
		mc.inGameHud.addChatMessage(MessageType.SYSTEM, new LiteralText(message), uuid);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, HavenMod.ANCHOR_BLOCK_ENTITY, (world1, pos, state1, be) -> AnchorBlockEntity.tick(world1, pos, state1, be));
	}
}
