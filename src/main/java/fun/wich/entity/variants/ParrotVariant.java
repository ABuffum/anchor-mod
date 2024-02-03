package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum ParrotVariant {
	RED_BLUE("red_blue", "minecraft:textures/entity/parrot/parrot_red_blue.png"),
	BLUE("blue", "minecraft:textures/entity/parrot/parrot_blue.png"),
	GREEN("green", "minecraft:textures/entity/parrot/parrot_green.png"),
	YELLOW_BLUE("yellow_blue", "minecraft:textures/entity/parrot/parrot_yellow_blue.png"),
	GREY("grey", "minecraft:textures/entity/parrot/parrot_grey.png"),
	//Mod Variants
	GOLDEN("golden", "textures/entity/parrot/parrot_golden.png");

	public final String name;
	public final Identifier texture;
	ParrotVariant(String name, String texture) {
		this.name = name;
		this.texture = ModId.ID(texture);
	}
	public static ParrotVariant get(ParrotEntity entity) {
		ParrotVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getVariant(), 0, variants.length - 1)];
	}
}
