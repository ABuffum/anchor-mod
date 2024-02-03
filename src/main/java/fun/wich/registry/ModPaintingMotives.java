package fun.wich.registry;

import net.minecraft.entity.decoration.painting.PaintingMotive;

import static fun.wich.registry.ModRegistry.Register;

public class ModPaintingMotives {
	public static final PaintingMotive HANG_IN_THERE_KITTY = new PaintingMotive(16, 32);
	//Base Game (never actually registered)
	public static final PaintingMotive EARTH = new PaintingMotive(32, 32);
	public static final PaintingMotive FIRE = new PaintingMotive(32, 32);
	public static final PaintingMotive WATER = new PaintingMotive(32, 32);
	public static final PaintingMotive WIND = new PaintingMotive(32, 32);
	//Dungeons
	public static final PaintingMotive ARCHILLAGER_COOL_GUY = new PaintingMotive(32, 32);
	public static final PaintingMotive ARCHILLAGER_PORTRAIT = new PaintingMotive(32, 32);
	public static final PaintingMotive BLUE_FIELDS = new PaintingMotive(16, 16);
	public static final PaintingMotive BANNER = new PaintingMotive(48, 48);
	public static final PaintingMotive BOOKS = new PaintingMotive(48, 48);
	public static final PaintingMotive CAVE = new PaintingMotive(48, 48);
	public static final PaintingMotive CREEPER = new PaintingMotive(16, 16);
	public static final PaintingMotive CREEPER_STATUE = new PaintingMotive(48, 32);
	public static final PaintingMotive CROCODILE = new PaintingMotive(32, 32);
	public static final PaintingMotive CRYSTAL = new PaintingMotive(48, 48);
	public static final PaintingMotive DESERT = new PaintingMotive(32, 64);
	public static final PaintingMotive DESERT_TEMPLE = new PaintingMotive(48, 48);
	public static final PaintingMotive DOG = new PaintingMotive(48, 48);
	//public static final PaintingMotive EVOKER = new PaintingMotive(24, 24);
	public static final PaintingMotive GALE_SANCTUM_MAP = new PaintingMotive(48, 32);
	public static final PaintingMotive GRAVE = new PaintingMotive(48, 48);
	public static final PaintingMotive KEY_GOLEM = new PaintingMotive(48, 48);
	public static final PaintingMotive LIGHTHOUSE = new PaintingMotive(48, 48);
	//public static final PaintingMotive MOUNTAIN = new PaintingMotive(24, 24);
	public static final PaintingMotive NETHER = new PaintingMotive(32, 48);
	//public static final PaintingMotive PILLAGER = new PaintingMotive(24, 24);
	public static final PaintingMotive RED_MUSHROOMS = new PaintingMotive(48, 48);
	public static final PaintingMotive REDSTONE_GOLEM = new PaintingMotive(32, 16);
	public static final PaintingMotive SEASIDE = new PaintingMotive(32, 32);
	public static final PaintingMotive TORCH = new PaintingMotive(48, 48);
	public static final PaintingMotive TROPICAL = new PaintingMotive(32, 32);

	public static void RegisterPaintings() {
		Register("hang_in_there_kitty", HANG_IN_THERE_KITTY);
		//Base Game (never actually registered)
		Register("earth", EARTH);
		Register("fire", FIRE);
		Register("water", WATER);
		Register("wind", WIND);
		//Dungeons
		Register("archillager_cool_guy", ARCHILLAGER_COOL_GUY);
		Register("archillager_portrait", ARCHILLAGER_PORTRAIT);
		Register("blue_fields", BLUE_FIELDS);
		Register("banner", BANNER);
		Register("books", BOOKS);
		Register("cave", CAVE);
		Register("creeper", CREEPER);
		Register("creeper_statue", CREEPER_STATUE);
		Register("crocodile", CROCODILE);
		Register("crystal", CRYSTAL);
		Register("desert", DESERT);
		Register("desert_temple", DESERT_TEMPLE);
		Register("dog", DOG);
		//Register("evoker", EVOKER);
		Register("gale_sanctum_map", GALE_SANCTUM_MAP);
		Register("grave", GRAVE);
		Register("key_golem", KEY_GOLEM);
		Register("lighthouse", LIGHTHOUSE);
		//Register("mountain", MOUNTAIN);
		Register("nether", NETHER);
		//Register("pillager", PILLAGER);
		Register("red_mushrooms", RED_MUSHROOMS);
		Register("redstone_golem", REDSTONE_GOLEM);
		Register("seaside", SEASIDE);
		Register("torch", TORCH);
		Register("tropical", TROPICAL);
	}
}
