package haven.materials.metal;

import haven.ModBase;
import haven.blocks.MetalButtonBlock;
import haven.blocks.UnlitLanternBlock;
import haven.blocks.basic.*;
import haven.items.basic.HavenHorseArmorItem;
import haven.items.buckets.*;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class NetheriteMaterial extends BaseMaterial implements
		NuggetProvider, HorseArmorProvider, ShearsProvider, BucketProvider,
		TorchProvider, EnderTorchProvider, SoulTorchProvider, LanternProvider, EnderLanternProvider, SoulLanternProvider,
		ButtonProvider, ChainProvider, BarsProvider, WallProvider,
		BricksProvider, BrickSlabProvider, BrickStairsProvider, BrickWallProvider,
		CutProvider, CutPillarProvider, CutSlabProvider, CutStairsProvider, CutWallProvider {

	private final TorchContainer torch;
	public TorchContainer getTorch() { return torch; }
	private final TorchContainer ender_torch;
	public TorchContainer getEnderTorch() { return ender_torch; }
	private final TorchContainer soul_torch;
	public TorchContainer getSoulTorch() { return soul_torch; }
	private final BlockContainer lantern;
	public BlockContainer getLantern() { return lantern; }
	private final Block unlit_lantern;
	public Block getUnlitLantern() { return unlit_lantern; }
	private final BlockContainer ender_lantern;
	public BlockContainer getEnderLantern() { return ender_lantern; }
	private final Block unlit_ender_lantern;
	public Block getUnlitEnderLantern() { return unlit_ender_lantern; }
	private final BlockContainer soul_lantern;
	public BlockContainer getSoulLantern() { return soul_lantern; }
	private final Block unlit_soul_lantern;
	public Block getUnlitSoulLantern() { return unlit_soul_lantern; }
	private final BlockContainer button;
	public BlockContainer getButton() { return button; }
	private final Item nugget;
	public Item getNugget() { return nugget; }
	private final BlockContainer chain;
	public BlockContainer getChain() { return chain; }
	private final BlockContainer bars;
	public BlockContainer getBars() { return bars; }
	private final BlockContainer wall;
	public BlockContainer getWall() { return wall; }
	private final BlockContainer bricks;
	public BlockContainer getBricks() { return bricks; }
	private final BlockContainer brick_slab;
	public BlockContainer getBrickSlab() { return brick_slab; }
	private final BlockContainer brick_stairs;
	public BlockContainer getBrickStairs() { return brick_stairs; }
	private final BlockContainer brick_wall;
	public BlockContainer getBrickWall() { return brick_wall; }
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
	private final Item horse_armor;
	public Item getHorseArmor() { return horse_armor; }
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
	private final Item vanilla_milk_bucket;
	public Item getVanillaMilkBucket() { return vanilla_milk_bucket; }
	private final Item cottage_cheese_bucket;
	public Item getCottageCheeseBucket() { return cottage_cheese_bucket; }

	public NetheriteMaterial() {
		super("netherite", false);
		torch = MakeTorch(ModBase.LUMINANCE_14, BlockSoundGroup.NETHERITE, ModBase.NETHERITE_FLAME_PARTICLE);
		ender_torch = MakeTorch(ModBase.LUMINANCE_10, BlockSoundGroup.NETHERITE, ModBase.ENDER_FIRE_FLAME_PARTICLE);
		soul_torch = MakeTorch(ModBase.LUMINANCE_10, BlockSoundGroup.NETHERITE, ParticleTypes.SOUL_FIRE_FLAME);
		lantern = MakeLantern(ModBase.LUMINANCE_15);
		unlit_lantern = new UnlitLanternBlock(this::getLantern);
		ender_lantern = MakeLantern(ModBase.LUMINANCE_10);
		unlit_ender_lantern = new UnlitLanternBlock(this::getEnderLantern);
		soul_lantern = MakeLantern(ModBase.LUMINANCE_10);
		unlit_soul_lantern = new UnlitLanternBlock(this::getSoulLantern);
		nugget = new Item(ItemSettings());
		button = new BlockContainer(new MetalButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(10.0F).sounds(BlockSoundGroup.NETHERITE)), ItemSettings());
		chain = new BlockContainer(new ChainBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque()), ItemSettings());
		bars = new BlockContainer(new ModPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE).nonOpaque()), ItemSettings());
		wall = new BlockContainer(new ModWallBlock(Blocks.NETHERITE_BLOCK), ItemSettings());
		bricks = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)), ItemSettings());
		brick_slab = new BlockContainer(new ModSlabBlock(bricks.getBlock()), ItemSettings());
		brick_stairs = new BlockContainer(new ModStairsBlock(bricks.getBlock()), ItemSettings());
		brick_wall = new BlockContainer(new ModWallBlock(bricks.getBlock()), ItemSettings());
		cut = new BlockContainer(new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)), ItemSettings());
		cut_pillar = new BlockContainer(new PillarBlock(AbstractBlock.Settings.copy(cut.getBlock())), ItemSettings());
		cut_slab = new BlockContainer(new ModSlabBlock(cut.getBlock()), ItemSettings());
		cut_stairs = new BlockContainer(new ModStairsBlock(cut.getBlock()), ItemSettings());
		cut_wall = new BlockContainer(new ModWallBlock(cut.getBlock()), ItemSettings());
		horse_armor = new HavenHorseArmorItem(15, getName(), ItemSettings().maxCount(1));

		shears = new ShearsItem(ItemSettings().maxDamage(2031));

		bucket = new HavenBucketItem(Fluids.EMPTY, BucketSettings(), this);
		water_bucket = new HavenBucketItem(Fluids.WATER, FilledBucketSettings(), this);
		lava_bucket = new HavenBucketItem(Fluids.LAVA, FilledBucketSettings(), this);
		powder_snow_bucket = new HavenPowderSnowBucketItem(Blocks.POWDER_SNOW, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, FilledBucketSettings(), this);
		blood_bucket = new HavenBucketItem(ModBase.STILL_BLOOD_FLUID, FilledBucketSettings().group(ModBase.BLOOD_ITEM_GROUP), this);
		mud_bucket = new HavenBucketItem(ModBase.STILL_MUD_FLUID, FilledBucketSettings(), this);
		milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		chocolate_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		coffee_milk_bucket = new CoffeeMilkBucketItem(FilledBucketSettings(), this);
		strawberry_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		vanilla_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		cottage_cheese_bucket = new CottageCheeseBucketItem(FilledBucketSettings().food(ModBase.COTTAGE_CHEESE_FOOD_COMPONENT), this);
	}
	@Override
	protected Item.Settings ItemSettings() {
		return super.ItemSettings().fireproof();
	}

	public boolean contains(Block block) {
		return torch.contains(block) || ender_torch.contains(block) || soul_torch.contains(block)
				|| containsLantern(block) || containsEnderLantern(block) || containsSoulLantern(block)
				|| chain.contains(block) || wall.contains(block)
				|| bricks.contains(block) || brick_slab.contains(block) || brick_stairs.contains(block) ||  brick_wall.contains(block)
				|| cut.contains(block) || cut_pillar.contains(block) || cut_slab.contains(block) || cut_stairs.contains(block)
				|| cut_wall.contains(block) || bars.contains(block) || super.contains(block);
	}
	public boolean contains(Item item) {
		return torch.contains(item) || ender_torch.contains(item) || soul_torch.contains(item)
				|| lantern.contains(item) || ender_lantern.contains(item) || soul_lantern.contains(item) || chain.contains(item) || wall.contains(item)
				|| bricks.contains(item) || brick_slab.contains(item) || brick_stairs.contains(item) ||  brick_wall.contains(item)
				|| cut.contains(item) || cut_pillar.contains(item) || cut_slab.contains(item) || cut_stairs.contains(item)
				|| cut_wall.contains(item) || bars.contains(item) || item == horse_armor || item == nugget || containsBucket(item) || super.contains(item);
	}
}
