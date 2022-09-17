package haven.mixins.client.rendering.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BatEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BatEntityModel.class)
public interface BatEntityModelAccessor {
	@Accessor("head")
	public ModelPart getHead();
	@Accessor("body")
	public ModelPart getBody();
	@Accessor("rightWing")
	public ModelPart getRightWing();
	@Accessor("leftWing")
	public ModelPart getLeftWing();
	@Accessor("rightWingTip")
	public ModelPart getRightWingTip();
	@Accessor("leftWingTip")
	public ModelPart getLeftWingTip();
}
