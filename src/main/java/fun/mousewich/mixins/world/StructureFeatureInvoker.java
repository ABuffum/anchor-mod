package fun.mousewich.mixins.world;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructureFeature.class)
public interface StructureFeatureInvoker {
	@Invoker("register")
	static <F extends StructureFeature<?>> F Register(String name, F structureFeature, GenerationStep.Feature step) { return null; }
}
