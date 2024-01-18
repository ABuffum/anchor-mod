package fun.mousewich.gen.data.language;

import fun.mousewich.ModConfig;
import fun.mousewich.ModGameRules;
import fun.mousewich.ModId;
import fun.mousewich.gen.data.fabric.FabricLanguageProvider;
import fun.mousewich.registry.ModBannerPatterns;
import fun.mousewich.util.dye.ModDyeColor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.DyeColor;

import java.util.Map;

import static fun.mousewich.ModBase.*;

public class EnglishLanguageProvider extends FabricLanguageProvider {
	public EnglishLanguageProvider(FabricDataGenerator dataGenerator) { super(dataGenerator, "en_us"); }

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		builder.add("category." + ModId.NAMESPACE, "Wich Pack");
		//Item Groups
		builder.add(ITEM_GROUP, "Wich Pack");
		builder.add("itemGroup." + ModId.NAMESPACE + ".flowers", EN_US.Flowers("Wich Pack"));
		builder.add("itemGroup." + ModId.NAMESPACE + ".decorative_blocks", "Wich Pack Decoration-Only Blocks");
		builder.add("itemGroup." + ModId.NAMESPACE + ".plushies", "Wich Pack Plushies");
		//Game Rules
		builder.add(ModGameRules.DO_RED_PHANTOM_SPAWNING, EN_US.Phantoms(EN_US.Red(EN_US.Spawn())));
		builder.add(ModGameRules.DO_VINES_SPREAD, EN_US.Vines() + " spread", "Controls whether or not the " + EN_US.Vines() + " block spreads randomly to adjacent blocks. Does not affect other type of vine blocks such as Weeping Vines, Twisting Vines, etc.");
		builder.add(ModGameRules.DO_WARDEN_BLINDNESS, "Apply " + EN_US.Blindness() + " effect around " + EN_US.Wardens(), "Controls whether or not " + EN_US.Wardens() + " apply the " + EN_US.Blindness() + " effect to nearby players. This is a temporary fix while this mod's developer figures out how to stop shader packs from disabling the " + EN_US.Darkness() + " Effect.");
		builder.add(ModGameRules.DO_WARDEN_DARKNESS, "Apply " + EN_US.Darkness() + " effect around " + EN_US.Wardens(), "Controls whether or not " + EN_US.Wardens() + " apply the " + EN_US.Darkness() + " effect to nearby players.");
		builder.add(ModGameRules.DO_WARDEN_SPAWNING, EN_US.Wardens(EN_US.Spawn()));
		//Containers
		builder.add("container.woodcutter", EN_US.Woodcutter());
		builder.add("container.trimming", EN_US.Trimming());
		builder.add("container.upgrade.missing_template_tooltip", EN_US.here(EN_US.Template(EN_US.Smithing(EN_US.a(EN_US.Put())))));
		//Stats
		builder.add("stat." + ModId.NAMESPACE + ".interact_with_trimming_table", EN_US.Table(EN_US.Trimming(EN_US.with(EN_US.Interactions()))));
		builder.add("stat." + ModId.NAMESPACE + ".interact_with_faceting_table", EN_US.Table(EN_US.Faceting(EN_US.with(EN_US.Interactions()))));
		//<editor-fold desc="Advancements">
		builder.addAdvancement("advancements.husbandry.tadpole_in_a_bucket", "Bukkit Bukkit", "Catch a Tadpole in a Bucket");
		builder.addAdvancement("advancements.husbandry.froglights", "With Our Powers Combined!", "Have all Froglights in your inventory");
		builder.addAdvancement("advancements.husbandry.leash_all_frog_variants", "When the Squad Hops into Town", "Get each Frog variant on a Lead");
		builder.addAdvancement("advancements.husbandry.allay_deliver_item_to_player", "You've Got a Friend in Me", "Have an Allay deliver items to you");
		builder.addAdvancement("advancements.husbandry.allay_deliver_cake_to_note_block", "Birthday Song", "Have an Allay drop a Cake at a Note Block");
		builder.addAdvancement("advancements.adventure.avoid_vibration", "Sneak 100", "Sneak near a Sculk Sensor or Warden to prevent it from detecting you");
		builder.addAdvancement("advancements.adventure.kill_mob_near_sculk_catalyst", "It Spreads", "Kill a mob near a Sculk Catalyst");
		//</editor-fold>
		//<editor-fold desc="Item descriptions">
		builder.add("item.minecraft.music_disc_otherside.desc", "Lena Raine - otherside");
		builder.add("item.minecraft.music_disc_5.desc", "Samuel Åberg - 5");
		builder.add("item.minecraft.disc_fragment_5.desc", "Music Disc - 5");
		builder.add("item.minecraft.music_disc_relic.desc", "Aaron Cherof - Relic");
		//</editor-fold>
		//<editor-fold desc="Custom Banner Patterns">
		builder.add("item." + ModId.NAMESPACE + ".club_banner_pattern.desc", EN_US.Club());
		builder.add("item." + ModId.NAMESPACE + ".diamond_banner_pattern.desc", EN_US.Diamond());
		builder.add("item." + ModId.NAMESPACE + ".heart_banner_pattern.desc", EN_US.Heart());
		builder.add("item." + ModId.NAMESPACE + ".spade_banner_pattern.desc", EN_US.Spade());
		for (DyeColor color : DyeColor.values()) {
			builder.add("block." + ModId.NAMESPACE + ".banner." + ModBannerPatterns.CLUB.getName() + "." + color.getName(), EN_US.Club(EN_US.Color(color)));
			builder.add("block." + ModId.NAMESPACE + ".banner." + ModBannerPatterns.DIAMOND.getName() + "." + color.getName(), EN_US.Diamond(EN_US.Color(color)));
			builder.add("block." + ModId.NAMESPACE + ".banner." + ModBannerPatterns.HEART.getName() + "." + color.getName(), EN_US.Heart(EN_US.Color(color)));
			builder.add("block." + ModId.NAMESPACE + ".banner." + ModBannerPatterns.SPADE.getName() + "." + color.getName(), EN_US.Spade(EN_US.Color(color)));
		}
		//</editor-fold>
		//<editor-fold desc="Goat Horn">
		builder.add("instrument.minecraft.ponder_goat_horn", EN_US.Ponder());
		builder.add("instrument.minecraft.sing_goat_horn", "Sing");
		builder.add("instrument.minecraft.seek_goat_horn", "Seek");
		builder.add("instrument.minecraft.feel_goat_horn", "Feel");
		builder.add("instrument.minecraft.admire_goat_horn", EN_US.Admire());
		builder.add("instrument.minecraft.call_goat_horn", EN_US.Call());
		builder.add("instrument.minecraft.yearn_goat_horn", "Yearn");
		builder.add("instrument.minecraft.dream_goat_horn", EN_US.Dream());
		builder.add("subtitles.item.goat_horn.play", EN_US.plays(EN_US.Horn(EN_US.Goat())));
		//</editor-fold>
		//<editor-fold desc="Smithing Templates">
		builder.add("item.minecraft.smithing_template", EN_US.Template(EN_US.Smithing()));
		builder.add("item.minecraft.smithing_template.applies_to", "Applies to:");
		builder.add("item.minecraft.smithing_template.armor_trim.additions_slot_description", "Add ingot or crystal");
		builder.add("item.minecraft.smithing_template.armor_trim.applies_to", EN_US.Armor());
		builder.add("item.minecraft.smithing_template.armor_trim.base_slot_description", "Add a piece of armor");
		builder.add("item.minecraft.smithing_template.armor_trim.ingredients", "Ingots & Crystals");
		builder.add("item.minecraft.smithing_template.ingredients", "Ingredients:");
		builder.add("item.minecraft.smithing_template.netherite_upgrade.additions_slot_description", EN_US.Ingot(EN_US.Netherite(EN_US.Add())));
		builder.add("item.minecraft.smithing_template.netherite_upgrade.applies_to", EN_US.Equipment(EN_US.Diamond()));
		builder.add("item.minecraft.smithing_template.netherite_upgrade.base_slot_description", "Add diamond armor, weapon or tool");
		builder.add("item.minecraft.smithing_template.netherite_upgrade.ingredients", EN_US.Ingot(EN_US.Netherite()));
		builder.add("item.minecraft.smithing_template.upgrade", "Upgrade: ");
		//</editor-fold>
		//<editor-fold desc="Trim Patterns">
		builder.add("trim_pattern.minecraft.coast", EN_US.Trim(EN_US.Armor(EN_US.Coast())));
		builder.add("trim_pattern.minecraft.dune", EN_US.Trim(EN_US.Armor(EN_US.Dune())));
		builder.add("trim_pattern.minecraft.eye", EN_US.Trim(EN_US.Armor(EN_US.Eye())));
		builder.add("trim_pattern.minecraft.rib", EN_US.Trim(EN_US.Armor(EN_US.Rib())));
		builder.add("trim_pattern.minecraft.sentry", EN_US.Trim(EN_US.Armor(EN_US.Sentry())));
		builder.add("trim_pattern.minecraft.snout", EN_US.Trim(EN_US.Armor(EN_US.Snout())));
		builder.add("trim_pattern.minecraft.spire", EN_US.Trim(EN_US.Armor(EN_US.Spire())));
		builder.add("trim_pattern.minecraft.tide", EN_US.Trim(EN_US.Armor(EN_US.Tide())));
		builder.add("trim_pattern.minecraft.vex", EN_US.Trim(EN_US.Armor(EN_US.Vex())));
		builder.add("trim_pattern.minecraft.ward", EN_US.Trim(EN_US.Armor(EN_US.Ward())));
		builder.add("trim_pattern.minecraft.wild", EN_US.Trim(EN_US.Armor(EN_US.Wild())));
		//</editor-fold>
		//Mod Dye Colors
		for (ModDyeColor color : ModDyeColor.ALL_VALUES) {
			builder.add("item." + ModId.NAMESPACE + ".firework_star." + color.getName(), EN_US.Color(color));
		}
		//Wind Horn
		builder.add("subtitles.item.wind_horn.play", EN_US.plays(EN_US.Horn(EN_US.Wind())));
		//Keybinds
		builder.add("key." + ModId.NAMESPACE + ".tertiary_active", "Active Origins Power (3rd)");
		builder.add("key." + ModId.NAMESPACE + ".quaternary_active", "Active Origins Power (4th)");
		builder.add("key." + ModId.NAMESPACE + ".quinary_active", "Active Origins Power (5th)");
		//Biomes
		builder.add(DEEP_DARK, EN_US.Dark(EN_US.Deep()));
		builder.add(MANGROVE_SWAMP, EN_US.Swamp(EN_US.Mangrove()));
		builder.add(CHERRY_GROVE, EN_US.Grove(EN_US.Cherry()));
		//Subtitles
		provideSubtitles(builder);
		//Death messages
		provideDeathMessages(builder);
		//Status Effects
		for (int i = 11; i <= 32; i++) builder.add("enchantment.level." + i, ToRomanNumeral(i));
		//Registry
		applyCache(builder, EN_US);
	}

	private String ToRomanNumeral(int number) {
		StringBuilder result = new StringBuilder();
		if (number < 0) {
			result.append('-');
			number = -number;
		}
		int[] digitsValues = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
		String[] romanDigits = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
		while (number > 0) {
			for (int i = digitsValues.length - 1; i >= 0; i--) {
				if (number / digitsValues[i] >= 1) {
					number -= digitsValues[i];
					result.append(romanDigits[i]);
					break;
				}
			}
		}
		return result.toString();
	}

	public void applyCache(TranslationBuilder builder, ModLanguageCache cache) {
		for (Map.Entry<String, String> entry : cache.TranslationKeys.entrySet()) builder.add(entry.getKey(), entry.getValue());
		cache.TranslationKeys.clear();
	}
	
	public void provideDeathMessages(TranslationBuilder builder) {
		//<editor-fold desc="Warden">
		builder.add("death.attack.sonic_boom", "%1$s was obliterated by a sonically-charged shriek");
		builder.add("death.attack.sonic_boom.item", "%1$s was obliterated by a sonically-charged shriek whilst trying to escape %2$s wielding %3$s");
		builder.add("death.attack.sonic_boom.player", "%1$s was obliterated by a sonically-charged shriek whilst trying to escape %2$s");
		//</editor-fold>
		//<editor-fold desc="Misc">
		builder.add("death.attack.die_instantly", "%1$s drank potion of they die are killed by instantly");
		builder.add("death.attack.die_instantly.player", "%1$s drank concentrated death to escape %2$s... it went about how you'd expect");
		builder.add("death.attack.ichor", "%1$s succumbed to the ichor");
		builder.add("death.attack.ichor.player", "%1$s succumbed to the ichor whilst fighting %2$s");
		builder.add("death.attack.deterioration", "%1$s lost integrity");
		builder.add("death.attack.deterioration.player", "%2$s added insult to %1$s's injury");
		builder.add("death.attack.bone_rot", "%1$s succumbed to bone rot");
		builder.add("death.attack.bone_rot.player", "%1$s's rotting bones couldn't stand up to %2$s");
		builder.add("death.attack.bone_shard", "%1$s was spiked by %2$s");
		builder.add("death.attack.bone_shard.item", "%1$s was spiked by %2$s using %3$s");
		builder.add("death.attack.bleeding", "%1$s bled out");
		builder.add("death.attack.bleeding.player", "%1$s bled out whilst fighting %2$s");
		builder.add("death.attack.leeching", "%1$s had their lifeforce drained");
		builder.add("death.attack.leeching.player", "%1$s had their lifeforce drained by %2$s");
		builder.add("death.attack.melon_seeded", "%1$s was meloned to death by %2$s");
		builder.add("death.attack.melon_seeded.item", "%1$s was meloned to death by %2$s using %3$s");
		builder.add("death.attack.quills", "%1$s was quilled");
		builder.add("death.attack.quills.item", "%1$s was quilled whilst trying to escape %2$s wielding %3$s");
		builder.add("death.attack.quills.player", "%1$s was quilled whilst trying to escape %2$s");
		builder.add("death.attack.suffocate", "%1$s suffocated");
		builder.add("death.attack.suffocate.player", "%1$s couldn't breathe whilst fighting %2$s");
		builder.add("death.attack.ice_chunk", "%1$s was crushed by falling ice");;
		builder.add("death.attack.ice_chunk.player", "%1$s chose the avalanche over fighting %2$s");
		builder.add("death.attack.withering", "%1$s withered away");
		builder.add("death.attack.withering.player", "%1$s withered away whilst fighting %2$s");
		//</editor-fold>
		//<editor-fold desc="Syringes">
		builder.add("death.injected.blood_taken", "%1$s donated too much blood");
		builder.add("death.injected.blood_taken.player", "%1$s gave %2$s too much blood");
		builder.add("death.injected.default", "%1$s injected themselves with %2$s blood");
		builder.add("death.injected.default.player", "%1$s responded poorly to %2$s's experiment");
		builder.add("death.injected.bee_enderman_blood", "%1$s inj3ct3d th3ms3lv3s with b33-3nd3rman hybrid bl00d");
		builder.add("death.injected.bee_enderman_blood.player", "%1$s was inj3ct3d with b33-3nd3rman hybrid bl00d by %2$s");
		builder.add("death.injected.chorus", "%1$s injected themselves with chorus");
		builder.add("death.injected.chorus.player", "%1$s was injected with chorus by %2$s");
		builder.add("death.injected.creeper_blood", "%1$s injected themselves with creeper blood (aw man)");
		builder.add("death.injected.creeper_blood.player", "%1$s was injected with creeper blood by %2$s");
		builder.add("death.injected.diseased_blood", "%1$s injected themselves with some particularly nasty blood");
		builder.add("death.injected.diseased_blood.player", "%1$s was injected with diseased blood by %2$s");
		builder.add("death.injected.confetti", "%1$s injected themselves with confetti");
		builder.add("death.injected.confetti.player", "%1$s received unwelcome confetti from %2$s");
		builder.add("death.injected.confetti_as_clown", "%1$s tried and failed to put their confetti back");
		builder.add("death.injected.confetti_as_clown.player", "%2$s put the confetti back into %1$s");
		builder.add("death.injected.dragon_blood", "%1$s couldn't handle dragon blood");
		builder.add("death.injected.dragon_blood.player", "%1$s was injected with dragon blood by %2$s");
		builder.add("death.injected.dragon_breath", "%1$s's blood boiled in dragon breath");
		builder.add("death.injected.dragon_breath.player", "%1$s's blood boiled in dragon breath thanks to %2$s");
		builder.add("death.injected.goat_blood", "%1$s was not the goat");
		builder.add("death.injected.goat_blood.player", "%1$s was injected with goat blood by %2$s");
		builder.add("death.injected.honey", "%1$s died sweet as honey");
		builder.add("death.injected.honey.player", "%1$s was injected with honey by %2$s");
		builder.add("death.injected.ichor", "%1$s injected themselves with ichor... gross");
		builder.add("death.injected.ichor.player", "%1$s was injected with ichor by %2$s");
		builder.add("death.injected.lava", "%1$s injected themselves with lava");
		builder.add("death.injected.lava.player", "%1$s was injected with lava by %2$s");
		builder.add("death.injected.magma_cream", "%1$s injected themselves with magma cream");
		builder.add("death.injected.magma_cream.player", "%1$s was injected with magma cream by %2$s");
		builder.add("death.injected.mud", "%1$s injected themselves with mud");
		builder.add("death.injected.mud.player", "%1$s was injected with mud by %2$s");
		builder.add("death.injected.milk", "%1$s injected themselves with milk");
		builder.add("death.injected.milk.player", "%1$s was injected with milk by %2$s");
		builder.add("death.injected.nether_royalty_blood", "%1$s was unfit for royal blood");
		builder.add("death.injected.nether_royalty_blood.player", "%1$s died a peasant before %2$s");
		builder.add("death.injected.sheep_blood", "%1$s injected themselves with sheep blood");
		builder.add("death.injected.sheep_blood.player", "%1$s was a bit sheepish about %2$s's needle");
		builder.add("death.injected.slime", "%1$s clogged their veins with slime");
		builder.add("death.injected.slime.player", "%1$s was stuck down by %2$s");
		builder.add("death.injected.sludge", "%1$s injected themselves with sludge");
		builder.add("death.injected.sludge.player", "%1$s was injected with sludge by %2$s");
		builder.add("death.injected.sugar_water", "%1$s injected themselves with sugar water... why?");
		builder.add("death.injected.sugar_water.player", "%1$s died with high blood sugar thanks to %2$s");
		builder.add("death.injected.water", "%1$s succumbed to water poisoning");
		builder.add("death.injected.water.player", "%1$s was a bit too hydrated by %2$s");
		//</editor-fold>
		//<editor-fold desc="Drinking">
		builder.add("death.drank.honey", "%1$s died drinking honey");
		builder.add("death.drank.honey.player", "%1$s took their last smackeral whilst fighting %2$s");
		builder.add("death.drank.ichor", "%1$s drank something no one should");
		builder.add("death.drank.ichor.player", "%1$s took the gross way out whilst fighting %2$s");
		builder.add("death.drank.ichor_as_anemic", "Boyfriend privileges have not made %1$s immune to drinking awful shit");
		builder.add("death.drank.ichor_as_anemic.player", "%1$s took the gross way out whilst fighting %2$s");
		builder.add("death.drank.ichor_as_vampire", "%1$s really shouldn't have drunk ichor again. They should have learned their lesson");
		builder.add("death.drank.ichor_as_vampire.player", "%1$s took the gross way out whilst fighting %2$s");
		builder.add("death.drank.lava", "%1$s drank lava");
		builder.add("death.drank.lava.player", "%1$s drank lava whilst fighting %2$s");
		builder.add("death.drank.magma_cream", "%1$s drank magma cream");
		builder.add("death.drank.magma_cream.player", "%1$s took the creamy way out whilst fighting %2$s");
		builder.add("death.drank.milk", "%1$s is lactose-intolerant");
		builder.add("death.drank.milk.player", "%1$s took the milky way out whilst fighting %2$s");
		builder.add("death.drank.mud", "%1$s drank mud. Congrats?");
		builder.add("death.drank.mud.player", "%1$s drank mud whilst fighting %2$s");
		builder.add("death.drank.slime", "%1$s went out slurping");
		builder.add("death.drank.slime.player", "%1$s took the slimy way out whilst fighting %2$s");
		builder.add("death.drank.sludge", "Slutch");
		builder.add("death.drank.sludge.player", "%1$s died slutchy and %2$s watched");
		builder.add("death.drank.smoothie", "%1$s got that slurp, bitch");
		builder.add("death.drank.smoothie.player", "%1$s got that slurp. %2$s must be jealous");
		builder.add("death.drank.sugar_water", "%1$s's drink was too sugary");
		builder.add("death.drank.sugar_water.player", "%1$s drank sugar whilst fighting %2$s");
		//</editor-fold>
	}
	
	public void provideSubtitles(TranslationBuilder builder) {
		//<editor-fold desc="Allay">
		builder.add("subtitles.entity.allay.death", EN_US.dies(EN_US.Allay()));
		builder.add("subtitles.entity.allay.hurt", EN_US.hurts(EN_US.Allay()));
		builder.add("subtitles.entity.allay.ambient_with_item", EN_US.seeks(EN_US.Allay()));
		builder.add("subtitles.entity.allay.ambient_without_item", EN_US.yearns(EN_US.Allay()));
		builder.add("subtitles.entity.allay.item_given", EN_US.chortles(EN_US.Allay()));
		builder.add("subtitles.entity.allay.item_taken", EN_US.allays(EN_US.Allay()));
		builder.add("subtitles.entity.allay.item_thrown", EN_US.tosses(EN_US.Allay()));
		//</editor-fold>
		//<editor-fold desc="Camel">
		builder.add("subtitles.entity.camel.ambient", EN_US.grunts(EN_US.Camel()));
		builder.add("subtitles.entity.camel.dash", EN_US.yeets(EN_US.Camel()));
		builder.add("subtitles.entity.camel.dash_ready", EN_US.recovers(EN_US.Camel()));
		builder.add("subtitles.entity.camel.death", EN_US.dies(EN_US.Camel()));
		builder.add("subtitles.entity.camel.eat", EN_US.eats(EN_US.Camel()));
		builder.add("subtitles.entity.camel.hurt", EN_US.hurts(EN_US.Camel()));
		builder.add("subtitles.entity.camel.saddle", EN_US.equips(EN_US.Saddle()));
		builder.add("subtitles.entity.camel.sit", EN_US.down(EN_US.sits(EN_US.Camel())));
		builder.add("subtitles.entity.camel.stand", EN_US.up(EN_US.stands(EN_US.Camel())));
		builder.add("subtitles.entity.camel.step", EN_US.steps(EN_US.Camel()));
		builder.add("subtitles.entity.camel.step_sand", EN_US.steps(EN_US.Camel()));
		//</editor-fold>
		//<editor-fold desc="Chiseled Bookshelf">
		builder.add("subtitles.chiseled_bookshelf.insert", EN_US.placed(EN_US.Book()));
		builder.add("subtitles.chiseled_bookshelf.insert_enchanted", EN_US.placed(EN_US.book(EN_US.Enchanted())));
		builder.add("subtitles.chiseled_bookshelf.take", EN_US.taken(EN_US.Book()));
		builder.add("subtitles.chiseled_bookshelf.take_enchanted", EN_US.taken(EN_US.book(EN_US.Enchanted())));
		//</editor-fold>
		//<editor-fold desc="Frog & Tadpole">
		builder.add("subtitles.entity.frog.ambient", EN_US.croaks(EN_US.Frog()));
		builder.add("subtitles.entity.frog.death", EN_US.dies(EN_US.Frog()));
		builder.add("subtitles.entity.frog.eat", EN_US.eats(EN_US.Frog()));
		builder.add("subtitles.entity.frog.hurt", EN_US.hurts(EN_US.Frog()));
		builder.add("subtitles.entity.frog.lay_spawn", EN_US.spawn(EN_US.lays(EN_US.Frog())));
		builder.add("subtitles.entity.frog.long_jump", EN_US.jumps(EN_US.Frog()));
		builder.add("subtitles.entity.tadpole.death", EN_US.dies(EN_US.Tadpole()));
		builder.add("subtitles.entity.tadpole.flop", EN_US.flops(EN_US.Tadpole()));
		builder.add("subtitles.entity.tadpole.hurt", EN_US.hurts(EN_US.Tadpole()));
		//</editor-fold>
		//<editor-fold desc="Sculk">
		builder.add("subtitles.block.sculk.charge", EN_US.bubbles(EN_US.Sculk()));
		builder.add("subtitles.block.sculk.spread", EN_US.spreads(EN_US.Sculk()));
		builder.add("subtitles.block.sculk_catalyst.bloom", EN_US.blooms(EN_US.Catalyst(EN_US.Sculk())));
		builder.add("subtitles.block.sculk_sensor.clicking", EN_US.clicking(EN_US.starts(EN_US.Sensor(EN_US.Sculk()))));
		builder.add("subtitles.block.sculk_sensor.clicking_stop", EN_US.clicking(EN_US.stops(EN_US.Sensor(EN_US.Sculk()))));
		builder.add("subtitles.block.sculk_shrieker.shriek", EN_US.shrieks(EN_US.Shrieker(EN_US.Sculk())));
		//</editor-fold>
		//<editor-fold desc="Sniffer">
		builder.add("subtitles.entity.sniffer.death", EN_US.dies(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.digging", EN_US.digs(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.digging_stop", EN_US.up(EN_US.stands(EN_US.Sniffer())));
		builder.add("subtitles.entity.sniffer.drop_seed", EN_US.seed(EN_US.drops(EN_US.Sniffer())));
		builder.add("subtitles.entity.sniffer.eat", EN_US.eats(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.happy", EN_US.delights(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.hurt", EN_US.hurts(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.idle", EN_US.grunts(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.scenting", EN_US.scents(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.searching", EN_US.searches(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.sniffing", EN_US.sniffs(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.step", EN_US.steps(EN_US.Sniffer()));
		builder.add("subtitles.entity.sniffer.egg_crack", EN_US.cracks(EN_US.Egg(EN_US.Sniffer())));
		builder.add("subtitles.entity.sniffer.egg_hatch", EN_US.hatches(EN_US.Egg(EN_US.Sniffer())));
		//</editor-fold>
		builder.add("subtitles.block.decorated_pot.shatter", EN_US.shatters(EN_US.Pot()));
		//<editor-fold desc="Warden">
		builder.add("subtitles.entity.parrot.imitate.warden", EN_US.whines(EN_US.Parrot()));
		builder.add("subtitles.entity.warden.roar", EN_US.roars(EN_US.Warden()));
		builder.add("subtitles.entity.warden.sniff", EN_US.sniffs(EN_US.Warden()));
		builder.add("subtitles.entity.warden.emerge", EN_US.emerges(EN_US.Warden()));
		builder.add("subtitles.entity.warden.dig", EN_US.digs(EN_US.Warden()));
		builder.add("subtitles.entity.warden.hurt", EN_US.hurts(EN_US.Warden()));
		builder.add("subtitles.entity.warden.death", EN_US.dies(EN_US.Warden()));
		builder.add("subtitles.entity.warden.step", EN_US.steps(EN_US.Warden()));
		builder.add("subtitles.entity.warden.listening", EN_US.notice(EN_US.takes(EN_US.Warden())));
		builder.add("subtitles.entity.warden.listening_angry", EN_US.angrily(EN_US.notice(EN_US.takes(EN_US.Warden()))));
		builder.add("subtitles.entity.warden.heartbeat", EN_US.beats(EN_US.heart(EN_US.Warden() + "'s")));
		builder.add("subtitles.entity.warden.attack_impact", EN_US.hit(EN_US.lands(EN_US.Warden())));
		builder.add("subtitles.entity.warden.tendril_clicks", EN_US.click(EN_US.tendrils(EN_US.Warden())));
		builder.add("subtitles.entity.warden.angry", EN_US.rages(EN_US.Warden()));
		builder.add("subtitles.entity.warden.agitated", EN_US.angrily(EN_US.groans(EN_US.Warden())));
		builder.add("subtitles.entity.warden.ambient", EN_US.whines(EN_US.Warden()));
		builder.add("subtitles.entity.warden.nearby_close", EN_US.approaches(EN_US.Warden()));
		builder.add("subtitles.entity.warden.nearby_closer", EN_US.advances(EN_US.Warden()));
		builder.add("subtitles.entity.warden.nearby_closest", EN_US.close(EN_US.draws(EN_US.Warden())));
		builder.add("subtitles.entity.warden.sonic_charge", EN_US.charges(EN_US.Warden()));
		builder.add("subtitles.entity.warden.sonic_boom", EN_US.booms(EN_US.Warden()));
		//</editor-fold>
		//<editor-fold desc="Brush">
		builder.add("subtitles.item.brush.brushing.generic", EN_US.Brushing());
		builder.add("subtitles.item.brush.brushing.sand", EN_US.Sand(EN_US.Brushing()));
		builder.add("subtitles.item.brush.brushing.gravel", EN_US.Gravel(EN_US.Brushing()));
		builder.add("subtitles.item.brush.brushing.sand.complete", EN_US.completed(EN_US.Sand(EN_US.Brushing())));
		builder.add("subtitles.item.brush.brushing.gravel.complete", EN_US.completed(EN_US.Gravel(EN_US.Brushing())));
		//</editor-fold>
		//<editor-fold desc="Evoker">
		builder.add("subtitles.entity.evoker.yes", EN_US.agrees(EN_US.Evoker()));
		builder.add("subtitles.entity.evoker.no", EN_US.disagrees(EN_US.Evoker()));
		//</editor-fold>
		//<editor-fold desc="Iceologer">
		builder.add("subtitles.entity.iceologer.ambient", EN_US.murmurs(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.attack", EN_US.attacks(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.cast_spell", EN_US.spell(EN_US.casts(EN_US.Iceologer())));
		builder.add("subtitles.entity.iceologer.celebrate", EN_US.cheers(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.death", EN_US.dies(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.hurt", EN_US.hurts(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.prepare_summon", EN_US.summoning(EN_US.prepares(EN_US.Iceologer())));
		builder.add("subtitles.entity.iceologer.yes", EN_US.agrees(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.no", EN_US.disagrees(EN_US.Iceologer()));
		builder.add("subtitles.entity.iceologer.ice_fall_small", EN_US.fell(EN_US.Ice()));
		builder.add("subtitles.entity.iceologer.ice_fall_big", EN_US.hard(EN_US.fell(EN_US.Ice())));
		//</editor-fold>
		//<editor-fold desc="Illusioner">
		builder.add("subtitles.entity.illusioner.yes", EN_US.agrees(EN_US.Illusioner()));
		builder.add("subtitles.entity.illusioner.no", EN_US.disagrees(EN_US.Illusioner()));
		//</editor-fold>
		//<editor-fold desc="Mage">
		builder.add("subtitles.entity.mage.ambient", EN_US.murmurs(EN_US.Mage()));
		builder.add("subtitles.entity.mage.attack", EN_US.attacks(EN_US.Mage()));
		builder.add("subtitles.entity.mage.cast_spell", EN_US.spell(EN_US.casts(EN_US.Mage())));
		builder.add("subtitles.entity.mage.celebrate", EN_US.cheers(EN_US.Mage()));
		builder.add("subtitles.entity.mage.death", EN_US.dies(EN_US.Mage()));
		builder.add("subtitles.entity.mage.hurt", EN_US.hurts(EN_US.Mage()));
		builder.add("subtitles.entity.mage.prepare_spell", EN_US.spell(EN_US.prepares(EN_US.Mage())));
		builder.add("subtitles.entity.mage.yes", EN_US.agrees(EN_US.Mage()));
		builder.add("subtitles.entity.mage.no", EN_US.disagrees(EN_US.Mage()));
		//</editor-fold>
		//<editor-fold desc="Pillager">
		builder.add("subtitles.entity.pillager.yes", EN_US.agrees(EN_US.Pillager()));
		builder.add("subtitles.entity.pillager.no", EN_US.disagrees(EN_US.Pillager()));
		//</editor-fold>
		//<editor-fold desc="Vindicator">
		builder.add("subtitles.entity.vindicator.yes", EN_US.agrees(EN_US.Vindicator()));
		builder.add("subtitles.entity.vindicator.no", EN_US.disagrees(EN_US.Vindicator()));
		//</editor-fold>
		//<editor-fold desc="Witch">
		builder.add("subtitles.entity.witch.yes", EN_US.agrees(EN_US.Witch()));
		builder.add("subtitles.entity.witch.no", EN_US.disagrees(EN_US.Witch()));
		//</editor-fold>
		//<editor-fold desc="Villager">
		builder.add("entity.minecraft.villager.lapidary", EN_US.Lapidary());
		builder.add("subtitles.entity.villager.work_lapidary", EN_US.works(EN_US.Lapidary()));
		//</editor-fold>
		builder.add("subtitles.block.amethyst_block.resonate", EN_US.resonates(EN_US.Amethyst()));
		//<editor-fold desc="Jolly LLama">
		builder.add("subtitles.entity.jolly_llama.bell", EN_US.jingles(EN_US.Llama(EN_US.Jolly())));
		builder.add("subtitles.entity.jolly_llama.eat_fern", EN_US.Fern(EN_US.eats(EN_US.Llama(EN_US.Jolly()))));
		builder.add("subtitles.entity.jolly_llama.prance", EN_US.prances(EN_US.Llama(EN_US.Jolly())));
		//</editor-fold>

		//Nethershroom
		builder.add("subtitles.block.nethershroom.explode", EN_US.explodes(EN_US.Nethershroom()));
		builder.add("subtitles.block.nethershroom.squeeze", EN_US.squeezed(EN_US.Nethershroom()));

		//Thrown Items
		builder.add("subtitles.entity.slime.throw", EN_US.flies(EN_US.Slime()));
		//Pouch
		builder.add("subtitles.item.pouch.empty", EN_US.empties(EN_US.Pouch()));
		builder.add("subtitles.item.pouch.fill", EN_US.fills(EN_US.Pouch()));

		builder.add("item." + ModId.NAMESPACE + ".chicken_pouch.greg", EN_US.Greg(EN_US.of(EN_US.Pouch())));
		builder.add("subtitles.item.pouch.empty_chicken", EN_US.released(EN_US.Chicken()));
		builder.add("subtitles.item.pouch.fill_chicken", EN_US.bagged(EN_US.Chicken()));
		builder.add("subtitles.item.pouch.empty_chicken.greg", EN_US.deployed(EN_US.Greg()));
		builder.add("subtitles.item.pouch.fill_chicken.greg", EN_US.collected(EN_US.Greg()));
		builder.add("subtitles.item.pouch.empty_rabbit", EN_US.released(EN_US.Rabbit()));
		builder.add("subtitles.item.pouch.fill_rabbit", EN_US.bagged(EN_US.Rabbit()));
		builder.add("subtitles.item.pouch.empty_parrot", EN_US.released(EN_US.Parrot()));
		builder.add("subtitles.item.pouch.fill_parrot", EN_US.bagged(EN_US.Parrot()));
		builder.add("subtitles.item.pouch.empty_endermite", EN_US.released(EN_US.Endermite()));
		builder.add("subtitles.item.pouch.fill_endermite", EN_US.bagged(EN_US.Endermite()));
		builder.add("subtitles.item.pouch.empty_silverfish", EN_US.released(EN_US.Silverfish()));
		builder.add("subtitles.item.pouch.fill_silverfish", EN_US.bagged(EN_US.Silverfish()));

		builder.add("subtitles.block.coins.drop", EN_US.dropped(EN_US.Coins()));

		//Bone Spider
		builder.add("subtitles.entity.bone_spider.attack", EN_US.attacks(EN_US.Spider(EN_US.Bone())));
		builder.add("subtitles.entity.bone_spider.spit", EN_US.spits(EN_US.Spider(EN_US.Bone())));
		//Jumping Spider
		builder.add("subtitles.entity.jumping_spider.jump", EN_US.jumps(EN_US.Spider(EN_US.Jumping())));
		//Slime Cow
		builder.add("subtitles.entity.slime_cow.ambient", EN_US.goos(EN_US.Cow(EN_US.Slime())));
		//<editor-fold desc="Hedgehog">
		builder.add("subtitles.entity.hedgehog.ambient", EN_US.grunts(EN_US.Hedgehog()));
		builder.add("subtitles.entity.hedgehog.death", EN_US.dies(EN_US.Hedgehog()));
		builder.add("subtitles.entity.hedgehog.hurt", EN_US.hurts(EN_US.Hedgehog()));
		builder.add("subtitles.entity.hedgehog.sniff", EN_US.sniffs(EN_US.Hedgehog()));
		builder.add("subtitles.item.pouch.empty_hedgehog", EN_US.deployed(EN_US.Hedgehog()));
		builder.add("subtitles.item.pouch.fill_hedgehog", EN_US.scooped(EN_US.Hedgehog()));
		//</editor-fold>
		//<editor-fold desc="Mossy Skeleton">
		builder.add("subtitles.entity.mossy_skeleton.ambient", EN_US.rattles(EN_US.Skeleton(EN_US.Mossy())));
		builder.add("subtitles.entity.mossy_skeleton.death", EN_US.dies(EN_US.Skeleton(EN_US.Mossy())));
		builder.add("subtitles.entity.mossy_skeleton.hurt", EN_US.hurts(EN_US.Skeleton(EN_US.Mossy())));
		//</editor-fold>
		//<editor-fold desc="Sunken Skeleton">
		builder.add("subtitles.entity.skeleton.converted_to_sunken_skeleton", EN_US.Skeleton(EN_US.Sunken(EN_US.to(EN_US.converts(EN_US.Skeleton())))));
		builder.add("subtitles.entity.stray.converted_to_skeleton", EN_US.Skeleton(EN_US.into(EN_US.melts(EN_US.Stray()))));
		builder.add("subtitles.entity.sunken_skeleton.ambient", EN_US.rattles(EN_US.Skeleton(EN_US.Sunken())));
		builder.add("subtitles.entity.sunken_skeleton.death", EN_US.dies(EN_US.Skeleton(EN_US.Sunken())));
		builder.add("subtitles.entity.sunken_skeleton.hurt", EN_US.hurts(EN_US.Skeleton(EN_US.Sunken())));
		//</editor-fold>
		//<editor-fold desc="Piranha">
		builder.add("subtitles.entity.piranha.death", EN_US.dies(EN_US.Piranha()));
		builder.add("subtitles.entity.piranha.flop", EN_US.flops(EN_US.Piranha()));
		builder.add("subtitles.entity.piranha.hurt", EN_US.hurts(EN_US.Piranha()));
		//</editor-fold>
		//Copper Trapdoor
		builder.add("subtitles.block.copper_door.toggle", EN_US.creaks(EN_US.Door()));
		builder.add("subtitles.block.copper_trapdoor.open", EN_US.opens(EN_US.Trapdoor()));
		builder.add("subtitles.block.copper_trapdoor.close", EN_US.closes(EN_US.Trapdoor()));
		//Mod Blocks
		builder.add("subtitles.block.echo_block.chime", EN_US.chimes(EN_US.Crystal(EN_US.Echo())));
		builder.add("subtitles.block.echo_block.resonate", EN_US.resonates(EN_US.Crystal(EN_US.Echo())));
		builder.add("subtitles.block.juicer.loaded", EN_US.loaded(EN_US.Juicer()));
		builder.add("subtitles.block.juicer.juiced", EN_US.juiced(EN_US.Juicer()));
		builder.add("subtitles.block.trimming_table.use", EN_US.used(EN_US.Table(EN_US.Trimming())));
		//Mod Items
		builder.add("subtitles.item.chum.used", EN_US.squelches(EN_US.Chum()));
		builder.add("subtitles.item.salve.apply", EN_US.applied(EN_US.Salve()));
		builder.add("subtitles.item.syringe.inject", EN_US.injected(EN_US.Syringe()));

		if (ModConfig.REGISTER_HAVEN_MOD) {
			//TNT Variants
			builder.add("subtitles.entity.sharp_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Sharp())));
			builder.add("subtitles.entity.chunkeater_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Chunkeater())));
			builder.add("subtitles.entity.violent_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Violent())));
			builder.add("subtitles.entity.devouring_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Devouring())));
			builder.add("subtitles.entity.catalyzing_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Catalyzing())));
			builder.add("subtitles.entity.soft_tnt.primed", EN_US.fizzes(EN_US.TNT(EN_US.Soft())));
			//Misc
			builder.add("subtitles.ambient.miasma_coming", "She's coming");
		}
	}
}
