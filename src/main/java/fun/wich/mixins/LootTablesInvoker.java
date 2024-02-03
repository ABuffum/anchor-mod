package fun.wich.mixins;

import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LootTables.class)
public interface LootTablesInvoker {
	@Invoker("registerLootTable")
	static Identifier InvokeRegisterLootTable(Identifier id) { return null; }
}
