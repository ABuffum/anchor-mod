package fun.mousewich.entity.ai;

import fun.mousewich.mixins.entity.ai.LandPathNodeMakerAccessor;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class ModLandPathNodeMaker extends LandPathNodeMaker {
	protected boolean canWalkOverFences;
	public void setCanWalkOverFences(boolean canWalkOverFences) { this.canWalkOverFences = canWalkOverFences; }
	public boolean canWalkOverFences() { return this.canWalkOverFences; }

	private double getStepHeight() { return Math.max(1.125, this.entity.stepHeight); }
	private PathNode getNodeWith(int x, int y, int z, PathNodeType type, float penalty) {
		PathNode pathNode = this.getNode(x, y, z);
		if (pathNode != null) {
			pathNode.type = type;
			pathNode.penalty = Math.max(pathNode.penalty, penalty);
		}
		return pathNode;
	}
	protected boolean isAmphibious() { return false; }
	private static boolean isBlocked(PathNodeType nodeType) {
		return nodeType == PathNodeType.FENCE || nodeType == PathNodeType.DOOR_WOOD_CLOSED || nodeType == PathNodeType.DOOR_IRON_CLOSED;
	}
	private PathNode getBlockedNode(int x, int y, int z) {
		PathNode pathNode = this.getNode(x, y, z);
		if (pathNode != null) {
			pathNode.type = PathNodeType.BLOCKED;
			pathNode.penalty = -1.0f;
		}
		return pathNode;
	}

	protected double getFeetY(BlockPos pos) {
		return LandPathNodeMaker.getFeetY(this.cachedWorld, pos);
	}

	protected PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY, Direction direction, PathNodeType nodeType) {
		double h;
		double g;
		LandPathNodeMakerAccessor lpnma = (LandPathNodeMakerAccessor)this;
		PathNode pathNode = null;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		double d = this.getFeetY(mutable.set(x, y, z));
		if (d - prevFeetY > this.getStepHeight()) return null;
		PathNodeType pathNodeType = this.getNodeType(this.entity, x, y, z);
		float f = this.entity.getPathfindingPenalty(pathNodeType);
		double e = (double)this.entity.getWidth() / 2.0;
		if (f >= 0.0f) pathNode = this.getNodeWith(x, y, z, pathNodeType, f);
		if (isBlocked(nodeType) && pathNode != null && pathNode.penalty >= 0.0f && !lpnma.IsBlocked(pathNode)) pathNode = null;
		if (pathNodeType == PathNodeType.WALKABLE || this.isAmphibious() && pathNodeType == PathNodeType.WATER) return pathNode;
		if ((pathNode == null || pathNode.penalty < 0.0f) && maxYStep > 0
				&& (pathNodeType != PathNodeType.FENCE || this.canWalkOverFences())
				&& pathNodeType != PathNodeType.UNPASSABLE_RAIL && pathNodeType != PathNodeType.TRAPDOOR
				&& pathNodeType != PathNodeType.POWDER_SNOW
				&& (pathNode = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY, direction, nodeType)) != null
				&& (pathNode.type == PathNodeType.OPEN || pathNode.type == PathNodeType.WALKABLE)
				&& this.entity.getWidth() < 1.0f
				&& lpnma.CheckBoxCollision(new Box((g = (double)(x - direction.getOffsetX()) + 0.5) - e, LandPathNodeMaker.getFeetY(this.cachedWorld, mutable.set(g, y + 1, h = (double)(z - direction.getOffsetZ()) + 0.5)) + 0.001, h - e, g + e, (double)this.entity.getHeight() + LandPathNodeMaker.getFeetY(this.cachedWorld, mutable.set(pathNode.x, pathNode.y, (double)pathNode.z)) - 0.002, h + e))) pathNode = null;
		if (!this.isAmphibious() && pathNodeType == PathNodeType.WATER && !this.canSwim()) {
			if (this.getNodeType(this.entity, x, y - 1, z) != PathNodeType.WATER) return pathNode;
			while (y > this.entity.world.getBottomY()) {
				if ((pathNodeType = this.getNodeType(this.entity, x, --y, z)) == PathNodeType.WATER) {
					pathNode = this.getNodeWith(x, y, z, pathNodeType, this.entity.getPathfindingPenalty(pathNodeType));
					continue;
				}
				return pathNode;
			}
		}
		if (pathNodeType == PathNodeType.OPEN) {
			int i = 0;
			int j = y;
			while (pathNodeType == PathNodeType.OPEN) {
				if (--y < this.entity.world.getBottomY()) return this.getBlockedNode(x, j, z);
				if (i++ >= this.entity.getSafeFallDistance()) return this.getBlockedNode(x, y, z);
				pathNodeType = this.getNodeType(this.entity, x, y, z);
				f = this.entity.getPathfindingPenalty(pathNodeType);
				if (pathNodeType != PathNodeType.OPEN && f >= 0.0f) {
					pathNode = this.getNodeWith(x, y, z, pathNodeType, f);
					break;
				}
				if (!(f < 0.0f)) continue;
				return this.getBlockedNode(x, y, z);
			}
		}
		if (isBlocked(pathNodeType) && pathNode == null && (pathNode = this.getNode(x, y, z)) != null) {
			pathNode.visited = true;
			pathNode.type = pathNodeType;
			pathNode.penalty = pathNodeType.getDefaultPenalty();
		}
		return pathNode;
	}
}
