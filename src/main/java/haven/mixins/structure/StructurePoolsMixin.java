package haven.mixins.structure;

import haven.gen.structures.AncientCityGenerator;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePools.class)
public class StructurePoolsMixin {
	@Inject(method="initDefaultPools", at = @At("TAIL"))
	private static void initDefaultPools(CallbackInfoReturnable<StructurePool> cir) {
		//TODO: Initialize the Ancient City structure pools
		//AncientCityGenerator.init();
	}
}
