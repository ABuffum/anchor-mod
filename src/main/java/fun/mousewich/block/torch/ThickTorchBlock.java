package fun.mousewich.block.torch;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class ThickTorchBlock extends LightableTorchBlock {
	protected static final VoxelShape SHAPE = createCuboidShape(6, 0, 6, 10, 8, 10);
	public ThickTorchBlock(Settings settings, ParticleEffect particle) { super(settings, particle); }
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(Properties.LIT)) {
			double d = pos.getX() + 0.5;
			double e = pos.getY() + 0.5;
			double f = pos.getZ() + 0.5;
			world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
			world.addParticle(this.particle, d, e, f, 0.0, 0.0, 0.0);
		}
	}
}
