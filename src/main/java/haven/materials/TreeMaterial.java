package haven.materials;

import haven.blocks.*;
import haven.boats.HavenBoat;
import haven.util.HavenPair;
import haven.util.HavenSapling;
import haven.util.HavenSign;
import net.minecraft.block.*;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class TreeMaterial extends BaseTreeMaterial {
	public final HavenSapling SAPLING;

	public TreeMaterial(String name, MapColor mapColor, Supplier<SaplingGenerator> saplingGenerator) {
		this(name, mapColor, saplingGenerator, true);
	}
	public TreeMaterial(String name, MapColor mapColor, Supplier<SaplingGenerator> saplingGenerator, boolean isFlammable) {
		this(name, mapColor, BlockSoundGroup.GRASS, saplingGenerator, isFlammable);
	}
	public TreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, Supplier<SaplingGenerator> saplingGenerator) {
		this(name, mapColor, leafSounds, saplingGenerator, true);
	}
	public TreeMaterial(String name, MapColor mapColor, BlockSoundGroup leafSounds, Supplier<SaplingGenerator> saplingGenerator, boolean isFlammable) {
		super(name, mapColor, leafSounds, isFlammable);
		SAPLING = new HavenSapling(saplingGenerator);
	}
}
