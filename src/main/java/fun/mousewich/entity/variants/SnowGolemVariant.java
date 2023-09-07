package fun.mousewich.entity.variants;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.Predicate;

public enum SnowGolemVariant {
	DEFAULT("default", Items.CARVED_PUMPKIN, List.of(Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN)),
	WHITE("white", ModBase.CARVED_WHITE_PUMPKIN.asItem(), List.of(ModBase.CARVED_WHITE_PUMPKIN.asBlock(), ModBase.WHITE_JACK_O_LANTERN.asBlock())),
	ROTTEN("rotten", ModBase.CARVED_ROTTEN_PUMPKIN.asItem(), List.of(ModBase.CARVED_ROTTEN_PUMPKIN.asBlock(), ModBase.ROTTEN_JACK_O_LANTERN.asBlock()));

	public static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE  = (state) -> {
		if (state == null) return false;
		Block block = state.getBlock();
		for (SnowGolemVariant variant : values()) {
			if (variant.head_blocks.contains(block)) return true;
		}
		return false;
	};

	public static final TrackedData<Integer> VARIANT = DataTracker.registerData(SnowGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int getVariant(SnowGolemEntity entity) { return entity.getDataTracker().get(VARIANT); }
	public static void setVariant(SnowGolemEntity entity, int variant) { entity.getDataTracker().set(VARIANT, variant); }

	public final String name;
	public final Item item;
	public final List<Block> head_blocks;
	SnowGolemVariant(String name, Item item, List<Block> head_blocks) {
		this.name = name;
		this.item = item;
		this.head_blocks = head_blocks;
	}
	public static SnowGolemVariant get(SnowGolemEntity entity) {
		SnowGolemVariant[] variants = values();
		return variants[MathHelper.clamp(entity.getDataTracker().get(VARIANT), 0, variants.length - 1)];
	}
}
