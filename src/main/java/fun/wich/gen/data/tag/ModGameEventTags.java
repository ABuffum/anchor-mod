package fun.wich.gen.data.tag;

import fun.wich.ModId;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

public class ModGameEventTags {
	public static final Tag.Identified<GameEvent> ALLAY_CAN_LISTEN = createMinecraftTag("allay_can_listen");
	public static final Tag.Identified<GameEvent> SHRIEKER_CAN_LISTEN = createMinecraftTag("shrieker_can_listen");
	public static final Tag.Identified<GameEvent> WARDEN_CAN_LISTEN = createMinecraftTag("warden_can_listen");

	private static Tag.Identified<GameEvent> createTag(String name) { return TagFactory.GAME_EVENT.create(ModId.ID(name)); }
	private static Tag.Identified<GameEvent> createCommonTag(String name) { return TagFactory.GAME_EVENT.create(new Identifier("c", name)); }
	private static Tag.Identified<GameEvent> createMinecraftTag(String name) { return TagFactory.GAME_EVENT.create(new Identifier(name)); }
}
