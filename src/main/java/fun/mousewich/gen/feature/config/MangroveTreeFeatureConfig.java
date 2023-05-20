package fun.mousewich.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.mousewich.gen.placer.MangroveRootPlacer;
import fun.mousewich.gen.placer.MangroveTrunkPlacer;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.size.FeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;

public class MangroveTreeFeatureConfig implements FeatureConfig {
	public static final Codec<MangroveTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					Codec.BOOL.fieldOf("is_tall").forGetter(config -> config.isTall))
			.apply(instance, MangroveTreeFeatureConfig::new));
	public final boolean isTall;
	public final MangroveTrunkPlacer trunkPlacer;
	public final MangroveRootPlacer rootPlacer;
	public final FeatureSize minimumSize;

	public MangroveTreeFeatureConfig(boolean isTall) {
		this.isTall = isTall;
		if (this.isTall) {
			this.trunkPlacer = new MangroveTrunkPlacer(4, 1, 9);
			this.rootPlacer = new MangroveRootPlacer(UniformIntProvider.create(3, 7));
			this.minimumSize = new TwoLayersFeatureSize(3, 0, 2);
		}
		else {
			this.trunkPlacer = new MangroveTrunkPlacer(2, 1, 4);
			this.rootPlacer = new MangroveRootPlacer(UniformIntProvider.create(1, 3));
			this.minimumSize = new TwoLayersFeatureSize(2, 0, 2);
		}
	}
}