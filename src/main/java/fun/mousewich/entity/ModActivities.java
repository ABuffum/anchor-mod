package fun.mousewich.entity;

import fun.mousewich.mixins.entity.ai.ActivityInvoker;
import net.minecraft.entity.ai.brain.Activity;

public class ModActivities {
	public static final Activity TONGUE = ActivityInvoker.Register("tongue");
	public static final Activity SWIM = ActivityInvoker.Register("swim");
	public static final Activity LAY_SPAWN = ActivityInvoker.Register("lay_spawn");
	public static final Activity SNIFF = ActivityInvoker.Register("sniff");
	public static final Activity INVESTIGATE = ActivityInvoker.Register("investigate");
	public static final Activity ROAR = ActivityInvoker.Register("roar");
	public static final Activity EMERGE = ActivityInvoker.Register("emerge");
	public static final Activity DIG = ActivityInvoker.Register("dig");
	public static void Initialize() { }
}
