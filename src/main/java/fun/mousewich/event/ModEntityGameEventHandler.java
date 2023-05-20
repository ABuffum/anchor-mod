package fun.mousewich.event;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.event.listener.GameEventDispatcher;
import net.minecraft.world.event.listener.GameEventListener;

import java.util.function.Consumer;

public class ModEntityGameEventHandler<T extends GameEventListener> {
	private T listener;
	private ChunkSectionPos sectionPos;
	public ModEntityGameEventHandler(T listener) { this.listener = listener; }
	public void onEntitySetPosCallback(ServerWorld world) { this.onEntitySetPos(world); }
	public void setListener(T listener, World world) {
		if (this.listener == listener) return;
		if (world instanceof ServerWorld serverWorld) {
			updateDispatcher(serverWorld, this.sectionPos, dispatcher -> dispatcher.removeListener(this.listener));
			updateDispatcher(serverWorld, this.sectionPos, dispatcher -> dispatcher.addListener(listener));
		}
		this.listener = listener;
	}
	public T getListener() { return this.listener; }
	public void onEntityRemoval(ServerWorld world) {
		updateDispatcher(world, this.sectionPos, dispatcher -> dispatcher.removeListener(this.listener));
	}
	public void onEntitySetPos(ServerWorld world) {
		this.listener.getPositionSource().getPos(world).map(ChunkSectionPos::from).ifPresent(sectionPos -> {
			if (this.sectionPos == null || !this.sectionPos.equals(sectionPos)) {
				updateDispatcher(world, this.sectionPos, dispatcher -> dispatcher.removeListener(this.listener));
				this.sectionPos = sectionPos;
				updateDispatcher(world, this.sectionPos, dispatcher -> dispatcher.addListener(this.listener));
			}
		});
	}

	private static void updateDispatcher(WorldView world, ChunkSectionPos sectionPos, Consumer<GameEventDispatcher> dispatcherConsumer) {
		if (sectionPos == null) return;
		Chunk chunk = world.getChunk(sectionPos.getSectionX(), sectionPos.getSectionZ(), ChunkStatus.FULL, false);
		if (chunk != null) dispatcherConsumer.accept(chunk.getGameEventDispatcher(sectionPos.getSectionY()));
	}
}