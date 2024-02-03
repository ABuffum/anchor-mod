package fun.wich.mixins.entity.ai;

import net.minecraft.entity.ai.goal.FormCaravanGoal;
import org.spongepowered.asm.mixin.Mixin;

//TODO: Compatibility bridge with Lithium mod (almost exact same implementation and redirect)
@Mixin(FormCaravanGoal.class)
public class FormCaravanGoalMixin {
	//@Redirect(method="canStart", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"))
	//private List<Entity> GetInclusiveLlamas(World instance, @Nullable Entity except, Box box, Predicate<? super Entity> predicate) {
	//	return instance.getOtherEntities(except, box, entity -> predicate.test(entity) || entity instanceof LlamaEntity);
	//}
}
