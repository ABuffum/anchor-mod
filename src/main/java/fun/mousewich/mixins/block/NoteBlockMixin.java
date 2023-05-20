package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin extends Block {
	public NoteBlockMixin(Settings settings) { super(settings); }

	@Inject(method="playNote", at = @At("HEAD"), cancellable = true)
	private void PlayNote(World world, BlockPos pos, CallbackInfo ci) {
		if (world.getBlockState(pos.up()).isAir()) {
			world.emitGameEvent(null, ModGameEvent.NOTE_BLOCK_PLAY, pos);
		}
		else if (fromAboveState(world.getBlockState(pos.up())) != null) {
			world.addSyncedBlockEvent(pos, this, 0, 0);
			world.emitGameEvent(null, ModGameEvent.NOTE_BLOCK_PLAY, pos);
			ci.cancel();
		}
	}
	@Inject(method="onSyncedBlockEvent", at = @At("HEAD"), cancellable = true)
	public void tryPlayMobHeadSound(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir) {
		SoundEvent instrument = fromAboveState(world.getBlockState(pos.up()));
		if (instrument != null) {
			int i = state.get(Properties.NOTE);
			float f = (float)Math.pow(2.0, (double)(i - 12) / 12.0);
			world.playSound(null, pos, instrument, SoundCategory.RECORDS, 3.0f, f);
			world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
			cir.setReturnValue(true);
		}
	}

	private static SoundEvent fromAboveState(BlockState state) {
		if (state.isOf(Blocks.ZOMBIE_HEAD)) return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
		if (state.isOf(Blocks.SKELETON_SKULL)) return SoundEvents.ENTITY_SKELETON_AMBIENT;
		if (state.isOf(Blocks.CREEPER_HEAD)) return SoundEvents.ENTITY_CREEPER_PRIMED;
		if (state.isOf(Blocks.DRAGON_HEAD)) return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
		if (state.isOf(Blocks.WITHER_SKELETON_SKULL)) return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
		if (state.isOf(ModBase.PIGLIN_HEAD.asBlock())) return SoundEvents.ENTITY_PIGLIN_ANGRY;
		if (state.isOf(ModBase.ZOMBIFIED_PIGLIN_HEAD.asBlock())) return SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_ANGRY;
		return null;
	}
}
