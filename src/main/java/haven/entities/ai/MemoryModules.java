package haven.entities.ai;

import com.mojang.serialization.Codec;
import haven.mixins.entities.ai.MemoryModuleTypeMixin;
import haven.mixins.entities.ai.OtherMemoryModuleTypeMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;

public class MemoryModules {
	public static final MemoryModuleType<Unit> IS_SNIFFING = MemoryModuleTypeMixin.Register("is_sniffing", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> IS_EMERGING = MemoryModuleTypeMixin.Register("is_emerging", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SNIFF_COOLDOWN = MemoryModuleTypeMixin.Register("sniff_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<BlockPos> DISTURBANCE_LOCATION = OtherMemoryModuleTypeMixin.Register("disturbance_location");
	public static final MemoryModuleType<Unit> ROAR_SOUND_DELAY = MemoryModuleTypeMixin.Register("roar_sound_delay", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> DIG_COOLDOWN = MemoryModuleTypeMixin.Register("dig_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> ROAR_SOUND_COOLDOWN = MemoryModuleTypeMixin.Register("roar_sound_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<LivingEntity> ROAR_TARGET = OtherMemoryModuleTypeMixin.Register("roar_target");
	public static final MemoryModuleType<Unit> SONIC_BOOM_COOLDOWN = MemoryModuleTypeMixin.Register("sonic_boom_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SONIC_BOOM_SOUND_COOLDOWN = MemoryModuleTypeMixin.Register("sonic_boom_sound_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SONIC_BOOM_SOUND_DELAY = MemoryModuleTypeMixin.Register("sonic_boom_sound_delay", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> VIBRATION_COOLDOWN = MemoryModuleTypeMixin.Register("vibration_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> RECENT_PROJECTILE = MemoryModuleTypeMixin.Register("recent_projectile", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> TOUCH_COOLDOWN = MemoryModuleTypeMixin.Register("touch_cooldown", Codec.unit(Unit.INSTANCE));
}
