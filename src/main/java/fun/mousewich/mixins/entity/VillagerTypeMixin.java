package fun.mousewich.mixins.entity;

import fun.mousewich.ModBase;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(VillagerType.class)
public class VillagerTypeMixin {
	@Inject(method="forBiome", at=@At("HEAD"), cancellable = true)
	private static void forBiome(Optional<RegistryKey<Biome>> biomeEntry, CallbackInfoReturnable<VillagerType> cir) {
		if (biomeEntry.isPresent() && biomeEntry.get() == ModBase.MANGROVE_SWAMP) cir.setReturnValue(VillagerType.SWAMP);
	}
}
