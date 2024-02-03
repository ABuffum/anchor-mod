package fun.wich.haven.block.anchor;

import fun.wich.ModId;
import fun.wich.haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class AnchorBlockEntity extends BlockEntity {
	public static final Identifier ERROR_TEXTURE = ModId.ID("textures/entity/anchor/inactive_anchor.png");
	public static HashMap<Integer, Identifier> TEXTURE_IDS = new HashMap<>(Map.of(0, ERROR_TEXTURE));
	public static void testTexture(int owner) {
		if (!TEXTURE_IDS.containsKey(owner)) TEXTURE_IDS.put(owner, ModId.ID("textures/entity/anchor/" + HavenMod.ANCHOR_MAP.get(owner) + "_anchor.png"));
	}

	private int owner = 0;
	public double prevTick = 0;
	public AnchorBlockEntity(BlockPos pos, BlockState state) {
		super(HavenMod.ANCHOR_BLOCK_ENTITY, pos, state);
		update(state);
	}
	public void update(BlockState state) {
		int owner = state.get(AnchorBlock.OWNER);
		if (owner != this.owner) testTexture(this.owner = owner);
	}
	public static Identifier getTextureId(int owner) {
		if (TEXTURE_IDS.containsKey(owner)) return TEXTURE_IDS.get(owner);
		return ERROR_TEXTURE;
	}
	public static void tick(World w, BlockPos p, BlockState s, AnchorBlockEntity e) { if (e != null) e.update(s); }
	public int getOwner() { return this.owner; }
	public Identifier getTextureId() { return getTextureId(this.owner); }
}
