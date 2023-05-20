package fun.mousewich.item.pouch;

import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class ChickenPouchItem extends EntityPouchItem {
	public ChickenPouchItem(EntityType<?> type, Settings settings) { super(type, ModSoundEvents.ITEM_POUCH_EMPTY_CHICKEN, settings); }

	@Override
	protected void playEmptyingSound(PlayerEntity player, WorldAccess world, BlockPos pos, ItemStack stack) {
		if (stack.hasCustomName()) {
			String name = stack.getName().asString();
			if (name.equals("Greg")) {
				world.playSound(null, pos, ModSoundEvents.ITEM_POUCH_EMPTY_CHICKEN_GREG, SoundCategory.NEUTRAL, 1.0f, 1.0f);
				return;
			}
		}
		super.playEmptyingSound(player, world, pos, stack);
	}
}