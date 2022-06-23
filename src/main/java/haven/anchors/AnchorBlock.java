package haven.anchors;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class AnchorBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final IntProperty OWNER = IntProperty.of("owner", 0, 50);
	
	public static final AnchorBlock ANCHOR_BLOCK = new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	
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
		if (Anchors.ANCHOR_MAP.containsKey(owner)) {
			ItemStack otherStack = new ItemStack(Anchors.ANCHOR_CORES.get(owner), 1);
			ItemEntity itemEntity = new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), otherStack);
		    player.world.spawnEntity(itemEntity);
		}
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, Anchors.ANCHOR_BLOCK_ENTITY, (world1, pos, state1, be) -> AnchorBlockEntity.tick(world1, pos, state1, be));
	}
}
