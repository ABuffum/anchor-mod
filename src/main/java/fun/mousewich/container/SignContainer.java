package fun.mousewich.container;

import fun.mousewich.ModFactory;
import fun.mousewich.gen.data.ModDatagen;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.mixins.SignTypeAccessor;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.SignType;

public class SignContainer extends WallBlockContainer {
	protected final SignType type;
	public SignType getType() { return type; }
	private final WallBlockContainer hangingSign;
	public WallBlockContainer getHanging() { return hangingSign; }

	public SignContainer(String name, Material material, BlockSoundGroup blockSoundGroup, Item.Settings itemSettings, Block hangingSignBase, BlockSoundGroup hangingSounds) {
		this(ModFactory.MakeSignType(name), material, blockSoundGroup, itemSettings, hangingSignBase, hangingSounds);
	}
	private SignContainer(SignType type, Material material, BlockSoundGroup blockSoundGroup, Item.Settings itemSettings, Block hangingSignBase, BlockSoundGroup hangingSounds) {
		this(type,new SignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), type),material, blockSoundGroup, itemSettings, hangingSignBase, hangingSounds);
	}
	private SignContainer(SignType type, Block block, Material material, BlockSoundGroup blockSoundGroup, Item.Settings itemSettings, Block hangingSignBase, BlockSoundGroup hangingSounds) {
		this (type, block, new WallSignBlock(AbstractBlock.Settings.of(material).noCollision().strength(1.0F).sounds(blockSoundGroup), type), itemSettings, hangingSignBase, hangingSounds);
	}
	private SignContainer(SignType type, Block block, Block wallBlock, Item.Settings itemSettings, Block hangingSignBase, BlockSoundGroup hangingSounds) {
		this(type, block, wallBlock, new SignItem(itemSettings, block, wallBlock), itemSettings, hangingSignBase, hangingSounds);
	}
	private SignContainer(SignType type, Block block, Block wallBlock, Item item, Item.Settings itemSettings, Block hangingSignBase, BlockSoundGroup hangingSounds) {
		super(block, wallBlock, item);
		this.type = type;
		this.hangingSign = ModFactory.MakeHangingSign(type, hangingSignBase, itemSettings, hangingSounds);
	}

	@Override
	public SignContainer flammable(int burn, int spread) {
		FlammableBlockRegistry.getDefaultInstance().add(asBlock(), burn, spread);
		FlammableBlockRegistry.getDefaultInstance().add(getWallBlock(), burn, spread);
		return this;
	}
	@Override
	public SignContainer fuel(int fuelTime) {
		FuelRegistry.INSTANCE.add(this.item, fuelTime);
		return this;
	}
	@Override
	public SignContainer compostable(float chance) {
		CompostingChanceRegistry.INSTANCE.add(this.item, chance);
		return this;
	}
	@Override
	public SignContainer dispenser(DispenserBehavior behavior) {
		DispenserBlock.registerBehavior(this.item, behavior);
		return this;
	}

	public SignContainer blockTag(Tag.Identified<Block> tag) {
		ModDatagen.Cache.Tags.Register(tag, this.block);
		ModDatagen.Cache.Tags.Register(tag, this.wallBlock);
		hangingSign.blockTag(tag);
		return this;
	}
	public SignContainer itemTag(Tag.Identified<Item> tag) {
		ModDatagen.Cache.Tags.Register(tag, this.item);
		hangingSign.itemTag(tag);
		return this;
	}

	public SignContainer dropSelf() {
		ModDatagen.Cache.Drops.put(this.block, DropTable.Drops(this.item));
		ModDatagen.Cache.Drops.put(this.wallBlock, DropTable.Drops(this.item));
		return this;
	}
}
