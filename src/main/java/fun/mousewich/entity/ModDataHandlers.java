package fun.mousewich.entity;

import fun.mousewich.ModBase;
import fun.mousewich.entity.passive.sniffer.SnifferEntity;
import fun.mousewich.entity.variants.FrogVariant;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;

import java.util.OptionalInt;

public class ModDataHandlers {
	public static final TrackedData<Integer> MOD_CARPET_COLOR = DataTracker.registerData(LlamaEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static int GetModCarpetColor(Item item) {
		if (item != null) {
			if (ModBase.RAINBOW_CARPET.contains(item) || ModBase.RAINBOW_FLEECE_CARPET.contains(item)) return 0;
			else if (Items.MOSS_CARPET == item) return 1;
			else if (ModBase.GLOW_LICHEN_CARPET.contains(item)) return 2;
			else if (ModBase.BEIGE_CARPET.contains(item) || ModBase.BEIGE_FLEECE_CARPET.contains(item)) return 3;
			else if (ModBase.BURGUNDY_CARPET.contains(item) || ModBase.BURGUNDY_FLEECE_CARPET.contains(item)) return 4;
			else if (ModBase.MINT_CARPET.contains(item) || ModBase.MINT_FLEECE_CARPET.contains(item)) return 5;
			else if (ModBase.LAVENDER_CARPET.contains(item) || ModBase.LAVENDER_FLEECE_CARPET.contains(item)) return 6;
		}
		return -1;
	}

	public static final TrackedDataHandler<Long> LONG = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, Long value) { packetByteBuf.writeVarLong(value); }
		@Override public Long read(PacketByteBuf packetByteBuf) { return packetByteBuf.readVarLong(); }
		@Override public Long copy(Long value) { return value; }
	};
	public static final TrackedDataHandler<FrogVariant> FROG_VARIANT = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, FrogVariant variant) { packetByteBuf.writeEnumConstant(variant); }
		@Override public FrogVariant read(PacketByteBuf packetByteBuf) { return packetByteBuf.readEnumConstant(FrogVariant.class); }
		@Override public FrogVariant copy(FrogVariant variant) { return variant; }
	};
	public static final TrackedDataHandler<ModEntityPose> NEW_ENTITY_POSE = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, ModEntityPose entityPose) { packetByteBuf.writeEnumConstant(entityPose); }
		@Override public ModEntityPose read(PacketByteBuf packetByteBuf) { return packetByteBuf.readEnumConstant(ModEntityPose.class); }
		@Override public ModEntityPose copy(ModEntityPose entityPose) { return entityPose; }
	};
	public static final TrackedDataHandler<OptionalInt> OPTIONAL_INT = new TrackedDataHandler<>() {
		@Override public void write(PacketByteBuf packetByteBuf, OptionalInt optionalInt) { packetByteBuf.writeVarInt(optionalInt.orElse(-1) + 1); }
		@Override public OptionalInt read(PacketByteBuf packetByteBuf) {
			int i = packetByteBuf.readVarInt();
			return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
		}
		@Override public OptionalInt copy(OptionalInt optionalInt) { return optionalInt; }
	};
	public static final TrackedDataHandler<SnifferEntity.State> SNIFFER_STATE = new TrackedDataHandler<>(){
		@Override public void write(PacketByteBuf packetByteBuf, SnifferEntity.State state) { packetByteBuf.writeEnumConstant(state); }
		@Override public SnifferEntity.State read(PacketByteBuf packetByteBuf) { return packetByteBuf.readEnumConstant(SnifferEntity.State.class); }
		@Override public SnifferEntity.State copy(SnifferEntity.State state) { return state; }
	};
	public static void Initialize() {
		TrackedDataHandlerRegistry.register(LONG);
		TrackedDataHandlerRegistry.register(FROG_VARIANT);
		TrackedDataHandlerRegistry.register(NEW_ENTITY_POSE);
		TrackedDataHandlerRegistry.register(OPTIONAL_INT);
		TrackedDataHandlerRegistry.register(SNIFFER_STATE);
	}
}
