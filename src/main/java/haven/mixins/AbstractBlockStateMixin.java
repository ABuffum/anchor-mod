package haven.mixins;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import haven.blocks.BookshelfBlock;
import haven.blocks.decoration.DecorativeCactusBlock;
import haven.blocks.decoration.DecorativeSugarCaneBlock;
import haven.blocks.decoration.DecorativeVineBlock;
import net.minecraft.block.*;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin extends State<Block, BlockState> {
	protected AbstractBlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec) {
		super(owner, entries, codec);
	}

	@Shadow
	public abstract Block getBlock();

	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Block block, CallbackInfoReturnable<Boolean> cir) {
		if (block == Blocks.BOOKSHELF) {
			if (getBlock() instanceof BookshelfBlock) cir.setReturnValue(true);
		}
		else if (block == Blocks.CACTUS) {
			if (getBlock() instanceof DecorativeCactusBlock) cir.setReturnValue(true);
		}
		else if (block == Blocks.LADDER) {
			if (getBlock() instanceof LadderBlock) cir.setReturnValue(true);
		}
		else if (block == Blocks.SUGAR_CANE) {
			if (getBlock() instanceof DecorativeSugarCaneBlock) cir.setReturnValue(true);
		}
		else if (block == Blocks.VINE) {
			if (getBlock() instanceof DecorativeVineBlock) cir.setReturnValue(true);
		}
	}
}
