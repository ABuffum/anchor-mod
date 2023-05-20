package fun.mousewich.event;

import net.minecraft.world.event.PositionSourceType;

public class ModPositionSourceTypes {
	public static final PositionSourceType<ModEntityPositionSource> MOD_ENTITY = PositionSourceType.register("mod_entity", new ModEntityPositionSource.Type());
	public static void Initialize() {
		PositionSourceType<?>[] positionSourceTypes = new PositionSourceType<?>[] {
			MOD_ENTITY
		};
	}
}
