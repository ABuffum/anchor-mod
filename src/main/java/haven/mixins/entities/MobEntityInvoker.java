package haven.mixins.entities;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobEntity.class)
public interface MobEntityInvoker {
	@Invoker("interactWithItem")
	public ActionResult InvokeInteractWithItem(PlayerEntity player, Hand hand);
	@Invoker("interactMob")
	public ActionResult InvokeInteractMob(PlayerEntity player, Hand hand);
}
