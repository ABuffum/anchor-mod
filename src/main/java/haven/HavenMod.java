package haven;

import haven.anchors.*;
import haven.blocks.*;
import haven.entities.*;
import haven.items.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.HashMap;
import static java.util.Map.entry;

import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.*;

@SuppressWarnings("deprecation")
public class HavenMod implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger();

	public static final String NAMESPACE = "haven";
	
	public static BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY;
	public static final Block ANCHOR_BLOCK = new AnchorBlock(FabricBlockSettings.of(Material.SOIL).hardness(1f));

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(NAMESPACE, "general"), () -> new ItemStack(ANCHOR_BLOCK));
	public static final Item.Settings ITEM_SETTINGS = new Item.Settings().group(ITEM_GROUP);

	public static final Item ANCHOR_ITEM = new BlockItem(ANCHOR_BLOCK, ITEM_SETTINGS);

	public static final Block BONE_TORCH_BLOCK = new BoneTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance((state) -> {
		return 14;
	}).sounds(BlockSoundGroup.BONE), ParticleTypes.FLAME);
	public static final Block BONE_WALL_TORCH_BLOCK = new BoneWallTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance((state) -> {
		return 14;
	}).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME);
	public static final BlockItem BONE_TORCH_ITEM = new WallStandingBlockItem(BONE_TORCH_BLOCK, BONE_WALL_TORCH_BLOCK, ITEM_SETTINGS);

	public static final Item TINKER_TOY_ITEM = new TinkerToy(new FabricItemSettings().group(ITEM_GROUP));

	public static final Block CHARCOAL_BLOCK = new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F));
	public static final Item CHARCOAL_BLOCK_ITEM = new BlockItem(CHARCOAL_BLOCK, new Item.Settings().group(ITEM_GROUP));

	public static final Block AMETHYST_CRYSTAL_BLOCK = new AmethystCrystalBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f, 1.5f).requiresTool().luminance((state) -> {
		return 5;
	}));
	public static final Item AMETHYST_CRYSTAL_BLOCK_ITEM = new BlockItem(AMETHYST_CRYSTAL_BLOCK, new Item.Settings().group(ITEM_GROUP));

	public static final Block AMETHYST_BRICKS_BLOCK = new AmethystBricksBlock(FabricBlockSettings.of(Material.AMETHYST, MapColor.PURPLE).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f, 1.5f).requiresTool());
	public static final Item AMETHYST_BRICKS_ITEM = new BlockItem(AMETHYST_BRICKS_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block AMETHYST_BRICK_SLAB_BLOCK = new AmethystBrickSlabBlock(AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_SLAB_ITEM = new BlockItem(AMETHYST_BRICK_SLAB_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block AMETHYST_BRICK_STAIRS_BLOCK = new AmethystBrickStairsBlock(AMETHYST_BRICKS_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_STAIRS_ITEM = new BlockItem(AMETHYST_BRICK_STAIRS_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block AMETHYST_BRICK_WALL_BLOCK = new AmethystBrickWallBlock(AbstractBlock.Settings.copy(AMETHYST_BRICKS_BLOCK));
	public static final Item AMETHYST_BRICK_WALL_ITEM = new BlockItem(AMETHYST_BRICK_WALL_BLOCK, new Item.Settings().group(ITEM_GROUP));

	public static final Block AMETHYST_SLAB_BLOCK = new AmethystSlabBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_SLAB_ITEM = new BlockItem(AMETHYST_SLAB_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block AMETHYST_STAIRS_BLOCK = new AmethystStairsBlock(Blocks.AMETHYST_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_STAIRS_ITEM = new BlockItem(AMETHYST_STAIRS_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block AMETHYST_WALL_BLOCK = new AmethystWallBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK));
	public static final Item AMETHYST_WALL_ITEM = new BlockItem(AMETHYST_WALL_BLOCK, new Item.Settings().group(ITEM_GROUP));

	//Carnations
	public static final Block BLACK_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BLACK_CARNATION_ITEM = new BlockItem(BLACK_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_BLACK_CARNATION = new FlowerPotBlock(BLACK_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block BLUE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BLUE_CARNATION_ITEM = new BlockItem(BLUE_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_BLUE_CARNATION = new FlowerPotBlock(BLUE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block BROWN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item BROWN_CARNATION_ITEM = new BlockItem(BROWN_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_BROWN_CARNATION = new FlowerPotBlock(BROWN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block CYAN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item CYAN_CARNATION_ITEM = new BlockItem(CYAN_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_CYAN_CARNATION = new FlowerPotBlock(CYAN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block GRAY_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item GRAY_CARNATION_ITEM = new BlockItem(GRAY_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_GRAY_CARNATION = new FlowerPotBlock(GRAY_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block GREEN_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item GREEN_CARNATION_ITEM = new BlockItem(GREEN_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_GREEN_CARNATION = new FlowerPotBlock(GREEN_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIGHT_BLUE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIGHT_BLUE_CARNATION_ITEM = new BlockItem(LIGHT_BLUE_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_LIGHT_BLUE_CARNATION = new FlowerPotBlock(LIGHT_BLUE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIGHT_GRAY_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIGHT_GRAY_CARNATION_ITEM = new BlockItem(LIGHT_GRAY_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_LIGHT_GRAY_CARNATION = new FlowerPotBlock(LIGHT_GRAY_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block LIME_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item LIME_CARNATION_ITEM = new BlockItem(LIME_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_LIME_CARNATION = new FlowerPotBlock(LIME_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block MAGENTA_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item MAGENTA_CARNATION_ITEM = new BlockItem(MAGENTA_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_MAGENTA_CARNATION = new FlowerPotBlock(MAGENTA_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block ORANGE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item ORANGE_CARNATION_ITEM = new BlockItem(ORANGE_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_ORANGE_CARNATION = new FlowerPotBlock(ORANGE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block PINK_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item PINK_CARNATION_ITEM = new BlockItem(PINK_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_PINK_CARNATION = new FlowerPotBlock(PINK_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block PURPLE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item PURPLE_CARNATION_ITEM = new BlockItem(PURPLE_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_PURPLE_CARNATION = new FlowerPotBlock(PURPLE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block RED_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item RED_CARNATION_ITEM = new BlockItem(RED_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_RED_CARNATION = new FlowerPotBlock(RED_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block WHITE_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item WHITE_CARNATION_ITEM = new BlockItem(WHITE_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_WHITE_CARNATION = new FlowerPotBlock(WHITE_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());
	public static final Block YELLOW_CARNATION_BLOCK = new FlowerBlock(StatusEffects.WEAKNESS, 5, FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS));
	public static final Item YELLOW_CARNATION_ITEM = new BlockItem(YELLOW_CARNATION_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final Block POTTED_YELLOW_CARNATION = new FlowerPotBlock(YELLOW_CARNATION_BLOCK, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

	public static final ToolItem PTEROR = new SwordItem(ToolMaterials.NETHERITE, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());
	public static final ToolItem SBEHESOHE = new SwordItem(ToolMaterials.DIAMOND, 3, -2.4F, new Item.Settings().group(ITEM_GROUP).fireproof());

	public static final Block SOFT_TNT_BLOCK = new SoftTntBlock(AbstractBlock.Settings.of(Material.TNT).breakInstantly().sounds(BlockSoundGroup.GRASS));
	public static final Item SOFT_TNT_ITEM = new BlockItem(SOFT_TNT_BLOCK, new Item.Settings().group(ITEM_GROUP));
	public static final EntityModelLayer SOFT_TNT_LAYER = new EntityModelLayer(new Identifier(HavenMod.NAMESPACE, "soft_tnt"), "main");
	public static final EntityType<SoftTntEntity> SOFT_TNT_ENTITY = new FabricEntityTypeBuilderImpl<SoftTntEntity>(SpawnGroup.MISC, SoftTntEntity::new)
			.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeBlocks(10).trackedUpdateRate(10).build();

	@Override
	public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "anchor"), ANCHOR_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "anchor"), ANCHOR_ITEM);

        ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, NAMESPACE + ":" + "anchor_block_entity", FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, ANCHOR_BLOCK).build(null));
        
        //Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
        	Registry.register(Registry.ITEM, new Identifier(NAMESPACE, ANCHOR_MAP.get(owner) + "_core"), ANCHOR_CORES.get(owner));
		}

		//Bone Torch
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "bone_torch"), BONE_TORCH_BLOCK);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "bone_wall_torch"), BONE_WALL_TORCH_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "bone_torch"), BONE_TORCH_ITEM);

		//Tinker Toy
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "tinker_toy"), TINKER_TOY_ITEM);

		//Charcoal Block
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "charcoal_block"), CHARCOAL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "charcoal_block"), CHARCOAL_BLOCK_ITEM);
		FuelRegistry.INSTANCE.add(CHARCOAL_BLOCK_ITEM, 16000);

		//Amethyst Crystal Block
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_crystal_block"), AMETHYST_CRYSTAL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_crystal_block"), AMETHYST_CRYSTAL_BLOCK_ITEM);
		//Amethyst Bricks
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_bricks"), AMETHYST_BRICKS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_bricks"), AMETHYST_BRICKS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_slab"), AMETHYST_BRICK_SLAB_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_slab"), AMETHYST_BRICK_SLAB_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_stairs"), AMETHYST_BRICK_STAIRS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_stairs"), AMETHYST_BRICK_STAIRS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_brick_wall"), AMETHYST_BRICK_WALL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_brick_wall"), AMETHYST_BRICK_WALL_ITEM);
		//Amethyst Variants
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_slab"), AMETHYST_SLAB_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_slab"), AMETHYST_SLAB_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_stairs"), AMETHYST_STAIRS_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_stairs"), AMETHYST_STAIRS_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "amethyst_wall"), AMETHYST_WALL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "amethyst_wall"), AMETHYST_WALL_ITEM);

		//Carnations
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "black_carnation"), BLACK_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "black_carnation"), BLACK_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_black_carnation"), POTTED_BLACK_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "blue_carnation"), BLUE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "blue_carnation"), BLUE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_blue_carnation"), POTTED_BLUE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "brown_carnation"), BROWN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "brown_carnation"), BROWN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_brown_carnation"), POTTED_BROWN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "cyan_carnation"), CYAN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "cyan_carnation"), CYAN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_cyan_carnation"), POTTED_CYAN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "gray_carnation"), GRAY_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "gray_carnation"), GRAY_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_gray_carnation"), POTTED_GRAY_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "green_carnation"), GREEN_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "green_carnation"), GREEN_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_green_carnation"), POTTED_GREEN_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "light_blue_carnation"), LIGHT_BLUE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "light_blue_carnation"), LIGHT_BLUE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_light_blue_carnation"), POTTED_LIGHT_BLUE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "light_gray_carnation"), LIGHT_GRAY_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "light_gray_carnation"), LIGHT_GRAY_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_light_gray_carnation"), POTTED_LIGHT_GRAY_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "lime_carnation"), LIME_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "lime_carnation"), LIME_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_lime_carnation"), POTTED_LIME_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "magenta_carnation"), MAGENTA_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "magenta_carnation"), MAGENTA_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_magenta_carnation"), POTTED_MAGENTA_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "orange_carnation"), ORANGE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "orange_carnation"), ORANGE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_orange_carnation"), POTTED_ORANGE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "pink_carnation"), PINK_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "pink_carnation"), PINK_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_pink_carnation"), POTTED_PINK_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "purple_carnation"), PURPLE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "purple_carnation"), PURPLE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_purple_carnation"), POTTED_PURPLE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "red_carnation"), RED_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "red_carnation"), RED_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_red_carnation"), POTTED_RED_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "white_carnation"), WHITE_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "white_carnation"), WHITE_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_white_carnation"), POTTED_WHITE_CARNATION);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "yellow_carnation"), YELLOW_CARNATION_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "yellow_carnation"), YELLOW_CARNATION_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "potted_yellow_carnation"), POTTED_YELLOW_CARNATION);

		//Reskins
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "pteror"), PTEROR);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "sbehesohe"), SBEHESOHE);

		//Soft TNT
		Registry.register(Registry.BLOCK, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_ITEM);
		Registry.register(Registry.ENTITY_TYPE, new Identifier(NAMESPACE, "soft_tnt"), SOFT_TNT_ENTITY);

		DispenserBlock.registerBehavior(SOFT_TNT_BLOCK, new ItemDispenserBehavior() {
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

		/*
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public void reload(ResourceManager manager) {
				// Clear Caches Here
				for(Identifier id : manager.findResources("my_resource_folder", path -> path.endsWith(".json"))) {
					try(InputStream stream = manager.getResource(id).getInputStream()) {
						// Consume the stream however you want, medium, rare, or well done.
					} catch(Exception e) {
						log(Level.ERROR, "Error occurred while loading resource json " + id.toString() + "\n" + e.toString());
					}
				}
			}

			@Override
			public Identifier getFabricId() {
				return new Identifier("haven", "my_resources");
			}
		});*/
	}
	
	public static Map<Integer, String> ANCHOR_MAP = Map.ofEntries(
		entry(1, "jackdaw"),
		entry(2, "august"),
		entry(3, "bird"),
		entry(4, "moth"),
		entry(5, "rat"),
		entry(6, "stars"),
		entry(7, "whimsy"),
		entry(8, "aster"),
		entry(9, "gawain"),
		entry(10, "sleep"),
		entry(11, "lux"),
		entry(12, "sylph"),
		entry(13, "angel"),
		entry(14, "captain"),
		entry(15, "oz"),
		entry(16, "navn"),
		entry(17, "amber"),
		entry(18, "kota"),
		entry(19, "rhys"),
		entry(20, "soleil"),
		entry(21, "dj"),
		entry(22, "miasma"),
		entry(23, "k")
	);
	public static Map<Integer, AnchorCoreItem> ANCHOR_CORES;

	public static void log(Level level, String message){
		LOGGER.log(level, "[Haven] " + message);
	}
	
	static {
		ANCHOR_CORES = new HashMap<Integer, AnchorCoreItem>();
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}
	}
}
