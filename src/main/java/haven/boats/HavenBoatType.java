package haven.boats;

import haven.materials.WoodMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class HavenBoatType {
	private final String name;
	private final Block baseBlock;
	private final GetBoatItem item;
	private final int ordinal;
	private static int ORDINAL_CTR = 0;

	public HavenBoatType(Block baseBlock, String name, GetBoatItem item) {
		this.name = name;
		this.baseBlock = baseBlock;
		this.item = item;
		ordinal = ORDINAL_CTR++;
	}
	public interface GetBoatItem {
		public Item op();
	}

	public int ordinal() { return ordinal; }

	public Item GetItem() {
		return item.op();
	}

	private static final ArrayList<HavenBoatType> BOAT_TYPES = new ArrayList<>();

	public static HavenBoatType Register(HavenBoatType type) {
		BOAT_TYPES.add(type);
		return type;
	}

	public String getName() {
		return this.name;
	}

	public Block getBaseBlock() {
		return this.baseBlock;
	}

	public String toString() {
		return this.name;
	}

	public static HavenBoatType getType(int type) {
		if (type < 0 || type >= BOAT_TYPES.size()) {
			type = 0;
		}
		return BOAT_TYPES.get(type);
	}

	public static HavenBoatType getType(String name) {
		for(HavenBoatType type : BOAT_TYPES) {
			if (type.getName().equals(name)) {
				return type;
			}
		}
		return BOAT_TYPES.get(0);
	}

	public static List<HavenBoatType> getTypes() {
		List<HavenBoatType> output = new ArrayList<>();
		output.addAll(BOAT_TYPES);
		return output;
	}
}
