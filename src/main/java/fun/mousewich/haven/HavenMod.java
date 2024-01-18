package fun.mousewich.haven;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import fun.mousewich.ModBase;
import fun.mousewich.ModFactory;
import fun.mousewich.ModId;
import fun.mousewich.block.decoration.*;
import fun.mousewich.block.plushie.BatPlushieBlock;
import fun.mousewich.block.tnt.*;
import fun.mousewich.container.FlowerContainer;
import fun.mousewich.container.FlowerPartContainer;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.effect.ModStatusEffect;
import fun.mousewich.effect.ModStatusEffects;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.projectile.BottledLightningEntity;
import fun.mousewich.entity.projectile.JavelinEntity;
import fun.mousewich.haven.block.*;
import fun.mousewich.haven.block.tnt.*;
import fun.mousewich.haven.effect.*;
import fun.mousewich.haven.entity.AngelBatEntity;
import fun.mousewich.haven.block.anchor.*;
import fun.mousewich.container.BlockContainer;
import fun.mousewich.haven.entity.VectortechJavelinEntity;
import fun.mousewich.haven.entity.tnt.*;
import fun.mousewich.haven.item.*;
import fun.mousewich.haven.origins.power.SwapWithLastVectortechJavelinPower;
import fun.mousewich.item.ConditionalLoreItem;
import fun.mousewich.item.JavelinItem;
import fun.mousewich.item.consumable.BottledDrinkItem;
import fun.mousewich.item.syringe.BloodSyringeItem;
import fun.mousewich.item.syringe.SyringeItem;
import fun.mousewich.item.tool.ModSwordItem;
import fun.mousewich.material.ModArmorMaterials;
import fun.mousewich.origins.power.PowersUtil;
import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.ColorUtil;
import io.github.apace100.origins.origin.Origin;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static fun.mousewich.ModBase.*;
import static fun.mousewich.ModFactory.*;
import static fun.mousewich.registry.ModRegistry.Register;
import static java.util.Map.entry;

public class HavenMod {
	public static final String NAMESPACE = "haven";

	public static final String AMBER = "Amber";
	public static final String AMBER_s = "Amber's";
	public static final String LUX_s = "Lux's";
	public static final String SOLEIL = "Soleil";
	public static final String SOLEIL_s = "Soleil's";
	public static final String VECTORTECH = "Vectortech";

	//<editor-fold desc="Sound Events">
	public static final SoundEvent CHUNKEATER_TNT_PRIMED = ModSoundEvents.registerSoundEvent("entity.chunkeater_tnt.primed");
	public static final SoundEvent DEVOURING_TNT_PRIMED = ModSoundEvents.registerSoundEvent("entity.devouring_tnt.primed");
	public static final SoundEvent SHARP_TNT_PRIMED = ModSoundEvents.registerSoundEvent("entity.sharp_tnt.primed");
	public static final SoundEvent SOFT_TNT_PRIMED = ModSoundEvents.registerSoundEvent("entity.soft_tnt.primed");
	public static final SoundEvent VIOLENT_TNT_PRIMED = ModSoundEvents.registerSoundEvent("entity.violent_tnt.primed");
	//Misc
	public static final SoundEvent MIASMA_COMING = ModSoundEvents.registerSoundEvent("ambient.miasma_coming");
	//</editor-fold>

	//<editor-fold desc="Anchors">
	public static final BlockContainer ANCHOR = new BlockContainer(new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f))).dropSelf();
	public static final BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR.asBlock()).build(null);
	public static final BlockContainer BROKEN_ANCHOR = new BlockContainer(new BrokenAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f))).dropSelf();
	public static final BlockEntityType<BrokenAnchorBlockEntity> BROKEN_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(BrokenAnchorBlockEntity::new, BROKEN_ANCHOR.asBlock()).build(null);
	public static final Block SUBSTITUTE_ANCHOR_BLOCK = new SubstituteAnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));
	public static final BlockEntityType<SubstituteAnchorBlockEntity> SUBSTITUTE_ANCHOR_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SubstituteAnchorBlockEntity::new, SUBSTITUTE_ANCHOR_BLOCK).build(null);

	public static Map<Integer, String> ANCHOR_MAP = Map.ofEntries(
			entry(1, "jackdaw"), entry(2, "august"), entry(3, "bird"), entry(4, "moth"),
			entry(5, "rat"), entry(6, "stars"), entry(7, "whimsy"), entry(8, "aster"),
			entry(9, "gawain"), entry(10, "sleep"), entry(11, "lux"), entry(12, "sylph"),
			entry(13, "angel"), entry(14, "captain"), entry(15, "oz"), entry(16, "navn"),
			entry(17, "amber"), entry(18, "kota"), entry(19, "rhys"), entry(20, "soleil"),
			entry(21, "dj"), entry(22, "miasma"), entry(23, "k"),
			Map.entry(24, "bones"), Map.entry(25, "citadel"), Map.entry(26, "ares"),
			Map.entry(27, "keltzy"), Map.entry(28, "dasner"), Map.entry(29, "skirmish"),
			Map.entry(30, "tree"), Map.entry(31, "ferris")
	);
	public static final Map<Integer, AnchorCoreItem> ANCHOR_CORES = new HashMap<>();
	public static final Map<Integer, BrokenAnchorCoreItem> BROKEN_ANCHOR_CORES = new HashMap<>(Map.ofEntries(
			entry(20, GeneratedItem(new BrokenAnchorCoreItem(20)))
	));
	//</editor-fold>
	//<editor-fold desc="Blood Types">
	public static final BloodType ANEMIC_BLOOD_TYPE = BloodType.Register(NAMESPACE, "anemic");
	public static final BloodType BEE_ENDERMAN_BLOOD_TYPE = BloodType.Register(NAMESPACE, "bee_enderman");
	public static final BloodType CONFETTI_BLOOD_TYPE = BloodType.Register(NAMESPACE, "confetti");
	public static final BloodType DISEASED_CAT_BLOOD_TYPE = BloodType.Register(NAMESPACE, "diseased_cat");
	public static final BloodType ICHOR_BLOOD_TYPE = BloodType.Register(NAMESPACE, "ichor");
	public static final BloodType NEPHAL_BLOOD_TYPE = BloodType.Register(NAMESPACE, "nephal");
	public static final BloodType NETHER_ROYALTY_BLOOD_TYPE = BloodType.Register(NAMESPACE, "nether_royalty");
	public static final BloodType SLUDGE_BLOOD_TYPE = BloodType.Register(NAMESPACE, "sludge");
	//</editor-fold>

	//<editor-fold desc="Alcatraz">
	public static final BlockContainer SHARP_TNT = new BlockContainer(new SharpTntBlock(ModFactory.TntSettings(MapColor.BLACK)));
	public static final EntityType<SharpTntEntity> SHARP_TNT_ENTITY = FabricEntityTypeBuilder.<SharpTntEntity>create(SpawnGroup.MISC, SharpTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Amber">
	public static final Item AMBER_EYE = ParentedItem(new AmberEyeItem(ItemSettings().maxCount(1)), PURPLE_ENDER_EYE);
	public static final Block AMBER_EYE_END_PORTAL_FRAME = new AmberEyeEndPortalFrameBlock(Block.Settings.of(Material.STONE, MapColor.PURPLE).sounds(BlockSoundGroup.GLASS).luminance(ModFactory.LUMINANCE_1).strength(-1.0F, 3600000.0F));
	public static final Item BEE_ENDERMAN_BLOOD_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == BEE_ENDERMAN_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else if (bloodType == ModBase.BEE_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else {
			if (bloodType == ModBase.ENDERMAN_BLOOD_TYPE || bloodType == ModBase.ENDER_DRAGON_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
			else entity.damage(ModDamageSource.Injected(user, BEE_ENDERMAN_BLOOD_TYPE), 1);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
		}
	}));
	//</editor-fold>
	//<editor-fold desc="Anathema">
	public static final StatusEffect BONE_ROT_EFFECT = new BoneRotEffect();
	public static final StatusEffect DETERIORATION_EFFECT = new DeteriorationEffect().milkImmune();
	public static final StatusEffect MARKED_EFFECT = new ModStatusEffect(StatusEffectType.NEUTRAL,0xFF00FF).milkImmune();
	//</editor-fold>
	//<editor-fold desc="Angel">
	public static final EntityType<AngelBatEntity> ANGEL_BAT_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AngelBatEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.9F)).trackRangeChunks(5).build();
	public static final BlockContainer ANGEL_BAT_PLUSHIE = MakePlushie(BatPlushieBlock::new);
	//</editor-fold>
	//<editor-fold desc="Aster">
	public static final Item BROKEN_BOTTLE = GeneratedItem(new Item(ItemSettings()));
	//</editor-fold>
	//<editor-fold desc="August">
	public static final StatusEffect ICHORED_EFFECT = new IchoredEffect();
	public static final Item ICHOR_BOTTLE = GeneratedItem(new BottledDrinkItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE)) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BloodType bloodType = BloodType.Get(user);
			if (bloodType == ICHOR_BLOOD_TYPE) user.heal(2);
			else {
				if (bloodType == ANEMIC_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_ICHOR_AS_ANEMIC, 8);
				else if (bloodType == VAMPIRE_BLOOD_TYPE) user.damage(ModDamageSource.DRANK_ICHOR_AS_VAMPIRE, 8);
				else user.damage(ModDamageSource.DRANK_ICHOR, 8);
				user.addStatusEffect(new StatusEffectInstance(HavenMod.ICHORED_EFFECT, 200, 2));
			}
		}
	});
	public static final Item ICHOR_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == ICHOR_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else {
			entity.damage(ModDamageSource.Injected(user, ICHOR_BLOOD_TYPE), 4);
			entity.addStatusEffect(new StatusEffectInstance(HavenMod.ICHORED_EFFECT, 200, 1));
		}
	}));
	//</editor-fold>
	//<editor-fold desc="Bird">
	public static final JavelinItem PRIDE_TRIDENT = GeneratedItem(new PrideTridentItem(8.0, -2.9f, ModId.ID("textures/entity/projectiles/pride_trident.png"), ItemSettings().maxDamage(250)));
	private static class PrideTridentItem extends JavelinItem {
		public PrideTridentItem(double attackDamage, float attackSpeed, Identifier texture, Settings settings) {
			super(attackDamage, attackSpeed, texture, settings);
			this.FACTORY = PrideTridentEntity::new;
		}
	}
	public static final EntityType<JavelinEntity> PRIDE_TRIDENT_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, PrideTridentEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	private static class PrideTridentEntity extends JavelinEntity {
		public PrideTridentEntity(EntityType<? extends JavelinEntity> entityType, World world) {
			super(entityType, world);
			this.item = HavenMod.PRIDE_TRIDENT;
		}
		public PrideTridentEntity(World world, LivingEntity owner, ItemStack stack) {
			super(HavenMod.PRIDE_TRIDENT_ENTITY, world, owner, stack, HavenMod.PRIDE_TRIDENT);
		}
	}
	//</editor-fold>
	//<editor-fold desc="Carnation">
	public static final Map<DyeColor, FlowerContainer> CARNATIONS = ColorUtil.Map(color -> MakeFlower(StatusEffects.WEAKNESS, 5).pottedModel());
	public static final Map<DyeColor, FlowerPartContainer> CARNATION_PARTS = ColorUtil.Map(color -> MakeFlowerParts(CARNATIONS.get(color).asBlock()));
	//</editor-fold>
	//<editor-fold desc="Dakota">
	public static final ToolItem PTEROR = HandheldItem(new ModSwordItem(ToolMaterials.NETHERITE, 3, -2.4F, ItemSettings().fireproof()));
	public static final Item LOCKET = MakeGeneratedItem();
	public static final Item EMERALD_LOCKET = MakeGeneratedItem();
	//</editor-fold>
	//<editor-fold desc="Deepest Sleep">
	public static final DefaultParticleType VECTOR_ARROW_PARTICLE = FabricParticleTypes.simple(false);
	public static final BlockContainer CHUNKEATER_TNT = new BlockContainer(new ChunkeaterTntBlock(ModFactory.TntSettings(MapColor.PURPLE)));
	public static final EntityType<ChunkeaterTntEntity> CHUNKEATER_TNT_ENTITY = FabricEntityTypeBuilder.<ChunkeaterTntEntity>create(SpawnGroup.MISC, ChunkeaterTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Digger Krozhul">
	private static void applySludge(LivingEntity entity) {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == SLUDGE_BLOOD_TYPE) entity.heal(2);
		else {
			if (bloodType == ModBase.SLIME_BLOOD_TYPE || bloodType == ModBase.BLUE_SLIME_BLOOD_TYPE || bloodType == ModBase.PINK_SLIME_BLOOD_TYPE
					|| bloodType == ModBase.MAGMA_CREAM_BLOOD_TYPE) {
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 0));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 300, 0));
			}
			else {
				entity.damage(ModDamageSource.DRANK_SLUDGE, 8);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 2));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 2));
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 600, 2));
				entity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
			}
		}
	}
	public static final Item SLUDGE_BOTTLE = GeneratedItem(new BottledDrinkItem(ItemSettings().maxCount(16).recipeRemainder(Items.GLASS_BOTTLE)) {
		@Override public void OnDrink(ItemStack stack, LivingEntity user) { applySludge(user); }
	});
	public static final Item SLUDGE_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> applySludge(entity)));
	public static final BlockContainer VIOLENT_TNT = new BlockContainer(new ViolentTntBlock(ModFactory.TntSettings(MapColor.GRAY)));
	public static final EntityType<ViolentTntEntity> VIOLENT_TNT_ENTITY = FabricEntityTypeBuilder.<ViolentTntEntity>create(SpawnGroup.MISC, ViolentTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Dr. Dae">
	public static final Item SECRET_INGREDIENT = MakeGeneratedItem();
	public static final Item SYRINGE_BLINDNESS = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 4, true, false)));
	public static final Item SYRINGE_MINING_FATIGUE = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, (600), 4, true, false)));
	public static final Item SYRINGE_POISON = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.POISON, 600, 4, true, false)));
	public static final Item SYRINGE_REGENERATION = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 4, true, false)));
	public static final Item SYRINGE_SATURATION = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.SATURATION, 600, 4, true, false)));
	public static final Item SYRINGE_SLOWNESS = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 4, true, false)));
	public static final Item SYRINGE_WEAKNESS = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 4, true, false)));
	public static final Item SYRINGE_WITHER = HandheldItem(new SyringeItem(new StatusEffectInstance(StatusEffects.WITHER, 600, 4, true, false)));
	public static final Item SYRINGE_EXP1 = HandheldItem(new SyringeItem(new StatusEffectInstance(HavenMod.DETERIORATION_EFFECT, 600, 4, true, false)));
	public static final Item SYRINGE_EXP2 = HandheldItem(new SyringeItem(new StatusEffectInstance(HavenMod.DETERIORATION_EFFECT, 600, 4, true, false)));
	public static final Item SYRINGE_EXP3 = HandheldItem(new SyringeItem(new StatusEffectInstance(HavenMod.DETERIORATION_EFFECT, 600, 4, true, false)));
	//</editor-fold>
	//<editor-fold desc="Electron The Blue Mage">
	public static final Item DRINKABLE_BOTTLED_LIGHTNING = ParentedItem(new BottledDrinkItem(GlassBottledItemSettings()) {
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			BottledLightningEntity.SummonLightning(user.world, user.getPos(), user instanceof ServerPlayerEntity server ? server : null);
		}
	}, BOTTLED_LIGHTNING_ITEM);
	//</editor-fold>
	//<editor-fold desc="Ferris">
	public static final JavelinItem VECTORTECH_JAVELIN = GeneratedItem(new VectortechJavelinItem(10.0, -2.8f, ModId.ID("textures/entity/projectiles/vectortech_javelin.png"), ItemSettings().maxDamage(250)));
	public static final EntityType<JavelinEntity> VECTORTECH_JAVELIN_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, VectortechJavelinEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	//</editor-fold>
	//<editor-fold desc="Gawain">
	//Red for Protection
	public static final StatusEffect PROTECTED_EFFECT = new ModStatusEffect(StatusEffectType.BENEFICIAL,0xED302C);
	public static final Item RED_CURSE_BREAKER_POTION = GeneratedItem(new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE)) {
		@Override public boolean hasGlint(ItemStack stack) { return true; }
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			user.addStatusEffect(new StatusEffectInstance(PROTECTED_EFFECT, 3600)); //Apply for 3 minutes
			if (user.hasStatusEffect(WITHERING_EFFECT)) user.removeStatusEffect(WITHERING_EFFECT);
		}
	});
	//White for Pain
	public static final StatusEffect RELIEVED_EFFECT = new ModStatusEffect(StatusEffectType.BENEFICIAL,0xD6E8E8);
	public static final Item WHITE_CURSE_BREAKER_POTION = GeneratedItem(new BottledDrinkItem(ItemSettings().recipeRemainder(Items.GLASS_BOTTLE)) {
		@Override public boolean hasGlint(ItemStack stack) { return true; }
		@Override
		public void OnDrink(ItemStack stack, LivingEntity user) {
			user.addStatusEffect(new StatusEffectInstance(RELIEVED_EFFECT, 9600)); //Apply for 8 minutes
			if (user.hasStatusEffect(WITHERING_EFFECT)) WitheringEffect.reduce(user.world, user);
		}
	});
	//</editor-fold>
	//<editor-fold desc="Hezekiah">
	public static final BlockContainer DEVOURING_TNT = new BlockContainer(new DevouringTntBlock(ModFactory.TntSettings(MapColor.GREEN)));
	public static final EntityType<DevouringTntEntity> DEVOURING_TNT_ENTITY = FabricEntityTypeBuilder.<DevouringTntEntity>create(SpawnGroup.MISC, DevouringTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Jackdaw">
	public static final StatusEffect WITHERING_EFFECT = new WitheringEffect().milkImmune();
	//</editor-fold>
	//<editor-fold desc="Lux">
	public static final Item LUX_CROWN = ModFactory.MakeHelmet(ModArmorMaterials.LUX_CROWN);
	public static final Item NETHER_ROYALTY_BLOOD_SYRINGE = HandheldItem(new BloodSyringeItem((PlayerEntity user, ItemStack stack, LivingEntity entity) -> {
		BloodType bloodType = BloodType.Get(entity);
		if (bloodType == NETHER_ROYALTY_BLOOD_TYPE) BloodSyringeItem.heal(entity, 1);
		else if (bloodType == BloodType.LAVA || bloodType == ModBase.MAGMA_CREAM_BLOOD_TYPE) BloodSyringeItem.heal(entity, 2);
		else {
			entity.setOnFireFor(5);
			entity.damage(ModDamageSource.Injected("nether_royalty_blood", user), 2);
		}
	}));
	//</editor-fold>
	//<editor-fold desc="Miasma">
	public static final BlockContainer CATALYZING_TNT = new BlockContainer(new CatalyzingTntBlock(ModFactory.TntSettings(MapColor.PINK)));
	public static final EntityType<CatalyzingTntEntity> CATALYZING_TNT_ENTITY = FabricEntityTypeBuilder.<CatalyzingTntEntity>create(SpawnGroup.MISC, CatalyzingTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(16).trackedUpdateRate(16).build();
	//</editor-fold>
	//<editor-fold desc="Moth">
	public static final Item SHODDY_WOOD_BUCKET = MakeGeneratedItem();
	//</editor-fold>
	//<editor-fold desc="Soleil">
	public static final BlockContainer SOFT_TNT = new BlockContainer(new SoftTntBlock(ModFactory.TntSettings(MapColor.LIGHT_BLUE)));
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = FabricEntityTypeBuilder.<SoftTntEntity>create(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//Misc
	public static final Item SOLEIL_MASK = MakeGeneratedItem();
	public static final Item SOLEIL_PUMPKIN_MASK = MakeGeneratedItem();
	public static final Item SOLEIL_WHITE_PUMPKIN_MASK = MakeGeneratedItem();
	public static final Item SOLEIL_MELON_MASK = MakeGeneratedItem();
	//<editor-fold desc="Gourds">
	public static final BlockContainer SOLEIL_CARVED_PUMPKIN = ModFactory.MakeCarvedGourd(MapColor.ORANGE);
	public static final BlockContainer SOLEIL_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.ORANGE, LUMINANCE_15);
	public static final BlockContainer SOLEIL_SOUL_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.ORANGE, LUMINANCE_10);
	public static final BlockContainer SOLEIL_ENDER_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.ORANGE, LUMINANCE_13);
	public static final BlockContainer SOLEIL_CARVED_MELON = ModFactory.MakeCarvedGourd(MapColor.LIME);
	public static final BlockContainer SOLEIL_MELON_LANTERN = ModFactory.MakeGourdLantern(MapColor.LIME, LUMINANCE_15);
	public static final BlockContainer SOLEIL_SOUL_MELON_LANTERN = ModFactory.MakeGourdLantern(MapColor.LIME, LUMINANCE_10);
	public static final BlockContainer SOLEIL_ENDER_MELON_LANTERN = ModFactory.MakeGourdLantern(MapColor.LIME, LUMINANCE_13);
	public static final BlockContainer SOLEIL_CARVED_WHITE_PUMPKIN = ModFactory.MakeCarvedGourd(MapColor.WHITE);
	public static final BlockContainer SOLEIL_WHITE_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.WHITE, LUMINANCE_15);
	public static final BlockContainer SOLEIL_WHITE_SOUL_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.WHITE, LUMINANCE_10);
	public static final BlockContainer SOLEIL_WHITE_ENDER_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.WHITE, LUMINANCE_13);
	public static final BlockContainer SOLEIL_CARVED_ROTTEN_PUMPKIN = ModFactory.MakeCarvedGourd(MapColor.PALE_YELLOW);
	public static final BlockContainer SOLEIL_ROTTEN_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.PALE_YELLOW, LUMINANCE_15);
	public static final BlockContainer SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.PALE_YELLOW, LUMINANCE_10);
	public static final BlockContainer SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN = ModFactory.MakeGourdLantern(MapColor.PALE_YELLOW, LUMINANCE_13);
	//</editor-fold>
	//</editor-fold>
	//<editor-fold desc="STARS">
	public static final Item TINKER_TOY = MakeGeneratedItem();
	public static final Item AMETHYST_CANDY = MakeGeneratedItem(); //not edible usually (it's rocks)
	public static final BlockContainer BROKEN_STARS = BuildBlock(new BrokenStarsBlock(Block.Settings.copy(Blocks.IRON_BLOCK).nonOpaque()));
	//</editor-fold>
	//<editor-fold desc="The Captain">
	public static final ToolItem SBEHESOHE = HandheldItem(new ModSwordItem(ToolMaterials.DIAMOND, 3, -2.4F, ItemSettings().fireproof()));
	public static final ToolItem SBEHESOHE_FULL = new ModSwordItem(ToolMaterials.DIAMOND, 4, -2.8F, ItemSettings().fireproof());
	//</editor-fold>

	//<editor-fold desc="Origin Powers">
	public static void RegisterOriginPowers() {
		Register(SwapWithLastVectortechJavelinPower::createFactory);
	}
	//</editor-fold>

	//<editor-fold desc="Decoration Only Blocks">
	private static ItemStack getDecorativeVineItemStack() { return new ItemStack(DECORATIVE_VINE.asItem()); }
	public static final ItemGroup DECORATION_GROUP = FabricItemGroupBuilder.build(ModId.ID("decorative_blocks"), HavenMod::getDecorativeVineItemStack);
	private static BlockContainer MakeDecorativeBlock(Function<Block.Settings, Block> blockProvider, Block copyOf) {
		return new BlockContainer(blockProvider.apply(Block.Settings.copy(copyOf)), ItemSettings().group(DECORATION_GROUP).rarity(Rarity.EPIC));
	}
	public static final BlockContainer DECORATIVE_VINE = MakeDecorativeBlock(DecorativeVineBlock::new, Blocks.VINE).blockTag(BlockTags.CLIMBABLE);
	public static final BlockContainer DECORATIVE_SUGAR_CANE = MakeDecorativeBlock(DecorativeSugarCaneBlock::new, Blocks.SUGAR_CANE);
	public static final BlockContainer DECORATIVE_CACTUS = MakeDecorativeBlock(DecorativeCactusBlock::new, Blocks.CACTUS);
	public static final BlockContainer DECORATIVE_ACACIA_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.ACACIA_SAPLING);
	public static final BlockContainer DECORATIVE_BIRCH_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.BIRCH_SAPLING);
	public static final BlockContainer DECORATIVE_DARK_OAK_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.DARK_OAK_SAPLING);
	public static final BlockContainer DECORATIVE_JUNGLE_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.JUNGLE_SAPLING);
	public static final BlockContainer DECORATIVE_OAK_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.OAK_SAPLING);
	public static final BlockContainer DECORATIVE_CHERRY_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, ModBase.CHERRY_SAPLING.asBlock());
	public static final BlockContainer DECORATIVE_SPRUCE_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, Blocks.SPRUCE_SAPLING);
	public static final BlockContainer DECORATIVE_CASSIA_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, ModBase.CASSIA_SAPLING.asBlock());
	public static final BlockContainer DECORATIVE_DOGWOOD_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, ModBase.DOGWOOD_SAPLING.asBlock());
	public static final BlockContainer DECORATIVE_GRAPE_SAPLING = MakeDecorativeBlock(DecorativeSaplingBlock::new, ModBase.GRAPE_SAPLING.asBlock());
	public static final BlockContainer DECORATIVE_BEETROOTS = MakeDecorativeBlock(DecorativeBeetrootBlock::new, Blocks.BEETROOTS);
	public static final BlockContainer DECORATIVE_CARROTS = MakeDecorativeBlock(DecorativeCropBlock::new, Blocks.CARROTS);
	public static final BlockContainer DECORATIVE_POTATOES = MakeDecorativeBlock(DecorativeCropBlock::new, Blocks.POTATOES);
	public static final BlockContainer DECORATIVE_WHEAT = MakeDecorativeBlock(DecorativeWheatBlock::new, Blocks.WHEAT);
	public static final BlockContainer DECORATIVE_CABBAGES = MakeDecorativeBlock(DecorativeCabbageBlock::new, BlocksRegistry.CABBAGE_CROP.get());
	public static final BlockContainer DECORATIVE_ONIONS = MakeDecorativeBlock(DecorativeCropBlock::new, BlocksRegistry.ONION_CROP.get());
	//</editor-fold>

	private static String GetAnchorCoreString_EN_US(int owner) {
		return switch (owner) {
			case 1 -> "Jackdaw's"; case 2 -> "August's"; case 3 -> "Bird's"; case 4 -> "Wechsel's";
			case 5 -> "Rat's"; case 6 -> "STARS'"; case 7 -> "Whimsy's"; case 8 -> "Aster's";
			case 9 -> "Gawain's"; case 10 -> "Deepest Sleep's"; case 11 -> LUX_s; case 12 -> "Sylph's";
			case 13 -> "Angel's"; case 14 -> "The Captain's"; case 15 -> "Oz's"; case 16 -> "Navn's";
			case 17 -> AMBER_s; case 18 -> "Dakota's"; case 19 -> "Rhys'"; case 20 -> SOLEIL_s;
			case 21 -> "Doctor Dae's"; case 22 -> "Miasma's"; case 23 -> "Digger Krozhul's";
			case 24 -> "Bones'"; case 25 -> "Citadel's"; case 26 -> "Ares'";
			case 27 -> "Keltzy's"; case 28 -> "Dasner's"; case 29 -> "Skirmish's";
			case 30 -> "Tree's"; case 31 -> "Ferris'";
			default -> "Unknown";
		};
	}

	public static void RegisterAll() {
		//<editor-fold desc="Anchors">
		Register("anchor", ANCHOR, List.of(EN_US.Anchor()));
		Register("anchor_block_entity", ANCHOR_BLOCK_ENTITY);
		Register("broken_anchor", BROKEN_ANCHOR, List.of(EN_US.Anchor(EN_US.Broken())));
		Register("broken_anchor_block_entity", BROKEN_ANCHOR_BLOCK_ENTITY);
		Register("substitute_anchor", SUBSTITUTE_ANCHOR_BLOCK, List.of(EN_US.Anchor(EN_US.Substitute())));
		Register("substitute_anchor_block_entity", SUBSTITUTE_ANCHOR_BLOCK_ENTITY);
		//Anchor core items
		for (Integer owner : ANCHOR_CORES.keySet()) {
			Register(ANCHOR_MAP.get(owner) + "_core", ANCHOR_CORES.get(owner), List.of(EN_US.Core(EN_US.Anchor(GetAnchorCoreString_EN_US(owner)))));
		}
		for (Integer owner : BROKEN_ANCHOR_CORES.keySet()) {
			Register(ANCHOR_MAP.get(owner) + "_broken_core", BROKEN_ANCHOR_CORES.get(owner), List.of(EN_US.Core(EN_US.Anchor(EN_US.Broken(GetAnchorCoreString_EN_US(owner))))));
		}
		//</editor-fold>

		//<editor-fold desc="Alcatraz">
		Register("sharp_tnt", SHARP_TNT, List.of(EN_US.TNT(EN_US.Sharp())));
		Register("sharp_tnt", SHARP_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Sharp())));
		DispenserBlock.registerBehavior(SHARP_TNT.asBlock(), ModTntBlock.DispenserBehavior(SharpTntEntity::new));
		//</editor-fold>
		//<editor-fold desc="Amber">
		Register("amber_eye", AMBER_EYE, List.of(EN_US.Eye(AMBER_s)));
		Register("amber_eye_end_portal_frame", AMBER_EYE_END_PORTAL_FRAME, List.of(EN_US.Frame(EN_US.Portal(EN_US.End(EN_US.Eye(AMBER))))));
		Register("bee_enderman_blood_syringe", BEE_ENDERMAN_BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Syringe(EN_US.Blood(EN_US.Hybrid(EN_US.Enderman(EN_US.Bee())))))));
		BloodType.RegisterBloodType(BEE_ENDERMAN_BLOOD_TYPE, BEE_ENDERMAN_BLOOD_SYRINGE);
		//</editor-fold>
		//<editor-fold desc="Anathema">
		Register("bone_rot", BONE_ROT_EFFECT, List.of(EN_US.Rot(EN_US.Bone())));
		Register("deteriorating", DETERIORATION_EFFECT, List.of(EN_US.Deterioration()));
		Register("marked", MARKED_EFFECT, List.of(EN_US.Marked()));
		//</editor-fold>
		//<editor-fold desc="Moth">
		Register("shoddy_wood_bucket", SHODDY_WOOD_BUCKET, List.of(EN_US.Bucket(EN_US.Wood(EN_US.Shoddy()))));
		//</editor-fold>
		//<editor-fold desc="Angel">
		Register("angel_bat_plushie", ANGEL_BAT_PLUSHIE, List.of(EN_US.Plushie(EN_US.Bat(EN_US.Angel()))));
		Register("angel_bat", ANGEL_BAT_ENTITY, List.of(EN_US.Bat(EN_US.Angel())));
		SpawnRestrictionAccessor.callRegister(ANGEL_BAT_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelBatEntity::CanSpawn);
		FabricDefaultAttributeRegistry.register(ANGEL_BAT_ENTITY, AngelBatEntity.createBatAttributes());
		//</editor-fold>
		//<editor-fold desc="Aster">
		Register("broken_bottle", BROKEN_BOTTLE, List.of(EN_US.Bottle(EN_US.Broken())));
		//</editor-fold>
		//<editor-fold desc="August">
		Register("ichored", ICHORED_EFFECT, List.of(EN_US.Ichored()));
		Register("ichor_bottle", ICHOR_BOTTLE, List.of(EN_US.Bottle(EN_US.Ichor())));
		Register("ichor_syringe", ICHOR_SYRINGE, List.of(EN_US.Syringe(EN_US.Ichor())));
		BloodType.RegisterBloodType(ICHOR_BLOOD_TYPE, ICHOR_SYRINGE);
		//</editor-fold>
		//<editor-fold desc="Bird">
		Register("pride_trident", PRIDE_TRIDENT, List.of(EN_US.Pride()));
		Register("pride_trident", PRIDE_TRIDENT_ENTITY, List.of(EN_US.Pride()));
		//</editor-fold>
		//<editor-fold desc="Carnation">
		for (DyeColor color : DyeColor.values()) {
			Register(color.getName() + "_carnation", CARNATIONS.get(color), List.of(EN_US._Potted(EN_US.Carnation(EN_US.Color(color)))));
			Register(color.getName() + "_carnation", CARNATION_PARTS.get(color), List.of(EN_US._FlowerParts(EN_US.Carnation(EN_US.Color(color)))));
		}
		//</editor-fold>
		//<editor-fold desc="Dakota">
		Register("pteror", PTEROR, List.of("Pteror"));
		Register("locket", LOCKET, List.of(EN_US.Locket()));
		Register("emerald_locket", EMERALD_LOCKET, List.of(EN_US.Locket(EN_US.Emerald())));
		//</editor-fold>
		//<editor-fold desc="Deepest Sleep">
		Register("vector_arrow", VECTOR_ARROW_PARTICLE);
		Register("chunkeater_tnt", CHUNKEATER_TNT, List.of(EN_US.TNT(EN_US.Chunkeater())));
		Register("chunkeater_tnt", CHUNKEATER_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Chunkeater())));
		DispenserBlock.registerBehavior(CHUNKEATER_TNT.asBlock(), ModTntBlock.DispenserBehavior(ChunkeaterTntEntity::new));
		//</editor-fold>
		//<editor-fold desc="Digger Krozhul">
		Register("sludge_bottle", SLUDGE_BOTTLE, List.of(EN_US.Bottle(EN_US.Sludge())));
		Register("sludge_syringe", SLUDGE_SYRINGE, List.of(EN_US.Syringe(EN_US.Sludge())));
		BloodType.RegisterBloodType(SLUDGE_BLOOD_TYPE, SLUDGE_SYRINGE);
		Register("violent_tnt", VIOLENT_TNT, List.of(EN_US.TNT(EN_US.Violent())));
		Register("violent_tnt", VIOLENT_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Violent())));
		DispenserBlock.registerBehavior(VIOLENT_TNT.asBlock(), ModTntBlock.DispenserBehavior(ViolentTntEntity::new));
		//</editor-fold>
		//<editor-fold desc="Dr. Dae">
		Register("secret_ingredient", SECRET_INGREDIENT, List.of(EN_US.Ingredient(EN_US.Secret())));
		Register("syringe_blindness", SYRINGE_BLINDNESS, List.of(EN_US.Syringe(EN_US.Blinding())));
		Register("syringe_mining_fatigue", SYRINGE_MINING_FATIGUE, List.of(EN_US.Syringe(EN_US.Inducing(EN_US.Fatigue(EN_US.Mining())))));
		Register("syringe_poison", SYRINGE_POISON, List.of(EN_US.Syringe(EN_US.Poison())));
		Register("syringe_regeneration", SYRINGE_REGENERATION, List.of(EN_US.Syringe(EN_US.Regenerative())));
		Register("syringe_saturation", SYRINGE_SATURATION, List.of(EN_US.Syringe(EN_US.Saturating())));
		Register("syringe_slowness", SYRINGE_SLOWNESS, List.of(EN_US.Syringe(EN_US.Inducing(EN_US.Slowness()))));
		Register("syringe_weakness", SYRINGE_WEAKNESS, List.of(EN_US.Syringe(EN_US.Weakening())));
		Register("syringe_wither", SYRINGE_WITHER, List.of(EN_US.Syringe(EN_US.Withering())));
		Register("syringe_exp1", SYRINGE_EXP1, List.of(EN_US.I(EN_US.Syringe(EN_US.Experimental()))));
		Register("syringe_exp2", SYRINGE_EXP2, List.of(EN_US.II(EN_US.Syringe(EN_US.Experimental()))));
		Register("syringe_exp3", SYRINGE_EXP3, List.of(EN_US.III(EN_US.Syringe(EN_US.Experimental()))));
		//</editor-fold>
		//<editor-fold desc="Electron the Blue Mage">
		Register("drinkable_bottled_lightning", DRINKABLE_BOTTLED_LIGHTNING, List.of(EN_US.Lightning(EN_US.Bottled(EN_US.Drinkable()))));
		//</editor-fold>
		//<editor-fold desc="Ferris">
		Register("vectortech_javelin", VECTORTECH_JAVELIN, List.of(EN_US.Javelin(VECTORTECH)));
		Register("vectortech_javelin", VECTORTECH_JAVELIN_ENTITY, List.of(EN_US.Javelin(VECTORTECH)));
		//</editor-fold>
		//<editor-fold desc="Gawain">
		Register("protected", PROTECTED_EFFECT, List.of(EN_US.Protected()));
		Register("red_curse_breaker_potion", RED_CURSE_BREAKER_POTION, List.of(EN_US.Protection(EN_US._for(EN_US.Red()))));
		Register("relieved", RELIEVED_EFFECT, List.of(EN_US.Relieved()));
		Register("white_curse_breaker_potion", WHITE_CURSE_BREAKER_POTION, List.of(EN_US.Pain(EN_US._for(EN_US.White()))));
		//</editor-fold>
		//<editor-fold desc="Hezekiah">
		Register("devouring_tnt", DEVOURING_TNT, List.of(EN_US.TNT(EN_US.Devouring())));
		Register("devouring_tnt", DEVOURING_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Devouring())));
		DispenserBlock.registerBehavior(DEVOURING_TNT.asBlock(), ModTntBlock.DispenserBehavior(DevouringTntEntity::new));
		//</editor-fold>
		//<editor-fold desc="Jackdaw">
		Register("withering", WITHERING_EFFECT, List.of(EN_US.Withering()));
		//</editor-fold>
		//<editor-fold desc="Lux">
		Register("lux_crown", LUX_CROWN, List.of(EN_US.Crown(LUX_s)));
		Register("nether_royalty_blood_syringe", NETHER_ROYALTY_BLOOD_SYRINGE, List.of(EN_US.Syringe(EN_US.Blood(EN_US.Royalty(EN_US.Nether())))));
		BloodType.RegisterBloodType(NETHER_ROYALTY_BLOOD_TYPE, NETHER_ROYALTY_BLOOD_SYRINGE);
		//</editor-fold>
		//<editor-fold desc="Miasma">
		Register("catalyzing_tnt", CATALYZING_TNT, List.of(EN_US.TNT(EN_US.Catalyzing())));
		Register("catalyzing_tnt", CATALYZING_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Catalyzing())));
		DispenserBlock.registerBehavior(CATALYZING_TNT.asBlock(), ModTntBlock.DispenserBehavior(CatalyzingTntEntity::new));
		//</editor-fold>
		//<editor-fold desc="Soleil">
		Register("soft_tnt", SOFT_TNT, List.of(EN_US.TNT(EN_US.Soft())));
		Register("soft_tnt", SOFT_TNT_ENTITY, List.of(EN_US.TNT(EN_US.Soft())));
		DispenserBlock.registerBehavior(SOFT_TNT.asBlock(), ModTntBlock.DispenserBehavior(SoftTntEntity::new));
		//Confetti Syringe
		BloodType.RegisterBloodType(HavenMod.CONFETTI_BLOOD_TYPE, CONFETTI_SYRINGE);
		//Misc
		Register("soleil_mask", SOLEIL_MASK, List.of(EN_US.Mask(SOLEIL_s)));
		Register("soleil_pumpkin_mask", SOLEIL_PUMPKIN_MASK, List.of(EN_US.Mask(EN_US.Pumpkin(SOLEIL_s))));
		Register("soleil_white_pumpkin_mask", SOLEIL_WHITE_PUMPKIN_MASK, List.of(EN_US.Mask(EN_US.Pumpkin(EN_US.White(SOLEIL_s)))));
		Register("soleil_melon_mask", SOLEIL_MELON_MASK, List.of(EN_US.Mask(EN_US.Melon(SOLEIL_s))));
		//<editor-fold desc="Carved Gourds">
		Register("soleil_carved_pumpkin", SOLEIL_CARVED_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.Carved(SOLEIL))));
		Register("soleil_jack_o_lantern", SOLEIL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(SOLEIL)));
		Register("soleil_soul_jack_o_lantern", SOLEIL_SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul(SOLEIL))));
		Register("soleil_ender_jack_o_lantern", SOLEIL_ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender(SOLEIL))));
		Register("soleil_carved_melon", SOLEIL_CARVED_MELON, List.of(EN_US.Melon(EN_US.Carved(SOLEIL))));
		Register("soleil_melon_lantern", SOLEIL_MELON_LANTERN, List.of(EN_US.Lantern(EN_US.Melon(SOLEIL))));
		Register("soleil_soul_melon_lantern", SOLEIL_SOUL_MELON_LANTERN, List.of(EN_US.Lantern(EN_US.Melon(EN_US.Soul(SOLEIL)))));
		Register("soleil_ender_melon_lantern", SOLEIL_ENDER_MELON_LANTERN, List.of(EN_US.Lantern(EN_US.Ender(EN_US.Soul(SOLEIL)))));
		Register("soleil_carved_white_pumpkin", SOLEIL_CARVED_WHITE_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.White(EN_US.Carved(SOLEIL)))));
		Register("soleil_white_jack_o_lantern", SOLEIL_WHITE_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.White(SOLEIL))));
		Register("soleil_white_soul_jack_o_lantern", SOLEIL_WHITE_SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul(EN_US.White(SOLEIL)))));
		Register("soleil_white_ender_jack_o_lantern", SOLEIL_WHITE_ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender(EN_US.White(SOLEIL)))));
		Register("soleil_carved_rotten_pumpkin", SOLEIL_CARVED_ROTTEN_PUMPKIN, List.of(EN_US.Pumpkin(EN_US.Rotten(EN_US.Carved(SOLEIL)))));
		Register("soleil_rotten_jack_o_lantern", SOLEIL_ROTTEN_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Rotten(SOLEIL))));
		Register("soleil_rotten_soul_jack_o_lantern", SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Soul(EN_US.Rotten(SOLEIL)))));
		Register("soleil_rotten_ender_jack_o_lantern", SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN, List.of(EN_US.JackOLantern(EN_US.Ender(EN_US.Rotten(SOLEIL)))));
		//</editor-fold>
		//</editor-fold>
		//<editor-fold desc="STARS">
		Register("tinker_toy", TINKER_TOY, List.of(EN_US.Toy(EN_US.Tinker())));
		Register("amethyst_candy", AMETHYST_CANDY, List.of(EN_US.Candy(EN_US.Amethyst())));
		Register("broken_stars", BROKEN_STARS, List.of("The remnants of STARS"));
		//</editor-fold>
		//<editor-fold desc="The Captain">
		Register("sbehesohe", SBEHESOHE, List.of("Sbehésóhe"));
		Register("sbehesohe_full", SBEHESOHE_FULL, List.of("Sbehésóhe (Restored)"));
		//</editor-fold>

		//<editor-fold desc="Decoration Only Blocks">
		Register("decoration_only_vine", DECORATIVE_VINE, List.of(EN_US.Vine(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_sugar_cane", DECORATIVE_SUGAR_CANE, List.of(EN_US.Cane(EN_US.Sugar(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_cactus", DECORATIVE_CACTUS, List.of(EN_US.Cactus(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_acacia_sapling", DECORATIVE_ACACIA_SAPLING, List.of(EN_US.Sapling(EN_US.Acacia(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_birch_sapling", DECORATIVE_BIRCH_SAPLING, List.of(EN_US.Sapling(EN_US.Birch(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_dark_oak_sapling", DECORATIVE_DARK_OAK_SAPLING, List.of(EN_US.Sapling(EN_US.Oak(EN_US.Dark(EN_US.Only(EN_US.Decoration()))))));
		Register("decoration_only_jungle_sapling", DECORATIVE_JUNGLE_SAPLING, List.of(EN_US.Sapling(EN_US.Jungle(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_oak_sapling", DECORATIVE_OAK_SAPLING, List.of(EN_US.Sapling(EN_US.Oak(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_spruce_sapling", DECORATIVE_SPRUCE_SAPLING, List.of(EN_US.Sapling(EN_US.Spruce(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_cherry_sapling", DECORATIVE_CHERRY_SAPLING, List.of(EN_US.Sapling(EN_US.Cherry(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_cassia_sapling", DECORATIVE_CASSIA_SAPLING, List.of(EN_US.Sapling(EN_US.Cassia(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_dogwood_sapling", DECORATIVE_DOGWOOD_SAPLING, List.of(EN_US.Sapling(EN_US.Dogwood(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_grape_sapling", DECORATIVE_GRAPE_SAPLING, List.of(EN_US.Sapling(EN_US.Grape(EN_US.Only(EN_US.Decoration())))));
		Register("decoration_only_beetroots", DECORATIVE_BEETROOTS, List.of(EN_US.Beetroots(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_carrots", DECORATIVE_CARROTS, List.of(EN_US.Carrots(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_potatoes", DECORATIVE_POTATOES, List.of(EN_US.Potatoes(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_wheat", DECORATIVE_WHEAT, List.of(EN_US.Wheat(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_cabbages", DECORATIVE_CABBAGES, List.of(EN_US.Cabbages(EN_US.Only(EN_US.Decoration()))));
		Register("decoration_only_onions", DECORATIVE_ONIONS, List.of(EN_US.Onions(EN_US.Only(EN_US.Decoration()))));
		//</editor-fold>
	}

	static {
		for(Integer owner : ANCHOR_MAP.keySet()) ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		//for (Integer owner : ANCHOR_MAP.keySet()) BROKEN_ANCHOR_CORES.put(owner, GeneratedItem(new BrokenAnchorCoreItem(owner)));
	}
}
