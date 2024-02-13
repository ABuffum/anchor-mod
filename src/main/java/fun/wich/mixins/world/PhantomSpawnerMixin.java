package fun.wich.mixins.world;

import fun.wich.ModGameRules;
import fun.wich.entity.ModEntityType;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.PhantomSpawner;
import net.minecraft.world.gen.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnerMixin implements Spawner {
	@Shadow private int ticksUntilNextSpawn;

	@Inject(method="spawn", at=@At("HEAD"), cancellable=true)
	public void SpawnRedPhantoms(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
		if (!spawnMonsters) return;
		if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) return;
		Random random = world.random;
		--this.ticksUntilNextSpawn;
		if (this.ticksUntilNextSpawn > 0) return;
		this.ticksUntilNextSpawn += (60 + random.nextInt(60)) * 20;
		EntityType<? extends PhantomEntity> type;
		int days;
		if (!(world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight())) {
			type = EntityType.PHANTOM;
			days = world.getGameRules().getInt(ModGameRules.PHANTOM_START_DAYS);
		}
		else if (world.isDay()) {
			if (!world.getGameRules().getBoolean(ModGameRules.DO_RED_PHANTOM_SPAWNING)) return;
			type = ModEntityType.RED_PHANTOM_ENTITY;
			days = world.getGameRules().getInt(ModGameRules.RED_PHANTOM_START_DAYS);
		}
		else return;
		int i = 0;
		int k = 24000 * days;
		for (ServerPlayerEntity playerEntity : world.getPlayers()) {
			BlockPos blockPos2;
			LocalDifficulty localDifficulty;
			if (playerEntity.isSpectator()) continue;
			BlockPos blockPos = playerEntity.getBlockPos();
			if (world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos))
					|| !(localDifficulty = world.getLocalDifficulty(blockPos)).isHarderThan(random.nextFloat() * 3.0f)) continue;
			ServerStatHandler serverStatHandler = playerEntity.getStatHandler();
			int j = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
			if (j < k) continue; //Has slept too recently
			if (random.nextInt(j) < k
					|| !SpawnHelper.isClearForSpawn(world, blockPos2 = blockPos
							.up(20 + random.nextInt(15))
							.east(-10 + random.nextInt(21))
							.south(-10 + random.nextInt(21)),
					world.getBlockState(blockPos2), world.getFluidState(blockPos2), type)) continue;
			EntityData entityData = null;
			int l = 1 + random.nextInt(localDifficulty.getGlobalDifficulty().getId() + 1);
			for (int m = 0; m < l; ++m) {
				PhantomEntity phantomEntity = type.create(world);
				phantomEntity.refreshPositionAndAngles(blockPos2, 0.0f, 0.0f);
				entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
				world.spawnEntityAndPassengers(phantomEntity);
			}
			i += l;
		}
		if (i > 0) cir.setReturnValue(i);
	}
}
