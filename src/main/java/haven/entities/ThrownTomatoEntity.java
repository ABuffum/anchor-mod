package haven.entities;

import haven.HavenMod;
import haven.HavenModClient;
import haven.rendering.EntitySpawnPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownTomatoEntity extends ThrownItemEntity {
	public ThrownTomatoEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public ThrownTomatoEntity(World world, LivingEntity owner) {
		super(HavenMod.THROWABLE_TOMATO_ENTITY, owner, world); // null will be changed later
	}

	public ThrownTomatoEntity(World world, double x, double y, double z) {
		super(HavenMod.THROWABLE_TOMATO_ENTITY, x, y, z, world); // null will be changed later
	}

	@Override
	protected Item getDefaultItem() {
		return HavenMod.THROWABLE_TOMATO_ITEM;
	}

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? HavenMod.TOMATO_PARTICLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0F); // deals damage

		if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
			livingEntity.addStatusEffect((new StatusEffectInstance(HavenMod.BOO_EFFECT, 20 * 3, 0))); // applies a status effect
			if (livingEntity.getEntityWorld().isClient) {
				if (livingEntity instanceof PlayerEntity player) {
					if (player.hasStatusEffect(HavenMod.BOO_EFFECT)) player.sendMessage(Text.of("Boo!"), true);
					else player.sendMessage(Text.of("Boo!!!"), true);
				}
			}
			//TODO: Tomato Squelch sound on hit
		}
	}

	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
			this.world.sendEntityStatus(this, (byte)3); // particle?
			this.kill(); // kills the projectile
		}
	}

	@Override
	public Packet createSpawnPacket() {
		return EntitySpawnPacket.create(this, HavenModClient.PacketID);
	}
}
