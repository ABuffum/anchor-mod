package fun.wich.block;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
	public static final IntProperty AGE_4 = IntProperty.of("age", 0, 4);
	public static final IntProperty COUNT_4 = IntProperty.of("count", 1, 4);
	public static final IntProperty DUSTED = IntProperty.of("dusted", 0, 3);
	public static final BooleanProperty SLOT_0_OCCUPIED = BooleanProperty.of("slot_0_occupied");
	public static final BooleanProperty SLOT_1_OCCUPIED = BooleanProperty.of("slot_1_occupied");
	public static final BooleanProperty SLOT_2_OCCUPIED = BooleanProperty.of("slot_2_occupied");
	public static final BooleanProperty SLOT_3_OCCUPIED = BooleanProperty.of("slot_3_occupied");
	public static final BooleanProperty SLOT_4_OCCUPIED = BooleanProperty.of("slot_4_occupied");
	public static final BooleanProperty SLOT_5_OCCUPIED = BooleanProperty.of("slot_5_occupied");
}
