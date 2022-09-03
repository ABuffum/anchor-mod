package haven;

import haven.blocks.BloodCauldronBlock;
import haven.entities.SoftTntEntity;
import haven.materials.WoodMaterial;
import haven.util.*;

import static haven.HavenMod.*;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HavenRegistry {
	public static Block Register(String path, Block block) {
		return Registry.register(Registry.BLOCK, ID(path), block);
	}
	public static Item Register(String path, Item item) {
		return Registry.register(Registry.ITEM, ID(path), item);
	}
	public static void Register(String path, HavenPair pair) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, pair.BLOCK);
		Registry.register(Registry.ITEM, id, pair.ITEM);
	}
	public static <T extends BlockEntity> BlockEntityType Register(String path, BlockEntityType<T> entity) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ID(path), entity);
	}
	public static StatusEffect Register(String path, StatusEffect effect) {
		return Registry.register(Registry.STATUS_EFFECT, ID(path), effect);
	}
	public static <T extends Entity> EntityType<T> Register(String path, EntityType entityType) {
		return Registry.register(Registry.ENTITY_TYPE, ID(path), entityType);
	}
	public static void Register(String path, PottedBlock potted) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, potted.BLOCK);
		Registry.register(Registry.ITEM, id, potted.ITEM);
		Registry.register(Registry.BLOCK, ID("potted_" + path), potted.POTTED);
	}
	public static void Register(String path, String wallPath, HavenTorch torch) {
		Identifier id = ID(path);
		Registry.register(Registry.BLOCK, id, torch.BLOCK);
		Registry.register(Registry.BLOCK, ID(wallPath), torch.WALL_BLOCK);
		Registry.register(Registry.ITEM, id, torch.ITEM);
	}
	public static void Register(String name, WoodMaterial material) {
		Register(name + "_log", material.LOG);
		Register("stripped_" + name + "_log", material.STRIPPED_LOG);
		Register(name + "_wood", material.WOOD);
		Register("stripped_" + name + "_wood", material.STRIPPED_WOOD);

		Register(name + "_leaves", material.LEAVES);
		Register(name + "_sapling", material.SAPLING);

		Register(name + "_planks", material.PLANKS);
		Register(name + "_stairs", material.STAIRS);
		Register(name + "_slab", material.SLAB);

		Register(name + "_fence", material.FENCE);
		Register(name + "_fence_gate", material.FENCE_GATE);
		Register(name + "_door", material.DOOR);
		Register(name + "_trapdoor", material.TRAPDOOR);
		Register(name + "_pressure_plate", material.PRESSURE_PLATE);
		Register(name + "_button", material.BUTTON);

		//TODO: Signs don't work right (fail out of edit screens and go invisible)
		//Register(name + "_sign", material.SIGN_BLOCK, material.SIGN_ITEM);
		//Register(name + "_wall_sign", material.WALL_SIGN_BLOCK);
	}

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
	public static void RegisterAmethyst() {
		Register("amethyst_crystal_block", AMETHYST_CRYSTAL_BLOCK);
		//Amethyst Bricks
		Register("amethyst_bricks", AMETHYST_BRICKS);
		Register("amethyst_brick_slab", AMETHYST_BRICK_SLAB);
		Register("amethyst_brick_stairs", AMETHYST_BRICK_STAIRS);
		Register("amethyst_brick_wall", AMETHYST_BRICK_WALL);
		//Amethyst Variants
		Register("amethyst_slab", AMETHYST_SLAB);
		Register("amethyst_stairs", AMETHYST_STAIRS);
		Register("amethyst_wall", AMETHYST_WALL);
	}
	public static void RegisterFlowers() {
		//Carnations
		for (DyeColor color : COLORS) {
			Register(color.getName() + "_carnation", CARNATIONS.get(color));
		}
		//Minecraft Earth Flowers
		Register("buttercup", BUTTERCUP);
		Register("pink_daisy", PINK_DAISY);
		//Other Flowers
		Register("rose", ROSE);
		Register("blue_rose", BLUE_ROSE);
		Register("magenta_tulip", MAGENTA_TULIP);
		Register("marigold", MARIGOLD);
		Register("pink_allium", PINK_ALLIUM);
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
		Register("coffee_cherry", COFFEE_CHERRY);
		Register("coffee_beans", COFFEE_BEANS);
		Register("coffee", COFFEE);
		Register("black_coffee", BLACK_COFFEE);
	}
	public static void RegisterCherry() {
		Register("cherry", CHERRY);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("cherry_tree"), CHERRY_TREE);
		Register("white_cherry_leaves", WHITE_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("white_cherry_tree"), WHITE_CHERRY_TREE);
		Register("pale_cherry_leaves", PALE_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pale_cherry_tree"), PALE_CHERRY_TREE);
		Register("pink_cherry_leaves", PINK_CHERRY_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID("pink_cherry_tree"), PINK_CHERRY_TREE);
		//TODO: Cherry Fruit
	}
	public static void RegisterCinnamon() {
		Register("cinnamon", CINNAMON);
		Register("cassia", CASSIA);
		Register("flowering_cassia_leaves", FLOWERING_CASSIA_LEAVES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(NAMESPACE, "cassia_tree"), CASSIA_TREE);
		Register("snickerdoodle", SNICKERDOODLE);
		Register("cinnamon_roll", CINNAMON_ROLL);
	}
	public static void RegisterCandy() {
		Register("cinnamon_bean", CINNAMON_BEAN);
		Register("pink_cotton_candy", PINK_COTTON_CANDY);
		Register("blue_cotton_candy", BLUE_COTTON_CANDY);
		Register("candy_cane", CANDY_CANE);
		Register("caramel", CARAMEL);
		Register("caramel_apple", CARAMEL_APPLE);
		//Amethyst & Rock Candy
		Register("amethyst_candy", AMETHYST_CANDY);
		for(DyeColor color : COLORS) {
			Register(color.getName() + "_rock_candy", ROCK_CANDIES.get(color));
		}
	}
	public static void RegisterThrowableTomatoes() {
		Register("throwable_tomato", THROWABLE_TOMATO_ITEM);
		Register("throwable_tomato", THROWABLE_TOMATO_ENTITY);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(NAMESPACE, "thrown_tomato"), TOMATO_PARTICLE);
		Register("boo", BOO_EFFECT);
		Register("killjoy", KILLJOY_EFFECT);
	}
	public static void RegisterServerBlood() {
		Register("blood_block", BLOOD_BLOCK);
		Register("blood_fence", BLOOD_FENCE);
		Register("blood_pane", BLOOD_PANE);
		Register("blood_slab", BLOOD_SLAB);
		Register("blood_stairs", BLOOD_STAIRS);
		Register("blood_wall", BLOOD_WALL);
		Register("dried_blood_block", DRIED_BLOOD_BLOCK);
		Register("dried_blood_fence", DRIED_BLOOD_FENCE);
		Register("dried_blood_pane", DRIED_BLOOD_PANE);
		Register("dried_blood_slab", DRIED_BLOOD_SLAB);
		Register("dried_blood_stairs", DRIED_BLOOD_STAIRS);
		Register("dried_blood_wall", DRIED_BLOOD_WALL);

		Register("blood_bucket", BLOOD_BUCKET);
		Register("blood_cauldron", BLOOD_CAULDRON);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BUCKET, BloodCauldronBlock.FILL_FROM_BUCKET);
		CauldronFluidContent.registerCauldron(BLOOD_CAULDRON, STILL_BLOOD_FLUID, FluidConstants.BOTTLE, LeveledCauldronBlock.LEVEL);
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.BLOOD_BOTTLE, BloodCauldronBlock.FILL_FROM_BOTTLE);
		BloodCauldronBlock.BLOOD_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, BloodCauldronBlock.EMPTY_TO_BOTTLE);
		Register("blood_bottle", BLOOD_BOTTLE);
		FluidStorage.combinedItemApiProvider(BLOOD_BOTTLE).register(context -> new FullItemFluidStorage(context, bottle -> ItemVariant.of(Items.GLASS_BOTTLE), FluidVariant.of(STILL_BLOOD_FLUID), FluidConstants.BOTTLE));
	}

	public static void RegisterAll() {
		RegisterAnchors();
		Register("bone_torch", "bone_wall_torch", BONE_TORCH);
		Register("tinker_toy", TINKER_TOY);
		Register("charcoal_block", CHARCOAL_BLOCK);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK.ITEM, 16000);
		RegisterFlowers();
		RegisterAmethyst();
		Register("pteror", PTEROR);
		Register("sbehesohe", SBEHESOHE);
		RegisterSoftTNT();
		RegisterCoffee();
		RegisterCherry();
		RegisterCinnamon();
		RegisterCandy();
		RegisterThrowableTomatoes();
		RegisterServerBlood();
	}
}
