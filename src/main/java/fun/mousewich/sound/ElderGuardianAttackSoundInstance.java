package fun.mousewich.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.sound.SoundCategory;

@Environment(value=EnvType.CLIENT)
public class ElderGuardianAttackSoundInstance extends MovingSoundInstance {
	private final ElderGuardianEntity guardian;
	public ElderGuardianEntity getGuardian() { return guardian; }

	public ElderGuardianAttackSoundInstance(ElderGuardianEntity guardian) {
		super(ModSoundEvents.ENTITY_ELDER_GUARDIAN_ATTACK, SoundCategory.HOSTILE);
		this.guardian = guardian;
		this.attenuationType = AttenuationType.NONE;
		this.repeat = true;
		this.repeatDelay = 0;
	}

	@Override
	public boolean canPlay() { return !this.guardian.isSilent(); }

	@Override
	public void tick() {
		if (this.guardian.isRemoved() || this.guardian.getTarget() != null) {
			this.setDone();
			return;
		}
		this.x = (float)this.guardian.getX();
		this.y = (float)this.guardian.getY();
		this.z = (float)this.guardian.getZ();
		float f = this.guardian.getBeamProgress(0.0f);
		this.volume = 0.0f + 1.0f * f * f;
		this.pitch = 0.7f + 0.5f * f;
	}
}

