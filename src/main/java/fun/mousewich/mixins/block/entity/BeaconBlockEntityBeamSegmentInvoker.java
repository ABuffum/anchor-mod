package fun.mousewich.mixins.block.entity;

import net.minecraft.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeaconBlockEntity.BeamSegment.class)
public interface BeaconBlockEntityBeamSegmentInvoker {
	@Invoker("increaseHeight")
	void InvokeIncreaseHeight();
}
