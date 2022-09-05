package haven.util;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;

public class HavenSign extends WalledBlock {
	public final SignType TYPE;

	public HavenSign(String name, Material material, BlockSoundGroup blockSoundGroup) {
		this(new HavenSignType(name), material, blockSoundGroup);
	}
	private HavenSign(HavenSignType type, Material material, BlockSoundGroup blockSoundGroup) {
		this(type,new SignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), type),material, blockSoundGroup);
	}
	private HavenSign(HavenSignType type, Block block, Material material, BlockSoundGroup blockSoundGroup) {
		this (type, block, new WallSignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup).dropsLike(block), type));
	}
	private HavenSign(HavenSignType type, Block block, Block wallBlock) {
		this(type, block, wallBlock, new SignItem((new Item.Settings()).maxCount(16).group(HavenMod.ITEM_GROUP), block, wallBlock));
	}
	private HavenSign(HavenSignType type, Block block, Block wallBlock, Item item) {
		super(block, wallBlock, item);
		TYPE = type;
	}
}
