package fun.mousewich.block.plushie;

import fun.mousewich.gen.data.model.ModModels;
import net.minecraft.data.client.model.Model;

public class FancyChickenPlushieBlock extends ChickenPlushieBlock {
	public FancyChickenPlushieBlock(Settings settings) { super(settings); }
	@Override
	public Model getModel() { return ModModels.TEMPLATE_PLUSHIE_FANCY_CHICKEN; }
}
