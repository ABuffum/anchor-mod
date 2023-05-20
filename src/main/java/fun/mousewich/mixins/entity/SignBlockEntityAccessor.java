package fun.mousewich.mixins.entity;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignBlockEntity.class)
public interface SignBlockEntityAccessor {
	@Invoker("getTexts")
	Text[] GetTexts(boolean filtered);
}
