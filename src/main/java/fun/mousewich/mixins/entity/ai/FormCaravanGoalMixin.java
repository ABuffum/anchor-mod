package fun.mousewich.mixins.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.FormCaravanGoal;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

//TODO: Compatibility bridge with Lithium mod (almost exact same implementation and redirect)
@Mixin(FormCaravanGoal.class)
public class FormCaravanGoalMixin {
	//@Redirect(method="canStart", at=@At(value="INVOKE", target="Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"))
	//private List<Entity> GetInclusiveLlamas(World instance, @Nullable Entity except, Box box, Predicate<? super Entity> predicate) {
	//	return instance.getOtherEntities(except, box, entity -> predicate.test(entity) || entity instanceof LlamaEntity);
	//}
}
