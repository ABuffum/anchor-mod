package fun.wich.mixins.client.render.entity.model;

import fun.wich.client.render.entity.model.ModVexEntityModel;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.VexEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(VexEntityModel.class)
public class VexEntityModelMixin {
	/**
	 * @author mousewich
	 * @reason new vex renderer from 1.20
	 */
	@Overwrite()
	public static TexturedModelData getTexturedModelData() { return ModVexEntityModel.getTexturedModelData(); }
}
