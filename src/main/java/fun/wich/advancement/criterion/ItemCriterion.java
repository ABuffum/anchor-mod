package fun.wich.advancement.criterion;

import com.google.gson.JsonObject;
import fun.wich.advancement.ModCriteria;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ItemCriterion extends AbstractCriterion<ItemCriterion.Conditions> {
	final Identifier id;
	public ItemCriterion(Identifier id) { this.id = id; }
	@Override
	public Identifier getId() { return this.id; }
	@Override
	public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		LocationPredicate locationPredicate = LocationPredicate.fromJson(jsonObject.get("location"));
		ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
		return new Conditions(this.id, extended, locationPredicate, itemPredicate);
	}
	public void trigger(ServerPlayerEntity player, BlockPos pos, ItemStack stack) {
		BlockState blockState = player.world.getBlockState(pos);
		this.trigger(player, conditions -> conditions.test(blockState, player.getServerWorld(), pos, stack));
	}
	public static class Conditions extends AbstractCriterionConditions {
		private final LocationPredicate location;
		private final ItemPredicate item;
		public Conditions(Identifier id, EntityPredicate.Extended entity, LocationPredicate location, ItemPredicate item) {
			super(id, entity);
			this.location = location;
			this.item = item;
		}
		public static Conditions create(LocationPredicate.Builder location, ItemPredicate.Builder item) {
			return new Conditions(Criteria.ITEM_USED_ON_BLOCK.getId(), EntityPredicate.Extended.EMPTY, location.build(), item.build());
		}
		public static Conditions createAllayDropItemOnBlock(LocationPredicate.Builder location, ItemPredicate.Builder item) {
			return new Conditions(ModCriteria.ALLAY_DROP_ITEM_ON_BLOCK.id, EntityPredicate.Extended.EMPTY, location.build(), item.build());
		}
		public boolean test(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
			if (!this.location.test(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) return false;
			return this.item.test(stack);
		}
		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("location", this.location.toJson());
			jsonObject.add("item", this.item.toJson());
			return jsonObject;
		}
	}
}
