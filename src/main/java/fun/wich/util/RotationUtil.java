package fun.wich.util;

import net.minecraft.util.math.Direction;

import java.util.Optional;

public class RotationUtil {
	private static final int MAX = 15;
	private static final int NORTH = 0;
	private static final int EAST = 4;
	private static final int SOUTH = 8;
	private static final int WEST = 12;
	public static int getMax() { return MAX; }
	public static int fromDirection(Direction direction) {
		return direction.getAxis().isVertical() ? 0 : direction.getOpposite().getHorizontal() * 4;
	}
	public static Optional<Direction> toDirection(int rotation) {
		Direction direction = switch (rotation) {
			case NORTH -> Direction.NORTH;
			case EAST -> Direction.EAST;
			case SOUTH -> Direction.SOUTH;
			case WEST -> Direction.WEST;
			default -> null;
		};
		return Optional.ofNullable(direction);
	}
}
