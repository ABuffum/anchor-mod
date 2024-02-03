package fun.wich.origins.power;

import fun.wich.ModId;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonLightningPower extends CooldownPower implements Active {
	public SummonLightningPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender) {
		super(type, entity, cooldownDuration, hudRender);
	}

	public static PowerFactory<SummonLightningPower> createFactory() {
		return new PowerFactory<SummonLightningPower>(ModId.ID("summon_lightning"),
			new SerializableData()
					.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Key())
					.add("cooldown", SerializableDataTypes.INT, 1)
					.add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER),
				data -> (type, player) -> {
					SummonLightningPower power = new SummonLightningPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"));
					power.setKey((Active.Key)data.get("key"));
					return power;
				}
			).allowCondition();
	}

	@Override
	public void onUse() {
		if (canUse()) {
			World world = entity.getEntityWorld();
			HitResult result = entity.raycast(80.0f, 0.0f, false);
			if (result.getType() != HitResult.Type.MISS) {
				BlockPos pos = new BlockPos(result.getPos());
				LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
				if (lightningEntity != null) {
					lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos.up()));
					lightningEntity.setChanneler(this.entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null);
					world.spawnEntity(lightningEntity);
				}
			}
			use();
		}
	}
	private Key key;
	@Override
	public Key getKey() { return key; }
	@Override
	public void setKey(Key key) { this.key = key; }
}
