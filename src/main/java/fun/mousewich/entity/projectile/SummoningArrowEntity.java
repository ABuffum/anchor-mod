package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import fun.mousewich.container.ArrowContainer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SummoningArrowEntity extends ModArrowEntity {
	public static final Identifier TEXTURE = ModBase.ID("textures/entity/projectiles/summoning_arrow.png");
	private final EntityType<? extends MobEntity> summon;

	public EntityType<? extends MobEntity> getSummon() { return summon; }

	public SummoningArrowEntity(EntityType<? extends MobEntity> summon, EntityType<? extends ModArrowEntity> entityType, World world) {
		super(entityType, world);
		this.summon = summon;
	}
	public SummoningArrowEntity(EntityType<? extends MobEntity> summon, EntityType<? extends ModArrowEntity> entityType, World world, LivingEntity owner) {
		super(entityType, world, owner);
		this.summon = summon;
	}
	public SummoningArrowEntity(EntityType<? extends MobEntity> summon, EntityType<? extends ModArrowEntity> entityType, World world, double x, double y, double z) {
		super(entityType, world, x, y, z);
		this.summon = summon;
	}

	@Override
	public ItemStack asItemStack() {
		EntityType<? extends MobEntity> entityType = this.getSummon();
		if (entityType == null) return new ItemStack(Items.ARROW);
		return new ItemStack(ArrowContainer.SUMMONING_ARROWS.getOrDefault(this.getSummon(), Items.ARROW));
	}
	@Override
	public ItemStack getRecycledStack() { return new ItemStack(Items.ARROW); }
	@Override
	public Identifier getTexture() { return TEXTURE; }

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		EntityType<? extends MobEntity> entityType = this.getSummon();
		if (entityType != null) {
			if (this.world instanceof ServerWorld serverWorld) {
				BlockPos pos = entityHitResult.getEntity().getBlockPos();
				PlayerEntity player = getOwner() instanceof PlayerEntity p ? p : null;
				MobEntity entity = entityType.create(serverWorld, null, null, player, pos, SpawnReason.TRIGGERED, false, false);
				if (entity != null) {
					entity.initialize(serverWorld, serverWorld.getLocalDifficulty(pos), SpawnReason.TRIGGERED, null, null);
					Vec3d entityPos = entityHitResult.getPos();
					entity.setPos(entityPos.x, entityPos.y, entityPos.z);
					world.spawnEntity(entity);
					world.emitGameEvent(this.getOwner(), GameEvent.ENTITY_PLACE, pos);
					world.emitGameEvent(this.getOwner(), GameEvent.BLOCK_CHANGE, pos);
					this.discard();
				}
			}
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		EntityType<? extends MobEntity> entityType = this.getSummon();
		if (entityType != null) {
			if (this.world instanceof ServerWorld serverWorld) {
				BlockPos pos = blockHitResult.getBlockPos();
				PlayerEntity player = getOwner() instanceof PlayerEntity p ? p : null;
				MobEntity entity = entityType.create(serverWorld, null, null, player, pos, SpawnReason.TRIGGERED, false, false);
				if (entity != null) {
					entity.initialize(serverWorld, serverWorld.getLocalDifficulty(pos), SpawnReason.TRIGGERED, null, null);
					entity.setPosition(blockHitResult.getPos());
					world.spawnEntity(entity);
					world.emitGameEvent(this.getOwner(), GameEvent.ENTITY_PLACE, pos);
					world.emitGameEvent(this.getOwner(), GameEvent.BLOCK_CHANGE, pos);
					this.discard();
				}
			}
		}
	}
}
