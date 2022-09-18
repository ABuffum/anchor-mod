package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.basic.*;
import haven.items.basic.HavenHorseArmorItem;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.util.HavenPair;
import haven.util.HavenTorch;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;

public class NetheriteMaterial extends BaseMaterial implements
		NuggetProvider, HorseArmorProvider,
		TorchProvider, SoulTorchProvider,
		LanternProvider, SoulLanternProvider,
		ChainProvider, BarsProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {

	private final HavenTorch torch;
	public HavenTorch getTorch() { return torch; }
	private final HavenTorch soul_torch;
	public HavenTorch getSoulTorch() { return soul_torch; }
	private final HavenPair lantern;
	public HavenPair getLantern() { return lantern; }
	private final HavenPair soul_lantern;
	public HavenPair getSoulLantern() { return soul_lantern; }
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final HavenPair chain;
	public HavenPair getChain() { return chain; }
	private final HavenPair bars;
	public HavenPair getBars() { return bars; }
	private final HavenPair wall;
	public HavenPair getWall() { return wall; }
	private final HavenPair cut;
	public HavenPair getCut() { return cut; }
	private final HavenPair cut_pillar;
	public HavenPair getCutPillar() { return cut_pillar; }
	private final HavenPair cut_slab;
	public HavenPair getCutSlab() { return cut_slab; }
	private final HavenPair cut_stairs;
	public HavenPair getCutStairs() { return cut_stairs; }
	private final HavenPair cut_wall;
	public HavenPair getCutWall() { return cut_wall; }
	private final Item horse_armor;
	public Item getHorseArmor() { return horse_armor; }

	public NetheriteMaterial() {
		super("netherite", false);
		torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.NETHERITE), HavenMod.NETHERITE_FLAME, ItemSettings());
		soul_torch = new HavenTorch(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.NETHERITE), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(15)).nonOpaque()), ItemSettings());
		soul_lantern = new HavenPair(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(10)).nonOpaque()), ItemSettings());
		nugget = new Item(ItemSettings());
		chain = new HavenPair(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		bars = new HavenPair(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE).nonOpaque()), ItemSettings());
		wall = new HavenPair(new HavenWallBlock(Blocks.NETHERITE_BLOCK), ItemSettings());
		cut = new HavenPair(new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)), ItemSettings());
		cut_pillar = new HavenPair(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new HavenPair(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new HavenPair(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new HavenPair(new HavenWallBlock(cut.BLOCK), ItemSettings());
		horse_armor = new HavenHorseArmorItem(15, getName(), ItemSettings().maxCount(1));
	}
	@Override
	protected Item.Settings ItemSettings() {
		return super.ItemSettings().fireproof();
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block)
				|| block == lantern.BLOCK || block == soul_lantern.BLOCK || block == chain.BLOCK || block == wall.BLOCK
				|| block == cut.BLOCK || block == cut_pillar.BLOCK || block == cut_slab.BLOCK || block == cut_stairs.BLOCK
				|| block == cut_wall.BLOCK || block == bars.BLOCK || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| item == lantern.ITEM || item == soul_lantern.ITEM || item == chain.ITEM || item == wall.ITEM
				|| item == cut.ITEM || item == cut_pillar.ITEM || item == cut_slab.ITEM || item == cut_stairs.ITEM
				|| item == cut_wall.ITEM || item == bars.ITEM || item == horse_armor || item == nugget || super.contains(item);
	}
}
