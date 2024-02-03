package fun.wich.origins.power;

import fun.wich.ModId;
import fun.wich.client.render.gui.Generic1x1ContainerScreenHandler;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Predicate;

public class SingleSlotInventoryPower extends Power implements Active, IInventoryPower {
	private final DefaultedList<ItemStack> inventory;
	private final TranslatableText containerName;
	private final ScreenHandlerFactory factory;
	private final boolean shouldDropOnDeath;
	private final Predicate<ItemStack> dropOnDeathFilter;

	public SingleSlotInventoryPower(PowerType<?> type, LivingEntity entity, String containerName, boolean shouldDropOnDeath, Predicate<ItemStack> dropOnDeathFilter) {
		super(type, entity);
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
		this.containerName = new TranslatableText(containerName);
		this.factory = (i, playerInventory, playerEntity) -> new Generic1x1ContainerScreenHandler(i, playerInventory, this);
		this.shouldDropOnDeath = shouldDropOnDeath;
		this.dropOnDeathFilter = dropOnDeathFilter;
	}

	@Override
	public void onUse() {
		if(!isActive()) return;
		if(!entity.world.isClient && entity instanceof PlayerEntity player) {
			player.openHandledScreen(new SimpleNamedScreenHandlerFactory(factory, containerName));
		}
	}
	@Override
	public NbtCompound toTag() {
		NbtCompound tag = new NbtCompound();
		Inventories.writeNbt(tag, inventory);
		return tag;
	}
	@Override public void fromTag(NbtElement tag) { Inventories.readNbt((NbtCompound)tag, inventory); }
	@Override public int size() { return 1; }
	@Override public boolean isEmpty() { return inventory.isEmpty(); }
	@Override public ItemStack getStack(int slot) { return inventory.get(slot); }
	@Override public ItemStack removeStack(int slot, int amount) { return inventory.get(slot).split(amount); }
	@Override
	public ItemStack removeStack(int slot) {
		ItemStack stack = inventory.get(slot);
		setStack(slot, ItemStack.EMPTY);
		return stack;
	}
	@Override public void setStack(int slot, ItemStack stack) { inventory.set(slot, stack); }
	@Override public void markDirty() { }
	@Override public boolean canPlayerUse(PlayerEntity player) { return player == this.entity; }
	@Override public void clear() { setStack(0, ItemStack.EMPTY); }
	public boolean shouldDropOnDeath() { return shouldDropOnDeath; }
	@Override
	public boolean shouldDropOnDeath(ItemStack stack) { return shouldDropOnDeath && dropOnDeathFilter.test(stack); }

	private Key key;
	@Override public Key getKey() { return key; }
	@Override public void setKey(Key key) { this.key = key; }

	public static PowerFactory<SingleSlotInventoryPower> createFactory() {
		return new PowerFactory<SingleSlotInventoryPower>(ModId.ID("single_slot_inventory"),
				new SerializableData()
						.add("title", SerializableDataTypes.STRING, "container.inventory")
						.add("drop_on_death", SerializableDataTypes.BOOLEAN, false)
						.add("drop_on_death_filter", ApoliDataTypes.ITEM_CONDITION, null)
						.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
				data ->
						(type, player) -> {
							SingleSlotInventoryPower power = new SingleSlotInventoryPower(type, player, data.getString("title"),
									data.getBoolean("drop_on_death"),
									data.isPresent("drop_on_death_filter") ? (ConditionFactory<ItemStack>.Instance)data.get("drop_on_death_filter") : itemStack -> true);
							power.setKey((Active.Key)data.get("key"));
							return power;
						})
				.allowCondition();
	}
}