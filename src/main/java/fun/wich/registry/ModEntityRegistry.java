package fun.wich.registry;

import fun.wich.container.ArrowContainer;
import fun.wich.entity.hostile.PiranhaEntity;
import fun.wich.entity.hostile.SlimeCreeperEntity;
import fun.wich.entity.hostile.illager.IceologerEntity;
import fun.wich.entity.hostile.illager.MageEntity;
import fun.wich.entity.hostile.illager.MountaineerEntity;
import fun.wich.entity.hostile.skeleton.MossySkeletonEntity;
import fun.wich.entity.hostile.skeleton.SunkenSkeletonEntity;
import fun.wich.entity.hostile.slime.TropicalSlimeEntity;
import fun.wich.entity.hostile.spider.BoneSpiderEntity;
import fun.wich.entity.hostile.spider.IcySpiderEntity;
import fun.wich.entity.hostile.spider.JumpingSpiderEntity;
import fun.wich.entity.hostile.spider.SlimeSpiderEntity;
import fun.wich.entity.hostile.warden.WardenEntity;
import fun.wich.entity.hostile.zombie.FrozenZombieEntity;
import fun.wich.entity.hostile.zombie.JungleZombieEntity;
import fun.wich.entity.hostile.zombie.SlimeZombieEntity;
import fun.wich.entity.neutral.golem.MelonGolemEntity;
import fun.wich.entity.passive.HedgehogEntity;
import fun.wich.entity.passive.RaccoonEntity;
import fun.wich.entity.passive.RedPandaEntity;
import fun.wich.entity.passive.SlimeHorseEntity;
import fun.wich.entity.passive.camel.CamelEntity;
import fun.wich.entity.passive.chicken.FancyChickenEntity;
import fun.wich.entity.passive.chicken.SlimeChickenEntity;
import fun.wich.entity.passive.cow.*;
import fun.wich.entity.passive.frog.FrogEntity;
import fun.wich.entity.passive.frog.TadpoleEntity;
import fun.wich.entity.passive.sheep.MossySheepEntity;
import fun.wich.entity.passive.sheep.RainbowSheepEntity;
import fun.wich.entity.passive.sniffer.SnifferEntity;
import fun.wich.entity.vehicle.DispenserMinecartEntity;
import fun.wich.gen.data.language.Words;
import fun.wich.gen.data.tag.ModBiomeTags;
import fun.wich.item.pouch.ChickenPouchItem;
import fun.wich.item.pouch.EntityPouchItem;
import fun.wich.sound.ModSoundEvents;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.event.GameEvent;

import java.util.List;

import static fun.wich.ModBase.*;
import static fun.wich.ModFactory.*;
import static fun.wich.ModFactory.MakeSpawnEgg;
import static fun.wich.registry.ModRegistry.Register;

import static fun.wich.entity.ModEntityType.*;

public class ModEntityRegistry {
	//<editor-fold desc="Buckets">
	public static final Item TADPOLE_BUCKET = GeneratedItem(new EntityBucketItem(TADPOLE_ENTITY, Fluids.WATER, ModSoundEvents.ITEM_BUCKET_EMPTY_TADPOLE, ItemSettings(ItemGroup.MISC).maxCount(1)));
	//Mod Mobs
	public static final Item PIRANHA_BUCKET = GeneratedItem(new EntityBucketItem(PIRANHA_ENTITY, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, ItemSettings().maxCount(1)));
	//</editor-fold>
	//<editor-fold desc="Pouches">
	public static final Item CHICKEN_POUCH = GeneratedItem(new ChickenPouchItem(EntityType.CHICKEN, ItemSettings().maxCount(1)).dispensible());
	public static final Item RABBIT_POUCH = GeneratedItem(new EntityPouchItem(EntityType.RABBIT, ModSoundEvents.ITEM_POUCH_EMPTY_RABBIT, ItemSettings().maxCount(1)).dispensible());
	public static final Item PARROT_POUCH = GeneratedItem(new EntityPouchItem(EntityType.PARROT, ModSoundEvents.ITEM_POUCH_EMPTY_PARROT, ItemSettings().maxCount(1)).dispensible());
	public static final Item ENDERMITE_POUCH = GeneratedItem(new EntityPouchItem(EntityType.ENDERMITE, ModSoundEvents.ITEM_POUCH_EMPTY_ENDERMITE, ItemSettings().maxCount(1)).dispensible());
	public static final Item SILVERFISH_POUCH = GeneratedItem(new EntityPouchItem(EntityType.SILVERFISH, ModSoundEvents.ITEM_POUCH_EMPTY_SILVERFISH, ItemSettings().maxCount(1)).dispensible());
	//Mod Mobs
	public static final Item HEDGEHOG_POUCH = GeneratedItem(new EntityPouchItem(HEDGEHOG_ENTITY, ModSoundEvents.ITEM_POUCH_EMPTY_HEDGEHOG, ItemSettings().maxCount(1)).dispensible());
	//</editor-fold>
	//<editor-fold desc="Spawn Eggs">
	public static final Item CAMEL_SPAWN_EGG = MakeSpawnEgg(CAMEL_ENTITY, 16565097, 13341495, ItemSettings(ItemGroup.MISC));
	public static final Item FROG_SPAWN_EGG = MakeSpawnEgg(FROG_ENTITY, 13661252, 0xFFC77C, ItemSettings(ItemGroup.MISC));
	public static final Item SNIFFER_SPAWN_EGG = MakeSpawnEgg(SNIFFER_ENTITY, 9840944, 5085536, ItemSettings(ItemGroup.MISC));
	public static final Item TADPOLE_SPAWN_EGG = MakeSpawnEgg(TADPOLE_ENTITY, 7164733, 1444352, ItemSettings(ItemGroup.MISC));
	public static final Item WARDEN_SPAWN_EGG = MakeSpawnEgg(WARDEN_ENTITY, 1001033, 3790560, ItemSettings(ItemGroup.MISC));
	//</editor-fold>
	//<editor-fold desc="Mod Spawn Eggs">
	public static final Item BONE_SPIDER_SPAWN_EGG = MakeSpawnEgg(BONE_SPIDER_ENTITY, 0x270F19, 0x632FB7);
	public static final Item BLUE_MOOSHROOM_SPAWN_EGG = MakeSpawnEgg(BLUE_MOOSHROOM_ENTITY, 0x0D6794, 0x929292);
	public static final Item NETHER_MOOSHROOM_SPAWN_EGG = MakeSpawnEgg(NETHER_MOOSHROOM_ENTITY, 0x871116, 0xFF6500);
	public static final Item MOOBLOOM_SPAWN_EGG = MakeSpawnEgg(MOOBLOOM_ENTITY, 0xFDD500, 0xFDF7BA);
	public static final Item MOOLIP_SPAWN_EGG = MakeSpawnEgg(MOOLIP_ENTITY, 0xFFA9C2, 0xFFE4E4);
	public static final Item MOOBLOSSOM_SPAWN_EGG = MakeSpawnEgg(MOOBLOSSOM_ENTITY, 0xDF317C, 0x994369);
	public static final Item FANCY_CHICKEN_SPAWN_EGG = MakeSpawnEgg(FANCY_CHICKEN_ENTITY, 0xB788CB, 0xF7B035);
	public static final Item HEDGEHOG_SPAWN_EGG = MakeSpawnEgg(HEDGEHOG_ENTITY, 11440263, 7558239);
	public static final Item ICY_SPIDER_SPAWN_EGG = MakeSpawnEgg(ICY_SPIDER_ENTITY, 0x045480, 0x3CFEFB);
	public static final Item JUMPING_SPIDER_SPAWN_EGG = MakeSpawnEgg(JUMPING_SPIDER_ENTITY, 0x281206, 0x3C0202);
	public static final Item MOSSY_SHEEP_SPAWN_EGG = GeneratedItem(new SpawnEggItem(MOSSY_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF, ItemSettings()));
	public static final Item RED_PHANTOM_SPAWN_EGG = MakeSpawnEgg(RED_PHANTOM_ENTITY, 0x881214, 0x00E5F9);
	public static final Item PIRANHA_SPAWN_EGG = MakeSpawnEgg(PIRANHA_ENTITY, 4877153, 11762012);
	public static final Item RACCOON_SPAWN_EGG = MakeSpawnEgg(RACCOON_ENTITY, 0x646464, 0x0B0B0B);
	public static final Item RED_PANDA_SPAWN_EGG = MakeSpawnEgg(RED_PANDA_ENTITY, 0xC35330, 0x0B0B0B);
	public static final Item TROPICAL_SLIME_SPAWN_EGG = MakeSpawnEgg(TROPICAL_SLIME_ENTITY, 0x8FD3FF, 0x345C7A);
	public static final Item PINK_SLIME_SPAWN_EGG = MakeSpawnEgg(PINK_SLIME_ENTITY, 0xE0A3EE, 0xB85ECE);
	public static final Item SLIME_CHICKEN_SPAWN_EGG = MakeSpawnEgg(SLIME_CHICKEN_ENTITY, 5349438, 0xFF0000);
	public static final Item SLIME_COW_SPAWN_EGG = MakeSpawnEgg(SLIME_COW_ENTITY, 5349438, 0x3F3024);
	public static final Item SLIME_HORSE_SPAWN_EGG = MakeSpawnEgg(SLIME_HORSE_ENTITY, 5349438, 0xCFC700);
	public static final Item SLIME_SPIDER_SPAWN_EGG = MakeSpawnEgg(SLIME_SPIDER_ENTITY, 5349438, 11013646);
	public static final Item RAINBOW_SHEEP_SPAWN_EGG = GeneratedItem(new SpawnEggItem(RAINBOW_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF, ItemSettings()));
	public static final Item SLIME_CREEPER_SPAWN_EGG = MakeSpawnEgg(SLIME_CREEPER_ENTITY, 5349438, 0);
	public static final Item MOSSY_SKELETON_SPAWN_EGG = MakeSpawnEgg(MOSSY_SKELETON_ENTITY, 0xD6D7C6, 0x526121);
	public static final Item SLIMY_SKELETON_SPAWN_EGG = MakeSpawnEgg(SLIMY_SKELETON_ENTITY, 5349438, 0x494949);
	public static final Item SUNKEN_SKELETON_SPAWN_EGG = MakeSpawnEgg(SUNKEN_SKELETON_ENTITY, 0xD6D0C9, 0x98439E);
	public static final Item FROZEN_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(FROZEN_ZOMBIE_ENTITY, 0x78BEDE, 0x5A8684);
	public static final Item JUNGLE_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(JUNGLE_ZOMBIE_ENTITY, 0x67BC97, 0x5A8646);
	public static final Item SLIME_ZOMBIE_SPAWN_EGG = MakeSpawnEgg(SLIME_ZOMBIE_ENTITY, 5349438, 0x5A8646);
	public static final Item JOLLY_LLAMA_SPAWN_EGG = MakeSpawnEgg(JOLLY_LLAMA_ENTITY, 12623485, 0xFF0000);
	public static final Item ICEOLOGER_SPAWN_EGG = MakeSpawnEgg(ICEOLOGER_ENTITY, 0x959B9B, 0x01374B);
	public static final Item MOUNTAINEER_SPAWN_EGG = MakeSpawnEgg(MOUNTAINEER_ENTITY, 0x959B9B, 0x6F4A2D);
	public static final Item MAGE_SPAWN_EGG = MakeSpawnEgg(MAGE_ENTITY, 0x959B9B, 0x5A1949);
	//</editor-fold>
	//<editor-fold desc="Summoning Arrows">
	public static final ArrowContainer AXOLOTL_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.AXOLOTL, 16499171, 10890612);
	public static final ArrowContainer BAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BAT, 4996656, 986895);
	public static final ArrowContainer BEE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BEE, 15582019, 4400155);
	public static final ArrowContainer BLAZE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.BLAZE, 16167425, 16775294);
	public static final ArrowContainer CAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CAT, 15714446, 9794134);
	public static final ArrowContainer CAMEL_SUMMONING_ARROW = ArrowContainer.Summoning(CAMEL_ENTITY, 16565097, 13341495);
	public static final ArrowContainer CAVE_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CAVE_SPIDER, 803406, 11013646);
	public static final ArrowContainer CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CHICKEN, 0xA1A1A1, 0xFF0000);
	public static final ArrowContainer COD_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.COD, 12691306, 15058059);
	public static final ArrowContainer COW_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.COW, 4470310, 0xA1A1A1);
	public static final ArrowContainer CREEPER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.CREEPER, 894731, 0);
	public static final ArrowContainer DOLPHIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DOLPHIN, 2243405, 0xF9F9F9);
	public static final ArrowContainer DONKEY_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DONKEY, 5457209, 8811878);
	public static final ArrowContainer DROWNED_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.DROWNED, 9433559, 7969893);
	public static final ArrowContainer ELDER_GUARDIAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ELDER_GUARDIAN, 13552826, 7632531);
	public static final ArrowContainer ENDERMAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ENDERMAN, 0x161616, 0);
	public static final ArrowContainer ENDERMITE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ENDERMITE, 0x161616, 0x6E6E6E);
	public static final ArrowContainer EVOKER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.EVOKER, 0x959B9B, 1973274);
	public static final ArrowContainer FOX_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.FOX, 14005919, 13396256);
	public static final ArrowContainer FROG_SUMMONING_ARROW = ArrowContainer.Summoning(FROG_ENTITY, 13661252, 0xFFC77C);
	public static final ArrowContainer GHAST_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GHAST, 0xF9F9F9, 0xBCBCBC);
	public static final ArrowContainer GIANT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GIANT, 44975, 7969893);
	public static final ArrowContainer GLOW_SQUID_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GLOW_SQUID, 611926, 8778172);
	public static final ArrowContainer GOAT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GOAT, 10851452, 5589310);
	public static final ArrowContainer GUARDIAN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.GUARDIAN, 5931634, 15826224);
	public static final ArrowContainer HOGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HOGLIN, 13004373, 6251620);
	public static final ArrowContainer HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HORSE, 12623485, 0xEEE500);
	public static final ArrowContainer HUSK_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.HUSK, 7958625, 15125652);
	public static final ArrowContainer IRON_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.IRON_GOLEM, 0xB1B0B0, 0xE3901D);
	public static final ArrowContainer LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.LLAMA, 12623485, 10051392);
	public static final ArrowContainer MAGMA_CUBE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MAGMA_CUBE, 0x340000, 0xFCFC00);
	public static final ArrowContainer MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MOOSHROOM, 10489616, 0xB7B7B7);
	public static final ArrowContainer MULE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.MULE, 1769984, 5321501);
	public static final ArrowContainer OCELOT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.OCELOT, 15720061, 5653556);
	public static final ArrowContainer PANDA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PANDA, 0xE7E7E7, 0x1B1B22);
	public static final ArrowContainer PARROT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PARROT, 894731, 0xFF0000);
	public static final ArrowContainer PHANTOM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PHANTOM, 4411786, 0x88FF00);
	public static final ArrowContainer PIG_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIG, 15771042, 14377823);
	public static final ArrowContainer PIGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIGLIN, 10051392, 16380836);
	public static final ArrowContainer PIGLIN_BRUTE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PIGLIN_BRUTE, 5843472, 16380836);
	public static final ArrowContainer PILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PILLAGER, 5451574, 0x959B9B);
	public static final ArrowContainer POLAR_BEAR_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.POLAR_BEAR, 0xF2F2F2, 0x959590);
	public static final ArrowContainer PUFFERFISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.PUFFERFISH, 16167425, 3654642);
	public static final ArrowContainer RABBIT_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.RABBIT, 10051392, 7555121);
	public static final ArrowContainer RAVAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.RAVAGER, 7697520, 5984329);
	public static final ArrowContainer SALMON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SALMON, 10489616, 951412);
	public static final ArrowContainer SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SHEEP, 0xE7E7E7, 0xFFB5B5);
	public static final ArrowContainer SHULKER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SHULKER, 9725844, 5060690);
	public static final ArrowContainer SILVERFISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SILVERFISH, 0x6E6E6E, 0x303030);
	public static final ArrowContainer SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SKELETON, 0xC1C1C1, 0x494949);
	public static final ArrowContainer SKELETON_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SKELETON_HORSE, 6842447, 15066584);
	public static final ArrowContainer SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SLIME, 5349438, 8306542);
	public static final ArrowContainer SNIFFER_SUMMONING_ARROW = ArrowContainer.Summoning(SNIFFER_ENTITY, 9840944, 5085536);
	public static final ArrowContainer SNOW_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SNOW_GOLEM, 0xF0FDFD, 0xE3901D);
	public static final ArrowContainer SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SPIDER, 3419431, 11013646);
	public static final ArrowContainer SQUID_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.SQUID, 2243405, 7375001);
	public static final ArrowContainer STRAY_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.STRAY, 0x617677, 0xDDEAEA);
	public static final ArrowContainer STRIDER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.STRIDER, 10236982, 0x4D494D);
	public static final ArrowContainer TADPOLE_SUMMONING_ARROW = ArrowContainer.Summoning(TADPOLE_ENTITY, 7164733, 1444352);
	public static final ArrowContainer TRADER_LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TRADER_LLAMA, 15377456, 4547222);
	public static final ArrowContainer TROPICAL_FISH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TROPICAL_FISH, 15690005, 0xFFF9EF);
	public static final ArrowContainer TURTLE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.TURTLE, 0xE7E7E7, 44975);
	public static final ArrowContainer VEX_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VEX, 8032420, 15265265);
	public static final ArrowContainer VILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VILLAGER, 5651507, 12422002);
	public static final ArrowContainer VINDICATOR_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.VINDICATOR, 0x959B9B, 2580065);
	public static final ArrowContainer WANDERING_TRADER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WANDERING_TRADER, 4547222, 15377456);
	public static final ArrowContainer WARDEN_SUMMONING_ARROW = ArrowContainer.Summoning(WARDEN_ENTITY, 1001033, 3790560);
	public static final ArrowContainer WITCH_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITCH, 0x340000, 5349438);
	public static final ArrowContainer WITHER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITHER, 0x141414, 0x474D4D);
	public static final ArrowContainer WITHER_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WITHER_SKELETON, 0x141414, 0x474D4D);
	public static final ArrowContainer WOLF_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.WOLF, 0xD7D3D3, 13545366);
	public static final ArrowContainer ZOGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOGLIN, 13004373, 0xE6E6E6);
	public static final ArrowContainer ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE, 44975, 7969893);
	public static final ArrowContainer ZOMBIE_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE_HORSE, 3232308, 9945732);
	public static final ArrowContainer ZOMBIE_VILLAGER_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIE_VILLAGER, 5651507, 7969893);
	public static final ArrowContainer ZOMBIFIED_PIGLIN_SUMMONING_ARROW = ArrowContainer.Summoning(EntityType.ZOMBIFIED_PIGLIN, 15373203, 5009705);
	//</editor-fold>
	//<editor-fold desc="Mod Mob Summoning Arrows">
	public static final ArrowContainer MELON_GOLEM_SUMMONING_ARROW = ArrowContainer.Summoning(MELON_GOLEM_ENTITY, 0xF0FDFD, 0x7B7F16);
	public static final ArrowContainer BONE_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(BONE_SPIDER_ENTITY, 0x270F19, 0x632FB7);
	public static final ArrowContainer ICY_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(ICY_SPIDER_ENTITY, 0x045480, 0x3CFEFB);
	public static final ArrowContainer SLIME_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_SPIDER_ENTITY, 5349438, 11013646);
	public static final ArrowContainer HEDGEHOG_SUMMONING_ARROW = ArrowContainer.Summoning(HEDGEHOG_ENTITY, 11440263, 7558239);
	public static final ArrowContainer RACCOON_SUMMONING_ARROW = ArrowContainer.Summoning(RACCOON_ENTITY, 0x646464, 0x0B0B0B);
	public static final ArrowContainer RED_PANDA_SUMMONING_ARROW = ArrowContainer.Summoning(RED_PANDA_ENTITY, 0xC35330, 0x0B0B0B);
	public static final ArrowContainer JUMPING_SPIDER_SUMMONING_ARROW = ArrowContainer.Summoning(JUMPING_SPIDER_ENTITY, 0x281206, 0x3C0202);
	public static final ArrowContainer RED_PHANTOM_SUMMONING_ARROW = ArrowContainer.Summoning(RED_PHANTOM_ENTITY, 0x881214, 0x00E5F9);
	public static final ArrowContainer PIRANHA_SUMMONING_ARROW = ArrowContainer.Summoning(PIRANHA_ENTITY, 4877153, 11762012);
	public static final ArrowContainer TROPICAL_SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(TROPICAL_SLIME_ENTITY, 0x8FD3FF, 0x345C7A);
	public static final ArrowContainer PINK_SLIME_SUMMONING_ARROW = ArrowContainer.Summoning(PINK_SLIME_ENTITY, 0xE0A3EE, 0xB85ECE);
	public static final ArrowContainer SLIME_CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_CHICKEN_ENTITY, 5349438, 0xFF0000);
	public static final ArrowContainer SLIME_COW_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_COW_ENTITY, 5349438, 0x3F3024);
	public static final ArrowContainer SLIME_HORSE_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_HORSE_ENTITY, 5349438, 0xCFC700);
	public static final ArrowContainer FANCY_CHICKEN_SUMMONING_ARROW = ArrowContainer.Summoning(FANCY_CHICKEN_ENTITY, 0xB788CB, 0xF7B035);
	public static final ArrowContainer BLUE_MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(BLUE_MOOSHROOM_ENTITY, 0x0D6794, 0x929292);
	public static final ArrowContainer NETHER_MOOSHROOM_SUMMONING_ARROW = ArrowContainer.Summoning(NETHER_MOOSHROOM_ENTITY, 0x871116, 0xFF6500);
	public static final ArrowContainer MOOBLOOM_SUMMONING_ARROW = ArrowContainer.Summoning(MOOBLOOM_ENTITY, 0xFDD500, 0xFDF7BA);
	public static final ArrowContainer MOOLIP_SUMMONING_ARROW = ArrowContainer.Summoning(MOOLIP_ENTITY, 0xFFA9C2, 0xFFE4E4);
	public static final ArrowContainer MOOBLOSSOM_SUMMONING_ARROW = ArrowContainer.Summoning(MOOBLOSSOM_ENTITY, 0xDF317C, 0x994369);
	public static final ArrowContainer MOSSY_SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(MOSSY_SHEEP_ENTITY, 0xFFFFFF, 0x6C8031);
	public static final ArrowContainer RAINBOW_SHEEP_SUMMONING_ARROW = ArrowContainer.Summoning(RAINBOW_SHEEP_ENTITY, 0xFFFFFF, 0xFFFFFF).generatedItemModel();
	public static final ArrowContainer SLIME_CREEPER_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_CREEPER_ENTITY, 5349438, 0);
	public static final ArrowContainer MOSSY_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(MOSSY_SKELETON_ENTITY, 0xD6D7C6, 0x526121);
	public static final ArrowContainer SLIMY_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(SLIMY_SKELETON_ENTITY, 5349438, 0x494949);
	public static final ArrowContainer SUNKEN_SKELETON_SUMMONING_ARROW = ArrowContainer.Summoning(SUNKEN_SKELETON_ENTITY, 0xD6D0C9, 0x98439E);
	public static final ArrowContainer FROZEN_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(FROZEN_ZOMBIE_ENTITY, 0x78BEDE, 0x5A8684);
	public static final ArrowContainer JUNGLE_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(JUNGLE_ZOMBIE_ENTITY, 0x67BC97, 0x5A8646);
	public static final ArrowContainer SLIME_ZOMBIE_SUMMONING_ARROW = ArrowContainer.Summoning(SLIME_ZOMBIE_ENTITY, 5349438, 0x5A8646);
	public static final ArrowContainer ICEOLOGER_SUMMONING_ARROW = ArrowContainer.Summoning(ICEOLOGER_ENTITY, 5349438, 0x5A8646);
	public static final ArrowContainer MOUNTAINEER_SUMMONING_ARROW = ArrowContainer.Summoning(MOUNTAINEER_ENTITY, 5349438, 0x6F4A2D);
	public static final ArrowContainer MAGE_SUMMONING_ARROW = ArrowContainer.Summoning(MAGE_ENTITY, 5349438, 0x5A1949);
	public static final ArrowContainer JOLLY_LLAMA_SUMMONING_ARROW = ArrowContainer.Summoning(JOLLY_LLAMA_ENTITY, 12623485, 0xFF0000);
	//</editor-fold>

	public static void RegisterAll() {
		RegisterEntities();
		RegisterMobs();
		//<editor-fold desc="Buckets">
		Register("minecraft:tadpole_bucket", TADPOLE_BUCKET, List.of(EN_US.Tadpole(EN_US.of(EN_US.Bucket()))));
		Register("piranha_bucket", PIRANHA_BUCKET, List.of(EN_US.Piranha(EN_US.of(EN_US.Bucket()))));
		//</editor-fold>
		//<editor-fold desc="Pouches">
		Register("pouch", POUCH, List.of(EN_US.Pouch()));
		Register("chicken_pouch", CHICKEN_POUCH, List.of(EN_US.Chicken(EN_US.of(EN_US.Pouch()))));
		Register("rabbit_pouch", RABBIT_POUCH, List.of(EN_US.Rabbit(EN_US.of(EN_US.Pouch()))));
		Register("parrot_pouch", PARROT_POUCH, List.of(EN_US.Parrot(EN_US.of(EN_US.Pouch()))));
		Register("endermite_pouch", ENDERMITE_POUCH, List.of(EN_US.Endermite(EN_US.of(EN_US.Pouch()))));
		Register("silverfish_pouch", SILVERFISH_POUCH, List.of(EN_US.Silverfish(EN_US.of(EN_US.Pouch()))));
		Register("hedgehog_pouch", HEDGEHOG_POUCH, List.of(EN_US.Hedgehog(EN_US.of(EN_US.Pouch()))));
		//</editor-fold>
		RegisterSummoningArrows();
		RegisterSpawnEggs();
	}
	public static void RegisterEntities() {
		//Vehicles
		Register("mod_boat", MOD_BOAT_ENTITY, List.of(EN_US.Boat()));
		Register("minecraft:chest_boat", CHEST_BOAT_ENTITY, List.of(EN_US.Boat(EN_US.Chest())));
		Register("mod_chest_boat", MOD_CHEST_BOAT_ENTITY, List.of(EN_US.Boat(EN_US.Chest())));
		Register("dispenser_minecart", DISPENSER_MINECART_ENTITY, List.of(EN_US.Dispenser(EN_US.with(EN_US.Minecart()))));
		DispenserMinecartEntity.OverrideDispenserBehaviors();
		//Mod Entities
		Register("ice_chunk", ICE_CHUNK_ENTITY, List.of(EN_US.Chunk(Words.Ice)));
		Register("powder_keg", POWDER_KEG_ENTITY, List.of(EN_US.Keg(EN_US.Powder())));
		//Projectile
		Register("bone_shard_projectile", BONE_SHARD_PROJECTILE_ENTITY, List.of(EN_US.Shard(EN_US.Bone())));
		Register("bottled_confetti", BOTTLED_CONFETTI_ENTITY, List.of(EN_US.Confetti(EN_US.Bottled())));
		Register("bottled_lightning", BOTTLED_LIGHTNING_ENTITY, List.of(EN_US.Lightning(EN_US.Bottled())));
		Register("dropped_confetti", DROPPED_CONFETTI_ENTITY, List.of(EN_US.Confetti(EN_US.Dropped())));
		Register("dropped_dragon_breath", DROPPED_DRAGON_BREATH_ENTITY, List.of(EN_US.Breath(EN_US.Dragon(EN_US.Dropped()))));
		Register("melon_seeds_projectile", MELON_SEED_PROJECTILE_ENTITY, List.of(EN_US.Seeds(EN_US.Melon())));
		Register("pink_slime_ball", PINK_SLIME_BALL_ENTITY, List.of(EN_US.Ball(EN_US.Slime(Words.Pink))));
		Register("purple_eye_of_ender", PURPLE_EYE_OF_ENDER_ENTITY, List.of(EN_US.Ender(EN_US.of(EN_US.Eye(Words.Purple)))));
		Register("slowing_snowball", SLOWING_SNOWBALL_ENTITY, List.of(EN_US.Snowball(EN_US.Slowing())));
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY, List.of(EN_US.Tomato(EN_US.Throwable())));
		//Tridents
		Register("javelin", JAVELIN_ENTITY, List.of(EN_US.Javelin()));
		Register("amethyst_trident", AMETHYST_TRIDENT_ENTITY, List.of(EN_US.Trident(Words.Amethyst)));
		//Cloud
		Register("confetti_cloud", CONFETTI_CLOUD_ENTITY, List.of(EN_US.Cloud(EN_US.Confetti())));
		Register("dragon_breath_cloud", DRAGON_BREATH_CLOUD_ENTITY, List.of(EN_US.Cloud(EN_US.Breath(EN_US.Dragon()))));
	}
	public static void RegisterMobs() {
		//<editor-fold desc="Backport">
		Register("minecraft:camel", CAMEL_ENTITY, List.of(EN_US.Camel()));
		FabricDefaultAttributeRegistry.register(CAMEL_ENTITY, CamelEntity.createCamelAttributes());

		Register("minecraft:frog", FROG_ENTITY, List.of(Words.Frog));
		FabricDefaultAttributeRegistry.register(FROG_ENTITY, FrogEntity.createFrogAttributes());
		SpawnRestrictionAccessor.callRegister(FROG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrogEntity::canSpawn);
		Register("minecraft:tadpole", TADPOLE_ENTITY, List.of(EN_US.Tadpole()));
		FabricDefaultAttributeRegistry.register(TADPOLE_ENTITY, TadpoleEntity.createTadpoleAttributes());
		SpawnRestrictionAccessor.callRegister(TADPOLE_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TadpoleEntity::canSpawn);

		Register("minecraft:sniffer", SNIFFER_ENTITY, List.of(EN_US.Sniffer()));
		FabricDefaultAttributeRegistry.register(SNIFFER_ENTITY, SnifferEntity.createSnifferAttributes());

		Register("minecraft:warden", WARDEN_ENTITY, List.of(EN_US.Warden()));
		FabricDefaultAttributeRegistry.register(WARDEN_ENTITY, WardenEntity.addAttributes());
		SpawnRestrictionAccessor.callRegister(WARDEN_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WardenEntity::canMobSpawn);
		//</editor-fold>

		//<editor-fold desc="Chicken Variants">
		Register("fancy_chicken", FANCY_CHICKEN_ENTITY, List.of(EN_US.Chicken(EN_US.Fancy())));
		FabricDefaultAttributeRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntity.createChickenAttributes());
		SpawnRestrictionAccessor.callRegister(FANCY_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

		Register("slime_chicken", SLIME_CHICKEN_ENTITY, List.of(EN_US.Slicken()));
		FabricDefaultAttributeRegistry.register(SLIME_CHICKEN_ENTITY, SlimeChickenEntity.createChickenAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Cow Variants">
		Register("blue_mooshroom", BLUE_MOOSHROOM_ENTITY, List.of(EN_US.Mooshroom(Words.Blue)));
		FabricDefaultAttributeRegistry.register(BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(BLUE_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlueMooshroomEntity::canSpawn);
		//Nether Mooshroom
		Register("nether_mooshroom", NETHER_MOOSHROOM_ENTITY, List.of(EN_US.Mooshroom(Words.Nether)));
		FabricDefaultAttributeRegistry.register(NETHER_MOOSHROOM_ENTITY, NetherMooshroomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(NETHER_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NetherMooshroomEntity::canSpawn);
		//Flower Cows
		Register("moobloom", MOOBLOOM_ENTITY, List.of(EN_US.Moobloom()));
		FabricDefaultAttributeRegistry.register(MOOBLOOM_ENTITY, MoobloomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOBLOOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		Register("moolip", MOOLIP_ENTITY, List.of(EN_US.Moolip()));
		FabricDefaultAttributeRegistry.register(MOOLIP_ENTITY, MoolipEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOLIP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		Register("mooblossom", MOOBLOSSOM_ENTITY, List.of(EN_US.Mooblossom()));
		FabricDefaultAttributeRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlowerCowEntity::canSpawn);
		//Slime
		Register("slime_cow", SLIME_COW_ENTITY, List.of(EN_US.Cow(Words.Slime)));
		FabricDefaultAttributeRegistry.register(SLIME_COW_ENTITY, SlimeCowEntity.createCowAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_COW_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Creeper Variants">
		Register("slime_creeper", SLIME_CREEPER_ENTITY, List.of(EN_US.Creeper(Words.Slime)));
		FabricDefaultAttributeRegistry.register(SLIME_CREEPER_ENTITY, SlimeCreeperEntity.createCreeperAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_CREEPER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Golem Variants">
		Register("melon_golem", MELON_GOLEM_ENTITY, List.of(EN_US.Golem(EN_US.Melon())));
		FabricDefaultAttributeRegistry.register(MELON_GOLEM_ENTITY, MelonGolemEntity.createMelonGolemAttributes());
		//</editor-fold>
		//<editor-fold desc="Horse Variants">
		Register("slime_horse", SLIME_HORSE_ENTITY, List.of(EN_US.Slorse()));
		FabricDefaultAttributeRegistry.register(SLIME_HORSE_ENTITY, SlimeHorseEntity.createSlimeHorseAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_HORSE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Illagers">
		Register("iceologer", ICEOLOGER_ENTITY, List.of(Words.Iceologer));
		FabricDefaultAttributeRegistry.register(ICEOLOGER_ENTITY, IceologerEntity.createSpellcasterAttributes());
		SpawnRestrictionAccessor.callRegister(ICEOLOGER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);

		Register("mountaineer", MOUNTAINEER_ENTITY, List.of(Words.Mountaineer));
		FabricDefaultAttributeRegistry.register(MOUNTAINEER_ENTITY, MountaineerEntity.createMountaineerAttributes());
		SpawnRestrictionAccessor.callRegister(MOUNTAINEER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);

		Register("mage", MAGE_ENTITY, List.of(Words.Iceologer));
		FabricDefaultAttributeRegistry.register(MAGE_ENTITY, MageEntity.createSpellcasterAttributes());
		SpawnRestrictionAccessor.callRegister(MAGE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);

		//</editor-fold>
		//<editor-fold desc="Llama Variants">
		Register("jolly_llama", JOLLY_LLAMA_ENTITY, List.of(EN_US.Llama(Words.Jolly)));
		FabricDefaultAttributeRegistry.register(JOLLY_LLAMA_ENTITY, LlamaEntity.createLlamaAttributes());
		SpawnRestrictionAccessor.callRegister(JOLLY_LLAMA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Phantom Variants">
		Register("red_phantom", RED_PHANTOM_ENTITY, List.of(EN_US.Phantom(Words.Red)));
		FabricDefaultAttributeRegistry.register(RED_PHANTOM_ENTITY, HostileEntity.createHostileAttributes());
		SpawnRestrictionAccessor.callRegister(RED_PHANTOM_ENTITY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
		//</editor-fold>
		//<editor-fold desc="Sheep Variants">
		Register("mossy_sheep", MOSSY_SHEEP_ENTITY, List.of(EN_US.Sheep(EN_US.Mossy())));
		FabricDefaultAttributeRegistry.register(MOSSY_SHEEP_ENTITY, MossySheepEntity.createSheepAttributes());
		SpawnRestrictionAccessor.callRegister(MOSSY_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.CREATURE, MOSSY_SHEEP_ENTITY, 2, 2, 4);

		Register("rainbow_sheep", RAINBOW_SHEEP_ENTITY, List.of(EN_US.Sheep(Words.Rainbow)));
		FabricDefaultAttributeRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntity.createSheepAttributes());
		SpawnRestrictionAccessor.callRegister(RAINBOW_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Skeleton Variants">
		Register("mossy_skeleton", MOSSY_SKELETON_ENTITY, List.of(EN_US.Skeleton(Words.Mossy)));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, MOSSY_SKELETON_ENTITY, 40, 4, 4);
		FabricDefaultAttributeRegistry.register(MOSSY_SKELETON_ENTITY, MossySkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(MOSSY_SKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		Register("slimy_skeleton", SLIMY_SKELETON_ENTITY, List.of(EN_US.Skeleton(EN_US.Slimy())));
		FabricDefaultAttributeRegistry.register(SLIMY_SKELETON_ENTITY, MossySkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(SLIMY_SKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		Register("sunken_skeleton", SUNKEN_SKELETON_ENTITY, List.of(EN_US.Skeleton(EN_US.Sunken())));
		BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.WARM_OCEANS),
				SpawnGroup.MONSTER, SUNKEN_SKELETON_ENTITY, 10, 2, 4);
		FabricDefaultAttributeRegistry.register(SUNKEN_SKELETON_ENTITY, SunkenSkeletonEntity.createAbstractSkeletonAttributes());
		SpawnRestrictionAccessor.callRegister(SUNKEN_SKELETON_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunkenSkeletonEntity::canSpawn);
		//</editor-fold>
		//<editor-fold desc="Slime Variants">
		Register("tropical_slime", TROPICAL_SLIME_ENTITY, List.of(EN_US.Slime(EN_US.Tropical())));
		FabricDefaultAttributeRegistry.register(TROPICAL_SLIME_ENTITY, HostileEntity.createHostileAttributes());
		BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.WARM_OCEANS).and(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE)),
				SpawnGroup.MONSTER, TROPICAL_SLIME_ENTITY, 1, 1, 1);
		SpawnRestrictionAccessor.callRegister(TROPICAL_SLIME_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TropicalSlimeEntity::canMobSpawn);

		Register("pink_slime", PINK_SLIME_ENTITY, List.of(EN_US.Slime(Words.Pink)));
		FabricDefaultAttributeRegistry.register(PINK_SLIME_ENTITY, HostileEntity.createHostileAttributes());
		SpawnRestrictionAccessor.callRegister(PINK_SLIME_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SlimeEntity::canMobSpawn);
		//</editor-fold
		//<editor-fold desc="Spider Variants">
		Register("bone_spider", BONE_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Bone())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
				SpawnGroup.MONSTER, BONE_SPIDER_ENTITY, 60, 2, 5);
		FabricDefaultAttributeRegistry.register(BONE_SPIDER_ENTITY, BoneSpiderEntity.createBoneSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(BONE_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		Register("icy_spider", ICY_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Icy())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.ICE_SPIKES, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_MOUNTAINS),
				SpawnGroup.MONSTER, ICY_SPIDER_ENTITY, 20, 2, 5);
		FabricDefaultAttributeRegistry.register(ICY_SPIDER_ENTITY, IcySpiderEntity.createIcySpiderAttributes());
		SpawnRestrictionAccessor.callRegister(ICY_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		Register("jumping_spider", JUMPING_SPIDER_ENTITY, List.of(EN_US.Spider(EN_US.Jumping())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DESERT,
						BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE,
						BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.WOODED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU),
				SpawnGroup.MONSTER, JUMPING_SPIDER_ENTITY, 40, 1, 2);
		FabricDefaultAttributeRegistry.register(JUMPING_SPIDER_ENTITY, JumpingSpiderEntity.createJumpingSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(JUMPING_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		Register("slime_spider", SLIME_SPIDER_ENTITY, List.of(EN_US.Slider()));
		FabricDefaultAttributeRegistry.register(SLIME_SPIDER_ENTITY, SlimeSpiderEntity.createSlimeSpiderAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_SPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
		//</editor-fold>
		//<editor-fold desc="Zombie Variants">
		Register("frozen_zombie", FROZEN_ZOMBIE_ENTITY, List.of(EN_US.Zombie(EN_US.Frozen())));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.ICE_SPIKES, BiomeKeys.SNOWY_TUNDRA,
						BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_MOUNTAINS, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.SNOWY_MOUNTAINS, BiomeKeys.SNOWY_BEACH),
				SpawnGroup.MONSTER, FROZEN_ZOMBIE_ENTITY, 40, 4, 4);
		FabricDefaultAttributeRegistry.register(FROZEN_ZOMBIE_ENTITY, FrozenZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(FROZEN_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FrozenZombieEntity::canSpawn);

		Register("jungle_zombie", JUNGLE_ZOMBIE_ENTITY, List.of(EN_US.Zombie(Words.Jungle)));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, JUNGLE_ZOMBIE_ENTITY, 30, 4, 4);
		FabricDefaultAttributeRegistry.register(JUNGLE_ZOMBIE_ENTITY, JungleZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(JUNGLE_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieEntity::canMobSpawn);

		Register("slime_zombie", SLIME_ZOMBIE_ENTITY, List.of(EN_US.Zombie(Words.Slime)));
		FabricDefaultAttributeRegistry.register(SLIME_ZOMBIE_ENTITY, SlimeZombieEntity.createZombieAttributes());
		SpawnRestrictionAccessor.callRegister(SLIME_ZOMBIE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieEntity::canMobSpawn);
		//</editor-fold>

		//<editor-fold desc="Hedgehog">
		Register("hedgehog", HEDGEHOG_ENTITY, List.of(EN_US.Hedgehog()));
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_TREE_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
				SpawnGroup.MONSTER, HEDGEHOG_ENTITY, 18, 1, 4);
		FabricDefaultAttributeRegistry.register(HEDGEHOG_ENTITY, HedgehogEntity.createHedgehogAttributes());
		SpawnRestrictionAccessor.callRegister(HEDGEHOG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Piranhas">
		Register("piranha", PIRANHA_ENTITY, List.of(Words.Piranha));
		FabricDefaultAttributeRegistry.register(PIRANHA_ENTITY, PiranhaEntity.createPiranhaAttributes());
		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.JUNGLE_EDGE, BiomeKeys.JUNGLE_HILLS, BiomeKeys.BAMBOO_JUNGLE_HILLS, BiomeKeys.MODIFIED_JUNGLE, BiomeKeys.MODIFIED_JUNGLE_EDGE, MANGROVE_SWAMP),
				SpawnGroup.WATER_AMBIENT, PIRANHA_ENTITY, 8, 1, 5);
		SpawnRestrictionAccessor.callRegister(PIRANHA_ENTITY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FishEntity::canSpawn);
		//</editor-fold>
		//<editor-fold desc="Racoon">
		Register("raccoon", RACCOON_ENTITY, List.of(EN_US.Raccoon()));
		FabricDefaultAttributeRegistry.register(RACCOON_ENTITY, RaccoonEntity.createRaccoonAttributes());
		SpawnRestrictionAccessor.callRegister(RACCOON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
		//<editor-fold desc="Red Panda">
		Register("red_panda", RED_PANDA_ENTITY, List.of(EN_US.Panda(Words.Red)));
		FabricDefaultAttributeRegistry.register(RED_PANDA_ENTITY, RedPandaEntity.createRedPandaAttributes());
		SpawnRestrictionAccessor.callRegister(RED_PANDA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		//</editor-fold>
	}
	public static void RegisterSummoningArrows() {
		//<editor-fold desc="Summoning Arrows">
		Register("axolotl_summoning_arrow", AXOLOTL_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Axolotl))));
		Register("bat_summoning_arrow", BAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Bat))));
		Register("bee_summoning_arrow", BEE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Bee))));
		Register("blaze_summoning_arrow", BLAZE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Blaze))));
		Register("camel_summoning_arrow", CAMEL_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Camel))));
		Register("cat_summoning_arrow", CAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Cat))));
		Register("cave_spider_summoning_arrow", CAVE_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(Words.Cave)))));
		Register("chicken_summoning_arrow", CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Chicken))));
		Register("cod_summoning_arrow", COD_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Cod))));
		Register("cow_summoning_arrow", COW_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Cow))));
		Register("creeper_summoning_arrow", CREEPER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Creeper))));
		Register("dolphin_summoning_arrow", DOLPHIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Dolphin))));
		Register("donkey_summoning_arrow", DONKEY_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Donkey))));
		Register("drowned_summoning_arrow", DROWNED_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Drowned))));
		Register("elder_guardian_summoning_arrow", ELDER_GUARDIAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Guardian(Words.Elder)))));
		Register("enderman_summoning_arrow", ENDERMAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Enderman))));
		Register("endermite_summoning_arrow", ENDERMITE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Endermite))));
		Register("evoker_summoning_arrow", EVOKER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Evoker))));
		Register("fox_summoning_arrow", FOX_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Fox))));
		Register("frog_summoning_arrow", FROG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Frog))));
		Register("ghast_summoning_arrow", GHAST_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Ghast))));
		Register("giant_summoning_arrow", GIANT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Giant))));
		Register("glow_squid_summoning_arrow", GLOW_SQUID_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Squid(Words.Glow)))));
		Register("goat_summoning_arrow", GOAT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Goat))));
		Register("guardian_summoning_arrow", GUARDIAN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Guardian))));
		Register("hoglin_summoning_arrow", HOGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Hoglin))));
		Register("horse_summoning_arrow", HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Horse))));
		Register("husk_summoning_arrow", HUSK_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Husk))));
		Register("iron_golem_summoning_arrow", IRON_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(Words.Iron)))));
		Register("llama_summoning_arrow", LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Llama))));
		Register("magma_cube_summoning_arrow", MAGMA_CUBE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cube(Words.Magma)))));
		Register("mooshroom_summoning_arrow", MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Mooshroom))));
		Register("mule_summoning_arrow", MULE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Mule))));
		Register("ocelot_summoning_arrow", OCELOT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Ocelot))));
		Register("panda_summoning_arrow", PANDA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Panda))));
		Register("parrot_summoning_arrow", PARROT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Parrot))));
		Register("phantom_summoning_arrow", PHANTOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Phantom))));
		Register("pig_summoning_arrow", PIG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Pig))));
		Register("piglin_summoning_arrow", PIGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Piglin))));
		Register("piglin_brute_summoning_arrow", PIGLIN_BRUTE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Brute(Words.Piglin)))));
		Register("pillager_summoning_arrow", PILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Pillager))));
		Register("polar_bear_summoning_arrow", POLAR_BEAR_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Bear(Words.Polar)))));
		Register("pufferfish_summoning_arrow", PUFFERFISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Pufferfish))));
		Register("rabbit_summoning_arrow", RABBIT_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Rabbit))));
		Register("ravager_summoning_arrow", RAVAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Ravager))));
		Register("salmon_summoning_arrow", SALMON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Salmon))));
		Register("sheep_summoning_arrow", SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Sheep))));
		Register("shulker_summoning_arrow", SHULKER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Shulker))));
		Register("silverfish_summoning_arrow", SILVERFISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Silverfish))));
		Register("skeleton_summoning_arrow", SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Skeleton))));
		Register("skeleton_horse_summoning_arrow", SKELETON_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Horse(Words.Skeleton)))));
		Register("slime_summoning_arrow", SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Slime))));
		Register("sniffer_summoning_arrow", SNIFFER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Sniffer))));
		Register("snow_golem_summoning_arrow", SNOW_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(Words.Snow)))));
		Register("spider_summoning_arrow", SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Spider))));
		Register("squid_summoning_arrow", SQUID_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Squid))));
		Register("stray_summoning_arrow", STRAY_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Stray))));
		Register("strider_summoning_arrow", STRIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Strider))));
		Register("tadpole_summoning_arrow", TADPOLE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Tadpole))));
		Register("trader_llama_summoning_arrow", TRADER_LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Llama(Words.Trader)))));
		Register("tropical_fish_summoning_arrow", TROPICAL_FISH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Fish(Words.Tropical)))));
		Register("turtle_summoning_arrow", TURTLE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Turtle))));
		Register("vex_summoning_arrow", VEX_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Vex))));
		Register("villager_summoning_arrow", VILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Villager))));
		Register("vindicator_summoning_arrow", VINDICATOR_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Vindicator))));
		Register("wandering_trader_summoning_arrow", WANDERING_TRADER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Trader(Words.Wandering)))));
		Register("warden_summoning_arrow", WARDEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Warden))));
		Register("witch_summoning_arrow", WITCH_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Witch))));
		Register("wither_summoning_arrow", WITHER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Wither))));
		Register("wither_skeleton_summoning_arrow", WITHER_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(Words.Wither)))));
		Register("wolf_summoning_arrow", WOLF_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Wolf))));
		Register("zoglin_summoning_arrow", ZOGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Zoglin))));
		Register("zombie_summoning_arrow", ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Zombie))));
		Register("zombie_horse_summoning_arrow", ZOMBIE_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Horse(Words.Zombie)))));
		Register("zombie_villager_summoning_arrow", ZOMBIE_VILLAGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Villager(Words.Zombie)))));
		Register("zombie_piglin_summoning_arrow", ZOMBIFIED_PIGLIN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Piglin(Words.Zombified)))));
		//</editor-fold>
		//<editor-fold desc="Mod Mob Summoning Arrows">
		Register("melon_golem_summoning_arrow", MELON_GOLEM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Golem(EN_US.Melon())))));
		Register("bone_spider_summoning_arrow", BONE_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Bone())))));
		Register("icy_spider_summoning_arrow", ICY_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Icy())))));
		Register("slime_spider_summoning_arrow", SLIME_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slider()))));
		Register("hedgehog_summoning_arrow", HEDGEHOG_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Hedgehog()))));
		Register("raccoon_summoning_arrow", RACCOON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Raccoon()))));
		Register("red_panda_summoning_arrow", RED_PANDA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Panda(Words.Red)))));
		Register("jumping_spider_summoning_arrow", JUMPING_SPIDER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Spider(EN_US.Jumping())))));
		Register("red_phantom_summoning_arrow", RED_PHANTOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Phantom(Words.Red)))));
		Register("piranha_summoning_arrow", PIRANHA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Piranha))));
		Register("fancy_chicken_summoning_arrow", FANCY_CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Chicken(EN_US.Fancy())))));
		Register("blue_mooshroom_summoning_arrow", BLUE_MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooshroom(Words.Blue)))));
		Register("nether_mooshroom_summoning_arrow", NETHER_MOOSHROOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooshroom(Words.Nether)))));
		Register("moobloom_summoning_arrow", MOOBLOOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Moobloom()))));
		Register("moolip_summoning_arrow", MOOLIP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Moolip()))));
		Register("mooblossom_summoning_arrow", MOOBLOSSOM_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Mooblossom()))));
		Register("mossy_sheep_summoning_arrow", MOSSY_SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sheep(Words.Mossy)))));
		Register("rainbow_sheep_summoning_arrow", RAINBOW_SHEEP_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Sheep(Words.Rainbow)))));
		Register("slime_creeper_summoning_arrow", SLIME_CREEPER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Creeper(Words.Slime)))));
		Register("mossy_skeleton_summoning_arrow", MOSSY_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(Words.Mossy)))));
		Register("slimy_skeleton_summoning_arrow", SLIMY_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Slimy())))));
		Register("sunken_skeleton_summoning_arrow", SUNKEN_SKELETON_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Skeleton(EN_US.Sunken())))));
		Register("tropical_slime_summoning_arrow", TROPICAL_SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slime(EN_US.Tropical())))));
		Register("pink_slime_summoning_arrow", PINK_SLIME_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slime(Words.Pink)))));
		Register("slime_chicken_summoning_arrow", SLIME_CHICKEN_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slicken()))));
		Register("slime_cow_summoning_arrow", SLIME_COW_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Cow(Words.Slime)))));
		Register("slime_horse_summoning_arrow", SLIME_HORSE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Slorse()))));
		Register("frozen_zombie_summoning_arrow", FROZEN_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(EN_US.Frozen())))));
		Register("jungle_zombie_summoning_arrow", JUNGLE_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(Words.Jungle)))));
		Register("slime_zombie_summoning_arrow", SLIME_ZOMBIE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Zombie(Words.Slime)))));
		Register("iceologer_summoning_arrow", ICEOLOGER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Iceologer))));
		Register("mountaineer_summoning_arrow", MOUNTAINEER_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Mountaineer))));
		Register("mage_summoning_arrow", MAGE_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(Words.Mage))));
		Register("jolly_llama_summoning_arrow", JOLLY_LLAMA_SUMMONING_ARROW, List.of(EN_US.Arrow(EN_US.Summoning(EN_US.Llama(Words.Jolly)))));
		//</editor-fold>
	}
	public static void RegisterSpawnEggs() {
		//<editor-fold desc="Spawn Eggs">
		Register("minecraft:frog_spawn_egg", FROG_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Frog))));
		Register("minecraft:tadpole_spawn_egg", TADPOLE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Tadpole()))));
		Register("minecraft:warden_spawn_egg", WARDEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Warden()))));
		Register("minecraft:camel_spawn_egg", CAMEL_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Camel()))));
		Register("minecraft:sniffer_spawn_egg", SNIFFER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sniffer()))));
		//</editor-fold>
		//<editor-fold desc="Mod Mob Spawn Eggs">
		Register("bone_spider_spawn_egg", BONE_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(Words.Bone)))));
		Register("icy_spider_spawn_egg", ICY_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(Words.Icy)))));
		Register("slime_spider_spawn_egg", SLIME_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Slider))));
		Register("hedgehog_spawn_egg", HEDGEHOG_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Hedgehog))));
		Register("raccoon_spawn_egg", RACCOON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Raccoon))));
		Register("red_panda_spawn_egg", RED_PANDA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Panda(Words.Red)))));
		Register("jumping_spider_spawn_egg", JUMPING_SPIDER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Spider(Words.Jumping)))));
		Register("red_phantom_spawn_egg", RED_PHANTOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Phantom(Words.Red)))));
		Register("piranha_spawn_egg", PIRANHA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Piranha))));
		Register("fancy_chicken_spawn_egg", FANCY_CHICKEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Chicken(Words.Fancy)))));
		Register("blue_mooshroom_spawn_egg", BLUE_MOOSHROOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooshroom(Words.Blue)))));
		Register("nether_mooshroom_spawn_egg", NETHER_MOOSHROOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooshroom(Words.Nether)))));
		Register("moobloom_spawn_egg", MOOBLOOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Moobloom()))));
		Register("moolip_spawn_egg", MOOLIP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Moolip()))));
		Register("mooblossom_spawn_egg", MOOBLOSSOM_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Mooblossom()))));
		Register("mossy_sheep_spawn_egg", MOSSY_SHEEP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sheep(Words.Mossy)))));
		Register("rainbow_sheep_spawn_egg", RAINBOW_SHEEP_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Sheep(Words.Rainbow)))));
		Register("slime_creeper_spawn_egg", SLIME_CREEPER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Creeper(Words.Slime)))));
		Register("mossy_skeleton_spawn_egg", MOSSY_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(Words.Mossy)))));
		Register("slimy_skeleton_spawn_egg", SLIMY_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(EN_US.Slimy())))));
		Register("sunken_skeleton_spawn_egg", SUNKEN_SKELETON_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Skeleton(EN_US.Sunken())))));
		Register("tropical_slime_spawn_egg", TROPICAL_SLIME_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slime(EN_US.Tropical())))));
		Register("pink_slime_spawn_egg", PINK_SLIME_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slime(Words.Pink)))));
		Register("slime_chicken_spawn_egg", SLIME_CHICKEN_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Slicken()))));
		Register("slime_cow_spawn_egg", SLIME_COW_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Cow(Words.Slime)))));
		Register("slime_horse_spawn_egg", SLIME_HORSE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Slorse))));
		Register("frozen_zombie_spawn_egg", FROZEN_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(Words.Frozen)))));
		Register("jungle_zombie_spawn_egg", JUNGLE_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(Words.Jungle)))));
		Register("slime_zombie_spawn_egg", SLIME_ZOMBIE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Zombie(Words.Slime)))));
		Register("iceologer_spawn_egg", ICEOLOGER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Iceologer))));
		Register("mountaineer_spawn_egg", MOUNTAINEER_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Mountaineer))));
		Register("mage_spawn_egg", MAGE_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(Words.Mage))));
		Register("jolly_llama_spawn_egg", JOLLY_LLAMA_SPAWN_EGG, List.of(EN_US.Egg(EN_US.Spawn(EN_US.Llama(Words.Jolly)))));
		//</editor-fold>
		//Add dispenser behavior (will override existing spawn egg behaviors but those really shouldn't be different anyway????
		ItemDispenserBehavior itemDispenserBehavior = new ItemDispenserBehavior() {
			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entityType = ((SpawnEggItem)stack.getItem()).getEntityType(stack.getNbt());
				try {
					entityType.spawnFromItemStack(pointer.getWorld(), stack, null, pointer.getPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				}
				catch (Exception exception) {
					//LOGGER.error("Error while dispensing spawn egg from dispenser at {}", pointer.getPos(), exception);
					return ItemStack.EMPTY;
				}
				stack.decrement(1);
				pointer.getWorld().emitGameEvent(GameEvent.ENTITY_PLACE, pointer.getPos());
				return stack;
			}
		};
		for (SpawnEggItem spawnEggItem : SpawnEggItem.getAll()) {
			DispenserBlock.registerBehavior(spawnEggItem, itemDispenserBehavior);
		}
	}
}
