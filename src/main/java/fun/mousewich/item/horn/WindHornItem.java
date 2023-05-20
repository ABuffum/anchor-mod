package fun.mousewich.item.horn;

import fun.mousewich.enchantment.GravityEnchantment;
import fun.mousewich.event.ModGameEvent;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class WindHornItem extends HornItem {
	public WindHornItem(Settings settings) { super(settings); }
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		playSound(world, user);
		user.getItemCooldownManager().set(this, 200);
		GravityEnchantment.pullInNearbyEntities(world, user.getPos(), -8, List.of(user));
		return TypedActionResult.consume(itemStack);
	}
	@Override
	public int getMaxUseTime(ItemStack stack) { return 140; }
	private static void playSound(World world, PlayerEntity player) {
		world.playSoundFromEntity(player, player, ModSoundEvents.ITEM_WIND_HORN, SoundCategory.RECORDS, 16, 1.0f);
		world.emitGameEvent(player, ModGameEvent.INSTRUMENT_PLAY, player.getBlockPos());
	}
}
