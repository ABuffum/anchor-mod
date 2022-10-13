package haven.materials.base;

import haven.HavenMod;
import haven.containers.BlockContainer;
import haven.containers.TorchContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
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
	protected Item.Settings ItemSettings() { return HavenMod.ItemSettings(); }
	public boolean contains(Block block) { return false; }
	public boolean contains(Item item) { return false; }
	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}
	public static Boolean always(BlockState state, BlockView world, BlockPos pos) { return true; }
	public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return true; }
	public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
	}

	protected AbstractBlock.Settings TorchSettings(int luminance, BlockSoundGroup sounds) {
		return FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().nonOpaque().luminance(luminance(luminance)).sounds(sounds);
	}

	protected TorchContainer MakeTorch(int luminance, BlockSoundGroup sounds, DefaultParticleType particle) {
		return new TorchContainer(TorchSettings(luminance, sounds), particle);
	}
	protected AbstractBlock.Settings LanternSettings(int luminance) {
		return AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.5F).sounds(BlockSoundGroup.LANTERN).luminance(luminance(luminance)).nonOpaque();
	}
	protected BlockContainer MakeLantern(int luminance) {
		return new BlockContainer(new LanternBlock(LanternSettings(luminance)));
	}
	protected BlockContainer MakeCampfire(int luminance, int fireDamage, MapColor mapColor) {
		return new BlockContainer(new CampfireBlock(false, fireDamage, AbstractBlock.Settings.of(Material.WOOD, mapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD).luminance(createLightLevelFromLitBlockState(luminance)).nonOpaque()));
	}
}
