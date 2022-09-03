package haven.entities;

import haven.HavenMod;

import java.util.HashMap;

import haven.blocks.AnchorBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class AnchorBlockEntity extends BlockEntity {
	public AnchorBlockEntity(BlockPos pos, BlockState state) {
		super(HavenMod.ANCHOR_BLOCK_ENTITY, pos, state);
		this.pos = pos;
		update(state);
	}

	private static final Identifier ERROR_TEXTURE = HavenMod.ID("textures/anchor/inactive_anchor.png");

	private int owner = 0;
	public final BlockPos pos;

	private static HashMap<Integer, Identifier> TEXTURE_IDS;

	public void update(BlockState state) {
		int owner = state.get(AnchorBlock.OWNER);
		if (owner != this.owner) {
			this.owner = owner;
			if (!TEXTURE_IDS.containsKey(owner)) {
				TEXTURE_IDS.put(owner, HavenMod.ID("textures/anchor/" + HavenMod.ANCHOR_MAP.get(owner) + "_anchor.png"));
			}
		}
	}

	public static void tick(World world, BlockPos pos, BlockState state, AnchorBlockEntity be) {
		if (be != null) be.update(state);
	}

	public int getOwner() {
		return owner;
	}
	public Identifier getTextureId() {
		if (TEXTURE_IDS.containsKey(owner)) return TEXTURE_IDS.get(owner);
		return ERROR_TEXTURE;
	}

	static {
		TEXTURE_IDS = new HashMap<>();
		TEXTURE_IDS.put(0, ERROR_TEXTURE);
	}
}
