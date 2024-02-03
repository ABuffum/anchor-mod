package fun.wich.util;

import fun.wich.ModBase;
import fun.wich.util.dye.ModDyeColor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class ColorUtil {
	public static <T> Map<DyeColor, T> Map(Function<DyeColor, T> op) {
		Map<DyeColor, T> output = new HashMap<>();
		for(DyeColor c : DyeColor.values()) output.put(c, op.apply(c));
		return output;
	}

	public static ModDyeColor GetRandomColor(Random random) {
		ModDyeColor[] colors = ModDyeColor.ALL_VALUES;
		return colors[random.nextInt(colors.length)];
	}

	public static Item GetDye(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_DYE;
			case MAGENTA -> Items.MAGENTA_DYE;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_DYE;
			case YELLOW -> Items.YELLOW_DYE;
			case LIME -> Items.LIME_DYE;
			case PINK -> Items.PINK_DYE;
			case GRAY -> Items.GRAY_DYE;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_DYE;
			case CYAN -> Items.CYAN_DYE;
			case PURPLE -> Items.PURPLE_DYE;
			case BLUE -> Items.BLUE_DYE;
			case BROWN -> Items.BROWN_DYE;
			case GREEN -> Items.GREEN_DYE;
			case RED -> Items.RED_DYE;
			case BLACK -> Items.BLACK_DYE;
			case WHITE -> Items.WHITE_DYE;
		};
	}

	public static DyeColor GetCandleColor(Block block) {
		if (block == Blocks.BLACK_CANDLE) return DyeColor.BLACK;
		else if (block == Blocks.BLUE_CANDLE) return DyeColor.BLUE;
		else if (block == Blocks.BROWN_CANDLE) return DyeColor.BROWN;
		else if (block == Blocks.CYAN_CANDLE) return DyeColor.CYAN;
		else if (block == Blocks.GRAY_CANDLE) return DyeColor.GRAY;
		else if (block == Blocks.GREEN_CANDLE) return DyeColor.GREEN;
		else if (block == Blocks.LIGHT_BLUE_CANDLE) return DyeColor.LIGHT_BLUE;
		else if (block == Blocks.LIGHT_GRAY_CANDLE) return DyeColor.LIGHT_GRAY;
		else if (block == Blocks.LIME_CANDLE) return DyeColor.LIME;
		else if (block == Blocks.MAGENTA_CANDLE) return DyeColor.MAGENTA;
		else if (block == Blocks.ORANGE_CANDLE) return DyeColor.ORANGE;
		else if (block == Blocks.PINK_CANDLE) return DyeColor.PINK;
		else if (block == Blocks.PURPLE_CANDLE) return DyeColor.PURPLE;
		else if (block == Blocks.RED_CANDLE) return DyeColor.RED;
		else if (block == Blocks.WHITE_CANDLE) return DyeColor.WHITE;
		else if (block == Blocks.YELLOW_CANDLE) return DyeColor.YELLOW;
		else return DyeColor.WHITE;
	}
	public static Block GetCandleBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_CANDLE;
			case MAGENTA -> Blocks.MAGENTA_CANDLE;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_CANDLE;
			case YELLOW -> Blocks.YELLOW_CANDLE;
			case LIME -> Blocks.LIME_CANDLE;
			case PINK -> Blocks.PINK_CANDLE;
			case GRAY -> Blocks.GRAY_CANDLE;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_CANDLE;
			case CYAN -> Blocks.CYAN_CANDLE;
			case PURPLE -> Blocks.PURPLE_CANDLE;
			case BLUE -> Blocks.BLUE_CANDLE;
			case BROWN -> Blocks.BROWN_CANDLE;
			case GREEN -> Blocks.GREEN_CANDLE;
			case RED -> Blocks.RED_CANDLE;
			case BLACK -> Blocks.BLACK_CANDLE;
			case WHITE -> Blocks.WHITE_CANDLE;
		};
	}

	public static Block GetWoolBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_WOOL;
			case MAGENTA -> Blocks.MAGENTA_WOOL;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_WOOL;
			case YELLOW -> Blocks.YELLOW_WOOL;
			case LIME -> Blocks.LIME_WOOL;
			case PINK -> Blocks.PINK_WOOL;
			case GRAY -> Blocks.GRAY_WOOL;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_WOOL;
			case CYAN -> Blocks.CYAN_WOOL;
			case PURPLE -> Blocks.PURPLE_WOOL;
			case BLUE -> Blocks.BLUE_WOOL;
			case BROWN -> Blocks.BROWN_WOOL;
			case GREEN -> Blocks.GREEN_WOOL;
			case RED -> Blocks.RED_WOOL;
			case BLACK -> Blocks.BLACK_WOOL;
			case WHITE -> Blocks.WHITE_WOOL;
		};
	}
	public static Item GetWoolItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_WOOL;
			case MAGENTA -> Items.MAGENTA_WOOL;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_WOOL;
			case YELLOW -> Items.YELLOW_WOOL;
			case LIME -> Items.LIME_WOOL;
			case PINK -> Items.PINK_WOOL;
			case GRAY -> Items.GRAY_WOOL;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_WOOL;
			case CYAN -> Items.CYAN_WOOL;
			case PURPLE -> Items.PURPLE_WOOL;
			case BLUE -> Items.BLUE_WOOL;
			case BROWN -> Items.BROWN_WOOL;
			case GREEN -> Items.GREEN_WOOL;
			case RED -> Items.RED_WOOL;
			case BLACK -> Items.BLACK_WOOL;
			case WHITE -> Items.WHITE_WOOL;
		};
	}
	public static Item GetWoolItem(ModDyeColor color) {
		if (color == ModDyeColor.BEIGE) return ModBase.BEIGE_WOOL.asItem();
		else if (color == ModDyeColor.BURGUNDY) return ModBase.BURGUNDY_WOOL.asItem();
		else if (color == ModDyeColor.LAVENDER) return ModBase.LAVENDER_WOOL.asItem();
		else if (color == ModDyeColor.MINT) return ModBase.MINT_WOOL.asItem();
		else return GetWoolItem(DyeColor.byId(color.getId()));
	}
	public static Block GetWoolCarpetBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_CARPET;
			case MAGENTA -> Blocks.MAGENTA_CARPET;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_CARPET;
			case YELLOW -> Blocks.YELLOW_CARPET;
			case LIME -> Blocks.LIME_CARPET;
			case PINK -> Blocks.PINK_CARPET;
			case GRAY -> Blocks.GRAY_CARPET;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_CARPET;
			case CYAN -> Blocks.CYAN_CARPET;
			case PURPLE -> Blocks.PURPLE_CARPET;
			case BLUE -> Blocks.BLUE_CARPET;
			case BROWN -> Blocks.BROWN_CARPET;
			case GREEN -> Blocks.GREEN_CARPET;
			case RED -> Blocks.RED_CARPET;
			case BLACK -> Blocks.BLACK_CARPET;
			case WHITE -> Blocks.WHITE_CARPET;
		};
	}
	public static Item GetWoolCarpetItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_CARPET;
			case MAGENTA -> Items.MAGENTA_CARPET;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_CARPET;
			case YELLOW -> Items.YELLOW_CARPET;
			case LIME -> Items.LIME_CARPET;
			case PINK -> Items.PINK_CARPET;
			case GRAY -> Items.GRAY_CARPET;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_CARPET;
			case CYAN -> Items.CYAN_CARPET;
			case PURPLE -> Items.PURPLE_CARPET;
			case BLUE -> Items.BLUE_CARPET;
			case BROWN -> Items.BROWN_CARPET;
			case GREEN -> Items.GREEN_CARPET;
			case RED -> Items.RED_CARPET;
			case BLACK -> Items.BLACK_CARPET;
			case WHITE -> Items.WHITE_CARPET;
		};
	}
	public static Block GetStainedGlassBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_STAINED_GLASS;
			case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS;
			case YELLOW -> Blocks.YELLOW_STAINED_GLASS;
			case LIME -> Blocks.LIME_STAINED_GLASS;
			case PINK -> Blocks.PINK_STAINED_GLASS;
			case GRAY -> Blocks.GRAY_STAINED_GLASS;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS;
			case CYAN -> Blocks.CYAN_STAINED_GLASS;
			case PURPLE -> Blocks.PURPLE_STAINED_GLASS;
			case BLUE -> Blocks.BLUE_STAINED_GLASS;
			case BROWN -> Blocks.BROWN_STAINED_GLASS;
			case GREEN -> Blocks.GREEN_STAINED_GLASS;
			case RED -> Blocks.RED_STAINED_GLASS;
			case BLACK -> Blocks.BLACK_STAINED_GLASS;
			case WHITE -> Blocks.WHITE_STAINED_GLASS;
		};
	}
	public static Item GetStainedGlassItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_STAINED_GLASS;
			case MAGENTA -> Items.MAGENTA_STAINED_GLASS;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_STAINED_GLASS;
			case YELLOW -> Items.YELLOW_STAINED_GLASS;
			case LIME -> Items.LIME_STAINED_GLASS;
			case PINK -> Items.PINK_STAINED_GLASS;
			case GRAY -> Items.GRAY_STAINED_GLASS;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_STAINED_GLASS;
			case CYAN -> Items.CYAN_STAINED_GLASS;
			case PURPLE -> Items.PURPLE_STAINED_GLASS;
			case BLUE -> Items.BLUE_STAINED_GLASS;
			case BROWN -> Items.BROWN_STAINED_GLASS;
			case GREEN -> Items.GREEN_STAINED_GLASS;
			case RED -> Items.RED_STAINED_GLASS;
			case BLACK -> Items.BLACK_STAINED_GLASS;
			case WHITE -> Items.WHITE_STAINED_GLASS;
		};
	}
	public static Block GetStainedGlassPaneBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_STAINED_GLASS_PANE;
			case MAGENTA -> Blocks.MAGENTA_STAINED_GLASS_PANE;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_STAINED_GLASS_PANE;
			case YELLOW -> Blocks.YELLOW_STAINED_GLASS_PANE;
			case LIME -> Blocks.LIME_STAINED_GLASS_PANE;
			case PINK -> Blocks.PINK_STAINED_GLASS_PANE;
			case GRAY -> Blocks.GRAY_STAINED_GLASS_PANE;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_STAINED_GLASS_PANE;
			case CYAN -> Blocks.CYAN_STAINED_GLASS_PANE;
			case PURPLE -> Blocks.PURPLE_STAINED_GLASS_PANE;
			case BLUE -> Blocks.BLUE_STAINED_GLASS_PANE;
			case BROWN -> Blocks.BROWN_STAINED_GLASS_PANE;
			case GREEN -> Blocks.GREEN_STAINED_GLASS_PANE;
			case RED -> Blocks.RED_STAINED_GLASS_PANE;
			case BLACK -> Blocks.BLACK_STAINED_GLASS_PANE;
			case WHITE -> Blocks.WHITE_STAINED_GLASS_PANE;
		};
	}
	public static Item GetStainedGlassPaneItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_STAINED_GLASS_PANE;
			case MAGENTA -> Items.MAGENTA_STAINED_GLASS_PANE;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_STAINED_GLASS_PANE;
			case YELLOW -> Items.YELLOW_STAINED_GLASS_PANE;
			case LIME -> Items.LIME_STAINED_GLASS_PANE;
			case PINK -> Items.PINK_STAINED_GLASS_PANE;
			case GRAY -> Items.GRAY_STAINED_GLASS_PANE;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_STAINED_GLASS_PANE;
			case CYAN -> Items.CYAN_STAINED_GLASS_PANE;
			case PURPLE -> Items.PURPLE_STAINED_GLASS_PANE;
			case BLUE -> Items.BLUE_STAINED_GLASS_PANE;
			case BROWN -> Items.BROWN_STAINED_GLASS_PANE;
			case GREEN -> Items.GREEN_STAINED_GLASS_PANE;
			case RED -> Items.RED_STAINED_GLASS_PANE;
			case BLACK -> Items.BLACK_STAINED_GLASS_PANE;
			case WHITE -> Items.WHITE_STAINED_GLASS_PANE;
		};
	}
	public static Block GetBannerBlock(DyeColor color) {
		return switch (color) {
			case ORANGE -> Blocks.ORANGE_BANNER;
			case MAGENTA -> Blocks.MAGENTA_BANNER;
			case LIGHT_BLUE -> Blocks.LIGHT_BLUE_BANNER;
			case YELLOW -> Blocks.YELLOW_BANNER;
			case LIME -> Blocks.LIME_BANNER;
			case PINK -> Blocks.PINK_BANNER;
			case GRAY -> Blocks.GRAY_BANNER;
			case LIGHT_GRAY -> Blocks.LIGHT_GRAY_BANNER;
			case CYAN -> Blocks.CYAN_BANNER;
			case PURPLE -> Blocks.PURPLE_BANNER;
			case BLUE -> Blocks.BLUE_BANNER;
			case BROWN -> Blocks.BROWN_BANNER;
			case GREEN -> Blocks.GREEN_BANNER;
			case RED -> Blocks.RED_BANNER;
			case BLACK -> Blocks.BLACK_BANNER;
			case WHITE -> Blocks.WHITE_BANNER;
		};
	}
	public static Item GetBannerItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_BANNER;
			case MAGENTA -> Items.MAGENTA_BANNER;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_BANNER;
			case YELLOW -> Items.YELLOW_BANNER;
			case LIME -> Items.LIME_BANNER;
			case PINK -> Items.PINK_BANNER;
			case GRAY -> Items.GRAY_BANNER;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_BANNER;
			case CYAN -> Items.CYAN_BANNER;
			case PURPLE -> Items.PURPLE_BANNER;
			case BLUE -> Items.BLUE_BANNER;
			case BROWN -> Items.BROWN_BANNER;
			case GREEN -> Items.GREEN_BANNER;
			case RED -> Items.RED_BANNER;
			case BLACK -> Items.BLACK_BANNER;
			case WHITE -> Items.WHITE_BANNER;
		};
	}
	public static Block GetConcreteBlock(DyeColor color) {
		switch (color) {
			case ORANGE: return Blocks.ORANGE_CONCRETE;
			case MAGENTA: return Blocks.MAGENTA_CONCRETE;
			case LIGHT_BLUE: return Blocks.LIGHT_BLUE_CONCRETE;
			case YELLOW: return Blocks.YELLOW_CONCRETE;
			case LIME: return Blocks.LIME_CONCRETE;
			case PINK: return Blocks.PINK_CONCRETE;
			case GRAY: return Blocks.GRAY_CONCRETE;
			case LIGHT_GRAY: return Blocks.LIGHT_GRAY_CONCRETE;
			case CYAN: return Blocks.CYAN_CONCRETE;
			case PURPLE: return Blocks.PURPLE_CONCRETE;
			case BLUE: return Blocks.BLUE_CONCRETE;
			case BROWN: return Blocks.BROWN_CONCRETE;
			case GREEN: return Blocks.GREEN_CONCRETE;
			case RED: return Blocks.RED_CONCRETE;
			case BLACK: return Blocks.BLACK_CONCRETE;
			case WHITE:
			default: return Blocks.WHITE_CONCRETE;
		}
	}
	public static Item GetConcreteItem(DyeColor color) {
		switch (color) {
			case ORANGE: return Items.ORANGE_CONCRETE;
			case MAGENTA: return Items.MAGENTA_CONCRETE;
			case LIGHT_BLUE: return Items.LIGHT_BLUE_CONCRETE;
			case YELLOW: return Items.YELLOW_CONCRETE;
			case LIME: return Items.LIME_CONCRETE;
			case PINK: return Items.PINK_CONCRETE;
			case GRAY: return Items.GRAY_CONCRETE;
			case LIGHT_GRAY: return Items.LIGHT_GRAY_CONCRETE;
			case CYAN: return Items.CYAN_CONCRETE;
			case PURPLE: return Items.PURPLE_CONCRETE;
			case BLUE: return Items.BLUE_CONCRETE;
			case BROWN: return Items.BROWN_CONCRETE;
			case GREEN: return Items.GREEN_CONCRETE;
			case RED: return Items.RED_CONCRETE;
			case BLACK: return Items.BLACK_CONCRETE;
			case WHITE:
			default: return Items.WHITE_CONCRETE;
		}
	}
	public static Block GetTerracottaBlock(DyeColor color) {
		switch (color) {
			case ORANGE: return Blocks.ORANGE_TERRACOTTA;
			case MAGENTA: return Blocks.MAGENTA_TERRACOTTA;
			case LIGHT_BLUE: return Blocks.LIGHT_BLUE_TERRACOTTA;
			case YELLOW: return Blocks.YELLOW_TERRACOTTA;
			case LIME: return Blocks.LIME_TERRACOTTA;
			case PINK: return Blocks.PINK_TERRACOTTA;
			case GRAY: return Blocks.GRAY_TERRACOTTA;
			case LIGHT_GRAY: return Blocks.LIGHT_GRAY_TERRACOTTA;
			case CYAN: return Blocks.CYAN_TERRACOTTA;
			case PURPLE: return Blocks.PURPLE_TERRACOTTA;
			case BLUE: return Blocks.BLUE_TERRACOTTA;
			case BROWN: return Blocks.BROWN_TERRACOTTA;
			case GREEN: return Blocks.GREEN_TERRACOTTA;
			case RED: return Blocks.RED_TERRACOTTA;
			case BLACK: return Blocks.BLACK_TERRACOTTA;
			case WHITE: return Blocks.WHITE_TERRACOTTA;
			default: return Blocks.TERRACOTTA;
		}
	}
	public static Item GetTerracottaItem(DyeColor color) {
		switch (color) {
			case ORANGE: return Items.ORANGE_TERRACOTTA;
			case MAGENTA: return Items.MAGENTA_TERRACOTTA;
			case LIGHT_BLUE: return Items.LIGHT_BLUE_TERRACOTTA;
			case YELLOW: return Items.YELLOW_TERRACOTTA;
			case LIME: return Items.LIME_TERRACOTTA;
			case PINK: return Items.PINK_TERRACOTTA;
			case GRAY: return Items.GRAY_TERRACOTTA;
			case LIGHT_GRAY: return Items.LIGHT_GRAY_TERRACOTTA;
			case CYAN: return Items.CYAN_TERRACOTTA;
			case PURPLE: return Items.PURPLE_TERRACOTTA;
			case BLUE: return Items.BLUE_TERRACOTTA;
			case BROWN: return Items.BROWN_TERRACOTTA;
			case GREEN: return Items.GREEN_TERRACOTTA;
			case RED: return Items.RED_TERRACOTTA;
			case BLACK: return Items.BLACK_TERRACOTTA;
			case WHITE: return Items.WHITE_TERRACOTTA;
			default: return Items.TERRACOTTA;
		}
	}
	public static Block GetGlazedTerracottaBlock(DyeColor color) {
		switch (color) {
			case ORANGE: return Blocks.ORANGE_GLAZED_TERRACOTTA;
			case MAGENTA: return Blocks.MAGENTA_GLAZED_TERRACOTTA;
			case LIGHT_BLUE: return Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA;
			case YELLOW: return Blocks.YELLOW_GLAZED_TERRACOTTA;
			case LIME: return Blocks.LIME_GLAZED_TERRACOTTA;
			case PINK: return Blocks.PINK_GLAZED_TERRACOTTA;
			case GRAY: return Blocks.GRAY_GLAZED_TERRACOTTA;
			case LIGHT_GRAY: return Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA;
			case CYAN: return Blocks.CYAN_GLAZED_TERRACOTTA;
			case PURPLE: return Blocks.PURPLE_GLAZED_TERRACOTTA;
			case BLUE: return Blocks.BLUE_GLAZED_TERRACOTTA;
			case BROWN: return Blocks.BROWN_GLAZED_TERRACOTTA;
			case GREEN: return Blocks.GREEN_GLAZED_TERRACOTTA;
			case RED: return Blocks.RED_GLAZED_TERRACOTTA;
			case BLACK: return Blocks.BLACK_GLAZED_TERRACOTTA;
			case WHITE: return Blocks.WHITE_GLAZED_TERRACOTTA;
			default: return ModBase.GLAZED_TERRACOTTA.asBlock();
		}
	}
	public static Item GetGlazedTerracottaItem(DyeColor color) {
		switch (color) {
			case ORANGE: return Items.ORANGE_GLAZED_TERRACOTTA;
			case MAGENTA: return Items.MAGENTA_GLAZED_TERRACOTTA;
			case LIGHT_BLUE: return Items.LIGHT_BLUE_GLAZED_TERRACOTTA;
			case YELLOW: return Items.YELLOW_GLAZED_TERRACOTTA;
			case LIME: return Items.LIME_GLAZED_TERRACOTTA;
			case PINK: return Items.PINK_GLAZED_TERRACOTTA;
			case GRAY: return Items.GRAY_GLAZED_TERRACOTTA;
			case LIGHT_GRAY: return Items.LIGHT_GRAY_GLAZED_TERRACOTTA;
			case CYAN: return Items.CYAN_GLAZED_TERRACOTTA;
			case PURPLE: return Items.PURPLE_GLAZED_TERRACOTTA;
			case BLUE: return Items.BLUE_GLAZED_TERRACOTTA;
			case BROWN: return Items.BROWN_GLAZED_TERRACOTTA;
			case GREEN: return Items.GREEN_GLAZED_TERRACOTTA;
			case RED: return Items.RED_GLAZED_TERRACOTTA;
			case BLACK: return Items.BLACK_GLAZED_TERRACOTTA;
			case WHITE: return Items.WHITE_GLAZED_TERRACOTTA;
			default: return ModBase.GLAZED_TERRACOTTA.asItem();
		}
	}
	public static Item GetBedItem(DyeColor color) {
		return switch (color) {
			case ORANGE -> Items.ORANGE_BED;
			case MAGENTA -> Items.MAGENTA_BED;
			case LIGHT_BLUE -> Items.LIGHT_BLUE_BED;
			case YELLOW -> Items.YELLOW_BED;
			case LIME -> Items.LIME_BED;
			case PINK -> Items.PINK_BED;
			case GRAY -> Items.GRAY_BED;
			case LIGHT_GRAY -> Items.LIGHT_GRAY_BED;
			case CYAN -> Items.CYAN_BED;
			case PURPLE -> Items.PURPLE_BED;
			case BLUE -> Items.BLUE_BED;
			case BROWN -> Items.BROWN_BED;
			case GREEN -> Items.GREEN_BED;
			case RED -> Items.RED_BED;
			case BLACK -> Items.BLACK_BED;
			case WHITE -> Items.WHITE_BED;
		};
	}
}
