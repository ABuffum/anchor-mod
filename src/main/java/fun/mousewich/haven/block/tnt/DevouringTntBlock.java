package fun.mousewich.haven.block.tnt;

import fun.mousewich.block.tnt.ModTntBlock;
import fun.mousewich.haven.HavenMod;
import fun.mousewich.haven.entity.tnt.DevouringTntEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

public class DevouringTntBlock extends ModTntBlock {

	public DevouringTntBlock(Settings settings) { super(settings); }

	@Override
	public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient) {
			DevouringTntEntity tntEntity = new DevouringTntEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, explosion.getCausingEntity());
			int i = tntEntity.getFuse();
			tntEntity.setFuse((short)(world.random.nextInt(i / 4) + i / 8));
			world.spawnEntity(tntEntity);
		}
	}

	@Override
	protected void primeTnt(World world, BlockPos pos, LivingEntity igniter) {
		if (!world.isClient) {
			DevouringTntEntity tntEntity = new DevouringTntEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter);
			world.spawnEntity(tntEntity);
			world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), HavenMod.DEVOURING_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
		}
	}
}
