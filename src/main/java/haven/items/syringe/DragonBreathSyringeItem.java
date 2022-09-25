package haven.items.syringe;

import haven.HavenMod;
import haven.blood.BloodType;
import haven.damage.HavenDamageSource;
import haven.mixins.entities.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Iterator;

public class DragonBreathSyringeItem extends BaseSyringeItem {
	public DragonBreathSyringeItem() {
		this(new Settings().group(HavenMod.BLOOD_ITEM_GROUP).recipeRemainder(HavenMod.DIRTY_SYRINGE).maxCount(1));
	}
	public DragonBreathSyringeItem(Settings settings) {
		super(settings);
	}
}
