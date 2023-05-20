package fun.mousewich.block.archaeology;

import fun.mousewich.ModBase;
import fun.mousewich.block.dust.BrushableBlockEntity;
import fun.mousewich.entity.ModNbtKeys;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SuspiciousSandBlockEntity extends BrushableBlockEntity {
	private Identifier lootTable;
	private long lootTableSeed;

	public SuspiciousSandBlockEntity(BlockPos pos, BlockState state) { super(ModBase.SUSPICIOUS_BLOCK_ENTITY, pos, state); }

	@Override
	public void generateItem(PlayerEntity player) {
		if (this.lootTable == null || this.world == null || this.world.isClient() || this.world.getServer() == null) return;
		LootTable lootTable = this.world.getServer().getLootManager().getTable(this.lootTable);
		if (player instanceof ServerPlayerEntity serverPlayerEntity) Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger(serverPlayerEntity, this.lootTable);
		LootContext.Builder builder = new LootContext.Builder((ServerWorld)this.world).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos)).random(this.lootTableSeed).luck(player.getLuck()).parameter(LootContextParameters.THIS_ENTITY, player);
		List<ItemStack> objectArrayList = lootTable.generateLoot(builder.build(LootContextTypes.CHEST));
		this.item = switch (objectArrayList.size()) {
			case 0 -> ItemStack.EMPTY;
			case 1 -> objectArrayList.get(0);
			default -> {
				ModBase.LOGGER.warn("Expected max 1 loot from loot table " + this.lootTable + " got " + objectArrayList.size());
				yield objectArrayList.get(0);
			}
		};
		this.lootTable = null;
		this.markDirty();
	}
	private boolean readLootTableFromNbt(NbtCompound nbt) {
		if (nbt.contains(ModNbtKeys.LOOT_TABLE, NbtElement.STRING_TYPE)) {
			this.lootTable = new Identifier(nbt.getString(ModNbtKeys.LOOT_TABLE));
			this.lootTableSeed = nbt.getLong(ModNbtKeys.LOOT_TABLE_SEED);
			return true;
		}
		return false;
	}
	private boolean writeLootTableToNbt(NbtCompound nbt) {
		if (this.lootTable == null) return false;
		nbt.putString(ModNbtKeys.LOOT_TABLE, this.lootTable.toString());
		if (this.lootTableSeed != 0L) nbt.putLong(ModNbtKeys.LOOT_TABLE_SEED, this.lootTableSeed);
		return true;
	}
	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (!this.readLootTableFromNbt(nbt) && nbt.contains(ModNbtKeys.ITEM)) this.item = ItemStack.fromNbt(nbt.getCompound(ModNbtKeys.ITEM));
	}
	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (!this.writeLootTableToNbt(nbt)) nbt.put(ModNbtKeys.ITEM, this.item.writeNbt(new NbtCompound()));
		return nbt;
	}
	public void setLootTable(Identifier lootTable, long seed) {
		this.lootTable = lootTable;
		this.lootTableSeed = seed;
	}
}
