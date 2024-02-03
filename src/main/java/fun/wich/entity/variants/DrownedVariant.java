package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum DrownedVariant {
	DEFAULT("default", "minecraft:textures/entity/zombie/drowned.png", "minecraft:textures/entity/zombie/drowned_outer_layer.png"),
	BLACK("variant1", "textures/entity/zombie/drowned_variant_1.png", "textures/entity/zombie/drowned_variant_1_outer_layer.png"),
	GRAY("variant2", "textures/entity/zombie/drowned_variant_2.png", "textures/entity/zombie/drowned_variant_2_outer_layer.png");

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(DrownedEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(DrownedEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(DrownedEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Identifier texture;
	public final Identifier outer;
	DrownedVariant(String name, String texture, String outer) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.outer = ModId.ID(outer);
	}
	public static DrownedVariant get(DrownedEntity entity) {
		DrownedVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
}
