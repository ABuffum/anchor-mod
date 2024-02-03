package fun.wich.mixins.entity.decoration;

import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ArmorStandEntity.class)
public interface ArmorStandEntityAccessor {
	@Invoker("setHideBasePlate")
	void SetHideBasePlate(boolean hideBasePlate);
	@Invoker("setShowArms")
	void SetShowArms(boolean showArms);
}
