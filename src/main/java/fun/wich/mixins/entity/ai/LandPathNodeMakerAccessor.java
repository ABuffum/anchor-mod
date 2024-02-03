package fun.wich.mixins.entity.ai;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LandPathNodeMaker.class)
public interface LandPathNodeMakerAccessor {
	@Invoker("isBlocked")
	boolean IsBlocked(PathNode node);
	@Invoker("checkBoxCollision")
	boolean CheckBoxCollision(Box box);
}
