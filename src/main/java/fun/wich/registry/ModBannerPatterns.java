package fun.wich.registry;

import fun.wich.util.banners.ModBannerPattern;

import java.util.List;

import static fun.wich.ModBase.EN_US;
import static fun.wich.registry.ModRegistry.Register;

public class ModBannerPatterns {
	public static final ModBannerPattern CLUB = new ModBannerPattern("club", "club", true);
	public static final ModBannerPattern DIAMOND = new ModBannerPattern("diamond", "diamond", true);
	public static final ModBannerPattern HEART = new ModBannerPattern("heart", "heart", true);
	public static final ModBannerPattern SPADE = new ModBannerPattern("spade", "spade", true);

	public static void RegisterBannerPatterns() {
		Register("club_banner_pattern", CLUB.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner())));
		Register("diamond_banner_pattern", DIAMOND.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner())));
		Register("heart_banner_pattern", HEART.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner())));
		Register("spade_banner_pattern", SPADE.getPatternItem(), List.of(EN_US.Pattern(EN_US.Banner())));
	}
}
