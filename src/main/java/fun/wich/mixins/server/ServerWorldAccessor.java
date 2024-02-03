package fun.wich.mixins.server;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Set;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {
	@Accessor("entityList")
	EntityList getEntityList();
	@Accessor("players")
	List<ServerPlayerEntity> getPlayers();
	@Accessor("loadedMobs")
	Set<MobEntity> getLoadedMobs();
	@Accessor("dragonParts")
	Int2ObjectMap<EnderDragonPart> getDragonParts();
	@Accessor("idleTimeout")
	int getIdleTimeout();
}
