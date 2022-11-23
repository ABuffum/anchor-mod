package haven;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.blocks.*;
import haven.blocks.cake.CakeContainer;
import haven.blocks.gourd.CarvedMelonBlock;
import haven.blocks.gourd.CarvedWhitePumpkinBlock;
import haven.blocks.lighting.DynamicLightManager;
import haven.blocks.lighting.DynamicLitBlock;
import haven.blocks.mud.MudCauldronBlock;
import haven.containers.OxidizableBlockContainer;
import haven.containers.BoatContainer;
import haven.dispenser.ModBoatDispenserBehavior;
import haven.command.ChorusCommand;
import haven.containers.*;
import haven.deprecated.Deprecated;
import haven.entities.passive.*;
import haven.entities.passive.cow.*;
import haven.entities.passive.sheep.MossySheepEntity;
import haven.entities.passive.sheep.RainbowSheepEntity;
import haven.entities.tnt.SoftTntEntity;
import haven.gen.DeepDarkBiome;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.*;
import haven.origins.powers.*;
import haven.rendering.gui.WoodcutterScreen;
import haven.util.*;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
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
import net.minecraft.fluid.Fluids;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static haven.ModBase.*;

public class ModRegistry {
	public static Block Register(String path, Block block) { return Registry.register(Registry.BLOCK, ID(path), block); }
	public static Item Register(String path, Item item) { return Registry.register(Registry.ITEM, ID(path), item); }
	public static void Register(String path, BlockContainer container) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, container.getBlock());
		Registry.register(Registry.ITEM, id, container.getItem());
	}
	public static void Register(String path, WaxedBlockContainer waxed) {
		Register(path, (BlockContainer)waxed);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.getBlock(), waxed.getBlock());
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
		UNLIT_LANTERNS.put(container.getUnaffected().getBlock(), unaffected);
		UNLIT_LANTERNS.put(container.getExposed().getBlock(), exposed);
		UNLIT_LANTERNS.put(container.getWeathered().getBlock(), weathered);
		UNLIT_LANTERNS.put(container.getOxidized().getBlock(), oxidized);
		//Waxed Unlit
		Block waxed_unaffected = container.getUnlitWaxedUnaffected(), waxed_exposed = container.getUnlitWaxedExposed();
		Block waxed_weathered = container.getUnlitWaxedWeathered(), waxed_oxidized = container.getUnlitWaxedOxidized();
		Register("unlit_waxed_" + path, waxed_unaffected);
		Register("unlit_waxed_exposed_" + path, waxed_exposed);
		Register("unlit_waxed_weathered_" + path, waxed_weathered);
		Register("unlit_waxed_oxidized_" + path, waxed_oxidized);
		UNLIT_LANTERNS.put(container.getWaxedUnaffected().getBlock(), waxed_unaffected);
		UNLIT_LANTERNS.put(container.getWaxedExposed().getBlock(), waxed_exposed);
		UNLIT_LANTERNS.put(container.getWaxedWeathered().getBlock(), waxed_weathered);
		UNLIT_LANTERNS.put(container.getWaxedOxidized().getBlock(), waxed_oxidized);
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
		CompostingChanceRegistry.INSTANCE.add(gourd_.getItem(), 0.65F);
		BlockContainer carved = gourd.getCarved();
		Register("carved_" + name, carved);
		CompostingChanceRegistry.INSTANCE.add(carved.getItem(), 0.65F);
		Item seeds = gourd.getSeeds();
		Register(name + "_seeds", seeds);
		Register(name += "_stem", gourd.getStem());
		Register("attached_" + name, gourd.getAttachedStem());
		CompostingChanceRegistry.INSTANCE.add(gourd.getSeeds(), 0.65F);
	}
	public static void Register(CakeContainer cake) {
		String name = cake.getFlavor().getName();
		Register(name + "_cake", cake.getCake());
		String cc = name + "_candle_cake";
		Register(cc, cake.getCandleCake());
		Register(name + "_soul_candle_cake", cake.getSoulCandleCake());
		Register(name + "_ender_candle_cake", cake.getEnderCandleCake());
		for(DyeColor color : DyeColor.values()) Register(color.getName() + "_" + cc, cake.getCandleCake(color));
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
		Registry.register(Registry.BLOCK, id, potted.getBlock());
		Registry.register(Registry.ITEM, id, potted.getItem());
		String registryName = path.startsWith("minecraft:") ? ("minecraft:potted_" + path.substring("minecraft:".length())) : ("potted_" + path);
		Registry.register(Registry.BLOCK, ID(registryName), potted.getPotted());
	}
	public static void Register(String path, FlowerContainer flower) {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		Register(path, (PottedBlockContainer)flower);
		FlammableBlockRegistry.getDefaultInstance().add(flower.getBlock(), 60, 100);
		COMPOST.add(flower.getItem(), 0.65F);
		Register(path + "_petals", flower.PETALS);
		COMPOST.add(flower.PETALS, 0.325F);
		Register(path + "_seeds", flower.SEEDS);
		COMPOST.add(flower.SEEDS.getItem(), 0.3F);
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
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.getBlock(), waxed.getBlock());
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.getWallBlock(), waxed.getWallBlock());
		//Unlit
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.UNLIT.UNLIT, waxed.UNLIT.UNLIT);
		OxidationScale.WAXED_BLOCKS.put(waxed.UNWAXED.UNLIT.UNLIT_WALL, waxed.UNLIT.UNLIT_WALL);
	}
	public static void Register(String path, String wallPath, WallBlockContainer walled) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, walled.getBlock());
		Registry.register(Registry.BLOCK, ID(wallPath), walled.getWallBlock());
		Registry.register(Registry.ITEM, id, walled.getItem());
	}
	public static void Register(BaseMaterial material) {
		String name = material.getName();
		boolean flammable = material.isFlammable();
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		FuelRegistry FUEL = FuelRegistry.INSTANCE;
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		if (material instanceof PottedProvider potted) Register(name, potted.getPotted());
		else if (material instanceof Provider provider) Register(name, provider.get()); //PottedProvider registers the block for us
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
		if (material instanceof EnderTorchProvider enderTorch) Register(name + "_ender", enderTorch.getEnderTorch());
		if (material instanceof OxidizableEnderTorchProvider oxidizableEnderTorch) Register(name + "_ender", oxidizableEnderTorch.getOxidizableEnderTorch());
		if (material instanceof CampfireProvider campfire) Register(name + "_campfire", campfire.getCampfire());
		if (material instanceof SoulCampfireProvider soulCampfire) Register(name + "_soul_campfire", soulCampfire.getSoulCampfire());
		if (material instanceof EnderCampfireProvider enderCampfire) Register(name + "_ender_campfire", enderCampfire.getEnderCampfire());
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
		if (material instanceof EnderLanternProvider enderLantern) {
			Register(name + "_ender_lantern", enderLantern.getEnderLantern());
			Register("unlit_" + name + "_ender_lantern", enderLantern.getUnlitEnderLantern());
		}
		if (material instanceof OxidizableEnderLanternProvider oxidizableEnderLantern) Register(name + "_ender_lantern", oxidizableEnderLantern.getOxidizableEnderLantern());
		if (material instanceof ChainProvider chain) Register(name + "_chain", chain.getChain());
		if (material instanceof OxidizableChainProvider oxidizableChain) Register(name + "_chain", oxidizableChain.getOxidizableChain());
		if (material instanceof BarsProvider bars) Register(name + "_bars", bars.getBars());
		if (material instanceof OxidizableBarsProvider oxidizableBars) Register(name + "_bars", oxidizableBars.getOxidizableBars());
		if (material instanceof BlockProvider block) Register(name + "_block", block.getBlock());
		if (material instanceof BaleProvider bail) {
			BlockContainer pair = bail.getBale();
			Register(name + "_bale", pair);
			COMPOST.add(pair.getItem(), 0.85F);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 60, 20);
		}
		if (material instanceof BundleProvider bundle) {
			BlockContainer pair = bundle.getBundle();
			Register(name + "_bundle", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StrippedBundleProvider strippedBundle) {
			BlockContainer pair = strippedBundle.getStrippedBundle();
			String registerName = name.startsWith("minecraft:") ? ("minecraft:stripped_" + name.substring("minecraft:".length())) : ("stripped_" + name);
			Register(registerName + "_bundle", pair);
			Block strippedBundleBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedBundleBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof LogProvider log) {
			BlockContainer pair = log.getLog();
			Register(name + "_log", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StrippedLogProvider strippedLog) {
			BlockContainer pair = strippedLog.getStrippedLog();
			String registerName = name.startsWith("minecraft:") ? ("minecraft:stripped_" + name.substring("minecraft:".length())) : ("stripped_" + name);
			Register(registerName + "_log", pair);
			Block strippedLogBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedLogBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof WoodProvider wood) {
			BlockContainer pair = wood.getWood();
			Register(name + "_wood", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StrippedWoodProvider strippedWood) {
			BlockContainer pair = strippedWood.getStrippedWood();
			String registerName = name.startsWith("minecraft:") ? ("minecraft:stripped_" + name.substring("minecraft:".length())) : ("stripped_" + name);
			Register(registerName + "_wood", pair);
			Block strippedWoodBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedWoodBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StemProvider stem) {
			BlockContainer pair = stem.getStem();
			Register(name + "_stem", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StrippedStemProvider strippedStem) {
			BlockContainer pair = strippedStem.getStrippedStem();
			Register("stripped_" + name + "_stem", pair);
			Block strippedStemBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedStemBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof HyphaeProvider hyphae) {
			BlockContainer pair = hyphae.getHyphae();
			Register(name + "_hyphae", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof StrippedHyphaeProvider strippedHyphae) {
			BlockContainer pair = strippedHyphae.getStrippedHyphae();
			Register("stripped_" + name + "_hyphae", pair);
			Block strippedHyphaeBlock = pair.getBlock();
			if (flammable) {
				FLAMMABLE.add(strippedHyphaeBlock, 5, 5);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof LeavesProvider leaves) {
			BlockContainer pair = leaves.getLeaves();
			Register(name + "_leaves", pair);
			COMPOST.add(pair.getItem(), 0.3f);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 30, 60);
		}
		if (material instanceof WartBlockProvider wartBlock){
			BlockContainer pair = wartBlock.getWartBlock();
			Register(name + "_wart_block", pair);
			COMPOST.add(pair.getItem(), 0.85f);
		}
		if (material instanceof SaplingProvider saplingProvider) {
			SaplingContainer sapling = saplingProvider.getSapling();
			Register(name + "_sapling", sapling);
			COMPOST.add(sapling.getItem(), 0.3f);
		}
		if (material instanceof FungusProvider fungusProvider) {
			FungusContainer fungus = fungusProvider.getFungus();
			Register(name + "_fungus", fungus);
			COMPOST.add(fungus.getItem(), 0.65f);
		}
		if (material instanceof PropaguleProvider propaguleProvider) {
			PottedBlockContainer propagule = propaguleProvider.getPropagule();
			Register(name + "_propagule", propagule);
			COMPOST.add(propagule.getItem(), 0.3f);
		}
		if (material instanceof PlanksProvider planks) {
			BlockContainer pair = planks.getPlanks();
			Register(name + "_planks", pair);
			if (flammable) FLAMMABLE.add(pair.getBlock(), 5, 20);
		}
		if (material instanceof SlabProvider slab) {
			BlockContainer pair = slab.getSlab();
			Register(name + "_slab", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 150);
			}
		}
		if (material instanceof StairsProvider stairs) {
			BlockContainer pair = stairs.getStairs();
			Register(name + "_stairs", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof DoorProvider door) Register(name + "_door", door.getDoor());
		if (material instanceof TrapdoorProvider trapdoor) Register(name + "_trapdoor", trapdoor.getTrapdoor());
		if (material instanceof PressurePlateProvider pressurePlate) Register(name + "_pressure_plate", pressurePlate.getPressurePlate());
		if (material instanceof ButtonProvider button) Register(name + "_button", button.getButton());
		if (material instanceof OxidizableButtonProvider oxidizableButton) Register(name + "_button", oxidizableButton.getOxidizableButton());
		if (material instanceof FenceProvider fence) {
			BlockContainer pair = fence.getFence();
			Register(name + "_fence", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof FenceGateProvider fenceGate) {
			BlockContainer pair = fenceGate.getFenceGate();
			Register(name + "_fence_gate", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof BookshelfProvider bookshelf) {
			BlockContainer pair = bookshelf.getBookshelf();
			Register(name + "_bookshelf", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 30, 20);
				FUEL.add(pair.getItem(), 300);
			}
		}
		if (material instanceof ChiseledBookshelfProvider chiseledBookshelf) {
			BlockContainer pair = chiseledBookshelf.getChiseledBookshelf();
			Register(name + "_chiseled_bookshelf", pair);
			if (flammable) FUEL.add(pair.getItem(), 300);
		}
		if (material instanceof RowProvider row) {
			BlockContainer pair = row.getRow();
			Register(name + "_row", pair);
			if (flammable) {
				FLAMMABLE.add(pair.getBlock(), 5, 20);
				FUEL.add(pair.getItem(), 300);
			}
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
		if (material instanceof PackedProvider packed) Register("packed_" + name, packed.getPacked());
		if (material instanceof BricksProvider bricks) Register(name + "_bricks", bricks.getBricks());
		if (material instanceof OxidizableBricksProvider oxidizableBricks) Register(name + "_bricks", oxidizableBricks.getOxidizableBricks());
		if (material instanceof BrickSlabProvider brickSlab) Register(name + "_brick_slab", brickSlab.getBrickSlab());
		if (material instanceof OxidizableBrickSlabProvider oxidizableBrickSlab) Register(name + "_brick_slab", oxidizableBrickSlab.getOxidizableBrickSlab());
		if (material instanceof BrickStairsProvider brickStairs) Register(name + "_brick_stairs", brickStairs.getBrickStairs());
		if (material instanceof OxidizableBrickStairsProvider oxidizableBrickStairs) Register(name + "_brick_stairs", oxidizableBrickStairs.getOxidizableBrickStairs());
		if (material instanceof BrickWallProvider brickWall) Register(name + "_brick_wall", brickWall.getBrickWall());
		if (material instanceof OxidizableBrickWallProvider oxidizableBrickWall) Register(name + "_brick_wall", oxidizableBrickWall.getOxidizableBrickWall());
		if (material instanceof CutProvider cut) Register("cut_" + name, cut.getCut());
		if (material instanceof CutPillarProvider cutPillar) Register("cut_" + name + "_pillar", cutPillar.getCutPillar());
		if (material instanceof OxidizableCutPillarProvider oxidizableCutPillar) Register("cut_" + name + "_pillar", oxidizableCutPillar.getOxidizableCutPillar());
		if (material instanceof CutSlabProvider cutSlab) Register("cut_" + name + "_slab", cutSlab.getCutSlab());
		if (material instanceof CutStairsProvider cutStairs) Register("cut_" + name + "_stairs", cutStairs.getCutStairs());
		if (material instanceof CutWallProvider cutWall) Register("cut_" + name + "_wall", cutWall.getCutWall());
		if (material instanceof OxidizableCutWallProvider oxidizableCutWall) Register("cut_" + name + "_wall", oxidizableCutWall.getOxidizableCutWall());
		if (material instanceof PolishedProvider polished) Register("polished_" + name, polished.getPolished());
		if (material instanceof PolishedSlabProvider polishedSlab) Register("polished_" + name + "_slab", polishedSlab.getPolishedSlab());
		if (material instanceof PolishedStairsProvider polishedStairs) Register("polished_" + name + "_stairs", polishedStairs.getPolishedStairs());
		if (material instanceof PolishedWallProvider polishedWall) Register("polished_" + name + "_wall", polishedWall.getPolishedWall());
		if (material instanceof PolishedBricksProvider polishedBricks) Register("polished_" + name + "_bricks", polishedBricks.getPolishedBricks());
		if (material instanceof PolishedBrickSlabProvider polishedBrickSlab) Register("polished_" + name + "_brick_slab", polishedBrickSlab.getPolishedBrickSlab());
		if (material instanceof PolishedBrickStairsProvider polishedBrickStairs) Register("polished_" + name + "_brick_stairs", polishedBrickStairs.getPolishedBrickStairs());
		if (material instanceof PolishedBrickWallProvider polishedBrickWall) Register("polished_" + name + "_brick_wall", polishedBrickWall.getPolishedBrickWall());
		if (material instanceof LadderProvider ladder) {
			BlockContainer pair = ladder.getLadder();
			Register(name + "_ladder", pair);
			if (flammable) FUEL.add(pair.getItem(), 300);
		}
		if (material instanceof WoodcutterProvider woodcutter) Register(name + "_woodcutter", woodcutter.getWoodcutter());
		if (material instanceof SignProvider sign) Register(name + "_sign", name + "_wall_sign", sign.getSign());
		if (material instanceof BoatProvider boatProvider) {
			BoatContainer boat = boatProvider.getBoat();
			Register(boat.getType().getName() + "_boat", boat.getItem());
			DispenserBlock.registerBehavior(boat.getItem(), new ModBoatDispenserBehavior(boat.getType()));
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
		for(Integer owner : ANCHOR_MAP.keySet()) Register(ANCHOR_MAP.get(owner) + "_core", ANCHOR_CORES.get(owner));
	}
	public static void RegisterFlowers() {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		//Carnations
		for (DyeColor color : COLORS) Register(color.getName() + "_carnation", CARNATIONS.get(color));
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		Register("pink_daisy", PINK_DAISY);
		//Other Flowers
		Register("rose", ROSE);
		Register("blue_rose", BLUE_ROSE);
		Register("magenta_tulip", MAGENTA_TULIP);
		Register("marigold", MARIGOLD);
		Register("pink_allium", PINK_ALLIUM);
		Register("lavender", LAVENDER);
		Register("hydrangea", HYDRANGEA);
		Register("indigo_orchid", INDIGO_ORCHID);
		Register("magenta_orchid", MAGENTA_ORCHID);
		Register("purple_orchid", PURPLE_ORCHID);
		Register("white_orchid", WHITE_ORCHID);
		Register("yellow_orchid", YELLOW_ORCHID);
		Register("paeonia", PAEONIA);
		Register("aster", ASTER);
		Register("amaranth", AMARANTH);
		COMPOST.add(AMARANTH.getItem(), 0.65F);
		FLAMMABLE.add(AMARANTH.getBlock(), 60, 100);
		Register("amaranth_petals", AMARANTH_PETALS);
		COMPOST.add(AMARANTH_PETALS, 0.325F);
		Register("amaranth_seeds", AMARANTH_SEEDS);
		COMPOST.add(AMARANTH_SEEDS.getItem(), 0.3F);
		Register("blue_rose_bush", BLUE_ROSE_BUSH);
		COMPOST.add(BLUE_ROSE_BUSH.getItem(), 0.65F);
		FLAMMABLE.add(BLUE_ROSE_BUSH.getBlock(), 60, 100);
		Register("tall_allium", TALL_ALLIUM);
		COMPOST.add(TALL_ALLIUM.getItem(), 0.65F);
		FLAMMABLE.add(TALL_ALLIUM.getBlock(), 60, 100);
		Register("tall_pink_allium", TALL_PINK_ALLIUM);
		COMPOST.add(TALL_PINK_ALLIUM.getItem(), 0.65F);
		FLAMMABLE.add(TALL_PINK_ALLIUM.getBlock(), 60, 100);
		//Vanilla Flower Extensions
		Register("allium_petals", ALLIUM_PETALS);
		COMPOST.add(ALLIUM_PETALS, 0.325F);
		Register("allium_seeds", ALLIUM_SEEDS);
		COMPOST.add(ALLIUM_SEEDS.getItem(), 0.3F);
		Register("azure_bluet_petals", AZURE_BLUET_PETALS);
		COMPOST.add(AZURE_BLUET_PETALS, 0.325F);
		Register("azure_bluet_seeds", AZURE_BLUET_SEEDS);
		COMPOST.add(AZURE_BLUET_SEEDS.getItem(), 0.3F);
		Register("blue_orchid_petals", BLUE_ORCHID_PETALS);
		COMPOST.add(BLUE_ORCHID_PETALS, 0.325F);
		Register("blue_orchid_seeds", BLUE_ORCHID_SEEDS);
		COMPOST.add(BLUE_ORCHID_SEEDS.getItem(), 0.3F);
		Register("cornflower_petals", CORNFLOWER_PETALS);
		COMPOST.add(CORNFLOWER_PETALS, 0.325F);
		Register("cornflower_seeds", CORNFLOWER_SEEDS);
		COMPOST.add(CORNFLOWER_SEEDS.getItem(), 0.3F);
		Register("dandelion_petals", DANDELION_PETALS);
		COMPOST.add(DANDELION_PETALS, 0.325F);
		Register("dandelion_seeds", DANDELION_SEEDS);
		COMPOST.add(DANDELION_SEEDS.getItem(), 0.3F);
		Register("lilac_petals", LILAC_PETALS);
		COMPOST.add(LILAC_PETALS, 0.325F);
		Register("lilac_seeds", LILAC_SEEDS);
		COMPOST.add(LILAC_SEEDS.getItem(), 0.3F);
		Register("lily_of_the_valley_petals", LILY_OF_THE_VALLEY_PETALS);
		COMPOST.add(LILY_OF_THE_VALLEY_PETALS, 0.325F);
		Register("lily_of_the_valley_seeds", LILY_OF_THE_VALLEY_SEEDS);
		COMPOST.add(LILY_OF_THE_VALLEY_SEEDS.getItem(), 0.3F);
		Register("orange_tulip_petals", ORANGE_TULIP_PETALS);
		COMPOST.add(ORANGE_TULIP_PETALS, 0.325F);
		Register("orange_tulip_seeds", ORANGE_TULIP_SEEDS);
		COMPOST.add(ORANGE_TULIP_SEEDS.getItem(), 0.3F);
		Register("oxeye_daisy_petals", OXEYE_DAISY_PETALS);
		COMPOST.add(OXEYE_DAISY_PETALS, 0.325F);
		Register("oxeye_daisy_seeds", OXEYE_DAISY_SEEDS);
		COMPOST.add(OXEYE_DAISY_SEEDS.getItem(), 0.3F);
		Register("peony_petals", PEONY_PETALS);
		COMPOST.add(PEONY_PETALS, 0.325F);
		Register("peony_seeds", PEONY_SEEDS);
		COMPOST.add(PEONY_SEEDS.getItem(), 0.3F);
		Register("pink_tulip_petals", PINK_TULIP_PETALS);
		COMPOST.add(PINK_TULIP_PETALS, 0.325F);
		Register("pink_tulip_seeds", PINK_TULIP_SEEDS);
		COMPOST.add(PINK_TULIP_SEEDS.getItem(), 0.3F);
		Register("poppy_petals", POPPY_PETALS);
		COMPOST.add(POPPY_PETALS, 0.325F);
		Register("poppy_seeds", POPPY_SEEDS);
		COMPOST.add(POPPY_SEEDS.getItem(), 0.3F);
		Register("red_tulip_petals", RED_TULIP_PETALS);
		COMPOST.add(RED_TULIP_PETALS, 0.325F);
		Register("red_tulip_seeds", RED_TULIP_SEEDS);
		COMPOST.add(RED_TULIP_SEEDS.getItem(), 0.3F);
		Register("sunflower_petals", SUNFLOWER_PETALS);
		COMPOST.add(SUNFLOWER_PETALS, 0.325F);
		Register("sunflower_seeds", SUNFLOWER_SEEDS);
		COMPOST.add(SUNFLOWER_SEEDS.getItem(), 0.3F);
		Register("white_tulip_petals", WHITE_TULIP_PETALS);
		COMPOST.add(WHITE_TULIP_PETALS, 0.325F);
		Register("white_tulip_seeds", WHITE_TULIP_SEEDS);
		COMPOST.add(WHITE_TULIP_SEEDS.getItem(), 0.3F);
		Register("wither_rose_petals", WITHER_ROSE_PETALS);
		COMPOST.add(WITHER_ROSE_PETALS, 0.325F);
		Register("wither_rose_seeds", WITHER_ROSE_SEEDS);
		COMPOST.add(WITHER_ROSE_SEEDS.getItem(), 0.3F);
		//Special Petals (no seeds)
		Register("azalea_petals", AZALEA_PETALS);
		COMPOST.add(AZALEA_PETALS, 0.325F);
		Register("spore_blossom_petal", SPORE_BLOSSOM_PETAL);
		COMPOST.add(SPORE_BLOSSOM_PETAL, 0.1625F);
	}
	public static void RegisterFleece() {
		Register(FLEECE_MATERIAL);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(FLEECE_MATERIAL.getHelmet(), CauldronBehavior.CLEAN_DYEABLE_ITEM);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(FLEECE_MATERIAL.getChestplate(), CauldronBehavior.CLEAN_DYEABLE_ITEM);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(FLEECE_MATERIAL.getLeggings(), CauldronBehavior.CLEAN_DYEABLE_ITEM);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(FLEECE_MATERIAL.getBoots(), CauldronBehavior.CLEAN_DYEABLE_ITEM);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(FLEECE_MATERIAL.getHorseArmor(), CauldronBehavior.CLEAN_DYEABLE_ITEM);
	}
	public static void RegisterPlushies() {
		Register("chicken_plushie", CHICKEN_PLUSHIE);
		Register("fancy_chicken_plushie", FANCY_CHICKEN_PLUSHIE);
		Register("amber_chicken_plushie", AMBER_CHICKEN_PLUSHIE);
		Register("bronzed_chicken_plushie", BRONZED_CHICKEN_PLUSHIE);
		Register("gold_crested_chicken_plushie", GOLD_CRESTED_CHICKEN_PLUSHIE);
		Register("midnight_chicken_plushie", MIDNIGHT_CHICKEN_PLUSHIE);
		Register("skewbald_chicken_plushie", SKEWBALD_CHICKEN_PLUSHIE);
		Register("stormy_chicken_plushie", STORMY_CHICKEN_PLUSHIE);
		Register("pig_plushie", PIG_PLUSHIE);
		Register("saddled_pig_plushie", SADDLED_PIG_PLUSHIE);
		Register("mottled_pig_plushie", MOTTLED_PIG_PLUSHIE);
		Register("muddy_pig_plushie", MUDDY_PIG_PLUSHIE);
		Register("dried_muddy_pig_plushie", DRIED_MUDDY_PIG_PLUSHIE);
		Register("pale_pig_plushie", PALE_PIG_PLUSHIE);
		Register("piebald_pig_plushie", PIEBALD_PIG_PLUSHIE);
		Register("pink_footed_pig_plushie", PINK_FOOTED_PIG_PLUSHIE);
		Register("sooty_pig_plushie", SOOTY_PIG_PLUSHIE);
		Register("spotted_pig_plushie", SPOTTED_PIG_PLUSHIE);
		Register("sheared_sheep_plushie", SHEARED_SHEEP_PLUSHIE);
		Register("flecked_sheep_plushie", FLECKED_SHEEP_PLUSHIE);
		Register("sheared_flecked_sheep_plushie", SHEARED_FLECKED_SHEEP_PLUSHIE);
		Register("fuzzy_sheep_plushie", FUZZY_SHEEP_PLUSHIE);
		Register("sheared_fuzzy_sheep_plushie", SHEARED_FUZZY_SHEEP_PLUSHIE);
		Register("inky_sheep_plushie", INKY_SHEEP_PLUSHIE);
		Register("sheared_inky_sheep_plushie", SHEARED_INKY_SHEEP_PLUSHIE);
		Register("long_nose_sheep_plushie", LONG_NOSE_SHEEP_PLUSHIE);
		Register("sheared_long_nose_sheep_plushie", SHEARED_LONG_NOSE_SHEEP_PLUSHIE);
		Register("patched_sheep_plushie", PATCHED_SHEEP_PLUSHIE);
		Register("sheared_patched_sheep_plushie", SHEARED_PATCHED_SHEEP_PLUSHIE);
		Register("rocky_sheep_plushie", ROCKY_SHEEP_PLUSHIE);
		Register("sheared_rocky_sheep_plushie", SHEARED_ROCKY_SHEEP_PLUSHIE);
		Register("rainbow_sheep_plushie", RAINBOW_SHEEP_PLUSHIE);
		Register("sheared_rainbow_sheep_plushie", SHEARED_RAINBOW_SHEEP_PLUSHIE);
		Register("mossy_sheep_plushie", MOSSY_SHEEP_PLUSHIE);
		Register("sheared_mossy_sheep_plushie", SHEARED_MOSSY_SHEEP_PLUSHIE);
		Register("albino_cow_plushie", ALBINO_COW_PLUSHIE);
		Register("ashen_cow_plushie", ASHEN_COW_PLUSHIE);
		Register("cookie_cow_plushie", COOKIE_COW_PLUSHIE);
		Register("cream_cow_plushie", CREAM_COW_PLUSHIE);
		Register("dairy_cow_plushie", DAIRY_COW_PLUSHIE);
		Register("pinto_cow_plushie", PINTO_COW_PLUSHIE);
		Register("sunset_cow_plushie", SUNSET_COW_PLUSHIE);
		Register("umbra_cow_plushie", UMBRA_COW_PLUSHIE);
		Register("sheared_umbra_cow_plushie", SHEARED_UMBRA_COW_PLUSHIE);
		Register("wooly_cow_plushie", WOOLY_COW_PLUSHIE);
		Register("sheared_wooly_cow_plushie", SHEARED_WOOLY_COW_PLUSHIE);
		Register("mooshroom_plushie_blue", MOOSHROOM_PLUSHIE_BLUE);
		Register("mooshroom_plushie_crimson", MOOSHROOM_PLUSHIE_CRIMSON);
		Register("mooshroom_plushie_gilded", MOOSHROOM_PLUSHIE_GILDED);
		Register("mooshroom_plushie_warped", MOOSHROOM_PLUSHIE_WARPED);
		Register("moobloom_plushie", MOOBLOOM_PLUSHIE);
		Register("moolip_plushie", MOOLIP_PLUSHIE);
		Register("mooblossom_plushie", MOOBLOSSOM_PLUSHIE);
		Register("mooblossom_plushie_orange", MOOBLOSSOM_PLUSHIE_ORANGE);
		Register("mooblossom_plushie_pink", MOOBLOSSOM_PLUSHIE_PINK);
		Register("mooblossom_plushie_red", MOOBLOSSOM_PLUSHIE_RED);
		Register("mooblossom_plushie_white", MOOBLOSSOM_PLUSHIE_WHITE);
		Register("cowcoa_plushie", COWCOA_PLUSHIE);
		Register("cowfee_plushie", COWFEE_PLUSHIE);
		Register("strawbovine_plushie", STRAWBOVINE_PLUSHIE);
		Register("moonilla_plushie", MOONILLA_PLUSHIE);
		Register("white_snow_golem_plushie", WHITE_SNOW_GOLEM_PLUSHIE);
		Register("rotten_snow_golem_plushie", ROTTEN_SNOW_GOLEM_PLUSHIE);
		Register("sheared_snow_golem_plushie", SHEARED_SNOW_GOLEM_PLUSHIE);
		Register("melon_golem_plushie", MELON_GOLEM_PLUSHIE);
		Register("sheared_melon_golem_plushie", SHEARED_MELON_GOLEM_PLUSHIE);
		Register("black_axolotl_plushie", BLACK_AXOLOTL_PLUSHIE);
		Register("cyanide_axolotl_plushie", CYANIDE_AXOLOTL_PLUSHIE);
		Register("glow_axolotl_plushie", GLOW_AXOLOTL_PLUSHIE);
		Register("golden_axolotl_plushie", GOLDEN_AXOLOTL_PLUSHIE);
		Register("green_axolotl_plushie", GREEN_AXOLOTL_PLUSHIE);
		Register("lucia_axolotl_plushie", LUCIA_AXOLOTL_PLUSHIE);
		Register("red_axolotl_plushie", RED_AXOLOTL_PLUSHIE);
		Register("sculk_axolotl_plushie", SCULK_AXOLOTL_PLUSHIE);
		Register("white_axolotl_plushie", WHITE_AXOLOTL_PLUSHIE);
		Register("wilder_axolotl_plushie", WILDER_AXOLOTL_PLUSHIE);
	}
	public static void RegisterDecorativeBlocks() {
		Register("decoration_only_vine", DECORATIVE_VINE);
		Register("decoration_only_sugar_cane", DECORATIVE_SUGAR_CANE);
		Register("decoration_only_cactus", DECORATIVE_CACTUS);
	}
	public static void RegisterSoleil() {
		Register("soft_tnt", SOFT_TNT);
		Register("soft_tnt", SOFT_TNT_ENTITY);
		DispenserBlock.registerBehavior(SOFT_TNT.getBlock(), new ItemDispenserBehavior() {
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
		//Carved Gourds
		Register("soleil_carved_pumpkin", SOLEIL_CARVED_PUMPKIN);
		Register("soleil_jack_o_lantern", SOLEIL_JACK_O_LANTERN);
		Register("soleil_soul_jack_o_lantern", SOLEIL_SOUL_JACK_O_LANTERN);
		Register("soleil_ender_jack_o_lantern", SOLEIL_ENDER_JACK_O_LANTERN);
		Register("soleil_carved_melon", SOLEIL_CARVED_MELON);
		Register("soleil_melon_lantern", SOLEIL_MELON_LANTERN);
		Register("soleil_soul_melon_lantern", SOLEIL_SOUL_MELON_LANTERN);
		Register("soleil_ender_melon_lantern", SOLEIL_ENDER_MELON_LANTERN);
		Register("soleil_carved_white_pumpkin", SOLEIL_CARVED_WHITE_PUMPKIN);
		Register("soleil_white_jack_o_lantern", SOLEIL_WHITE_JACK_O_LANTERN);
		Register("soleil_white_soul_jack_o_lantern", SOLEIL_WHITE_SOUL_JACK_O_LANTERN);
		Register("soleil_white_ender_jack_o_lantern", SOLEIL_WHITE_ENDER_JACK_O_LANTERN);
		Register("soleil_carved_rotten_pumpkin", SOLEIL_CARVED_ROTTEN_PUMPKIN);
		Register("soleil_rotten_jack_o_lantern", SOLEIL_ROTTEN_JACK_O_LANTERN);
		Register("soleil_rotten_soul_jack_o_lantern", SOLEIL_ROTTEN_SOUL_JACK_O_LANTERN);
		Register("soleil_rotten_ender_jack_o_lantern", SOLEIL_ROTTEN_ENDER_JACK_O_LANTERN);
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
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		Register(CHERRY_MATERIAL);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cherry_tree"), CHERRY_TREE);
		Register("white_cherry_leaves", WHITE_CHERRY_LEAVES);
		FLAMMABLE.add(WHITE_CHERRY_LEAVES.getBlock(), 30, 60);
		Register("white_cherry_petals", WHITE_CHERRY_PETALS);
		COMPOST.add(WHITE_CHERRY_PETALS, 0.625F);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("white_cherry_tree"), WHITE_CHERRY_TREE);
		Register("pale_cherry_leaves", PALE_CHERRY_LEAVES);
		FLAMMABLE.add(PALE_CHERRY_LEAVES.getBlock(), 30, 60);
		Register("pale_cherry_petals", PALE_CHERRY_PETALS);
		COMPOST.add(PALE_CHERRY_PETALS, 0.625F);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pale_cherry_tree"), PALE_CHERRY_TREE);
		Register("pink_cherry_leaves", PINK_CHERRY_LEAVES);
		FLAMMABLE.add(PINK_CHERRY_LEAVES.getBlock(), 30, 60);
		Register("pink_cherry_petals", PINK_CHERRY_PETALS);
		COMPOST.add(PINK_CHERRY_PETALS, 0.625F);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pink_cherry_tree"), PINK_CHERRY_TREE);
		Register("cherry", CHERRY_ITEM);
		COMPOST.add(CHERRY_ITEM, 0.65F);
	}
	public static void RegisterCinnamon() {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		Register("cinnamon", CINNAMON);
		COMPOST.add(CINNAMON, 0.2f);
		Register(CASSIA_MATERIAL);
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES);
		FlammableBlockRegistry.getDefaultInstance().add(FLOWERING_CASSIA_LEAVES.getBlock(), 30, 60);
		Register("cassia_petals", CASSIA_PETALS);
		COMPOST.add(CASSIA_PETALS, 0.325f);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cassia_tree"), CASSIA_TREE);
	}
	public static void RegisterVanilla() {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		Register("vanilla_flower", VANILLA_FLOWER);
		Register("tall_vanilla", TALL_VANILLA);
		COMPOST.add(TALL_PINK_ALLIUM.getItem(), 0.65F);
		FLAMMABLE.add(TALL_VANILLA.getBlock(), 60, 100);
		Register("vanilla", VANILLA);
		COMPOST.add(VANILLA, 0.2f);
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
	public static void RegisterGrapes() {
		Register("grapes", GRAPES);
		CompostingChanceRegistry.INSTANCE.add(GRAPES, 0.3F);
	}
	public static Map<Supplier<Item>, Item> JUICE_MAP = new HashMap<Supplier<Item>, Item>();
	public static void RegisterJuice(String path, Item juice, Item source) {
		RegisterJuice(path, juice, () -> source);
	}
	public static void RegisterJuice(String path, Item juice, Supplier<Item> source) {
		Register(path + "_juice", juice);
		JUICE_MAP.put(source, juice);
	}
	public static void RegisterJuice() {
		Register("apple_cider", APPLE_CIDER);
		Register("juicer", JUICER);
		RegisterJuice("apple", APPLE_JUICE, Items.APPLE);
		JUICE_MAP.put(() -> GREEN_APPLE, APPLE_JUICE);
		RegisterJuice("beetroot", BEETROOT_JUICE, Items.BEETROOT);
		//TODO: Hook into better nether explicitly
		RegisterJuice("black_apple", BLACK_APPLE_JUICE, () -> Registry.ITEM.get(new Identifier("betternether:black_apple")));
		//JUICE_MAP.put(Items.APPLE, BLACK_APPLE_JUICE);
		RegisterJuice("cabbage", CABBAGE_JUICE, ItemsRegistry.CABBAGE.get());
		RegisterJuice("cactus", CACTUS_JUICE, Items.CACTUS);
		RegisterJuice("carrot", CARROT_JUICE, Items.CARROT);
		RegisterJuice("cherry", CHERRY_JUICE, CHERRY_ITEM);
		RegisterJuice("chorus", CHORUS_JUICE, Items.CHORUS_FRUIT);
		RegisterJuice("glow_berry", GLOW_BERRY_JUICE, Items.GLOW_BERRIES);
		RegisterJuice("grape", GRAPE_JUICE, GRAPES);
		RegisterJuice("kelp", KELP_JUICE, Items.KELP);
		RegisterJuice("melon", MELON_JUICE, Items.MELON_SLICE);
		RegisterJuice("onion", ONION_JUICE, ItemsRegistry.ONION.get());
		RegisterJuice("potato", POTATO_JUICE, Items.POTATO);
		RegisterJuice("pumpkin", PUMPKIN_JUICE, Items.PUMPKIN);
		JUICE_MAP.put(() -> ModBase.WHITE_PUMPKIN.getGourd().getItem(), PUMPKIN_JUICE);
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
		Register("grape_smoothie", GRAPE_SMOOTHIE);
		Register("kelp_smoothie", KELP_SMOOTHIE);
		Register("melon_smoothie", MELON_SMOOTHIE);
		Register("onion_smoothie", ONION_SMOOTHIE);
		Register("potato_smoothie", POTATO_SMOOTHIE);
		Register("pumpkin_smoothie", PUMPKIN_SMOOTHIE);
		Register("sea_pickle_smoothie", SEA_PICKLE_SMOOTHIE);
		Register("strawberry_smoothie", STRAWBERRY_SMOOTHIE);
		Register("sweet_berry_smoothie", SWEET_BERRY_SMOOTHIE);
		Register("tomato_smoothie", TOMATO_SMOOTHIE);
		//Poisons
		JUICE_MAP.put(() -> Items.POISONOUS_POTATO, POISON_VIAL);
		JUICE_MAP.put(() -> POISONOUS_CARROT, POISON_VIAL);
		JUICE_MAP.put(() -> POISONOUS_BEETROOT, POISON_VIAL);
		JUICE_MAP.put(() -> POISONOUS_GLOW_BERRIES, POISON_VIAL);
		JUICE_MAP.put(() -> POISONOUS_SWEET_BERRIES, POISON_VIAL);
		JUICE_MAP.put(() -> POISONOUS_TOMATO, POISON_VIAL);
		JUICE_MAP.put(() -> SPOILED_EGG, POISON_VIAL);
		JUICE_MAP.put(() -> Items.SPIDER_EYE, SPIDER_POISON_VIAL);
		JUICE_MAP.put(() -> Items.PUFFERFISH, PUFFERFISH_POISON_VIAL);
	}
	public static void RegisterMilkshakesIceCream() {
		Register("milkshake", MILKSHAKE);
		Register("chocolate_milkshake", CHOCOLATE_MILKSHAKE);
		Register("coffee_milkshake", COFFEE_MILKSHAKE);
		Register("strawberry_milkshake", STRAWBERRY_MILKSHAKE);
		Register("vanilla_milkshake", VANILLA_MILKSHAKE);
		Register("chocolate_chip_milkshake", CHOCOLATE_CHIP_MILKSHAKE);
		Register("ice_cream", ICE_CREAM);
		Register("chocolate_ice_cream", CHOCOLATE_ICE_CREAM);
		Register("coffee_ice_cream", COFFEE_ICE_CREAM);
		Register("strawberry_ice_cream", STRAWBERRY_ICE_CREAM);
		Register("vanilla_ice_cream", VANILLA_ICE_CREAM);
		Register("chocolate_chip_ice_cream", CHOCOLATE_CHIP_ICE_CREAM);
	}
	public static void RegisterBamboo() {
		Register(BAMBOO_MATERIAL);
		Register("dried_bamboo", DRIED_BAMBOO_BLOCK);
		Register("potted_dried_bamboo", POTTED_DRIED_BAMBOO);
		Register(DRIED_BAMBOO_MATERIAL);
		//New Bamboo
		Register(VANILLA_BAMBOO_MATERIAL);
		Register("minecraft:bamboo_mosaic", BAMBOO_MOSAIC);
		Register("minecraft:bamboo_mosaic_slab", BAMBOO_MOSAIC_SLAB);
		Register("minecraft:bamboo_mosaic_stairs", BAMBOO_MOSAIC_STAIRS);
		Register("vanilla_bamboo_bookshelf", BAMBOO_BOOKSHELF);
		Register("vanilla_bamboo_chiseled_bookshelf", BAMBOO_CHISELED_BOOKSHELF);
		Register("vanilla_bamboo_ladder", BAMBOO_LADDER);
		Register("vanilla_bamboo_woodcutter", BAMBOO_WOODCUTTER);
	}
	public static void RegisterChiseledBookshelves() {
		Register("minecraft:chiseled_bookshelf", CHISELED_BOOKSHELF);
		FuelRegistry.INSTANCE.add(CHISELED_BOOKSHELF.getItem(), 300);
		Register("minecraft:chiseled_bookshelf_entity", CHISELED_BOOKSHELF_ENTITY);
	}
	public static void RegisterVanillaWood() {
		Register(ACACIA_MATERIAL);
		Register(BIRCH_MATERIAL);
		Register(DARK_OAK_MATERIAL);
		Register(JUNGLE_MATERIAL);
		//Register(OAK_MATERIAL);
		Register(SPRUCE_MATERIAL);
	}
	public static void RegisterMushroomWood() {
		Register(BROWN_MUSHROOM_MATERIAL);
		Register(RED_MUSHROOM_MATERIAL);
		Register(MUSHROOM_STEM_MATERIAL);
		Registry.register(Registry.FEATURE, ID("huge_blue_mushroom"), HUGE_BLUE_MUSHROOM_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("huge_blue_mushroom"), HUGE_BLUE_MUSHROOM);
		Register(BLUE_MUSHROOM_MATERIAL);
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		COMPOST.add(BLUE_MUSHROOM_MATERIAL.get().getItem(), 0.65F);
		COMPOST.add(BLUE_MUSHROOM_MATERIAL.getBlock().getItem(), 0.85F);
	}
	public static void RegisterVanillaNetherWood() {
		Register(CRIMSON_MATERIAL);
		Register(WARPED_MATERIAL);
	}
	public static void RegisterGildedFungus() {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_forest_vegetation"), GILDED_FOREST_VEGETATION);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("patch_gilded_roots"), PATCH_GILDED_ROOTS);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_fungi"), GILDED_FUNGI);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("gilded_fungi_planted"), GILDED_FUNGI_PLANTED);
		Register("gilded_nylium", GILDED_NYLIUM);
		Register("gilded_roots", GILDED_ROOTS);
		Register(GILDED_MATERIAL);
	}
	public static void Register119() {
		//Goat Stuff (not part of backport)
		FlammableBlockRegistry FLAMMABLE = FlammableBlockRegistry.getDefaultInstance();
		FuelRegistry FUEL = FuelRegistry.INSTANCE;
		for(DyeColor color : DyeColor.values()) {
			BlockContainer container = FLEECE.get(color);
			Register(color.getName() + "_fleece", container);
			FLAMMABLE.add(container.getBlock(), 30, 60);
		}
		ItemDispenserBehavior itemDispenserBehavior2 = new FallibleItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				List<HorseBaseEntity> list = pointer.getWorld().getEntitiesByClass(HorseBaseEntity.class, new Box(blockPos),
						(horseBaseEntityx) -> horseBaseEntityx.isAlive() && horseBaseEntityx.hasArmorSlot());
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
		for(DyeColor color : DyeColor.values()) {
			BlockContainer container = FLEECE_CARPETS.get(color);
			Register(color.getName() + "_fleece_carpet", container);
			FLAMMABLE.add(container.getBlock(), 60, 20);
			DispenserBlock.registerBehavior(container.getItem(), itemDispenserBehavior2);
		}
		Register("rainbow_fleece", RAINBOW_FLEECE);
		FLAMMABLE.add(RAINBOW_FLEECE.getBlock(), 30, 60);
		Register("rainbow_fleece_carpet", RAINBOW_FLEECE_CARPET);
		FLAMMABLE.add(RAINBOW_FLEECE_CARPET.getBlock(), 60, 20);
		DispenserBlock.registerBehavior(RAINBOW_FLEECE_CARPET.getItem(), itemDispenserBehavior2);
		Register("chevon", CHEVON);
		Register("cooked_chevon", COOKED_CHEVON);
		Register("goat_horn_salve", GOAT_HORN_SALVE);
		//Goat Horn
		Register("minecraft:goat_horn", GOAT_HORN);
		//Goat Horn (compatibility)
		Deprecated.RegisterOldGoatForCompatibility();
		//Music Discs
		Register("minecraft:music_disc_otherside", MUSIC_DISC_OTHERSIDE);
		Register("minecraft:music_disc_5", MUSIC_DISC_5);
		Register("minecraft:disc_fragment_5", DISC_FRAGMENT_5);
		//Music Discs (compatilbility)
		Deprecated.RegisterOldMusicDiscsForCompatibility();
		//Mud
		Register("minecraft:mud", MUD);
		Register("minecraft:packed_mud", PACKED_MUD);
		Register("minecraft:mud_bricks", MUD_BRICKS);
		Register("minecraft:mud_brick_slab", MUD_BRICK_SLAB);
		Register("minecraft:mud_brick_stairs", MUD_BRICK_STAIRS);
		Register("minecraft:mud_brick_wall", MUD_BRICK_WALL);
		//Mud (compatibility)
		Deprecated.RegisterOldMudForCompatibility();
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
				if (serverWorld.getBlockState(blockPos2).isIn(ModTags.Blocks.CONVERTIBLE_TO_MUD)) {
					if (!serverWorld.isClient) {
						for (int i = 0; i < 5; ++i) {
							serverWorld.spawnParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + serverWorld.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + serverWorld.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
						}
					}
					serverWorld.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
					serverWorld.emitGameEvent(null, GameEvent.FLUID_PLACE, blockPos);
					serverWorld.setBlockState(blockPos2, MUD.getBlock().getDefaultState());
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				return this.fallback.dispense(pointer, stack);
			}
		});
		//Mangrove
		Register(MANGROVE_MATERIAL);
		Register("minecraft:mangrove_roots", MANGROVE_ROOTS);
		CompostingChanceRegistry.INSTANCE.add(MANGROVE_ROOTS.getItem(), 0.3F);
		FlammableBlockRegistry.getDefaultInstance().add(MANGROVE_ROOTS.getBlock(), 5, 20);
		Register("minecraft:muddy_mangrove_roots", MUDDY_MANGROVE_ROOTS);
		Register("mangrove", MANGROVE_TORCH);
		Register("mangrove_soul", MANGROVE_SOUL_TORCH);
		Register("mangrove_ender", MANGROVE_ENDER_TORCH);
		Register("mangrove_campfire", MANGROVE_CAMPFIRE);
		Register("mangrove_soul_campfire", MANGROVE_SOUL_CAMPFIRE);
		Register("mangrove_ender_campfire", MANGROVE_ENDER_CAMPFIRE);
		Register("mangrove_bookshelf", MANGROVE_BOOKSHELF);
		Register("mangrove_chiseled_bookshelf", MANGROVE_CHISELED_BOOKSHELF);
		Register("mangrove_ladder", MANGROVE_LADDER);
		Register("mangrove_woodcutter", MANGROVE_WOODCUTTER);
		Registry.register(Registry.FEATURE, ID("minecraft:mangrove_tree"), MANGROVE_TREE_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:mangrove"), MANGROVE_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:tall_mangrove"), TALL_MANGROVE_FEATURE);
		//Mangrove (compatibility)
		Deprecated.RegisterOldMangroveForCompatibility();
		//Frogs
		Register("minecraft:ochre_froglight", OCHRE_FROGLIGHT);
		Register("minecraft:verdant_froglight", VERDANT_FROGLIGHT);
		Register("minecraft:pearlescent_froglight", PEARLESCENT_FROGLIGHT);
		//Frogs (compatibility)
		Deprecated.RegisterOldFrogsForCompatibility();
		//Deep Dark
		Register("minecraft:reinforced_deepslate", REINFORCED_DEEPSLATE);
		Register("minecraft:echo_shard", ECHO_SHARD);
		Register(ECHO_MATERIAL);
		Register("budding_echo", BUDDING_ECHO);
		Register("echo_cluster", ECHO_CLUSTER);
		Register("large_echo_bud", LARGE_ECHO_BUD);
		Register("medium_echo_bud", MEDIUM_ECHO_BUD);
		Register("small_echo_bud", SMALL_ECHO_BUD);
		//Sculk
		Registry.register(Registry.BLOCK, Registry.BLOCK.getRawId(Blocks.SCULK_SENSOR), "minecraft:sculk_sensor", SCULK_SENSOR.getBlock());
		Registry.register(Registry.ITEM, Registry.ITEM.getRawId(Items.SCULK_SENSOR), "minecraft:sculk_sensor", SCULK_SENSOR.getItem());
		Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK_ENTITY_TYPE.getRawId(BlockEntityType.SCULK_SENSOR), "minecraft:sculk_sensor", SCULK_SENSOR_ENTITY);
		Register(SCULK_STONE_MATERIAL);
		Register("calcite_sculk_turf", CALCITE_SCULK_TURF);
		Register("deepslate_sculk_turf", DEEPSLATE_SCULK_TURF);
		Register("dripstone_sculk_turf", DRIPSTONE_SCULK_TURF);
		Register("smooth_basalt_sculk_turf", SMOOTH_BASALT_SCULK_TURF);
		Register("tuff_sculk_turf", TUFF_SCULK_TURF);
		Register("minecraft:sculk", SCULK);
		Register("minecraft:sculk_vein", SCULK_VEIN);
		Register("minecraft:sculk_soul", SCULK_SOUL_PARTICLE);
		Register("minecraft:sculk_catalyst", SCULK_CATALYST);
		Register("minecraft:sculk_catalyst_entity", SCULK_CATALYST_ENTITY);
		Register("minecraft:sculk_shrieker", SCULK_SHRIEKER);
		Register("minecraft:sculk_shrieker_entity", SCULK_SHRIEKER_ENTITY);
		Registry.register(Registry.PARTICLE_TYPE, ID("minecraft:sculk_charge"), SCULK_CHARGE_PARTICLE);
		Register("minecraft:sculk_charge_pop", SCULK_CHARGE_POP_PARTICLE);
		Registry.register(Registry.PARTICLE_TYPE, ID("minecraft:shriek"), SHRIEK_PARTICLE);
		Register("minecraft:sonic_boom", SONIC_BOOM_PARTICLE);
		Register("minecraft:darkness", DARKNESS_EFFECT);
		Registry.register(Registry.ENCHANTMENT, "swift_sneak", SWIFT_SNEAK_ENCHANTMENT);
		//TODO: Fix Warden
		//FabricDefaultAttributeRegistry.register(WARDEN_ENTITY, WardenEntity.addAttributes());
		//Register("minecraft:warden", WARDEN_ENTITY);
		//Register("minecraft:warden_spawn_egg", WARDEN_SPAWN_EGG);
		//Deep Dark
		Registry.register(Registry.FEATURE, ID("minecraft:sculk_patch"), DeepDarkBiome.SCULK_PATCH_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:sculk_patch_deep_dark"), DeepDarkBiome.SCULK_PATCH_DEEP_DARK_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:sculk_patch_ancient_city"), DeepDarkBiome.SCULK_PATCH_ANCIENT_CITY_FEATURE);
		Registry.register(Registry.FEATURE, ID("minecraft:sculk_vein"), SCULK_VEIN_FEATURE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("minecraft:sculk_vein"), DeepDarkBiome.SCULK_VEIN_FEATURE);
		//Biome
		//Registry.register(BuiltinRegistries.BIOME, DEEP_DARK_KEY.getValue(), DEEP_DARK);
		//TODO: Register Ancient City
		//BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, new Identifier("ancient_city_start_degradation"), HavenStructures.ANCIENT_CITY_START_DEGRADATION);
		//BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, new Identifier("ancient_city_generic_degradation"), HavenStructures.ANCIENT_CITY_GENERIC_DEGRADATION);
		//BuiltinRegistries.add(BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, new Identifier("ancient_city_walls_degradation"), HavenStructures.ANCIENT_CITY_WALLS_DEGRADATION);
		//Structures.register(HavenStructures.ANCIENT_CITY);

		//StructurePools.register(AncientCityGenerator.CITY_CENTER);
		//StructurePools.register(AncientCityOutskirtsGenerator.STRUCTURES);
		//StructurePools.register(AncientCityOutskirtsGenerator.SCULK);
		//StructurePools.register(AncientCityOutskirtsGenerator.WALLS);
		//StructurePools.register(AncientCityOutskirtsGenerator.WALLS_NO_CORNERS);
		//StructurePools.register(AncientCityOutskirtsGenerator.CITY_CENTER_WALLS);
		//StructurePools.register(AncientCityOutskirtsGenerator.ENTRANCE);
		Deprecated.RegisterOldDeepDarkForCompatibility();
	}
	public static Block RegisterDynamicLightingBlock(String path, Block lit) {
		Register(path, lit);
		if (lit instanceof DynamicLitBlock dynamic) DynamicLightManager.vanillaBlocksToLitBlocksMap.put(dynamic.getUnlitBlock(), lit);
		else throw new IllegalArgumentException("Block must implement DynamicLitBlock");
		return lit;
	}
	public static void RegisterDynamicLights() {
		//Air
		RegisterDynamicLightingBlock("lit_air", LIT_AIR);
		RegisterDynamicLightingBlock("lit_cave_air", LIT_CAVE_AIR);
		RegisterDynamicLightingBlock("lit_void_air", LIT_VOID_AIR);
		RegisterDynamicLightingBlock("lit_blood", LIT_BLOOD);
		//Fluids
		RegisterDynamicLightingBlock("lit_mud", LIT_MUD);
		RegisterDynamicLightingBlock("lit_water", LIT_WATER);
		//Climbable
		RegisterDynamicLightingBlock("lit_ladder", LIT_LADDER);
	}
	public static final RegistryKey<Biome> DEEP_DARK_KEY = RegistryKey.of(Registry.BIOME_KEY, ID("deep_dark"));
	public static void RegisterCandy() {
		Register("cinnamon_bean", CINNAMON_BEAN);
		Register("pink_cotton_candy", PINK_COTTON_CANDY);
		Register("blue_cotton_candy", BLUE_COTTON_CANDY);
		Register("candy_cane", CANDY_CANE);
		Register("candy_corn", CANDY_CORN);
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
		//Candy Blocks
		Register("sugar_block", SUGAR_BLOCK);
		Register("caramel_block", CARAMEL_BLOCK);
		Register("cocoa_block", COCOA_BLOCK);
		CompostingChanceRegistry.INSTANCE.add(COCOA_BLOCK.getItem(), 0.65F);
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
		Register(name + "_vanilla_milk_bucket", bucketProvider.getVanillaMilkBucket());
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
		FluidStorage.combinedItemApiProvider(LAVA_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(Fluids.LAVA), FluidConstants.BOTTLE));
		Register("distilled_water_bottle", DISTILLED_WATER_BOTTLE);
		Register("sugar_water_bottle", SUGAR_WATER_BOTTLE);
		Register("ichored", ICHORED_EFFECT);
		Register("ichor_bottle", ICHOR_BOTTLE);
		Register("slime_bottle", SLIME_BOTTLE);
		Register("sludge_bottle", SLUDGE_BOTTLE);
		Register("magma_cream_bottle", MAGMA_CREAM_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
		Register("still_blood", STILL_BLOOD_FLUID);
		Register("flowing_blood", FLOWING_BLOOD_FLUID);
		Register("blood_fluid_block", BLOOD_FLUID_BLOCK);
		//Non-blood / special blood syringes
		Register("chocolate_milk_syringe", CHOCOLATE_MILK_SYRINGE);
		Register("chorus_syringe", CHORUS_SYRINGE);
		Register("coffee_milk_syringe", COFFEE_MILK_SYRINGE);
		Register("confetti_syringe", CONFETTI_SYRINGE);
		Register("dragon_breath_syringe", DRAGON_BREATH_SYRINGE);
		Register("honey_syringe", HONEY_SYRINGE);
		Register("ichor_syringe", ICHOR_SYRINGE);
		Register("lava_syringe", LAVA_SYRINGE);
		Register("magma_cream_syringe", MAGMA_CREAM_SYRINGE);
		Register("milk_syringe", MILK_SYRINGE);
		Register("mud_syringe", MUD_SYRINGE);
		Register("slime_syringe", SLIME_SYRINGE);
		Register("sludge_syringe", SLUDGE_SYRINGE);
		Register("strawberry_milk_syringe", STRAWBERRY_MILK_SYRINGE);
		Register("sugar_water_syringe", SUGAR_WATER_SYRINGE);
		Register("vanilla_milk_syringe", VANILLA_MILK_SYRINGE);
		Register("water_syringe", WATER_SYRINGE);
		//Blood Syringes
		Register("blood_syringe", BLOOD_SYRINGE);
		Register("allay_blood_syringe", ALLAY_BLOOD_SYRINGE);
		Register("anemic_blood_syringe", ANEMIC_BLOOD_SYRINGE);
		Register("avian_blood_syringe", AVIAN_BLOOD_SYRINGE);
		Register("axolotl_blood_syringe", AXOLOTL_BLOOD_SYRINGE);
		Register("bat_blood_syringe", BAT_BLOOD_SYRINGE);
		Register("bear_blood_syringe", BEAR_BLOOD_SYRINGE);
		Register("bee_blood_syringe", BEE_BLOOD_SYRINGE);
		Register("bee_enderman_blood_syringe", BEE_ENDERMAN_BLOOD_SYRINGE);
		Register("canine_blood_syringe", CANINE_BLOOD_SYRINGE);
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
		Register("experience_syringe", EXPERIENCE_SYRINGE);
	}
	public static void RegisterCommands() {
		CommandRegistrationCallback.EVENT.register(ChorusCommand::register);
	}
	public static void RegisterOriginPowers() {
		Register(AttackedByAxolotlsPower::createFactory);
		Register(AttackedByFoxesPower::createFactory);
		Register(AttackedByHedgehogsPower::createFactory);
		Register(BloodTypePower::createFactory);
		Register(ChorusTeleportPower::createFactory);
		Register(ClownPacifistPower::createFactory);
		Register(DrinkBloodPower::createFactory);
		Register(IlluminatedPower::createFactory);
		Register(LactoseIntolerantPower::createFactory);
		Register(PulsingSkinGlowPower::createFactory);
		Register(ScareFoxesPower::createFactory);
		Register(ScareHedgehogsPower::createFactory);
		Register(ScareRaccoonsPower::createFactory);
		Register(ScareRedPandasPower::createFactory);
		Register(SkinGlowPower::createFactory);
		Register(SneakSpeedPower::createFactory);
		Register(SoftStepsPower::createFactory);
		Register(UnfreezingPower::createFactory);
	}
	public static void RegisterAngelBat() {
		Register("angel_bat", ANGEL_BAT_ENTITY);
		SpawnRestrictionAccessor.callRegister(ANGEL_BAT_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AngelBatEntity::CanSpawn);
		FabricDefaultAttributeRegistry.register(ANGEL_BAT_ENTITY, AngelBatEntity.createBatAttributes());
	}
	public static void RegisterGourds() {
		//Soul Jack o' Lantern
		Register("soul_jack_o_lantern", SOUL_JACK_O_LANTERN);
		Register("ender_jack_o_lantern", ENDER_JACK_O_LANTERN);
		//Melon
		Register("melon_seeds_projectile", MELON_SEED_PROJECTILE_ENTITY);
		Register("melon_golem", MELON_GOLEM_ENTITY);
		FabricDefaultAttributeRegistry.register(MELON_GOLEM_ENTITY, MelonGolemEntity.createMelonGolemAttributes());
		Register("carved_melon", CARVED_MELON);
		DispenserBlock.registerBehavior(CARVED_MELON.getBlock(), new FallibleItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				CarvedMelonBlock carvedMelonBlock = (CarvedMelonBlock) CARVED_MELON.getBlock();
				if (world.isAir(blockPos) && carvedMelonBlock.canDispense(world, blockPos)) {
					if (!world.isClient) {
						world.setBlockState(blockPos, carvedMelonBlock.getDefaultState(), Block.NOTIFY_ALL);
						world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
					}
					stack.decrement(1);
					this.setSuccess(true);
				}
				else this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
				return stack;
			}
		});
		CompostingChanceRegistry.INSTANCE.add(CARVED_MELON.getItem(), 0.65F);
		Register("melon_lantern", MELON_LANTERN);
		Register("soul_melon_lantern", SOUL_MELON_LANTERN);
		Register("ender_melon_lantern", ENDER_MELON_LANTERN);
		//White Pumpkin
		Register("white_snow_golem", WHITE_SNOW_GOLEM_ENTITY);
		FabricDefaultAttributeRegistry.register(WHITE_SNOW_GOLEM_ENTITY, WhiteSnowGolemEntity.createSnowGolemAttributes());
		Register("white_pumpkin", WHITE_PUMPKIN);
		Register("white_jack_o_lantern", WHITE_JACK_O_LANTERN);
		Register("white_soul_jack_o_lantern", WHITE_SOUL_JACK_O_LANTERN);
		Register("white_ender_jack_o_lantern", WHITE_ENDER_JACK_O_LANTERN);
		DispenserBlock.registerBehavior(WHITE_PUMPKIN.getCarved().getBlock(), new FallibleItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				BlockPos blockPos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
				CarvedWhitePumpkinBlock carvedPumpkinBlock = (CarvedWhitePumpkinBlock) WHITE_PUMPKIN.getCarved().getBlock();
				if (world.isAir(blockPos) && carvedPumpkinBlock.canDispense(world, blockPos)) {
					if (!world.isClient) {
						world.setBlockState(blockPos, carvedPumpkinBlock.getDefaultState(), Block.NOTIFY_ALL);
						world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
					}
					stack.decrement(1);
					this.setSuccess(true);
				}
				else this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
				return stack;
			}
		});
		//Rotten Pumpkin
		Register("rotten_pumpkin", ROTTEN_PUMPKIN);
		Register("carved_rotten_pumpkin", CARVED_ROTTEN_PUMPKIN);
		DispenserBlock.registerBehavior(CARVED_ROTTEN_PUMPKIN.getBlock(), new FallibleItemDispenserBehavior() {
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
				return stack;
			}
		});
		Register("rotten_jack_o_lantern", ROTTEN_JACK_O_LANTERN);
		Register("rotten_soul_jack_o_lantern", ROTTEN_SOUL_JACK_O_LANTERN);
		Register("rotten_ender_jack_o_lantern", ROTTEN_ENDER_JACK_O_LANTERN);
		Register("rotten_pumpkin_seeds", ROTTEN_PUMPKIN_SEEDS);
		CompostingChanceRegistry.INSTANCE.add(ROTTEN_PUMPKIN_SEEDS, 0.65F);
	}
	public static void RegisterRainbow() {
		Register("rainbow_sheep", RAINBOW_SHEEP_ENTITY);
		SpawnRestrictionAccessor.callRegister(RAINBOW_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(RAINBOW_SHEEP_ENTITY, RainbowSheepEntity.createSheepAttributes());
		Register("rainbow_sheep_spawn_egg", RAINBOW_SHEEP_SPAWN_EGG);
		Register("rainbow_wool", RAINBOW_WOOL);
		FlammableBlockRegistry.getDefaultInstance().add(RAINBOW_WOOL.getBlock(), 30, 60);
		Register("rainbow_carpet", RAINBOW_CARPET);
		FlammableBlockRegistry.getDefaultInstance().add(RAINBOW_WOOL.getBlock(), 60, 20);
		Register("rainbow_bed", RAINBOW_BED);
	}
	public static void RegisterSheepVariants() {
		Register("mossy_sheep", MOSSY_SHEEP_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOSSY_SHEEP_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(MOSSY_SHEEP_ENTITY, MossySheepEntity.createSheepAttributes());
		Register("mossy_sheep_spawn_egg", MOSSY_SHEEP_SPAWN_EGG);
		Register("moss_bed", MOSS_BED);
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
		Register("moonilla", MOONILLA_ENTITY);
		SpawnRestrictionAccessor.callRegister(MOONILLA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoonillaEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(MOONILLA_ENTITY, MoonillaEntity.createCowAttributes());
		Register("moonilla_spawn_egg", MOONILLA_SPAWN_EGG);
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
		//Blue Mooshroom
		Register("blue_mooshroom", BLUE_MOOSHROOM_ENTITY);
		SpawnRestrictionAccessor.callRegister(BLUE_MOOSHROOM_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlueMooshroomEntity::canSpawn);
		FabricDefaultAttributeRegistry.register(BLUE_MOOSHROOM_ENTITY, BlueMooshroomEntity.createCowAttributes());
		Register("blue_mooshroom_spawn_egg", BLUE_MOOSHROOM_SPAWN_EGG);
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
		Register("vanilla_milk_bowl", VANILLA_MILK_BOWL);
		Register("cottage_cheese_bowl", COTTAGE_CHEESE_BOWL);
		Register("chocolate_milk_bottle", CHOCOLATE_MILK_BOTTLE);
		Register("coffee_milk_bottle", COFFEE_MILK_BOTTLE);
		Register("strawberry_milk_bottle", STRAWBERRY_MILK_BOTTLE);
		Register("vanilla_milk_bottle", VANILLA_MILK_BOTTLE);
		Register("milk_cauldron", MILK_CAULDRON);
		Register("cottage_cheese_cauldron", COTTAGE_CHEESE_CAULDRON);
		Register("cheese_cauldron", CHEESE_CAULDRON);
		Register("cottage_cheese_block", COTTAGE_CHEESE_BLOCK);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, MilkCauldronBlock.FillFromBucket(Items.BUCKET));
		Register("chocolate_milk_bucket", CHOCOLATE_MILK_BUCKET);
		Register("coffee_milk_bucket", COFFEE_MILK_BUCKET);
		Register("strawberry_milk_bucket", STRAWBERRY_MILK_BUCKET);
		Register("vanilla_milk_bucket", VANILLA_MILK_BUCKET);
		Register("cottage_cheese_bucket", COTTAGE_CHEESE_BUCKET);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(COTTAGE_CHEESE_BUCKET, MilkCauldronBlock.FillCheeseFromBucket(Items.BUCKET));
		Register("cheese_block", CHEESE_BLOCK);
		Register("cheese", CHEESE);
		Register("grilled_cheese", GRILLED_CHEESE);
	}
	public static void RegisterHedgehog() {
		Register("hedgehog", HEDGEHOG_ENTITY);
		SpawnRestrictionAccessor.callRegister(HEDGEHOG_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(HEDGEHOG_ENTITY, HedgehogEntity.createHedgehogAttributes());
		Register("hedgehog_spawn_egg", HEDGEHOG_SPAWN_EGG);
	}
	public static void RegisterRaccoon() {
		Register("raccoon", RACCOON_ENTITY);
		SpawnRestrictionAccessor.callRegister(RACCOON_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(RACCOON_ENTITY, RaccoonEntity.createRaccoonAttributes());
		Register("raccoon_spawn_egg", RACCOON_SPAWN_EGG);
	}
	public static void RegisterRedPanda() {
		Register("red_panda", RED_PANDA_ENTITY);
		SpawnRestrictionAccessor.callRegister(RED_PANDA_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		FabricDefaultAttributeRegistry.register(RED_PANDA_ENTITY, RedPandaEntity.createRedPandaAttributes());
		Register("red_panda_spawn_egg", RED_PANDA_SPAWN_EGG);
	}
	public static void RegisterPoison() {
		Register("poison_vial", POISON_VIAL);
		Register("spider_poison_vial", SPIDER_POISON_VIAL);
		Register("pufferfish_poison_vial", PUFFERFISH_POISON_VIAL);
		Register("poisonous_carrot", POISONOUS_CARROT);
		Register("poisonous_beetroot", POISONOUS_BEETROOT);
		Register("poisonous_glow_berries", POISONOUS_GLOW_BERRIES);
		Register("poisonous_sweet_berries", POISONOUS_SWEET_BERRIES);
		Register("poisonous_tomato", POISONOUS_TOMATO);
		Register("wilted_cabbage", WILTED_CABBAGE);
		Register("wilted_onion", WILTED_ONION);
		Register("moldy_bread", MOLDY_BREAD);
		Register("moldy_cookie", MOLDY_COOKIE);
		Register("rotten_pumpkin_pie", ROTTEN_PUMPKIN_PIE);
		Register("spoiled_egg", SPOILED_EGG);
		Register("rotten_beef", ROTTEN_BEEF);
		Register("rotten_chevon", ROTTEN_CHEVON);
		Register("rotten_chicken", ROTTEN_CHICKEN);
		Register("rotten_cod", ROTTEN_COD);
		Register("rotten_mutton", ROTTEN_MUTTON);
		Register("rotten_porkchop", ROTTEN_PORKCHOP);
		Register("rotten_rabbit", ROTTEN_RABBIT);
		Register("rotten_salmon", ROTTEN_SALMON);
	}
	public static void RegisterCakes() {
		CompostingChanceRegistry COMPOST = CompostingChanceRegistry.INSTANCE;
		Register("small_soul_flame", SMALL_SOUL_FLAME_PARTICLE);
		Register("soul_candle", SOUL_CANDLE);
		Register("soul_candle_cake", SOUL_CANDLE_CAKE);
		Register("small_ender_flame", SMALL_ENDER_FLAME_PARTICLE);
		Register("ender_candle", ENDER_CANDLE);
		Register("ender_candle_cake", ENDER_CANDLE_CAKE);
		Register(CHOCOLATE_CAKE);
		COMPOST.add(CHOCOLATE_CAKE.getCake().getItem(), 1F);
		Register(COFFEE_CAKE);
		COMPOST.add(COFFEE_CAKE.getCake().getItem(), 1F);
		Register(STRAWBERRY_CAKE);
		COMPOST.add(STRAWBERRY_CAKE.getCake().getItem(), 1F);
		Register(VANILLA_CAKE);
		COMPOST.add(VANILLA_CAKE.getCake().getItem(), 1F);
		Register(CARROT_CAKE);
		COMPOST.add(CARROT_CAKE.getCake().getItem(), 1F);
		Register(CONFETTI_CAKE);
		COMPOST.add(CONFETTI_CAKE.getCake().getItem(), 1F);
		Register(CHORUS_CAKE);
		COMPOST.add(CHORUS_CAKE.getCake().getItem(), 1F);
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
	public static void RegisterWoodcutter() {
		Registry.register(Registry.RECIPE_TYPE, ID("woodcutting"),WOODCUTTING_RECIPE_TYPE);
		Registry.register(Registry.RECIPE_SERIALIZER, ID("woodcutting"), WOODCUTTING_RECIPE_SERIALIZER);
		ScreenRegistry.register(WOODCUTTER_SCREEN_HANDLER, WoodcutterScreen::new);
	}
	public static void RegisterLiquidMud() {
		Register("mud_bubble", MUD_BUBBLE);
		Register("mud_splash", MUD_SPLASH);
		Register("dripping_mud", DRIPPING_MUD);
		Register("falling_mud", FALLING_MUD);
		Register("falling_dripstone_mud", FALLING_DRIPSTONE_MUD);
		Register("mud_bottle", MUD_BOTTLE);
		Register("mud_bucket", MUD_BUCKET);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(MUD_BUCKET, MudCauldronBlock.FillFromBucket(Items.BUCKET));
		FluidStorage.combinedItemApiProvider(MUD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_MUD_FLUID), FluidConstants.BOTTLE));
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
		Register("glow_flame", UNDERWATER_TORCH_GLOW);
		Register("unlit", UNLIT_TORCH);
		Register("unlit_soul", UNLIT_SOUL_TORCH);
		Register("unlit_lantern", UNLIT_LANTERN);
		Register("unlit_soul_lantern", UNLIT_SOUL_LANTERN);
		Register("ender_fire_flame", ENDER_FIRE_FLAME_PARTICLE);
		Register("ender", ENDER_TORCH);
		Register("ender_lantern", ENDER_LANTERN);
		Register("unlit_ender_lantern", UNLIT_ENDER_LANTERN);
		Register("ender_campfire", ENDER_CAMPFIRE);
		RegisterChiseledBookshelves();
		Register("underwater", UNDERWATER_TORCH);
		Register(BONE_MATERIAL);
		Register("tinker_toy", TINKER_TOY);
		Register("charcoal_block", CHARCOAL_BLOCK);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK.getItem(), 16000);
		RegisterFlowers();
		Register(STUDDED_LEATHER_MATERIAL);
		RegisterFleece();
		Register(AMETHYST_MATERIAL);
		Register("tinted_goggles", TINTED_GOGGLES);
		Register(EMERALD_MATERIAL);
		Register(DIAMOND_MATERIAL);
		Register(QUARTZ_MATERIAL);
		RegisterObsidian();
		Register(CALCITE_MATERIAL);
		Register(DRIPSTONE_MATERIAL);
		Register(TUFF_MATERIAL);
		Register(PURPUR_MATERIAL);
		Register(GILDED_BLACKSTONE_MATERIAL);
		Register("chiseled_polished_gilded_blackstone", CHISELED_POLISHED_GILDED_BLACKSTONE);
		Register("cracked_polished_gilded_blackstone_bricks", CRACKED_POLISHED_GILDED_BLACKSTONE_BRICKS);
		Register("tinted_glass_pane", TINTED_GLASS_PANE);
		RegisterPlushies();
		RegisterDecorativeBlocks();
		Register("sbehesohe", SBEHESOHE);
		Register("broken_bottle", BROKEN_BOTTLE);
		Register("pteror", PTEROR);
		Register("locket", LOCKET);
		Register("emerald_locket", EMERALD_LOCKET);
		Register("amber_eye", AMBER_EYE);
		Register("amber_eye_end_portal_frame", AMBER_EYE_END_PORTAL_FRAME);
		Register("vector_arrow", VECTOR_ARROW_PARTICLE);
		RegisterSoleil();
		RegisterCoffee();
		Register("haven_boat", BOAT_ENTITY);
		//RegisterChiseledBookshelves();
		RegisterCherry();
		RegisterCinnamon();
		RegisterVanilla();
		RegisterBamboo();
		Register(SUGAR_CANE_MATERIAL);
		Register(HAY_MATERIAL);
		Register(CHARRED_MATERIAL);
		RegisterVanillaWood();
		RegisterMushroomWood();
		RegisterVanillaNetherWood();
		RegisterGildedFungus();
		Register("blue_shroomlight", BLUE_SHROOMLIGHT);
		CompostingChanceRegistry.INSTANCE.add(BLUE_SHROOMLIGHT.getItem(), 0.65F);
		Register(WOOD_MATERIAL);
		Register("shoddy_wood_bucket", SHODDY_WOOD_BUCKET);
		RegisterThrowableTomatoes();
		RegisterServerBlood();
		Register("bleeding", BLEEDING_EFFECT);
		Register("bone_rot", BONE_ROT_EFFECT);
		Register("marked", MARKED_EFFECT);
		Register("withering", WITHERING_EFFECT);
		Register("protected", PROTECTED_EFFECT);
		Register("relieved", RELIEVED_EFFECT);
		Register("flashbanged", FLASHBANGED_EFFECT);
		JUICE_MAP.put(() -> BLOOD_BLOCK.getItem(), BLOOD_BOTTLE);
		JUICE_MAP.put(() -> MUD.getItem(), MUD_BOTTLE);
		JUICE_MAP.put(() -> Items.HONEY_BLOCK, Items.HONEY_BOTTLE);
		JUICE_MAP.put(() -> Items.SLIME_BLOCK, SLIME_BOTTLE);
		JUICE_MAP.put(() -> Items.MAGMA_BLOCK, MAGMA_CREAM_BOTTLE);
		JUICE_MAP.put(() -> Items.ICE, DISTILLED_WATER_BOTTLE);
		JUICE_MAP.put(() -> Items.BLUE_ICE, DISTILLED_WATER_BOTTLE);
		JUICE_MAP.put(() -> Items.PACKED_ICE, DISTILLED_WATER_BOTTLE);
		RegisterCommands();
		RegisterOriginPowers();
		Register("grappling_rod", GRAPPLING_ROD);
		RegisterAngelBat();
		RegisterGourds();
		RegisterRainbow();
		RegisterSheepVariants();
		RegisterChickenVariants();
		RegisterCowVariants();
		Register("ramen", RAMEN);
		Register("stir_fry", STIR_FRY);
		RegisterStrawberries();
		RegisterGrapes();
		RegisterJuice();
		RegisterMilkshakesIceCream();
		RegisterCandy();
		Register("seed_block", SEED_BLOCK);
		CompostingChanceRegistry.INSTANCE.add(SEED_BLOCK.getItem(), 1F);
		RegisterCookies();
		RegisterMilks();
		RegisterRaccoon();
		RegisterRedPanda();
		RegisterHedgehog();
		Register("hotdog", HOTDOG);
		Register("green_apple", GREEN_APPLE);
		Register("golden_potato", GOLDEN_POTATO);
		Register("golden_baked_potato", GOLDEN_BAKED_POTATO);
		Register("golden_beetroot", GOLDEN_BEETROOT);
		Register("golden_chorus_fruit", GOLDEN_CHORUS_FRUIT);
		Register("golden_tomato", GOLDEN_TOMATO);
		Register("golden_onion", GOLDEN_ONION);
		Register("golden_egg", GOLDEN_EGG);
		Register("red_curse_breaker_potion", RED_CURSE_BREAKER_POTION);
		Register("white_curse_breaker_potion", WHITE_CURSE_BREAKER_POTION);
		RegisterPoison();
		RegisterCakes();
		RegisterBottledConfetti();
		RegisterCopper();
		RegisterGold();
		RegisterIron();
		RegisterNetherite();
		RegisterWoodcutter();
		Register("hedge_block", HEDGE_BLOCK);
		Register("wax_block", WAX_BLOCK);
		Register("horn", HORN);
		RegisterLiquidMud();
		RegisterDynamicLights();
		Register119();
	}
}
