package haven.deprecated;
import haven.blocks.sculk.HavenSculkSensorBlock;
import haven.blocks.sculk.SculkCatalystBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.state.StateManager;

public class DeprecatedSculkSensorBlock extends DeprecatedBlock {
	public DeprecatedSculkSensorBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(HavenSculkSensorBlock.SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE).with(HavenSculkSensorBlock.POWER, 0).with(HavenSculkSensorBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HavenSculkSensorBlock.SCULK_SENSOR_PHASE, HavenSculkSensorBlock.POWER, HavenSculkSensorBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(HavenSculkSensorBlock.SCULK_SENSOR_PHASE, state.get(HavenSculkSensorBlock.SCULK_SENSOR_PHASE)).with(HavenSculkSensorBlock.POWER, state.get(HavenSculkSensorBlock.POWER)).with(HavenSculkSensorBlock.WATERLOGGED, state.get(HavenSculkSensorBlock.WATERLOGGED));
	}
}
