package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum WolfVariant {
	DEFAULT("default", "minecraft:textures/entity/wolf/wolf.png", "minecraft:textures/entity/wolf/wolf_angry.png", "minecraft:textures/entity/wolf/wolf_tame.png"),
	BLACK("black", "textures/entity/wolf/black.png", "textures/entity/wolf/black_angry.png", "textures/entity/wolf/black_tame.png"),
	GRAY("gray", "textures/entity/wolf/gray.png", "textures/entity/wolf/gray_angry.png", "textures/entity/wolf/gray_tame.png"),
	BROWN("brown", "textures/entity/wolf/brown.png", "textures/entity/wolf/brown_angry.png", "textures/entity/wolf/brown_tame.png");

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(WolfEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(WolfEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Identifier texture;
	public final Identifier angryTexture;
	public final Identifier tameTexture;
	WolfVariant(String name, String texture, String angryTexture, String tameTexture) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.angryTexture = ModId.ID(angryTexture);
		this.tameTexture = ModId.ID(tameTexture);
	}
	public static WolfVariant get(WolfEntity entity) {
		WolfVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
	public static Identifier getTexture(WolfEntity entity) {
		WolfVariant variant = get(entity);
		if (variant != null) {
			if (entity.isTamed()) return variant.tameTexture;
			if (entity.hasAngerTime()) return variant.angryTexture;
			return variant.texture;
		}
		return null;
	}
}
