package fun.mousewich.mixins.block.entity;

import com.google.common.collect.Lists;
import fun.mousewich.util.dye.ModStainable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity implements NamedScreenHandlerFactory {
	@Shadow private static int updateLevel(World world, int x, int y, int z) { return 0; }
	@Shadow private static void applyPlayerEffects(World world, BlockPos pos, int beaconLevel, @Nullable StatusEffect primaryEffect, @Nullable StatusEffect secondaryEffect) { }

	public BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Inject(method="tick", at=@At("HEAD"), cancellable=true)
	private static void TickWithModBeamColors(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci) {
		int m;
		BlockPos blockPos;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		BeaconBlockEntityAccessor accessor = (BeaconBlockEntityAccessor)blockEntity;
		if (accessor.GetMinY() < j) {
			blockPos = pos;
			accessor.SetSegments(Lists.newArrayList());
			accessor.SetMinY(blockPos.getY() - 1);
		}
		else blockPos = new BlockPos(i, accessor.GetMinY() + 1, k);
		BeaconBlockEntity.BeamSegment beamSegment = accessor.GetSegments().isEmpty() ? null : accessor.GetSegments().get(accessor.GetSegments().size() - 1);
		int l = world.getTopY(Heightmap.Type.WORLD_SURFACE, i, k);
		for (m = 0; m < 10 && blockPos.getY() <= l; ++m) {
			block18: {
				BlockState blockState;
				block16: {
					float[] fs;
					block17: {
						blockState = world.getBlockState(blockPos);
						Block block = blockState.getBlock();
						if (block instanceof ModStainable dyed) fs = dyed.GetModColor().getColorComponents();
						else if (block instanceof Stainable stained) fs = stained.getColor().getColorComponents();
						else break block16;
						if (accessor.GetSegments().size() > 1) break block17;
						beamSegment = new BeaconBlockEntity.BeamSegment(fs);
						accessor.GetSegments().add(beamSegment);
						break block18;
					}
					if (beamSegment == null) break block18;
					if (Arrays.equals(fs, beamSegment.getColor())) {
						((BeaconBlockEntityBeamSegmentInvoker)beamSegment).InvokeIncreaseHeight();
					}
					else {
						float[] color = beamSegment.getColor();
						beamSegment = new BeaconBlockEntity.BeamSegment(new float[]{ (color[0] + fs[0]) / 2f, (color[1] + fs[1]) / 2f, (color[2] + fs[2]) / 2f });
						accessor.GetSegments().add(beamSegment);
					}
					break block18;
				}
				if (beamSegment != null && (blockState.getOpacity(world, blockPos) < 15 || blockState.isOf(Blocks.BEDROCK))) {
					((BeaconBlockEntityBeamSegmentInvoker)beamSegment).InvokeIncreaseHeight();
				}
				else {
					accessor.GetSegments().clear();
					accessor.SetMinY(l);
					break;
				}
			}
			blockPos = blockPos.up();
			accessor.SetMinY(accessor.GetMinY() + 1);
		}
		m = accessor.GetLevel();
		if (world.getTime() % 80L == 0L) {
			if (!accessor.GetBeamSegments().isEmpty()) accessor.SetLevel(updateLevel(world, i, j, k));
			if (accessor.GetLevel() > 0 && !accessor.GetBeamSegments().isEmpty()) {
				applyPlayerEffects(world, pos, accessor.GetLevel(), accessor.GetPrimary(), accessor.GetSecondary());
				BeaconBlockEntity.playSound(world, pos, SoundEvents.BLOCK_BEACON_AMBIENT);
			}
		}
		if (accessor.GetMinY() >= l) {
			accessor.SetMinY(world.getBottomY() - 1);
			boolean bl = m > 0;
			accessor.SetBeamSegments(accessor.GetSegments());
			if (!world.isClient) {
				boolean bl2 = accessor.GetLevel() > 0;
				if (!bl && bl2) {
					BeaconBlockEntity.playSound(world, pos, SoundEvents.BLOCK_BEACON_ACTIVATE);
					for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, new Box(i, j, k, i, j - 4, k).expand(10.0, 5.0, 10.0))) {
						Criteria.CONSTRUCT_BEACON.trigger(serverPlayerEntity, accessor.GetLevel());
					}
				}
				else if (bl && !bl2) BeaconBlockEntity.playSound(world, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE);
			}
		}
		ci.cancel();
	}
}
