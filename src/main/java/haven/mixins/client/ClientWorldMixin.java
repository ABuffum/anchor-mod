package haven.mixins.client;

import haven.blocks.lighting.DynamicLitBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
	protected ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
	}

	@Inject(method="doRandomBlockDisplayTicks", at = @At("HEAD"))
	public void DoRandomBlockDisplayTicks(int centerX, int centerY, int centerZ, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE) {
			if (client.player.getMainHandStack().getItem() == Items.LIGHT) {
				BlockPos.Mutable mutable = new BlockPos.Mutable();
				for (int j = 0; j < 667; ++j) {
					this.randomBlockDisplayTick(centerX, centerY, centerZ, 16, mutable);
					this.randomBlockDisplayTick(centerX, centerY, centerZ, 32, mutable);
				}
			}
		}
	}

	public void randomBlockDisplayTick(int centerX, int centerY, int centerZ, int radius, BlockPos.Mutable pos) {
		int i = centerX + random.nextInt(radius) - random.nextInt(radius);
		int j = centerY + random.nextInt(radius) - random.nextInt(radius);
		int k = centerZ + random.nextInt(radius) - random.nextInt(radius);
		pos.set(i, j, k);
		BlockState blockState = this.getBlockState(pos);
		if (blockState.getBlock() instanceof DynamicLitBlock) {
			this.addParticle(ParticleTypes.LIGHT, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 0.0D, 0.0D, 0.0D);
		}
	}
}
