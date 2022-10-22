package haven.mixins.blocks;

import haven.events.HavenGameEvent;
import net.minecraft.block.Block;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin extends Block {
	public NoteBlockMixin(Settings settings) { super(settings); }

	@Inject(method="playNote", at = @At("TAIL"))
	private void playNote(World world, BlockPos pos, CallbackInfo ci) {
		world.emitGameEvent(null, HavenGameEvent.NOTE_BLOCK_PLAY, pos);
	}
}
