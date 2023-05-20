package fun.mousewich.mixins.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.DesertVillageData;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DesertVillageData.class)
public class DesertVillageDataMixin {
	@Inject(method="<clinit>", at = @At("TAIL"))
	private static void AddCamels(CallbackInfo ci) {
		StructurePools.register(new StructurePool(new Identifier("village/desert/camel"), new Identifier("empty"),
				ImmutableList.of(Pair.of(StructurePoolElement.ofLegacySingle("village/desert/camel_spawn"), 1)),
				StructurePool.Projection.RIGID));
	}
}
