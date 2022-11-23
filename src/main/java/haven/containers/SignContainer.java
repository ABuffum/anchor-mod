package haven.containers;

import haven.ModBase;
import haven.mixins.SignTypeAccessor;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.SignType;

public class SignContainer extends WallBlockContainer {
	public final SignType TYPE;

	public SignContainer(String name, Material material, BlockSoundGroup blockSoundGroup) {
		this(SignTypeAccessor.registerNew(SignTypeAccessor.newSignType(name)), material, blockSoundGroup);
	}
	private SignContainer(SignType type, Material material, BlockSoundGroup blockSoundGroup) {
		this(type,new SignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), type),material, blockSoundGroup);
	}
	private SignContainer(SignType type, Block block, Material material, BlockSoundGroup blockSoundGroup) {
		this (type, block, new WallSignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), type));
	}
	private SignContainer(SignType type, Block block, Block wallBlock) {
		this(type, block, wallBlock, new SignItem((new Item.Settings()).maxCount(16).group(ModBase.ITEM_GROUP), block, wallBlock));
	}
	private SignContainer(SignType type, Block block, Block wallBlock, Item item) {
		super(block, wallBlock, item);
		TYPE = type;
	}
}
