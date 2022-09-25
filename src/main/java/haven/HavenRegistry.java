package haven;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.blocks.*;
import haven.blocks.cake.CakeContainer;
import haven.blocks.mud.MudCauldronBlock;
import haven.containers.OxidizableBlockContainer;
import haven.boats.HavenBoat;
import haven.boats.HavenBoatDispenserBehavior;
import haven.command.ChorusCommand;
import haven.containers.*;
import haven.entities.passive.*;
import haven.entities.passive.cow.*;
import haven.entities.tnt.SoftTntEntity;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.origins.powers.BloodTypePower;
import haven.origins.powers.ChorusTeleportPower;
import haven.origins.powers.LactoseIntolerantPower;
import haven.origins.powers.UnfreezingPower;
import haven.util.*;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static haven.HavenMod.*;

public class HavenRegistry {
	public static Block Register(String path, Block block) {
		return Registry.register(Registry.BLOCK, ID(path), block);
	}
	public static Item Register(String path, Item item) { return Registry.register(Registry.ITEM, ID(path), item); }
	public static void Register(String path, BlockContainer pair) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, pair.BLOCK);
		Registry.register(Registry.ITEM, id, pair.ITEM);
	}
	public static void Register(String path, WaxedBlockContainer waxed) {
		Register(path, (BlockContainer)waxed);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.BLOCK, waxed.BLOCK);
	}
	public static void Register(String path, OxidizableTorchContainer container) {
		Register(path, container.getUnaffected());
		Register("exposed_" + path, container.getExposed());
		Register("weathered_" + path, container.getWeathered());
		Register("oxidized_" + path, container.getOxidized());
		OxidationScale.Register(container);
		Register("waxed_" + path, container.getWaxedUnaffected());
		Register("waxed_exposed_" + path, container.getWaxedExposed());
		Register("waxed_weathered_" + path, container.getWaxedWeathered());
		Register("waxed_oxidized_" + path, container.getWaxedOxidized());
	}
	public static void Register(String path, OxidizableLanternContainer container) {
		Register(path, (OxidizableBlockContainer)container);
		//Unlit
		Block unaffected = container.getUnlitUnaffected(), exposed = container.getUnlitExposed();
		Block weathered = container.getUnlitWeathered(), oxidized = container.getUnlitOxidized();
		Register("unlit_" + path, unaffected);
		Register("unlit_exposed_" + path, exposed);
		Register("unlit_weathered_" + path, weathered);
		Register("unlit_oxidized_" + path, oxidized);
		OxidationScale.Register(unaffected, exposed, weathered, oxidized);
		UNLIT_LANTERNS.put(container.getUnaffected().BLOCK, unaffected);
		UNLIT_LANTERNS.put(container.getExposed().BLOCK, exposed);
		UNLIT_LANTERNS.put(container.getWeathered().BLOCK, weathered);
		UNLIT_LANTERNS.put(container.getOxidized().BLOCK, oxidized);
		//Waxed Unlit
		Block waxed_unaffected = container.getUnlitWaxedUnaffected(), waxed_exposed = container.getUnlitWaxedExposed();
		Block waxed_weathered = container.getUnlitWaxedWeathered(), waxed_oxidized = container.getUnlitWaxedOxidized();
		Register("unlit_waxed_" + path, waxed_unaffected);
		Register("unlit_waxed_exposed_" + path, waxed_exposed);
		Register("unlit_waxed_weathered_" + path, waxed_weathered);
		Register("unlit_waxed_oxidized_" + path, waxed_oxidized);
		UNLIT_LANTERNS.put(container.getWaxedUnaffected().BLOCK, waxed_unaffected);
		UNLIT_LANTERNS.put(container.getWaxedExposed().BLOCK, waxed_exposed);
		UNLIT_LANTERNS.put(container.getWaxedWeathered().BLOCK, waxed_weathered);
		UNLIT_LANTERNS.put(container.getWaxedOxidized().BLOCK, waxed_oxidized);
		OxidationScale.WAXED_BLOCKS.put(unaffected, waxed_unaffected);
		OxidationScale.WAXED_BLOCKS.put(exposed, waxed_exposed);
		OxidationScale.WAXED_BLOCKS.put(weathered, waxed_weathered);
		OxidationScale.WAXED_BLOCKS.put(oxidized, waxed_oxidized);
	}
	public static void Register(String path, OxidizableBlockContainer container) {
		Register(path, container.getUnaffected());
		Register("exposed_" + path, container.getExposed());
		Register("weathered_" + path, container.getWeathered());
		Register("oxidized_" + path, container.getOxidized());
		OxidationScale.Register(container);
		Register("waxed_" + path, container.getWaxedUnaffected());
		Register("waxed_exposed_" + path, container.getWaxedExposed());
		Register("waxed_weathered_" + path, container.getWaxedWeathered());
		Register("waxed_oxidized_" + path, container.getWaxedOxidized());
	}
	public static <T extends BlockEntity> BlockEntityType Register(String path, BlockEntityType<T> entity) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), entity);
	}
	public static void Register(String name, GourdContainer gourd) {
		Register(name, gourd.getGourd());
		Item seeds = gourd.getSeeds();
		Register(name + "_seeds", seeds);
		Register(name += "_stem", gourd.getStem());
		Register("attached_" + name, gourd.getAttachedStem());
		CompostingChanceRegistry.INSTANCE.add(gourd.getSeeds(), 0.65F);
	}
	public static void Register(String name, CarvedGourdContainer gourd) {
		BlockContainer gourd_ = gourd.getGourd();
		Register(name, gourd_);
		CompostingChanceRegistry.INSTANCE.add(gourd_.ITEM, 0.65F);
		BlockContainer carved = gourd.getCarved();
		Register("carved_" + name, carved);
		CompostingChanceRegistry.INSTANCE.add(carved.ITEM, 0.65F);
		Item seeds = gourd.getSeeds();
		Register(name + "_seeds", seeds);
		Register(name += "_stem", gourd.getStem());
		Register("attached_" + name, gourd.getAttachedStem());
		CompostingChanceRegistry.INSTANCE.add(gourd.getSeeds(), 0.65F);
	}
	public static void Register(CakeContainer cake) {
		String name = cake.getFlavor().getName();
		Register(name + "_cake", cake.getCake());
		Register(name += "_candle_cake", cake.getCandleCake());
		for(DyeColor color : DyeColor.values()) Register(color.getName() + "_" + name, cake.getCandleCake(color));
	}
	public static StatusEffect Register(String path, StatusEffect effect) {
		return Registry.register(Registry.STATUS_EFFECT, ID(path), effect);
	}
	public static DefaultParticleType Register(String path, DefaultParticleType effect) {
		return Registry.register(Registry.PARTICLE_TYPE, ID(path), effect);
	}
	public static Fluid Register(String path, Fluid fluid) {
		return Registry.register(Registry.FLUID, ID(path), fluid);
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	public static void Register(String path, PottedBlockContainer potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.BLOCK);
		Registry.register(Registry.ITEM, id, potted.ITEM);
		Registry.register(Registry.BLOCK, ID("potted_" + path), potted.POTTED);
	}
	public static void Register(String name, TorchContainer torch) {
		Register(name + "_torch", name + "_wall_torch", torch);
		Register("unlit_" + name, torch.UNLIT);
	}
	public static void Register(String name, TorchContainer.Unlit unlit) {
		Register(name + "_torch", unlit.UNLIT);
		Register(name + "_wall_torch", unlit.UNLIT_WALL);
		TorchContainer.UNLIT_TORCHES.put(unlit.LIT, unlit.UNLIT);
		TorchContainer.UNLIT_WALL_TORCHES.put(unlit.LIT_WALL, unlit.UNLIT_WALL);
	}
	public static void Register(String name, WaxedTorchContainer waxed) {
		Register(name, (TorchContainer)waxed);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.BLOCK, waxed.BLOCK);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.WALL_BLOCK, waxed.WALL_BLOCK);
		//Unlit
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.UNLIT.UNLIT, waxed.UNLIT.UNLIT);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.UNLIT.UNLIT_WALL, waxed.UNLIT.UNLIT_WALL);
	}
	public static void Register(String path, String wallPath, WallBlockContainer walled) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, walled.BLOCK);
		Registry.register(Registry.BLOCK, ID(wallPath), walled.WALL_BLOCK);
		Registry.register(Registry.ITEM, id, walled.ITEM);
	}
	public static void Register(BaseMaterial material) {
		String name = material.getName();
		boolean flammable = material.isFlammable();
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		FuelRegistry FUEL = FuelRegistry.INSTANCE;
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		//Crafting
		if (material instanceof NuggetProvider nugget) Register(name + "_nugget", nugget.getNugget());
		//Tool
		if (material instanceof AxeProvider axe) Register(name + "_axe", axe.getAxe());
		if (material instanceof HoeProvider hoe) Register(name + "_hoe", hoe.getHoe());
		if (material instanceof PickaxeProvider pickaxe) Register(name + "_pickaxe", pickaxe.getPickaxe());
		if (material instanceof ShovelProvider shovel) Register(name + "_shovel", shovel.getShovel());
		if (material instanceof SwordProvider sword) Register(name + "_sword", sword.getSword());
		if (material instanceof KnifeProvider knife) Register(name + "_knife", knife.getKnife());
		if (material instanceof HelmetProvider helmet) Register(name + "_helmet", helmet.getHelmet());
		if (material instanceof ChestplateProvider chestplate) Register(name + "_chestplate", chestplate.getChestplate());
		if (material instanceof LeggingsProvider leggings) Register(name + "_leggings", leggings.getLeggings());
		if (material instanceof BootsProvider boots) Register(name + "_boots", boots.getBoots());
		if (material instanceof HorseArmorProvider horseArmorProvider){
			ItemDispenserBehavior HORSE_ARMOR_DISPENSER_BEHAVIOR = new FallibleItemDispenserBehavior() {
				protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
					BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
					List<HorseBaseEntity> list = pointer.getWorld().getEntitiesByClass(HorseBaseEntity.class, new Box(blockPos), (horseBaseEntityx) -> {
						return horseBaseEntityx.isAlive() && horseBaseEntityx.hasArmorSlot();
					});
					Iterator var5 = list.iterator();
					HorseBaseEntity horseBaseEntity;
					do {
						if (!var5.hasNext()) return super.dispenseSilently(pointer, stack);
						horseBaseEntity = (HorseBaseEntity)var5.next();
					} while(!horseBaseEntity.isHorseArmor(stack) || horseBaseEntity.hasArmorInSlot() || !horseBaseEntity.isTame());
					horseBaseEntity.getStackReference(401).set(stack.split(1));
					this.setSuccess(true);
					return stack;
				}
			};
			Item horseArmor = horseArmorProvider.getHorseArmor();
			Register(name + "_horse_armor", horseArmor);
			DispenserBlock.registerBehavior(horseArmor, HORSE_ARMOR_DISPENSER_BEHAVIOR);
		}
		if (material instanceof ShearsProvider shearsProvider) {
			Item shears = shearsProvider.getShears();
			Register(name + "_shears", shears);
			DispenserBlock.registerBehavior(shears, new ShearsDispenserBehavior());
		}
		if (material instanceof BucketProvider bucketProvider) RegisterBuckets(name, bucketProvider);
		//Blocks
		if (material instanceof TorchProvider torch) Register(name, torch.getTorch());
		if (material instanceof OxidizableTorchProvider oxidizableTorch) Register(name, oxidizableTorch.getOxidizableTorch());
		if (material instanceof SoulTorchProvider soulTorch) Register(name + "_soul", soulTorch.getSoulTorch());
		if (material instanceof OxidizableSoulTorchProvider oxidizableSoulTorch) Register(name + "_soul", oxidizableSoulTorch.getOxidizableSoulTorch());
		if (material instanceof CampfireProvider campfire) Register(name + "_campfire", campfire.getCampfire());
		if (material instanceof SoulCampfireProvider soulCampfire) Register(name + "_soul_campfire", soulCampfire.getSoulCampfire());
		if (material instanceof LanternProvider lantern) {
			Register(name + "_lantern", lantern.getLantern());
			Register("unlit_" + name + "_lantern", lantern.getUnlitLantern());
		}
		if (material instanceof OxidizableLanternProvider oxidizableLantern) Register(name + "_lantern", oxidizableLantern.getOxidizableLantern());
		if (material instanceof SoulLanternProvider soulLantern) {
			Register(name + "_soul_lantern", soulLantern.getSoulLantern());
			Register("unlit_" + name + "_soul_lantern", soulLantern.getUnlitSoulLantern());
		}
		if (material instanceof OxidizableSoulLanternProvider oxidizableSoulLantern) Register(name + "_soul_lantern", oxidizableSoulLantern.getOxidizableSoulLantern());
		if (material instanceof ChainProvider chain) Register(name + "_chain", chain.getChain());
		if (material instanceof OxidizableChainProvider oxidizableChain) Register(name + "_chain", oxidizableChain.getOxidizableChain());
		if (material instanceof BarsProvider bars) Register(name + "_bars", bars.getBars());
		if (material instanceof OxidizableBarsProvider oxidizableBars) Register(name + "_bars", oxidizableBars.getOxidizableBars());
		if (material instanceof BlockProvider block) Register(name + "_block", block.getBlock());
		if (material instanceof BundleProvider bundle) {
			BlockContainer pair = bundle.getBundle();
			Register(name + "_bundle", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StrippedBundleProvider strippedBundle) {
			BlockContainer pair = strippedBundle.getStrippedBundle();
			Register("stripped_" + name + "_bundle", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof LogProvider log) {
			BlockContainer pair = log.getLog();
			Register(name + "_log", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StrippedLogProvider strippedLog) {
			BlockContainer pair = strippedLog.getStrippedLog();
			Register("stripped_" + name + "_log", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof WoodProvider wood) {
			BlockContainer pair = wood.getWood();
			Register(name + "_wood", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StrippedWoodProvider strippedWood) {
			BlockContainer pair = strippedWood.getStrippedWood();
			Register("stripped_" + name + "_wood", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StemProvider stem) {
			BlockContainer pair = stem.getStem();
			Register(name + "_stem", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StrippedStemProvider strippedStem) {
			BlockContainer pair = strippedStem.getStrippedStem();
			Register("stripped_" + name + "_stem", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof HyphaeProvider hyphae) {
			BlockContainer pair = hyphae.getHyphae();
			Register(name + "_hyphae", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof StrippedHyphaeProvider strippedHyphae) {
			BlockContainer pair = strippedHyphae.getStrippedHyphae();
			Register("stripped_" + name + "_hyphae", pair);
			if (flammable) {
				FLAMMABLE.add(pair.BLOCK, 5, 5);
				FUEL.add(pair.ITEM, 300);
			}
		}
		if (material instanceof LeavesProvider leaves) {
			BlockContainer pair = leaves.getLeaves();
			Register(name + "_leaves", pair);
			COMPOST.add(pair.ITEM, 0.3f);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 30, 60);
		}
		if (material instanceof WartBlockProvider wartBlock){
			BlockContainer pair = wartBlock.getWartBlock();
			Register(name + "_wart_block", pair);
			COMPOST.add(pair.ITEM, 0.85f);
		}
		if (material instanceof SaplingProvider saplingProvider) {
			SaplingContainer sapling = saplingProvider.getSapling();
			Register(name + "_sapling", sapling);
			COMPOST.add(sapling.ITEM, 0.3f);
		}
		if (material instanceof FungusProvider fungusProvider) {
			FungusContainer fungus = fungusProvider.getFungus();
			Register(name + "_fungus", fungus);
			COMPOST.add(fungus.ITEM, 0.65f);
		}
		//if (material instanceof PropaguleProvider propaguleProvider) {
		//	HavenPropagule propagule = propaguleProvider.getPropagule();
		//	Register(name + "_propagule", sapling);
		//	COMPOST.add(propagule.ITEM, 0.3f);
		//}
		if (material instanceof PlanksProvider planks) {
			BlockContainer pair = planks.getPlanks();
			Register(name + "_planks", pair);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 5, 20);
		}
		if (material instanceof StairsProvider stairs) {
			BlockContainer pair = stairs.getStairs();
			Register(name + "_stairs", pair);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 5, 20);
		}
		if (material instanceof SlabProvider slab) {
			BlockContainer pair = slab.getSlab();
			Register(name + "_slab", pair);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 5, 20);
		}
		if (material instanceof DoorProvider door) Register(name + "_door", door.getDoor());
		if (material instanceof TrapdoorProvider trapdoor) Register(name + "_trapdoor", trapdoor.getTrapdoor());
		if (material instanceof PressurePlateProvider pressurePlate) Register(name + "_pressure_plate", pressurePlate.getPressurePlate());
		if (material instanceof ButtonProvider button) Register(name + "_button", button.getButton());
		if (material instanceof OxidizableButtonProvider oxidizableButton) Register(name + "_button", oxidizableButton.getOxidizableButton());
		if (material instanceof FenceProvider fence) {
			BlockContainer pair = fence.getFence();
			Register(name + "_fence", pair);
			FUEL.add(pair.ITEM, 300);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 5, 20);
		}
		if (material instanceof FenceGateProvider fenceGate) {
			BlockContainer pair = fenceGate.getFenceGate();
			Register(name + "_fence_gate", pair);
			FUEL.add(pair.ITEM, 300);
			if (flammable) FLAMMABLE.add(pair.BLOCK, 5, 20);
		}
		if (material instanceof WallProvider wall) Register(name + "_wall", wall.getWall());
		if (material instanceof OxidizableWallProvider oxidizableWall) Register(name + "_wall", oxidizableWall.getOxidizableWall());
		if (material instanceof PaneProvider pane) Register(name + "_pane", pane.getPane());
		if (material instanceof SmoothProvider smooth) Register("smooth_" + name, smooth.getSmooth());
		if (material instanceof SmoothBlockProvider smoothBlock) Register("smooth_" + name + "_block", smoothBlock.getSmoothBlock());
		if (material instanceof SmoothSlabProvider smoothSlab) Register("smooth_" + name + "_slab", smoothSlab.getSmoothSlab());
		if (material instanceof SmoothStairsProvider smoothStairs) Register("smooth_" + name + "_stairs", smoothStairs.getSmoothStairs());
		if (material instanceof SmoothWallProvider smoothWall) Register("smooth_" + name + "_wall", smoothWall.getSmoothWall());
		if (material instanceof CrystalBlockProvider crystalBlock) Register(name + "_crystal_block", crystalBlock.getCrystalBlock());
		if (material instanceof CrystalSlabProvider crystalSlab) Register(name + "_crystal_slab", crystalSlab.getCrystalSlab());
		if (material instanceof CrystalStairsProvider crystalStairs) Register(name + "_crystal_stairs", crystalStairs.getCrystalStairs());
		if (material instanceof CrystalWallProvider crystalWall) Register(name + "_crystal_wall", crystalWall.getCrystalWall());
		if (material instanceof CutProvider cut) Register("cut_" + name, cut.getCut());
		if (material instanceof CutPillarProvider cutPillar) Register("cut_" + name + "_pillar", cutPillar.getCutPillar());
		if (material instanceof OxidizableCutPillarProvider oxidizableCutPillar) Register("cut_" + name + "_pillar", oxidizableCutPillar.getOxidizableCutPillar());
		if (material instanceof CutSlabProvider cutSlab) Register("cut_" + name + "_slab", cutSlab.getCutSlab());
		if (material instanceof CutStairsProvider cutStairs) Register("cut_" + name + "_stairs", cutStairs.getCutStairs());
		if (material instanceof CutWallProvider cutWall) Register("cut_" + name + "_wall", cutWall.getCutWall());
		if (material instanceof OxidizableCutWallProvider oxidizableCutWall) Register("cut_" + name + "_wall", oxidizableCutWall.getOxidizableCutWall());
		if (material instanceof BricksProvider bricks) Register(name + "_bricks", bricks.getBricks());
		if (material instanceof BrickSlabProvider brickSlab) Register(name + "_brick_slab", brickSlab.getBrickSlab());
		if (material instanceof BrickStairsProvider brickStairs) Register(name + "_brick_stairs", brickStairs.getBrickStairs());
		if (material instanceof BrickWallProvider brickWall) Register(name + "_brick_wall", brickWall.getBrickWall());
		if (material instanceof SignProvider sign) Register(name + "_sign", name + "_wall_sign", sign.getSign());
		if (material instanceof BoatProvider boatProvider) {
			HavenBoat boat = boatProvider.getBoat();
			Register(boat.TYPE.getName() + "_boat", boat.ITEM);
			DispenserBlock.registerBehavior(boat.ITEM, new HavenBoatDispenserBehavior(boat.TYPE));
		}
	}
	public static void Register(PowerFactory<?> powerFactory) { Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory); }
	public static void Register(PowerFactorySupplier<?> factorySupplier) { Register(factorySupplier.createFactory()); }

	public static void RegisterAnchors() {
		Register("anchor", ANCHOR);
		Register("anchor_block_entity", ANCHOR_BLOCK_ENTITY);
		Register("substitute_anchor", SUBSTITUTE_ANCHOR_BLOCK);
		Register("substitute_anchor_block_entity", SUBSTITUTE_ANCHOR_BLOCK_ENTITY);
		//Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
			Register(ANCHOR_MAP.get(owner) + "_core", ANCHOR_CORES.get(owner));
		}
	}

	public static void RegisterFlowers() {
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		//Carnations
		for (DyeColor color : COLORS) {
			FlowerContainer carnation = CARNATIONS.get(color);
			Register(color.getName() + "_carnation", carnation);
			FLAMMABLE.add(carnation.BLOCK, 60, 100);
		}
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		FLAMMABLE.add(BUTTERCUP.BLOCK, 60, 100);
		Register("pink_daisy", PINK_DAISY);
		FLAMMABLE.add(PINK_DAISY.BLOCK, 60, 100);
		//Other Flowers
		Register("rose", ROSE);
		FLAMMABLE.add(ROSE.BLOCK, 60, 100);
		Register("blue_rose", BLUE_ROSE);
		FLAMMABLE.add(BLUE_ROSE.BLOCK, 60, 100);
		Register("magenta_tulip", MAGENTA_TULIP);
		FLAMMABLE.add(MAGENTA_TULIP.BLOCK, 60, 100);
		Register("marigold", MARIGOLD);
		FLAMMABLE.add(MARIGOLD.BLOCK, 60, 100);
		Register("pink_allium", PINK_ALLIUM);
		FLAMMABLE.add(PINK_ALLIUM.BLOCK, 60, 100);
		Register("lavender", LAVENDER);
		FLAMMABLE.add(LAVENDER.BLOCK, 60, 100);
		Register("hydrangea", HYDRANGEA);
		FLAMMABLE.add(HYDRANGEA.BLOCK, 60, 100);
		Register("paeonia", PAEONIA);
		FLAMMABLE.add(PAEONIA.BLOCK, 60, 100);
		Register("amaranth", AMARANTH);
		FLAMMABLE.add(AMARANTH.BLOCK, 60, 100);
		Register("tall_allium", TALL_ALLIUM);
		FLAMMABLE.add(TALL_ALLIUM.BLOCK, 60, 100);
		Register("tall_pink_allium", TALL_PINK_ALLIUM);
		FLAMMABLE.add(TALL_PINK_ALLIUM.BLOCK, 60, 100);
	}
	public static void RegisterSoftTNT() {
		Register("soft_tnt", SOFT_TNT);
		Register("soft_tnt", SOFT_TNT_ENTITY);
		DispenserBlock.registerBehavior(SOFT_TNT.BLOCK, new ItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
				SoftTntEntity tntEntity = new SoftTntEntity(world, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, (LivingEntity)null);
				world.spawnEntity(tntEntity);
				world.playSound((PlayerEntity)null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent((Entity)null, GameEvent.ENTITY_PLACE, blockPos);
				stack.decrement(1);
				return stack;
			}
		});
	}
	public static void RegisterCoffee() {
		Register("coffee_plant", COFFEE_PLANT);
		FlammableBlockRegistry.getDefaultInstance().add(COFFEE_PLANT, 30, 60);
		Register("coffee_cherry", COFFEE_CHERRY);
		CompostingChanceRegistry.INSTANCE.add(COFFEE_CHERRY, 0.65F);
		Register("coffee_beans", COFFEE_BEANS);
		CompostingChanceRegistry.INSTANCE.add(COFFEE_BEANS, 0.65F);
		Register("coffee", COFFEE);
		Register("black_coffee", BLACK_COFFEE);
	}
	public static void RegisterCherry() {
		Register(CHERRY_MATERIAL);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cherry_tree"), CHERRY_TREE);
		Register("white_cherry_leaves", WHITE_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(WHITE_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("white_cherry_tree"), WHITE_CHERRY_TREE);
		Register("pale_cherry_leaves", PALE_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(PALE_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pale_cherry_tree"), PALE_CHERRY_TREE);
		Register("pink_cherry_leaves", PINK_CHERRY_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(PINK_CHERRY_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pink_cherry_tree"), PINK_CHERRY_TREE);
		Register("cherry", CHERRY_ITEM);
		CompostingChanceRegistry.INSTANCE.add(CHERRY_ITEM, 0.65F);
	}
	public static void RegisterCinnamon() {
		Register("cinnamon", CINNAMON);
		CompostingChanceRegistry.INSTANCE.add(CINNAMON, 0.2f);
		Register(CASSIA_MATERIAL);
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(FLOWERING_CASSIA_LEAVES.BLOCK, 30, 60);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cassia_tree"), CASSIA_TREE);
	}
	public static void RegisterCookies() {
		Register("snickerdoodle", SNICKERDOODLE);
		CompostingChanceRegistry.INSTANCE.add(SNICKERDOODLE, 0.85F);
		Register("chorus_cookie", CHORUS_COOKIE);
		CompostingChanceRegistry.INSTANCE.add(CHORUS_COOKIE, 0.85F);
		Register("chocolate_cookie", CHOCOLATE_COOKIE);
		CompostingChanceRegistry.INSTANCE.add(CHOCOLATE_COOKIE, 0.85F);
		Register("confetti_cookie", CONFETTI_COOKIE);
		CompostingChanceRegistry.INSTANCE.add(CONFETTI_COOKIE, 0.85F);
		Register("cinnamon_roll", CINNAMON_ROLL);
	}
	public static void RegisterStrawberries() {
		Register("strawberry_bush", STRAWBERRY_BUSH);
		FlammableBlockRegistry.getDefaultInstance().add(STRAWBERRY_BUSH, 60, 100);
		Register("strawberry", STRAWBERRY);
		CompostingChanceRegistry.INSTANCE.add(STRAWBERRY, 0.3F);
	}
	public static Map<Item, Item> JUICE_MAP = new HashMap<Item, Item>();
	public static void RegisterJuice(String path, Item juice, Item source) {
		Register(path + "_juice", juice);
		JUICE_MAP.put(source, juice);
	}
	public static void RegisterJuice() {
		Register("apple_cider", APPLE_CIDER);
		Register("juicer", JUICER);
		RegisterJuice("apple", APPLE_JUICE, Items.APPLE);
		RegisterJuice("beetroot", BEETROOT_JUICE, Items.BEETROOT);
		Register("black_apple_juice", BLACK_APPLE_JUICE); //TODO: Hook into better nether explicitly
		//JUICE_MAP.put(Items.APPLE, BLACK_APPLE_JUICE);
		RegisterJuice("cabbage", CABBAGE_JUICE, ItemsRegistry.CABBAGE.get());
		RegisterJuice("cactus", CACTUS_JUICE, Items.CACTUS);
		RegisterJuice("carrot", CARROT_JUICE, Items.CARROT);
		RegisterJuice("cherry", CHERRY_JUICE, CHERRY_ITEM);
		RegisterJuice("chorus", CHORUS_JUICE, Items.CHORUS_FRUIT);
		RegisterJuice("glow_berry", GLOW_BERRY_JUICE, Items.GLOW_BERRIES);
		RegisterJuice("kelp", KELP_JUICE, Items.KELP);
		RegisterJuice("melon", MELON_JUICE, Items.MELON_SLICE);
		RegisterJuice("onion", ONION_JUICE, ItemsRegistry.ONION.get());
		RegisterJuice("potato", POTATO_JUICE, Items.POTATO);
		RegisterJuice("pumpkin", PUMPKIN_JUICE, Items.PUMPKIN);
		JUICE_MAP.put(HavenMod.WHITE_PUMPKIN.getGourd().ITEM, PUMPKIN_JUICE);
		RegisterJuice("sea_pickle", SEA_PICKLE_JUICE, Items.SEA_PICKLE);
		RegisterJuice("strawberry", STRAWBERRY_JUICE, STRAWBERRY);
		RegisterJuice("sweet_berry", SWEET_BERRY_JUICE, Items.SWEET_BERRIES);
		RegisterJuice("tomato", TOMATO_JUICE, ItemsRegistry.TOMATO.get());
		//Smoothies
		Register("apple_smoothie", APPLE_SMOOTHIE);
		Register("beetroot_smoothie", BEETROOT_SMOOTHIE);
		Register("black_apple_smoothie", BLACK_APPLE_SMOOTHIE);
		Register("cabbage_smoothie", CABBAGE_SMOOTHIE);
		Register("cactus_smoothie", CACTUS_SMOOTHIE);
		Register("carrot_smoothie", CARROT_SMOOTHIE);
		Register("cherry_smoothie", CHERRY_SMOOTHIE);
		Register("chorus_smoothie", CHORUS_SMOOTHIE);
		Register("glow_berry_smoothie", GLOW_BERRY_SMOOTHIE);
		Register("kelp_smoothie", KELP_SMOOTHIE);
		Register("melon_smoothie", MELON_SMOOTHIE);
		Register("onion_smoothie", ONION_SMOOTHIE);
		Register("potato_smoothie", POTATO_SMOOTHIE);
		Register("pumpkin_smoothie", PUMPKIN_SMOOTHIE);
		Register("sea_pickle_smoothie", SEA_PICKLE_SMOOTHIE);
		Register("strawberry_smoothie", STRAWBERRY_SMOOTHIE);
		Register("sweet_berry_smoothie", SWEET_BERRY_SMOOTHIE);
		Register("tomato_smoothie", TOMATO_SMOOTHIE);
	}
	public static void RegisterMilkshakesIceCream() {
		Register("milkshake", MILKSHAKE);
		Register("chocolate_milkshake", CHOCOLATE_MILKSHAKE);
		Register("coffee_milkshake", COFFEE_MILKSHAKE);
		Register("strawberry_milkshake", STRAWBERRY_MILKSHAKE);
		Register("chocolate_chip_milkshake", CHOCOLATE_CHIP_MILKSHAKE);
		Register("ice_cream", ICE_CREAM);
		Register("chocolate_ice_cream", CHOCOLATE_ICE_CREAM);
		Register("coffee_ice_cream", COFFEE_ICE_CREAM);
		Register("strawberry_ice_cream", STRAWBERRY_ICE_CREAM);
		Register("chocolate_chip_ice_cream", CHOCOLATE_CHIP_ICE_CREAM);
	}
	public static void RegisterBamboo() {
		Register(BAMBOO_MATERIAL);
		Register("dried_bamboo", DRIED_BAMBOO_BLOCK);
		Register("potted_dried_bamboo", POTTED_DRIED_BAMBOO);
		Register(DRIED_BAMBOO_MATERIAL);
	}
	public static void RegisterVanillaWood() {
		Register(ACACIA_MATERIAL);
		Register(BIRCH_MATERIAL);
		Register(DARK_OAK_MATERIAL);
		Register(JUNGLE_MATERIAL);
		//Register(OAK_MATERIAL);
		Register(SPRUCE_MATERIAL);
		Register(CRIMSON_MATERIAL);
		Register(WARPED_MATERIAL);
	}
	public static void RegisterAmberFungus() {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_forest_vegetation"), GILDED_FOREST_VEGETATION);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("patch_gilded_roots"), PATCH_GILDED_ROOTS);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_fungi"), GILDED_FUNGI);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_fungi_planted"), GILDED_FUNGI_PLANTED);
		Register("gilded_nylium", GILDED_NYLIUM);
		Register("gilded_roots", GILDED_ROOTS);
		Register(GILDED_MATERIAL);
	}
	public static void RegisterBackport() {
		//Goat Horn
		Register("goat_horn", GOAT_HORN);
		//Music Discs
		Register("music_disc_otherside", MUSIC_DISC_OTHERSIDE);
		Register("music_disc_5", MUSIC_DISC_5);
		Register("disc_fragment_5", DISC_FRAGMENT_5);
		//Mud
		Register("mud", MUD);
		Register("packed_mud", PACKED_MUD);
		Register("mud_bricks", MUD_BRICKS);
		Register("mud_brick_slab", MUD_BRICK_SLAB);
		Register("mud_brick_stairs", MUD_BRICK_STAIRS);
		Register("mud_brick_wall", MUD_BRICK_WALL);
		DispenserBlock.registerBehavior(Items.POTION, new ItemDispenserBehavior(){
			private final ItemDispenserBehavior fallback = new ItemDispenserBehavior();

			@Override
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				if (PotionUtil.getPotion(stack) != Potions.WATER) {
					return this.fallback.dispense(pointer, stack);
				}
				ServerWorld serverWorld = pointer.getWorld();
				BlockPos blockPos = pointer.getPos();
				BlockPos blockPos2 = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				if (serverWorld.getBlockState(blockPos2).isIn(HavenTags.Blocks.CONVERTIBLE_TO_MUD)) {
					if (!serverWorld.isClient) {
						for (int i = 0; i < 5; ++i) {
							serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + serverWorld.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + serverWorld.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
						}
					}
					serverWorld.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					serverWorld.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
					serverWorld.setBlockState(blockPos2, MUD.BLOCK.getDefaultState());
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				return this.fallback.dispense(pointer, stack);
			}
		});
		//Mangrove
		Register(MANGROVE_MATERIAL);
		Register("mangrove_roots", MANGROVE_ROOTS);
		CompostingChanceRegistry.INSTANCE.add(MANGROVE_ROOTS.ITEM, 0.3F);
		FlammableBlockRegistry.getDefaultInstance().add(MANGROVE_ROOTS.BLOCK, 5, 20);
		Register("muddy_mangrove_roots", MUDDY_MANGROVE_ROOTS);
		//Frogs
		Register("ochre_froglight", OCHRE_FROGLIGHT);
		Register("verdant_froglight", VERDANT_FROGLIGHT);
		Register("pearlescent_froglight", PEARLESCENT_FROGLIGHT);
		//Deep Dark
		Register("reinforced_deepslate", REINFORCED_DEEPSLATE);
		Register("echo_shard", ECHO_SHARD);
	}
	public static void RegisterCandy() {
		Register("cinnamon_bean", CINNAMON_BEAN);
		Register("pink_cotton_candy", PINK_COTTON_CANDY);
		Register("blue_cotton_candy", BLUE_COTTON_CANDY);
		Register("candy_cane", CANDY_CANE);
		Register("caramel", CARAMEL);
		Register("lollipop", LOLLIPOP);
		Register("caramel_apple", CARAMEL_APPLE);
		//Chocolate
		Register("milk_chocolate", MILK_CHOCOLATE);
		Register("dark_chocolate", DARK_CHOCOLATE);
		Register("white_chocolate", WHITE_CHOCOLATE);
		//Marshmallows
		Register("marshmallow", MARSHMALLOW);
		Register("roast_marshmallow", ROAST_MARSHMALLOW);
		Register("marshmallow_on_stick", MARSHMALLOW_ON_STICK);
		Register("roast_marshmallow_on_stick", ROAST_MARSHMALLOW_ON_STICK);
		//Amethyst & Rock Candy
		Register("amethyst_candy", AMETHYST_CANDY);
		for(DyeColor color : COLORS) Register(color.getName() + "_rock_candy", ROCK_CANDIES.get(color));
	}
	public static void RegisterBuckets(String name, BucketProvider bucketProvider) {
		Item bucket = bucketProvider.getBucket();
		Register(name + "_bucket", bucket);
		Item waterBucket = bucketProvider.getWaterBucket();
		Register(name + "_water_bucket", waterBucket);
		Item lavaBucket = bucketProvider.getLavaBucket();
		if (lavaBucket != null) Register(name + "_lava_bucket", bucketProvider.getLavaBucket());
		Item powderSnowBucket = bucketProvider.getPowderSnowBucket();
		Register(name + "_powder_snow_bucket", bucketProvider.getPowderSnowBucket());
		Item bloodBucket = bucketProvider.getBloodBucket();
		Register(name + "_blood_bucket",bloodBucket);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(bloodBucket, BloodCauldronBlock.FillFromBucket(bucket));
		Item mudBucket = bucketProvider.getMudBucket();
		Register(name + "_mud_bucket", mudBucket);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(mudBucket, MudCauldronBlock.FillFromBucket(bucket));
		DispenserBlock.registerBehavior(bucket, new ItemDispenserBehavior() {
			private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				WorldAccess worldAccess = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
				BlockState blockState = worldAccess.getBlockState(blockPos);
				Block block = blockState.getBlock();
				if (block instanceof FluidDrainable) {
					ItemStack itemStack = ((FluidDrainable)block).tryDrainFluid(worldAccess, blockPos, blockState);
					itemStack = bucketProvider.bucketInMaterial(itemStack);
					if (itemStack.isEmpty()) return super.dispenseSilently(pointer, stack);
					else {
						worldAccess.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, (BlockPos)blockPos);
						Item item2 = itemStack.getItem();
						stack.decrement(1);
						if (stack.isEmpty()) return new ItemStack(item2);
						else {
							if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(item2)) < 0) {
								this.fallbackBehavior.dispense(pointer, new ItemStack(item2));
							}
							return stack;
						}
					}
				}
				else return super.dispenseSilently(pointer, stack);
			}
		});
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(waterBucket, (state, world, pos, player, hand, stack) -> {
			return BucketUtils.fillCauldron(world, pos, player, hand, stack,
					(BlockState)Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
					SoundEvents.ITEM_BUCKET_EMPTY, bucket);
		});
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(bucket, (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(waterBucket),
					(statex) -> (Integer)statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL);
		});
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(powderSnowBucket, (state, world, pos, player, hand, stack) -> {
			return BucketUtils.fillCauldron(world, pos, player, hand, stack,
					(BlockState)Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
					SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, bucket);
		});
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.put(bucket, (state, world, pos, player, hand, stack) -> {
			return CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(powderSnowBucket),
					(statex) -> (Integer)statex.get(LeveledCauldronBlock.LEVEL) == 3, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
		});
		Item milkBucket = bucketProvider.getMilkBucket();
		Register(name + "_milk_bucket", milkBucket);
		Register(name + "_chocolate_milk_bucket", bucketProvider.getChocolateMilkBucket());
		Register(name + "_coffee_milk_bucket", bucketProvider.getCoffeeMilkBucket());
		Register(name + "_strawberry_milk_bucket", bucketProvider.getStrawberryMilkBucket());
		Item cottageCheeseBucket = bucketProvider.getCottageCheeseBucket();
		Register(name + "_cottage_cheese_bucket", cottageCheeseBucket);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(milkBucket, MilkCauldronBlock.FillFromBucket(bucket));
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(cottageCheeseBucket, MilkCauldronBlock.FillCheeseFromBucket(bucket));
	}
	public static void RegisterThrowableTomatoes() {
		Register("throwable_tomato", THROWABLE_TOMATO_ITEM);
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY);
		Register("thrown_tomato", TOMATO_PARTICLE);
		Register("boo", BOO_EFFECT);
		Register("killjoy", KILLJOY_EFFECT);
		Register("tomato_soup", TOMATO_SOUP);
	}
	public static void RegisterServerBlood() {
		Register("blood_bubble", BLOOD_BUBBLE);
		Register("blood_splash", BLOOD_SPLASH);
		Register("dripping_blood", DRIPPING_BLOOD);
		Register("falling_blood", FALLING_BLOOD);
		Register("falling_dripstone_blood", FALLING_DRIPSTONE_BLOOD);

		Register("blood_block", BLOOD_BLOCK);
		Register(BLOOD_MATERIAL);
		Register("dried_blood_block", DRIED_BLOOD_BLOCK);
		Register(DRIED_BLOOD_MATERIAL);
		//Liquid Blood, Bucket, and Bottles
		Register("blood_bucket", BLOOD_BUCKET);
		Register("blood_cauldron", BLOOD_CAULDRON);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BUCKET, BloodCauldronBlock.FillFromBucket(Items.BUCKET));
		CauldronFluidContent.registerCauldron(BLOOD_CAULDRON, STILL_BLOOD_FLUID, FluidConstants.BOTTLE, LeveledCauldronBlock.LEVEL);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, BloodCauldronBlock.EMPTY_TO_BOTTLE);
		Register("blood_bottle", BLOOD_BOTTLE);
		Register("lava_bottle", LAVA_BOTTLE);
		Register("sugar_water_bottle", SUGAR_WATER_BOTTLE);
		Register("ichored", ICHORED_EFFECT);
		Register("ichor_bottle", ICHOR_BOTTLE);
		Register("slime_bottle", SLIME_BOTTLE);
		Register("magma_cream_bottle", MAGMA_CREAM_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
		Register("still_blood", STILL_BLOOD_FLUID);
		Register("flowing_blood", FLOWING_BLOOD_FLUID);
		Register("blood_fluid_block", BLOOD_FLUID_BLOCK);
		//Blood Syringes
		Register("blood_syringe", BLOOD_SYRINGE);
		Register("lava_syringe", LAVA_SYRINGE);
		Register("water_syringe", WATER_SYRINGE);
		Register("sugar_water_syringe", SUGAR_WATER_SYRINGE);
		Register("magma_cream_syringe", MAGMA_CREAM_SYRINGE);
		Register("slime_syringe", SLIME_SYRINGE);
		Register("sludge_syringe", SLUDGE_SYRINGE);
		Register("allay_blood_syringe", ALLAY_BLOOD_SYRINGE);
		Register("anemic_blood_syringe", ANEMIC_BLOOD_SYRINGE);
		Register("avian_blood_syringe", AVIAN_BLOOD_SYRINGE);
		Register("axolotl_blood_syringe", AXOLOTL_BLOOD_SYRINGE);
		Register("bat_blood_syringe", BAT_BLOOD_SYRINGE);
		Register("bear_blood_syringe", BEAR_BLOOD_SYRINGE);
		Register("bee_blood_syringe", BEE_BLOOD_SYRINGE);
		Register("bee_enderman_blood_syringe", BEE_ENDERMAN_BLOOD_SYRINGE);
		Register("canine_blood_syringe", CANINE_BLOOD_SYRINGE);
		Register("chorus_syringe", CHORUS_SYRINGE);
		Register("cow_blood_syringe", COW_BLOOD_SYRINGE);
		Register("creeper_blood_syringe", CREEPER_BLOOD_SYRINGE);
		Register("diseased_feline_blood_syringe", DISEASED_FELINE_BLOOD_SYRINGE);
		Register("dolphin_blood_syringe", DOLPHIN_BLOOD_SYRINGE);
		Register("dragon_blood_syringe", DRAGON_BLOOD_SYRINGE);
		Register("enderman_blood_syringe", ENDERMAN_BLOOD_SYRINGE);
		Register("equine_blood_syringe", EQUINE_BLOOD_SYRINGE);
		Register("feline_blood_syringe", FELINE_BLOOD_SYRINGE);
		Register("fish_blood_syringe", FISH_BLOOD_SYRINGE);
		Register("goat_blood_syringe", GOAT_BLOOD_SYRINGE);
		Register("honey_syringe", HONEY_SYRINGE);
		Register("ichor_syringe", ICHOR_SYRINGE);
		Register("insect_blood_syringe", INSECT_BLOOD_SYRINGE);
		Register("llama_blood_syringe", LLAMA_BLOOD_SYRINGE);
		Register("nephal_blood_syringe", NEPHAL_BLOOD_SYRINGE);
		Register("nether_blood_syringe", NETHER_BLOOD_SYRINGE);
		Register("nether_royalty_blood_syringe", NETHER_ROYALTY_BLOOD_SYRINGE);
		Register("phantom_blood_syringe", PHANTOM_BLOOD_SYRINGE);
		Register("pig_blood_syringe", PIG_BLOOD_SYRINGE);
		Register("rabbit_blood_syringe", RABBIT_BLOOD_SYRINGE);
		Register("ravager_blood_syringe", RAVAGER_BLOOD_SYRINGE);
		Register("sheep_blood_syringe", SHEEP_BLOOD_SYRINGE);
		Register("spider_blood_syringe", SPIDER_BLOOD_SYRINGE);
		Register("squid_blood_syringe", SQUID_BLOOD_SYRINGE);
		Register("strider_blood_syringe", STRIDER_BLOOD_SYRINGE);
		Register("turtle_blood_syringe", TURTLE_BLOOD_SYRINGE);
		Register("vampire_blood_syringe", VAMPIRE_BLOOD_SYRINGE);
		Register("vex_blood_syringe", VEX_BLOOD_SYRINGE);
		Register("villager_blood_syringe", VILLAGER_BLOOD_SYRINGE);
		Register("warden_blood_syringe", WARDEN_BLOOD_SYRINGE);
		Register("zombie_blood_syringe", ZOMBIE_BLOOD_SYRINGE);
		//Non-blood syringes
		Register("confetti_syringe", CONFETTI_SYRINGE);
		Register("dragon_breath_syringe", DRAGON_BREATH_SYRINGE);
		//Syringes
		Register("deteriorating", DETERIORATION_EFFECT);
		Register("secret_ingredient", SECRET_INGREDIENT);
		Register("syringe", SYRINGE);
		Register("dirty_syringe", DIRTY_SYRINGE);
		Register("syringe_blindness", SYRINGE_BLINDNESS);
		Register("syringe_mining_fatigue", SYRINGE_MINING_FATIGUE);
		Register("syringe_poison", SYRINGE_POISON);
		Register("syringe_regeneration", SYRINGE_REGENERATION);
		Register("syringe_saturation", SYRINGE_SATURATION);
		Register("syringe_slowness", SYRINGE_SLOWNESS);
		Register("syringe_weakness", SYRINGE_WEAKNESS);
		Register("syringe_wither", SYRINGE_WITHER);
		Register("syringe_exp1", SYRINGE_EXP1);
		Register("syringe_exp2", SYRINGE_EXP2);
		Register("syringe_exp3", SYRINGE_EXP3);
	}
	public static void RegisterCommands() {
		CommandRegistrationCallback.EVENT.register(ChorusCommand::register);
	}
	public static void RegisterOriginPowers() {
		Register(BloodTypePower::createFactory);
		Register(UnfreezingPower::createFactory);
		Register(ChorusTeleportPower::createFactory);
		Register(LactoseIntolerantPower::createFactory);
	}
	public static void RegisterAngelBat() {
		Register("angel_bat", ANGEL_BAT_ENTITY);
		SpawnRestrictionAccessor.callRegister(ANGEL_BAT_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelBatEntity::CanSpawn);
		FabricDefaultAttributeRegistry.register(ANGEL_BAT_ENTITY, AngelBatEntity.createBatAttributes());
	}
	public static void RegisterMelonGolem() {
		//Soul Jack o' Lantern
		Register("soul_jack_o_lantern", SOUL_JACK_O_LANTERN);
		//Melon
		Register("melon_seeds_projectile", MELON_SEED_PROJECTILE_ENTITY);
		Register("melon_golem", MELON_GOLEM_ENTITY);
		FabricDefaultAttributeRegistry.register(MELON_GOLEM_ENTITY, MelonGolemEntity.createMelonGolemAttributes());
		Register("carved_melon", CARVED_MELON);
		CompostingChanceRegistry.INSTANCE.add(CARVED_MELON.ITEM, 0.65F);
		Register("melon_lantern", MELON_LANTERN);
		Register("soul_melon_lantern", SOUL_MELON_LANTERN);
		//White Pumpkin
		Register("white_snow_golem", WHITE_SNOW_GOLEM_ENTITY);
		FabricDefaultAttributeRegistry.register(WHITE_SNOW_GOLEM_ENTITY, WhiteSnowGolemEntity.createSnowGolemAttributes());
		Register("white_pumpkin", WHITE_PUMPKIN);
		Register("white_jack_o_lantern", WHITE_JACK_O_LANTERN);
		Register("white_soul_jack_o_lantern", WHITE_SOUL_JACK_O_LANTERN);
		//Rotten Pumpkin
		Register("rotten_pumpkin", ROTTEN_PUMPKIN);
		Register("carved_rotten_pumpkin", CARVED_ROTTEN_PUMPKIN);
		Register("rotten_jack_o_lantern", ROTTEN_JACK_O_LANTERN);
		Register("rotten_soul_jack_o_lantern", ROTTEN_SOUL_JACK_O_LANTERN);
		Register("rotten_pumpkin_seeds", ROTTEN_PUMPKIN_SEEDS);
		CompostingChanceRegistry.INSTANCE.add(ROTTEN_PUMPKIN_SEEDS, 0.65F);
	}
	public static void RegisterRainbow() {
		Register("rainbow_sheep", RAINBOW_SHEEP_ENTITY);
		SpawnRestrictionAccessor.callRegister(RAINBOW_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntity.createSheepAttributes());
		Register("rainbow_sheep_spawn_egg", RAINBOW_SHEEP_SPAWN_EGG);
		Register("rainbow_wool", RAINBOW_WOOL);
		FlammableBlockRegistry.getDefaultInstance().add(RAINBOW_WOOL.BLOCK, 30, 60);
		Register("rainbow_carpet", RAINBOW_CARPET);
		FlammableBlockRegistry.getDefaultInstance().add(RAINBOW_WOOL.BLOCK, 60, 20);
		Register("rainbow_bed", RAINBOW_BED);
	}
	public static void RegisterChickenVariants() {
		Register("fancy_chicken", FANCY_CHICKEN_ENTITY);
		SpawnRestrictionAccessor.callRegister(FANCY_CHICKEN_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(FANCY_CHICKEN_ENTITY, FancyChickenEntity.createChickenAttributes());
		Register("fancy_chicken_spawn_egg", FANCY_CHICKEN_SPAWN_EGG);
		Register("fancy_feather", FANCY_FEATHER);
	}
	public static void RegisterCowVariants() {
		//Milk Cows
		Register("cowcoa", COWCOA_ENTITY);
		SpawnRestrictionAccessor.callRegister(COWCOA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CowcoaEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(COWCOA_ENTITY, CowcoaEntity.createCowAttributes());
		Register("cowcoa_spawn_egg", COACOW_SPAWN_EGG);
		Register("cowfee", COWFEE_ENTITY);
		SpawnRestrictionAccessor.callRegister(COWFEE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CowfeeEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(COWFEE_ENTITY, CowfeeEntity.createCowAttributes());
		Register("cowfee_spawn_egg", COWFEE_SPAWN_EGG);
		Register("strawbovine", STRAWBOVINE_ENTITY);
		SpawnRestrictionAccessor.callRegister(STRAWBOVINE_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StrawbovineEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(STRAWBOVINE_ENTITY, StrawbovineEntity.createCowAttributes());
		Register("strawbovine_spawn_egg", STRAWBOVINE_SPAWN_EGG);
		//Flower Mooshrooms
		Register("moobloom", MOOBLOOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOBLOOM_ENTITY, MoobloomEntity.createCowAttributes());
		Register("moobloom_spawn_egg", MOOBLOOM_SPAWN_EGG);
		Register("moolip", MOOLIP_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOLIP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoolipEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOLIP_ENTITY, MoolipEntity.createCowAttributes());
		Register("moolip_spawn_egg", MOOLIP_SPAWN_EGG);
		Register("mooblossom", MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOOBLOSSOM_ENTITY, MooblossomEntity.createCowAttributes());
		Register("mooblossom_spawn_egg", MOOBLOSSOM_SPAWN_EGG);
		Register("magenta_mooblossom_tulip", MAGENTA_MOOBLOSSOM_TULIP);
		Register("orange_mooblossom", ORANGE_MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(ORANGE_MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, OrangeMooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(ORANGE_MOOBLOSSOM_ENTITY, OrangeMooblossomEntity.createCowAttributes());
		Register("orange_mooblossom_spawn_egg", ORANGE_MOOBLOSSOM_SPAWN_EGG);
		Register("orange_mooblossom_tulip", ORANGE_MOOBLOSSOM_TULIP);
		Register("pink_mooblossom", PINK_MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(PINK_MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PinkMooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(PINK_MOOBLOSSOM_ENTITY, PinkMooblossomEntity.createCowAttributes());
		Register("pink_mooblossom_spawn_egg", PINK_MOOBLOSSOM_SPAWN_EGG);
		Register("pink_mooblossom_tulip", PINK_MOOBLOSSOM_TULIP);
		Register("red_mooblossom", RED_MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(RED_MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RedMooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(RED_MOOBLOSSOM_ENTITY, RedMooblossomEntity.createCowAttributes());
		Register("red_mooblossom_spawn_egg", RED_MOOBLOSSOM_SPAWN_EGG);
		Register("red_mooblossom_tulip", RED_MOOBLOSSOM_TULIP);
		Register("white_mooblossom", WHITE_MOOBLOSSOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(WHITE_MOOBLOSSOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WhiteMooblossomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(WHITE_MOOBLOSSOM_ENTITY, WhiteMooblossomEntity.createCowAttributes());
		Register("white_mooblossom_spawn_egg", WHITE_MOOBLOSSOM_SPAWN_EGG);
		Register("white_mooblossom_tulip", WHITE_MOOBLOSSOM_TULIP);
		//Nether Mooshrooms
		Register("gilded_mooshroom", GILDED_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(GILDED_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GildedMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(GILDED_MOOSHROOM_ENTITY, GildedMooshroomEntity.createCowAttributes());
		Register("gilded_mooshroom_spawn_egg", GILDED_MOOSHROOM_SPAWN_EGG);
		Register("crimson_mooshroom", CRIMSON_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(CRIMSON_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrimsonMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(CRIMSON_MOOSHROOM_ENTITY, CrimsonMooshroomEntity.createCowAttributes());
		Register("crimson_mooshroom_spawn_egg", CRIMSON_MOOSHROOM_SPAWN_EGG);
		Register("warped_mooshroom", WARPED_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(WARPED_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpedMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(WARPED_MOOSHROOM_ENTITY, WarpedMooshroomEntity.createCowAttributes());
		Register("warped_mooshroom_spawn_egg", WARPED_MOOSHROOM_SPAWN_EGG);
	}
	public static void RegisterMilks() {
		Register("milk_bowl", MILK_BOWL);
		Register("chocolate_milk_bowl", CHOCOLATE_MILK_BOWL);
		Register("coffee_milk_bowl", COFFEE_MILK_BOWL);
		Register("strawberry_milk_bowl", STRAWBERRY_MILK_BOWL);
		Register("cottage_cheese_bowl", COTTAGE_CHEESE_BOWL);
		Register("chocolate_milk_bottle", CHOCOLATE_MILK_BOTTLE);
		Register("coffee_milk_bottle", COFFEE_MILK_BOTTLE);
		Register("strawberry_milk_bottle", STRAWBERRY_MILK_BOTTLE);
		Register("milk_cauldron", MILK_CAULDRON);
		Register("cottage_cheese_cauldron", COTTAGE_CHEESE_CAULDRON);
		Register("cheese_cauldron", CHEESE_CAULDRON);
		Register("cottage_cheese_block", COTTAGE_CHEESE_BLOCK);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, MilkCauldronBlock.FillFromBucket(Items.BUCKET));
		Register("chocolate_milk_bucket", CHOCOLATE_MILK_BUCKET);
		Register("coffee_milk_bucket", COFFEE_MILK_BUCKET);
		Register("strawberry_milk_bucket", STRAWBERRY_MILK_BUCKET);
		Register("cottage_cheese_bucket", COTTAGE_CHEESE_BUCKET);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(COTTAGE_CHEESE_BUCKET, MilkCauldronBlock.FillCheeseFromBucket(Items.BUCKET));
		Register("cheese_block", CHEESE_BLOCK);
		Register("cheese", CHEESE);
		Register("grilled_cheese", GRILLED_CHEESE);
	}
	public static void RegisterCakes() {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		Register(CHOCOLATE_CAKE);
		COMPOST.add(CHOCOLATE_CAKE.getCake().ITEM, 1F);
		Register(STRAWBERRY_CAKE);
		COMPOST.add(STRAWBERRY_CAKE.getCake().ITEM, 1F);
		Register(COFFEE_CAKE);
		COMPOST.add(COFFEE_CAKE.getCake().ITEM, 1F);
		Register(CARROT_CAKE);
		COMPOST.add(CARROT_CAKE.getCake().ITEM, 1F);
		Register(CONFETTI_CAKE);
		COMPOST.add(CONFETTI_CAKE.getCake().ITEM, 1F);
		Register(CHORUS_CAKE);
		COMPOST.add(CHORUS_CAKE.getCake().ITEM, 1F);
	}
	public static void RegisterBottledConfetti() {
		Register("bottled_confetti", BOTTLED_CONFETTI_ITEM);
		Register("bottled_confetti", BOTTLED_CONFETTI_ENTITY);
		Register("dropped_confetti", DROPPED_CONFETTI_ENTITY);
		Register("confetti_cloud", CONFETTI_CLOUD_ENTITY);
		Register("dropped_dragon_breath", DROPPED_DRAGON_BREATH_ENTITY);
		Register("dragon_breath_cloud", DRAGON_BREATH_CLOUD_ENTITY);
	}
	public static void RegisterCopper() {
		Register("copper_flame", COPPER_FLAME_PARTICLE);
		Register(COPPER_MATERIAL);
		Register("medium_weighted_pressure_plate", MEDIUM_WEIGHTED_PRESSURE_PLATE);
	}
	public static void RegisterGold() {
		Register("gold_flame", GOLD_FLAME_PARTICLE);
		Register(GOLD_MATERIAL);
	}
	public static void RegisterIron() {
		Register("iron_flame", IRON_FLAME_PARTICLE);
		Register(IRON_MATERIAL);
		Register("dark_iron_ingot", DARK_IRON_INGOT);
		Register(DARK_IRON_MATERIAL);
		Register("dark_heavy_weighted_pressure_plate", DARK_HEAVY_WEIGHTED_PRESSURE_PLATE);
	}
	public static void RegisterNetherite() {
		Register("netherite_flame", NETHERITE_FLAME_PARTICLE);
		Register(NETHERITE_MATERIAL);
		Register("crushing_weighted_pressure_plate", CRUSHING_WEIGHTED_PRESSURE_PLATE);
	}
	public static void RegisterLiquidMud() {
		Register("mud_bubble", MUD_BUBBLE);
		Register("mud_splash", MUD_SPLASH);
		Register("dripping_mud", DRIPPING_MUD);
		Register("falling_mud", FALLING_MUD);
		Register("falling_dripstone_mud", FALLING_DRIPSTONE_MUD);
		Register("mud_bucket", MUD_BUCKET);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(MUD_BUCKET, MudCauldronBlock.FillFromBucket(Items.BUCKET));
		Register("mud_cauldron", MUD_CAULDRON);
		Register("still_mud", STILL_MUD_FLUID);
		Register("flowing_mud", FLOWING_MUD_FLUID);
		Register("mud_fluid_block", MUD_FLUID_BLOCK);
	}
	public static void RegisterObsidian() {
		Register(OBSIDIAN_MATERIAL);
		Register(CRYING_OBSIDIAN_MATERIAL);
		Register("landing_obsidian_blood", LANDING_OBSIDIAN_BLOOD);
		Register("falling_obsidian_blood", FALLING_OBSIDIAN_BLOOD);
		Register("dripping_obsidian_blood", DRIPPING_OBSIDIAN_BLOOD);
		Register("bleeding_obsidian", BLEEDING_OBSIDIAN);
		Register(BLEEDING_OBSIDIAN_MATERIAL);
	}

	public static void RegisterAll() {
		RegisterAnchors();
		Register("glow_flame", GLOW_FLAME);
		Register("unlit", UNLIT_TORCH);
		Register("unlit_soul", UNLIT_SOUL_TORCH);
		Register("unlit_lantern", UNLIT_LANTERN);
		Register("unlit_soul_lantern", UNLIT_SOUL_LANTERN);
		Register("underwater", UNDERWATER_TORCH);
		Register(BONE_MATERIAL);
		Register("tinker_toy", TINKER_TOY);
		Register("charcoal_block", CHARCOAL_BLOCK);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK.ITEM, 16000);
		RegisterFlowers();
		Register(STUDDED_LEATHER_MATERIAL);
		Register(AMETHYST_MATERIAL);
		Register(EMERALD_MATERIAL);
		Register(DIAMOND_MATERIAL);
		Register(QUARTZ_MATERIAL);
		RegisterObsidian();
		Register(DRIPSTONE_MATERIAL);
		Register(TUFF_MATERIAL);
		Register("pteror", PTEROR);
		Register("sbehesohe", SBEHESOHE);
		Register("broken_bottle", BROKEN_BOTTLE);
		Register("locket", LOCKET);
		Register("emerald_locket", EMERALD_LOCKET);
		RegisterSoftTNT();
		RegisterCoffee();
		Register("haven_boat", BOAT_ENTITY);
		RegisterCherry();
		RegisterCinnamon();
		RegisterBamboo();
		RegisterVanillaWood();
		RegisterAmberFungus();
		Register(WOOD_MATERIAL);
		RegisterThrowableTomatoes();
		RegisterServerBlood();
		JUICE_MAP.put(BLOOD_BLOCK.ITEM, BLOOD_BOTTLE);
		JUICE_MAP.put(Items.SLIME_BLOCK, SLIME_BOTTLE);
		JUICE_MAP.put(Items.MAGMA_BLOCK, MAGMA_CREAM_BOTTLE);
		RegisterCommands();
		RegisterOriginPowers();
		Register("grappling_rod", GRAPPLING_ROD);
		RegisterAngelBat();
		RegisterMelonGolem();
		RegisterRainbow();
		RegisterChickenVariants();
		RegisterCowVariants();
		Register("ramen", RAMEN);
		Register("stir_fry", STIR_FRY);
		RegisterStrawberries();
		RegisterJuice();
		RegisterMilkshakesIceCream();
		RegisterCandy();
		RegisterCookies();
		RegisterMilks();
		Register("hotdog", HOTDOG);
		RegisterCakes();
		RegisterBottledConfetti();
		RegisterCopper();
		RegisterGold();
		RegisterIron();
		RegisterNetherite();
		Register("wax_block", WAX_BLOCK);
		Register("horn", HORN);
		RegisterLiquidMud();
		RegisterBackport();
		//Compostable Items
		for(FlowerContainer flower : FLOWERS) CompostingChanceRegistry.INSTANCE.add(flower.ITEM, 0.65F);
		for(BlockContainer flower : TALL_FLOWERS) CompostingChanceRegistry.INSTANCE.add(flower.ITEM, 0.65F);
	}
}
