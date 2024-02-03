package fun.wich.item.basic;

import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModDyedSheep;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class ModDyeItem extends Item {
	private static final Map<ModDyeColor, ModDyeItem> DYES = new HashMap<>();
	private final ModDyeColor color;

	public ModDyeItem(ModDyeColor color, Settings settings) {
		super(settings);
		this.color = color;
		DYES.put(color, this);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof SheepEntity sheepEntity && sheepEntity.isAlive() && !sheepEntity.isSheared() && ((ModDyedSheep)sheepEntity).GetModColor() != this.color) {
			sheepEntity.world.playSoundFromEntity(user, sheepEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
			if (!user.world.isClient) {
				((ModDyedSheep)sheepEntity).SetModColor(this.color);
				stack.decrement(1);
			}
			return ActionResult.success(user.world.isClient);
		}
		return ActionResult.PASS;
	}

	public ModDyeColor getColor() { return this.color; }
	public static ModDyeItem byColor(ModDyeColor color) { return DYES.get(color); }
}
