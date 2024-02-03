package fun.wich.block.gourd;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class GourdStemBlock extends StemBlock {
	public GourdStemBlock(GourdBlock gourd, Supplier<Item> pickBlockItem, AbstractBlock block) {
		super(gourd, pickBlockItem, Settings.copy(block));
	}
	public GourdStemBlock(GourdBlock gourdBlock, Supplier<Item> pickBlockItem, Settings settings) {
		super(gourdBlock, pickBlockItem, settings);
	}
}
