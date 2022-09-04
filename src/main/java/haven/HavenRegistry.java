package haven;

import haven.blocks.*;
import haven.boats.HavenBoat;
import haven.boats.HavenBoatDispenserBehavior;
import haven.entities.*;
import haven.materials.WoodMaterial;
import haven.util.*;

import static haven.HavenMod.*;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

public class HavenRegistry {
	public static Block Register(String path, Block block) {
		return Registry.register(Registry.BLOCK, ID(path), block);
	}
	public static Item Register(String path, Item item) {
		return Registry.register(Registry.ITEM, ID(path), item);
	}
	public static void Register(String path, HavenPair pair) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, pair.BLOCK);
		Registry.register(Registry.ITEM, id, pair.ITEM);
	}
	public static <T extends BlockEntity> BlockEntityType Register(String path, BlockEntityType<T> entity) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), entity);
	}
	public static StatusEffect Register(String path, StatusEffect effect) {
		return Registry.register(Registry.STATUS_EFFECT, ID(path), effect);
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	public static void Register(String path, PottedBlock potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.BLOCK);
		Registry.register(Registry.ITEM, id, potted.ITEM);
		Registry.register(Registry.BLOCK, ID("potted_" + path), potted.POTTED);
	}
	public static void Register(String path, String wallPath, HavenTorch torch) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, torch.BLOCK);
		Registry.register(Registry.BLOCK, ID(wallPath), torch.WALL_BLOCK);
		Registry.register(Registry.ITEM, id, torch.ITEM);
	}
	public static void Register(String name, WoodMaterial material) {
		Register(name + "_log", material.LOG);
		Register("stripped_" + name + "_log", material.STRIPPED_LOG);
		Register(name + "_wood", material.WOOD);
		Register("stripped_" + name + "_wood", material.STRIPPED_WOOD);

		Register(name + "_leaves", material.LEAVES);
		Register(name + "_sapling", material.SAPLING);

		Register(name + "_planks", material.PLANKS);
		Register(name + "_stairs", material.STAIRS);
		Register(name + "_slab", material.SLAB);

		Register(name + "_fence", material.FENCE);
		FuelRegistry.INSTANCE.add(material.FENCE.ITEM, 300);
		Register(name + "_fence_gate", material.FENCE_GATE);
		FuelRegistry.INSTANCE.add(material.FENCE_GATE.ITEM, 300);
		Register(name + "_door", material.DOOR);
		Register(name + "_trapdoor", material.TRAPDOOR);
		Register(name + "_pressure_plate", material.PRESSURE_PLATE);
		Register(name + "_button", material.BUTTON);

		//TODO: Signs don't work right (fail out of edit screens and go invisible)
		//Register(name + "_sign", material.SIGN_BLOCK, material.SIGN_ITEM);
		//Register(name + "_wall_sign", material.WALL_SIGN_BLOCK);

		Register(material.BOAT);

		if (material.isFlammable) {
			FlammableBlockRegistry.getDefaultInstance().add(material.LOG.BLOCK, 5, 5);
			FlammableBlockRegistry.getDefaultInstance().add(material.STRIPPED_LOG.BLOCK, 5, 5);
			FlammableBlockRegistry.getDefaultInstance().add(material.WOOD.BLOCK, 5, 5);
			FlammableBlockRegistry.getDefaultInstance().add(material.STRIPPED_WOOD.BLOCK, 5, 5);
			FlammableBlockRegistry.getDefaultInstance().add(material.LEAVES.BLOCK, 30, 60);
			FlammableBlockRegistry.getDefaultInstance().add(material.PLANKS.BLOCK, 5, 20);
			FlammableBlockRegistry.getDefaultInstance().add(material.STAIRS.BLOCK, 5, 20);
			FlammableBlockRegistry.getDefaultInstance().add(material.SLAB.BLOCK, 5, 20);
			FlammableBlockRegistry.getDefaultInstance().add(material.FENCE.BLOCK, 5, 20);
			FlammableBlockRegistry.getDefaultInstance().add(material.FENCE_GATE.BLOCK, 5, 20);
		}
	}
	public static void Register(HavenBoat boat) {
		Register(boat.TYPE.getName() + "_boat", boat.ITEM);
		DispenserBlock.registerBehavior(boat.ITEM, new HavenBoatDispenserBehavior(boat.TYPE));
	}

	public static void RegisterAnchors() {
		Register("anchor", ANCHOR);
		Register("anchor_block_entity", ANCHOR_BLOCK_ENTITY);
		Register("substitute_anchor", SUBSTITUTE_ANCHOR_BLOCK);
		Register("substitute_anchor_block_entity", SUBSTITUTE_ANCHOR_BLOCK_ENTITY);
		//Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
			Register(ANCHOR_MAP.get(owner) + "_core", ANCHOR_CORES.get(owner));
		}
	}
	public static void RegisterAmethyst() {
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK);
		//Amethyst Bricks
		Register("amethyst_bricks", AMETHYST_BRICKS);
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB);
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS);
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL);
		//Amethyst Variants
		Register("amethyst_slab", AMETHYST_SLAB);
		Register("amethyst_stairs", AMETHYST_STAIRS);
		Register("amethyst_wall", AMETHYST_WALL);
	}
	public static void RegisterFlowers() {
		//Carnations
		for (DyeColor color : COLORS) {
			HavenFlower carnation = CARNATIONS.get(color);
			Register(color.getName() + "_carnation", carnation);
			FlammableBlockRegistry.getDefaultInstance().add(carnation.BLOCK, 60, 100);
		}
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		FlammableBlockRegistry.getDefaultInstance().add(BUTTERCUP.BLOCK, 60, 100);
		Register("pink_daisy", PINK_DAISY);
		FlammableBlockRegistry.getDefaultInstance().add(PINK_DAISY.BLOCK, 60, 100);
		//Other Flowers
		Register("rose", ROSE);
		FlammableBlockRegistry.getDefaultInstance().add(ROSE.BLOCK, 60, 100);
		Register("blue_rose", BLUE_ROSE);
		FlammableBlockRegistry.getDefaultInstance().add(BLUE_ROSE.BLOCK, 60, 100);
		Register("magenta_tulip", MAGENTA_TULIP);
		FlammableBlockRegistry.getDefaultInstance().add(MAGENTA_TULIP.BLOCK, 60, 100);
		Register("marigold", MARIGOLD);
		FlammableBlockRegistry.getDefaultInstance().add(MARIGOLD.BLOCK, 60, 100);
		Register("pink_allium", PINK_ALLIUM);
		FlammableBlockRegistry.getDefaultInstance().add(PINK_ALLIUM.BLOCK, 60, 100);
		Register("lavender", LAVENDER);
		FlammableBlockRegistry.getDefaultInstance().add(LAVENDER.BLOCK, 60, 100);
		Register("hydrangea", HYDRANGEA);
		FlammableBlockRegistry.getDefaultInstance().add(HYDRANGEA.BLOCK, 60, 100);
		Register("amaranth", AMARANTH);
		FlammableBlockRegistry.getDefaultInstance().add(AMARANTH.BLOCK, 60, 100);
		Register("tall_allium", TALL_ALLIUM);
		FlammableBlockRegistry.getDefaultInstance().add(TALL_ALLIUM.BLOCK, 60, 100);
		Register("tall_pink_allium", TALL_PINK_ALLIUM);
		FlammableBlockRegistry.getDefaultInstance().add(TALL_PINK_ALLIUM.BLOCK, 60, 100);
	}
	public static void RegisterSoftTNT() {
		Register("soft_tnt", SOFT_TNT);
		Register("soft_tnt", SOFT_TNT_ENTITY);
		DispenserBlock.registerBehavior(SOFT_TNT.BLOCK, new ItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
				SoftTntEntity tntEntity = new SoftTntEntity(world, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, (LivingEntity)null);
				world.spawnEntity(tntEntity);
				world.playSound((PlayerEntity)null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent((Entity)null, GameEvent.ENTITY_PLACE, blockPos);
				stack.decrement(1);
				return stack;
			}
		});
	}
	public static void RegisterCoffee() {
		Register("coffee_plant", COFFEE_PLANT);
		FlammableBlockRegistry.getDefaultInstance().add(COFFEE_PLANT, 30, 60);
		Register("coffee_cherry", COFFEE_CHERRY);
		Register("coffee_beans", COFFEE_BEANS);
		Register("coffee", COFFEE);
		Register("black_coffee", BLACK_COFFEE);
	}
	public static void RegisterCherry() {
		Register("cherry", CHERRY);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cherry_tree"), CHERRY_TREE);
		Register("white_cherry_leaves", WHITE_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(WHITE_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("white_cherry_tree"), WHITE_CHERRY_TREE);
		Register("pale_cherry_leaves", PALE_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(PALE_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pale_cherry_tree"), PALE_CHERRY_TREE);
		Register("pink_cherry_leaves", PINK_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(PINK_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pink_cherry_tree"), PINK_CHERRY_TREE);
		Register("cherry", CHERRY_ITEM);
	}
	public static void RegisterCinnamon() {
		Register("cinnamon", CINNAMON);
		Register("cassia", CASSIA);
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(FLOWERING_CASSIA_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NAMESPACE, "cassia_tree"), CASSIA_TREE);
		Register("snickerdoodle", SNICKERDOODLE);
		Register("cinnamon_roll", CINNAMON_ROLL);
		Register("apple_cider", APPLE_CIDER);
	}
	public static void RegisterCandy() {
		Register("cinnamon_bean", CINNAMON_BEAN);
		Register("pink_cotton_candy", PINK_COTTON_CANDY);
		Register("blue_cotton_candy", BLUE_COTTON_CANDY);
		Register("candy_cane", CANDY_CANE);
		Register("caramel", CARAMEL);
		Register("caramel_apple", CARAMEL_APPLE);
		//Amethyst & Rock Candy
		Register("amethyst_candy", AMETHYST_CANDY);
		for(DyeColor color : COLORS) {
			Register(color.getName() + "_rock_candy", ROCK_CANDIES.get(color));
		}
	}
	public static void RegisterThrowableTomatoes() {
		Register("throwable_tomato", THROWABLE_TOMATO_ITEM);
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(NAMESPACE, "thrown_tomato"), TOMATO_PARTICLE);
		Register("boo", BOO_EFFECT);
		Register("killjoy", KILLJOY_EFFECT);
	}
	public static void RegisterServerBlood() {
		Register("blood_block", BLOOD_BLOCK);
		Register("blood_fence", BLOOD_FENCE);
		Register("blood_pane", BLOOD_PANE);
		Register("blood_slab", BLOOD_SLAB);
		Register("blood_stairs", BLOOD_STAIRS);
		Register("blood_wall", BLOOD_WALL);
		Register("dried_blood_block", DRIED_BLOOD_BLOCK);
		Register("dried_blood_fence", DRIED_BLOOD_FENCE);
		Register("dried_blood_pane", DRIED_BLOOD_PANE);
		Register("dried_blood_slab", DRIED_BLOOD_SLAB);
		Register("dried_blood_stairs", DRIED_BLOOD_STAIRS);
		Register("dried_blood_wall", DRIED_BLOOD_WALL);

		Register("blood_bucket", BLOOD_BUCKET);
		Register("blood_cauldron", BLOOD_CAULDRON);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BUCKET, BloodCauldronBlock.FILL_FROM_BUCKET);
		CauldronFluidContent.registerCauldron(BLOOD_CAULDRON, STILL_BLOOD_FLUID, FluidConstants.BOTTLE, LeveledCauldronBlock.LEVEL);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, BloodCauldronBlock.EMPTY_TO_BOTTLE);
		Register("blood_bottle", BLOOD_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
		Registry.register(Registry.FLUID, ID("still_blood"), STILL_BLOOD_FLUID);
		Registry.register(Registry.FLUID, ID("flowing_blood"), FLOWING_BLOOD_FLUID);
		Register("blood_fluid_block", BLOOD_FLUID_BLOCK);
	}
	public static void RegisterAngelBat() {
		Register("angel_bat", ANGEL_BAT_ENTITY);
		SpawnRestrictionAccessor.callRegister(ANGEL_BAT_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelBatEntity::CanSpawn);
		FabricDefaultAttributeRegistry.register(ANGEL_BAT_ENTITY, AngelBatEntity.createBatAttributes());
	}
	public static void RegisterChickenVariants() {
		Register("fancy_chicken", FANCY_CHICKEN_ENTITY);
		SpawnRestrictionAccessor.callRegister(FANCY_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntity.createChickenAttributes());
		Register("fancy_chicken_spawn_egg", FANCY_CHICKEN_SPAWN_EGG);
		Register("fancy_feather", FANCY_FEATHER);
	}
	public static void RegisterCowVariants() {
		Register("moobloom", MOOBLOOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOBLOOM_ENTITY, MoobloomEntity.createCowAttributes());
		Register("moobloom_spawn_egg", MOOBLOOM_SPAWN_EGG);
		Register("mooblossom", MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntity.createCowAttributes());
		Register("mooblossom_spawn_egg", MOOBLOSSOM_SPAWN_EGG);
		Register("magenta_mooblossom_tulip", MAGENTA_MOOBLOSSOM_TULIP);
		Register("moolip", MOOLIP_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOLIP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoolipEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOLIP_ENTITY, MoolipEntity.createCowAttributes());
		Register("moolip_spawn_egg", MOOLIP_SPAWN_EGG);
		Register("orange_mooblossom", ORANGE_MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(ORANGE_MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OrangeMooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntity.createCowAttributes());
		Register("orange_mooblossom_spawn_egg", ORANGE_MOOBLOSSOM_SPAWN_EGG);
		Register("orange_mooblossom_tulip", ORANGE_MOOBLOSSOM_TULIP);
		//Nether Mooshrooms
		Register("crimson_mooshroom", CRIMSON_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(CRIMSON_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrimsonMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(CRIMSON_MOOSHROOM_ENTITY, CrimsonMooshroomEntity.createCowAttributes());
		Register("crimson_mooshroom_spawn_egg", CRIMSON_MOOSHROOM_SPAWN_EGG);
		Register("warped_mooshroom", WARPED_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(WARPED_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpedMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(WARPED_MOOSHROOM_ENTITY, WarpedMooshroomEntity.createCowAttributes());
		Register("warped_mooshroom_spawn_egg", WARPED_MOOSHROOM_SPAWN_EGG);
	}
	public static void RegisterMilks() {
		Register("chocolate_milk_bucket", CHOCOLATE_MILK_BUCKET);
		Register("strawberry_milk_bucket", STRAWBERRY_MILK_BUCKET);
		Register("coffee_milk_bucket", COFFEE_MILK_BUCKET);
	}
	public static void RegisterCakes() {
		Register("chocolate_cake", CHOCOLATE_CAKE);
		Register("chocolate_candle_cake", CHOCOLATE_CANDLE_CAKE);
		for(DyeColor color : COLORS) Register(color + "_chocolate_candle_cake", CHOCOLATE_CANDLE_CAKES.get(color));
		Register("strawberry_cake", STRAWBERRY_CAKE);
		Register("strawberry_candle_cake", STRAWBERRY_CANDLE_CAKE);
		for(DyeColor color : COLORS) Register(color + "_strawberry_candle_cake", STRAWBERRY_CANDLE_CAKES.get(color));
		Register("coffee_cake", COFFEE_CAKE);
		Register("coffee_candle_cake", COFFEE_CANDLE_CAKE);
		for(DyeColor color : COLORS) Register(color + "_coffee_candle_cake", COFFEE_CANDLE_CAKES.get(color));
		Register("carrot_cake", CARROT_CAKE);
		Register("carrot_candle_cake", CARROT_CANDLE_CAKE);
		for(DyeColor color : COLORS) Register(color + "_carrot_candle_cake", CARROT_CANDLE_CAKES.get(color));
	}
	public static void RegisterBottledConfetti() {
		Register("bottled_confetti", BOTTLED_CONFETTI_ITEM);
		Register("bottled_confetti", BOTTLED_CONFETTI_ENTITY);
		Register("confetti_cloud", CONFETTI_CLOUD_ENTITY);
	}
	public static void RegisterMoreCopper() {
		Register("copper_nugget", COPPER_NUGGET);
		//Medium Weighted Pressure Plates
		Register("medium_weighted_pressure_plate", MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("exposed_medium_weighted_pressure_plate", EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("weathered_medium_weighted_pressure_plate", WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("oxidized_medium_weighted_pressure_plate", OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("waxed_medium_weighted_pressure_plate", WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("waxed_exposed_medium_weighted_pressure_plate", WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("waxed_weathered_medium_weighted_pressure_plate", WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		Register("waxed_oxidized_medium_weighted_pressure_plate", WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
		//Lanterns
		Register("copper_lantern", COPPER_LANTERN);
		Register("exposed_copper_lantern", EXPOSED_COPPER_LANTERN);
		Register("weathered_copper_lantern", WEATHERED_COPPER_LANTERN);
		Register("oxidized_copper_lantern", OXIDIZED_COPPER_LANTERN);
		Register("waxed_copper_lantern", WAXED_COPPER_LANTERN);
		Register("waxed_exposed_copper_lantern", WAXED_EXPOSED_COPPER_LANTERN);
		Register("waxed_weathered_copper_lantern", WAXED_WEATHERED_COPPER_LANTERN);
		Register("waxed_oxidized_copper_lantern", WAXED_OXIDIZED_COPPER_LANTERN);
		//Chains
		Register("copper_chain", COPPER_CHAIN);
		Register("exposed_copper_chain", EXPOSED_COPPER_CHAIN);
		Register("weathered_copper_chain", WEATHERED_COPPER_CHAIN);
		Register("oxidized_copper_chain", OXIDIZED_COPPER_CHAIN);
		Register("waxed_copper_chain", WAXED_COPPER_CHAIN);
		Register("waxed_exposed_copper_chain", WAXED_EXPOSED_COPPER_CHAIN);
		Register("waxed_weathered_copper_chain", WAXED_WEATHERED_COPPER_CHAIN);
		Register("waxed_oxidized_copper_chain", WAXED_OXIDIZED_COPPER_CHAIN);
		//Bars
		Register("copper_bars", COPPER_BARS);
		Register("exposed_copper_bars", EXPOSED_COPPER_BARS);
		Register("weathered_copper_bars", WEATHERED_COPPER_BARS);
		Register("oxidized_copper_bars", OXIDIZED_COPPER_BARS);
		Register("waxed_copper_bars", WAXED_COPPER_BARS);
		Register("waxed_exposed_copper_bars", WAXED_EXPOSED_COPPER_BARS);
		Register("waxed_weathered_copper_bars", WAXED_WEATHERED_COPPER_BARS);
		Register("waxed_oxidized_copper_bars", WAXED_OXIDIZED_COPPER_BARS);
		//Cut Copper Pillars
		Register("cut_copper_pillar", CUT_COPPER_PILLAR);
		Register("exposed_cut_copper_pillar", EXPOSED_CUT_COPPER_PILLAR);
		Register("weathered_cut_copper_pillar", WEATHERED_CUT_COPPER_PILLAR);
		Register("oxidized_cut_copper_pillar", OXIDIZED_CUT_COPPER_PILLAR);
		Register("waxed_cut_copper_pillar", WAXED_CUT_COPPER_PILLAR);
		Register("waxed_exposed_cut_copper_pillar", WAXED_EXPOSED_CUT_COPPER_PILLAR);
		Register("waxed_weathered_cut_copper_pillar", WAXED_WEATHERED_CUT_COPPER_PILLAR);
		Register("waxed_oxidized_cut_copper_pillar", WAXED_OXIDIZED_CUT_COPPER_PILLAR);
	}
	public static void RegisterMoreGold() {
		Register("gold_lantern", GOLD_LANTERN);
		Register("gold_chain", GOLD_CHAIN);
		Register("gold_bars", GOLD_BARS);
	}

	public static void RegisterAll() {
		RegisterAnchors();
		Register("bone_torch", "bone_wall_torch", BONE_TORCH);
		Register("tinker_toy", TINKER_TOY);
		Register("charcoal_block", CHARCOAL_BLOCK);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK.ITEM, 16000);
		RegisterFlowers();
		RegisterAmethyst();
		Register("pteror", PTEROR);
		Register("sbehesohe", SBEHESOHE);
		Register("broken_bottle", BROKEN_BOTTLE);
		Register("locket", LOCKET);
		Register("emerald_locket", EMERALD_LOCKET);
		RegisterSoftTNT();
		RegisterCoffee();
		Registry.register(Registry.ENTITY_TYPE, "haven_boat", BOAT_ENTITY);
		RegisterCherry();
		RegisterCinnamon();
		Register(CRIMSON_BOAT);
		Register(WARPED_BOAT);
		Register("ramen", RAMEN);
		Register("stir_fry", STIR_FRY);
		RegisterCandy();
		RegisterThrowableTomatoes();
		RegisterServerBlood();
		RegisterAngelBat();
		RegisterChickenVariants();
		RegisterCowVariants();
		RegisterMilks();
		RegisterCakes();
		RegisterBottledConfetti();
		RegisterMoreCopper();
		RegisterMoreGold();
	}
}
