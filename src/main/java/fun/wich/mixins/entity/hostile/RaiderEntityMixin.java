package fun.wich.mixins.entity.hostile;

import com.google.common.collect.Maps;
import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.ModMerchant;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.ai.goal.StopAndLookAtTradingEntityGoal;
import fun.wich.origins.power.EnableTradePower;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RaiderEntity.class)
public abstract class RaiderEntityMixin extends PatrolEntity implements ModMerchant {
	@Nullable private PlayerEntity customer;

	protected RaiderEntityMixin(EntityType<? extends PatrolEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at=@At("HEAD"))
	private void AddMerchantGoals(CallbackInfo ci) {
		//Restock
		this.goalSelector.add(1, new Goal() {
			public boolean canStart() {
				return RaiderEntityMixin.this.canRestock();
			}
			public void start() {
				if (RaiderEntityMixin.this.shouldRestock()) RaiderEntityMixin.this.restock();
			}
		});
		//Stop Following Customer Goal
		this.goalSelector.add(1, new Goal() {
			@Override
			public boolean canStart() {
				PatrolEntity entity = RaiderEntityMixin.this;
				if (!entity.isAlive()) return false;
				if (!entity.isAlive()) return false;
				if (entity.isTouchingWater()) return false;
				if (!entity.isOnGround()) return false;
				if (entity.velocityModified) return false;
				PlayerEntity playerEntity = ((ModMerchant)entity).getCurrentCustomer();
				if (playerEntity == null) return false;
				if (entity.squaredDistanceTo(playerEntity) > 16.0) return false;
				return playerEntity.currentScreenHandler != null;
			}
			@Override
			public void start() { RaiderEntityMixin.this.getNavigation().stop(); }
			@Override
			public void stop() { RaiderEntityMixin.this.setCurrentCustomer(null); }
		});
		//Look at Customer Goal
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8) {
			@Override
			public boolean canStart() {
				ModMerchant merchant = RaiderEntityMixin.this;
				if (merchant.hasCustomer()) {
					this.target = merchant.getCurrentCustomer();
					return true;
				}
				return false;
			}
		});
		this.goalSelector.add(7, new StopAndLookAtTradingEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
	}
	@Override
	public void setCurrentCustomer(@Nullable PlayerEntity customer) { this.customer = customer; }
	@Override @Nullable
	public PlayerEntity getCurrentCustomer() { return this.customer; }
	@Override
	public boolean hasCustomer() { return this.customer != null; }

	@Nullable
	protected TradeOfferList offers;

	@Override
	public TradeOfferList getOffers() {
		if (this.offers == null) {
			this.offers = new TradeOfferList();
			EntityType<?> type = this.getType();
			Factory[] factories = TYPE_TO_TRADE_MAP.getOrDefault(type, null);
			if (factories != null) {
				for (Factory factory : factories) {
					this.offers.add(factory.create(this, getRandom()));
				}
			}
		}
		TradeOfferList tradeOfferList = new TradeOfferList();
		tradeOfferList.addAll(this.offers);
		//If disarmed, add an offer to rearm
		if (this.getMainHandStack().isEmpty()) {
			EntityType<?> type = this.getType();
			ItemStack rearm = null;
			if (type == EntityType.PILLAGER) rearm = new ItemStack(Items.CROSSBOW);
			else if (type == EntityType.VINDICATOR) rearm = new ItemStack(Items.IRON_AXE);
			else if (type == EntityType.ILLUSIONER) rearm = new ItemStack(Items.BOW);
			if (rearm != null) {
				ItemStack finalRearm = rearm;
				if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getOriginalFirstBuyItem().isOf(finalRearm.getItem()))) {
					tradeOfferList.add(new TradeOffer(rearm, new ItemStack(Items.EMERALD, 8), 1, 10, 0.05f));
				}
			}
			if (type == ModEntityType.MOUNTAINEER_ENTITY) {
				if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getOriginalFirstBuyItem().isOf(ModBase.MOUNTAINEER_AXE))) {
					tradeOfferList.add(new TradeOffer(new ItemStack(ModBase.MOUNTAINEER_AXE), new ItemStack(Items.EMERALD, 8), 1, 10, 0.05f));
				}
				if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getOriginalFirstBuyItem().isOf(ModBase.GOLD_MOUNTAINEER_AXE))) {
					tradeOfferList.add(new TradeOffer(new ItemStack(ModBase.GOLD_MOUNTAINEER_AXE), new ItemStack(Items.EMERALD, 8), 1, 10, 0.05f));
				}
				if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getOriginalFirstBuyItem().isOf(ModBase.DIAMOND_MOUNTAINEER_AXE))) {
					tradeOfferList.add(new TradeOffer(new ItemStack(ModBase.DIAMOND_MOUNTAINEER_AXE), new ItemStack(Items.EMERALD, 8), 1, 10, 0.05f));
				}
			}
		}
		//Allow raid captain promotions / demotions
		ItemStack OMINOUS_BANNER = Raid.getOminousBanner();
		ItemStack stack = this.getEquippedStack(EquipmentSlot.HEAD);
		if (!stack.isEmpty() && ItemStack.areEqual(stack, OMINOUS_BANNER)) {
			if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getSellItem().isOf(Items.WHITE_BANNER))) {
				tradeOfferList.add(new TradeOffer(new ItemStack(Items.EMERALD, 16), OMINOUS_BANNER, 1, 10, 0.05f));
			}
		}
		else {
			if (tradeOfferList.stream().noneMatch(tradeOffer -> tradeOffer.getOriginalFirstBuyItem().isOf(Items.WHITE_BANNER))) {
				tradeOfferList.add(new TradeOffer(OMINOUS_BANNER, new ItemStack(Items.EMERALD, 4), 1, 10, 0.05f));
			}
		}
		return tradeOfferList;
	}

	@Override
	public void setOffersFromServer(@Nullable TradeOfferList offers) { }

	private static final Map<EntityType<?>, Pair<SoundEvent, SoundEvent>> TYPE_TO_SOUND_MAP = Util.make(Maps.newHashMap(), map -> {
		map.put(EntityType.PILLAGER, new Pair<>(ModSoundEvents.ENTITY_PILLAGER_YES, ModSoundEvents.ENTITY_PILLAGER_NO));
		map.put(EntityType.VINDICATOR, new Pair<>(ModSoundEvents.ENTITY_VINDICATOR_YES, ModSoundEvents.ENTITY_VINDICATOR_NO));
		map.put(EntityType.ILLUSIONER, new Pair<>(ModSoundEvents.ENTITY_ILLUSIONER_YES, ModSoundEvents.ENTITY_ILLUSIONER_NO));
		map.put(EntityType.EVOKER, new Pair<>(ModSoundEvents.ENTITY_EVOKER_YES, ModSoundEvents.ENTITY_EVOKER_NO));
		map.put(EntityType.WITCH, new Pair<>(ModSoundEvents.ENTITY_WITCH_YES, ModSoundEvents.ENTITY_WITCH_NO));
		map.put(ModEntityType.ICEOLOGER_ENTITY, new Pair<>(ModSoundEvents.ENTITY_ICEOLOGER_YES, ModSoundEvents.ENTITY_ICEOLOGER_NO));
		map.put(ModEntityType.MOUNTAINEER_ENTITY, new Pair<>(ModSoundEvents.ENTITY_MOUNTAINEER_YES, ModSoundEvents.ENTITY_MOUNTAINEER_NO));
		map.put(ModEntityType.MAGE_ENTITY, new Pair<>(ModSoundEvents.ENTITY_MAGE_YES, ModSoundEvents.ENTITY_MAGE_NO));
	});

	private static final Map<EntityType<?>, Factory[]> TYPE_TO_TRADE_MAP = Util.make(Maps.newHashMap(), map -> {
		map.put(EntityType.PILLAGER, new Factory[] {
				new SellItemFactory(Items.ARROW, 1, 16, 1),
				new SellItemFactory(Items.CROSSBOW, 3, 1, 10),
				new SellEnchantedToolFactory(Items.CROSSBOW, 3, 3, 15)
		});
		map.put(EntityType.VINDICATOR, new Factory[] {
				new SellItemFactory(Items.IRON_AXE, 3, 1, 1),
				new SellEnchantedToolFactory(Items.IRON_AXE, 1, 3, 10, 0.2f)
		});
		map.put(EntityType.ILLUSIONER, new Factory[] {
				new SellItemFactory(Items.ARROW, 1, 16, 1),
				new SellItemFactory(Items.BOW, 2, 1, 5),
				new SellEnchantedToolFactory(Items.BOW, 2, 3, 15),
				new SellItemFactory(Items.SPECTRAL_ARROW, 1, 8, 10),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.POTION, 1, 8, 4, 30, Potions.INVISIBILITY)
		});
		map.put(EntityType.EVOKER, new Factory[] {
				new SellItemFactory(Items.TOTEM_OF_UNDYING, 64, 1, 1, 50),
				new EnchantBookFactory(1),
				new EnchantBookFactory(5)
		});
		map.put(EntityType.WITCH, new Factory[] {
				new SellItemFactory(Items.REDSTONE, 1, 2, 1),
				new SellItemFactory(Items.GLOWSTONE_DUST, 1, 2, 1),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.POTION, 1, 8, 4, 30, Potions.HEALING),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.POTION, 1, 8, 4, 30, Potions.FIRE_RESISTANCE),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.POTION, 1, 8, 4, 30, Potions.SWIFTNESS),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.POTION, 1, 8, 4, 30, Potions.WATER_BREATHING),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.SPLASH_POTION, 1, 12, 4, 30, Potions.SLOWNESS),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.SPLASH_POTION, 1, 12, 4, 30, Potions.POISON),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.SPLASH_POTION, 1, 12, 4, 30, Potions.WEAKNESS),
				new SellPotionHoldingItemFactory(Items.GLASS_BOTTLE, 1, Items.SPLASH_POTION, 1, 12, 4, 30, Potions.HARMING)
		});
		map.put(ModEntityType.ICEOLOGER_ENTITY, new Factory[] {
				new SellItemFactory(Items.SNOWBALL, 1, 16, 12, 1),
				new SellItemFactory(Items.ICE, 1, 1, 16, 2),
				new SellItemFactory(Items.PACKED_ICE, 2, 1, 8, 4),
				new SellItemFactory(Items.BLUE_ICE, 4, 1, 4, 8),
				new SellItemFactory(Items.POWDER_SNOW_BUCKET, 6, 1, 6, 10)
		});
		map.put(ModEntityType.MOUNTAINEER_ENTITY, new Factory[] {
				new SellItemFactory(Items.ICE, 1, 1, 16, 2),
				new SellItemFactory(Items.PACKED_ICE, 2, 1, 8, 4),
				new SellItemFactory(Items.STONE, 1, 8, 8, 2),
				new SellItemFactory(ModBase.MOUNTAINEER_AXE, 4, 1, 1),
				new SellEnchantedToolFactory(ModBase.MOUNTAINEER_AXE, 2, 3, 10, 0.2f)
		});
		map.put(ModEntityType.MAGE_ENTITY, new Factory[] {
				new EnchantBookFactory(1),
				new EnchantBookFactory(5),
				new EnchantBookFactory(10),
				new EnchantBookFactory(15)
		});
	});

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (EnableTradePower.canTrade(player, getType()) && (!(this instanceof VindicatorEntityAccessor vindicator) || !vindicator.IsJohnny())) {
			ItemStack itemStack = player.getStackInHand(hand);
			if (!itemStack.isOf(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.hasCustomer() && !this.isBaby()) {
				boolean bl = this.getOffers().isEmpty();
				if (hand == Hand.MAIN_HAND) {
					if (bl && !this.world.isClient) this.sayNo();
					player.incrementStat(Stats.TALKED_TO_VILLAGER);
				}
				if (bl) return ActionResult.success(this.world.isClient);
				if (!this.world.isClient) {
					this.setCurrentCustomer(player);
					this.sendOffers(player, this.getDisplayName(), 1);
				}
				return ActionResult.success(this.world.isClient);
			}
		}
		return super.interactMob(player, hand);
	}

	private void sayNo() {
		//this.setHeadRollingTimeLeft(40);
		if (!this.world.isClient()) {
			this.playSound(getNoSound(), this.getSoundVolume(), this.getSoundPitch());
		}
	}

	@Override
	public void trade(TradeOffer offer) {
		offer.use();
		this.ambientSoundChance = -this.getMinAmbientSoundDelay();
		this.afterUsing(offer);
		//if (this.customer instanceof ServerPlayerEntity) Criteria.VILLAGER_TRADE.trigger((ServerPlayerEntity)this.customer, this, offer.getSellItem());
	}
	@Override
	public void afterUsing(TradeOffer offer) {
		if (offer.shouldRewardPlayerExperience()) {
			int i = 3 + this.random.nextInt(4);
			this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY() + 0.5, this.getZ(), i));
		}
		//If disarmed and buying a weapon
		if (this.getMainHandStack().isEmpty()) {
			EntityType<?> type = this.getType();
			ItemStack buy = offer.getAdjustedFirstBuyItem();
			if (type == EntityType.PILLAGER) {
				if (buy.isOf(Items.CROSSBOW)) this.equipStack(EquipmentSlot.MAINHAND, buy);
			}
			else if (type == EntityType.VINDICATOR) {
				if (buy.isOf(Items.IRON_AXE)) this.equipStack(EquipmentSlot.MAINHAND, buy);
			}
			else if (type == EntityType.ILLUSIONER) {
				if (buy.isOf(Items.BOW)) this.equipStack(EquipmentSlot.MAINHAND, buy);
			}
		}
		//If trading an ominous banner
		ItemStack OMINOUS_BANNER = Raid.getOminousBanner();
		ItemStack headStack = this.getEquippedStack(EquipmentSlot.HEAD);
		if (headStack.isEmpty()) {
			//If buying an ominous banner
			if (ItemStack.areEqual(offer.getOriginalFirstBuyItem(), OMINOUS_BANNER)) {
				this.equipStack(EquipmentSlot.HEAD, OMINOUS_BANNER);
				this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
			}
		}
		else if (ItemStack.areEqual(headStack, OMINOUS_BANNER)) {
			//If selling an ominous banner
			if (ItemStack.areEqual(offer.getSellItem(), OMINOUS_BANNER)) {
				this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
			}
		}
	}
	@Override
	public void onSellingItem(ItemStack stack) {
		if (!this.world.isClient && this.ambientSoundChance > -this.getMinAmbientSoundDelay() + 20) {
			this.ambientSoundChance = -this.getMinAmbientSoundDelay();
			this.playSound(this.getTradingSound(!stack.isEmpty()), this.getSoundVolume(), this.getSoundPitch());
		}
	}
	@Override
	public SoundEvent getTradingSound(boolean sold) { return sold ? getYesSound() : getNoSound(); }
	@Override
	public SoundEvent getYesSound() {
		Pair<SoundEvent, SoundEvent> pair = TYPE_TO_SOUND_MAP.getOrDefault(this.getType(), null);
		return pair != null ? pair.getLeft() : SoundEvents.ENTITY_VILLAGER_YES;
	}
	@Override
	public SoundEvent getNoSound() {
		Pair<SoundEvent, SoundEvent> pair = TYPE_TO_SOUND_MAP.getOrDefault(this.getType(), null);
		return pair != null ? pair.getRight() : SoundEvents.ENTITY_VILLAGER_NO;
	}
	@Override
	public int getExperience() { return 0; }
	@Override
	public void setExperienceFromServer(int experience) { }
	@Override
	public boolean isLeveledMerchant() { return false; }
	@Override
	public World getMerchantWorld() { return this.world; }

	private long lastRestockTime;
	private int restocksToday;
	private long lastRestockCheckTime;

	@Override
	public void restock() {
		this.updateDemandBonus();
		for (TradeOffer tradeOffer : this.getOffers()) tradeOffer.resetUses();
		this.lastRestockTime = this.world.getTime();
		++this.restocksToday;
	}
	@Override
	public boolean canRestock() {
		return this.restocksToday == 0 || this.restocksToday < 2 && this.world.getTime() > this.lastRestockTime + 2400L;
	}
	@Override
	public boolean shouldRestock() {
		long m = this.world.getTime();
		boolean bl = m > (this.lastRestockTime + 12000L);
		long n = this.world.getTimeOfDay();
		if (this.lastRestockCheckTime > 0L) bl |= (n / 24000L) > (this.lastRestockCheckTime / 24000L);
		this.lastRestockCheckTime = n;
		if (bl) {
			this.lastRestockTime = m;
			int i = 2 - this.restocksToday;
			if (i > 0) {
				for (TradeOffer tradeOffer : this.getOffers()) tradeOffer.resetUses();
			}
			for (int j = 0; j < i; ++j) this.updateDemandBonus();
			this.restocksToday = 0;
		}
		return this.canRestock() && this.needsRestock();
	}

	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	public void WriteOffersToNbt(NbtCompound nbt, CallbackInfo ci) {
		TradeOfferList tradeOfferList = this.getOffers();
		if (!tradeOfferList.isEmpty()) {
			nbt.put(ModNbtKeys.OFFERS, tradeOfferList.toNbt());
			nbt.putLong(ModNbtKeys.LAST_RESTOCK, this.lastRestockTime);
			nbt.putInt(ModNbtKeys.RESTOCKS_TODAY, this.restocksToday);
		}
	}

	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	public void ReadOffersFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains(ModNbtKeys.OFFERS, 10)) {
			this.offers = new TradeOfferList(nbt.getCompound(ModNbtKeys.OFFERS));
			this.lastRestockTime = nbt.getLong(ModNbtKeys.LAST_RESTOCK);
			this.restocksToday = nbt.getInt(ModNbtKeys.RESTOCKS_TODAY);
		}
	}

	@Override
	public boolean canRefreshTrades() { return true; }
}
