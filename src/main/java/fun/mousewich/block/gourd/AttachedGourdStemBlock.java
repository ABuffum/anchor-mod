package fun.mousewich.block.gourd;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class AttachedGourdStemBlock extends AttachedStemBlock {
	public AttachedGourdStemBlock(GourdBlock gourd, Supplier<Item> pickBlockItem, AbstractBlock block) {
		super(gourd, pickBlockItem, Settings.copy(block));
	}
	public AttachedGourdStemBlock(GourdBlock gourd, Supplier<Item> pickBlockItem, Settings settings) {
		super(gourd, pickBlockItem, settings);
	}
}
