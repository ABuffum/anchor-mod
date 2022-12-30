package haven.blocks.anchors;

import haven.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrokenAnchorBlockEntity extends BlockEntity {
	private int owner = 0;
	public BrokenAnchorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.BROKEN_ANCHOR_BLOCK_ENTITY, pos, state);
		update(state);
	}
	public void update(BlockState state) {
		int owner = state.get(AnchorBlock.OWNER);
		if (owner != this.owner) AnchorBlockEntity.testTexture(this.owner = owner);
	}
	public static void tick(World w, BlockPos p, BlockState s, BrokenAnchorBlockEntity e) { if (e != null) e.update(s); }
	public int getOwner() { return this.owner; }
	public Identifier getTextureId() { return AnchorBlockEntity.getTextureId(this.owner); }
}
