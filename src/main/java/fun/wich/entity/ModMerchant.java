package fun.wich.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.*;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.*;
import net.minecraft.world.gen.feature.StructureFeature;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Assumes the implementing class extends Entity
 */
public interface ModMerchant extends Merchant {
	boolean hasCustomer();
	SoundEvent getTradingSound(boolean sold);
	SoundEvent getNoSound();
	void afterUsing(TradeOffer offer);

	boolean canRestock();
	boolean shouldRestock();
	void restock();
	default boolean needsRestock() {
		for (TradeOffer tradeOffer : this.getOffers()) {
			if (tradeOffer.hasBeenUsed()) return true;
		}
		return false;
	}
	default void updateDemandBonus() { for (TradeOffer tradeOffer : this.getOffers()) tradeOffer.updateDemandBonus(); }

	interface Factory { @Nullable TradeOffer create(Entity entity, Random random); }
	static Int2ObjectMap<Factory[]> copyToFastUtilMap(ImmutableMap<Integer, Factory[]> map) { return new Int2ObjectOpenHashMap<>(map); }

	class BuyForOneEmeraldFactory extends BuyForEmeraldsFactory {
		public BuyForOneEmeraldFactory(ItemConvertible item, int price, int maxUses, int experience) {
			super(item, price, 1, maxUses, experience);
		}
	}
	class BuyForEmeraldsFactory implements Factory {
		private final Item buy;
		private final int price;
		private final int payout;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForEmeraldsFactory(ItemConvertible item, int price, int payout, int maxUses, int experience) {
			this.buy = item.asItem();
			this.price = price;
			this.payout = payout;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new TradeOffer(itemStack, new ItemStack(Items.EMERALD, this.payout), this.maxUses, this.experience, this.multiplier);
		}
	}
	class SellItemFactory implements Factory {
		private final ItemStack sell;
		private final int price;
		private final int count;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public SellItemFactory(Block block, int price, int count, int maxUses, int experience) {
			this(new ItemStack(block), price, count, maxUses, experience);
		}

		public SellItemFactory(Item item, int price, int count, int experience) {
			this(new ItemStack(item), price, count, 12, experience);
		}

		public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
			this(new ItemStack(item), price, count, maxUses, experience);
		}

		public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
			this(stack, price, count, maxUses, experience, 0.05f);
		}

		public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
			this.sell = stack;
			this.price = price;
			this.count = count;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
		}
	}
	class SellSuspiciousStewFactory implements Factory {
		final StatusEffect effect;
		final int duration;
		final int experience;
		private final float multiplier;

		public SellSuspiciousStewFactory(StatusEffect effect, int duration, int experience) {
			this.effect = effect;
			this.duration = duration;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
			SuspiciousStewItem.addEffectToStew(itemStack, this.effect, this.duration);
			return new TradeOffer(new ItemStack(Items.EMERALD, 1), itemStack, 12, this.experience, this.multiplier);
		}
	}
	class ProcessItemFactory implements Factory {
		private final ItemStack secondBuy;
		private final int secondCount;
		private final int price;
		private final ItemStack sell;
		private final int sellCount;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public ProcessItemFactory(ItemConvertible item, int secondCount, Item sellItem, int sellCount, int maxUses, int experience) {
			this(item, secondCount, 1, sellItem, sellCount, maxUses, experience);
		}

		public ProcessItemFactory(ItemConvertible item, int secondCount, int price, Item sellItem, int sellCount, int maxUses, int experience) {
			this.secondBuy = new ItemStack(item);
			this.secondCount = secondCount;
			this.price = price;
			this.sell = new ItemStack(sellItem);
			this.sellCount = sellCount;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.secondBuy.getItem(), this.secondCount), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
		}
	}
	class SellEnchantedToolFactory implements Factory {
		private final ItemStack tool;
		private final int basePrice;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public SellEnchantedToolFactory(Item item, int basePrice, int maxUses, int experience) {
			this(item, basePrice, maxUses, experience, 0.05f);
		}

		public SellEnchantedToolFactory(Item item, int basePrice, int maxUses, int experience, float multiplier) {
			this.tool = new ItemStack(item);
			this.basePrice = basePrice;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			int i = 5 + random.nextInt(15);
			ItemStack itemStack = EnchantmentHelper.enchant(random, new ItemStack(this.tool.getItem()), i, false);
			int j = Math.min(this.basePrice + i, 64);
			ItemStack itemStack2 = new ItemStack(Items.EMERALD, j);
			return new TradeOffer(itemStack2, itemStack, this.maxUses, this.experience, this.multiplier);
		}
	}
	interface TypeAwareItemSupplier { ItemConvertible get(Entity entity); }
	class TypeAwareBuyForOneEmeraldFactory implements Factory {
		private final TypeAwareItemSupplier supplier;
		private final int count;
		private final int maxUses;
		private final int experience;

		public TypeAwareBuyForOneEmeraldFactory(int count, int maxUses, int experience, TypeAwareItemSupplier supplier) {
			this.supplier = supplier;
			this.count = count;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, Random random) {
			if (entity instanceof VillagerDataContainer data) {
				ItemStack itemStack = new ItemStack(supplier.get(entity), this.count);
				return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, 0.05f);
			}
			return null;
		}
	}
	class SellPotionHoldingItemFactory implements Factory {
		private final ItemStack sell;
		private final int sellCount;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final Item secondBuy;
		private final int secondCount;
		private final float priceMultiplier;
		private final Potion potion;

		public SellPotionHoldingItemFactory(Item secondBuy, int secondCount, Item potionHoldingItem, int sellCount, int price, int maxUses, int experience) {
			this(secondBuy, secondCount, potionHoldingItem, sellCount, price, maxUses, experience, null);
		}
		public SellPotionHoldingItemFactory(Item secondBuy, int secondCount, Item potionHoldingItem, int sellCount, int price, int maxUses, int experience, Potion potion) {
			this.sell = new ItemStack(potionHoldingItem);
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.secondBuy = secondBuy;
			this.secondCount = secondCount;
			this.sellCount = sellCount;
			this.priceMultiplier = 0.05f;
			this.potion = potion;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(Items.EMERALD, this.price);
			Potion appliedPotion = this.potion;
			if (appliedPotion == null) {
				List<Potion> list = Registry.POTION.stream().filter(potion -> !potion.getEffects().isEmpty() && BrewingRecipeRegistry.isBrewable(potion)).toList();
				appliedPotion = list.get(random.nextInt(list.size()));
			}
			ItemStack itemStack2 = PotionUtil.setPotion(new ItemStack(this.sell.getItem(), this.sellCount), appliedPotion);
			return new TradeOffer(itemStack, new ItemStack(this.secondBuy, this.secondCount), itemStack2, this.maxUses, this.experience, this.priceMultiplier);
		}
	}
	class EnchantBookFactory implements Factory {
		private final int experience;

		public EnchantBookFactory(int experience) { this.experience = experience; }

		@Override
		public TradeOffer create(Entity entity, Random random) {
			List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::isAvailableForEnchantedBookOffer).toList();
			Enchantment enchantment = list.get(random.nextInt(list.size()));
			int i = MathHelper.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
			ItemStack itemStack = EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, i));
			int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
			if (enchantment.isTreasure()) j *= 2;
			if (j > 64) j = 64;
			return new TradeOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemStack, 12, this.experience, 0.2f);
		}
	}
	class SellMapFactory implements Factory {
		private final int price;
		private final StructureFeature<?> structure;
		private final MapIcon.Type iconType;
		private final int maxUses;
		private final int experience;

		public SellMapFactory(int price, StructureFeature<?> feature, MapIcon.Type iconType, int maxUses, int experience) {
			this.price = price;
			this.structure = feature;
			this.iconType = iconType;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, Random random) {
			if (!(entity.world instanceof ServerWorld serverWorld)) {
				return null;
			}
			BlockPos blockPos = serverWorld.locateStructure(this.structure, entity.getBlockPos(), 100, true);
			if (blockPos != null) {
				ItemStack itemStack = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte)2, true, true);
				FilledMapItem.fillExplorationMap(serverWorld, itemStack);
				MapState.addDecorationsNbt(itemStack, blockPos, "+", this.iconType);
				itemStack.setCustomName(new TranslatableText("filled_map." + this.structure.getName().toLowerCase(Locale.ROOT)));
				return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(Items.COMPASS), itemStack, this.maxUses, this.experience, 0.2f);
			}
			return null;
		}
	}
	class SellDyedArmorFactory implements Factory {
		private final Item sell;
		private final int price;
		private final int maxUses;
		private final int experience;

		public SellDyedArmorFactory(Item item, int price) { this(item, price, 12, 1); }
		public SellDyedArmorFactory(Item item, int price, int maxUses, int experience) {
			this.sell = item;
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack itemStack = new ItemStack(Items.EMERALD, this.price);
			ItemStack itemStack2 = new ItemStack(this.sell);
			if (this.sell instanceof DyeableArmorItem) {
				ArrayList<DyeItem> list = Lists.newArrayList();
				list.add(SellDyedArmorFactory.getDye(random));
				if (random.nextFloat() > 0.7f) list.add(SellDyedArmorFactory.getDye(random));
				if (random.nextFloat() > 0.8f) list.add(SellDyedArmorFactory.getDye(random));
				itemStack2 = DyeableItem.blendAndSetColor(itemStack2, list);
			}
			return new TradeOffer(itemStack, itemStack2, this.maxUses, this.experience, 0.2f);
		}
		private static DyeItem getDye(Random random) { return DyeItem.byColor(DyeColor.byId(random.nextInt(16))); }
	}
}
