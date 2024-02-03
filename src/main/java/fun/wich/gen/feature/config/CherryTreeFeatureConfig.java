package fun.wich.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class CherryTreeFeatureConfig implements FeatureConfig {
	public static final Codec<CherryTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.fieldOf("bees").forGetter(config -> config.bees))
			.apply(instance, CherryTreeFeatureConfig::new));
	public final boolean bees;
	public CherryTreeFeatureConfig(boolean bees) { this.bees = bees; }
}