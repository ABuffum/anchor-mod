package haven.mixins;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChickenEntityModel.class)
public interface ChickenEntityModelAccessor {
	@Accessor("head")
	public abstract ModelPart getHead();
}
