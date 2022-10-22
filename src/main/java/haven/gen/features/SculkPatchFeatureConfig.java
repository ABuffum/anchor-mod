package haven.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public record SculkPatchFeatureConfig(int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float catalystChance) implements FeatureConfig {
	public static final Codec<SculkPatchFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					Codec.intRange(1, 32).fieldOf("charge_count").forGetter(config -> config.chargeCount),
					Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter(config -> config.amountPerCharge),
					Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter(config -> config.spreadAttempts),
					Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter(config -> config.growthRounds),
					Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter(config -> config.spreadRounds),
					IntProvider.VALUE_CODEC.fieldOf("extra_rare_growths").forGetter(config -> config.extraRareGrowths),
					Codec.floatRange(0.0f, 1.0f).fieldOf("catalyst_chance").forGetter(config -> config.catalystChance))
					.apply(instance, (chargeCount, amountPerCharge, spreadAttempts, growthRounds, spreadRounds, extraRareGrowths, catalystChance) ->
							new SculkPatchFeatureConfig((int)chargeCount, (int)amountPerCharge, (int)spreadAttempts, (int)growthRounds, (int)spreadAttempts, (IntProvider)extraRareGrowths, (float)catalystChance)));
}