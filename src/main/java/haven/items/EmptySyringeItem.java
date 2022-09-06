package haven.items;

import haven.sounds.HavenSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EmptySyringeItem extends Item {
	public EmptySyringeItem(Settings settings) {
		super(settings);
	}

	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		//TODO: Determine what type of blood to give
		entity.playSound(HavenSoundEvents.SYRINGE_INJECTED, 0.5F, 1.0F);
		return ActionResult.PASS;
	}
}
