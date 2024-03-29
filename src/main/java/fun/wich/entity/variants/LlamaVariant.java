package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum LlamaVariant {
	CREAMY("creamy", "minecraft:textures/entity/llama/creamy.png"),
	WHITE("white", "minecraft:textures/entity/llama/white.png"),
	BROWN("brown", "minecraft:textures/entity/llama/brown.png"),
	GRAY("gray", "minecraft:textures/entity/llama/gray.png"),
	//Mod Variants
	MOCHA("mocha", "textures/entity/llama/mocha.png"),
	COCOA("cocoa", "textures/entity/llama/cocoa.png");

	public final String name;
	public final Identifier texture;
	LlamaVariant(String name, String texture) {
		this.name = name;
		this.texture = ModId.ID(texture);
	}
	public static LlamaVariant get(LlamaEntity entity) {
		LlamaVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getVariant(), 0, variants.length - 1)];
	}
}
