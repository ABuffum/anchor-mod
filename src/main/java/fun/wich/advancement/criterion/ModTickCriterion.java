package fun.wich.advancement.criterion;

import com.google.gson.JsonObject;
import fun.wich.advancement.ModCriteria;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModTickCriterion extends AbstractCriterion<ModTickCriterion.Conditions> {
	final Identifier id;
	public ModTickCriterion(Identifier id) { this.id = id; }
	@Override
	public Identifier getId() { return this.id; }

	@Override
	public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		return new Conditions(this.id, extended);
	}

	public void trigger(ServerPlayerEntity player) { this.trigger(player, conditions -> true); }

	public static class Conditions extends AbstractCriterionConditions {
		public Conditions(Identifier identifier, EntityPredicate.Extended extended) { super(identifier, extended); }
		public static Conditions createAvoidVibration() {
			return new Conditions(ModCriteria.AVOID_VIBRATION.getId(), EntityPredicate.Extended.EMPTY);
		}
	}
}