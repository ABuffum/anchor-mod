package fun.mousewich.entity.variants;

import fun.mousewich.ModBase;
import fun.mousewich.ModId;
import fun.mousewich.entity.passive.cow.MooblossomEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum MooblossomVariant {
	MAGENTA_TULIP("magenta_tulip", "textures/entity/cow/mooblossom/magenta_tulip.png", ModBase.MAGENTA_TULIP.asBlock(), ModBase.MAGENTA_TULIP.asItem(), ModBase.MOOBLOSSOM_MAGENTA_TULIP),
	RED_TULIP("red_tulip", "textures/entity/cow/mooblossom/red_tulip.png", Blocks.RED_TULIP, Items.RED_TULIP, ModBase.MOOBLOSSOM_RED_TULIP),
	ORANGE_TULIP("orange_tulip", "textures/entity/cow/mooblossom/orange_tulip.png", Blocks.ORANGE_TULIP, Items.ORANGE_TULIP, ModBase.MOOBLOSSOM_ORANGE_TULIP),
	WHITE_TULIP("white_tulip", "textures/entity/cow/mooblossom/white_tulip.png", Blocks.WHITE_TULIP, Items.WHITE_TULIP, ModBase.MOOBLOSSOM_WHITE_TULIP),
	PINK_TULIP("pink_tulip", "textures/entity/cow/mooblossom/pink_tulip.png", Blocks.PINK_TULIP, Items.PINK_TULIP, ModBase.MOOBLOSSOM_PINK_TULIP);

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(MooblossomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(MooblossomEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(MooblossomEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Identifier texture;
	public final Block block;
	public final Item item;
	public final Block backBlock;
	MooblossomVariant(String name, String texture, Block block, Item item) { this(name, texture, block, item, block); }
	MooblossomVariant(String name, String texture, Block block, Item item, Block backBlock) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.block = block;
		this.item = item;
		this.backBlock = backBlock;
	}
	public static MooblossomVariant get(MooblossomEntity entity) {
		MooblossomVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
}
