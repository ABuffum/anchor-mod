package fun.mousewich.gen.loot;

import fun.mousewich.ModId;
import fun.mousewich.mixins.LootTablesInvoker;
import net.minecraft.util.Identifier;

public class ModLootTables {
	//Sheep
	public static final Identifier BEIGE_SHEEP_ENTITY = register("entities/sheep/beige");
	public static final Identifier BURGUNDY_SHEEP_ENTITY = register("entities/sheep/burgundy");
	public static final Identifier LAVENDER_SHEEP_ENTITY = register("entities/sheep/lavender");
	public static final Identifier MINT_SHEEP_ENTITY = register("entities/sheep/mint");
	//Archaeology
	public static final Identifier DESERT_WELL_ARCHAEOLOGY = register("minecraft:archaeology/desert_well");
	public static final Identifier DESERT_PYRAMID_ARCHAEOLOGY = register("minecraft:archaeology/desert_pyramid");
	public static final Identifier TRAIL_RUINS_ARCHAEOLOGY = register("minecraft:archaeology/trail_ruins");
	public static final Identifier OCEAN_RUIN_WARM_ARCHAEOLOGY = register("minecraft:archaeology/ocean_ruin_warm");
	public static final Identifier OCEAN_RUIN_COLD_ARCHAEOLOGY = register("minecraft:archaeology/ocean_ruin_cold");

	public static void Initialize() {
		Identifier[] identifiers = new Identifier[] {
				//Sheep
				BEIGE_SHEEP_ENTITY, BURGUNDY_SHEEP_ENTITY, LAVENDER_SHEEP_ENTITY, MINT_SHEEP_ENTITY,
				//Archaeology
				DESERT_WELL_ARCHAEOLOGY, DESERT_PYRAMID_ARCHAEOLOGY, TRAIL_RUINS_ARCHAEOLOGY,
				OCEAN_RUIN_WARM_ARCHAEOLOGY, OCEAN_RUIN_COLD_ARCHAEOLOGY
		};
	}

	private static Identifier register(String id) { return LootTablesInvoker.InvokeRegisterLootTable(ModId.ID(id)); }
}
