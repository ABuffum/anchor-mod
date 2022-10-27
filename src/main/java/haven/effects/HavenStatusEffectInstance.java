package haven.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HavenStatusEffectInstance extends StatusEffectInstance {
	private final Optional<FactorCalculationData> factorCalculationData;

	public HavenStatusEffectInstance(StatusEffect type) {
		super(type);
		this.factorCalculationData = Optional.empty();
	}

	public HavenStatusEffectInstance(StatusEffect type, int duration) {
		super(type, duration);
		this.factorCalculationData = Optional.empty();
	}

	public HavenStatusEffectInstance(StatusEffect type, int duration, int amplifier) {
		super(type, duration, amplifier);
		this.factorCalculationData = Optional.empty();
	}

	public HavenStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean visible) {
		super(type, duration, amplifier, ambient, visible);
		this.factorCalculationData = Optional.empty();
	}

	public HavenStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
		super(type, duration, amplifier, ambient, showParticles, showIcon);
		this.factorCalculationData = Optional.empty();
	}

	public HavenStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @Nullable HavenStatusEffectInstance hiddenEffect, Optional<HavenStatusEffectInstance.FactorCalculationData> factorCalculationData) {
		super(type, duration, amplifier, ambient, showParticles, showIcon, hiddenEffect);
		this.factorCalculationData = factorCalculationData;
	}

	public HavenStatusEffectInstance(HavenStatusEffectInstance instance) {
		super(instance);
		this.factorCalculationData = instance.getFactorCalculationData();
	}
	public HavenStatusEffectInstance(StatusEffectInstance instance) {
		super(instance);
		StatusEffect type = instance.getEffectType();
		if (type instanceof DarknessEffect) this.factorCalculationData = Optional.of(new FactorCalculationData(22));
		else if (type instanceof FlashbangedEffect) this.factorCalculationData = Optional.of(new FactorCalculationData(22));
		else this.factorCalculationData = Optional.of(new FactorCalculationData(22));
	}

	public Optional<HavenStatusEffectInstance.FactorCalculationData> getFactorCalculationData() {
		return this.factorCalculationData;
	}

	@Override
	public boolean update(LivingEntity entity, Runnable overwriteCallback) {
		this.factorCalculationData.ifPresent(factorCalculationData -> factorCalculationData.update(this));
		return super.update(entity, overwriteCallback);
	}

	public static class FactorCalculationData {
		private final int paddingDuration;
		private float factorStart;
		private float factorTarget;
		private float factorCurrent;
		int effectChangedTimestamp;
		private float factorPreviousFrame;
		private boolean hadEffectLastTick;

		public FactorCalculationData(int paddingDuration, float factorStart, float factorTarget, float factorCurrent, int effectChangedTimestamp, float factorPreviousFrame, boolean hadEffectLastTick) {
			this.paddingDuration = paddingDuration;
			this.factorStart = factorStart;
			this.factorTarget = factorTarget;
			this.factorCurrent = factorCurrent;
			this.effectChangedTimestamp = effectChangedTimestamp;
			this.factorPreviousFrame = factorPreviousFrame;
			this.hadEffectLastTick = hadEffectLastTick;
		}

		public FactorCalculationData(int paddingDuration) {
			this(paddingDuration, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
		}

		public void update(StatusEffectInstance instance) {
			this.factorPreviousFrame = this.factorCurrent;
			boolean bl = instance.getDuration() > this.paddingDuration;
			if (this.hadEffectLastTick != bl) {
				this.hadEffectLastTick = bl;
				this.effectChangedTimestamp = instance.getDuration();
				this.factorStart = this.factorCurrent;
				this.factorTarget = bl ? 1.0F : 0.0F;
			}

			float tickDelta = MathHelper.clamp(((float) this.effectChangedTimestamp - (float) instance.getDuration()) / (float) this.paddingDuration, 0.0F, 1.0F);
			this.factorCurrent = MathHelper.lerp(tickDelta, this.factorStart, this.factorTarget);
		}

		public float lerp(LivingEntity entity, float factor) {
			if (entity.isRemoved()) {
				this.factorPreviousFrame = this.factorCurrent;
			}
			return MathHelper.lerp(factor, this.factorPreviousFrame, this.factorCurrent);
		}
	}
}