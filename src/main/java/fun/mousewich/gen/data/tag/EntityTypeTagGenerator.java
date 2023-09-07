package fun.mousewich.gen.data.tag;

import fun.mousewich.container.ArrowContainer;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Set;

import static fun.mousewich.ModBase.*;

public class EntityTypeTagGenerator extends FabricTagProvider<EntityType<?>> {
	public EntityTypeTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ENTITY_TYPE, "entity_types", NAMESPACE + ":entity_type_tag_generator");
	}
	private static Identifier betterend(String path) { return new Identifier("betterend", path); }
	private static Identifier betternether(String path) { return new Identifier("betternether", path); }
	private static Identifier graveyard(String path) { return new Identifier("graveyard", path); }
	@Override
	protected void generateTags() {
		for (Map.Entry<Tag.Identified<EntityType<?>>, Set<EntityType<?>>> entry : ModDatagen.Cache.Tags.ENTITY_TYPE_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(EntityType<?>[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tags.ENTITY_TYPE_TAGS.clear();

		getOrCreateTagBuilder(EntityTypeTags.ARROWS).add(ArrowContainer.ARROW_CONTAINERS.stream().map(ArrowContainer::getEntityType).toArray(EntityType<?>[]::new));
		getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES).add(SUNKEN_SKELETON_ENTITY);
		getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(TADPOLE_ENTITY);
		getOrCreateTagBuilder(ModEntityTypeTags.DISMOUNTS_UNDERWATER).add(CAMEL_ENTITY)
				.add(EntityType.CHICKEN, EntityType.DONKEY, EntityType.HORSE, EntityType.LLAMA, EntityType.MULE, EntityType.PIG)
				.add(EntityType.RAVAGER, EntityType.SPIDER, EntityType.STRIDER, EntityType.TRADER_LLAMA, EntityType.ZOMBIE_HORSE);
		getOrCreateTagBuilder(ModEntityTypeTags.FALL_DAMAGE_IMMUNE).add(EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM)
				.add(EntityType.SHULKER, EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT)
				.add(EntityType.CHICKEN, EntityType.GHAST, EntityType.PHANTOM, RED_PHANTOM_ENTITY, EntityType.MAGMA_CUBE)
				.add(EntityType.OCELOT, EntityType.PARROT, EntityType.WITHER)
				.addOptional(new Identifier("allay", "allay"));
		getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(MELON_GOLEM_ENTITY, FROZEN_ZOMBIE_ENTITY);
		getOrCreateTagBuilder(ModEntityTypeTags.FROG_FOOD)
				.add(EntityType.SLIME, EntityType.MAGMA_CUBE, TROPICAL_SLIME_ENTITY, PINK_SLIME_ENTITY)
				.addOptional(betterend("end_slime"));
		getOrCreateTagBuilder(EntityTypeTags.IMPACT_PROJECTILES).add(THROWABLE_TOMATO_ENTITY, PINK_SLIME_ENTITY);
		getOrCreateTagBuilder(EntityTypeTags.SKELETONS)
				.add(MOSSY_SKELETON_ENTITY, SUNKEN_SKELETON_ENTITY)
				.addOptional(betternether("jungle_skeleton"))
				.addOptional(betternether("skull"))
				.addOptional(betternether("naga"))
				.addOptional(graveyard("revenant"));
		getOrCreateTagBuilder(ModEntityTypeTags.SPIDERS).add(EntityType.SPIDER, EntityType.CAVE_SPIDER, BONE_SPIDER_ENTITY, JUMPING_SPIDER_ENTITY);
		getOrCreateTagBuilder(ModEntityTypeTags.ZOMBIES)
				.add(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.DROWNED, EntityType.GIANT)
				.add(JUNGLE_ZOMBIE_ENTITY, FROZEN_ZOMBIE_ENTITY)
				.addOptional(graveyard("ghoul"));
	}
}
