package fun.mousewich.gen.carver;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;

public class ModCarvers {
	public static final ConfiguredCarver<CaveCarverConfig> CAVE_EXTRA_UNDERGROUND = register("cave_extra_underground", Carver.CAVE.configure(
			new CaveCarverConfig(
					0.07f,
					UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(47)),
					UniformFloatProvider.create(0.1f, 0.9f),
					YOffset.aboveBottom(8),
					false,
					CarverDebugConfig.create(false, Blocks.OAK_BUTTON.getDefaultState()),
					UniformFloatProvider.create(0.7f, 1.4f),
					UniformFloatProvider.create(0.8f, 1.3f),
					UniformFloatProvider.create(-1.0f, -0.4f))));

	private static <WC extends CarverConfig> ConfiguredCarver<WC> register(String id, ConfiguredCarver<WC> configuredCarver) {
		return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, id, configuredCarver);
	}
}
