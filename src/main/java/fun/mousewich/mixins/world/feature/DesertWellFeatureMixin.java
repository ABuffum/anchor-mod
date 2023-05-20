package fun.mousewich.mixins.world.feature;

import fun.mousewich.ModBase;
import fun.mousewich.gen.loot.ModLootTables;
import net.minecraft.block.Block;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DesertWellFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(DesertWellFeature.class)
public class DesertWellFeatureMixin {

	@Inject(method="generate", at=@At("TAIL"))
	public void PlaceSuspiciousSandAfterGeneration(FeatureContext<DefaultFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
		StructureWorldAccess structureWorldAccess = context.getWorld();
		BlockPos blockPos = context.getOrigin().up();
		while (structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2) {
			blockPos = blockPos.down();
		}
		BlockPos blockPos3 = blockPos;
		List<BlockPos> list = List.of(blockPos3, blockPos3.east(), blockPos3.south(), blockPos3.west(), blockPos3.north());
		Random random = context.getRandom();
		placeSuspiciousSand(structureWorldAccess, Util.getRandom(list, random).down(1));
		placeSuspiciousSand(structureWorldAccess, Util.getRandom(list, random).down(2));
	}

	private static void placeSuspiciousSand(StructureWorldAccess structureWorldAccess, BlockPos blockPos) {
		structureWorldAccess.setBlockState(blockPos, ModBase.SUSPICIOUS_SAND.asBlock().getDefaultState(), Block.NOTIFY_ALL);
		structureWorldAccess.getBlockEntity(blockPos, ModBase.SUSPICIOUS_BLOCK_ENTITY)
				.ifPresent(blockEntity -> blockEntity.setLootTable(ModLootTables.DESERT_WELL_ARCHAEOLOGY, blockPos.asLong()));
	}
}
