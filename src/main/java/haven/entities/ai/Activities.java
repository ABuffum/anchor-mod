package haven.entities.ai;

import haven.mixins.entities.ai.ActivityInvoker;
import net.minecraft.entity.ai.brain.Activity;

public class Activities {
	public static final Activity SNIFF = ActivityInvoker.Register("sniff");
	public static final Activity INVESTIGATE = ActivityInvoker.Register("investigate");
	public static final Activity ROAR = ActivityInvoker.Register("roar");
	public static final Activity EMERGE = ActivityInvoker.Register("emerge");
	public static final Activity DIG = ActivityInvoker.Register("dig");
}
