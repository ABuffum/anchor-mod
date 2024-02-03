package fun.wich.mixins.block.entity;

import fun.wich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.PistonBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntityMixin {
	@Redirect(method="pushEntities", at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private static boolean PushNewSlime(BlockState instance, Block block) {
		return (block == Blocks.SLIME_BLOCK && (instance.isOf(ModBase.BLUE_SLIME_BLOCK.asBlock()) || instance.isOf(ModBase.PINK_SLIME_BLOCK.asBlock())))
				|| instance.isOf(block);
	}
}
