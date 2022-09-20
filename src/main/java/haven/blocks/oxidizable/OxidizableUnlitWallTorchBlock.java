package haven.blocks.oxidizable;

import haven.HavenMod;
import haven.blocks.UnlitWallTorchBlock;
import haven.util.OxidationScale;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;
import java.util.Random;

public class OxidizableUnlitWallTorchBlock extends UnlitWallTorchBlock implements Oxidizable {
	private final OxidizationLevel oxidizationLevel;

	public OxidizableUnlitWallTorchBlock(OxidizationLevel oxidizationLevel, Block lit, Block notWall) {
		super(lit, notWall);
		this.oxidizationLevel = oxidizationLevel;
	}

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.tickDegradation(state, world, pos, random);
	}

	public boolean hasRandomTicks(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).isPresent();
	}

	public OxidizationLevel getDegradationLevel() {
		return this.oxidizationLevel;
	}

	@Override
	public Optional<BlockState> getDegradationResult(BlockState state) {
		return OxidationScale.getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}
}
