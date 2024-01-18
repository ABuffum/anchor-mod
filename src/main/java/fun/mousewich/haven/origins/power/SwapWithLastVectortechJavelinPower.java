package fun.mousewich.haven.origins.power;

import fun.mousewich.ModId;
import fun.mousewich.entity.LastJavelinStoring;
import fun.mousewich.entity.projectile.JavelinEntity;
import fun.mousewich.haven.entity.VectortechJavelinEntity;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class SwapWithLastVectortechJavelinPower extends CooldownPower implements Active {
	public SwapWithLastVectortechJavelinPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender) {
		super(type, entity, cooldownDuration, hudRender);
	}

	public static PowerFactory<SwapWithLastVectortechJavelinPower> createFactory() {
		return new PowerFactory<SwapWithLastVectortechJavelinPower>(ModId.ID("swap_with_last_vectortech_javelin"),
				new SerializableData()
						.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Key())
						.add("cooldown", SerializableDataTypes.INT, 1)
						.add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER),
				data -> (type, player) -> {
					SwapWithLastVectortechJavelinPower power = new SwapWithLastVectortechJavelinPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"));
					power.setKey((Active.Key)data.get("key"));
					return power;
				}
		).allowCondition();
	}

	@Override
	public void onUse() {
		if (canUse()) {
			SwapWithLastJavelin(entity);
			use();
		}
	}
	private Key key;
	@Override
	public Key getKey() { return key; }
	@Override
	public void setKey(Key key) { this.key = key; }

	public static void SwapWithLastJavelin(LivingEntity entity) {
		if (!(entity instanceof LastJavelinStoring lastJavelinStore)) return;
		JavelinEntity javelin = lastJavelinStore.getLastJavelin();
		if (javelin == null || !javelin.isAlive()) return;
		if (!(javelin instanceof VectortechJavelinEntity)) return;
		if (entity.world != javelin.world) return;
		Vec3d javelinPos = javelin.getPos();
		if (entity instanceof ServerPlayerEntity serverPlayer) {
			serverPlayer.teleport(serverPlayer.getServerWorld(), javelinPos.getX(), javelinPos.getY(), javelinPos.getZ(), entity.prevYaw, entity.prevPitch);
		}
		else entity.teleport(javelinPos.getX(), javelinPos.getY(), javelinPos.getZ());
		//Show teleportation particles & play a sound
		entity.world.sendEntityStatus(entity, (byte)46);
		SoundEvent soundEvent = entity instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
		entity.world.playSound(null, javelinPos.getX(), javelinPos.getY(), javelinPos.getZ(), soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
		entity.playSound(soundEvent, 1.0F, 1.0F);
		javelin.discard();
	}
}
