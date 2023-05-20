package fun.mousewich.mixins.item;

import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BundleItem.class)
public class BundleItemMixin {
	@Redirect(method="getItemOccupancy", at=@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean UseAllBeehivesInCheck(ItemStack instance, Item item) {
		if (item == Items.BEEHIVE && instance.isIn(ModItemTags.BEEHIVES)) return true;
		return instance.isOf(item);
	}
}
