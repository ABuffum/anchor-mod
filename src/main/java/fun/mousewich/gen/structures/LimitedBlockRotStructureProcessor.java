package fun.mousewich.gen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.mousewich.util.StructureUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LimitedBlockRotStructureProcessor extends StructureProcessor {
	private static Object RegistryCodecs;
	public static final Codec<LimitedBlockRotStructureProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockState.CODEC.listOf().fieldOf("rottable_blocks").forGetter(processor -> new ArrayList<BlockState>(processor.rottableBlocks)),
			Codec.floatRange(0.0f, 1.0f).fieldOf("integrity").forGetter(processor -> processor.integrity))
			.apply(instance, (rottableBlocks, integrity) -> new LimitedBlockRotStructureProcessor((List<BlockState>)rottableBlocks, (float)integrity)));
	private List<BlockState> rottableBlocks;
	private final float integrity;

	public LimitedBlockRotStructureProcessor(float integrity) { this(new ArrayList<BlockState>(), integrity); }
	public LimitedBlockRotStructureProcessor(Tag.Identified<Block> rottableBlocks, float integrity) {
		this.integrity = integrity;
		this.rottableBlocks = new ArrayList<>();
		for (Block block : rottableBlocks.values()) this.rottableBlocks.add(block.getDefaultState());
	}
	public LimitedBlockRotStructureProcessor(List<BlockState> rottableBlocks, float integrity) {
		this.integrity = integrity;
		this.rottableBlocks = rottableBlocks;
	}

	@Override
	@Nullable
	public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo originalBlockInfo, Structure.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
		Random random = data.getRandom(currentBlockInfo.pos);
		if (this.rottableBlocks.size() > 0 && !StructureUtil.contains(this.rottableBlocks, originalBlockInfo.state) || random.nextFloat() <= this.integrity) {
			return currentBlockInfo;
		}
		return null;
	}

	public boolean canGrowOn(Block block) { return this.rottableBlocks.stream().anyMatch((state) -> state.isOf(block)); }

	@Override
	protected StructureProcessorType<?> getType() {
		return StructureProcessorType.BLOCK_ROT;
	}
}