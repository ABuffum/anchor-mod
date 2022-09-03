package haven.mixins;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(AxeItem.class)
public class AxeMixin {
	@Shadow protected static Map<Block, Block> STRIPPED_BLOCKS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void AddStrippedBlocks(CallbackInfo ci) {
		STRIPPED_BLOCKS = new HashMap<Block, Block>(STRIPPED_BLOCKS);
		//Add our own stripped blocks
		STRIPPED_BLOCKS.putAll(HavenMod.STRIPPED_BLOCKS);
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"))
	private void DropCinnamon(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		Block block = world.getBlockState(blockPos).getBlock();
		if (block == HavenMod.CASSIA.LOG.BLOCK) world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(HavenMod.CINNAMON, 4)));
		else if (block == HavenMod.CASSIA.WOOD.BLOCK) world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(HavenMod.CINNAMON, 6)));
	}

	@Shadow private Optional<BlockState> getStrippedState(BlockState state) { return null; }
}
