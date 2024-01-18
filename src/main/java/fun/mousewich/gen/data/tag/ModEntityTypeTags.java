package fun.mousewich.gen.data.tag;

import fun.mousewich.ModId;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModEntityTypeTags {
	public static final Tag.Identified<EntityType<?>> DISMOUNTS_UNDERWATER = createMinecraftTag("dismounts_underwater");
	public static final Tag.Identified<EntityType<?>> FALL_DAMAGE_IMMUNE = createMinecraftTag("fall_damage_immune");
	public static final Tag.Identified<EntityType<?>> FROG_FOOD = createMinecraftTag("frog_food");
	public static final Tag.Identified<EntityType<?>> SPIDERS = createTag("spiders");
	public static final Tag.Identified<EntityType<?>> ZOMBIES = createTag("zombies");

	private static Tag.Identified<EntityType<?>> createTag(String name) { return TagFactory.ENTITY_TYPE.create(ModId.ID(name)); }
	private static Tag.Identified<EntityType<?>> createTag(String namespace, String path) { return TagFactory.ENTITY_TYPE.create(new Identifier(namespace, path)); }
	private static Tag.Identified<EntityType<?>> createCommonTag(String name) { return TagFactory.ENTITY_TYPE.create(new Identifier("c", name)); }
	private static Tag.Identified<EntityType<?>> createMinecraftTag(String name) { return TagFactory.ENTITY_TYPE.create(new Identifier(name)); }
}
