package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.MetalButtonBlock;
import haven.blocks.basic.*;
import haven.items.buckets.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class GoldMaterial extends BaseMaterial implements
		TorchProvider, SoulTorchProvider, LanternProvider, SoulLanternProvider,
		ButtonProvider, ChainProvider, BarsProvider, WallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider,
		BucketProvider, ShearsProvider {
	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer lantern;
	public BlockContainer getLantern() { return lantern; }
	private final Block unlit_lantern;
	public Block getUnlitLantern() { return unlit_lantern; }
	private final BlockContainer soul_lantern;
	public BlockContainer getSoulLantern() { return soul_lantern; }
	private final Block unlit_soul_lantern;
	public Block getUnlitSoulLantern() { return unlit_soul_lantern; }
	private final BlockContainer button;
	public BlockContainer getButton() { return button; }
	private final BlockContainer chain;
	public BlockContainer getChain() { return chain; }
	private final BlockContainer bars;
	public BlockContainer getBars() { return bars; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer cut;
	public BlockContainer getCut() { return cut; }
	private final BlockContainer cut_pillar;
	public BlockContainer getCutPillar() { return cut_pillar; }
	private final BlockContainer cut_slab;
	public BlockContainer getCutSlab() { return cut_slab; }
	private final BlockContainer cut_stairs;
	public BlockContainer getCutStairs() { return cut_stairs; }
	private final BlockContainer cut_wall;
	public BlockContainer getCutWall() { return cut_wall; }
	private final Item shears;
	public Item getShears() { return shears; }

	//Base Item
	private final Item bucket;
	public Item getBucket() { return bucket; }
	//Vanilla Fluids
	private final Item water_bucket;
	public Item getWaterBucket() { return water_bucket; }
	private final Item lava_bucket;
	public Item getLavaBucket() { return lava_bucket; }
	private final Item powder_snow_bucket;
	public Item getPowderSnowBucket() { return powder_snow_bucket; }
	//Mod Fluids
	private final Item blood_bucket;
	public Item getBloodBucket() { return blood_bucket; }
	private final Item mud_bucket;
	public Item getMudBucket() { return mud_bucket; }
	//Milk
	private final Item milk_bucket;
	public Item getMilkBucket() { return milk_bucket; }
	private final Item chocolate_milk_bucket;
	public Item getChocolateMilkBucket() { return chocolate_milk_bucket; }
	private final Item coffee_milk_bucket;
	public Item getCoffeeMilkBucket() { return coffee_milk_bucket; }
	private final Item strawberry_milk_bucket;
	public Item getStrawberryMilkBucket() { return strawberry_milk_bucket; }
	private final Item cottage_cheese_bucket;
	public Item getCottageCheeseBucket() { return cottage_cheese_bucket; }

	public GoldMaterial() {
		super("gold", false);
		torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(14)).sounds(BlockSoundGroup.METAL), HavenMod.GOLD_FLAME_PARTICLE, ItemSettings());
		soul_torch = new TorchContainer(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(10)).sounds(BlockSoundGroup.METAL), ParticleTypes.SOUL_FIRE_FLAME, ItemSettings());
		lantern = new BlockContainer(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(15)).nonOpaque()), ItemSettings());
		unlit_lantern = new LanternBlock(HavenMod.UnlitLanternSettings());
		soul_lantern = new BlockContainer(new LanternBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(10)).nonOpaque()), ItemSettings());
		unlit_soul_lantern = new LanternBlock(HavenMod.UnlitLanternSettings());
		button = new BlockContainer(new MetalButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(1.0F).sounds(BlockSoundGroup.METAL)));
		chain = new BlockContainer(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		bars = new BlockContainer(new HavenPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()), ItemSettings());
		wall = new BlockContainer(new HavenWallBlock(Blocks.GOLD_BLOCK), ItemSettings());
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)), ItemSettings());
		cut_pillar = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(cut.BLOCK)), ItemSettings());
		cut_slab = new BlockContainer(new HavenSlabBlock(cut.BLOCK), ItemSettings());
		cut_stairs = new BlockContainer(new HavenStairsBlock(cut.BLOCK), ItemSettings());
		cut_wall = new BlockContainer(new HavenWallBlock(cut.BLOCK), ItemSettings());
		shears = new ShearsItem(ItemSettings().maxDamage(128));

		bucket = new HavenBucketItem(Fluids.EMPTY, BucketSettings(), this);
		water_bucket = new HavenBucketItem(Fluids.WATER, FilledBucketSettings(), this);
		lava_bucket = new HavenBucketItem(Fluids.LAVA, FilledBucketSettings(), this);
		powder_snow_bucket = new HavenPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, FilledBucketSettings(), this);
		blood_bucket = new HavenBucketItem(HavenMod.STILL_BLOOD_FLUID, FilledBucketSettings(), this);
		mud_bucket = new HavenBucketItem(HavenMod.STILL_MUD_FLUID, FilledBucketSettings(), this);
		milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		chocolate_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		coffee_milk_bucket = new CoffeeMilkBucketItem(FilledBucketSettings(), this);
		strawberry_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		cottage_cheese_bucket = new CottageCheeseBucketItem(HavenMod.COTTAGE_CHEESE_BLOCK, FilledBucketSettings().food(HavenMod.COTTAGE_CHEESE_FOOD_COMPONENT), this);
	}

	public boolean contains(Block block) {
		return torch.contains(block) || soul_torch.contains(block) || containsLantern(block) || containsSoulLantern(block)
				|| chain.contains(block) || wall.contains(block)
				|| cut.contains(block) || cut_pillar.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block)
				|| cut_wall.contains(block) || bars.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || soul_torch.contains(item)
				|| lantern.contains(item) || soul_lantern.contains(item) || chain.contains(item) || wall.contains(item)
				|| cut.contains(item) || cut_pillar.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item)
				|| cut_wall.contains(item) || bars.contains(item) || item == shears || containsBucket(item) || super.contains(item);
	}
}
