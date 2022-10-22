package haven.mixins.items;

import haven.blood.BloodType;
import haven.events.HavenGameEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFruitItem.class)
public abstract class ChorusFruitItemMixin extends Item {
	public ChorusFruitItemMixin(Settings settings) { super(settings); }

	@Inject(method="finishUsing", at = @At("HEAD"), cancellable = true)
	public void DontTeleportIfImmune(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (!BloodType.Get(user).IsChorusVulnerable()) {
			cir.setReturnValue(super.finishUsing(stack, world, user));
		}
	}

	@Inject(method="finishUsing", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
	public void TriggerTeleportEvent(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (!world.isClient) world.emitGameEvent(user, HavenGameEvent.TELEPORT, user.getBlockPos());
	}
}
