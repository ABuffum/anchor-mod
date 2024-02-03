package fun.wich.block.basic;

import fun.wich.ModId;
import net.minecraft.block.BedBlock;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBedBlock extends BedBlock {
	private final Identifier texture;
	public ModBedBlock(String name, Settings settings) {
		super(DyeColor.WHITE, settings);
		this.texture = ModId.ID("entity/bed/" + name);
	}
	public Identifier GetTexture() { return texture; }
}
