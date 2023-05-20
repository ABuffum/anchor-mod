package fun.mousewich.mixins.client.sound;

import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuardianAttackSoundInstance.class)
public interface GuardianAttackSoundInstanceAccessor {
	@Accessor("guardian")
	GuardianEntity getGuardian();
}
