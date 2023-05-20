package fun.mousewich.gen.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;

import static fun.mousewich.gen.structures.ModStructures.*;

public class AncientCityGenerator {
	public static final StructurePool CITY_CENTER = new StructurePool(new Identifier("ancient_city/city_center"), new Identifier("empty"), ImmutableList.of(
			Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/city_center_1", ANCIENT_CITY_START_DEGRADATION), 1),
			Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/city_center_2", ANCIENT_CITY_START_DEGRADATION), 1),
			Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/city_center_3", ANCIENT_CITY_START_DEGRADATION), 1)),
			StructurePool.Projection.RIGID);

	public static void init() { AncientCityOutskirtsGenerator.init(); }
}
