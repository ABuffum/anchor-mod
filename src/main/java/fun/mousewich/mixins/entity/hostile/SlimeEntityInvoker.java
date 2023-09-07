package fun.mousewich.mixins.entity.hostile;

import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlimeEntity.class)
public interface SlimeEntityInvoker {
	@Invoker("setSize")
	void SetSize(int size, boolean heal);
}
