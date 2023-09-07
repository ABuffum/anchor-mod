package fun.mousewich.gen.data.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.registry.Registry;

import static fun.mousewich.ModBase.*;

public class FluidTagGenerator extends FabricTagProvider<Fluid> {
	public FluidTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.FLUID, "fluids", NAMESPACE + ":fluid_tag_generator");
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(FluidTags.WATER)
				.add(STILL_BLOOD_FLUID, FLOWING_BLOOD_FLUID)
				.add(STILL_MUD_FLUID, FLOWING_MUD_FLUID);
	}
}