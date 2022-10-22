package haven.blocks.lighting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;

public interface DynamicLitBlock {
	public static final IntProperty LUMINANCE = IntProperty.of("luminance",1, 15);;
	public static int GetLuminance(BlockState state) { return state.get(LUMINANCE); }

	public Block getUnlitBlock();
}
