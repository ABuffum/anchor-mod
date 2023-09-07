package fun.mousewich.util.dye;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.MapColor;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModDyeColor implements StringIdentifiable {
	public static ModDyeColor WHITE = new ModDyeColor(0, "white", 0xF9FFFE,MapColor.WHITE, 0xF0F0F0, 0xFFFFFF);
	public static ModDyeColor ORANGE = new ModDyeColor(1, "orange", 16351261, MapColor.ORANGE, 15435844, 16738335);
	public static ModDyeColor MAGENTA = new ModDyeColor(2, "magenta", 13061821, MapColor.MAGENTA, 12801229, 0xFF00FF);
	public static ModDyeColor LIGHT_BLUE = new ModDyeColor(3, "light_blue", 3847130, MapColor.LIGHT_BLUE, 6719955, 10141901);
	public static ModDyeColor YELLOW = new ModDyeColor(4, "yellow", 16701501, MapColor.YELLOW, 14602026, 0xFFFF00);
	public static ModDyeColor LIME = new ModDyeColor(5, "lime", 8439583, MapColor.LIME, 4312372, 0xBFFF00);
	public static ModDyeColor PINK = new ModDyeColor(6, "pink", 15961002, MapColor.PINK, 14188952, 16738740);
	public static ModDyeColor GRAY = new ModDyeColor(7, "gray", 4673362, MapColor.GRAY, 0x434343, 0x808080);
	public static ModDyeColor LIGHT_GRAY = new ModDyeColor(8, "light_gray", 0x9D9D97, MapColor.LIGHT_GRAY, 0xABABAB, 0xD3D3D3);
	public static ModDyeColor CYAN = new ModDyeColor(9, "cyan", 1481884, MapColor.CYAN, 2651799, 65535);
	public static ModDyeColor PURPLE = new ModDyeColor(10, "purple", 8991416, MapColor.PURPLE, 8073150, 10494192);
	public static ModDyeColor BLUE = new ModDyeColor(11, "blue", 3949738, MapColor.BLUE, 2437522, 255);
	public static ModDyeColor BROWN = new ModDyeColor(12, "brown", 8606770, MapColor.BROWN, 5320730, 9127187);
	public static ModDyeColor GREEN = new ModDyeColor(13, "green", 6192150, MapColor.GREEN, 3887386, 65280);
	public static ModDyeColor RED = new ModDyeColor(14, "red", 11546150, MapColor.RED, 11743532, 0xFF0000);
	public static ModDyeColor BLACK = new ModDyeColor(15, "black", 0x1D1D21, MapColor.BLACK, 0x1E1B1B, 0);

	public static ModDyeColor BEIGE = new ModDyeColor(16, "beige", 0xE8D7B9, MapColor.PALE_YELLOW, 0xECDDC2, 0xECDDC2);
	public static ModDyeColor LAVENDER = new ModDyeColor(17, "lavender", 0x997CAD, MapColor.PALE_PURPLE, 0xB490CB, 0xB490CB);
	public static ModDyeColor BURGUNDY = new ModDyeColor(18, "burgundy", 0x791138, MapColor.DARK_RED, 0x7E0326, 0x7E0326);
	public static ModDyeColor MINT = new ModDyeColor(19, "mint", 0x74C98C, MapColor.LICHEN_GREEN, 0x8DE3A1, 0x8DE3A1);

	public static final ModDyeColor[] VANILLA_VALUES = {
			WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK,
	};
	public static final ModDyeColor[] MOD_VALUES = {
			BEIGE, LAVENDER, BURGUNDY, MINT
	};
	public static final ModDyeColor[] ALL_VALUES = {
			WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK,
			//Mod
			BEIGE, LAVENDER, BURGUNDY, MINT
	};
	private static final Int2ObjectOpenHashMap<ModDyeColor> BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap<>(Arrays.stream(ALL_VALUES).collect(Collectors.toMap(dyeColor -> dyeColor.fireworkColor, dyeColor -> dyeColor)));
	private final int id;
	public int getId() { return this.id; }
	private final String name;
	public String getName() { return this.name; }
	private final MapColor mapColor;
	public MapColor getMapColor() { return this.mapColor; }
	private final float[] colorComponents;
	public float[] getColorComponents() { return this.colorComponents; }
	private final float[] sheepColors;
	public float[] getSheepColors() { return this.sheepColors; }
	private final int fireworkColor;
	public int getFireworkColor() { return this.fireworkColor; }
	private final int signColor;
	public int getSignColor() { return this.signColor; }

	public ModDyeColor(int woolId, String name, int color, MapColor mapColor, int fireworkColor, int signColor) {
		this.id = woolId;
		this.name = name;
		this.mapColor = mapColor;
		this.fireworkColor = fireworkColor;
		this.signColor = signColor;
		this.colorComponents = new float[] { ((color & 0xFF0000) >> 16) / 255.0f, ((color & 0xFF00) >> 8) / 255.0f, (color & 0xFF) / 255.0f };
		if (this.id == 0) this.sheepColors = new float[] { 0.9019608f, 0.9019608f, 0.9019608f };
		else this.sheepColors = new float[] { this.colorComponents[0] * 0.75f, this.colorComponents[1] * 0.75f, this.colorComponents[2] * 0.75f };
	}
	public static ModDyeColor byId(int id) {
		if (id < 0 || id >= ALL_VALUES.length) id = 0;
		return ALL_VALUES[id];
	}
	public static ModDyeColor byName(String name, ModDyeColor defaultColor) {
		for (ModDyeColor dyeColor : ALL_VALUES) {
			if (!dyeColor.name.equals(name)) continue;
			return dyeColor;
		}
		return defaultColor;
	}
	@Nullable
	public static ModDyeColor byFireworkColor(int color) { return BY_FIREWORK_COLOR.get(color); }
	public String toString() { return this.name; }
	@Override
	public String asString() { return this.name; }
}
