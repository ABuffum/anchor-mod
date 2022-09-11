package haven.blocks.complex;

import haven.HavenMod;
import net.minecraft.block.BedBlock;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class HavenBedBlock extends BedBlock {
	private final Identifier texture;
	public HavenBedBlock(String name, Settings settings) {
		super(DyeColor.WHITE, settings);
		this.texture = HavenMod.ID("entity/bed/" + name);
	}

	public Identifier GetTexture() {
		return texture;
	}
}
