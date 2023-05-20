package fun.mousewich.util;

import fun.mousewich.ModBase;
import fun.mousewich.block.JukeboxBlockExtension;
import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JukeboxUtil {
	public static final Set<Item> MISSING_LENGTHS = new HashSet<>();
	public static final Map<Item, Integer> SONG_LENGTHS = new HashMap<>(Map.ofEntries(
			Map.entry(Items.MUSIC_DISC_11, 71),
			Map.entry(Items.MUSIC_DISC_13, 178),
			Map.entry(Items.MUSIC_DISC_CAT, 185),
			Map.entry(Items.MUSIC_DISC_BLOCKS, 345),
			Map.entry(Items.MUSIC_DISC_CHIRP, 185),
			Map.entry(Items.MUSIC_DISC_FAR, 179),
			Map.entry(Items.MUSIC_DISC_MALL, 197),
			Map.entry(Items.MUSIC_DISC_MELLOHI, 96),
			Map.entry(Items.MUSIC_DISC_STAL, 150),
			Map.entry(Items.MUSIC_DISC_STRAD, 188),
			Map.entry(Items.MUSIC_DISC_WARD, 251),
			Map.entry(Items.MUSIC_DISC_WAIT, 238),
			Map.entry(ModBase.MUSIC_DISC_OTHERSIDE, 195),
			Map.entry(Items.MUSIC_DISC_PIGSTEP, 149),
			Map.entry(ModBase.MUSIC_DISC_5, 178),
			Map.entry(ModBase.MUSIC_DISC_RELIC, 128)
	));
	public static void tick(World world, BlockPos pos, BlockState state, JukeboxBlockEntity blockEntity) {
		if (blockEntity instanceof JukeboxBlockExtension jukebox) {
			if (state.get(JukeboxBlock.HAS_RECORD) && jukebox.isPlaying() && blockEntity.getRecord().getItem() instanceof MusicDiscItem musicDiscItem) {
				jukebox.setTicksThisSecond(jukebox.getTicksThisSecond() + 1);
				if (isSongFinished(jukebox, musicDiscItem)) {
					world.emitGameEvent(ModGameEvent.JUKEBOX_STOP_PLAY, pos);
					jukebox.setPlaying(false);
				}
				else if (jukebox.getTicksThisSecond() >= 20) {
					jukebox.setTicksThisSecond(0);
					world.emitGameEvent(ModGameEvent.JUKEBOX_PLAY, pos);
					spawnNoteParticle(world, pos);
				}
				jukebox.setTickCount(jukebox.getTickCount() + 1);
			}
		}
	}

	private static void spawnNoteParticle(World world, BlockPos pos) {
		if (world instanceof ServerWorld serverWorld) {
			Vec3d vec3d = Vec3d.ofBottomCenter(pos).add(0.0, 1.2f, 0.0);
			float f = (float)world.getRandom().nextInt(4) / 24.0f;
			serverWorld.spawnParticles(ParticleTypes.NOTE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0, f, 0.0, 0.0, 1.0);
		}
	}

	private static int getLengthInSeconds(Item musicDisc) {
		int length = SONG_LENGTHS.getOrDefault(musicDisc, -1);
		if (length > 0) return length;
		Identifier id = Registry.ITEM.getId(musicDisc);
		if (id.getNamespace().equals("betterend")) {
			if (id.getPath().endsWith("strange_and_alien")) length = 266;
			else if (id.getPath().endsWith("grasping_at_stars")) length = 528;
			else if (id.getPath().endsWith("endseeker")) length = 461;
			else if (id.getPath().endsWith("eo_dracona")) length = 353;
		}
		if (length > 0) {
			SONG_LENGTHS.put(musicDisc, length);
			return length;
		}
		if (!MISSING_LENGTHS.contains(musicDisc)) {
			MISSING_LENGTHS.add(musicDisc);
			ModBase.LOGGER.warn("Missing sound length for music disc: " + id);
		}
		return 60;
	}
	private static long getSongLengthInTicks(Item musicDisc) { return getLengthInSeconds(musicDisc) * 20L; }
	private static boolean isSongFinished(JukeboxBlockExtension blockEntity, Item musicDisc) {
		return blockEntity.getTickCount() >= blockEntity.getRecordStartTick() + getSongLengthInTicks(musicDisc);
	}
}
