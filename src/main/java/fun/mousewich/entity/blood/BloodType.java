package fun.mousewich.entity.blood;

import fun.mousewich.ModBase;
import fun.mousewich.ModId;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BloodType {
	private final String name;
	public String getName() { return name; }
	private final Identifier identifier;
	public Identifier getIdentifier() { return identifier; }
	private final PowerType<?> powerType;
	public PowerType<?> getPowerType() { return powerType; }

	private BloodType(String namespace, String path) { this(new Identifier(namespace, path)); }
	private BloodType(Identifier identifier) {
		this.identifier = identifier;
		this.name = this.identifier.getPath();
		this.powerType = new PowerTypeReference<>(new Identifier(this.identifier.getNamespace(), "blood_types/" + name));
		BLOOD_TYPES.put(this.identifier, this);
	}

	public static final Map<Identifier, BloodType> BLOOD_TYPES = new HashMap<>();

	public static BloodType Register(String name) { return Register(ModId.NAMESPACE, name); }
	public static BloodType Register(String namespace, String name) { return new BloodType(namespace, name); }

	public static final BloodType NONE = Register("none");
	public static final BloodType PLAYER = Register("player");

	public static final BloodType HONEY = Register("honey");
	public static final BloodType LAVA = Register("lava");
	public static final BloodType MUD = Register("mud");
	public static final BloodType SAP = Register("sap");
	public static final BloodType WATER = Register("water");

	public static BloodType Get(LivingEntity entity) {
		for (BloodType bloodType : BLOOD_TYPES.values()) {
			if (bloodType.getPowerType().isActive(entity)) return bloodType;
		}
		if (entity instanceof EntityWithBloodType withBloodType) return withBloodType.GetDefaultBloodType();
		return NONE;
	}


	public static final Map<Block, BloodType> BLOCK_TO_BLOOD_TYPE = new HashMap<>();
	private static boolean registered = false;
	public static BloodType Get(BlockState state) {
		//There might be some blockstate variants that produce different blood types
		return Get(state.getBlock());
	}
	public static BloodType Get(Block block) {
		if (!registered) RegisterBlocksToBloodTypes();
		return BLOCK_TO_BLOOD_TYPE.getOrDefault(block, NONE);
	}
	private static void RegisterBlocksToBloodTypes() {
		registered = true;
		BLOCK_TO_BLOOD_TYPE.putAll(Map.ofEntries(
				Map.entry(Blocks.MAGMA_BLOCK, ModBase.MAGMA_CREAM_BLOOD_TYPE),
				Map.entry(Blocks.HONEY_BLOCK, HONEY),
				//Slime
				Map.entry(Blocks.SLIME_BLOCK, ModBase.SLIME_BLOOD_TYPE),
				Map.entry(ModBase.BLUE_SLIME_BLOCK.asBlock(), ModBase.BLUE_SLIME_BLOOD_TYPE),
				Map.entry(ModBase.PINK_SLIME_BLOCK.asBlock(), ModBase.PINK_SLIME_BLOOD_TYPE),
				//Lava
				Map.entry(Blocks.LAVA, LAVA),
				Map.entry(Blocks.LAVA_CAULDRON, LAVA),
				//Water
				Map.entry(Blocks.WATER, WATER),
				Map.entry(Blocks.WATER_CAULDRON, WATER),
				Map.entry(Blocks.SNOW, WATER),
				Map.entry(Blocks.SNOW_BLOCK, WATER),
				Map.entry(Blocks.POWDER_SNOW, WATER),
				Map.entry(Blocks.POWDER_SNOW_CAULDRON, WATER),
				Map.entry(Blocks.ICE, WATER),
				Map.entry(Blocks.BLUE_ICE, WATER),
				Map.entry(Blocks.FROSTED_ICE, WATER),
				Map.entry(Blocks.PACKED_ICE, WATER),
				//Mud
				Map.entry(ModBase.MUD.asBlock(), MUD),
				Map.entry(ModBase.MUD_FLUID_BLOCK, MUD),
				//Player Blood
				Map.entry(ModBase.BLOOD_BLOCK.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_STAIRS.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_SLAB.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_WALL.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_FENCE.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_PANE.asBlock(), PLAYER),
				Map.entry(ModBase.BLOOD_FLUID_BLOCK, PLAYER),
				Map.entry(ModBase.BLOOD_CAULDRON, PLAYER)
		));
	}

	public static final Map<BloodType, Item> BLOOD_TYPE_TO_SYRINGE = new HashMap<>();
	public static final Map<Item, BloodType> SYRINGE_TO_BLOOD_TYPE = new HashMap<>();

	public static void RegisterBloodType(BloodType type, Item item) {
		BLOOD_TYPE_TO_SYRINGE.put(type, item);
		SYRINGE_TO_BLOOD_TYPE.put(item, type);
	}
}
