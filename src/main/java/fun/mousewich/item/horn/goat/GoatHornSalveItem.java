package fun.mousewich.item.horn.goat;

import fun.mousewich.sound.ModSoundEvents;
import fun.mousewich.util.GoatUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class GoatHornSalveItem extends Item {

	public GoatHornSalveItem(Settings settings) { super(settings); }

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof GoatEntity goat) {
			if (!GoatUtil.hasBothHorns(goat)) {
				GoatUtil.addHorns(goat);
				user.incrementStat(Stats.USED.getOrCreateStat(this));
				if (!user.getAbilities().creativeMode) stack.decrement(1);
				entity.playSound(ModSoundEvents.SALVE_APPLIED, 1.0F, 1.0F);
				return ActionResult.CONSUME;
			}
		}
		return ActionResult.PASS;
	}
}
