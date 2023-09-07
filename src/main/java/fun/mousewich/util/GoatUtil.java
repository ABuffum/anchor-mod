package fun.mousewich.util;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.item.horn.goat.GoatHornInstrument;
import fun.mousewich.item.horn.goat.GoatHornInstruments;
import fun.mousewich.item.horn.goat.GoatHornItem;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class GoatUtil {
	public static final TrackedData<Boolean> LEFT_HORN = DataTracker.registerData(GoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public static final TrackedData<Boolean> RIGHT_HORN = DataTracker.registerData(GoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(GoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public static boolean hasLeftHorn(LivingEntity goat) { return goat.getDataTracker().get(LEFT_HORN); }
	public static boolean hasRightHorn(LivingEntity goat) { return goat.getDataTracker().get(RIGHT_HORN); }

	public static boolean hasBothHorns(LivingEntity goat) {
		DataTracker tracker = goat.getDataTracker();
		return tracker.get(LEFT_HORN) && tracker.get(RIGHT_HORN);
	}
	public static boolean hasAnyHorns(LivingEntity goat) {
		DataTracker tracker = goat.getDataTracker();
		return tracker.get(LEFT_HORN) || tracker.get(RIGHT_HORN);
	}

	public static boolean dropHorn(LivingEntity goat) {
		DataTracker tracker = goat.getDataTracker();
		boolean bl = tracker.get(LEFT_HORN);
		boolean bl2 = tracker.get(RIGHT_HORN);
		if (!bl && !bl2) return false;
		Random random = goat.getRandom();
		TrackedData<Boolean> trackedData = !bl ? RIGHT_HORN : (!bl2 ? LEFT_HORN : (random.nextBoolean() ? LEFT_HORN : RIGHT_HORN));
		goat.getDataTracker().set(trackedData, false);
		Vec3d vec3d = goat.getPos();
		ItemStack itemStack = getGoatHornStack((GoatEntity)goat);
		double d = MathHelper.nextBetween(random, -0.2f, 0.2f);
		double e = MathHelper.nextBetween(random, 0.3f, 0.7f);
		double f = MathHelper.nextBetween(random, -0.2f, 0.2f);
		ItemEntity itemEntity = new ItemEntity(goat.world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), itemStack, d, e, f);
		goat.world.spawnEntity(itemEntity);
		return true;
	}

	public static ItemStack getGoatHornStack(GoatEntity goat) {
		Random random = new Random(goat.getUuid().hashCode());
		List<GoatHornInstrument> instruments = goat.isScreaming() ? GoatHornInstruments.SCREAMING_INSTRUMENTS : GoatHornInstruments.REGULAR_INSTRUMENTS;
		return GoatHornItem.getStackForInstrument(ModBase.GOAT_HORN, instruments.get(random.nextInt(instruments.size())));
	}

	public static boolean shouldSnapHorn(ServerWorld world, LivingEntity goat) {
		Vec3d vec3d = goat.getVelocity().multiply(1.0, 0.0, 1.0).normalize();
		BlockPos blockPos = new BlockPos(goat.getPos().add(vec3d));
		return world.getBlockState(blockPos).isIn(ModBlockTags.SNAPS_GOAT_HORN) || world.getBlockState(blockPos.up()).isIn(ModBlockTags.SNAPS_GOAT_HORN);
	}

	public static void addHorns(LivingEntity goat) {
		goat.getDataTracker().set(LEFT_HORN, true);
		goat.getDataTracker().set(RIGHT_HORN, true);
	}

	public static void removeHorns(LivingEntity goat) {
		goat.getDataTracker().set(LEFT_HORN, false);
		goat.getDataTracker().set(RIGHT_HORN, false);
	}

	public static SoundEvent getHornBreakSound(GoatEntity goat) {
		return goat.isScreaming() ? ModSoundEvents.ENTITY_GOAT_SCREAMING_HORN_BREAK : ModSoundEvents.ENTITY_GOAT_HORN_BREAK;
	}
}
