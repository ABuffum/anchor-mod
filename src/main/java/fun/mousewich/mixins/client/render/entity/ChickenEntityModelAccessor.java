package fun.mousewich.mixins.client.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChickenEntityModel.class)
public interface ChickenEntityModelAccessor {
	@Accessor("head")
	ModelPart getHead();
}
