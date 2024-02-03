package fun.wich.container;

import fun.wich.ModBase;
import fun.wich.block.basic.ModCakeBlock;
import fun.wich.block.basic.ModCandleCakeBlock;
import fun.wich.gen.data.ModDatagen;
import fun.wich.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CandleBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

import java.util.Map;

public class CakeContainer implements IContainer {
	public void onEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) { }
	private Block getCandleCake(CandleBlock candle) {
		if (candle == Blocks.CANDLE) return CANDLE_CAKE;
		else if (candle == ModBase.SOUL_CANDLE.asBlock()) return SOUL_CANDLE_CAKE;
		else if (candle == ModBase.ENDER_CANDLE.asBlock()) return ENDER_CANDLE_CAKE;
		else if (candle == ModBase.NETHERRACK_CANDLE.asBlock()) return NETHERRACK_CANDLE_CAKE;
		else if (candle == ModBase.BEIGE_CANDLE.asBlock()) return BEIGE_CANDLE_CAKE;
		else if (candle == ModBase.BURGUNDY_CANDLE.asBlock()) return BURGUNDY_CANDLE_CAKE;
		else if (candle == ModBase.LAVENDER_CANDLE.asBlock()) return LAVENDER_CANDLE_CAKE;
		else if (candle == ModBase.MINT_CANDLE.asBlock()) return MINT_CANDLE_CAKE;
		else return CANDLE_CAKES.get(ColorUtil.GetCandleColor(candle));
	}
	public final BlockContainer CAKE = new BlockContainer(new ModCakeBlock(this::getCandleCake) {
		@Override public void onEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
			CakeContainer.this.onEat(world, pos, state, player);
		}
	});
	public final Block CANDLE_CAKE = new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem).drops(Blocks.CANDLE);
	public final Block SOUL_CANDLE_CAKE = new ModCandleCakeBlock(2, CAKE::asBlock, CAKE::asItem) {
		@Override public boolean isSoulCandle() { return true; }
	}.drops(ModBase.SOUL_CANDLE.asBlock());
	public final Block ENDER_CANDLE_CAKE = new ModCandleCakeBlock(3, () -> Blocks.CAKE, () -> Items.CAKE) {
		@Override public boolean isEnderCandle() { return true; }
	}.drops(ModBase.ENDER_CANDLE.asBlock());
	public final Block NETHERRACK_CANDLE_CAKE = new ModCandleCakeBlock(3, CAKE::asBlock, CAKE::asItem) {
		@Override public boolean isNetherrackCandle() { return true; }
	}.drops(ModBase.NETHERRACK_CANDLE.asBlock());
	public final Map<DyeColor, Block> CANDLE_CAKES = ColorUtil.Map(color ->
			new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem)
					.drops(ColorUtil.GetCandleBlock(color)));
	public final Block BEIGE_CANDLE_CAKE = new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem).drops(ModBase.BEIGE_CANDLE.asBlock());
	public final Block BURGUNDY_CANDLE_CAKE = new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem).drops(ModBase.BURGUNDY_CANDLE.asBlock());
	public final Block LAVENDER_CANDLE_CAKE = new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem).drops(ModBase.LAVENDER_CANDLE.asBlock());
	public final Block MINT_CANDLE_CAKE = new ModCandleCakeBlock(4, CAKE::asBlock, CAKE::asItem).drops(ModBase.MINT_CANDLE.asBlock());

	public CakeContainer() {
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, SOUL_CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, ENDER_CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, NETHERRACK_CANDLE_CAKE);
		for (Block block : CANDLE_CAKES.values()) ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, block);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, BEIGE_CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, BURGUNDY_CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, LAVENDER_CANDLE_CAKE);
		ModDatagen.Cache.Tags.Register(BlockTags.CANDLE_CAKES, MINT_CANDLE_CAKE);
	}

	public CakeContainer compostable(float chance) {
		CAKE.compostable(chance);
		return this;
	}

	@Override
	public boolean contains(Block block) {
		return CAKE.contains(block) || block == CANDLE_CAKE || block == SOUL_CANDLE_CAKE || block == ENDER_CANDLE_CAKE
				|| block == NETHERRACK_CANDLE_CAKE || CANDLE_CAKES.containsValue(block)
				|| block == BEIGE_CANDLE_CAKE || block == BURGUNDY_CANDLE_CAKE || block == LAVENDER_CANDLE_CAKE || block == MINT_CANDLE_CAKE;
	}

	@Override
	public boolean contains(Item item) { return CAKE.contains(item); }
}
