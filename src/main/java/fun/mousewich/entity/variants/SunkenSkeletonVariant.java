package fun.mousewich.entity.variants;

import fun.mousewich.ModId;
import fun.mousewich.entity.hostile.skeleton.SunkenSkeletonEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum SunkenSkeletonVariant {
	PURPLE("purple", "textures/entity/skeleton/sunken_purple.png", Items.BUBBLE_CORAL, Items.BUBBLE_CORAL_FAN),
	RED("red", "textures/entity/skeleton/sunken_red.png", Items.FIRE_CORAL, Items.FIRE_CORAL_FAN),
	YELLOW("yellow", "textures/entity/skeleton/sunken_yellow.png", Items.HORN_CORAL, Items.HORN_CORAL_FAN);

	public final String name;
	public final Identifier texture;
	public final ItemConvertible coral;
	public final ItemConvertible coral_fan;
	SunkenSkeletonVariant(String name, String texture, ItemConvertible coral, ItemConvertible coral_fan) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.coral = coral;
		this.coral_fan = coral_fan;
	}
	public static SunkenSkeletonVariant get(SunkenSkeletonEntity entity) {
		SunkenSkeletonVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getVariant(), 0, variants.length - 1)];
	}
}
