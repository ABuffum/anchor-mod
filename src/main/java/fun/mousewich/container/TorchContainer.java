package fun.mousewich.container;

import fun.mousewich.ModFactory;
import fun.mousewich.block.oxidizable.OxidizableTorchBlock;
import fun.mousewich.block.oxidizable.OxidizableWallTorchBlock;
import fun.mousewich.block.oxidizable.WaterloggableOxidizableTorchBlock;
import fun.mousewich.block.oxidizable.WaterloggableOxidizableWallTorchBlock;
import fun.mousewich.block.torch.LightableTorchBlock;
import fun.mousewich.block.torch.LightableWallTorchBlock;
import fun.mousewich.block.torch.WaterloggableTorchBlock;
import fun.mousewich.block.torch.WaterloggableWallTorchBlock;
import fun.mousewich.gen.data.ModDatagen;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.util.OxidationScale;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Pair;

public class TorchContainer implements IWallBlockItemContainer {
	protected final LightableTorchBlock block;
	public Block asBlock() { return block; }
	protected final LightableWallTorchBlock wallBlock;
	public Block getWallBlock() { return wallBlock; }
	protected final Item item;
	public Item asItem() { return item; }

	public TorchContainer(Block.Settings settings, ParticleEffect particle) { this(settings, particle, ModFactory.ItemSettings()); }
	public TorchContainer(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		this(new LightableTorchBlock(blockSettings, particle), particle, itemSettings);
	}
	private TorchContainer(LightableTorchBlock block, ParticleEffect particle, Item.Settings itemSettings) {
		this(block, new LightableWallTorchBlock(Block.Settings.copy(block), particle), itemSettings);
	}
	private TorchContainer(LightableTorchBlock block, LightableWallTorchBlock wallBlock, Item.Settings itemSettings) {
		this.block = block;
		this.item = new WallStandingBlockItem(block, wallBlock, itemSettings);
		this.wallBlock = wallBlock;
	}

	public boolean contains(Block block) { return block == this.block || block == this.wallBlock; }
	public boolean contains(Item item) { return item == this.item; }

	public static TorchContainer Oxidizable(Oxidizable.OxidizationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle) { return Oxidizable(level, settings, particle, ModFactory.ItemSettings()); }
	public static TorchContainer Oxidizable(Oxidizable.OxidizationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle, Item.Settings itemSettings) {
		return Oxidizable(level, settings.get(level), particle, itemSettings);
	}
	public static TorchContainer Oxidizable(Oxidizable.OxidizationLevel level, Block.Settings settings, ParticleEffect particle) { return Oxidizable(level, settings, particle, ModFactory.ItemSettings()); }
	public static TorchContainer Oxidizable(Oxidizable.OxidizationLevel level, Block.Settings settings, ParticleEffect particle, Item.Settings itemSettings) {
		OxidizableTorchBlock block = new OxidizableTorchBlock(level, settings, particle);
		OxidizableWallTorchBlock wallBlock = new OxidizableWallTorchBlock(level, Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings);
	}

	public static TorchContainer Waterloggable(Block.Settings blockSettings, ParticleEffect particle) {
		return Waterloggable(blockSettings, particle, ModFactory.ItemSettings());
	}
	public static TorchContainer Waterloggable(Block.Settings blockSettings, ParticleEffect particle, Item.Settings itemSettings) {
		WaterloggableTorchBlock block = new WaterloggableTorchBlock(blockSettings, particle);
		WaterloggableWallTorchBlock wallBlock = new WaterloggableWallTorchBlock(Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings).dropSelf();
	}

	public static TorchContainer WaterloggableOxidizable(Oxidizable.OxidizationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle) { return WaterloggableOxidizable(level, settings, particle, ModFactory.ItemSettings()); }
	public static TorchContainer WaterloggableOxidizable(Oxidizable.OxidizationLevel level, OxidationScale.BlockSettingsSupplier settings, ParticleEffect particle, Item.Settings itemSettings) {
		return WaterloggableOxidizable(level, settings.get(level), particle, itemSettings);
	}
	public static TorchContainer WaterloggableOxidizable(Oxidizable.OxidizationLevel level, Block.Settings settings, ParticleEffect particle) { return WaterloggableOxidizable(level, settings, particle, ModFactory.ItemSettings()); }
	public static TorchContainer WaterloggableOxidizable(Oxidizable.OxidizationLevel level, Block.Settings settings, ParticleEffect particle, Item.Settings itemSettings) {
		WaterloggableOxidizableTorchBlock block = new WaterloggableOxidizableTorchBlock(level, settings, particle);
		WaterloggableOxidizableWallTorchBlock wallBlock = new WaterloggableOxidizableWallTorchBlock(level, Block.Settings.copy(block), particle);
		return new TorchContainer(block, wallBlock, itemSettings);
	}

	public TorchContainer torchModel() {
		ModDatagen.Cache.Models.TORCH.add(this);
		return this;
	}
	public TorchContainer torchModel(TorchContainer parent) {
		ModDatagen.Cache.Models.TORCH_CHILD.add(new Pair<>(this, parent));
		return this;
	}

	public TorchContainer dropSelf() {
		ModDatagen.Cache.Drops.put(this.block, DropTable.Drops(this.item));
		ModDatagen.Cache.Drops.put(this.wallBlock, DropTable.Drops(this.item));
		return this;
	}
}
