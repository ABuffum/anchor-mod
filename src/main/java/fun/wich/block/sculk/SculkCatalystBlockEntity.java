package fun.wich.block.sculk;

import fun.wich.ModBase;
import fun.wich.advancement.ModCriteria;
import fun.wich.event.ModGameEvent;
import fun.wich.mixins.entity.LivingEntityAccessor;
import fun.wich.util.VectorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

public class SculkCatalystBlockEntity extends BlockEntity implements GameEventListener {
	private final BlockPositionSource positionSource;
	private final SculkSpreadManager spreadManager;
	public SculkCatalystBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.SCULK_CATALYST_ENTITY, pos, state);
		this.positionSource = new BlockPositionSource(this.pos);
		this.spreadManager = SculkSpreadManager.create();
	}
	public boolean shouldListenImmediately() { return true; }
	@Override
	public PositionSource getPositionSource() { return this.positionSource; }
	@Override
	public int getRange() { return 8; }
	@Override
	public boolean listen(World world, GameEvent event, Entity entity, BlockPos pos) {
		if (this.isRemoved()) return false;
		if (event == GameEvent.ENTITY_KILLED || event == ModGameEvent.ENTITY_DIE && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			LivingEntityAccessor lea = (LivingEntityAccessor)livingEntity;
			if (lea.ShouldDropXp()) {
				int i = lea.GetXpToDrop(null);
				if (lea.ShouldDropXp() && i > 0) {
					this.spreadManager.spread(new BlockPos(VectorUtil.withBias(Vec3d.ofCenter(pos), Direction.UP, 0.5)), i);
					this.triggerCriteria(livingEntity);
				}
				//livingEntity.disableExperienceDropping();
				if (world instanceof ServerWorld serverWorld) SculkCatalystBlock.bloom(serverWorld, this.pos, this.getCachedState(), world.getRandom());
			}
			return true;
		}
		return false;
	}
	private void triggerCriteria(LivingEntity deadEntity) {
		LivingEntity livingEntity = deadEntity.getAttacker();
		if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
			DamageSource damageSource = deadEntity.getRecentDamageSource() == null ? DamageSource.player(serverPlayerEntity) : deadEntity.getRecentDamageSource();
			ModCriteria.KILL_MOB_NEAR_SCULK_CATALYST.trigger(serverPlayerEntity, deadEntity, damageSource);
		}
	}
	public static void tick(World world, BlockPos pos, BlockState state, SculkCatalystBlockEntity blockEntity) {
		blockEntity.spreadManager.tick(world, pos, world.getRandom(), true);
	}
	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.spreadManager.readNbt(nbt);
	}
	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		this.spreadManager.writeNbt(nbt);
		super.writeNbt(nbt);
		return nbt;
	}
	public SculkSpreadManager getSpreadManager() { return this.spreadManager; }
}