package fun.mousewich.mixins.client;

import fun.mousewich.sound.IdentifiedSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value= EnvType.CLIENT)
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
	@Shadow @Final private MinecraftClient client;
	@Shadow private GameMode gameMode;
	@Shadow private boolean breakingBlock;
	@Shadow private int blockBreakingCooldown;
	@Shadow private BlockPos currentBreakingPos;
	@Shadow private float currentBreakingProgress;
	@Shadow private float blockBreakingSoundCooldown;
	@Shadow
	protected abstract void syncSelectedSlot();
	@Shadow
	public abstract boolean breakBlock(BlockPos pos);
	@Shadow
	protected abstract boolean isCurrentlyBreaking(BlockPos pos);
	@Shadow
	public abstract boolean attackBlock(BlockPos pos, Direction direction);
	@Shadow
	protected abstract void sendPlayerAction(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction);

	@Inject(method="updateBlockBreakingProgress", at = @At(value="HEAD", shift = At.Shift.AFTER, target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"), cancellable = true)
	public void UpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		this.syncSelectedSlot();
		if (this.blockBreakingCooldown > 0) {
			--this.blockBreakingCooldown;
			cir.setReturnValue(true);
			return;
		}
		if (this.gameMode.isCreative() && this.client.world.getWorldBorder().contains(pos)) {
			this.blockBreakingCooldown = 5;
			BlockState blockState = this.client.world.getBlockState(pos);
			this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, 1.0f);
			this.sendPlayerAction(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, direction);
			this.breakBlock(pos);
			cir.setReturnValue(true);
			return;
		}
		if (this.isCurrentlyBreaking(pos)) {
			BlockState blockState = this.client.world.getBlockState(pos);
			if (blockState.isAir()) {
				this.breakingBlock = false;
				cir.setReturnValue(false);
				return;
			}
			this.currentBreakingProgress += blockState.calcBlockBreakingDelta(this.client.player, this.client.player.world, pos);
			if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
				BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
				SoundEvent hitSound = IdentifiedSounds.getHitSound(blockState);
				if (hitSound == null) hitSound = blockSoundGroup.getHitSound();
				this.client.getSoundManager().play(new PositionedSoundInstance(hitSound, SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, pos));
			}
			this.blockBreakingSoundCooldown += 1.0f;
			this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));
			if (this.currentBreakingProgress >= 1.0f) {
				this.breakingBlock = false;
				this.sendPlayerAction(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction);
				this.breakBlock(pos);
				this.currentBreakingProgress = 0.0f;
				this.blockBreakingSoundCooldown = 0.0f;
				this.blockBreakingCooldown = 5;
			}
		}
		else {
			cir.setReturnValue(this.attackBlock(pos, direction));
			return;
		}
		this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0f) - 1);
		cir.setReturnValue(true);
	}
}
