package fun.wich.util.banners;

import net.minecraft.nbt.NbtList;

import java.util.List;

public interface ModBannerPatternContainer {
	List<ModBannerPatternData> getModBannerPatterns();

	interface MixinHelper {
		NbtList getModBannerPatternsNbt();
		void setModBannerPatternsNbt(NbtList tag);
	}
}
