package fun.mousewich.entity.vehicle;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModBoatType {
	private final String name;
	private final Block baseBlock;
	private final GetBoatItem item;
	private final GetBoatItem chest;
	private final int ordinal;
	private static int ORDINAL_CTR = 0;

	public final boolean floatsOnLava;

	public ModBoatType(Block baseBlock, String name, GetBoatItem item, GetBoatItem chest, boolean floatsOnLava) {
		this.name = name;
		this.baseBlock = baseBlock;
		this.item = item;
		this.chest = chest;
		this.floatsOnLava = floatsOnLava;
		ordinal = ORDINAL_CTR++;
	}
	public interface GetBoatItem { public Item op(); }

	public int ordinal() { return ordinal; }
	public Item GetItem() { return item.op(); }
	public Item GetChestBoatItem() { return chest.op(); }

	private static final ArrayList<ModBoatType> BOAT_TYPES = new ArrayList<>();

	public static ModBoatType Register(ModBoatType type) {
		BOAT_TYPES.add(type);
		return type;
	}
	public String getName() { return this.name; }
	public Block getBaseBlock() { return this.baseBlock; }

	public String toString() { return this.name; }

	public static ModBoatType getType(int type) {
		if (type < 0 || type >= BOAT_TYPES.size()) type = 0;
		return BOAT_TYPES.get(type);
	}

	public static ModBoatType getType(String name) {
		for(ModBoatType type : BOAT_TYPES) {
			if (type.getName().equals(name)) return type;
		}
		return BOAT_TYPES.get(0);
	}

	public static List<ModBoatType> getTypes() { return new ArrayList<>(BOAT_TYPES); }
}
