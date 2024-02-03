package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum GoatVariant {
	DEFAULT("default", "minecraft:textures/entity/goat/goat.png"),
	DARK("dark", "textures/entity/goat/dark.png");

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(GoatEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(GoatEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(GoatEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Identifier texture;
	GoatVariant(String name, String texture) {
		this.name = name;
		this.texture = ModId.ID(texture);
	}
	public static GoatVariant get(GoatEntity entity) {
		GoatVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
}
