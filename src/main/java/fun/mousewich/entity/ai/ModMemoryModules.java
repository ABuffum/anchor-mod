package fun.mousewich.entity.ai;

import com.mojang.serialization.Codec;
import fun.mousewich.mixins.entity.ai.MemoryModuleTypeCodecInvoker;
import fun.mousewich.mixins.entity.ai.MemoryModuleTypeInvoker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;

public class ModMemoryModules {
	public static final MemoryModuleType<FishEntity> FISH_BREED_TARGET = MemoryModuleTypeInvoker.RegisterID("fish_breed_target");
	public static final MemoryModuleType<Integer> GAZE_COOLDOWN_TICKS = MemoryModuleTypeCodecInvoker.Register("gaze_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<UUID> LIKED_PLAYER = MemoryModuleTypeCodecInvoker.Register("liked_player", DynamicSerializableUuid.CODEC);
	public static final MemoryModuleType<GlobalPos> LIKED_NOTEBLOCK = MemoryModuleTypeCodecInvoker.Register("liked_noteblock", GlobalPos.CODEC);
	public static final MemoryModuleType<Integer> LIKED_NOTEBLOCK_COOLDOWN_TICKS = MemoryModuleTypeCodecInvoker.Register("liked_noteblock_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Integer> ITEM_PICKUP_COOLDOWN_TICKS = MemoryModuleTypeCodecInvoker.Register("item_pickup_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Unit> IS_IN_WATER = MemoryModuleTypeCodecInvoker.Register("is_in_water", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> IS_PREGNANT = MemoryModuleTypeCodecInvoker.Register("is_pregnant", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Boolean> IS_PANICKING = MemoryModuleTypeCodecInvoker.Register("is_panicking", Codec.BOOL);
	public static final MemoryModuleType<List<UUID>> UNREACHABLE_TONGUE_TARGETS = MemoryModuleTypeInvoker.RegisterID("unreachable_tongue_targets");
	public static final MemoryModuleType<LivingEntity> ROAR_TARGET = MemoryModuleTypeInvoker.RegisterID("roar_target");
	public static final MemoryModuleType<Unit> ROAR_SOUND_DELAY = MemoryModuleTypeCodecInvoker.Register("roar_sound_delay", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> ROAR_SOUND_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("roar_sound_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<BlockPos> DISTURBANCE_LOCATION = MemoryModuleTypeInvoker.RegisterID("disturbance_location");
	public static final MemoryModuleType<Unit> RECENT_PROJECTILE = MemoryModuleTypeCodecInvoker.Register("recent_projectile", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> DIG_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("dig_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> IS_SNIFFING = MemoryModuleTypeCodecInvoker.Register("is_sniffing", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SNIFF_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("sniff_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> TOUCH_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("touch_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> VIBRATION_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("vibration_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> IS_EMERGING = MemoryModuleTypeCodecInvoker.Register("is_emerging", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SONIC_BOOM_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("sonic_boom_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SONIC_BOOM_SOUND_COOLDOWN = MemoryModuleTypeCodecInvoker.Register("sonic_boom_sound_cooldown", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<Unit> SONIC_BOOM_SOUND_DELAY = MemoryModuleTypeCodecInvoker.Register("sonic_boom_sound_delay", Codec.unit(Unit.INSTANCE));
	public static final MemoryModuleType<List<BlockPos>> SNIFFER_EXPLORED_POSITIONS = MemoryModuleTypeCodecInvoker.Register("sniffer_explored_positions", Codec.list(BlockPos.CODEC));
	public static final MemoryModuleType<BlockPos> SNIFFER_SNIFFING_TARGET = MemoryModuleTypeInvoker.RegisterID("sniffer_sniffing_target");
	public static final MemoryModuleType<Boolean> SNIFFER_DIGGING = MemoryModuleTypeInvoker.RegisterID("sniffer_digging");
	public static final MemoryModuleType<Boolean> SNIFFER_HAPPY = MemoryModuleTypeInvoker.RegisterID("sniffer_happy");
	public static void Initialize() { }
}
