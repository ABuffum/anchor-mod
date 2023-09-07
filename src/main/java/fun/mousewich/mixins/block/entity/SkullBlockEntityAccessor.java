package fun.mousewich.mixins.block.entity;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.Executor;

@Mixin(SkullBlockEntity.class)
public interface SkullBlockEntityAccessor {
	@Accessor("userCache")
	static UserCache getUserCache() { return null; }
	@Accessor("sessionService")
	static MinecraftSessionService getSessionService() { return null; }
	@Accessor("executor")
	static Executor getExecutor() { return null; }
}
