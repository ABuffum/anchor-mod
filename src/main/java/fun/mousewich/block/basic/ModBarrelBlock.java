package fun.mousewich.block.basic;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;

import java.util.HashSet;
import java.util.Set;

public class ModBarrelBlock extends BarrelBlock {
	public static final Set<Block> BARRELS = new HashSet<>();

	public ModBarrelBlock(BlockConvertible block) { this(block.asBlock()); }
	public ModBarrelBlock(Block block) { this(Settings.copy(block)); }
	public ModBarrelBlock(Settings settings) {
		super(settings);
		BARRELS.add(this);
	}
}
