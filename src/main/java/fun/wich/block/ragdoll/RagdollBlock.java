package fun.wich.block.ragdoll;

import com.mojang.authlib.GameProfile;
import fun.wich.ModBase;
import fun.wich.entity.ModNbtKeys;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class RagdollBlock extends BlockWithEntity {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
	public RagdollBlock(Settings settings) { super(settings); }
	@Override
	public BlockRenderType getRenderType(BlockState blockState) { return BlockRenderType.ENTITYBLOCK_ANIMATED; }
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ModBase.RAGDOLL_BLOCK_ENTITY, RagdollBlockEntity::tick);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return context.isHolding(ModBase.RAGDOLL_WAND) ? VoxelShapes.fullCube() : SHAPE;
	}
	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) { return VoxelShapes.empty(); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new RagdollBlockEntity(pos, state); }
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof RagdollBlockEntity entity) {
			GameProfile gameProfile = null;
			if (itemStack.hasNbt()) {
				NbtCompound nbtCompound = itemStack.getNbt();
				if (nbtCompound.contains(ModNbtKeys.OWNER, 10)) {
					gameProfile = NbtHelper.toGameProfile(nbtCompound.getCompound(ModNbtKeys.OWNER));
				}
				else if (nbtCompound.contains(ModNbtKeys.OWNER, 8) && !StringUtils.isBlank(nbtCompound.getString(ModNbtKeys.OWNER))) {
					gameProfile = new GameProfile(null, nbtCompound.getString(ModNbtKeys.OWNER));
				}
			}
			entity.setOwner(gameProfile);
		}
	}
}
