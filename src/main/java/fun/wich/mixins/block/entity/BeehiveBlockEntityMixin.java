package fun.wich.mixins.block.entity;

import fun.wich.origins.power.PowersUtil;
import fun.wich.origins.power.TakeHoneyPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntityMixin extends BlockEntity {
	public BeehiveBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Inject(method = "angerBees", at = @At("HEAD"), cancellable = true)
	public void angerBees$MobOrigins(PlayerEntity player, BlockState state, BeehiveBlockEntity.BeeState beeState, CallbackInfo ci) {
		if (PowersUtil.Active(player, TakeHoneyPower.class)) ci.cancel();
	}
	@Inject(method="tryEnterHive(Lnet/minecraft/entity/Entity;ZI)V", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
	private void InjectBlockChangeOnBeeEnter(Entity entity, boolean hasNectar, int ticksInHive, CallbackInfo ci) {
		if (this.world != null) this.world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, this.getPos());
	}
}
