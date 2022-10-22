package haven.entities;

import net.minecraft.entity.EntityPose;

import java.util.function.Supplier;

public class Poses {

	public static final Supplier<Integer> STANDING= EntityPose.STANDING::ordinal;
	public static final Supplier<Integer> FALL_FLYING = EntityPose.FALL_FLYING::ordinal;
	public static final Supplier<Integer> SLEEPING = EntityPose.SLEEPING::ordinal;
	public static final Supplier<Integer> SWIMMING = EntityPose.SWIMMING::ordinal;
	public static final Supplier<Integer> SPIN_ATTACK = EntityPose.SPIN_ATTACK::ordinal;
	public static final Supplier<Integer> CROUCHING = EntityPose.CROUCHING::ordinal;
	public static final Supplier<Integer> LONG_JUMPING = EntityPose.LONG_JUMPING::ordinal;
	public static final Supplier<Integer> DYING = EntityPose.DYING::ordinal;
	public static final Supplier<Integer> CROAKING = () -> EntityPose.values().length;
	public static final Supplier<Integer> USING_TONGUE = () -> CROAKING.get() + 1;
	public static final Supplier<Integer> ROARING = () -> USING_TONGUE.get() + 1;
	public static final Supplier<Integer> SNIFFING = () -> ROARING.get() + 1;
	public static final Supplier<Integer> EMERGING = () -> SNIFFING.get() + 1;
	public static final Supplier<Integer> DIGGING = () -> EMERGING.get() + 1;
}
