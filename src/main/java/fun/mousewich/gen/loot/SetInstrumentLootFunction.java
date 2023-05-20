package fun.mousewich.gen.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import fun.mousewich.ModBase;
import fun.mousewich.item.horn.goat.GoatHornItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

public class SetInstrumentLootFunction extends ConditionalLootFunction {
	SetInstrumentLootFunction(LootCondition[] conditions) { super(conditions); }
	@Override
	public LootFunctionType getType() { return ModBase.SET_INSTRUMENT_LOOT_FUNCTION; }
	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		GoatHornItem.setRandomInstrumentFromRegular(stack, context.getRandom());
		return stack;
	}
	public static Builder<?> builder() { return SetInstrumentLootFunction.builder(SetInstrumentLootFunction::new); }
	public static class Serializer extends ConditionalLootFunction.Serializer<SetInstrumentLootFunction> {
		@Override
		public void toJson(JsonObject jsonObject, SetInstrumentLootFunction setInstrumentLootFunction, JsonSerializationContext jsonSerializationContext) {
			super.toJson(jsonObject, setInstrumentLootFunction, jsonSerializationContext);
		}
		@Override
		public SetInstrumentLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditions) {
			return new SetInstrumentLootFunction(lootConditions);
		}
	}
}
