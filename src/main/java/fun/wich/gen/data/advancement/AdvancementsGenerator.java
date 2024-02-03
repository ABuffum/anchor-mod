package fun.wich.gen.data.advancement;

import fun.wich.ModBase;
import fun.wich.advancement.ModCriteria;
import fun.wich.advancement.criterion.ItemCriterion;
import fun.wich.advancement.criterion.ModTickCriterion;
import fun.wich.entity.passive.frog.FrogEntity;
import fun.wich.entity.variants.FrogVariant;
import fun.wich.gen.data.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementsProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.function.Consumer;

public class AdvancementsGenerator extends FabricAdvancementsProvider {
	public AdvancementsGenerator(FabricDataGenerator dataGenerator) { super(dataGenerator); }

	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{
			EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.SHEEP, EntityType.COW,
			EntityType.MOOSHROOM, EntityType.PIG, EntityType.CHICKEN, EntityType.WOLF, EntityType.OCELOT,
			EntityType.RABBIT, EntityType.LLAMA, EntityType.CAT, EntityType.PANDA, EntityType.FOX, EntityType.BEE,
			EntityType.HOGLIN, EntityType.STRIDER, EntityType.GOAT, EntityType.AXOLOTL, ModBase.CAMEL_ENTITY, ModBase.SNIFFER_ENTITY
	};

	@Override
	public void generateAdvancement(Consumer<Advancement> exporter) {
		Advancement ADVENTURE_ROOT = Advancement.Task.create().display(Items.MAP, new TranslatableText("advancements.adventure.root.title"), new TranslatableText("advancements.adventure.root.description"), new Identifier("textures/gui/advancements/backgrounds/adventure.png"), AdvancementFrame.TASK, false, false, false).criteriaMerger(CriterionMerger.OR).criterion("killed_something", OnKilledCriterion.Conditions.createPlayerKilledEntity()).criterion("killed_by_something", OnKilledCriterion.Conditions.createEntityKilledPlayer()).build(exporter, "adventure/root");
		Advancement killMobNearSculkCatalyst = Advancement.Task.create().parent(ADVENTURE_ROOT).display(ModBase.SCULK_CATALYST, new TranslatableText("advancements.adventure.kill_mob_near_sculk_catalyst.title"), new TranslatableText("advancements.adventure.kill_mob_near_sculk_catalyst.description"), null, AdvancementFrame.CHALLENGE, true, true, false).criterion("kill_mob_near_sculk_catalyst", ModCriteria.createKillMobNearSculkCatalyst()).build(exporter, "adventure/kill_mob_near_sculk_catalyst");
		Advancement avoidVibration = Advancement.Task.create().parent(ADVENTURE_ROOT).display(Blocks.SCULK_SENSOR, new TranslatableText("advancements.adventure.avoid_vibration.title"), new TranslatableText("advancements.adventure.avoid_vibration.description"), null, AdvancementFrame.TASK, true, true, false).criterion("avoid_vibration", ModTickCriterion.Conditions.createAvoidVibration()).build(exporter, "adventure/avoid_vibration");
		Advancement HUSBANDRY_ROOT = Advancement.Task.create().display(Blocks.HAY_BLOCK, new TranslatableText("advancements.husbandry.root.title"), new TranslatableText("advancements.husbandry.root.description"), new Identifier("textures/gui/advancements/backgrounds/husbandry.png"), AdvancementFrame.TASK, false, false, false).criterion("consumed_item", ConsumeItemCriterion.Conditions.any()).build(exporter, "husbandry/root");
		Advancement BREED_ANIMAL = Advancement.Task.create().parent(HUSBANDRY_ROOT).display(Items.WHEAT, new TranslatableText("advancements.husbandry.breed_an_animal.title"), new TranslatableText("advancements.husbandry.breed_an_animal.description"), null, AdvancementFrame.TASK, true, true, false).criteriaMerger(CriterionMerger.OR).criterion("bred", BredAnimalsCriterion.Conditions.any()).build(exporter, "husbandry/breed_an_animal");
		//Override all animals bred
		Advancement breedAllAnimals = this.requireListedAnimalsBred(Advancement.Task.create()).parent(BREED_ANIMAL).display(Items.GOLDEN_CARROT, new TranslatableText("advancements.husbandry.breed_all_animals.title"), new TranslatableText("advancements.husbandry.breed_all_animals.description"), null, AdvancementFrame.CHALLENGE, true, true, false).rewards(AdvancementRewards.Builder.experience(100)).build(exporter, "husbandry/bred_all_animals");
		//Override glow sign
		Advancement makeASignGlow = Advancement.Task.create().parent(HUSBANDRY_ROOT).display(Items.GLOW_INK_SAC, new TranslatableText("advancements.husbandry.make_a_sign_glow.title"), new TranslatableText("advancements.husbandry.make_a_sign_glow.description"), null, AdvancementFrame.TASK, true, true, false).criterion("make_a_sign_glow", ItemCriterion.Conditions.create(LocationPredicate.Builder.create().block(BlockPredicate.Builder.create().tag(ModBlockTags.ALL_SIGNS).build()), ItemPredicate.Builder.create().items(Items.GLOW_INK_SAC))).build(exporter, "husbandry/make_a_sign_glow");
		Advancement tadpoleInABucket = Advancement.Task.create().parent(HUSBANDRY_ROOT).criterion(Registry.ITEM.getId(ModBase.TADPOLE_BUCKET).getPath(), FilledBucketCriterion.Conditions.create(ItemPredicate.Builder.create().items(ModBase.TADPOLE_BUCKET).build())).display(ModBase.TADPOLE_BUCKET, new TranslatableText("advancements.husbandry.tadpole_in_a_bucket.title"), new TranslatableText("advancements.husbandry.tadpole_in_a_bucket.description"), null, AdvancementFrame.TASK, true, true, false).build(exporter, "husbandry/tadpole_in_a_bucket");
		Advancement leashAllFrogVariants = this.requireAllFrogsOnLeads(Advancement.Task.create()).parent(tadpoleInABucket).display(Items.LEAD, new TranslatableText("advancements.husbandry.leash_all_frog_variants.title"), new TranslatableText("advancements.husbandry.leash_all_frog_variants.description"), null, AdvancementFrame.TASK, true, true, false).build(exporter, "husbandry/leash_all_frog_variants");
		Advancement froglights = Advancement.Task.create().parent(leashAllFrogVariants).display(ModBase.VERDANT_FROGLIGHT, new TranslatableText("advancements.husbandry.froglights.title"), new TranslatableText("advancements.husbandry.froglights.description"), null, AdvancementFrame.CHALLENGE, true, true, false).criterion("froglights", InventoryChangedCriterion.Conditions.items(ModBase.OCHRE_FROGLIGHT, ModBase.PEARLESCENT_FROGLIGHT, ModBase.VERDANT_FROGLIGHT)).build(exporter, "husbandry/froglights");
	}

	private static class FrogVariantPredicate extends NbtPredicate {
		private final FrogVariant variant;
		public FrogVariantPredicate(FrogVariant variant) { super(null); this.variant = variant; }
		public boolean test(Entity entity) { return entity instanceof FrogEntity frog && frog.getVariant() == variant; }
	}
	private Advancement.Task requireAllFrogsOnLeads(Advancement.Task builder) {
		Arrays.stream(FrogVariant.values()).forEach(variant -> builder.criterion(variant.name,
				PlayerInteractedWithEntityCriterion.Conditions.create(
						EntityPredicate.Extended.EMPTY,
						ItemPredicate.Builder.create().items(Items.LEAD),
						EntityPredicate.Extended.ofLegacy(
								EntityPredicate.Builder.create().type(ModBase.FROG_ENTITY)
										.nbt(new FrogVariantPredicate(variant))
										.build()))));
		return builder;
	}
	private Advancement.Task requireListedAnimalsBred(Advancement.Task task) {
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			task.criterion(EntityType.getId(entityType).toString(), BredAnimalsCriterion.Conditions.create(EntityPredicate.Builder.create().type(entityType)));
		}
		task.criterion(EntityType.getId(EntityType.TURTLE).toString(), BredAnimalsCriterion.Conditions.create(EntityPredicate.Builder.create().type(EntityType.TURTLE).build(), EntityPredicate.Builder.create().type(EntityType.TURTLE).build(), EntityPredicate.ANY));
		return task;
	}
}
