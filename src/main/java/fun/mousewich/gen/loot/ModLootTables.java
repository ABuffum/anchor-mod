package fun.mousewich.gen.loot;

import fun.mousewich.ModBase;
import fun.mousewich.mixins.LootTablesInvoker;
import net.minecraft.util.Identifier;

public class ModLootTables {
	public static final Identifier DESERT_WELL_ARCHAEOLOGY = register("minecraft:archaeology/desert_well");
	public static final Identifier DESERT_PYRAMID_ARCHAEOLOGY = register("minecraft:archaeology/desert_pyramid");
	public static final Identifier TRAIL_RUINS_ARCHAEOLOGY = register("minecraft:archaeology/trail_ruins");
	public static final Identifier OCEAN_RUIN_WARM_ARCHAEOLOGY = register("minecraft:archaeology/ocean_ruin_warm");
	public static final Identifier OCEAN_RUIN_COLD_ARCHAEOLOGY = register("minecraft:archaeology/ocean_ruin_cold");

	public static void Initialize() {
		Identifier[] identifiers = new Identifier[] {
				DESERT_WELL_ARCHAEOLOGY, DESERT_PYRAMID_ARCHAEOLOGY, TRAIL_RUINS_ARCHAEOLOGY,
				OCEAN_RUIN_WARM_ARCHAEOLOGY, OCEAN_RUIN_COLD_ARCHAEOLOGY
		};
	}

	private static Identifier register(String id) { return LootTablesInvoker.InvokeRegisterLootTable(ModBase.ID(id)); }
}
