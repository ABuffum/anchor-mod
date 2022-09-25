package haven.blocks.gourd;

import net.minecraft.block.*;

import java.util.function.Supplier;

public class HavenGourdBlock extends GourdBlock {
	private final Supplier<StemBlock> stem;
	private final Supplier<AttachedStemBlock> attachedStem;

	public HavenGourdBlock(Settings settings, Supplier<StemBlock> stem, Supplier<AttachedStemBlock> attachedStem) {
		super(settings);
		this.stem = stem;
		this.attachedStem = attachedStem;
	}
	public StemBlock getStem() { return stem.get(); }
	public AttachedStemBlock getAttachedStem() { return attachedStem.get(); }
}
