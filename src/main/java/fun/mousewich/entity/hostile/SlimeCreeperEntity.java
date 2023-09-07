package fun.mousewich.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.effect.ModStatusEffects;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.mixins.entity.hostile.CreeperEntityAccessor;
import io.github.apace100.apoli.Apoli;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;

public class SlimeCreeperEntity extends CreeperEntity implements EntityWithBloodType {
	public SlimeCreeperEntity(EntityType<SlimeCreeperEntity> type, World world) { super(type, world); }

	@Override
	public void tick() {
		if (this.isAlive()) {
			int i;
			CreeperEntityAccessor cea = (CreeperEntityAccessor)this;
			cea.setLastFuseTime(cea.getCurrentFuseTime());
			if (this.isIgnited()) this.setFuseSpeed(1);
			if ((i = this.getFuseSpeed()) > 0 && cea.getCurrentFuseTime() == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f);
				this.emitGameEvent(GameEvent.PRIME_FUSE);
			}
			cea.setCurrentFuseTime(cea.getCurrentFuseTime() + i);
			if (cea.getCurrentFuseTime() < 0) cea.setCurrentFuseTime(0);
			if (cea.getCurrentFuseTime() >= cea.getFuseTime()) {
				cea.setCurrentFuseTime(cea.getFuseTime());
				this.explode();
			}
		}
		super.tick();
	}

	private void explode() {
		boolean doMobGriefing = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
		if (!this.world.isClient) {
			CreeperEntityAccessor cea = (CreeperEntityAccessor)this;
			Explosion.DestructionType destructionType = /*doMobGriefing ? Explosion.DestructionType.DESTROY :*/ Explosion.DestructionType.NONE;
			this.dead = true;
			this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), cea.getExplosionRadius() * (this.shouldRenderOverlay() ? 2 : 1), destructionType);
			this.discard();
			spawnEffectsCloud();
		}
		MinecraftServer server = this.world.getServer();
		if (doMobGriefing && server != null) {
			ServerCommandSource source = new ServerCommandSource(
					CommandOutput.DUMMY,
					this.getPos(),
					this.getRotationClient(),
					this.world instanceof ServerWorld ? (ServerWorld)this.world : null,
					Apoli.config.executeCommand.permissionLevel,
					this.getName().getString(),
					this.getDisplayName(),
					this.world.getServer(),
					this);
			server.getCommandManager().execute(source, "function haven:slime_creeper_explodes");
		}
	}

	private void spawnEffectsCloud() {
		ArrayList<StatusEffectInstance> collection = new ArrayList<>(this.getStatusEffects());
		collection.add(new StatusEffectInstance(ModStatusEffects.STICKY, 600));
		collection.add(new StatusEffectInstance(StatusEffects.POISON, 200));
		//Build cloud
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setRadius(2.5f);
		areaEffectCloudEntity.setRadiusOnUse(-0.5f);
		areaEffectCloudEntity.setWaitTime(10);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
		for (StatusEffectInstance statusEffectInstance : collection) {
			areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
		}
		this.world.spawnEntity(areaEffectCloudEntity);
	}

	@Override
	public BloodType GetDefaultBloodType() { return ModBase.SLIME_BLOOD_TYPE; }
}
