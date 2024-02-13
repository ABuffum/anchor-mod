package fun.wich.entity.variants;

import fun.wich.ModBase;
import fun.wich.ModId;
import fun.wich.entity.hostile.illager.MountaineerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum MountaineerVariant {
	BROWN("brown", "textures/entity/illager/mountaineer.png", ModBase.MOUNTAINEER_AXE),
	GREY("grey", "textures/entity/illager/mountaineer_1.png", ModBase.GOLD_MOUNTAINEER_AXE),
	RED("red", "textures/entity/illager/mountaineer_2.png", ModBase.DIAMOND_MOUNTAINEER_AXE);

	public final String name;
	public final Identifier texture;
	public final ItemConvertible stoneaxe;
	MountaineerVariant(String name, String texture, ItemConvertible stoneaxe) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.stoneaxe = stoneaxe;
	}
	public static MountaineerVariant get(MountaineerEntity entity) {
		MountaineerVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getVariant(), 0, variants.length - 1)];
	}
}
