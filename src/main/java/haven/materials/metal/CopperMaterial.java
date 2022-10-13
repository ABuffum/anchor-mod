package haven.materials.metal;

import haven.HavenMod;
import haven.blocks.UnlitLanternBlock;
import haven.blocks.basic.HavenPaneBlock;
import haven.blocks.basic.HavenWallBlock;
import haven.blocks.MetalButtonBlock;
import haven.blocks.oxidizable.*;
import haven.containers.*;
import haven.items.buckets.*;
import haven.materials.base.ToolArmorHorseMaterial;
import haven.materials.providers.*;
import haven.materials.HavenArmorMaterials;
import haven.materials.HavenToolMaterials;
import haven.util.OxidationScale;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class CopperMaterial extends ToolArmorHorseMaterial implements
		NuggetProvider, ShearsProvider, BucketProvider,
		OxidizableTorchProvider, OxidizableEnderTorchProvider, OxidizableSoulTorchProvider,
		OxidizableLanternProvider, OxidizableEnderLanternProvider, OxidizableSoulLanternProvider,
		OxidizableChainProvider, OxidizableBarsProvider,
		OxidizableButtonProvider,
		OxidizableWallProvider, OxidizableCutPillarProvider, OxidizableCutWallProvider {

	private final OxidizableTorchContainer oxidizable_torch;
	public OxidizableTorchContainer getOxidizableTorch() { return oxidizable_torch; }
	private final OxidizableTorchContainer oxidizable_ender_torch;
	public OxidizableTorchContainer getOxidizableEnderTorch() { return oxidizable_ender_torch; }
	private final OxidizableTorchContainer oxidizable_soul_torch;
	public OxidizableTorchContainer getOxidizableSoulTorch() { return oxidizable_soul_torch; }
	private final OxidizableLanternContainer oxidizable_lantern;
	public OxidizableLanternContainer getOxidizableLantern() { return oxidizable_lantern; }
	private final OxidizableLanternContainer oxidizable_ender_lantern;
	public OxidizableLanternContainer getOxidizableEnderLantern() { return oxidizable_ender_lantern; }
	private final OxidizableLanternContainer oxidizable_soul_lantern;
	public OxidizableLanternContainer getOxidizableSoulLantern() { return oxidizable_soul_lantern; }

	private final Item nugget;
	public Item getNugget() { return nugget; }

	private final OxidizableBlockContainer oxidizable_chain;
	public OxidizableBlockContainer getOxidizableChain() { return oxidizable_chain; }
	private final OxidizableBlockContainer oxidizable_bars;
	public OxidizableBlockContainer getOxidizableBars() { return oxidizable_bars; }
	private final OxidizableBlockContainer oxidizable_button;
	public OxidizableBlockContainer getOxidizableButton() { return oxidizable_button; }
	private final OxidizableBlockContainer oxidizable_wall;
	public OxidizableBlockContainer getOxidizableWall() { return oxidizable_wall; }
	private final OxidizableBlockContainer oxidizable_cut_pillar;
	public OxidizableBlockContainer getOxidizableCutPillar() { return oxidizable_cut_pillar; }
	private final OxidizableBlockContainer oxidizable_cut_wall;
	public OxidizableBlockContainer getOxidizableCutWall() { return oxidizable_cut_wall; }

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

	private static AbstractBlock.Settings CopperSettings(Oxidizable.OxidizationLevel level) {
		return AbstractBlock.Settings.of(Material.METAL, OxidationScale.getMapColor(level)).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER);
	}

	public CopperMaterial() {
		super("copper", false, HavenToolMaterials.COPPER,
				6, -3, -1, -2, 1, -2.8F, 1.5F, -3, 3, -2.4F,
				HavenArmorMaterials.COPPER, 6);
		oxidizable_torch = new OxidizableTorchContainer(HavenMod.COPPER_FLAME_PARTICLE, TorchSettings(12, BlockSoundGroup.COPPER));
		oxidizable_ender_torch = new OxidizableTorchContainer(HavenMod.ENDER_FIRE_FLAME_PARTICLE, TorchSettings(12, BlockSoundGroup.COPPER));
		oxidizable_soul_torch = new OxidizableTorchContainer(ParticleTypes.SOUL_FIRE_FLAME, TorchSettings(10, BlockSoundGroup.COPPER));
		oxidizable_lantern = new OxidizableLanternContainer(OxidizableLanternBlock::new, OxidizableUnlitLanternBlock::new, LanternBlock::new, UnlitLanternBlock::new, LanternSettings(13));
		oxidizable_ender_lantern = new OxidizableLanternContainer(OxidizableLanternBlock::new, OxidizableUnlitLanternBlock::new, LanternBlock::new, UnlitLanternBlock::new, LanternSettings(13));
		oxidizable_soul_lantern = new OxidizableLanternContainer(OxidizableLanternBlock::new, OxidizableUnlitLanternBlock::new, LanternBlock::new, UnlitLanternBlock::new, LanternSettings(10));
		nugget = new Item(ItemSettings());
		oxidizable_chain = new OxidizableBlockContainer(OxidizableChainBlock::new, ChainBlock::new, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.CHAIN).nonOpaque());
		oxidizable_bars = new OxidizableBlockContainer(OxidizablePaneBlock::new, HavenPaneBlock::new, AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).nonOpaque());
		oxidizable_button = new OxidizableBlockContainer(OxidizableButtonBlock::new, MetalButtonBlock::new, AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(1.0F).sounds(BlockSoundGroup.COPPER));
		oxidizable_wall = new OxidizableBlockContainer(OxidizableWallBlock::new, HavenWallBlock::new, CopperMaterial::CopperSettings);
		oxidizable_cut_pillar = new OxidizableBlockContainer(OxidizablePillarBlock::new, PillarBlock::new, CopperMaterial::CopperSettings);
		oxidizable_cut_wall = new OxidizableBlockContainer(OxidizableWallBlock::new, HavenWallBlock::new, CopperMaterial::CopperSettings);

		shears = new ShearsItem(ItemSettings().maxDamage(200));

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
		vanilla_milk_bucket = new HavenMilkBucketItem(FilledBucketSettings(), this);
		cottage_cheese_bucket = new CottageCheeseBucketItem(HavenMod.COTTAGE_CHEESE_BLOCK, FilledBucketSettings().food(HavenMod.COTTAGE_CHEESE_FOOD_COMPONENT), this);
	}

	public boolean contains(Block block) {
		return oxidizable_torch.contains(block) || oxidizable_ender_torch.contains(block) || oxidizable_soul_torch.contains(block)
				|| oxidizable_lantern.contains(block) || oxidizable_ender_lantern.contains(block) || oxidizable_soul_lantern.contains(block)
				|| oxidizable_chain.contains(block) || oxidizable_bars.contains(block) || oxidizable_button.contains(block)
				|| oxidizable_wall.contains(block) || oxidizable_cut_pillar.contains(block) || oxidizable_cut_wall.contains(block)
				|| super.contains(block);
	}

	public boolean contains(Item item) {
		return item == nugget || item == shears || containsBucket(item)
				|| oxidizable_torch.contains(item) || oxidizable_ender_torch.contains(item) || oxidizable_soul_torch.contains(item)
				|| oxidizable_lantern.contains(item) || oxidizable_ender_lantern.contains(item) || oxidizable_soul_lantern.contains(item)
				|| oxidizable_chain.contains(item) || oxidizable_bars.contains(item) || oxidizable_button.contains(item)
				|| oxidizable_wall.contains(item) || oxidizable_cut_pillar.contains(item) || oxidizable_cut_wall.contains(item)
				|| super.contains(item);
	}
}
