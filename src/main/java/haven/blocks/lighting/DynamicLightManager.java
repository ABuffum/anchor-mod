package haven.blocks.lighting;

import haven.ModBase;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DynamicLightManager {

	private static DynamicLightManager INSTANCE;
	public static DynamicLightManager getInstance() {
		if (INSTANCE == null) INSTANCE = new DynamicLightManager();
		return INSTANCE;
	}

	public DynamicLightManager() { INSTANCE = this; }

	public static final Map<Block, Block> vanillaBlocksToLitBlocksMap = new HashMap<>();

	/**
	 * This Map contains a List of DynamicLightSourceContainer for each World. Since
	 * the client can only be in a single World, the other Lists just float idle
	 * when unused.
	 */
	private final ConcurrentHashMap<World, ConcurrentLinkedQueue<DynamicLightContainer>> worldLightsMap = new ConcurrentHashMap<>();

	public static void addLight(DynamicLightSource light) {
		Entity entity = light.getEntity();
		if (entity != null) {
			ModBase.LOGGER.info("Calling addLightSource on entity {}", entity);
			if (entity.isAlive()) {
				DynamicLightContainer newLightContainer = new DynamicLightContainer(light);
				ConcurrentLinkedQueue<DynamicLightContainer> lightList = getInstance().worldLightsMap.get(entity.world);
				if (lightList != null) {
					if (!lightList.contains(newLightContainer)) {
						ModBase.LOGGER.info("Successfully registered DynamicLight on Entity: {} in list {}", newLightContainer.getLightSource().getEntity(), lightList);
						lightList.add(newLightContainer);
					} else {
						ModBase.LOGGER.info("Cannot add Dynamic Light: Attachment Entity is already registered!");
					}
				} else {
					lightList = new ConcurrentLinkedQueue<>();
					lightList.add(newLightContainer);
					getInstance().worldLightsMap.put(entity.world, lightList);
				}
			} else {
				ModBase.LOGGER.error("Cannot add Dynamic Light: Attachment Entity {} is dead", entity);
			}
		} else {
			ModBase.LOGGER.error("Cannot add Dynamic Light: Attachment Entity is null!");
		}
	}
	public static void removeLight(DynamicLightSource light) {
		Entity entity;
		if (light != null && (entity = light.getEntity()) != null) {
			World world = entity.world;
			if (world != null) {
				DynamicLightContainer iterContainer = null;
				ConcurrentLinkedQueue<DynamicLightContainer> lightList = getInstance().worldLightsMap.get(world);
				if (lightList != null) {
					Iterator<DynamicLightContainer> iter = lightList.iterator();
					while (iter.hasNext()) {
						iterContainer = iter.next();
						if (iterContainer.getLightSource().equals(light)) {
							iter.remove();
							break;
						}
					}
					if (iterContainer != null) {
						ModBase.LOGGER.info("Removing Dynamic Light attached to {}", entity);
						iterContainer.removeLight(world);
					}
				}
			}
		}
	}

	public void tick(ServerWorld serverWorld) {
		ConcurrentLinkedQueue<DynamicLightContainer> worldLights = worldLightsMap.get(serverWorld);
		if (worldLights != null) {
			Iterator<DynamicLightContainer> iter = worldLights.iterator();
			while (iter.hasNext()) {
				DynamicLightContainer tickedLightContainer = iter.next();
				if (tickedLightContainer.onUpdate()) {
					iter.remove();
					tickedLightContainer.removeLight(serverWorld);
					ModBase.LOGGER.debug("Dynamic Lights killing off LightSource on dead Entity: " + tickedLightContainer.getLightSource().getEntity());
				}
			}
		}
	}

	/**
	 * in order to cleanup orphaned dynamic light blocks, they tick randomly and kill themselves unless they are a known dynamic light source
	 */
	public static boolean isUnknownLitPosition(ServerWorld serverWorld, BlockPos blockPos) {
		ConcurrentLinkedQueue<DynamicLightContainer> worldLights = getInstance().worldLightsMap.get(serverWorld);
		if (worldLights != null) {
			for (DynamicLightContainer light : worldLights) {
				if (!light.containsPos(blockPos)) return true;
			}
		}
		return false;
	}

	public static class DynamicLightContainer {
		private final DynamicLightSource lightSource;

		private BlockPos.Mutable prevPos;
		private BlockPos.Mutable curPos;

		public DynamicLightContainer(DynamicLightSource light) {
			lightSource = light;
			Entity entity = light.getEntity();
			prevPos = new BlockPos.Mutable();
			curPos = new BlockPos.Mutable().set(entity.getBlockPos());
		}

		/**
		 * Update passed on from the World tick. Checks for the Light Source Entity to
		 * be alive, and for it to have changed Coordinates. Marks it's current Block
		 * for Update if it has moved. When this method returns true, the Light Source
		 * Entity has died and it should be removed from the List!
		 *
		 * @return true when the Light Source has died, false otherwise
		 */
		public boolean onUpdate() {
			Entity entity = lightSource.getEntity();
			if (!entity.isAlive()) return true;
			if (hasEntityMoved(entity)) {
				removePreviousLight(entity.world);
				addLight(entity.world, lightSource.getLightLevel());
			}
			return false;
		}

		public boolean containsPos(BlockPos pos) {
			Entity entity = lightSource.getEntity();
			for (int i = 0; i <= Math.round(entity.getEyeHeight(entity.getPose())); i++) {
				if (pos.equals(curPos.offset(Direction.Axis.Y, i))) return true;
			}
			return false;
		}

		public BlockPos getPos() { return curPos; }

		public DynamicLightSource getLightSource() { return lightSource; }

		/**
		 * Checks for the Entity coordinates to have changed. Updates internal
		 * Coordinates to new position if so.
		 *
		 * @return true when Entities x, y or z changed, false otherwise
		 */
		private boolean hasEntityMoved(Entity ent) {
			// use yOffset so player positions are +1 y, at eye height
			BlockPos pos = ent.getBlockPos();
			if (pos != curPos.toImmutable()) {
				prevPos.set(curPos);
				curPos.set(pos);
				return true;
			}
			return false;
		}

		private void removeLight(World world, BlockPos pos) {
			BlockState blockState = world.getBlockState(pos);
			Block block = blockState.getBlock();
			for (Map.Entry<Block, Block> vanillaBlockToLitBlockEntry : vanillaBlocksToLitBlocksMap.entrySet()) {
				if (vanillaBlockToLitBlockEntry.getValue() == block) {
					Block outBlock = vanillaBlockToLitBlockEntry.getKey();
					BlockState out = outBlock.getDefaultState();
					if (outBlock instanceof Waterloggable) out = out.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
					if (outBlock instanceof LadderBlock) out = out.with(LadderBlock.FACING, blockState.get(LadderBlock.FACING));
					// previous block is lit, reset it to default block
					world.setBlockState(pos, out, 3);
				}
			}
		}

		private void removePreviousLight(World world) { removeLight(world, prevPos); }
		public void removeLight(World world) {
			// reset previous and current position light blocks if they exist
			removePreviousLight(world);
			//removeLight(world, curPos);
			repeat((BlockPos pos) -> removeLight(world, pos));
		}
		private interface AddRemoveFunc {
			public void apply(BlockPos pos);
		}

		private void repeat(AddRemoveFunc func) {
			Entity entity = lightSource.getEntity();
			for (int i = 0; i <= Math.round(entity.getEyeHeight(entity.getPose())); i++) {
				func.apply(curPos.offset(Direction.Axis.Y, i));
			}
		}

		private void addLight(World world, BlockPos pos) {
			// add light block on current position, depending on what type (air, water)
			BlockState blockState = world.getBlockState(pos);
			Block currentBlock = blockState.getBlock();
			for (Map.Entry<Block, Block> vanillaBlockToLitBlockEntry : vanillaBlocksToLitBlocksMap.entrySet()) {
				if (currentBlock == vanillaBlockToLitBlockEntry.getKey()) {
					Block outBlock = vanillaBlockToLitBlockEntry.getValue();
					BlockState out = outBlock.getDefaultState();
					if (currentBlock instanceof FluidBlock && blockState.get(FluidBlock.LEVEL) != 0) return;
					if (currentBlock instanceof Waterloggable) out = out.with(Properties.WATERLOGGED, blockState.get(Properties.WATERLOGGED));
					if (currentBlock instanceof LadderBlock) out = out.with(LadderBlock.FACING, blockState.get(LadderBlock.FACING));
					out = out.with(DynamicLitBlock.LUMINANCE, lightSource.getLightLevel());
					world.setBlockState(pos, out, 3);
					break;
				}
			}
		}

		private void addLight(World world, int lightLevel) {
			if (lightLevel < 0 || lightLevel > 15) return;
			//AddLight(world, curPos);
			repeat((BlockPos pos) -> this.addLight(world, pos));
		}

		@Override
		public boolean equals(Object obj) { return obj instanceof DynamicLightContainer other && other.lightSource == this.lightSource; }
		@Override
		public int hashCode() { return lightSource.getEntity().hashCode(); }
	}
}
