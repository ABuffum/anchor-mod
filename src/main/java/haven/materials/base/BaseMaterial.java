package haven.materials.base;

import haven.ModBase;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

public abstract class BaseMaterial {
	private final String name;
	public String getName() { return name; }
	protected boolean flammable = true;
	public boolean isFlammable() { return flammable; }
	public BaseMaterial(String name, boolean flammable) {
		this.name = name;
		this.flammable = flammable;
	}
	protected Item.Settings ItemSettings() { return ModBase.ItemSettings(); }
	public boolean contains(Block block) { return false; }
	public boolean contains(Item item) { return false; }
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}
	public static Boolean always(BlockState state, BlockView world, BlockPos pos) { return true; }
	public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return true; }
	public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
	}

	public static AbstractBlock.Settings TorchSettings(ToIntFunction<BlockState> luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance).sounds(sounds);
	}

	protected TorchContainer MakeTorch(ToIntFunction<BlockState> luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return MakeTorch(luminance, sounds, particle, ItemSettings());
	}
	public static TorchContainer MakeTorch(ToIntFunction<BlockState> luminance, BlockSoundGroup sounds, DefaultParticleType particle, Item.Settings settings) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle, settings);
	}
	public static AbstractBlock.Settings LanternSettings(ToIntFunction<BlockState> luminance) {
		return AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance).nonOpaque();
	}
	protected BlockContainer MakeLantern(ToIntFunction<BlockState> luminance) { return MakeLantern(luminance, ItemSettings()); }
	public static BlockContainer MakeLantern(ToIntFunction<BlockState> luminance, Item.Settings settings) {
		return new BlockContainer(new LanternBlock(LanternSettings(luminance)), settings);
	}
	protected BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles) {
		return MakeCampfire(luminance, fireDamage, mapColor, sounds, emitsParticles, ItemSettings());
	}
	public static BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor, BlockSoundGroup sounds, boolean emitsParticles, Item.Settings settings) {
		return new BlockContainer(new CampfireBlock(false, fireDamage, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(sounds).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()), settings);
	}
}
