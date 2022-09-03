package haven.util;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;

public class HavenSign {
	public final SignType TYPE;
	public final Identifier ID;
	public final Block BLOCK;
	public final Identifier WALL_ID;
	public final Block WALL_BLOCK;
	public final Item ITEM;

	public HavenSign(String name, Material material, BlockSoundGroup blockSoundGroup) {
		TYPE = new HavenSignType(name);
		ID = HavenMod.ID(name + "_sign");
		BLOCK = new SignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), TYPE);
		WALL_ID = HavenMod.ID(name + "_wall_sign");
		WALL_BLOCK = new WallSignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup).dropsLike(BLOCK), TYPE);
		ITEM = new SignItem((new Item.Settings()).maxCount(16).group(HavenMod.ITEM_GROUP), BLOCK, WALL_BLOCK);
	}

	public void Register() {
		Registry.register(Registry.BLOCK, ID, BLOCK);
		Registry.register(Registry.ITEM, ID, ITEM);
		Registry.register(Registry.BLOCK, WALL_ID, WALL_BLOCK);
	}
}
