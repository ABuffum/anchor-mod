package haven.mixins.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Invoker("onStatusEffectRemoved")
	public void OnStatusEffectRemoved(StatusEffectInstance effect);

	@Invoker("shouldDropXp")
	public boolean ShouldDropXp();

	@Invoker("getXpToDrop")
	public int GetXpToDrop(PlayerEntity player);

	@Accessor("dead")
	public boolean GetDead();

	@Accessor("roll")
	public int GetRoll();
}
