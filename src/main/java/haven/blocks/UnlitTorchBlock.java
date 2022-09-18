package haven.blocks;

import haven.HavenMod;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class UnlitTorchBlock extends TorchBlock {
	private final Block lit;
	public UnlitTorchBlock(Block lit) {
		super(AbstractBlock.Settings.copy(lit).luminance(HavenMod.luminance(0)).dropsLike(lit), ParticleTypes.FLAME);
		this.lit = lit;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
			world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			world.setBlockState(pos, lit.getDefaultState());
			world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos);
			itemStack.damage(1, (LivingEntity)player, (p) -> p.sendToolBreakStatus(hand));
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) { }

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return lit.getPickStack(world, pos, state);
	}
}
