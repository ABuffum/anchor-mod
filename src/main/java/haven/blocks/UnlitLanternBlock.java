package haven.blocks;

import haven.containers.BlockContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class UnlitLanternBlock extends LanternBlock {
	private LanternSupplier getPickStack;
	public UnlitLanternBlock(Supplier<BlockContainer> getBlock) { this(getBlock, ItemSettings()); }
	public UnlitLanternBlock(Supplier<BlockContainer> getBlock, Settings settings) { this(() -> getBlock.get().getItem(), settings); }
	public UnlitLanternBlock(LanternSupplier getPickStack) { this(getPickStack, ItemSettings()); }
	public UnlitLanternBlock(LanternSupplier getPickStack, Settings settings) {
		super(settings);
		this.getPickStack = getPickStack;
	}
	public UnlitLanternBlock(Item pickStack, Settings settings) { this(() -> pickStack, settings); }

	public static interface LanternSupplier{
		public Item get();
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return getPickStack.get().getDefaultStack(); }

	public static AbstractBlock.Settings ItemSettings() {
		return AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).nonOpaque();
	}
}
