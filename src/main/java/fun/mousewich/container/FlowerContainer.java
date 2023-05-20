package fun.mousewich.container;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

public class FlowerContainer extends PottedBlockContainer {
	public FlowerContainer(StatusEffect effect, int effectDuration, Block.Settings settings, Item.Settings itemSettings) {
		this(new FlowerBlock(effect, effectDuration, settings), itemSettings);
	}
	public FlowerContainer(FlowerBlock block, Item.Settings settings) { super(block, settings); }
	public FlowerContainer flammable(int burn, int spread) { return (FlowerContainer)super.flammable(burn, spread); }
	public FlowerContainer compostable(float chance) { return (FlowerContainer)super.compostable(chance); }
	public FlowerContainer dropSelf() { return (FlowerContainer)super.dropSelf(); }
	public FlowerContainer pottedModel() { return (FlowerContainer)super.pottedModel(); }
}