package fun.mousewich.util.banners;

import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.util.dye.ModDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class ModBannerPatternConversions {

	public static NbtList getModBannerPatternNbt(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(ModNbtKeys.BLOCK_ENTITY_TAG);
		if (tag != null && tag.contains(ModNbtKeys.MOD_BANNER_PATTERNS, 9)) return tag.getList(ModNbtKeys.MOD_BANNER_PATTERNS, 10);
		else return null;
	}

	public static List<ModBannerPatternData> makeModBannerPatternData(NbtList nbt) {
		List<ModBannerPatternData> res = new ArrayList<>();
		if (nbt != null) {
			for (NbtElement element : nbt) {
				NbtCompound patternNbt = (NbtCompound)element;
				ModBannerPattern pattern = ModBannerPattern.byId(patternNbt.getString(ModNbtKeys.PATTERN));
				if (pattern != null) {
					ModDyeColor color = ModDyeColor.byId(patternNbt.getInt(ModNbtKeys.COLOR));
					int index = patternNbt.getInt(ModNbtKeys.INDEX);
					res.add(new ModBannerPatternData(pattern, color, index));
				}
			}
		}
		return res;
	}
}
