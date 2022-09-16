package haven.entities.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class AngelBatEntity extends BatEntity {
	public AngelBatEntity(EntityType<? extends BatEntity> entityType, World world) {
		super(entityType, world);
	}

	public static boolean CanSpawn(EntityType<AngelBatEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return false;
	}
}
