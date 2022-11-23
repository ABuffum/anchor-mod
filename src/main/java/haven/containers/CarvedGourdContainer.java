package haven.containers;

import haven.blocks.gourd.CarvableGourdBlock;
import haven.blocks.gourd.CarvedGourdBlock;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;

public class CarvedGourdContainer {
	private final BlockContainer gourd;
	public BlockContainer getGourd() { return gourd; }
	private final BlockContainer carved;
	public BlockContainer getCarved() { return carved; }
	private final GourdContainer.StemContainer stemContainer;
	public StemBlock getStem() { return stemContainer.getStem(); }
	public AttachedStemBlock getAttachedStem() { return stemContainer.getAttached(); }
	public Item getSeeds() { return stemContainer.getSeeds(); }

	public CarvedGourdContainer(Block carved, Block lantern, SoundEvent carveSound, AbstractBlock.Settings gourdSettings, AbstractBlock.Settings stemSettings, AbstractBlock.Settings attachedStemSettings, Item.Settings itemSettings) {
		this.carved = new BlockContainer(carved, itemSettings);
		gourd = new BlockContainer(new CarvableGourdBlock(gourdSettings, carveSound, this::getStem, this::getAttachedStem, this::getCarvedBlock, () -> new ItemStack(getSeeds(), 4)), itemSettings);
		stemContainer = new GourdContainer.StemContainer((GourdBlock) gourd.getBlock(), stemSettings, attachedStemSettings, itemSettings);
	}

	private CarvedGourdBlock getCarvedBlock() { return (CarvedGourdBlock) carved.getBlock(); }
}
