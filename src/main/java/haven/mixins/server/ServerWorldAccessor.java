package haven.mixins.server;

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
	public EntityList getEntityList();
	@Accessor("players")
	public List<ServerPlayerEntity> getPlayers();
	@Accessor("loadedMobs")
	public Set<MobEntity> getLoadedMobs();
	@Accessor("dragonParts")
	public Int2ObjectMap<EnderDragonPart> getDragonParts();
	@Accessor("idleTimeout")
	public int getIdleTimeout();
}
