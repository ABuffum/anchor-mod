package haven.mixins.client.rendering;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BedBlockEntityRenderer.class)
public interface BedBlockEntityRendererAccessor {
	@Accessor("bedHead")
	public ModelPart getBedHead();
	@Accessor("bedFoot")
	public ModelPart getBedFoot();
}
