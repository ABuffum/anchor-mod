package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.mixin.EntityAccessor;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;

public class FluidBreatherPower extends Power {
	public final Tag.Identified<Fluid> fluid;
	public final boolean suffocate;
	public final boolean dryout;
	public final boolean touch;
	public FluidBreatherPower(PowerType<?> type, LivingEntity entity, Tag.Identified<Fluid> fluid, boolean suffocate, boolean dryout, boolean touch) {
		super(type, entity);
		this.fluid = fluid;
		this.suffocate = suffocate;
		this.dryout = dryout;
		this.touch = touch;
	}
	public static PowerFactory<FluidBreatherPower> createFactory() {
		return new PowerFactory<FluidBreatherPower>(ModId.ID("fluid_breather"), new SerializableData()
				.add("fluid", SerializableDataType.tag(Registry.FLUID_KEY))
				.add("suffocate", SerializableDataTypes.BOOLEAN, false)
				.add("dryout", SerializableDataTypes.BOOLEAN, false)
				.add("touch", SerializableDataTypes.BOOLEAN, true),
				data -> (type, entity) -> new FluidBreatherPower(type, entity,
						(Tag.Identified<Fluid>)data.get("fluid"), data.getBoolean("suffocate"), data.getBoolean("dryout"), data.getBoolean("touch")))
				.allowCondition();
	}
	public static boolean Applies(Entity entity, Fluid... fluids) {
		for (FluidBreatherPower power : PowerHolderComponent.getPowers(entity, FluidBreatherPower.class)) {
			if (power.isActive()) {
				for (Fluid fluid : fluids) {
					if (fluid.isIn(power.fluid)) return true;
				}
			}
		}
		return false;
	}

	public boolean touchingFluid() {
		BlockState state = this.entity.getBlockStateAtPos();
		if (state.isOf(Blocks.WATER_CAULDRON)) {
			if (Fluids.WATER.isIn(fluid)) return true;
		}
		else if (state.isOf(Blocks.LAVA_CAULDRON)) {
			if (Fluids.LAVA.isIn(fluid)) return true;
		}
		if (this.entity.getFluidHeight(fluid) > 0.0) return true;
		if (this.entity.world.getFluidState(this.entity.getBlockPos()).isIn(fluid)) return true;
		if (((EntityAccessor)this.entity).callIsBeingRainedOn() && Fluids.FLOWING_WATER.isIn(fluid)) return true;
		return false;
	}
}
