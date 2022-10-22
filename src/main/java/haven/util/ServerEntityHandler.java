package haven.util;

import haven.entities.hostile.warden.WardenEntity;
import haven.mixins.server.ServerWorldAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.entity.EntityHandler;
import net.minecraft.world.event.listener.EntityGameEventHandler;

public class ServerEntityHandler implements EntityHandler<Entity> {
	public final ServerWorld world;
	public final ServerWorldAccessor worldAccessor;
	public ServerEntityHandler(ServerWorld world) {
		this.world = world;
		this.worldAccessor = (ServerWorldAccessor)this.world;
	}

	@Override
	public void create(Entity entity) { }

	@Override
	public void destroy(Entity entity) { this.world.getScoreboard().resetEntityScore(entity); }

	@Override
	public void startTicking(Entity entity) { this.worldAccessor.getEntityList().add(entity); }

	@Override
	public void stopTicking(Entity entity) { this.worldAccessor.getEntityList().remove(entity); }

	@Override
	public void startTracking(Entity entity) {
		this.world.getChunkManager().loadEntity(entity);
		if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
			this.worldAccessor.getPlayers().add(serverPlayerEntity);
			this.world.updateSleepingPlayers();
		}
		if (entity instanceof MobEntity mobEntity) {
			this.worldAccessor.getLoadedMobs().add(mobEntity);
		}
		if (entity instanceof EnderDragonEntity enderDragonEntity) {
			for (EnderDragonPart enderDragonPart : enderDragonEntity.getBodyParts()) {
				this.worldAccessor.getDragonParts().put(enderDragonPart.getId(), enderDragonPart);
			}
		}
		if (entity instanceof WardenEntity wardenEntity) {
			wardenEntity.updateEventHandler(EntityGameEventHandler::onEntitySetPos);
		}
	}

	@Override
	public void stopTracking(Entity entity) {
		this.world.getChunkManager().unloadEntity(entity);
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
			this.worldAccessor.getPlayers().remove(serverPlayerEntity);
			this.world.updateSleepingPlayers();
		}
		if (entity instanceof MobEntity mobEntity) {
			this.worldAccessor.getLoadedMobs().remove(mobEntity);
		}
		if (entity instanceof EnderDragonEntity enderDragonEntity) {
			for (EnderDragonPart enderDragonPart : enderDragonEntity.getBodyParts()) {
				this.worldAccessor.getDragonParts().remove(enderDragonPart.getId());
			}
		}
		if (entity instanceof WardenEntity wardenEntity) {
			wardenEntity.updateEventHandler(EntityGameEventHandler::onEntityRemoval);
		}
	}

	//@Override
	public void updateLoadStatus(Entity entity) {
		if (entity instanceof WardenEntity wardenEntity) {
			wardenEntity.updateEventHandler(EntityGameEventHandler::onEntitySetPos);
		}
	}
}