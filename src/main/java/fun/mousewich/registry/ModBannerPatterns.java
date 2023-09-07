package fun.mousewich.registry;

import fun.mousewich.util.banners.ModBannerPattern;

import java.util.List;

import static fun.mousewich.ModBase.EN_US;
import static fun.mousewich.registry.ModRegistry.Register;

public class ModBannerPatterns {
	public static final ModBannerPattern CLUB = new ModBannerPattern("club", "club", true);
	public static final ModBannerPattern DIAMOND = new ModBannerPattern("diamond", "diamond", true);
	public static final ModBannerPattern HEART = new ModBannerPattern("heart", "heart", true);
	public static final ModBannerPattern SPADE = new ModBannerPattern("spade", "spade", true);

	public static void RegisterBannerPatterns() {
		Register("club_banner_pattern", CLUB.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner(EN_US.Club()))));
		Register("diamond_banner_pattern", DIAMOND.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner(EN_US.Diamond()))));
		Register("heart_banner_pattern", HEART.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner(EN_US.Heart()))));
		Register("spade_banner_pattern", SPADE.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner(EN_US.Spade()))));
	}
}
