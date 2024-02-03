package fun.wich.registry;

import fun.wich.ModBase;
import fun.wich.ModId;
import fun.wich.block.JuicerBlock;
import fun.wich.container.*;
import fun.wich.entity.projectile.ModArrowEntity;
import fun.wich.item.projectile.ModArrowItem;
import fun.wich.mixins.world.BuiltinBiomesInvoker;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static fun.wich.ModBase.LANGUAGE_CACHES;

public class ModRegistry {
	public static <T, S> List<T>getLeft(Pair<T, S>[] pairs) { return getLeft(List.of(pairs)); }
	public static <T, S> List<T>getLeft(List<Pair<T, S>> pairs) { return getLeft(pairs.stream()); }
	public static <T, S> List<T>getLeft(Stream<Pair<T, S>> pairs) { return pairs.map(Pair::getLeft).toList(); }
	public static <T, S> List<S>getRight(Pair<T, S>[] pairs) { return getRight(List.of(pairs)); }
	public static <T, S> List<S>getRight(List<Pair<T, S>> pairs) { return getRight(pairs.stream()); }
	public static <T, S> List<S>getRight(Stream<Pair<T, S>> pairs) { return pairs.map(Pair::getRight).toList(); }

	public static Block Register(String path, Block value, List<String> translations) {
		int length = translations.size();
		Identifier id = ModId.ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Block: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("block", ModId.ID(path)), translations.get(i));
		}
		return Registry.register(Registry.BLOCK, id, value);
	}
	public static Item Register(String path, Item value, List<String> translations) {
		int length = translations.size();
		Identifier id = ModId.ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Item: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("item", ModId.ID(path)), translations.get(i));
		}
		return Registry.register(Registry.ITEM, id, value);
	}
	public static BlockContainer Register(String path, BlockContainer value, List<String> translations) {
		Register(path, value.asBlock(), translations);
		Register(path, value.asItem(), translations);
		return value;
	}
	public static IWallBlockItemContainer Register(String path, String wallPath, IWallBlockItemContainer value, List<Pair<String, String>> translations) {
		List<String> left = getLeft(translations);
		Register(path, value.asBlock(), left);
		Register(path, value.asItem(), left);
		Register(wallPath, value.getWallBlock(), getRight(translations));
		return value;
	}
	public static UnlitTorchContainer Register(String path, String wallPath, UnlitTorchContainer value, List<Pair<String, String>> translations) {
		Register(path, value.asBlock(), getLeft(translations));
		Register(wallPath, value.getWallBlock(), getRight(translations));
		return value;
	}
	public static SignContainer Register(String name, SignContainer value, List<Pair<Pair<String, String>, Pair<String, String>>> translations) {
		Register(name + "_sign", name + "_wall_sign", value, getLeft(translations));
		Register(name + "_hanging_sign", name + "_wall_hanging_sign", value.getHanging(), getRight(translations));
		return value;
	}
	public static BoatContainer Register(String name, BoatContainer value, List<Pair<String, String>> translations) { return Register(name, "boat", value, translations); }
	public static BoatContainer Register(String name, String type, BoatContainer value, List<Pair<String, String>> translations) {
		Register(name + "_" + type, value.asItem(), getLeft(translations));
		Register(name + "_chest_" + type, value.getChestBoat(), getRight(translations));
		return value;
	}
	public static PottedBlockContainer Register(String path, PottedBlockContainer value, List<Pair<String, String>> translations) {
		List<String> left = getLeft(translations);
		Register(path, value.asBlock(), left);
		Register(path, value.asItem(), left);
		Identifier id = ModId.ID(path);
		Register(new Identifier(id.getNamespace(), "potted_" + id.getPath()).toString(), value.getPottedBlock(), getRight(translations));
		return value;
	}
	public static FlowerPartContainer Register(String path, FlowerPartContainer value, List<Pair<String, String>> translations) {
		List<String> left = getLeft(translations);
		String seeds = path + "_seeds";
		Register(seeds, value.asBlock(), left);
		CompostingChanceRegistry.INSTANCE.add(value.asItem(), 0.3F);
		Register(seeds, value.asItem(), left);
		Register(path + "_petals", value.petalsItem(), getRight(translations));
		CompostingChanceRegistry.INSTANCE.add(value.petalsItem(), 0.325F);
		return value;
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType<T> value, List<String> translations) {
		int length = translations.size();
		Identifier id = ModId.ID(path);
		for (int i = 0; i < LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Entity Type: " + id);
			LANGUAGE_CACHES[i].TranslationKeys.put(Util.createTranslationKey("entity", ModId.ID(path)), translations.get(i));
		}
		return Registry.register(Registry.ENTITY_TYPE, id, value);
	}
	public static <T extends BlockEntity> BlockEntityType<T> Register(String path, BlockEntityType<T> value) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ModId.ID(path), value);
	}
	public static <T extends BlockEntity> void Register(String path, BlockContainer container, BlockEntityType<T> type, List<String> translations) {
		Register(path, container, translations);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, ModId.ID(path), type);
	}
	public static <T extends ParticleEffect> ParticleType<T> Register(String path, ParticleType<T> value) {
		return Registry.register(Registry.PARTICLE_TYPE, ModId.ID(path), value);
	}
	public static <T extends FeatureConfig> Feature<T> Register(String path, Feature<T> value) {
		return Registry.register(Registry.FEATURE, ModId.ID(path), value);
	}
	public static DefaultParticleType Register(String path, DefaultParticleType effect) {
		return Registry.register(Registry.PARTICLE_TYPE, ModId.ID(path), effect);
	}
	public static StatusEffect Register(String path, StatusEffect value, List<String> translations) {
		int length = translations.size();
		Identifier id = ModId.ID(path);
		if (length == 0) throw new RuntimeException("Must provide at least one translation for Status Effect: " + id);
		ModBase.EN_US.TranslationKeys.put(Util.createTranslationKey("effect", id), translations.get(0));
		return Registry.register(Registry.STATUS_EFFECT, id, value);
	}
	public static Enchantment Register(String path, Enchantment value, List<String> translations) {
		int length = translations.size();
		Identifier id = ModId.ID(path);
		if (length == 0) throw new RuntimeException("Must provide at least one translation for Enchantment: " + id);
		ModBase.EN_US.TranslationKeys.put(Util.createTranslationKey("enchantment", id), translations.get(0));
		return Registry.register(Registry.ENCHANTMENT, id, value);
	}
	public static int BIOME_RAW_ID = 11111000;
	public static Biome Register(RegistryKey<Biome> key, Biome biome) {
		BuiltinBiomesInvoker.Register(BIOME_RAW_ID++, key, biome);
		return biome;
	}
	public static PaintingMotive Register(String path, PaintingMotive painting) { return Registry.register(Registry.PAINTING_MOTIVE, ModId.ID(path), painting); }
	public static VillagerProfession Register(String path, VillagerProfession profession) { return Registry.register(Registry.VILLAGER_PROFESSION, ModId.ID(path), profession); }
	public static PointOfInterestType Register(String path, PointOfInterestType type) { return Registry.register(Registry.POINT_OF_INTEREST_TYPE, ModId.ID(path), type); }

	public static Item RegisterJuice(String path, Item value, Item source, List<String> translations) { return RegisterJuice(path, value, source::asItem, translations); }
	public static Item RegisterJuice(String path, Item value, Supplier<Item> source, List<String> translations) {
		JuicerBlock.JUICE_MAP.put(source, value);
		return Register(path, value, translations);
	}

	public static <T extends Recipe<?>> RecipeType<T> RegisterRecipeType(String path) {
		return Registry.register(Registry.RECIPE_TYPE, ModId.ID(path), new RecipeType<T>() { public String toString() { return path; } });
	}
	public static <T extends Recipe<?>> RecipeSerializer<T> Register(String path, RecipeSerializer<T> serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, ModId.ID(path), serializer);
	}
	public static <T extends Power> void Register(PowerFactory<T> powerFactory) { Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory); }
	public static <T extends Power> void Register(PowerFactorySupplier<T> factorySupplier) { Register(factorySupplier.createFactory()); }
	public static Identifier Register(String path, StatFormatter formatter) { return Register(ModId.ID(path), formatter); }
	public static Identifier Register(Identifier identifier, StatFormatter formatter) {
		Registry.register(Registry.CUSTOM_STAT, identifier, identifier);
		Stats.CUSTOM.getOrCreateStat(identifier, formatter);
		return identifier;
	}
	public static LootFunctionType Register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, ModId.ID(id), new LootFunctionType(jsonSerializer));
	}
	public static LootFunctionType Register(String id, LootFunctionType lootFunctionType) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, ModId.ID(id), lootFunctionType);
	}
	public static ArrowContainer Register(String id, ArrowContainer arrow, List<String> translations) {
		Register(id, arrow.asItem(), translations);
		Register(id, arrow.getEntityType(), translations);
		DispenserBlock.registerBehavior(arrow, new ProjectileDispenserBehavior(){
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				ModArrowEntity arrow = ((ModArrowItem)stack.getItem()).createArrow(world, position.getX(), position.getY(), position.getZ());
				arrow.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
				return arrow;
			}
		});
		return arrow;
	}
}
