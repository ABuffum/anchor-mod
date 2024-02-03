package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class PulsingSkinGlowPower extends Power {
	public final int light_level; //Must be [0,15]
	public final int pulse_time; //Must be >= 1
	public final int pulse_delay; //Must be >= 10
	public final short pulse_offset; //Must be [0,32767]
	public PulsingSkinGlowPower(PowerType<?> type, LivingEntity entity, int light_level, int pulse_time, int pulse_delay, int pulse_offset) {
		super(type, entity);
		this.light_level = Math.max(0, Math.min(light_level, 15));
		this.pulse_time = Math.max(1, pulse_time);
		this.pulse_delay = Math.max(10, pulse_delay);
		this.pulse_offset = (short)Math.max(0, Math.min(Short.MAX_VALUE, pulse_offset)); //Cast safe, value clamped to [0,32767 = Short.MAX_VALUE]
	}

	public static PowerFactory<PulsingSkinGlowPower> createFactory() {
		return new PowerFactory<PulsingSkinGlowPower>(ModId.ID("pulsing_skin_glow"), new SerializableData()
				.add("light_level", SerializableDataTypes.INT, 1)
				.add("pulse_time", SerializableDataTypes.INT, 10)
				.add("pulse_delay", SerializableDataTypes.INT, 100)
				.add("pulse_offset", SerializableDataTypes.INT, 0),
				data -> (type, player) -> new PulsingSkinGlowPower(type, player, data.getInt("light_level"), data.getInt("pulse_time"), data.getInt("pulse_delay"), data.getInt("pulse_offset"))
		).allowCondition();
	}

	private static long tick = 0; //Stored as a long to support huge pulse gaps
	private static final int MAX_TICK = Integer.MAX_VALUE;
	public static void tick(ServerWorld world) { if (tick++ > MAX_TICK) tick = 0; }

	public int getGlow() {
		if (!isActive()) return 0;
		long pulse_off = light_level + pulse_delay;
		long pulse_on = pulse_off + light_level;
		long remainder = (tick - pulse_offset) % (pulse_on + pulse_time);
		//Light is ramping down
		if (remainder < light_level) return (int)(light_level - remainder); //int cast is safe, value can only be [1,15]
		//Light is off
		else if (remainder < pulse_off) return 0;
		//Light is ramping up
		else if (remainder < pulse_on) return (int)(remainder - pulse_off); //int cast is safe, value can only be [1,15]
		//Light is on
		else return light_level;
	}
}