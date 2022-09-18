package haven.materials.base;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

public abstract class BaseMaterial {
	private final String name;
	public String getName() { return name; }
	private boolean flammable = true;
	public boolean isFlammable() { return flammable; }
	public BaseMaterial(String name, boolean flammable) {
		this.name = name;
		this.flammable = flammable;
	}

	protected Item.Settings ItemSettings() {
		return HavenMod.ItemSettings();
	}

	public boolean contains(Block block) { return false; }
	public boolean contains(Item item) { return false; }

	public static ToIntFunction<BlockState> luminance(int value) { return (state) -> value; }
	public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
	public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}
}
