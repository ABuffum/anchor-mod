package fun.wich.sound;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import fun.wich.ModBase;
import fun.wich.container.BlockContainer;
import fun.wich.container.CakeContainer;
import fun.wich.entity.hostile.piranha.PiranhaEntity;
import fun.wich.entity.vehicle.ModBoatEntity;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.mixins.entity.vehicle.BoatEntityAccessor;
import fun.wich.util.ColorUtil;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.block.*;
import net.minecraft.block.enums.PistonType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static fun.wich.ModBase.*;
import static fun.wich.registry.ModBambooRegistry.*;
import static fun.wich.registry.ModCopperRegistry.*;

public class IdentifiedSounds {
	public static final String IDENTIFY = "identified_sound.";
	public static boolean Active(Entity entity) { return ModBase.IDENTIFIED_SOUNDS_POWER.isActive(entity); }
	private static SoundEvent makeSound(String name, List<String> translations) {
		SoundEvent sound = ModSoundEvents.registerSoundEvent(IDENTIFY + name);
		SubtitleOverrides.Register(sound, name, translations);
		return sound;
	}

	public record FallSounds(SoundEvent small, SoundEvent big) { }

	private static class EntitySounds {
		public final String name;
		public final SoundEvent stepSound, crashSound, swimSound, splashSound, highSpeedSplashSound, deathSound;
		public final FallSounds fallSounds;
		public EntitySounds(String name, SoundEvent step, SoundEvent trip, SoundEvent fall, SoundEvent crash, SoundEvent swim, SoundEvent splash, SoundEvent fastSplash, SoundEvent death) {
			this.name = name;
			this.stepSound = step;
			this.fallSounds = new FallSounds(trip, fall);
			this.crashSound = crash;
			this.swimSound = swim;
			this.splashSound = splash;
			this.highSpeedSplashSound = fastSplash;
			this.deathSound = death;
		}
	}
	private static EntitySounds makeEntitySounds(String name, List<String> translations, boolean steps, boolean trip, boolean fall, boolean crash, boolean swim, boolean splash, boolean fastSplash, boolean death) {
		SoundEvent stepSound = null, tripSound = null, fallSound = null, crashSound = null, swimSound = null, splashSound = null, highSpeedSplashSound = null, deathSound = null;
		int length = translations.size();
		List<String> stepTranslations = new ArrayList<>(), tripTranslations = new ArrayList<>();
		List<String> fallTranslations = new ArrayList<>(), crashTranslations = new ArrayList<>();
		List<String> swimTranslations = new ArrayList<>(), splashTranslations = new ArrayList<>();
		List<String> highSpeedSplashTranslations = new ArrayList<>(), deathTranslations = new ArrayList<>();
		for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
			if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & BlockSoundGroup");
			String translation = translations.get(i);
			stepTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Step(translation));
			tripTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Small_Fall(translation));
			fallTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Big_Fall(translation));
			crashTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Crash(translation));
			swimTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Swim(translation));
			splashTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Splash(translation));
			highSpeedSplashTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Splash_High_Speed(translation));
			deathTranslations.add(LANGUAGE_CACHES[i].Subtitle_Entity_Death(translation));
		}
		if (steps) stepSound = makeSound("footsteps." + name, stepTranslations);
		if (trip) tripSound = makeSound("small_fall." + name, tripTranslations);
		if (fall) fallSound = makeSound("big_fall." + name, fallTranslations);
		if (crash) crashSound = makeSound("crash." + name, crashTranslations);
		if (swim) swimSound = makeSound("swim." + name, swimTranslations);
		if (splash) splashSound = makeSound("splash." + name, splashTranslations);
		if (fastSplash) highSpeedSplashSound = makeSound("splash.high_speed." + name, highSpeedSplashTranslations);
		if (death) deathSound = makeSound("death." + name, deathTranslations);
		return new EntitySounds(name, stepSound, tripSound, fallSound, crashSound, swimSound, splashSound, highSpeedSplashSound, deathSound);
	}
	private static final EntitySounds AXOLOTL_ENTITY = makeEntitySounds("axolotl", List.of(EN_US.Axolotl()), true, false, false, false, false, false, false, false);
	private static final EntitySounds BAT_ENTITY = makeEntitySounds("bat", List.of(EN_US.Bat()), false, false, false, false, false, true, false, false);
	private static final EntitySounds CAT_ENTITY = makeEntitySounds("cat", List.of(EN_US.Cat()), true, false, false, false, false, true, false, false);
	private static final EntitySounds CHICKEN_ENTITY = makeEntitySounds("chicken", List.of(EN_US.Chicken()), false, false, false, false, false, true, false, false);
	private static final EntitySounds COD_ENTITY = makeEntitySounds("cod", List.of(EN_US.Cod()), false, false, false, false, true, true, false, false);
	private static final EntitySounds CREEPER_ENTITY = makeEntitySounds("creeper", List.of(EN_US.Creeper()), true, false, false, false, false, false, false, false);
	private static final EntitySounds FOX_ENTITY = makeEntitySounds("fox", List.of(EN_US.Fox()), true, false, false, false, false, false, false, false);
	private static final EntitySounds GLOW_SQUID_ENTITY = makeEntitySounds("glow_squid", List.of(EN_US.Squid(EN_US.Glow())), false, false, false, false, false, true, false, false);
	private static final EntitySounds OCELOT_ENTITY = makeEntitySounds("ocelot", List.of(EN_US.Ocelot()), true, false, false, false, false, false, false, false);
	private static final EntitySounds PIRANHA_ENTITY = makeEntitySounds("piranha", List.of(EN_US.Piranha()), false, false, false, false, true, true, false, false);
	private static final EntitySounds PUFFERFISH_ENTITY = makeEntitySounds("pufferfish", List.of(EN_US.Pufferfish()), false, false, false, false, true, true, false, false);
	private static final EntitySounds RABBIT_ENTITY = makeEntitySounds("rabbit", List.of(EN_US.Rabbit()), true, false, false, false, false, false, false, false);
	private static final EntitySounds SALMON_ENTITY = makeEntitySounds("salmon", List.of(EN_US.Salmon()), false, false, false, false, true, true, false, false);
	private static final EntitySounds SQUID_ENTITY = makeEntitySounds("squid", List.of(EN_US.Squid()), false, false, false, false, false, true, false, false);
	private static final EntitySounds TROPICAL_FISH_ENTITY = makeEntitySounds("tropical_fish", List.of(EN_US.Fish(EN_US.Tropical())), false, false, false, false, true, true, false, false);
	private static final EntitySounds VILLAGER_ENTITY = makeEntitySounds("villager", List.of(EN_US.Villager()), true, false, false, false, false, false, false, false);
	private static final EntitySounds WANDERING_TRADER_ENTITY = makeEntitySounds("wandering_trader", List.of(EN_US.Trader(EN_US.Wandering())), true, false, false, false, false, false, false, false);

	private static final Map<PowerType<?>, EntitySounds> powerSounds = new HashMap<>();
	public static void RegisterPower(String namespace, String name, List<String> stepTranslations, List<String> tripTranslations, List<String> fallTranslations, List<String> crashTranslations, List<String> swimTranslations, List<String> splashTranslations, List<String> highSpeedSplashTranslations, List<String> deathTranslations) {
		PowerType<?> power = new PowerTypeReference<>(new Identifier(namespace, "identified_sounds/" + name));
		String soundName = namespace + "." + name;
		SoundEvent stepSound = makeSound("footsteps." + soundName, stepTranslations);
		SoundEvent tripSound = makeSound("small_fall." + soundName, tripTranslations);
		SoundEvent fallSound = makeSound("big_fall." + soundName, fallTranslations);
		SoundEvent crashSound = makeSound("crash." + soundName, crashTranslations);
		SoundEvent swimSound = makeSound("swim." + soundName, swimTranslations);
		SoundEvent splashSound = makeSound("splash." + soundName, splashTranslations);
		SoundEvent highSpeedSplashSound = makeSound("splash.high_speed." + soundName, highSpeedSplashTranslations);
		SoundEvent deathSound = makeSound("death." + soundName, deathTranslations);
		powerSounds.put(power, new EntitySounds(name, stepSound, tripSound, fallSound, crashSound, swimSound, splashSound, highSpeedSplashSound, deathSound));
	}
	public static EntitySounds getEntitySounds(Entity entity) {
		if (entity == null) return null;
		for (Map.Entry<PowerType<?>, EntitySounds> entry : powerSounds.entrySet()) {
			if (entry.getKey().isActive(entity)) return entry.getValue();
		}
		if (entity instanceof AxolotlEntity) return AXOLOTL_ENTITY;
		if (entity instanceof BatEntity) return BAT_ENTITY;
		if (entity instanceof CatEntity) return CAT_ENTITY;
		if (entity instanceof ChickenEntity) return CHICKEN_ENTITY;
		if (entity instanceof CodEntity) return COD_ENTITY;
		if (entity instanceof CreeperEntity) return CREEPER_ENTITY;
		if (entity instanceof FoxEntity) return FOX_ENTITY;
		if (entity instanceof GlowSquidEntity) return GLOW_SQUID_ENTITY;
		if (entity instanceof OcelotEntity) return OCELOT_ENTITY;
		if (entity instanceof PiranhaEntity) return PIRANHA_ENTITY;
		if (entity instanceof PufferfishEntity) return PUFFERFISH_ENTITY;
		if (entity instanceof RabbitEntity) return RABBIT_ENTITY;
		if (entity instanceof SalmonEntity) return SALMON_ENTITY;
		if (entity instanceof SquidEntity) return SQUID_ENTITY;
		if (entity instanceof TropicalFishEntity) return TROPICAL_FISH_ENTITY;
		if (entity instanceof VillagerEntity) return VILLAGER_ENTITY;
		if (entity instanceof WanderingTraderEntity) return WANDERING_TRADER_ENTITY;
		return null;
	}
	public static SoundEvent getStepSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.stepSound : null;
	}
	public static FallSounds getFallSounds(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.fallSounds : null;
	}
	public static SoundEvent getCrashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.crashSound : null;
	}
	public static SoundEvent getSwimSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.swimSound : null;
	}
	public static SoundEvent getSplashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.splashSound : null;
	}
	public static SoundEvent getHighSpeedSplashSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		if (sounds != null && sounds.highSpeedSplashSound != null) return sounds.highSpeedSplashSound;
		return getSplashSound(entity);
	}
	public static SoundEvent getDeathSound(Entity entity) {
		EntitySounds sounds = getEntitySounds(entity);
		return sounds != null ? sounds.deathSound : null;
	}

	private static class BoatSounds {
		public final SoundEvent land, water;
		public BoatSounds(String name, List<String> translations) {
			int length = translations.size();
			List<String> landTranslations = new ArrayList<>(), waterTranslations = new ArrayList<>();
			for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
				if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & Boat: " + name);
				String translation = translations.get(i);
				landTranslations.add(LANGUAGE_CACHES[i].Subtitle_Boat_Paddle(translation));
				waterTranslations.add(LANGUAGE_CACHES[i].Subtitle_Boat_Row(translation));
			}

			this.land = makeSound("paddle.land." + name, landTranslations);
			this.water = makeSound("paddle.water." + name, waterTranslations);
		}
	}
	private static final Map<String, BoatSounds> boatSounds = new HashMap<>();
	public static SoundEvent getPaddleSound(BoatEntity boat) {
		String name;
		if (boat instanceof ModBoatEntity modBoat) name = modBoat.getModBoatType().getName();
		else name = boat.getBoatType().getName();
		if (boatSounds.containsKey(name)) {
			switch (((BoatEntityAccessor) boat).getLocation()) {
				case IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER -> { return boatSounds.get(name).water; }
				case ON_LAND -> { return boatSounds.get(name).land; }
			}
		}
		return null;
	}

	private static List<String> Translations(BoatEntity.Type type) {
		return switch (type) {
			case OAK -> List.of(EN_US.Oak());
			case SPRUCE -> List.of(EN_US.Spruce());
			case BIRCH -> List.of(EN_US.Birch());
			case JUNGLE -> List.of(EN_US.Jungle());
			case ACACIA -> List.of(EN_US.Acacia());
			case DARK_OAK -> List.of(EN_US.Oak(EN_US.Dark()));
		};
	}

	public static void RegisterAll() {
		//Boats
		for (BoatEntity.Type type : BoatEntity.Type.values()) {
			String name = type.getName();
			boatSounds.put(name, new BoatSounds(name, Translations(type)));
		}
		boatSounds.put(MANGROVE_BOAT.getType().getName(), new BoatSounds("mangrove", List.of(EN_US.Mangrove())));
		boatSounds.put(BAMBOO_RAFT.getType().getName(), new BoatSounds("bamboo", List.of(EN_US.Bamboo())));
		boatSounds.put(CHARRED_BOAT.getType().getName(), new BoatSounds("charred", List.of(EN_US.Charred())));
		//Blocks
		RegisterBlockSounds();
	}

	private static class BlockSounds {
		public final SoundEvent breakSound, stepSound, placeSound, hitSound;
		public BlockSounds(String name, List<String> translations) {
			int length = translations.size();
			List<String> breakTranslations = new ArrayList<>(), hitTranslations = new ArrayList<>();
			List<String> placeTranslations = new ArrayList<>(), stepTranslations = new ArrayList<>();
			for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
				if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & BlockSoundGroup");
				String translation = translations.get(i);
				breakTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Break(translation));
				hitTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Hit(translation));
				placeTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Place(translation));
				stepTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Step(translation));
			}
			this.breakSound = makeBreak(name, breakTranslations);
			this.stepSound = makeStep(name, stepTranslations);
			this.placeSound = makePlace(name, placeTranslations);
			this.hitSound = makeHit(name, hitTranslations);
		}
		public BlockSounds(SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound) {
			this.breakSound = breakSound;
			this.stepSound = stepSound;
			this.placeSound = placeSound;
			this.hitSound = hitSound;
		}
		public BlockSounds(BlockSoundGroup soundGroup) {
			this.breakSound = soundGroup.getBreakSound();
			this.stepSound = soundGroup.getStepSound();
			this.placeSound = soundGroup.getPlaceSound();
			this.hitSound = soundGroup.getHitSound();
		}
		public static BlockSounds BreakPlace(String name, SoundEvent breakSound, SoundEvent placeSound, List<String> translations) {
			int length = translations.size();
			List<String> hitTranslations = new ArrayList<>(), stepTranslations = new ArrayList<>();
			for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
				if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & BlockSoundGroup");
				String translation = translations.get(i);
				hitTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Hit(translation));
				stepTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Step(translation));
			}
			return new BlockSounds(breakSound, makeStep(name, stepTranslations), placeSound, makeHit(name, hitTranslations));
		}
		public static BlockSounds StepOnly(SoundEvent stepSound)  { return new BlockSounds(null, stepSound, null, null); }
		public static BlockSounds Place(String name, SoundEvent placeSound, List<String> translations)  {
			int length = translations.size();
			List<String> breakTranslations = new ArrayList<>(), hitTranslations = new ArrayList<>();
			List<String> stepTranslations = new ArrayList<>();
			for (int i = 0; i < ModBase.LANGUAGE_CACHES.length; i++) {
				if (length <= i) throw new RuntimeException("Missing translation for Language: " + LANGUAGE_CACHES[i].getLanguageCode() + " & BlockSoundGroup");
				String translation = translations.get(i);
				breakTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Break(translation));
				hitTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Hit(translation));
				stepTranslations.add(LANGUAGE_CACHES[i].Subtitle_Block_Step(translation));
			}
			return new BlockSounds(makeBreak(name, breakTranslations), makeStep(name, stepTranslations), placeSound, makeHit(name, hitTranslations));
		}
		private static SoundEvent makeBreak(String name, List<String> translations) { return makeSound("block.break." + name, translations); }
		private static SoundEvent makeStep(String name, List<String> translations) { return makeSound("block.step." + name, translations); }
		private static SoundEvent makePlace(String name, List<String> translations) { return makeSound("block.place." + name, translations); }
		private static SoundEvent makeHit(String name, List<String> translations) { return makeSound("block.hit." + name, translations); }
	}

	private static final Map<Block, BlockSounds> blockSounds = new HashMap<>();
	private static final Map<Identifier, Block> copyBlockSounds = new HashMap<>();
	private static void Register(String name, List<String> translations, Block... blocks) { Register(new BlockSounds(name, translations), blocks); }
	private static void Register(BlockSounds sounds, Block... blocks) { for (Block block : blocks) blockSounds.put(block, sounds); }
	private static void Register(BlockSounds sounds, CakeContainer... cakes) {
		for (CakeContainer cake : cakes) {
			Register(sounds, cake.CAKE.asBlock(), cake.CANDLE_CAKE, cake.SOUL_CANDLE_CAKE, cake.ENDER_CANDLE_CAKE, cake.NETHERRACK_CANDLE_CAKE);
			for (DyeColor color : DyeColor.values()) Register(sounds, cake.CANDLE_CAKES.get(color));
			Register(sounds, cake.BEIGE_CANDLE_CAKE, cake.BURGUNDY_CANDLE_CAKE, cake.LAVENDER_CANDLE_CAKE, cake.MINT_CANDLE_CAKE);
		}
	}
	private static void RegisterBlockSounds() {
		Register("andesite", List.of(EN_US.Andesite()), Blocks.ANDESITE, Blocks.ANDESITE_STAIRS, Blocks.ANDESITE_SLAB, Blocks.ANDESITE_WALL,
				Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_ANDESITE_SLAB, /* Mod */ POLISHED_ANDESITE_WALL.asBlock(),
				ANDESITE_BRICKS.asBlock(), ANDESITE_BRICK_STAIRS.asBlock(), ANDESITE_BRICK_SLAB.asBlock(), ANDESITE_BRICK_WALL.asBlock(), CHISELED_ANDESITE_BRICKS.asBlock(),
				ANDESITE_TILES.asBlock(), ANDESITE_TILE_STAIRS.asBlock(), ANDESITE_TILE_SLAB.asBlock(), ANDESITE_TILE_WALL.asBlock(),
				CUT_POLISHED_ANDESITE.asBlock(), CUT_POLISHED_ANDESITE_STAIRS.asBlock(), CUT_POLISHED_ANDESITE_SLAB.asBlock(), CUT_POLISHED_ANDESITE_WALL.asBlock());
		Register("andesite_sandy", List.of(EN_US.Andesite(EN_US.Sandy())),
				/* Mod */ SANDY_POLISHED_ANDESITE.asBlock(), SANDY_POLISHED_ANDESITE_SLAB.asBlock(),
				SANDY_ANDESITE_BRICKS.asBlock(), SANDY_ANDESITE_BRICK_SLAB.asBlock(),
				SANDY_CUT_POLISHED_ANDESITE.asBlock(), SANDY_CUT_POLISHED_ANDESITE_SLAB.asBlock(),
				RED_SANDY_POLISHED_ANDESITE.asBlock(), RED_SANDY_POLISHED_ANDESITE_SLAB.asBlock(),
				RED_SANDY_ANDESITE_BRICKS.asBlock(), RED_SANDY_ANDESITE_BRICK_SLAB.asBlock(),
				RED_SANDY_CUT_POLISHED_ANDESITE.asBlock(), RED_SANDY_CUT_POLISHED_ANDESITE_SLAB.asBlock());
		Register("bamboo_barrel", List.of(EN_US.Barrel(EN_US.Bamboo())), /* Mod */ BAMBOO_BARREL.asBlock(), DRIED_BAMBOO_BARREL.asBlock(), BAMBOO_POWDER_KEG.asBlock(), DRIED_BAMBOO_POWDER_KEG.asBlock());
		Register("bamboo_beehive", List.of(EN_US.Beehive(EN_US.Bamboo())), /* Mod */ BAMBOO_BEEHIVE.asBlock(), DRIED_BAMBOO_BEEHIVE.asBlock());
		Register("bamboo_bookshelf", List.of(EN_US.Bookshelf(EN_US.Bamboo())), /* Mod */ BAMBOO_BOOKSHELF.asBlock(), DRIED_BAMBOO_BOOKSHELF.asBlock());
		Register("bamboo_crafting_table", List.of(EN_US.Table(EN_US.Crafting(EN_US.Bamboo()))), /* Mod */ BAMBOO_CRAFTING_TABLE.asBlock(), DRIED_BAMBOO_CRAFTING_TABLE.asBlock());
		Register("bamboo_door", List.of(EN_US.Door(EN_US.Bamboo())), /* Mod */ BAMBOO_DOOR.asBlock(), DRIED_BAMBOO_DOOR.asBlock());
		Register("bamboo_fence", List.of(EN_US.Fence(EN_US.Bamboo())), /* Mod */ BAMBOO_FENCE.asBlock(), DRIED_BAMBOO_FENCE.asBlock());
		Register("bamboo_fence_gate", List.of(EN_US.Gate(EN_US.Fence(EN_US.Bamboo()))), /* Mod */ BAMBOO_FENCE_GATE.asBlock(), DRIED_BAMBOO_FENCE_GATE.asBlock());
		Register("bamboo_ladder", List.of(EN_US.Ladder(EN_US.Bamboo())), /* Mod */ BAMBOO_LADDER.asBlock(), DRIED_BAMBOO_LADDER.asBlock());
		Register("bamboo_lectern", List.of(EN_US.Lectern(EN_US.Bamboo())), /* Mod */ BAMBOO_LECTERN.asBlock(), DRIED_BAMBOO_LECTERN.asBlock());
		Register("bamboo_trapdoor", List.of(EN_US.Trapdoor(EN_US.Bamboo())), /* Mod */ BAMBOO_TRAPDOOR.asBlock(), DRIED_BAMBOO_TRAPDOOR.asBlock());
		Register("bamboo_woodcutter", List.of(EN_US.Woodcutter(EN_US.Bamboo())), /* Mod */ BAMBOO_WOODCUTTER.asBlock(), DRIED_BAMBOO_WOODCUTTER.asBlock());
		Register("banner", List.of(EN_US.Banner()), Blocks.BLACK_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.CYAN_BANNER,
				Blocks.GRAY_BANNER, Blocks.GREEN_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.LIGHT_GRAY_BANNER,
				Blocks.LIME_BANNER, Blocks.MAGENTA_BANNER, Blocks.ORANGE_BANNER, Blocks.PINK_BANNER,
				Blocks.PURPLE_BANNER, Blocks.RED_BANNER, Blocks.WHITE_BANNER, Blocks.YELLOW_BANNER,
				Blocks.BLACK_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.CYAN_WALL_BANNER,
				Blocks.GRAY_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER,
				Blocks.LIME_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.PINK_WALL_BANNER,
				Blocks.PURPLE_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER);
		Register(BARK_BLOCK, Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE);
		Register("barrel", List.of(EN_US.Barrel()), Blocks.BARREL, /* Mod */ ACACIA_BARREL.asBlock(),
				BIRCH_BARREL.asBlock(), DARK_OAK_BARREL.asBlock(), JUNGLE_BARREL.asBlock(), OAK_BARREL.asBlock(),
				CRIMSON_BARREL.asBlock(), WARPED_BARREL.asBlock(), MANGROVE_BARREL.asBlock(), CHARRED_BARREL.asBlock(),
				//Powder Kegs
				ACACIA_POWDER_KEG.asBlock(), BIRCH_POWDER_KEG.asBlock(), DARK_OAK_POWDER_KEG.asBlock(),
				JUNGLE_POWDER_KEG.asBlock(), OAK_POWDER_KEG.asBlock(), SPRUCE_POWDER_KEG.asBlock(),
				CRIMSON_POWDER_KEG.asBlock(), WARPED_POWDER_KEG.asBlock(), MANGROVE_POWDER_KEG.asBlock(),
				CHARRED_POWDER_KEG.asBlock());
		Register("beacon", List.of(EN_US.Beacon()), Blocks.BEACON);
		Register("bed", List.of(EN_US.Bed()), Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED,
				Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED,
				Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED,
				Blocks.PURPLE_BED, Blocks.RED_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED);//,
//				/* Mod */ RAINBOW_BED.asBlock());
		Register("bedrock", List.of(EN_US.Bedrock()), Blocks.BEDROCK);
		Register("bee_nest", List.of(EN_US.Nest(EN_US.Bee())), Blocks.BEE_NEST);
		Register("beehive", List.of(EN_US.Beehive()), Blocks.BEEHIVE, /* Mod */ ACACIA_BEEHIVE.asBlock(), BIRCH_BEEHIVE.asBlock(),
				DARK_OAK_BEEHIVE.asBlock(), JUNGLE_BEEHIVE.asBlock(), MANGROVE_BEEHIVE.asBlock(), CHARRED_BEEHIVE.asBlock());
		Register("bell", List.of(EN_US.Bell()), Blocks.BELL);
		Register("blackstone", List.of(EN_US.Blackstone()), Blocks.BLACKSTONE, Blocks.BLACKSTONE_STAIRS, Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE_WALL,
				Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_WALL,
				Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
				Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BUTTON, Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);//,
//				/* Mod */ SMOOTH_CHISELED_POLISHED_BLACKSTONE.asBlock());
		Register("blackstone_tiles", List.of(EN_US.Tiles(EN_US.Blackstone())), /* Mod */ POLISHED_BLACKSTONE_TILES.asBlock(),
				POLISHED_BLACKSTONE_TILE_STAIRS.asBlock(), POLISHED_BLACKSTONE_TILE_SLAB.asBlock(), POLISHED_BLACKSTONE_TILE_WALL.asBlock());
		Register("blast_furnace", List.of(EN_US.Furnace(EN_US.Blast())), Blocks.SMOKER);
		Register("bone_ladder", List.of(EN_US.Ladder(EN_US.Bone())), /* Mod */ BONE_LADDER.asBlock());
		Register("bookshelf", List.of(EN_US.Bookshelf()), Blocks.BOOKSHELF, /* Mod */ ACACIA_BOOKSHELF.asBlock(), BIRCH_BOOKSHELF.asBlock(),
				DARK_OAK_BOOKSHELF.asBlock(), JUNGLE_BOOKSHELF.asBlock(), SPRUCE_BOOKSHELF.asBlock(),
				CHERRY_BOOKSHELF.asBlock(), MANGROVE_BOOKSHELF.asBlock(), CHARRED_BOOKSHELF.asBlock());
		Register("brewing_stand", List.of(EN_US.Stand(EN_US.Brewing())), Blocks.BREWING_STAND);
		Register("bricks", List.of(EN_US.Bricks()), Blocks.BRICKS);
		Register("cactus", List.of(EN_US.Cactus()), Blocks.CACTUS);
		BlockSounds CAKE = new BlockSounds("cake", List.of(EN_US.Cake()));
		Register(CAKE, Arrays.stream(DyeColor.values()).map(color -> CandleCakeBlock.getCandleCakeFromCandle(ColorUtil.GetCandleBlock(color)).getBlock()).toArray(Block[]::new));
		Register(CAKE, Blocks.CAKE, Blocks.CANDLE_CAKE,
				Blocks.BLACK_CANDLE_CAKE, Blocks.BLUE_CANDLE_CAKE, Blocks.BROWN_CANDLE_CAKE, Blocks.CYAN_CANDLE_CAKE,
				Blocks.GRAY_CANDLE_CAKE, Blocks.GREEN_CANDLE_CAKE, Blocks.LIGHT_BLUE_CANDLE_CAKE, Blocks.LIGHT_GRAY_CANDLE_CAKE,
				Blocks.LIME_CANDLE_CAKE, Blocks.MAGENTA_CANDLE_CAKE, Blocks.ORANGE_CANDLE_CAKE, Blocks.PINK_CANDLE_CAKE,
				Blocks.PURPLE_CANDLE_CAKE, Blocks.RED_CANDLE_CAKE, Blocks.WHITE_CANDLE_CAKE, Blocks.YELLOW_CANDLE_CAKE,
				/* Mod */ SOUL_CANDLE_CAKE, ENDER_CANDLE_CAKE, NETHERRACK_CANDLE_CAKE,
				BEIGE_CANDLE_CAKE, BURGUNDY_CANDLE_CAKE, LAVENDER_CANDLE_CAKE, MINT_CANDLE_CAKE);
		Register(CAKE, CARROT_CAKE, CHOCOLATE_CAKE, CHORUS_CAKE, COFFEE_CAKE, CONFETTI_CAKE, STRAWBERRY_CAKE, VANILLA_CAKE);
		Register("caramel", List.of(EN_US.Caramel()), /* Mod */ CARAMEL_BLOCK.asBlock());
		Register("carpet", List.of(EN_US.Carpet()), Blocks.BLACK_CARPET, Blocks.BLUE_CARPET, Blocks.BROWN_CARPET, Blocks.CYAN_CARPET,
				Blocks.GRAY_CARPET, Blocks.GREEN_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_GRAY_CARPET,
				Blocks.LIME_CARPET, Blocks.MAGENTA_CARPET, Blocks.ORANGE_CARPET, Blocks.PINK_CARPET,
				Blocks.PURPLE_CARPET, Blocks.RED_CARPET, Blocks.WHITE_CARPET, Blocks.YELLOW_CARPET,
				/* Mod */ RAINBOW_CARPET.asBlock(),
				BEIGE_CARPET.asBlock(), BURGUNDY_CARPET.asBlock(), LAVENDER_CARPET.asBlock(), MINT_CARPET.asBlock());
		Register("cartography_table", List.of(EN_US.Table(EN_US.Cartography())), Blocks.CARTOGRAPHY_TABLE);
		Register("cauldron", List.of(EN_US.Cauldron()), Blocks.CAULDRON, Blocks.WATER_CAULDRON, Blocks.LAVA_CAULDRON, Blocks.POWDER_SNOW_CAULDRON);
		Register("cherry_barrel", List.of(EN_US.Barrel(EN_US.Cherry())), /* Mod */ CHERRY_BARREL.asBlock(), CHERRY_POWDER_KEG.asBlock());
		Register("cherry_beehive", List.of(EN_US.Beehive(EN_US.Cherry())), /* Mod */ CHERRY_BEEHIVE.asBlock());
		Register("cherry_bookshelf", List.of(EN_US.Bookshelf(EN_US.Cherry())), /* Mod */ CHERRY_BOOKSHELF.asBlock());
		Register("cherry_crafting_table", List.of(EN_US.Table(EN_US.Crafting(EN_US.Cherry()))), /* Mod */ CHERRY_CRAFTING_TABLE.asBlock());
		Register("cherry_door", List.of(EN_US.Door(EN_US.Cherry())), /* Mod */ CHERRY_DOOR.asBlock());
		Register("cherry_fence", List.of(EN_US.Fence(EN_US.Cherry())), /* Mod */ CHERRY_FENCE.asBlock());
		Register("cherry_fence_gate", List.of(EN_US.Gate(EN_US.Fence(EN_US.Cherry()))), /* Mod */ CHERRY_FENCE_GATE.asBlock());
		Register("cherry_ladder", List.of(EN_US.Ladder(EN_US.Cherry())), /* Mod */ CHERRY_LADDER.asBlock());
		Register("cherry_lectern", List.of(EN_US.Lectern(EN_US.Cherry())), /* Mod */ CHERRY_LECTERN.asBlock());
		Register("cherry_trapdoor", List.of(EN_US.Trapdoor(EN_US.Cherry())), /* Mod */ CHERRY_TRAPDOOR.asBlock());
		Register("cherry_wood_sandy", List.of(EN_US.Wood(EN_US.Cherry(EN_US.Sandy()))), /* Mod */ SANDY_CHERRY_PLANKS.asBlock(), SANDY_CHERRY_SLAB.asBlock(), RED_SANDY_CHERRY_PLANKS.asBlock(), RED_SANDY_CHERRY_SLAB.asBlock());
		Register("cherry_woodcutter", List.of(EN_US.Woodcutter(EN_US.Cherry())), /* Mod */ CHERRY_WOODCUTTER.asBlock());
		Register("chest", List.of(EN_US.Chest()), Blocks.CHEST, Blocks.TRAPPED_CHEST);
		Register("chorus_plant", List.of(EN_US.Plant(EN_US.Chorus())), Blocks.CHORUS_PLANT, Blocks.CHORUS_FLOWER);
		Register("clay", List.of(EN_US.Clay()), Blocks.CLAY);
		Register("coal", List.of(EN_US.Coal()), Blocks.COAL_BLOCK, /* Mod */ COAL_STAIRS.asBlock(), COAL_SLAB.asBlock(), CHARCOAL_BLOCK.asBlock(), CHARCOAL_STAIRS.asBlock(), CHARCOAL_SLAB.asBlock());
		Register("coarse_dirt", List.of(EN_US.Dirt(EN_US.Coarse())), Blocks.COARSE_DIRT, /* Mod */ COARSE_DIRT_SLAB.asBlock());
		Register("cobbled_end_shale", List.of(EN_US.Shale(EN_US.End(EN_US.Cobbled()))), /* Mod */ COBBLED_END_SHALE.asBlock(), COBBLED_END_SHALE_STAIRS.asBlock(), COBBLED_END_SHALE_SLAB.asBlock(), COBBLED_END_SHALE_WALL.asBlock());
		Register("cobbled_deepslate", List.of(EN_US.Deepslate(EN_US.Cobbled())), Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE_WALL);
		Register("cobbled_sculk_stone", List.of(EN_US.Stone(EN_US.Sculk())), /* Mod */ COBBLED_SCULK_STONE.asBlock(), COBBLED_SCULK_STONE_STAIRS.asBlock(), COBBLED_SCULK_STONE_SLAB.asBlock(), COBBLED_SCULK_STONE_WALL.asBlock());
		Register("cobbled_shale", List.of(EN_US.Shale(EN_US.Cobbled())), /* Mod */ COBBLED_SHALE.asBlock(), COBBLED_SHALE_STAIRS.asBlock(), COBBLED_SHALE_SLAB.asBlock(), COBBLED_SHALE_WALL.asBlock());
		Register("cobblestone", List.of(EN_US.Cobblestone()), Blocks.COBBLESTONE, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_WALL,
				Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_WALL);
		Register("cobblestone_sandy", List.of(EN_US.Cobblestone(EN_US.Sandy())), /* Mod */ SANDY_COBBLESTONE.asBlock(), SANDY_COBBLESTONE_SLAB.asBlock(), RED_SANDY_COBBLESTONE.asBlock(), RED_SANDY_COBBLESTONE_SLAB.asBlock());
		Register("cobweb", List.of(EN_US.Cobweb()), Blocks.COBWEB);
		Register("cocoa", List.of(EN_US.Cocoa()), Blocks.COCOA, /* Mod */ COCOA_BLOCK.asBlock());
		Register("composter", List.of(EN_US.Composter()), Blocks.COMPOSTER);
		BlockSounds concrete = new BlockSounds("concrete", List.of(EN_US.Concrete()));
		Register(concrete, Blocks.BLACK_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.CYAN_CONCRETE,
				Blocks.GRAY_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE,
				Blocks.LIME_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.PINK_CONCRETE,
				Blocks.PURPLE_CONCRETE, Blocks.RED_CONCRETE, Blocks.WHITE_CONCRETE, Blocks.YELLOW_CONCRETE);
		Register(concrete, CONCRETE_STAIRS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(concrete, CONCRETE_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(concrete, CONCRETE_WALLS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(concrete, BEIGE_CONCRETE.asBlock(), BEIGE_CONCRETE_STAIRS.asBlock(), BEIGE_CONCRETE_SLAB.asBlock(), BEIGE_CONCRETE_WALL.asBlock(),
				LAVENDER_CONCRETE.asBlock(), LAVENDER_CONCRETE_STAIRS.asBlock(), LAVENDER_CONCRETE_SLAB.asBlock(), LAVENDER_CONCRETE_WALL.asBlock(),
				BURGUNDY_CONCRETE.asBlock(), BURGUNDY_CONCRETE_STAIRS.asBlock(), BURGUNDY_CONCRETE_SLAB.asBlock(), BURGUNDY_CONCRETE_WALL.asBlock(),
				MINT_CONCRETE.asBlock(), MINT_CONCRETE_STAIRS.asBlock(), MINT_CONCRETE_SLAB.asBlock(), MINT_CONCRETE_WALL.asBlock());
		Register("concrete_powder", List.of(EN_US.Powder(EN_US.Concrete())), Blocks.BLACK_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
				Blocks.GRAY_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER,
				Blocks.LIME_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER,
				Blocks.PURPLE_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.WHITE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER,
				/* Mod */ BEIGE_CONCRETE_POWDER.asBlock(), BURGUNDY_CONCRETE_POWDER.asBlock(), LAVENDER_CONCRETE_POWDER.asBlock(), MINT_CONCRETE_POWDER.asBlock());
		Register("conduit", List.of(EN_US.Conduit()), Blocks.CONDUIT);
		Register("cooled_magma", List.of(EN_US.Magma()), /* Mod */ COOLED_MAGMA_BLOCK.asBlock());
		Register("copper_bars", List.of(EN_US.Bars(EN_US.Copper())), /* Mod */
				COPPER_BARS.asBlock(), EXPOSED_COPPER_BARS.asBlock(), WEATHERED_COPPER_BARS.asBlock(), EXPOSED_COPPER_BARS.asBlock(),
				WAXED_COPPER_BARS.asBlock(), WAXED_EXPOSED_COPPER_BARS.asBlock(), WAXED_WEATHERED_COPPER_BARS.asBlock(), WAXED_OXIDIZED_COPPER_BARS.asBlock());
		Register("copper_chain", List.of(EN_US.Chain(EN_US.Copper())), /* Mod */
				COPPER_CHAIN.asBlock(), EXPOSED_COPPER_CHAIN.asBlock(), WEATHERED_COPPER_CHAIN.asBlock(), EXPOSED_COPPER_CHAIN.asBlock(),
				WAXED_COPPER_CHAIN.asBlock(), WAXED_EXPOSED_COPPER_CHAIN.asBlock(), WAXED_WEATHERED_COPPER_CHAIN.asBlock(), WAXED_OXIDIZED_COPPER_CHAIN.asBlock());
		Register("copper_lantern", List.of(EN_US.Lantern(EN_US.Copper())), /* Mod */
				COPPER_LANTERN.asBlock(), EXPOSED_COPPER_LANTERN.asBlock(), WEATHERED_COPPER_LANTERN.asBlock(), EXPOSED_COPPER_LANTERN.asBlock(),
				WAXED_COPPER_LANTERN.asBlock(), WAXED_EXPOSED_COPPER_LANTERN.asBlock(), WAXED_WEATHERED_COPPER_LANTERN.asBlock(), WAXED_OXIDIZED_COPPER_LANTERN.asBlock(),
				COPPER_SOUL_LANTERN.asBlock(), EXPOSED_COPPER_SOUL_LANTERN.asBlock(), WEATHERED_COPPER_SOUL_LANTERN.asBlock(), EXPOSED_COPPER_SOUL_LANTERN.asBlock(),
				WAXED_COPPER_SOUL_LANTERN.asBlock(), WAXED_EXPOSED_COPPER_SOUL_LANTERN.asBlock(), WAXED_WEATHERED_COPPER_SOUL_LANTERN.asBlock(), WAXED_OXIDIZED_COPPER_SOUL_LANTERN.asBlock(),
				COPPER_ENDER_LANTERN.asBlock(), EXPOSED_COPPER_ENDER_LANTERN.asBlock(), WEATHERED_COPPER_ENDER_LANTERN.asBlock(), EXPOSED_COPPER_ENDER_LANTERN.asBlock(),
				WAXED_COPPER_ENDER_LANTERN.asBlock(), WAXED_EXPOSED_COPPER_ENDER_LANTERN.asBlock(), WAXED_WEATHERED_COPPER_ENDER_LANTERN.asBlock(), WAXED_OXIDIZED_COPPER_ENDER_LANTERN.asBlock());
		Register("copper_trapdoor", List.of(EN_US.Trapdoor(EN_US.Copper())), /* Mod */
				COPPER_TRAPDOOR.asBlock(), EXPOSED_COPPER_TRAPDOOR.asBlock(), WEATHERED_COPPER_TRAPDOOR.asBlock(), EXPOSED_COPPER_TRAPDOOR.asBlock(),
				WAXED_COPPER_TRAPDOOR.asBlock(), WAXED_EXPOSED_COPPER_TRAPDOOR.asBlock(), WAXED_WEATHERED_COPPER_TRAPDOOR.asBlock(), WAXED_OXIDIZED_COPPER_TRAPDOOR.asBlock());
		Register("coral_fan", List.of(EN_US.Fan(EN_US.Coral())), Blocks.BRAIN_CORAL, Blocks.BUBBLE_CORAL, Blocks.FIRE_CORAL, Blocks.HORN_CORAL, Blocks.TUBE_CORAL,
				Blocks.BRAIN_CORAL_FAN, Blocks.BUBBLE_CORAL_FAN, Blocks.FIRE_CORAL_FAN, Blocks.HORN_CORAL_FAN, Blocks.TUBE_CORAL_FAN,
				Blocks.BRAIN_CORAL_WALL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.FIRE_CORAL_WALL_FAN, Blocks.HORN_CORAL_WALL_FAN, Blocks.TUBE_CORAL_WALL_FAN);
		Register("crafting_table", List.of(EN_US.Table(EN_US.Crafting())), Blocks.CRAFTING_TABLE, /* Mod */ ACACIA_CRAFTING_TABLE.asBlock(), BIRCH_CRAFTING_TABLE.asBlock(),
				DARK_OAK_CRAFTING_TABLE.asBlock(), JUNGLE_CRAFTING_TABLE.asBlock(), SPRUCE_CRAFTING_TABLE.asBlock(), MANGROVE_CRAFTING_TABLE.asBlock(), CHARRED_CRAFTING_TABLE.asBlock());
		Register(BlockSounds.BreakPlace("crop", SoundEvents.BLOCK_CROP_BREAK, SoundEvents.ITEM_CROP_PLANT, List.of(EN_US.Crop())),
				Blocks.BEETROOTS, Blocks.CARROTS, Blocks.POTATOES, Blocks.WHEAT,
				/* Farmer's Delight */ BlocksRegistry.CABBAGE_CROP.get(), BlocksRegistry.ONION_CROP.get(), BlocksRegistry.TOMATO_CROP.get());
		Register("stonecutter", List.of(EN_US.Stonecutter()), Blocks.STONECUTTER);
		Register("daylight_detector", List.of(EN_US.Detector(EN_US.Daylight())), Blocks.DAYLIGHT_DETECTOR);
		Register("dead_coral", List.of(EN_US.Coral(EN_US.Dead())), Blocks.DEAD_BRAIN_CORAL, Blocks.DEAD_BUBBLE_CORAL, Blocks.DEAD_FIRE_CORAL, Blocks.DEAD_HORN_CORAL, Blocks.DEAD_TUBE_CORAL,
				Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.DEAD_FIRE_CORAL_FAN, Blocks.DEAD_HORN_CORAL_FAN, Blocks.DEAD_TUBE_CORAL_FAN,
				Blocks.DEAD_BRAIN_CORAL_WALL_FAN, Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, Blocks.DEAD_FIRE_CORAL_WALL_FAN, Blocks.DEAD_HORN_CORAL_WALL_FAN, Blocks.DEAD_TUBE_CORAL_WALL_FAN,
				Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.DEAD_TUBE_CORAL_BLOCK);
		Register("deepslate_coal_ore", List.of(EN_US.Ore(EN_US.Coal(EN_US.Deepslate()))), Blocks.DEEPSLATE_COAL_ORE);
		Register("deepslate_copper_ore", List.of(EN_US.Ore(EN_US.Copper(EN_US.Deepslate()))), Blocks.DEEPSLATE_COPPER_ORE);
		Register("deepslate_diamond_ore", List.of(EN_US.Ore(EN_US.Diamond(EN_US.Deepslate()))), Blocks.DEEPSLATE_DIAMOND_ORE);
		Register("deepslate_emerald_ore", List.of(EN_US.Ore(EN_US.Emerald(EN_US.Deepslate()))), Blocks.DEEPSLATE_EMERALD_ORE);
		Register("deepslate_gold_ore", List.of(EN_US.Ore(EN_US.Gold(EN_US.Deepslate()))), Blocks.DEEPSLATE_GOLD_ORE);
		Register("deepslate_iron_ore", List.of(EN_US.Ore(EN_US.Iron(EN_US.Deepslate()))), Blocks.DEEPSLATE_IRON_ORE);
		Register("deepslate_lapis_ore", List.of(EN_US.Ore(EN_US.Lapis(EN_US.Deepslate()))), Blocks.DEEPSLATE_LAPIS_ORE);
		Register("deepslate_redstone_ore", List.of(EN_US.Ore(EN_US.Redstone(EN_US.Deepslate()))), Blocks.DEEPSLATE_REDSTONE_ORE);
		Register("deepslate_ruby_ore", List.of(EN_US.Ore(EN_US.Ruby(EN_US.Deepslate()))), DEEPSLATE_RUBY_ORE.asBlock());
		Register("deepslate_sapphire_ore", List.of(EN_US.Ore(EN_US.Sapphire(EN_US.Deepslate()))), DEEPSLATE_SAPPHIRE_ORE.asBlock());
		Register("diamond", List.of(EN_US.Diamond()), Blocks.DIAMOND_BLOCK,
				/* Mod */ DIAMOND_STAIRS.asBlock(), DIAMOND_SLAB.asBlock(), DIAMOND_WALL.asBlock(),
				DIAMOND_BRICKS.asBlock(), DIAMOND_BRICK_STAIRS.asBlock(), DIAMOND_BRICK_SLAB.asBlock(), DIAMOND_BRICK_WALL.asBlock());
		Register("diorite", List.of(EN_US.Diorite()), Blocks.DIORITE, Blocks.DIORITE_STAIRS, Blocks.DIORITE_SLAB, Blocks.DIORITE_WALL,
				Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_STAIRS, Blocks.POLISHED_DIORITE_SLAB, /* Mod */ POLISHED_DIORITE_WALL.asBlock(),
				DIORITE_BRICKS.asBlock(), DIORITE_BRICK_STAIRS.asBlock(), DIORITE_BRICK_SLAB.asBlock(), DIORITE_BRICK_WALL.asBlock(), CHISELED_DIORITE_BRICKS.asBlock(),
				DIORITE_TILES.asBlock(), DIORITE_TILE_STAIRS.asBlock(), DIORITE_TILE_SLAB.asBlock(), DIORITE_TILE_WALL.asBlock(),
				CUT_POLISHED_DIORITE.asBlock(), CUT_POLISHED_DIORITE_STAIRS.asBlock(), CUT_POLISHED_DIORITE_SLAB.asBlock(), CUT_POLISHED_DIORITE_WALL.asBlock());
		Register("diorite_sandy", List.of(EN_US.Diorite(EN_US.Sandy())),
				/* Mod */ SANDY_POLISHED_DIORITE.asBlock(), SANDY_POLISHED_DIORITE_SLAB.asBlock(),
				SANDY_DIORITE_BRICKS.asBlock(), SANDY_DIORITE_BRICK_SLAB.asBlock(),
				SANDY_CUT_POLISHED_DIORITE.asBlock(), SANDY_CUT_POLISHED_DIORITE_SLAB.asBlock(),
				RED_SANDY_POLISHED_DIORITE.asBlock(), RED_SANDY_POLISHED_DIORITE_SLAB.asBlock(),
				RED_SANDY_DIORITE_BRICKS.asBlock(), RED_SANDY_DIORITE_BRICK_SLAB.asBlock(),
				RED_SANDY_CUT_POLISHED_DIORITE.asBlock(), RED_SANDY_CUT_POLISHED_DIORITE_SLAB.asBlock());
		Register("dirt", List.of(EN_US.Dirt()), Blocks.DIRT);
		Register("dirt_path", List.of(EN_US.Path(EN_US.Dirt())), Blocks.DIRT_PATH);
		Register("dispenser", List.of(EN_US.Dispenser()), Blocks.DISPENSER);
		Register("dragon_egg", List.of(EN_US.Egg(EN_US.Dragon())), Blocks.DRAGON_EGG);
		Register("dragon_head", List.of(EN_US.Head(EN_US.Dragon())), Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD);
		Register("dried_kelp", List.of(EN_US.Kelp(EN_US.Dried())), Blocks.DRIED_KELP_BLOCK);
		BlockSounds dye_block = new BlockSounds("dye_block", List.of(EN_US.Block(EN_US.Dye())));
		Register(dye_block, DYE_BLOCKS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(dye_block, BEIGE_DYE_BLOCK.asBlock(), BURGUNDY_DYE_BLOCK.asBlock(), LAVENDER_DYE_BLOCK.asBlock(), MINT_DYE_BLOCK.asBlock());
		Register("dropper", List.of(EN_US.Dropper()), Blocks.DROPPER);
		Register("emerald", List.of(EN_US.Emerald()), Blocks.EMERALD_BLOCK,
				/* Mod */ EMERALD_STAIRS.asBlock(), EMERALD_SLAB.asBlock(), EMERALD_WALL.asBlock(),
				CUT_EMERALD.asBlock(), CUT_EMERALD_STAIRS.asBlock(), CUT_EMERALD_SLAB.asBlock(), CUT_EMERALD_WALL.asBlock(),
				EMERALD_BRICKS.asBlock(), EMERALD_BRICK_STAIRS.asBlock(), EMERALD_BRICK_SLAB.asBlock(), EMERALD_BRICK_WALL.asBlock());
		Register("enchanting_table", List.of(EN_US.Table(EN_US.Enchanting())), Blocks.ENCHANTING_TABLE);
		Register("end_portal_frame", List.of(EN_US.Frame(EN_US.Portal(EN_US.End()))), Blocks.END_PORTAL_FRAME, /* Mod */ PURPLE_EYE_END_PORTAL_FRAME);
		Register("end_rock", List.of(EN_US.Rock(EN_US.End())), /* Mod */ END_ROCK.asBlock(), END_ROCK_SHALE.asBlock());
		Register("end_rod", List.of(EN_US.Rod(EN_US.End())), Blocks.END_ROD);
		Register("end_shale", List.of(EN_US.Shale(EN_US.End())), /* Mod */ END_SHALE.asBlock(), END_SHALE_ROCK.asBlock(),
				END_SHALE_BRICKS.asBlock(), END_SHALE_BRICK_STAIRS.asBlock(), END_SHALE_BRICK_SLAB.asBlock(), END_SHALE_BRICK_WALL.asBlock(),
				END_SHALE_TILES.asBlock(), END_SHALE_TILE_STAIRS.asBlock(), END_SHALE_TILE_SLAB.asBlock(), END_SHALE_TILE_WALL.asBlock());
		Register("end_stone", List.of(EN_US.Stone(EN_US.End())), Blocks.END_STONE, Blocks.END_STONE_BRICKS,
				Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL,
				/* Mod */ END_STONE_SLAB.asBlock(), END_STONE_PILLAR.asBlock(),
				END_STONE_TILES.asBlock(), END_STONE_TILE_STAIRS.asBlock(), END_STONE_TILE_SLAB.asBlock(), END_STONE_TILE_WALL.asBlock());
		Register("end_stone_iron_ore", List.of(EN_US.Ore(EN_US.Iron(EN_US.Stone(EN_US.End())))), /* Mod */ END_IRON_ORE.asBlock());
		Register("end_stone_copper_ore", List.of(EN_US.Ore(EN_US.Copper(EN_US.Stone(EN_US.End())))), /* Mod */ END_COPPER_ORE.asBlock());
		Register("end_stone_gold_ore", List.of(EN_US.Ore(EN_US.Gold(EN_US.Stone(EN_US.End())))), /* Mod */ END_GOLD_ORE.asBlock());
		Register("ender_chest", List.of(EN_US.Chest(EN_US.Ender())), Blocks.ENDER_CHEST);
		Register("farmland", List.of(EN_US.Farmland()), Blocks.FARMLAND);
		Register("fire", List.of(EN_US.Fire()), Blocks.FIRE, Blocks.SOUL_FIRE);
		BlockSounds fleece = new BlockSounds("fleece", List.of(EN_US.Fleece()));
		Register(fleece, /* Mod */ RAINBOW_FLEECE_SLAB.asBlock(), RAINBOW_FLEECE.asBlock());
		Register(fleece, /* Mod */ FLEECE.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(fleece, /* Mod */ FLEECE_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(fleece, /* Mod */ BEIGE_FLEECE.asBlock(), BEIGE_FLEECE_SLAB.asBlock(),
				BURGUNDY_FLEECE.asBlock(), BURGUNDY_FLEECE_SLAB.asBlock(),
				LAVENDER_FLEECE.asBlock(), LAVENDER_FLEECE_SLAB.asBlock(),
				MINT_FLEECE.asBlock(), MINT_FLEECE_SLAB.asBlock());
		BlockSounds fleece_carpet = new BlockSounds("fleece_carpet", List.of(EN_US.Carpet(EN_US.Fleece())));
		Register(fleece_carpet, /* Mod */ RAINBOW_FLEECE_CARPET.asBlock());
		Register(fleece_carpet, /* Mod */ FLEECE_CARPETS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(fleece_carpet, /* Mod */ BEIGE_FLEECE_CARPET.asBlock(), BURGUNDY_FLEECE_CARPET.asBlock(), LAVENDER_FLEECE_CARPET.asBlock(), MINT_FLEECE_CARPET.asBlock());
		Register("fletching_table", List.of(EN_US.Table(EN_US.Fletching())), Blocks.FLETCHING_TABLE);
		Register("flint", List.of(EN_US.Flint()), /* Mod */ FLINT_BLOCK.asBlock(), FLINT_SLAB.asBlock(),
				FLINT_BRICKS.asBlock(), FLINT_BRICK_STAIRS.asBlock(), FLINT_BRICK_SLAB.asBlock(), FLINT_BRICK_WALL.asBlock());
		Register("flower", List.of(EN_US.Flower()), Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.CORNFLOWER, Blocks.DANDELION,
				Blocks.LILY_OF_THE_VALLEY, Blocks.ORANGE_TULIP, Blocks.OXEYE_DAISY, Blocks.PINK_TULIP, Blocks.POPPY,
				Blocks.RED_TULIP, Blocks.SUNFLOWER, Blocks.WHITE_TULIP, Blocks.WITHER_ROSE, Blocks.LILAC, Blocks.ROSE_BUSH, Blocks.PEONY,
				/* Mod */ TORCHFLOWER.asBlock(), PITCHER_PLANT.asBlock(),
				AMARANTH.asBlock(), BLUE_ROSE_BUSH.asBlock(), TALL_ALLIUM.asBlock(), TALL_PINK_ALLIUM.asBlock(),
				BUTTERCUP.asBlock(), PINK_DAISY.asBlock(), ROSE.asBlock(), BLUE_ROSE.asBlock(), MAGENTA_TULIP.asBlock(),
				MARIGOLD.asBlock(), INDIGO_ORCHID.asBlock(), MAGENTA_ORCHID.asBlock(), ORANGE_ORCHID.asBlock(),
				PURPLE_ORCHID.asBlock(), RED_ORCHID.asBlock(), WHITE_ORCHID.asBlock(), YELLOW_ORCHID.asBlock(),
				PINK_ALLIUM.asBlock(), LAVENDER.asBlock(), HYDRANGEA.asBlock(), PAEONIA.asBlock(), ASTER.asBlock(),
				VANILLA_FLOWER.asBlock(), TALL_VANILLA.asBlock());
		Register("furnace", List.of(EN_US.Furnace()), Blocks.FURNACE);
		Register("glass_pane", List.of(EN_US.Pane(EN_US.Glass())), Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE,
				Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
				Blocks.LIME_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
				Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE,
				Blocks.GLASS_PANE, /* Mod */ TINTED_GLASS_PANE.asBlock(), RUBY_GLASS_PANE.asBlock(),
				BEIGE_STAINED_GLASS_PANE.asBlock(), BURGUNDY_STAINED_GLASS_PANE.asBlock(), LAVENDER_STAINED_GLASS_PANE.asBlock(), MINT_STAINED_GLASS_PANE.asBlock());
		BlockSounds glazed_terracotta = new BlockSounds("glazed_terracotta", List.of(EN_US.Terracotta(EN_US.Glazed())));
		Register(glazed_terracotta, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA,
				Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
				Blocks.LIME_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA,
				Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA,
				/* Mod */ GLAZED_TERRACOTTA.asBlock(), GLAZED_TERRACOTTA_SLAB.asBlock());
		Register(glazed_terracotta, GLAZED_TERRACOTTA_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(glazed_terracotta, BEIGE_GLAZED_TERRACOTTA.asBlock(), BEIGE_GLAZED_TERRACOTTA_SLAB.asBlock(),
				BURGUNDY_GLAZED_TERRACOTTA.asBlock(), BURGUNDY_GLAZED_TERRACOTTA_SLAB.asBlock(),
				LAVENDER_GLAZED_TERRACOTTA.asBlock(), LAVENDER_GLAZED_TERRACOTTA_SLAB.asBlock(),
				MINT_GLAZED_TERRACOTTA.asBlock(), MINT_GLAZED_TERRACOTTA_SLAB.asBlock());
		Register("glow_lichen", List.of(EN_US.Lichen(EN_US.Glow())), Blocks.GLOW_LICHEN, /* Mod */ GLOW_LICHEN_BLOCK.asBlock(), GLOW_LICHEN_SLAB.asBlock());
		Register("glow_lichen_bed", List.of(EN_US.Bed(EN_US.Lichen(EN_US.Glow()))), /* Mod */ GLOW_LICHEN_BED.asBlock());
		Register("glow_lichen_carpet", List.of(EN_US.Carpet(EN_US.Lichen(EN_US.Glow()))), /* Mod */ GLOW_LICHEN_CARPET.asBlock());
		Register("glowstone", List.of(EN_US.Glowstone()), Blocks.GLOWSTONE);
		Register("gold", List.of(EN_US.Gold()), Blocks.GOLD_BLOCK, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
				/* Mod */ GOLD_TORCH.asBlock(), GOLD_SOUL_TORCH.asBlock(), GOLD_ENDER_TORCH.asBlock(), UNDERWATER_GOLD_TORCH.asBlock(),
				GOLD_LANTERN.asBlock(), GOLD_SOUL_LANTERN.asBlock(), GOLD_ENDER_LANTERN.asBlock(),
				GOLD_BUTTON.asBlock(), GOLD_STAIRS.asBlock(), GOLD_SLAB.asBlock(), GOLD_WALL.asBlock(),
				GOLD_BRICKS.asBlock(), GOLD_BRICK_STAIRS.asBlock(), GOLD_BRICK_SLAB.asBlock(), GOLD_BRICK_WALL.asBlock(),
				CUT_GOLD.asBlock(), CUT_GOLD_PILLAR.asBlock(), CUT_GOLD_STAIRS.asBlock(), CUT_GOLD_SLAB.asBlock(), CUT_GOLD_WALL.asBlock());
		Register("gold_bars", List.of(EN_US.Bars(EN_US.Gold())), /* Mod */ GOLD_BARS.asBlock());
		Register("gold_chain", List.of(EN_US.Chain(EN_US.Gold())), /* Mod */ GOLD_CHAIN.asBlock());
		Register("gold_door", List.of(EN_US.Door(EN_US.Gold())), /* Mod */ GOLD_DOOR.asBlock());
		Register("gold_lantern", List.of(EN_US.Lantern(EN_US.Gold())), /* Mod */ GOLD_LANTERN.asBlock(), GOLD_SOUL_LANTERN.asBlock(), GOLD_ENDER_LANTERN.asBlock());
		Register("gold_trapdoor", List.of(EN_US.Trapdoor(EN_US.Gold())), /* Mod */ GOLD_TRAPDOOR.asBlock());
		Register("granite", List.of(EN_US.Granite()), Blocks.GRANITE, Blocks.GRANITE_STAIRS, Blocks.GRANITE_SLAB, Blocks.GRANITE_WALL,
				Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_STAIRS, Blocks.POLISHED_GRANITE_SLAB, /* Mod */ POLISHED_GRANITE_WALL.asBlock(), CHISELED_GRANITE.asBlock(),
				GRANITE_BRICKS.asBlock(), GRANITE_BRICK_STAIRS.asBlock(), GRANITE_BRICK_SLAB.asBlock(), GRANITE_BRICK_WALL.asBlock(), CHISELED_GRANITE_BRICKS.asBlock(),
				GRANITE_TILES.asBlock(), GRANITE_TILE_STAIRS.asBlock(), GRANITE_TILE_SLAB.asBlock(), GRANITE_TILE_WALL.asBlock(),
				CUT_POLISHED_GRANITE.asBlock(), CUT_POLISHED_GRANITE_STAIRS.asBlock(), CUT_POLISHED_GRANITE_SLAB.asBlock(), CUT_POLISHED_GRANITE_WALL.asBlock());
		Register("granite_sandy", List.of(EN_US.Granite(EN_US.Sandy())),
				/* Mod */ SANDY_POLISHED_GRANITE.asBlock(), SANDY_POLISHED_GRANITE_SLAB.asBlock(),
				SANDY_GRANITE_BRICKS.asBlock(), SANDY_GRANITE_BRICK_SLAB.asBlock(),
				SANDY_CUT_POLISHED_GRANITE.asBlock(), SANDY_CUT_POLISHED_GRANITE_SLAB.asBlock(),
				RED_SANDY_POLISHED_GRANITE.asBlock(), RED_SANDY_POLISHED_GRANITE_SLAB.asBlock(),
				RED_SANDY_GRANITE_BRICKS.asBlock(), RED_SANDY_GRANITE_BRICK_SLAB.asBlock(),
				RED_SANDY_CUT_POLISHED_GRANITE.asBlock(), RED_SANDY_CUT_POLISHED_GRANITE_SLAB.asBlock());
		Register("grindstone", List.of(EN_US.Grindstone()), Blocks.GRINDSTONE);
		Register("gunpowder", List.of(EN_US.Gunpowder()), /* Mod */ GUNPOWDER_BLOCK.asBlock());
		Register("gunpowder_fuse", List.of(EN_US.Fuse(EN_US.Gunpowder())), /* Mod */ GUNPOWDER_FUSE.asBlock());
		Register("hay", List.of(EN_US.Hay()), Blocks.HAY_BLOCK);
		Register("head", List.of(EN_US.Head()), Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD,
				Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, /* Mob */ PIGLIN_HEAD.asBlock(), PIGLIN_HEAD.getWallBlock());
		Register("heavy_copper_chain", List.of(EN_US.Bars(EN_US.Copper())), /* Mod */
				HEAVY_COPPER_CHAIN.asBlock(), EXPOSED_HEAVY_COPPER_CHAIN.asBlock(), WEATHERED_HEAVY_COPPER_CHAIN.asBlock(), EXPOSED_HEAVY_COPPER_CHAIN.asBlock(),
				WAXED_HEAVY_COPPER_CHAIN.asBlock(), WAXED_EXPOSED_HEAVY_COPPER_CHAIN.asBlock(), WAXED_WEATHERED_HEAVY_COPPER_CHAIN.asBlock(), WAXED_OXIDIZED_HEAVY_COPPER_CHAIN.asBlock());
		Register("heavy_gold_chain", List.of(EN_US.Chain(EN_US.Gold(EN_US.Heavy()))), /* Mod */ HEAVY_GOLD_CHAIN.asBlock());
		Register("heavy_iron_chain", List.of(EN_US.Chain(EN_US.Iron(EN_US.Heavy()))), /* Mod */ HEAVY_IRON_CHAIN.asBlock());
		Register("heavy_netherite_chain", List.of(EN_US.Chain(EN_US.Netherite(EN_US.Heavy()))), /* Mod */ HEAVY_NETHERITE_CHAIN.asBlock());
		Register("honeycomb", List.of(EN_US.Honeycomb()), Blocks.HONEYCOMB_BLOCK);
		Register("hopper", List.of(EN_US.Hopper()), Blocks.HOPPER);
		Register("ice", List.of(EN_US.Ice()), Blocks.ICE, Blocks.BLUE_ICE, Blocks.FROSTED_ICE, Blocks.PACKED_ICE);
		Register("infested_andesite", List.of(EN_US.Andesite(EN_US.Infested())), /* Mod */ INFESTED_ANDESITE.asBlock(), INFESTED_ANDESITE_BRICKS.asBlock(), INFESTED_CHISELED_ANDESITE_BRICKS.asBlock());
		Register("infested_cobblestone", List.of(EN_US.Cobblestone(EN_US.Infested())), Blocks.INFESTED_COBBLESTONE);
		Register("infested_deepslate", List.of(EN_US.Deepslate(EN_US.Infested())), Blocks.INFESTED_DEEPSLATE,
				/* Mod */ INFESTED_COBBLED_DEEPSLATE.asBlock(), INFESTED_MOSSY_COBBLED_DEEPSLATE.asBlock());
		Register("infested_diorite", List.of(EN_US.Diorite(EN_US.Infested())), /* Mod */ INFESTED_DIORITE.asBlock(), INFESTED_DIORITE_BRICKS.asBlock(), INFESTED_CHISELED_DIORITE_BRICKS.asBlock());
		Register("infested_granite", List.of(EN_US.Granite(EN_US.Infested())), /* Mod */ INFESTED_GRANITE.asBlock(), INFESTED_GRANITE_BRICKS.asBlock(), INFESTED_CHISELED_GRANITE_BRICKS.asBlock());
		Register("infested_stone", List.of(EN_US.Stone(EN_US.Infested())), Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS,
				Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS,
				/* Mod */ INFESTED_MOSSY_CHISELED_STONE_BRICKS.asBlock());
		Register("infested_tuff", List.of(EN_US.Tuff(EN_US.Infested())), /* Mod */ INFESTED_TUFF.asBlock());
		Register("iron", List.of(EN_US.Iron()), Blocks.IRON_BLOCK, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
				/* Mod */ IRON_TORCH.asBlock(), IRON_SOUL_TORCH.asBlock(), IRON_ENDER_TORCH.asBlock(), UNDERWATER_IRON_TORCH.asBlock(),
				IRON_LANTERN.asBlock(), IRON_SOUL_LANTERN.asBlock(), IRON_ENDER_LANTERN.asBlock(),
				IRON_BUTTON.asBlock(), IRON_STAIRS.asBlock(), IRON_SLAB.asBlock(), IRON_WALL.asBlock(),
				IRON_BRICKS.asBlock(), IRON_BRICK_STAIRS.asBlock(), IRON_BRICK_SLAB.asBlock(), IRON_BRICK_WALL.asBlock(),
				CUT_IRON.asBlock(), CUT_IRON_PILLAR.asBlock(), CUT_IRON_STAIRS.asBlock(), CUT_IRON_SLAB.asBlock(), CUT_IRON_WALL.asBlock());
		Register("iron_bars", List.of(EN_US.Bars(EN_US.Iron())), Blocks.IRON_BARS);
		Register("iron_chain", List.of(EN_US.Chain(EN_US.Iron())), Blocks.CHAIN, /* Mod */ IRON_CHAIN.asBlock());
		Register("iron_lantern", List.of(EN_US.Lantern(EN_US.Iron())), Blocks.LANTERN, Blocks.SOUL_LANTERN,
				/* Mod */ ENDER_LANTERN.asBlock(), IRON_LANTERN.asBlock(), IRON_SOUL_LANTERN.asBlock(), IRON_ENDER_LANTERN.asBlock());
		Register("iron_door", List.of(EN_US.Door(EN_US.Iron())), Blocks.IRON_DOOR);
		Register("iron_trapdoor", List.of(EN_US.Trapdoor(EN_US.Iron())), Blocks.IRON_TRAPDOOR);
		Register("jukebox", List.of(EN_US.Jukebox()), Blocks.JUKEBOX);
		Register("kelp", List.of(EN_US.Kelp()), Blocks.KELP, Blocks.KELP_PLANT);
		Register("lapis", List.of(EN_US.Lapis()), Blocks.LAPIS_BLOCK);
		Register("leaves", List.of(EN_US.Leaves()), Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES,
				Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES,
				/* Mod */ MANGROVE_LEAVES.asBlock(), HEDGE_BLOCK.asBlock(),
				WHITE_SPRUCE_LEAVES.asBlock(), SWEET_BERRY_LEAVES.asBlock(), GRAPE_LEAVES.asBlock(),
				LIGHT_GREEN_ACACIA_LEAVES.asBlock(), RED_ACACIA_LEAVES.asBlock(), YELLOW_ACACIA_LEAVES.asBlock(),
				LIGHT_GREEN_BIRCH_LEAVES.asBlock(), RED_BIRCH_LEAVES.asBlock(), YELLOW_BIRCH_LEAVES.asBlock(), WHITE_BIRCH_LEAVES.asBlock(),
				BLUE_GREEN_OAK_LEAVES.asBlock(), LIGHT_GREEN_OAK_LEAVES.asBlock(), RED_OAK_LEAVES.asBlock(), YELLOW_OAK_LEAVES.asBlock(), WHITE_OAK_LEAVES.asBlock());
		Register("lectern", List.of(EN_US.Lectern()), Blocks.LECTERN,
				/* Mod */ ACACIA_LECTERN.asBlock(), BIRCH_LECTERN.asBlock(), DARK_OAK_LECTERN.asBlock(),
				JUNGLE_LECTERN.asBlock(), SPRUCE_LECTERN.asBlock(), MANGROVE_LECTERN.asBlock(), CHARRED_LECTERN.asBlock());
		Register(BlockSounds.Place("lily_pad", SoundEvents.BLOCK_LILY_PAD_PLACE, List.of(EN_US.Pad(EN_US.Lily()))), Blocks.LILY_PAD);
		Register("loom", List.of(EN_US.Loom()), Blocks.LOOM);
		Register("magma", List.of(EN_US.Magma()), Blocks.MAGMA_BLOCK);
		Register("melon", List.of(EN_US.Melon()), Blocks.MELON,
				/* Mod */ CARVED_MELON.asBlock(), MELON_LANTERN.asBlock(), SOUL_MELON_LANTERN.asBlock(), ENDER_MELON_LANTERN.asBlock());
//		Register("moss_bed", List.of(EN_US.Bed(EN_US.Moss())), /* Mod */ MOSS_BED.asBlock());
		Register("mushroom", List.of(EN_US.Mushroom()), Blocks.MUSHROOM_STEM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, /* Mod */ BLUE_MUSHROOM_BLOCK.asBlock());
		Register("mushroom_plant", List.of(EN_US.Plant(EN_US.Mushroom())), Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, /* Mod */ BLUE_MUSHROOM.asBlock(), DEATH_CAP_MUSHROOM.asBlock());
		Register("mycelium", List.of(EN_US.Mycelium()), Blocks.MYCELIUM);
		Register("mycelium_roots", List.of(EN_US.Roots(EN_US.Mycelium())), /* Mod */ MYCELIUM_ROOTS.asBlock());
		Register(NETHER_BARK_BLOCK, Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE);
		Register("nether_brick_fence", List.of(EN_US.Fence(EN_US.Brick(EN_US.Nether()))), Blocks.NETHER_BRICK_FENCE);//,
//				/* Mod */ RED_NETHER_BRICK_FENCE.asBlock(), BLUE_NETHER_BRICK_FENCE.asBlock(), YELLOW_NETHER_BRICK_FENCE.asBlock());
		Register(BlockSounds.BreakPlace("nether_wart", SoundEvents.BLOCK_NETHER_WART_BREAK, SoundEvents.ITEM_NETHER_WART_PLANT,
				List.of(EN_US.Wart(EN_US.Nether()))), Blocks.NETHER_WART);//, /* Mod */ WARPED_WART.asBlock(), GILDED_WART.asBlock());
		Register(NETHER_WOOD_BLOCK, Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_STAIRS, Blocks.CRIMSON_SLAB,
				Blocks.CRIMSON_BUTTON, Blocks.CRIMSON_PRESSURE_PLATE, Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN,
				Blocks.WARPED_PLANKS, Blocks.WARPED_STAIRS, Blocks.WARPED_SLAB, Blocks.WARPED_BUTTON,
				Blocks.WARPED_PRESSURE_PLATE, Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN);
		Register("nether_wood_beehive", List.of(EN_US.Beehive(EN_US.Wood(EN_US.Nether()))), /* Mod */ CRIMSON_BEEHIVE.asBlock(), WARPED_BEEHIVE.asBlock(), GILDED_BEEHIVE.asBlock());
		Register("nether_wood_bookshelf", List.of(EN_US.Bookshelf(EN_US.Wood(EN_US.Nether()))), /* Mod */ CRIMSON_BOOKSHELF.asBlock(), WARPED_BOOKSHELF.asBlock(), GILDED_BOOKSHELF.asBlock());
		Register("nether_wood_crafting_table", List.of(EN_US.Table(EN_US.Crafting(EN_US.Wood(EN_US.Nether())))), /* Mod */ CRIMSON_CRAFTING_TABLE.asBlock(), WARPED_CRAFTING_TABLE.asBlock(), GILDED_CRAFTING_TABLE.asBlock());
		Register("nether_wood_door", List.of(EN_US.Door(EN_US.Wood(EN_US.Nether()))), Blocks.CRIMSON_DOOR, Blocks.WARPED_DOOR, GILDED_DOOR.asBlock());
		Register("nether_wood_fence", List.of(EN_US.Fence(EN_US.Wood(EN_US.Nether()))), Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE, GILDED_FENCE.asBlock());
		Register("nether_wood_fence_gate", List.of(EN_US.Gate(EN_US.Fence(EN_US.Wood(EN_US.Nether())))), Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE, GILDED_FENCE_GATE.asBlock());
		Register("nether_wood_ladder", List.of(EN_US.Ladder(EN_US.Wood(EN_US.Nether()))), /* Mod */ CRIMSON_LADDER.asBlock(), WARPED_LADDER.asBlock(), GILDED_LADDER.asBlock());
		Register("nether_wood_lectern", List.of(EN_US.Lectern(EN_US.Wood(EN_US.Nether()))), /* Mod */ CRIMSON_LECTERN.asBlock(), WARPED_LECTERN.asBlock(), GILDED_LECTERN.asBlock());
		Register("nether_wood_sandy", List.of(EN_US.Wood(EN_US.Nether(EN_US.Sandy()))),
				/* Mod */ SANDY_CRIMSON_PLANKS.asBlock(), SANDY_CRIMSON_SLAB.asBlock(), SANDY_WARPED_PLANKS.asBlock(), SANDY_WARPED_SLAB.asBlock(), SANDY_GILDED_PLANKS.asBlock(), SANDY_GILDED_SLAB.asBlock(),
				/* Mod */ RED_SANDY_CRIMSON_PLANKS.asBlock(), RED_SANDY_CRIMSON_SLAB.asBlock(), RED_SANDY_WARPED_PLANKS.asBlock(), RED_SANDY_WARPED_SLAB.asBlock(), RED_SANDY_GILDED_PLANKS.asBlock(), RED_SANDY_GILDED_SLAB.asBlock());
		Register("nether_wood_trapdoor", List.of(EN_US.Trapdoor(EN_US.Wood(EN_US.Nether()))), Blocks.CRIMSON_TRAPDOOR, Blocks.WARPED_TRAPDOOR, GILDED_TRAPDOOR.asBlock());
		Register("netherite_bars", List.of(EN_US.Bars(EN_US.Netherite())), /* Mod */ NETHERITE_BARS.asBlock());
		Register("netherite_chain", List.of(EN_US.Chain(EN_US.Netherite())), /* Mod */ NETHERITE_CHAIN.asBlock());
		Register("netherite_lantern", List.of(EN_US.Lantern(EN_US.Netherite())), /* Mod */ NETHERITE_LANTERN.asBlock(), NETHERITE_SOUL_LANTERN.asBlock(), NETHERITE_ENDER_LANTERN.asBlock());
		Register("netherite_door", List.of(EN_US.Door(EN_US.Netherite())), /* Mod */ NETHERITE_DOOR.asBlock());
		Register("netherite_trapdoor", List.of(EN_US.Trapdoor(EN_US.Netherite())), /* Mod */ NETHERITE_TRAPDOOR.asBlock());
		Register("netherrack_iron_ore", List.of(EN_US.Ore(EN_US.Iron(EN_US.Netherrack()))), /* Mod */ NETHER_IRON_ORE.asBlock());
		Register("netherrack_copper_ore", List.of(EN_US.Ore(EN_US.Copper(EN_US.Netherrack()))), /* Mod */ NETHER_COPPER_ORE.asBlock());
		Register("netherrack_gold_ore", List.of(EN_US.Ore(EN_US.Gold(EN_US.Netherrack()))), Blocks.NETHER_GOLD_ORE);
		Register("netherrack_quartz_ore", List.of(EN_US.Ore(EN_US.Quartz(EN_US.Netherrack()))), Blocks.NETHER_QUARTZ_ORE);
		Register("note_block", List.of(EN_US.Block(EN_US.Note())), Blocks.NOTE_BLOCK);
		Register("observer", List.of(EN_US.Observer()), Blocks.OBSERVER);
		Register("obsidian", List.of(EN_US.Obsidian()), Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN,
				/* Mod */ OBSIDIAN_STAIRS.asBlock(), OBSIDIAN_SLAB.asBlock(), OBSIDIAN_WALL.asBlock(),// CRACKED_POLISHED_OBSIDIAN_BRICKS.asBlock(),
//				POLISHED_OBSIDIAN.asBlock(), POLISHED_OBSIDIAN_STAIRS.asBlock(), POLISHED_OBSIDIAN_SLAB.asBlock(), POLISHED_OBSIDIAN_WALL.asBlock(),
//				POLISHED_OBSIDIAN_BRICKS.asBlock(), POLISHED_OBSIDIAN_BRICK_STAIRS.asBlock(), POLISHED_OBSIDIAN_BRICK_SLAB.asBlock(), POLISHED_OBSIDIAN_BRICK_WALL.asBlock(),
				CRYING_OBSIDIAN_STAIRS.asBlock(), CRYING_OBSIDIAN_SLAB.asBlock(), CRYING_OBSIDIAN_WALL.asBlock(),// CRACKED_POLISHED_CRYING_OBSIDIAN_BRICKS.asBlock(),
//				POLISHED_CRYING_OBSIDIAN.asBlock(), POLISHED_CRYING_OBSIDIAN_STAIRS.asBlock(), POLISHED_CRYING_OBSIDIAN_SLAB.asBlock(), POLISHED_CRYING_OBSIDIAN_WALL.asBlock(),
//				POLISHED_CRYING_OBSIDIAN_BRICKS.asBlock(), POLISHED_CRYING_OBSIDIAN_BRICK_STAIRS.asBlock(), POLISHED_CRYING_OBSIDIAN_BRICK_SLAB.asBlock(), POLISHED_CRYING_OBSIDIAN_BRICK_WALL.asBlock(),
				BLEEDING_OBSIDIAN.asBlock(), BLEEDING_OBSIDIAN_STAIRS.asBlock(), BLEEDING_OBSIDIAN_SLAB.asBlock(), BLEEDING_OBSIDIAN_WALL.asBlock());//, CRACKED_POLISHED_BLEEDING_OBSIDIAN_BRICKS.asBlock(),
//				POLISHED_BLEEDING_OBSIDIAN.asBlock(), POLISHED_BLEEDING_OBSIDIAN_STAIRS.asBlock(), POLISHED_BLEEDING_OBSIDIAN_SLAB.asBlock(), POLISHED_BLEEDING_OBSIDIAN_WALL.asBlock(),
//				POLISHED_BLEEDING_OBSIDIAN_BRICKS.asBlock(), POLISHED_BLEEDING_OBSIDIAN_BRICK_STAIRS.asBlock(), POLISHED_BLEEDING_OBSIDIAN_BRICK_SLAB.asBlock(), POLISHED_BLEEDING_OBSIDIAN_BRICK_WALL.asBlock());
		Register("podzol", List.of(EN_US.Podzol()), Blocks.PODZOL);
		Register("polished_shale", List.of(EN_US.Shale(EN_US.Polished())), /* Mod */ POLISHED_SHALE.asBlock(), POLISHED_SHALE_STAIRS.asBlock(), POLISHED_SHALE_SLAB.asBlock(), POLISHED_SHALE_WALL.asBlock());
		Register("pot", List.of(EN_US.Pot()), Blocks.FLOWER_POT);
		Register("potted_plant", List.of(EN_US.Plant(EN_US.Potted())), Blocks.POTTED_ACACIA_SAPLING,
				Blocks.POTTED_BIRCH_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING, Blocks.POTTED_JUNGLE_SAPLING,
				Blocks.POTTED_OAK_SAPLING, Blocks.POTTED_SPRUCE_SAPLING,
				Blocks.POTTED_FERN, Blocks.POTTED_DEAD_BUSH, Blocks.POTTED_BAMBOO, Blocks.POTTED_CACTUS,
				Blocks.POTTED_ALLIUM, Blocks.POTTED_AZURE_BLUET, Blocks.POTTED_BLUE_ORCHID, Blocks.POTTED_CORNFLOWER,
				Blocks.POTTED_DANDELION, Blocks.POTTED_LILY_OF_THE_VALLEY,  Blocks.POTTED_ORANGE_TULIP,
				Blocks.POTTED_OXEYE_DAISY, Blocks.POTTED_PINK_TULIP, Blocks.POTTED_POPPY, Blocks.POTTED_RED_TULIP,
				Blocks.POTTED_WHITE_TULIP, Blocks.POTTED_WITHER_ROSE, Blocks.POTTED_AZALEA_BUSH, Blocks.POTTED_FLOWERING_AZALEA_BUSH,
				Blocks.POTTED_CRIMSON_FUNGUS, Blocks.POTTED_CRIMSON_ROOTS, Blocks.POTTED_WARPED_FUNGUS, Blocks.POTTED_WARPED_ROOTS,
				Blocks.POTTED_RED_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM,
				/* Mod */ BUTTERCUP.getPottedBlock(), PINK_DAISY.getPottedBlock(), ROSE.getPottedBlock(),
				BLUE_ROSE.getPottedBlock(), MAGENTA_TULIP.getPottedBlock(), MARIGOLD.getPottedBlock(),
				INDIGO_ORCHID.getPottedBlock(), MAGENTA_ORCHID.getPottedBlock(), ORANGE_ORCHID.getPottedBlock(),
				PURPLE_ORCHID.getPottedBlock(), RED_ORCHID.getPottedBlock(), WHITE_ORCHID.getPottedBlock(),
				YELLOW_ORCHID.getPottedBlock(), PINK_ALLIUM.getPottedBlock(), LAVENDER.getPottedBlock(),
				HYDRANGEA.getPottedBlock(), PAEONIA.getPottedBlock(), ASTER.getPottedBlock(),
				MANGROVE_PROPAGULE.getPottedBlock(), CHERRY_SAPLING.getPottedBlock(),
				BLUE_MUSHROOM.getPottedBlock(), MYCELIUM_ROOTS.getPottedBlock(), TORCHFLOWER.getPottedBlock(),
				DEATH_CAP_MUSHROOM.getPottedBlock(), BLUE_NETHERSHROOM.getPottedBlock(),
				CASSIA_SAPLING.getPottedBlock(), DOGWOOD_SAPLING.getPottedBlock(), GRAPE_SAPLING.getPottedBlock());
		Register("prismarine", List.of(EN_US.Prismarine()), Blocks.PRISMARINE, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_WALL,
				Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_STAIRS, Blocks.PRISMARINE_BRICK_SLAB,
				Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_STAIRS, Blocks.DARK_PRISMARINE_SLAB,
				/* Mod */ CHISELED_PRISMARINE_BRICKS.asBlock(), SMOOTH_CHISELED_PRISMARINE_BRICKS.asBlock(),
				CHISELED_PRISMARINE.asBlock(), CHISELED_PRISMARINE_STAIRS.asBlock(), CHISELED_PRISMARINE_SLAB.asBlock(),
				CUT_PRISMARINE_BRICKS.asBlock(), CUT_PRISMARINE_BRICK_STAIRS.asBlock(), CUT_PRISMARINE_BRICK_SLAB.asBlock(),
				PRISMARINE_TILES.asBlock(), PRISMARINE_TILE_STAIRS.asBlock(), PRISMARINE_TILE_SLAB.asBlock(),
				PRISMARINE_TILE_WALL.asBlock(), DARK_PRISMARINE_WALL.asBlock(),
				PRISMARINE_TORCH.asBlock(), PRISMARINE_TORCH.getWallBlock(), PRISMARINE_SOUL_TORCH.asBlock(),
				PRISMARINE_SOUL_TORCH.getWallBlock(), PRISMARINE_ENDER_TORCH.asBlock(),PRISMARINE_ENDER_TORCH.getWallBlock(),
				UNDERWATER_PRISMARINE_TORCH.asBlock(), UNDERWATER_PRISMARINE_TORCH.getWallBlock());
		Register("prismarine_sandy", List.of(EN_US.Prismarine(EN_US.Sandy())),
				/* Mod */ SANDY_PRISMARINE.asBlock(), SANDY_PRISMARINE_SLAB.asBlock(),
				SANDY_SANDY_PRISMARINE.asBlock(), SANDY_SANDY_PRISMARINE_SLAB.asBlock(),
				SANDY_PRISMARINE_BRICKS.asBlock(), SANDY_PRISMARINE_BRICK_SLAB.asBlock(),
				SANDY_CHISELED_PRISMARINE_BRICKS.asBlock(),
				SANDY_DARK_PRISMARINE.asBlock(), SANDY_DARK_PRISMARINE_SLAB.asBlock(),
				SANDY_SANDY_DARK_PRISMARINE.asBlock(), SANDY_SANDY_DARK_PRISMARINE_SLAB.asBlock(),
				RED_SANDY_PRISMARINE.asBlock(), RED_SANDY_PRISMARINE_SLAB.asBlock(),
				RED_SANDY_SANDY_PRISMARINE.asBlock(), RED_SANDY_SANDY_PRISMARINE_SLAB.asBlock(),
				RED_SANDY_PRISMARINE_BRICKS.asBlock(), RED_SANDY_PRISMARINE_BRICK_SLAB.asBlock(),
				RED_SANDY_CHISELED_PRISMARINE_BRICKS.asBlock(),
				RED_SANDY_DARK_PRISMARINE.asBlock(), RED_SANDY_DARK_PRISMARINE_SLAB.asBlock(),
				RED_SANDY_SANDY_DARK_PRISMARINE.asBlock(), RED_SANDY_SANDY_DARK_PRISMARINE_SLAB.asBlock());
		Register("pumice", List.of(EN_US.Pumice()), /* Mod */ PUMICE.asBlock());
		Register("pumpkin", List.of(EN_US.Pumpkin()), Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN,
				/* Mod */ SOUL_JACK_O_LANTERN.asBlock(), ENDER_JACK_O_LANTERN.asBlock(), BURNT_PUMPKIN.asBlock(),
				WHITE_PUMPKIN.asBlock(), CARVED_WHITE_PUMPKIN.asBlock(), WHITE_JACK_O_LANTERN.asBlock(), WHITE_SOUL_JACK_O_LANTERN.asBlock(), WHITE_ENDER_JACK_O_LANTERN.asBlock(),
				ROTTEN_PUMPKIN.asBlock(), CARVED_ROTTEN_PUMPKIN.asBlock(), ROTTEN_JACK_O_LANTERN.asBlock(), ROTTEN_SOUL_JACK_O_LANTERN.asBlock(), ROTTEN_ENDER_JACK_O_LANTERN.asBlock());
		Register("purpur", List.of(EN_US.Purpur()), Blocks.PURPUR_BLOCK, Blocks.PURPUR_STAIRS, Blocks.PURPUR_SLAB, Blocks.PURPUR_PILLAR,
				/* Mod */ PURPUR_WALL.asBlock(), CRACKED_PURPUR_BLOCK.asBlock(),
				CHISELED_PURPUR.asBlock(), CHISELED_PURPUR_STAIRS.asBlock(), CHISELED_PURPUR_SLAB.asBlock(), CHISELED_PURPUR_WALL.asBlock(),
				PURPUR_MOSAIC.asBlock(), PURPUR_MOSAIC_STAIRS.asBlock(), PURPUR_MOSAIC_SLAB.asBlock(), PURPUR_MOSAIC_WALL.asBlock(),
				SMOOTH_PURPUR.asBlock(), SMOOTH_PURPUR_STAIRS.asBlock(), SMOOTH_PURPUR_SLAB.asBlock(), SMOOTH_PURPUR_WALL.asBlock(),
				PURPUR_BRICKS.asBlock(), PURPUR_BRICK_STAIRS.asBlock(), PURPUR_BRICK_SLAB.asBlock(), PURPUR_BRICK_WALL.asBlock(),
				CHISELED_PURPUR_BRICKS.asBlock(), SMOOTH_CHISELED_PURPUR_BRICKS.asBlock(),
				PURPUR_TILES.asBlock(), PURPUR_TILE_STAIRS.asBlock(), PURPUR_TILE_SLAB.asBlock(), PURPUR_TILE_WALL.asBlock());
		Register("purpur_sandy", List.of(EN_US.Purpur()),
				/* Mod */ SANDY_PURPUR_BLOCK.asBlock(), SANDY_CHISELED_PURPUR.asBlock(), SANDY_SANDY_CHISELED_PURPUR.asBlock(),
				SANDY_SMOOTH_PURPUR.asBlock(), SANDY_PURPUR_BRICKS.asBlock(),
				RED_SANDY_PURPUR_BLOCK.asBlock(), RED_SANDY_CHISELED_PURPUR.asBlock(), RED_SANDY_SANDY_CHISELED_PURPUR.asBlock(),
				RED_SANDY_SMOOTH_PURPUR.asBlock(), RED_SANDY_PURPUR_BRICKS.asBlock());
		Register("quartz", List.of(EN_US.Quartz()), Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_SLAB, Blocks.QUARTZ_PILLAR,
				Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.CHISELED_QUARTZ_BLOCK,
				Blocks.QUARTZ_BRICKS, /* Mod */ QUARTZ_BRICK_STAIRS.asBlock(), QUARTZ_BRICK_SLAB.asBlock(), QUARTZ_BRICK_WALL.asBlock(),
				QUARTZ_CRYSTAL_BLOCK.asBlock(), QUARTZ_CRYSTAL_STAIRS.asBlock(), QUARTZ_CRYSTAL_SLAB.asBlock(), QUARTZ_CRYSTAL_WALL.asBlock(),
				QUARTZ_WALL.asBlock(), SMOOTH_QUARTZ_WALL.asBlock());
		Register("rail", List.of(EN_US.Rail()), Blocks.RAIL, Blocks.ACTIVATOR_RAIL, Blocks.DETECTOR_RAIL, Blocks.POWERED_RAIL);
		Register("raw_copper", List.of(EN_US.Copper(EN_US.Raw())), Blocks.RAW_COPPER_BLOCK, /* Mod */ RAW_COPPER_SLAB.asBlock());
		Register("raw_gold", List.of(EN_US.Gold(EN_US.Raw())), Blocks.RAW_GOLD_BLOCK, /* Mod */ RAW_GOLD_SLAB.asBlock());
		Register("raw_iron", List.of(EN_US.Iron(EN_US.Raw())), Blocks.RAW_IRON_BLOCK, /* Mod */ RAW_IRON_SLAB.asBlock());
		Register("redstone", List.of(EN_US.Redstone()), Blocks.REDSTONE_BLOCK);
		//TODO: Pressure Plates???
		Register("redstone_components", List.of(EN_US.Components(EN_US.Redstone())), Blocks.REPEATER, Blocks.COMPARATOR);
		Register("redstone_lamp", List.of(EN_US.Lamp(EN_US.Redstone())), Blocks.REDSTONE_LAMP);
		Register("redstone_wire", List.of(EN_US.Wire(EN_US.Redstone())), Blocks.REDSTONE_WIRE);
		Register("reinforced_deepslate", List.of(EN_US.Deepslate(EN_US.Reinforced())), REINFORCED_DEEPSLATE.asBlock());
		Register("respawn_anchor", List.of(EN_US.Anchor(EN_US.Respawn())), Blocks.RESPAWN_ANCHOR);
		Register("ruby", List.of(EN_US.Ruby()), /* Mod */ RUBY_BLOCK.asBlock(), RUBY_STAIRS.asBlock(), RUBY_SLAB.asBlock(), RUBY_WALL.asBlock(),
				RUBY_BRICKS.asBlock(), RUBY_BRICK_STAIRS.asBlock(), RUBY_BRICK_SLAB.asBlock(), RUBY_BRICK_WALL.asBlock());
		Register("sandstone", List.of(EN_US.Sandstone()), Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_WALL,
				Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_SANDSTONE_SLAB,
				Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_WALL,
				Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE_SLAB,
				Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE_SLAB,
				Blocks.CHISELED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE,
				/* Mod */ SMOOTH_SANDSTONE_WALL.asBlock(), SMOOTH_RED_SANDSTONE_WALL.asBlock());
		Register("sapling", List.of(EN_US.Sapling()), Blocks.ACACIA_SAPLING, Blocks.BIRCH_SAPLING, Blocks.DARK_OAK_SAPLING,
				Blocks.JUNGLE_SAPLING, Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, /* Mod */ CHERRY_SAPLING.asBlock(),
				CASSIA_SAPLING.asBlock(), DOGWOOD_SAPLING.asBlock(), GRAPE_SAPLING.asBlock());
		Register("sapphire", List.of(EN_US.Sapphire()), /* Mod */ SAPPHIRE_BLOCK.asBlock(), SAPPHIRE_STAIRS.asBlock(), SAPPHIRE_SLAB.asBlock(), SAPPHIRE_WALL.asBlock(),
				SAPPHIRE_BRICKS.asBlock(), SAPPHIRE_BRICK_STAIRS.asBlock(), SAPPHIRE_BRICK_SLAB.asBlock(), SAPPHIRE_BRICK_WALL.asBlock());
		Register(BlockSounds.StepOnly(ModSoundEvents.BLOCK_SCULK_STEP), /* Mod */ ANDESITE_SCULK_TURF.asBlock(), BASALT_SCULK_TURF.asBlock(),
				BLACKSTONE_SCULK_TURF.asBlock(), CALCITE_SCULK_TURF.asBlock(), DEEPSLATE_SCULK_TURF.asBlock(), DIORITE_SCULK_TURF.asBlock(),
				DRIPSTONE_SCULK_TURF.asBlock(), END_SHALE_SCULK_TURF.asBlock(), END_STONE_SCULK_TURF.asBlock(), END_ROCK_SCULK_TURF.asBlock(),
				END_ROCK_SHALE_SCULK_TURF.asBlock(), END_SHALE_ROCK_SCULK_TURF.asBlock(),
				GRANITE_SCULK_TURF.asBlock(), NETHERRACK_SCULK_TURF.asBlock(), RED_SANDSTONE_SCULK_TURF.asBlock(), SANDSTONE_SCULK_TURF.asBlock(),
				SHALE_SCULK_TURF.asBlock(), SMOOTH_BASALT_SCULK_TURF.asBlock(), STONE_SCULK_TURF.asBlock(), TUFF_SCULK_TURF.asBlock());
		Register("sculk_stone", List.of(EN_US.Stone(EN_US.Sculk())), /* Mod */ SCULK_STONE.asBlock(),
				SCULK_STONE_STAIRS.asBlock(), SCULK_STONE_SLAB.asBlock(), SCULK_STONE_WALL.asBlock());
		Register("sculk_stone_bricks", List.of(EN_US.Bricks(EN_US.Stone(EN_US.Sculk()))),
				/* Mod */ SCULK_STONE_BRICKS.asBlock(), SCULK_STONE_BRICK_STAIRS.asBlock(), SCULK_STONE_BRICK_SLAB.asBlock(), SCULK_STONE_BRICK_WALL.asBlock());//,
//				CHISELED_SCULK_STONE_BRICKS.asBlock(), SMOOTH_CHISELED_SCULK_STONE_BRICKS.asBlock());
		Register("sculk_stone_tiles", List.of(EN_US.Tiles(EN_US.Stone(EN_US.Sculk()))),
				/* Mod */ SCULK_STONE_TILES.asBlock(), SCULK_STONE_TILE_STAIRS.asBlock(), SCULK_STONE_TILE_SLAB.asBlock(), SCULK_STONE_TILE_WALL.asBlock());
		Register("sea_lantern", List.of(EN_US.Lantern(EN_US.Sea())), Blocks.SEA_LANTERN);
		Register("sea_pickle", List.of(EN_US.Pickle(EN_US.Sea())), Blocks.SEA_PICKLE);
		Register("seagrass", List.of(EN_US.Seagrass()), Blocks.SEAGRASS, Blocks.TALL_SEAGRASS);
		Register("seeds", List.of(EN_US.Seeds()), /* Mod */ SEED_BLOCK.asBlock());
		Register("shale", List.of(EN_US.Shale()), /* Mod */ SHALE.asBlock(), COBBLED_SHALE.asBlock(), COBBLED_SHALE_STAIRS.asBlock(), COBBLED_SHALE_SLAB.asBlock(), COBBLED_SHALE_WALL.asBlock());
		Register("shale_bricks", List.of(EN_US.Bricks(EN_US.Shale())), /* Mod */ SHALE_BRICKS.asBlock(), SHALE_BRICK_STAIRS.asBlock(), SHALE_BRICK_SLAB.asBlock(), SHALE_BRICK_WALL.asBlock());
		Register("shale_tiles", List.of(EN_US.Tiles(EN_US.Shale())), /* Mod */ SHALE_TILES.asBlock(), SHALE_TILE_STAIRS.asBlock(), SHALE_TILE_SLAB.asBlock(), SHALE_TILE_WALL.asBlock());
		Register("shulker_box", List.of(EN_US.Box(EN_US.Shulker())), Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX,
				Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX,
				Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
				Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX,
				Blocks.SHULKER_BOX);
		Register("skull", List.of(EN_US.Skull()), Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL);
		Register("smithing_table", List.of(EN_US.Table(EN_US.Smithing())), Blocks.SMITHING_TABLE);
		Register("smoker", List.of(EN_US.Smoker()), Blocks.SMOKER);
		Register("spawner", List.of(EN_US.Spawner()), Blocks.SPAWNER);
		Register("sponge", List.of(EN_US.Sponge()), Blocks.SPONGE);
		Register("stem", List.of(EN_US.Stem()), Blocks.MELON_STEM, Blocks.PUMPKIN_STEM, /* Mod */ WHITE_PUMPKIN_STEM);
		Register("stem_attached", List.of(EN_US.Stem(EN_US.Attached())), Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM, /* Mod */ ATTACHED_WHITE_PUMPKIN_STEM);
		Register("stone_coal_ore", List.of(EN_US.Ore(EN_US.Coal(EN_US.Stone()))), Blocks.COAL_ORE);
		Register("stone_copper_ore", List.of(EN_US.Ore(EN_US.Copper(EN_US.Stone()))), Blocks.COPPER_ORE);
		Register("stone_diamond_ore", List.of(EN_US.Ore(EN_US.Diamond(EN_US.Stone()))), Blocks.DIAMOND_ORE);
		Register("stone_emerald_ore", List.of(EN_US.Ore(EN_US.Emerald(EN_US.Stone()))), Blocks.EMERALD_ORE);
		Register("stone_gold_ore", List.of(EN_US.Ore(EN_US.Gold(EN_US.Stone()))), Blocks.GOLD_ORE);
		Register("stone_iron_ore", List.of(EN_US.Ore(EN_US.Iron(EN_US.Stone()))), Blocks.IRON_ORE);
		Register("stone_lapis_ore", List.of(EN_US.Ore(EN_US.Lapis(EN_US.Stone()))), Blocks.LAPIS_ORE);
		Register("stone_redstone_ore", List.of(EN_US.Ore(EN_US.Redstone(EN_US.Stone()))), Blocks.REDSTONE_ORE);
		Register("stone_ruby_ore", List.of(EN_US.Ore(EN_US.Ruby(EN_US.Stone()))), RUBY_ORE.asBlock());
		Register("stone_sapphire_ore", List.of(EN_US.Ore(EN_US.Sapphire(EN_US.Stone()))), SAPPHIRE_ORE.asBlock());
		Register("sugar", List.of(EN_US.Sugar()), /* Mod */ SUGAR_BLOCK.asBlock());
		Register("sugar_cane", List.of(EN_US.Cane(EN_US.Sugar())), Blocks.SUGAR_CANE);
		Register("sugar_cane_block", List.of(EN_US.Cane(EN_US.Sugar())), /* Mod */ SUGAR_CANE_BLOCK.asBlock(), SUGAR_CANE_BLOCK_SLAB.asBlock(), SUGAR_CANE_ROW.asBlock());
		Register(BlockSounds.BreakPlace("sweet_berry_bush", SoundEvents.BLOCK_SWEET_BERRY_BUSH_BREAK, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, List.of(EN_US.Bush(EN_US.Berry(EN_US.Sweet())))), Blocks.SWEET_BERRY_BUSH);
		Register("target", List.of(EN_US.Target()), Blocks.TARGET, /* Mod */ LIGHT_BLUE_TARGET.asBlock());
		BlockSounds terracotta = new BlockSounds("terracotta", List.of(EN_US.Terracotta()));
		Register(terracotta, Blocks.TERRACOTTA, Blocks.BLACK_TERRACOTTA,
				Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
				Blocks.GREEN_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA,
				Blocks.LIME_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.PINK_TERRACOTTA,
				Blocks.PURPLE_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA,
				/* Mod */ TERRACOTTA_STAIRS.asBlock(), TERRACOTTA_SLAB.asBlock(), TERRACOTTA_WALL.asBlock());
		Register(terracotta, TERRACOTTA_STAIRS_MAP.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(terracotta, TERRACOTTA_SLABS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(terracotta, TERRACOTTA_WALLS.values().stream().map(BlockContainer::asBlock).toArray(Block[]::new));
		Register(terracotta, BEIGE_TERRACOTTA.asBlock(), BEIGE_TERRACOTTA_STAIRS.asBlock(), BEIGE_TERRACOTTA_SLAB.asBlock(), BEIGE_TERRACOTTA_WALL.asBlock(),
				BURGUNDY_TERRACOTTA.asBlock(), BURGUNDY_TERRACOTTA_STAIRS.asBlock(), BURGUNDY_TERRACOTTA_SLAB.asBlock(), BURGUNDY_TERRACOTTA_WALL.asBlock(),
				LAVENDER_TERRACOTTA.asBlock(), LAVENDER_TERRACOTTA_STAIRS.asBlock(), LAVENDER_TERRACOTTA_SLAB.asBlock(), LAVENDER_TERRACOTTA_WALL.asBlock(),
				MINT_TERRACOTTA.asBlock(), MINT_TERRACOTTA_STAIRS.asBlock(), MINT_TERRACOTTA_SLAB.asBlock(), MINT_TERRACOTTA_WALL.asBlock());
		Register("tnt", List.of(EN_US.TNT()), Blocks.TNT);
		Register("tripwire", List.of(EN_US.Tripwire()), Blocks.TRIPWIRE, Blocks.TRIPWIRE_HOOK);
		Register("turtle_egg", List.of(EN_US.Egg(EN_US.Turtle())), Blocks.TURTLE_EGG);
		Register("wax", List.of(EN_US.Wax()), /* Mod */WAX_BLOCK.asBlock());
		Register("wet_sponge", List.of(EN_US.Sponge(EN_US.Wet())), Blocks.WET_SPONGE);
		Register("wood_door", List.of(EN_US.Door(EN_US.Wood())), Blocks.ACACIA_DOOR, Blocks.BIRCH_DOOR,
				Blocks.DARK_OAK_DOOR, Blocks.JUNGLE_DOOR, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR,
				/* Mod */ MANGROVE_DOOR.asBlock(), CHARRED_DOOR.asBlock());
		Register("wood_fence", List.of(EN_US.Fence(EN_US.Wood())), Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE,
				Blocks.DARK_OAK_FENCE, Blocks.JUNGLE_FENCE, Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE,
				/* Mod */ MANGROVE_FENCE.asBlock(), CHARRED_FENCE.asBlock());
		Register("wood_fence_gate", List.of(EN_US.Gate(EN_US.Fence(EN_US.Wood()))), Blocks.ACACIA_FENCE_GATE,
				Blocks.BIRCH_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.OAK_FENCE_GATE,
				Blocks.SPRUCE_FENCE_GATE, /* Mod */ MANGROVE_FENCE_GATE.asBlock(), CHARRED_FENCE_GATE.asBlock());
		Register("wood_sandy", List.of(EN_US.Wood(EN_US.Sandy())),
				/* Mod */ SANDY_ACACIA_PLANKS.asBlock(), SANDY_ACACIA_SLAB.asBlock(), SANDY_BIRCH_PLANKS.asBlock(), SANDY_BIRCH_SLAB.asBlock(), SANDY_DARK_OAK_PLANKS.asBlock(), SANDY_DARK_OAK_SLAB.asBlock(),
				SANDY_JUNGLE_PLANKS.asBlock(), SANDY_JUNGLE_SLAB.asBlock(), SANDY_OAK_PLANKS.asBlock(), SANDY_OAK_SLAB.asBlock(), SANDY_SPRUCE_PLANKS.asBlock(), SANDY_SPRUCE_SLAB.asBlock(),
				SANDY_MANGROVE_PLANKS.asBlock(), SANDY_MANGROVE_SLAB.asBlock(),
				SANDY_CASSIA_PLANKS.asBlock(), SANDY_CASSIA_SLAB.asBlock(), SANDY_CHARRED_PLANKS.asBlock(), SANDY_CHARRED_SLAB.asBlock(), SANDY_DOGWOOD_PLANKS.asBlock(), SANDY_DOGWOOD_SLAB.asBlock(),
				SANDY_HAY_PLANKS.asBlock(), SANDY_HAY_SLAB.asBlock(),
				SANDY_BLUE_MUSHROOM_PLANKS.asBlock(), SANDY_BLUE_MUSHROOM_SLAB.asBlock(), SANDY_BROWN_MUSHROOM_PLANKS.asBlock(), SANDY_BROWN_MUSHROOM_SLAB.asBlock(),
				SANDY_RED_MUSHROOM_PLANKS.asBlock(), SANDY_RED_MUSHROOM_SLAB.asBlock(), SANDY_MUSHROOM_STEM_PLANKS.asBlock(), SANDY_MUSHROOM_STEM_SLAB.asBlock(),
				RED_SANDY_ACACIA_PLANKS.asBlock(), RED_SANDY_ACACIA_SLAB.asBlock(), RED_SANDY_BIRCH_PLANKS.asBlock(), RED_SANDY_BIRCH_SLAB.asBlock(), RED_SANDY_DARK_OAK_PLANKS.asBlock(), RED_SANDY_DARK_OAK_SLAB.asBlock(),
				RED_SANDY_JUNGLE_PLANKS.asBlock(), RED_SANDY_JUNGLE_SLAB.asBlock(), RED_SANDY_OAK_PLANKS.asBlock(), RED_SANDY_OAK_SLAB.asBlock(), RED_SANDY_SPRUCE_PLANKS.asBlock(), RED_SANDY_SPRUCE_SLAB.asBlock(),
				RED_SANDY_MANGROVE_PLANKS.asBlock(), RED_SANDY_MANGROVE_SLAB.asBlock(),
				RED_SANDY_CASSIA_PLANKS.asBlock(), RED_SANDY_CASSIA_SLAB.asBlock(), RED_SANDY_CHARRED_PLANKS.asBlock(), RED_SANDY_CHARRED_SLAB.asBlock(), RED_SANDY_DOGWOOD_PLANKS.asBlock(), RED_SANDY_DOGWOOD_SLAB.asBlock(),
				RED_SANDY_HAY_PLANKS.asBlock(), RED_SANDY_HAY_SLAB.asBlock(),
				RED_SANDY_BLUE_MUSHROOM_PLANKS.asBlock(), RED_SANDY_BLUE_MUSHROOM_SLAB.asBlock(), RED_SANDY_BROWN_MUSHROOM_PLANKS.asBlock(), RED_SANDY_BROWN_MUSHROOM_SLAB.asBlock(),
				RED_SANDY_RED_MUSHROOM_PLANKS.asBlock(), RED_SANDY_RED_MUSHROOM_SLAB.asBlock(), RED_SANDY_MUSHROOM_STEM_PLANKS.asBlock(), RED_SANDY_MUSHROOM_STEM_SLAB.asBlock());
		Register("wood_trapdoor", List.of(EN_US.Trapdoor(EN_US.Wood())), Blocks.ACACIA_TRAPDOOR, Blocks.BIRCH_TRAPDOOR,
				Blocks.DARK_OAK_TRAPDOOR, Blocks.JUNGLE_TRAPDOOR, Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR,
				/* Mod */ MANGROVE_TRAPDOOR.asBlock(), CHARRED_TRAPDOOR.asBlock());
		Register("woodcutter", List.of(EN_US.Woodcutter()), /* Mod */ ACACIA_WOODCUTTER.asBlock(),
				BIRCH_WOODCUTTER.asBlock(), CRIMSON_WOODCUTTER.asBlock(), DARK_OAK_WOODCUTTER.asBlock(),
				JUNGLE_WOODCUTTER.asBlock(), MANGROVE_WOODCUTTER.asBlock(), WOODCUTTER.asBlock(),
				SPRUCE_WOODCUTTER.asBlock(), WARPED_WOODCUTTER.asBlock(), CHARRED_WOODCUTTER.asBlock());
	}
	private static final BlockSounds BARK_BLOCK = new BlockSounds("bark", List.of(EN_US.Bark()));
	private static final BlockSounds NETHER_BARK_BLOCK = new BlockSounds("nether_bark", List.of(EN_US.Bark(EN_US.Nether())));
	private static final BlockSounds NETHER_WOOD_BLOCK = new BlockSounds(ModBlockSoundGroups.NETHER_WOOD);
	private static BlockSounds getSounds(Block block) {
		if (blockSounds.containsKey(block)) return blockSounds.get(block);
		Identifier id = Registry.BLOCK.getId(block);
		if (copyBlockSounds.containsKey(id)) return getSounds(copyBlockSounds.get(id));
		return null;
	}
	public static SoundEvent getBreakSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.breakSound != null) return sounds.breakSound;
		return null;
	}
	private static final BlockSounds PISTON_BLOCK = new BlockSounds("piston", List.of(EN_US.Piston()));
	private static final SoundEvent STICKY_PISTON_TOP_STEP = BlockSounds.makeStep("sticky_piston_top", List.of(EN_US.Subtitle_Block_Step(EN_US.Sticky(EN_US.Something()))));
	public static SoundEvent getStepSound(BlockState state) {
		Block block = state.getBlock();
		if (block == Blocks.STICKY_PISTON) return state.get(PistonBlock.FACING) == Direction.UP ? STICKY_PISTON_TOP_STEP : PISTON_BLOCK.stepSound;
		if (block == Blocks.PISTON) return state.get(PistonBlock.FACING) == Direction.UP ? SoundEvents.BLOCK_WOOD_STEP : PISTON_BLOCK.stepSound;
		if (block == Blocks.PISTON_HEAD) {
			if (state.get(PistonBlock.FACING) == Direction.UP) {
				return state.get(PistonHeadBlock.TYPE) == PistonType.STICKY ? STICKY_PISTON_TOP_STEP : SoundEvents.BLOCK_WOOD_STEP;
			}
			return PISTON_BLOCK.stepSound;
		}
		if (block == Blocks.CRIMSON_STEM || block == Blocks.WARPED_STEM) {
			return state.get(PillarBlock.AXIS) != Direction.Axis.Y ? NETHER_BARK_BLOCK.stepSound : ModSoundEvents.BLOCK_NETHER_WOOD_STEP;
		}
		if (block == Blocks.ACACIA_LOG || block == Blocks.BIRCH_LOG || block == Blocks.DARK_OAK_LOG
				|| block == Blocks.JUNGLE_LOG || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG
				|| MANGROVE_LOG.contains(block) || CHARRED_LOG.contains(block)) {
			return state.get(PillarBlock.AXIS) != Direction.Axis.Y ? BARK_BLOCK.stepSound : SoundEvents.BLOCK_WOOD_STEP;
		}
		if (state.isIn(ModBlockTags.SCULK_TURFS)) return ModBlockSoundGroups.SCULK.getStepSound();
		BlockSounds sounds = getSounds(block);
		if (sounds != null && sounds.stepSound != null) return sounds.stepSound;
		return null;
	}
	public static SoundEvent getPlaceSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.placeSound != null) return sounds.placeSound;
		return null;
	}
	public static SoundEvent getHitSound(BlockState state) {
		BlockSounds sounds = getSounds(state.getBlock());
		if (sounds != null && sounds.hitSound != null) return sounds.hitSound;
		return null;
	}
}
