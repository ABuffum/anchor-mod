package haven.gen.features;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Function6;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import haven.util.CollectionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.*;

public class SculkVeinFeatureConfig implements FeatureConfig {
	public static final Codec<SculkVeinFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(
				Codec.intRange(1, 64).fieldOf("search_range").orElse(10)
						.forGetter((glowLichenFeatureConfig) -> glowLichenFeatureConfig.searchRange),
				Codec.BOOL.fieldOf("can_place_on_floor").orElse(false)
						.forGetter((glowLichenFeatureConfig) -> glowLichenFeatureConfig.placeOnFloor),
				Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false)
						.forGetter((glowLichenFeatureConfig) -> glowLichenFeatureConfig.placeOnCeiling),
				Codec.BOOL.fieldOf("can_place_on_wall").orElse(false)
						.forGetter((glowLichenFeatureConfig) -> glowLichenFeatureConfig.placeOnWalls),
				Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(0.5F)
						.forGetter((glowLichenFeatureConfig) -> glowLichenFeatureConfig.spreadChance),
				BlockState.CODEC.listOf().fieldOf("can_be_placed_on")
						.forGetter((glowLichenFeatureConfig) -> new ArrayList(glowLichenFeatureConfig.canPlaceOn)))
				.apply(instance, SculkVeinFeatureConfig::new);
	});
	public final int searchRange;
	public final boolean placeOnFloor;
	public final boolean placeOnCeiling;
	public final boolean placeOnWalls;
	public final float spreadChance;
	public final List<BlockState> canPlaceOn;
	public final List<Direction> directions;

	public SculkVeinFeatureConfig(int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, float spreadChance, List<BlockState> canPlaceOn) {
		this.searchRange = searchRange;
		this.placeOnFloor = placeOnFloor;
		this.placeOnCeiling = placeOnCeiling;
		this.placeOnWalls = placeOnWalls;
		this.spreadChance = spreadChance;
		this.canPlaceOn = canPlaceOn;
		List<Direction> list = Lists.newArrayList();
		if (placeOnCeiling) list.add(Direction.UP);
		if (placeOnFloor) list.add(Direction.DOWN);
		if (placeOnWalls) {
			Direction.Type var10000 = Direction.Type.HORIZONTAL;
			Objects.requireNonNull(list);
			var10000.forEach(list::add);
		}
		this.directions = Collections.unmodifiableList(list);
	}

	public List<Direction> shuffleDirections(Random random, Direction excluded) {
		return CollectionUtil.copyShuffled(this.directions.stream().filter(direction -> direction != excluded), random);
	}

	public List<Direction> shuffleDirections(Random random) {
		return CollectionUtil.copyShuffled(this.directions.stream(), random);
	}

	public boolean canGrowOn(Block block) {
		return this.canPlaceOn.stream().anyMatch((state) -> {
			return state.isOf(block);
		});
	}
}
