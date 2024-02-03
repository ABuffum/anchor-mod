package fun.wich.mixins.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Invoker("onStatusEffectRemoved")
	void OnStatusEffectRemoved(StatusEffectInstance effect);
	@Invoker("shouldDropXp")
	boolean ShouldDropXp();
	@Invoker("getXpToDrop")
	int GetXpToDrop(PlayerEntity player);
	@Accessor("jumping")
	boolean getJumping();
}
