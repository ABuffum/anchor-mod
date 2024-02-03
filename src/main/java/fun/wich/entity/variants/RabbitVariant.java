package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public enum RabbitVariant {
	BROWN("brown", RabbitEntity.BROWN_TYPE, "minecraft:textures/entity/rabbit/brown.png"),
	WHITE("white", RabbitEntity.WHITE_TYPE, "minecraft:textures/entity/rabbit/white.png"),
	BLACK("black", RabbitEntity.BLACK_TYPE, "minecraft:textures/entity/rabbit/black.png"),
	GOLD("gold", RabbitEntity.GOLD_TYPE, "minecraft:textures/entity/rabbit/gold.png"),
	SALT("salt", RabbitEntity.SALT_TYPE, "minecraft:textures/entity/rabbit/salt.png"),
	WHITE_SPLOTCHED("white_splotched", RabbitEntity.WHITE_SPOTTED_TYPE, "minecraft:textures/entity/rabbit/white_splotched.png"),
	//Mod Variants
	VESTED("vested", -1, "textures/entity/rabbit/vested.png"),
	//Special Vanilla Skins
	TOAST("toast", -1, "minecraft:textures/entity/rabbit/toast.png"),
	CAERBANNOG("caerbannog", RabbitEntity.KILLER_BUNNY_TYPE, "minecraft:textures/entity/rabbit/caerbannog.png");

	public final String name;
	public final int value;
	public final Identifier texture;
	RabbitVariant(String name, int value, String texture) {
		this.name = name;
		this.value = value;
		this.texture = ModId.ID(texture);
	}
	public static RabbitVariant get(RabbitEntity entity) {
		String string = Formatting.strip(entity.getName().getString());
		if ("Toast".equals(string)) return TOAST;
		int type = entity.getRabbitType();
		if (type == RabbitEntity.KILLER_BUNNY_TYPE) return CAERBANNOG;
		RabbitVariant[] variants = values();
		return (type < 0 || type > variants.length - 2) ? BROWN : variants[type];
	}
}
