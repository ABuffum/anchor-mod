package fun.wich.entity.variants;

import fun.wich.ModBase;
import fun.wich.ModId;
import fun.wich.entity.passive.cow.NetherMooshroomEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public enum NetherMooshroomVariant {
	CRIMSON("crimson", "textures/entity/cow/crimson_mooshroom.png", Blocks.CRIMSON_FUNGUS, Items.CRIMSON_FUNGUS),
	WAPED("warped", "textures/entity/cow/warped_mooshroom.png", Blocks.WARPED_FUNGUS, Items.WARPED_FUNGUS),
	GILDED("gilded", "textures/entity/cow/gilded_mooshroom.png", ModBase.GILDED_FUNGUS.asBlock(), ModBase.GILDED_FUNGUS.asItem());

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(NetherMooshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(NetherMooshroomEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(NetherMooshroomEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Identifier texture;
	public final Block block;
	public final Item item;
	NetherMooshroomVariant(String name, String texture, Block block, Item item) {
		this.name = name;
		this.texture = ModId.ID(texture);
		this.block = block;
		this.item = item;
	}
	public static NetherMooshroomVariant get(NetherMooshroomEntity entity) {
		NetherMooshroomVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
}
