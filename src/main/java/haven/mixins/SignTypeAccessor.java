package haven.mixins;

import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(SignType.class)
public interface SignTypeAccessor {
	@Invoker("<init>")
	static SignType newSignType(String name) { throw new AssertionError(); }
	@Invoker("register")
	static SignType registerNew(SignType type) { throw new AssertionError(); }
}
