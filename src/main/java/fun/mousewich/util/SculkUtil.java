package fun.mousewich.util;

import fun.mousewich.event.ModGameEvent;
import fun.mousewich.event.ModVibrationListener;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.sound.ModSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SculkUtil {
	public static final Object2IntMap<GameEvent> FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), map -> {
		map.put(GameEvent.STEP, 1);
		map.put(GameEvent.FLAP, 2);
		map.put(GameEvent.SWIM, 3);
		map.put(GameEvent.PROJECTILE_LAND, 2);
		map.put(GameEvent.HIT_GROUND, 2);
		map.put(GameEvent.SPLASH, 2);
		map.put(ModGameEvent.ITEM_INTERACT_FINISH, 3);
		map.put(GameEvent.PROJECTILE_SHOOT, 3);
		map.put(ModGameEvent.INSTRUMENT_PLAY, 3);
		map.put(ModGameEvent.ENTITY_ROAR, 4);
		map.put(ModGameEvent.ENTITY_SHAKE, 4);
		map.put(ModGameEvent.ELYTRA_GLIDE, 4);
		map.put(ModGameEvent.ENTITY_DISMOUNT, 5);
		map.put(GameEvent.EQUIP, 5);
		map.put(ModGameEvent.ENTITY_INTERACT, 6);
		map.put(GameEvent.SHEAR, 6);
		map.put(ModGameEvent.ENTITY_MOUNT, 6);
		map.put(GameEvent.ENTITY_DAMAGED, 7);
		map.put(ModGameEvent.DRINK, 8);
		map.put(GameEvent.EAT, 8);
		map.put(GameEvent.CONTAINER_CLOSE, 9);
		map.put(GameEvent.BLOCK_CLOSE, 9);
		map.put(ModGameEvent.BLOCK_DEACTIVATE, 9);
		map.put(GameEvent.BLOCK_DETACH, 9);
		map.put(GameEvent.CONTAINER_OPEN, 10);
		map.put(GameEvent.BLOCK_OPEN, 10);
		map.put(ModGameEvent.BLOCK_ACTIVATE, 10);
		map.put(GameEvent.BLOCK_ATTACH, 10);
		map.put(GameEvent.PRIME_FUSE, 10);
		map.put(ModGameEvent.NOTE_BLOCK_PLAY, 10);
		map.put(GameEvent.BLOCK_CHANGE, 11);
		map.put(GameEvent.BLOCK_DESTROY, 12);
		map.put(GameEvent.FLUID_PICKUP, 12);
		map.put(GameEvent.BLOCK_PLACE, 13);
		map.put(GameEvent.FLUID_PLACE, 13);
		map.put(GameEvent.ENTITY_PLACE, 14);
		map.put(GameEvent.LIGHTNING_STRIKE, 14);
		map.put(ModGameEvent.TELEPORT, 14);
		map.put(ModGameEvent.ENTITY_DIE, 15);
		map.put(GameEvent.EXPLODE, 15);
		map.put(GameEvent.DISPENSE_FAIL, 10);
		map.put(GameEvent.ENTITY_KILLED, 13);
		map.put(GameEvent.PISTON_CONTRACT, 14);
		map.put(GameEvent.PISTON_EXTEND, 15);
		map.put(ModGameEvent.ITEM_INTERACT_START, 15);
		for (int i = 1; i <= 15; ++i) {
			map.put(ModVibrationListener.method_49881(i), i);
		}
	}));

	public static int getPower(float distance, int range) {
		double d = (double)distance / (double)range;
		return Math.max(1, 15 - MathHelper.floor(d * 15.0));
	}

	public static boolean isOccluded(World world, Vec3d start, Vec3d end) {
		Vec3d vec3d = new Vec3d((double)MathHelper.floor(start.x) + 0.5, (double)MathHelper.floor(start.y) + 0.5, (double)MathHelper.floor(start.z) + 0.5);
		Vec3d vec3d2 = new Vec3d((double)MathHelper.floor(end.x) + 0.5, (double)MathHelper.floor(end.y) + 0.5, (double)MathHelper.floor(end.z) + 0.5);
		for (Direction direction : Direction.values()) {
			Vec3d vec3d3 = VectorUtil.withBias(vec3d, direction, 1.0E-5f);
			if (world.raycast(new BlockStateRaycastContext(vec3d3, vec3d2, state -> state.isIn(BlockTags.OCCLUDES_VIBRATION_SIGNALS))).getType() == HitResult.Type.BLOCK) continue;
			return false;
		}
		return true;
	}

	public static SculkSensorPhase getPhase(BlockState state) { return state.get(Properties.SCULK_SENSOR_PHASE); }
	public static boolean isInactive(BlockState state) { return getPhase(state) == SculkSensorPhase.INACTIVE; }

	public static void updateNeighbors(World world, BlockPos pos, Block block) {
		world.updateNeighborsAlways(pos, block);
		world.updateNeighborsAlways(pos.down(), block);
	}
	public static void updateNeighbors(World world, BlockPos pos) {
		updateNeighbors(world, pos, Blocks.SCULK_SENSOR);
	}
	private static void updateNeighbors(World world, BlockPos pos, BlockState blockState) {
		updateNeighbors(world, pos, blockState.getBlock());
	}

	public static void setActive(Entity entity, World world, BlockPos pos, BlockState state, int power) {
		world.setBlockState(pos, state.with(Properties.SCULK_SENSOR_PHASE, SculkSensorPhase.ACTIVE).with(Properties.POWER, power), Block.NOTIFY_ALL);
		world.getBlockTickScheduler().schedule(pos, state.getBlock(), 40);
		updateNeighbors(world, pos);
		world.emitGameEvent(entity, ModGameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (!state.get(Properties.WATERLOGGED)) {
			world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
		}
	}

	private static final float[] field_43248 = Util.make(new float[16], fs -> {
		int[] is = new int[]{0, 0, 2, 4, 6, 7, 9, 10, 12, 14, 15, 18, 19, 21, 22, 24};
		for (int i = 0; i < 16; ++i) fs[i] = (float)Math.pow(2.0, (is[i] - 12.0) / 12.0);
	});
	public static void method_49822(World world, BlockPos blockPos, int i) {
		for (Direction direction : Direction.values()) {
			BlockPos blockPos2 = blockPos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos2);
			if (!blockState.isIn(ModBlockTags.VIBRATION_RESONATORS)) continue;
			world.emitGameEvent(ModVibrationListener.method_49881(i), blockPos2);
			float f = field_43248[i];
			if (blockState.isIn(ModBlockTags.VIBRATION_RESONATORS_ECHO)) {
				world.playSound(null, blockPos2, ModSoundEvents.BLOCK_ECHO_BLOCK_RESONATE, SoundCategory.BLOCKS, 1.0f, f);
			}
			else {
				world.playSound(null, blockPos2, ModSoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1.0f, f);
			}
		}
	}

	public static void setActive(Entity entity, World world, BlockPos pos, BlockState state, int power, int i) {
		world.setBlockState(pos, state.with(Properties.SCULK_SENSOR_PHASE, SculkSensorPhase.ACTIVE).with(Properties.POWER, power), Block.NOTIFY_ALL);
		world.getBlockTickScheduler().schedule(pos, state.getBlock(), 40);
		updateNeighbors(world, pos, state);
		method_49822(world, pos, i);
		world.emitGameEvent(entity, ModGameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (!state.get(Properties.WATERLOGGED)) {
			world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.2f + 0.8f);
		}
	}
}
