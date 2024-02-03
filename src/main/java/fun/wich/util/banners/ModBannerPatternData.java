package fun.wich.util.banners;

import fun.wich.util.dye.ModDyeColor;

public class ModBannerPatternData {
	public ModBannerPattern pattern;
	public ModDyeColor color;
	public int index;
	public ModBannerPatternData(ModBannerPattern pattern, ModDyeColor color, int index) {
		this.pattern = pattern;
		this.color = color;
		if ((this.index = index) < 0) throw new IllegalArgumentException("index < 0: " + index);
	}
}
