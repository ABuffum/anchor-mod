package fun.wich.util.banners;

import fun.wich.ModFactory;
import fun.wich.ModId;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModBannerPattern {
	private static int ORDINAL = -1;
	private final String name;
	public String getName() { return this.name; }
	private final String id;
	public String getId() { return this.id; }
	private final Item patternItem;
	public Item getPatternItem() { return this.patternItem; }
	private final int ordinal;
	public int getOrdinal() { return this.ordinal; }

	public ModBannerPattern(String name, String id) { this(name, id, false); }
	public ModBannerPattern(String name, String id, boolean hasPatternItem) {
		this.name = name;
		this.id = id;
		this.patternItem = hasPatternItem ? ModFactory.MakeBannerPatternItem(this) : null;
		this.ordinal = ORDINAL--;
		ALL.add(this);
	}

	public Identifier getSpriteId(boolean banner) {
		String string = banner ? "banner" : "shield";
		return ModId.ID("entity/" + string + "/" + this.getName());
	}

	public static ModBannerPattern byName(String name) {
		for (ModBannerPattern pattern : ALL) { if (pattern.name.equals(name)) return pattern; }
		return null;
	}
	public static ModBannerPattern byId(String id) {
		for (ModBannerPattern pattern : ALL) { if (pattern.id.equals(id)) return pattern; }
		return null;
	}
	public static ModBannerPattern byOrdinal(int ordinal) {
		for (ModBannerPattern pattern : ALL) { if (pattern.ordinal == ordinal) return pattern; }
		return null;
	}

	private static final List<ModBannerPattern> ALL = new ArrayList<>();
	public static List<ModBannerPattern> values() { return ALL.stream().toList(); }

	public static List<ModBannerPattern> valuesWithItem() { return ALL.stream().filter(pattern -> pattern.patternItem != null).toList(); }
	public static List<ModBannerPattern> valuesWithoutItem() { return ALL.stream().filter(pattern -> pattern.patternItem == null).toList(); }

	public interface Provider {
		ModBannerPattern getPattern();
	}
}
