package fun.wich.block.dust.sand;

import fun.wich.ModBase;
import fun.wich.block.dust.BrushableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class SandyBlockEntity extends BrushableBlockEntity {
	public SandyBlockEntity(BlockPos pos, BlockState state) { super(ModBase.SANDY_BLOCK_ENTITY, pos, state); }
	@Override
	public void generateItem(PlayerEntity player) { this.item = new ItemStack(Items.SAND); }
	@Override
	public ItemStack getItem() { return new ItemStack(Items.SAND); }
}
