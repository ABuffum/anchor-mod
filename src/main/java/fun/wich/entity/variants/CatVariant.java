package fun.wich.entity.variants;

import fun.wich.ModId;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum CatVariant {
	TABBY("tabby", "minecraft:textures/entity/cat/tabby.png"),
	BLACK("black", "minecraft:textures/entity/cat/black.png"),
	RED("red", "minecraft:textures/entity/cat/red.png"),
	SIAMESE("siamese", "minecraft:textures/entity/cat/siamese.png"),
	BRITISH_SHORTHAIR("british_shorthair", "minecraft:textures/entity/cat/british_shorthair.png"),
	CALICO("calico", "minecraft:textures/entity/cat/calico.png"),
	PERSIAN("persian", "minecraft:textures/entity/cat/persian.png"),
	RAGDOLL("ragdoll", "minecraft:textures/entity/cat/ragdoll.png"),
	WHITE("white", "minecraft:textures/entity/cat/white.png"),
	JELLIE("jellie", "minecraft:textures/entity/cat/jellie.png"), //GoodTimesWithScar's cat (1.14 community cat contest)
	ALL_BLACK("all_black", "minecraft:textures/entity/cat/all_black.png"),
	//Mod Variants
	GRAY_TABBY("gray_tabby", "textures/entity/cat/gray_tabby.png"), //Unused from Minecraft Bedrock
	CLAM("clam", "textures/entity/cat/clam.png"), //Unused from Minecraft Dungeons
	DEVY("devy", "textures/entity/cat/devy.png"), //Knife_Moth's cat
	MOLLY("molly", "textures/entity/cat/molly.png"); //Knife_Moth's cat

	public final String name;
	public final Identifier texture;
	CatVariant(String name, String texture) {
		this.name = name;
		this.texture = ModId.ID(texture);
	}
	public static CatVariant get(CatEntity entity) {
		CatVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getCatType(), 0, variants.length - 1)];
	}
}
