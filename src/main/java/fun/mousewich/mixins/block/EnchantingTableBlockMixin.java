package fun.mousewich.mixins.block;

import fun.mousewich.gen.data.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {

	@Redirect(method="randomDisplayTick", at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private boolean DisplayTickWithModBookshelves(BlockState instance, Block block) {
		return instance.isIn(ModBlockTags.BOOKSHELVES);
	}
}
