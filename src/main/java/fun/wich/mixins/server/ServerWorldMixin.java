package fun.wich.mixins.server;

import fun.wich.origins.power.PulsingSkinGlowPower;
import fun.wich.util.LightningUtil;
import fun.wich.util.ServerEntityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.entity.EntityHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {
	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
	}

	@ModifyArg(method="<init>", at = @At(value="INVOKE", target="Lnet/minecraft/server/world/ServerEntityManager;<init>(Ljava/lang/Class;Lnet/minecraft/world/entity/EntityHandler;Lnet/minecraft/world/storage/ChunkDataAccess;)V"), index = 1)
	private EntityHandler<Entity> InjectServerEntityHandler(EntityHandler<Entity> handler) {
		return new ServerEntityHandler((ServerWorld)(Object)this);
	}

	@Inject(method="tick", at = @At(value="INVOKE", target="Lnet/minecraft/server/world/ServerWorld;tickBlockEntities()V"))
	public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ServerWorld serverWorld = (ServerWorld)(Object)this;
		PulsingSkinGlowPower.tick(serverWorld);
	}

	@Inject(method="getLightningRodPos", at = @At(value="HEAD"), cancellable = true)
	private void MakeLightningRod(BlockPos pos, CallbackInfoReturnable<Optional<BlockPos>> cir) {
		Vec3d origin = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
		List<LivingEntity> list = this.getEntitiesByClass(LivingEntity.class, Box.of(origin, 64, this.getHeight(), 64), e -> LightningUtil.ShouldLightningRod(origin, e));
		int size = list.size();
		if (size > 0) cir.setReturnValue(Optional.of(list.get(this.random.nextInt(size)).getBlockPos().up(1)));
	}
}
