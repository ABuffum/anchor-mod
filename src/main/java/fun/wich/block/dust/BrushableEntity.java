package fun.wich.block.dust;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public interface BrushableEntity {
	boolean brush(long worldTime, PlayerEntity player, Direction hitDirection);
	int getDustedLevel();
	Direction getHitDirection();
	ItemStack getItem();
}
