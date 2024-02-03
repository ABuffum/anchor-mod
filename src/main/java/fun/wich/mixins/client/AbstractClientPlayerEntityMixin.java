package fun.wich.mixins.client;

import com.mojang.authlib.GameProfile;
import fun.wich.origins.power.CapePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
	public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	private Identifier GetCapeTexture() {
		List<CapePower> powers = PowerHolderComponent.getPowers(this, CapePower.class);
		for (CapePower power : powers) {
			if (power.isActive()) {
				Identifier texture = power.getTextureLocation();
				if (texture != null) return texture;
			}
		}
		return null;
	}

	@Inject(method="canRenderCapeTexture", at=@At("HEAD"), cancellable=true)
	private void CanRenderCapeFromOriginPower(CallbackInfoReturnable<Boolean> cir) {
		Identifier texture = GetCapeTexture();
		if (texture != null) cir.setReturnValue(true);
	}

	@Inject(method="getCapeTexture", at=@At("HEAD"), cancellable=true)
	private void InjectCapeTextureFromOriginPower(CallbackInfoReturnable<Identifier> cir) {
		Identifier texture = GetCapeTexture();
		if (texture != null) cir.setReturnValue(texture);
	}
}
