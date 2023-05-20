package fun.mousewich.block.gourd;

import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;

import java.util.function.Supplier;

public class ModGourdBlock extends GourdBlock {
	private final Supplier<StemBlock> stem;
	private final Supplier<AttachedStemBlock> attachedStem;

	public ModGourdBlock(Settings settings, Supplier<StemBlock> stem, Supplier<AttachedStemBlock> attachedStem) {
		super(settings);
		this.stem = stem;
		this.attachedStem = attachedStem;
	}
	public StemBlock getStem() { return stem.get(); }
	public AttachedStemBlock getAttachedStem() { return attachedStem.get(); }
}
