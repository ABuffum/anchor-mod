package haven.effects;

import haven.HavenMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

public class KilljoyEffect extends StatusEffect {
	public KilljoyEffect() {
		super(StatusEffectType.NEUTRAL,0x1F8B33);
	}
}
