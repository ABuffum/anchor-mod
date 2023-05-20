package fun.mousewich.mixins.block;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static fun.mousewich.block.DriedBambooBlock.AllowDriedBamboo;

@Mixin(BambooBlock.class)
public class BambooBlockMixin {
	@Redirect(method="getPlacementState", at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	public boolean AllowDriedBamboo_GetPlacementState(BlockState instance, Block block) { return AllowDriedBamboo(instance, block); }

	@Redirect(method="getStateForNeighborUpdate", at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	public boolean AllowDriedBamboo_GetStateForNeighborUpdate(BlockState instance, Block block) { return AllowDriedBamboo(instance, block); }

	@Redirect(method="updateLeaves", at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	protected boolean AllowDriedBamboo_UpdateLeaves(BlockState instance, Block block) { return AllowDriedBamboo(instance, block); }

	@Redirect(method="countBambooAbove", at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	protected boolean AllowDriedBamboo_CountBambooAbove(BlockState instance, Block block) { return AllowDriedBamboo(instance, block); }

	@Redirect(method="countBambooBelow", at=@At(value="INVOKE",target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	protected boolean AllowDriedBamboo_CountBambooBelow(BlockState instance, Block block) { return AllowDriedBamboo(instance, block); }
}
