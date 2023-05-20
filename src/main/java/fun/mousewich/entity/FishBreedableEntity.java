package fun.mousewich.entity;

import net.minecraft.entity.passive.FishEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public interface FishBreedableEntity {
	int BREEDING_COOLDOWN = 6000;
	ServerPlayerEntity getLovingPlayer();
	boolean isInLove();
	void resetLoveTicks();
	default boolean canBreedWith(FishEntity other) {
		if (other == this) return false;
		if (other.getClass() != this.getClass()) return false;
		return this.isInLove() && ((FishBreedableEntity)other).isInLove();
	}
	void setBreedingAge(int age);
	void breed(ServerWorld world, FishEntity other);
	default boolean canBreedWith(FishBreedableEntity other) {
		if (other == this) return false;
		if (other.getClass() != this.getClass()) return false;
		return this.isInLove() && other.isInLove();
	}
}
