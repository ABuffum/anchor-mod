package haven.blocks.anchors;

import haven.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class SubstituteAnchorBlockEntity extends BlockEntity {
	public SubstituteAnchorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.SUBSTITUTE_ANCHOR_BLOCK_ENTITY, pos, state);
		this.pos = pos;
		update(state);
	}

	private static final Identifier ERROR_TEXTURE = ModBase.ID("textures/anchor/inactive_anchor.png");

	private int owner = 0;
	public final BlockPos pos;

	private static HashMap<Integer, Identifier> TEXTURE_IDS;

	public void update(BlockState state) {
		int owner = state.get(AnchorBlock.OWNER);
		if (owner != this.owner) {
			this.owner = owner;
			if (!TEXTURE_IDS.containsKey(owner)) {
				TEXTURE_IDS.put(owner, ModBase.ID("textures/anchor/" + ModBase.ANCHOR_MAP.get(owner) + "_core.png"));
			}
		}
	}

	public static void tick(World world, BlockPos pos, BlockState state, SubstituteAnchorBlockEntity be) {
		if (be != null) be.update(state);
	}

	public int getOwner() {
		return owner;
	}
	public Identifier getTextureId() {
		if (TEXTURE_IDS.containsKey(owner)) return TEXTURE_IDS.get(owner);
		return null;
	}

	static {
		TEXTURE_IDS = new HashMap<>();
	}
}
