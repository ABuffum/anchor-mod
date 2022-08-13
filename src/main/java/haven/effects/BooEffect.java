package haven.effects;

import haven.HavenMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public class BooEffect extends StatusEffect {
	public BooEffect() {
		super(StatusEffectType.NEUTRAL,0xBE331F);
	}

	@Override
	public void onApplied(LivingEntity livingEntity, AttributeContainer abstractEntityAttributeContainer, int i) {
		if (livingEntity instanceof PlayerEntity) {
			MinecraftClient mc = MinecraftClient.getInstance();
			//Boo the baby
			if (livingEntity.hasStatusEffect(HavenMod.BOO_EFFECT)) {
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, Text.of("Boo!!!!"), mc.player.getUuid());
			}
			else {
				mc.inGameHud.addChatMessage(MessageType.SYSTEM, Text.of("Boo!"), mc.player.getUuid());
			}
		}
	}
}
