package fun.wich.gen.data.loot;

import fun.wich.block.ModProperties;
import fun.wich.gen.data.tag.ModItemTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Properties;

public interface DropTable {
	LootTable.Builder get(Block block);

	LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));

	LootCondition.Builder WITH_AXE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.AXES));
	LootCondition.Builder WITH_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.HOES));
	LootCondition.Builder WITH_KNIFE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.KNIVES));
	LootCondition.Builder WITH_PICKAXE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.PICKAXES));
	LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.SHEARS));
	LootCondition.Builder WITH_SHOVEL = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.SHOVELS));
	LootCondition.Builder WITH_SWORD = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ModItemTags.SWORDS));

	static DropTable Drops(ItemConvertible item) { return (block) -> BlockLootTableGenerator.drops(item); }
	static DropTable CandleCake(Block candle) { return (block) -> BlockLootTableGenerator.candleCakeDrops(candle); }
	static DropTable EndOre(Item ore) {
		return (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block,
				ItemEntry.builder(ore).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 6.0f)))
						.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
	}
	static DropTable Mushroom(ItemConvertible mushroom) { return (block) -> BlockLootTableGenerator.mushroomBlockDrops(block, mushroom); }
	static DropTable NetherOre(Item ore) {
		return (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block,
				ItemEntry.builder(ore).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 6.0f)))
						.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
	}
	static DropTable Ore(Item ore) { return (block) -> BlockLootTableGenerator.oreDrops(block, ore); }
	static DropTable Potted(ItemConvertible plant) { return (block) -> BlockLootTableGenerator.pottedPlantDrops(plant); }
	static DropTable Stem(ItemConvertible item) { return (block) -> BlockLootTableGenerator.attachedCropStemDrops(block, item.asItem()); }
	static DropTable WithSilkTouch(ItemConvertible item) { return (block) -> BlockLootTableGenerator.dropsWithSilkTouch(item); }

	static LootPoolEntry.Builder<?> MelonLikePool(ItemConvertible drop, ItemConvertible item, float min, float max, int totalMax) {
		return BlockLootTableGenerator.applyExplosionDecay(drop, ItemEntry.builder(item)
				.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
				.apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
				.apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMax(totalMax))));
	}
	static LootPoolEntry.Builder<?> MelonLikeSlabPool(Block block, ItemConvertible drop, ItemConvertible item, float min, float max, int totalMax) {
		return BlockLootTableGenerator.applyExplosionDecay(drop, ItemEntry.builder(item)
				.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
				.apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
				.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
						.conditionally(BlockStatePropertyLootCondition.builder(block)
								.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))
				))
				.apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMax(totalMax))
						.conditionally(BlockStatePropertyLootCondition.builder(block)
								.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))
				)
				.apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMax(totalMax / 2))
						.conditionally(BlockStatePropertyLootCondition.builder(block)
								.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.BOTTOM)))
				)
				.apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.createMax(totalMax / 2))
						.conditionally(BlockStatePropertyLootCondition.builder(block)
								.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.TOP)))
				);
	}
	static LootTable.Builder SlabPool(Block block, LootCondition.Builder condition, LootPoolEntry.Builder<?> child) {
		return LootTable.builder().pool(LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(1.0f))
				.with(BlockLootTableGenerator
						.applyExplosionDecay(block,
								ItemEntry.builder(block)
										.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
												.conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))
										)
						)
						.conditionally(condition)
						.alternatively(child)
				)
		);
	}
	static DropTable SilkTouchOrElse(ItemConvertible item) { return (block) -> BlockLootTableGenerator.drops(block, item); }
	static DropTable BerryDrops(Item berry) {
		return (block) -> LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(berry)).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Properties.BERRIES, true))));
	}

	DropTable BED = (block) -> BlockLootTableGenerator.dropsWithProperty(block, BedBlock.PART, BedPart.HEAD);
	DropTable BEEHIVE = (block) -> LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
			.with(ItemEntry.builder(block).conditionally(WITH_SILK_TOUCH)
					.apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
							.withOperation("Bees", "BlockEntityTag.Bees"))
					.apply(CopyStateFunction.getBuilder(block).addProperty(BeehiveBlock.HONEY_LEVEL))
					.alternatively(ItemEntry.builder(block))));
	DropTable BOOKSHELF = (block) -> BlockLootTableGenerator.drops(block, Items.BOOK, ConstantLootNumberProvider.create(3.0f));
	DropTable CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.CHARCOAL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
	DropTable CANDLE = (block) -> LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
			.with(BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(block)
					.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
							.conditionally(BlockStatePropertyLootCondition.builder(block)
									.properties(StatePredicate.Builder.create().exactMatch(CandleBlock.CANDLES, 2))))
					.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f))
							.conditionally(BlockStatePropertyLootCondition.builder(block)
									.properties(StatePredicate.Builder.create().exactMatch(CandleBlock.CANDLES, 3))))
					.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f))
							.conditionally(BlockStatePropertyLootCondition.builder(block)
									.properties(StatePredicate.Builder.create().exactMatch(CandleBlock.CANDLES, 4)))))));;
	DropTable DOOR = (block) -> BlockLootTableGenerator.dropsWithProperty(block, DoorBlock.HALF, DoubleBlockHalf.LOWER);
	DropTable ENDER_CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.POPPED_CHORUS_FRUIT).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
	DropTable FLINT = (Block block) -> BlockLootTableGenerator.drops(block, DropTable.WITH_PICKAXE, DropTable.MelonLikePool(block, Items.FLINT, 3, 7, 9));
	DropTable FLINT_SLAB = (Block block) -> SlabPool(block, DropTable.WITH_PICKAXE, MelonLikeSlabPool(block, block, Items.FLINT, 1.5f, 3.5f, 9));
	DropTable FOUR_COUNT = (Block block) -> LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
			.with(BlockLootTableGenerator.applyExplosionDecay(block,
					(ItemEntry.builder(block)
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
									.conditionally(BlockStatePropertyLootCondition.builder(block)
											.properties(StatePredicate.Builder.create()
													.exactMatch(ModProperties.COUNT_4, 2)))))
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f))
									.conditionally(BlockStatePropertyLootCondition.builder(block)
											.properties(StatePredicate.Builder.create()
													.exactMatch(ModProperties.COUNT_4, 3))))
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f))
									.conditionally(BlockStatePropertyLootCondition.builder(block)
											.properties(StatePredicate.Builder.create()
													.exactMatch(ModProperties.COUNT_4, 4)))))));
	DropTable NAMEABLE_CONTAINER = (block) -> LootTable.builder().pool(BlockLootTableGenerator
			.addSurvivesExplosionCondition(block, LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
					.with(ItemEntry.builder(block).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY)))));
	DropTable NOTHING = (block) -> BlockLootTableGenerator.dropsNothing();
	DropTable SILK_TOUCH_SLAB = (block) -> LootTable.builder().pool(LootPool.builder().conditionally(WITH_SILK_TOUCH)
			.rolls(ConstantLootNumberProvider.create(1.0f))
			.with(BlockLootTableGenerator
					.applyExplosionDecay(block, ItemEntry.builder(block)
							.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
									.conditionally(BlockStatePropertyLootCondition.builder(block)
											.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))))));
	DropTable SLAB = (block) -> LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
			.with(BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(block)
					.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))
							.conditionally(BlockStatePropertyLootCondition.builder(block)
									.properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE)))))));
	DropTable SOUL_CAMPFIRE = (block) -> BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block,
			ItemEntry.builder(Items.SOUL_SOIL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)))));
}
