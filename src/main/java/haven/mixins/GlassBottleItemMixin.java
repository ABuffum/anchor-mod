package haven.mixins;

import haven.HavenMod;
//import haven.entities.ConfettiCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
	private GlassBottleItemMixin(Settings settings) {
		super(settings);
	}

	@Shadow
	protected abstract ItemStack fill(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2);

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		//Fill bottle with confetti
		/*List<ConfettiCloudEntity> list = world.getEntitiesByClass(ConfettiCloudEntity.class, user.getBoundingBox().expand(4.0D), (entity) -> {
			return entity != null && entity.isAlive();
		});
		ItemStack itemStack = user.getStackInHand(hand);
		if (!list.isEmpty()) {
			for(ConfettiCloudEntity cloud : list) {
				cloud.kill();
				world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				cir.setReturnValue(TypedActionResult.success(this.fill(itemStack, user, new ItemStack(HavenMod.BOTTLED_CONFETTI_ITEM)), world.isClient()));
				return;
			}
		}*/
		//Fill bottle with Blood
		BlockHitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockPos blockPos = hitResult.getBlockPos();
		FluidState state = world.getFluidState(blockPos);
		if (state.getFluid() == HavenMod.STILL_BLOOD_FLUID || state.getFluid() == HavenMod.FLOWING_BLOOD_FLUID) {
			world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(TypedActionResult.success(fill(user.getStackInHand(hand), user, new ItemStack(HavenMod.BLOOD_BOTTLE))));
		}
	}
}
