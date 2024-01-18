package fun.mousewich.origins.power;

import fun.mousewich.ModId;
import fun.mousewich.sound.ModSoundEvents;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class WardenHeartbeatPower extends Power {
	public WardenHeartbeatPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
		this.setTicking(true);
	}

	public static final SoundEvent WARDEN_HEARTBEAT = ModSoundEvents.registerSoundEvent("identified_sound.heartbeat.warden");
	public static PowerFactory<?> createFactory() {
		return new PowerFactory<>(ModId.ID("warden_heartbeat"), new SerializableData(), data -> WardenHeartbeatPower::new).allowCondition();
	}

	private int getHeartRate() {
		if (entity instanceof PlayerEntity player) {
			int foodLevel = player.getHungerManager().getFoodLevel();
			float health = player.getHealth() / player.getMaxHealth();
			if (foodLevel < 18 && health < 1) {
				if (foodLevel < 12 && health < 0.75) return 10; //angry
				else return 25; //agitated
			}
			else return 40; //calm
		}
		return 0;
	}
	@Override
	public void tick() {
		if (isActive()) {
			int heartRate = getHeartRate();
			if (heartRate < 1) return;
			if (entity.age % heartRate == 0) {
				if (!entity.isSilent() && !entity.isSpectator()) {
					PlayerEntity player = entity instanceof PlayerEntity p ? p : null;
					entity.world.playSound(player, entity.getX(), entity.getY(), entity.getZ(), WARDEN_HEARTBEAT, entity.getSoundCategory(), 5.0f, entity.getSoundPitch());
				}
			}
		}
	}
}
