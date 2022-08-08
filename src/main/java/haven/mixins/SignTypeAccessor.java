package haven.mixins;

import haven.HavenMod;
import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Accessor("VALUES")
	public static Set<SignType> getValues() {
		throw new AssertionError();
	}
	@Accessor("VALUES")
	public static void setValues(Set<SignType> values) {
		throw new AssertionError();
	}
}
