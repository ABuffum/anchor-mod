package fun.mousewich.effect;

import fun.mousewich.ModBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public interface StatusEffectFogModifier {
	StatusEffect getStatusEffect();
	void applyStartEndModifier(FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta);
	default boolean shouldApply(LivingEntity entity, float tickDelta) { return entity.hasStatusEffect(this.getStatusEffect()); }
	default float applyColorModifier(LivingEntity entity, StatusEffectInstance effect, float f, float tickDelta) {
		StatusEffectInstance statusEffectInstance = entity.getStatusEffect(this.getStatusEffect());
		if (statusEffectInstance != null) {
			if (statusEffectInstance.getDuration() < 20) f = 1.0F - (float) statusEffectInstance.getDuration() / 20.0F;
			else f = 0.0F;
		}
		return f;
	}

	@Environment(EnvType.CLIENT)
	class FogData {
		public final BackgroundRenderer.FogType fogType;
		public float fogStart;
		public float fogEnd;
		//public FogShape fogShape = FogShape.SPHERE;
		public FogData(BackgroundRenderer.FogType fogType) { this.fogType = fogType; }
	}

	@Environment(EnvType.CLIENT)
	enum FogType {
		FOG_SKY,
		FOG_TERRAIN;
		FogType() { }
	}

	@Environment(EnvType.CLIENT)
	class BlindnessFogModifier implements StatusEffectFogModifier {
		public BlindnessFogModifier() { }
		@Override
		public StatusEffect getStatusEffect() { return StatusEffects.BLINDNESS; }
		@Override
		public void applyStartEndModifier(FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
			float f = MathHelper.lerp(Math.min(1.0F, (float) effect.getDuration() / 20.0F), viewDistance, 5.0F);
			if (fogData.fogType == BackgroundRenderer.FogType.FOG_SKY) {
				fogData.fogStart = 0.0F;
				fogData.fogEnd = f * 0.8F;
			}
			else {
				fogData.fogStart = f * 0.25F;
				fogData.fogEnd = f;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	class DarknessFogModifier implements StatusEffectFogModifier {
		public DarknessFogModifier() { }
		@Override
		public StatusEffect getStatusEffect() { return ModStatusEffects.DARKNESS; }
		@Override
		public void applyStartEndModifier(FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
			if (effect.getEffectType() == ModStatusEffects.DARKNESS) {
				int duration = effect.getDuration() % 100;
				float lerp = duration < 50 ? duration / 50F : (1 - ((duration - 50)) / 50F);
				float f = MathHelper.lerp(lerp, viewDistance, 10.0F);
				fogData.fogStart = fogData.fogType == BackgroundRenderer.FogType.FOG_SKY ? 0.0F : f * 0.75F;
				fogData.fogEnd = f;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	class FlashbangedFogModifier implements StatusEffectFogModifier {
		public FlashbangedFogModifier() { }
		@Override
		public StatusEffect getStatusEffect() { return ModStatusEffects.FLASHBANGED; }
		@Override
		public void applyStartEndModifier(FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
			float f = MathHelper.lerp(Math.min(1.0F, (float) effect.getDuration() / 20.0F), viewDistance, 5.0F);
			if (fogData.fogType == BackgroundRenderer.FogType.FOG_SKY) {
				fogData.fogStart = 0.0F;
				fogData.fogEnd = f * 0.8F;
			}
			else {
				fogData.fogStart = f * 0.25F;
				fogData.fogEnd = f;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	abstract class GogglesFogModifier implements StatusEffectFogModifier {
		protected abstract StatusEffect getEffect();
		protected abstract Item getGoggles();
		public GogglesFogModifier() { }
		@Override
		public StatusEffect getStatusEffect() { return getEffect(); }
		@Override
		public boolean shouldApply(LivingEntity entity, float tickDelta) {
			return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(getGoggles())
					|| entity.hasStatusEffect(this.getStatusEffect());
		}
		@Override
		public void applyStartEndModifier(FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
			float f = MathHelper.lerp(Math.min(1.0F, (float) effect.getDuration() / 20.0F), viewDistance, 5.0F);
			if (fogData.fogType == BackgroundRenderer.FogType.FOG_SKY) {
				fogData.fogStart = 0.0F;
				fogData.fogEnd = f * 0.8F;
			}
			else {
				fogData.fogStart = f;
				fogData.fogEnd = f * 10.0F;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	class TintedGogglesFogModifier extends GogglesFogModifier {
		@Override protected StatusEffect getEffect() { return ModStatusEffects.TINTED_GOGGLES; }
		@Override protected Item getGoggles() { return ModBase.TINTED_GOGGLES; }
	}

	@Environment(EnvType.CLIENT)
	class RubyGogglesFogModifier extends GogglesFogModifier {
		@Override protected StatusEffect getEffect() { return ModStatusEffects.RUBY_GOGGLES; }
		@Override protected Item getGoggles() { return ModBase.RUBY_GOGGLES; }
	}

	@Environment(EnvType.CLIENT)
	class SapphireGogglesFogModifier extends GogglesFogModifier {
		@Override protected StatusEffect getEffect() { return ModStatusEffects.SAPPHIRE_GOGGLES; }
		@Override protected Item getGoggles() { return ModBase.SAPPHIRE_GOGGLES; }
	}
}
