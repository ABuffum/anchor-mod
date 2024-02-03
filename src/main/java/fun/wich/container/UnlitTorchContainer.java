package fun.wich.container;

import fun.wich.block.BlockConvertible;
import fun.wich.block.torch.UnlitTorchBlock;
import fun.wich.block.torch.UnlitWallTorchBlock;
import fun.wich.gen.data.ModDatagen;
import fun.wich.gen.data.loot.DropTable;
import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.util.HashMap;
import java.util.Map;

public class UnlitTorchContainer implements IContainer, BlockConvertible {
	private final UnlitTorchBlock block;
	public UnlitTorchBlock asBlock() { return block; }
	private final UnlitWallTorchBlock wallBlock;
	public UnlitWallTorchBlock getWallBlock() { return wallBlock; }

	public UnlitTorchContainer(TorchContainer torch) {
		this(new UnlitTorchBlock((TorchBlock)torch.asBlock()), new UnlitWallTorchBlock((WallTorchBlock)torch.getWallBlock()));
	}
	public UnlitTorchContainer(UnlitTorchBlock block, UnlitWallTorchBlock wallBlock) {
		this.block = block;
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return false; }

	public static Map<Block, Block> UNLIT_TO_LIT_TORCHES = new HashMap<Block, Block>();
	public static Map<Block, Block> UNLIT_TO_LIT_WALL_TORCHES = new HashMap<Block, Block>();

	public UnlitTorchContainer drops(ItemConvertible item) {
		ModDatagen.Cache.Drops.put(this.block, DropTable.Drops(item));
		ModDatagen.Cache.Drops.put(this.wallBlock, DropTable.Drops(item));
		return this;
	}
}
