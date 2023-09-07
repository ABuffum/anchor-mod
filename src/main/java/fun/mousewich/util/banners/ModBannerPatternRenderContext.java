package fun.mousewich.util.banners;

import java.util.Collections;
import java.util.List;

public class ModBannerPatternRenderContext {
	private static List<ModBannerPatternData> modBannerPatterns = Collections.emptyList();

	public static void setModBannerPatterns(List<ModBannerPatternData> patterns) { modBannerPatterns = patterns; }
	public static List<ModBannerPatternData> getModBannerPatterns() { return modBannerPatterns; }
}
