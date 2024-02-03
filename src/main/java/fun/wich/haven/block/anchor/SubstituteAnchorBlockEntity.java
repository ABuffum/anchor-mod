package fun.wich.haven.block.anchor;

import fun.wich.haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SubstituteAnchorBlockEntity extends BlockEntity {
	private int owner = 0;
	public double prevTick = 0;
	public SubstituteAnchorBlockEntity(BlockPos pos, BlockState state) {
		super(HavenMod.SUBSTITUTE_ANCHOR_BLOCK_ENTITY, pos, state);
		update(state);
	}
	public void update(BlockState state) {
		int owner = state.get(AnchorBlock.OWNER);
		if (owner != this.owner) AnchorBlockEntity.testTexture(this.owner = owner);
	}
	public static void tick(World w, BlockPos p, BlockState s, SubstituteAnchorBlockEntity e) { if (e != null) e.update(s); }
	public int getOwner() { return this.owner; }
	public Identifier getTextureId() { return AnchorBlockEntity.getTextureId(this.owner); }
}
