package fun.mousewich.block.sculk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.mousewich.entity.hostile.warden.WardenEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.*;
import java.util.function.Predicate;

public class SculkShriekerWarningManager {
	public static final Codec<SculkShriekerWarningManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codecs.NONNEGATIVE_INT.fieldOf("ticks_since_last_warning").orElse(0).forGetter(manager -> manager.ticksSinceLastWarning),
			Codecs.NONNEGATIVE_INT.fieldOf("warning_level").orElse(0).forGetter(manager -> manager.warningLevel),
			Codecs.NONNEGATIVE_INT.fieldOf("cooldown_ticks").orElse(0).forGetter(manager -> manager.cooldownTicks))
			.apply(instance, (ticksSinceLastWarning, warningLevel, cooldownTicks) ->
					new SculkShriekerWarningManager((int)ticksSinceLastWarning, (int)warningLevel, (int)cooldownTicks)));
	private int ticksSinceLastWarning;
	private int warningLevel;
	private int cooldownTicks;

	public SculkShriekerWarningManager(int ticksSinceLastWarning, int warningLevel, int cooldownTicks) {
		this.ticksSinceLastWarning = ticksSinceLastWarning;
		this.warningLevel = warningLevel;
		this.cooldownTicks = cooldownTicks;
	}

	public void tick() {
		if (this.ticksSinceLastWarning++ >= 12000) {
			this.decreaseWarningLevel();
			this.ticksSinceLastWarning = 0;
		}
		if (this.cooldownTicks > 0) --this.cooldownTicks;
	}

	public void reset() {
		this.ticksSinceLastWarning = 0;
		this.warningLevel = 0;
		this.cooldownTicks = 0;
	}

	public static Map<PlayerEntity, SculkShriekerWarningManager> WARNING_MANAGERS = new HashMap<>();
	public static SculkShriekerWarningManager getSculkShriekerWarningManager(PlayerEntity playerEntity) {
		SculkShriekerWarningManager manager;
		if (WARNING_MANAGERS.containsKey(playerEntity)) manager = WARNING_MANAGERS.get(playerEntity);
		else {
			manager = new SculkShriekerWarningManager(0, 0, 0);
			WARNING_MANAGERS.put(playerEntity, manager);
		}
		return manager;
	}

	public static OptionalInt warnNearbyPlayers(ServerWorld serverWorld, BlockPos blockPos, ServerPlayerEntity serverPlayerEntity2) {
		if (SculkShriekerWarningManager.canIncreaseWarningLevel(serverWorld, blockPos)) return OptionalInt.empty();
		List<ServerPlayerEntity> list = SculkShriekerWarningManager.getPlayersInRange(serverWorld, blockPos);
		if (!list.contains(serverPlayerEntity2)) list.add(serverPlayerEntity2);
		if (list.stream().anyMatch(serverPlayerEntity -> getSculkShriekerWarningManager(serverPlayerEntity).cooldownTicks > 0)) return OptionalInt.empty();
		Optional<SculkShriekerWarningManager> optional = list.stream().map(SculkShriekerWarningManager::getSculkShriekerWarningManager).max(Comparator.comparingInt(manager -> manager.warningLevel));
		SculkShriekerWarningManager sculkShriekerWarningManager = optional.get();
		sculkShriekerWarningManager.increaseWarningLevel();
		list.forEach(serverPlayerEntity -> getSculkShriekerWarningManager(serverPlayerEntity).copy(sculkShriekerWarningManager));
		return OptionalInt.of(sculkShriekerWarningManager.warningLevel);
	}

	private static boolean canIncreaseWarningLevel(ServerWorld serverWorld, BlockPos blockPos) {
		Box box = Box.of(Vec3d.ofCenter(blockPos), 48.0, 48.0, 48.0);
		return !serverWorld.getNonSpectatingEntities(WardenEntity.class, box).isEmpty();
	}

	private static List<ServerPlayerEntity> getPlayersInRange(ServerWorld world, BlockPos pos) {
		Predicate<ServerPlayerEntity> predicate = player -> player.getPos().isInRange(Vec3d.ofCenter(pos), 16.0);
		return world.getPlayers(predicate.and(LivingEntity::isAlive).and(EntityPredicates.EXCEPT_SPECTATOR));
	}

	private void increaseWarningLevel() {
		if (this.cooldownTicks <= 0) {
			this.ticksSinceLastWarning = 0;
			this.cooldownTicks = 200;
			this.setWarningLevel(this.getWarningLevel() + 1);
		}
	}

	private void decreaseWarningLevel() {
		this.setWarningLevel(this.getWarningLevel() - 1);
	}

	public void setWarningLevel(int warningLevel) {
		this.warningLevel = MathHelper.clamp(warningLevel, 0, 4);
	}

	public int getWarningLevel() {
		return this.warningLevel;
	}

	private void copy(SculkShriekerWarningManager other) {
		this.warningLevel = other.warningLevel;
		this.cooldownTicks = other.cooldownTicks;
		this.ticksSinceLastWarning = other.ticksSinceLastWarning;
	}
}
