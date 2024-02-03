package fun.wich.container;

import fun.wich.block.gourd.CarvableGourdBlock;
import fun.wich.block.gourd.CarvedGourdBlock;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;

public class CarvedGourdContainer {
	private final BlockContainer gourd;
	public BlockContainer getGourd() { return gourd; }
	private final BlockContainer carved;
	public BlockContainer getCarved() { return carved; }
	private final GourdStemContainer stemContainer;
	public StemBlock getStem() { return stemContainer.getStem(); }
	public AttachedStemBlock getAttachedStem() { return stemContainer.getAttached(); }
	public Item getSeeds() { return stemContainer.getSeeds(); }

	public CarvedGourdContainer(Block carved, SoundEvent carveSound, AbstractBlock.Settings gourdSettings, AbstractBlock.Settings stemSettings, AbstractBlock.Settings attachedStemSettings) {
		this.carved = new BlockContainer(carved);
		gourd = new BlockContainer(new CarvableGourdBlock(gourdSettings, carveSound, this::getStem, this::getAttachedStem, this::getCarvedBlock, () -> new ItemStack(getSeeds(), 4))).dropSelf();
		stemContainer = new GourdStemContainer((GourdBlock)gourd.asBlock(), stemSettings, attachedStemSettings);
	}
	private CarvedGourdBlock getCarvedBlock() { return (CarvedGourdBlock) carved.asBlock(); }
}
