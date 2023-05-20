package fun.mousewich.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientMixinStore {
	public static float clientPlayerSneakSpeed;
}
