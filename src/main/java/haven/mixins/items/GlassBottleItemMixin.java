package haven.mixins.items;

import haven.HavenMod;
//import haven.entities.ConfettiCloudEntity;
import haven.entities.ConfettiCloudEntity;
import haven.entities.DragonBreathCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
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
		List<ConfettiCloudEntity> clist = world.getEntitiesByClass(ConfettiCloudEntity.class, user.getBoundingBox().expand(4.0D), (entity) -> {
			return entity != null && entity.isAlive();
		});
		ItemStack itemStack = user.getStackInHand(hand);
		if (!clist.isEmpty()) {
			for(ConfettiCloudEntity cloud : clist) {
				cloud.kill();
				world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				cir.setReturnValue(TypedActionResult.success(this.fill(itemStack, user, new ItemStack(HavenMod.BOTTLED_CONFETTI_ITEM)), world.isClient()));
				return;
			}
		}
		//Fill bottle with dragon's breath
		List<DragonBreathCloudEntity> dlist = world.getEntitiesByClass(DragonBreathCloudEntity.class, user.getBoundingBox().expand(4.0D), (entity) -> {
			return entity != null && entity.isAlive();
		});
		itemStack = user.getStackInHand(hand);
		if (!dlist.isEmpty()) {
			for(DragonBreathCloudEntity cloud : dlist) {
				cloud.kill();
				world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, user.getBlockPos());
				cir.setReturnValue(TypedActionResult.success(this.fill(itemStack, user, new ItemStack(Items.DRAGON_BREATH)), world.isClient()));
				return;
			}
		}
		//Fill bottle with Blood
		BlockHitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockPos blockPos = hitResult.getBlockPos();
		FluidState state = world.getFluidState(blockPos);
		if (state.getFluid() == HavenMod.STILL_BLOOD_FLUID || state.getFluid() == HavenMod.FLOWING_BLOOD_FLUID) {
			world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(TypedActionResult.success(fill(user.getStackInHand(hand), user, new ItemStack(HavenMod.BLOOD_BOTTLE))));
			return;
		}
		//Fill bottle with Lava
		else if (state.getFluid() == Fluids.LAVA || state.getFluid() == Fluids.FLOWING_LAVA) {
			world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(TypedActionResult.success(fill(user.getStackInHand(hand), user, new ItemStack(HavenMod.LAVA_BOTTLE))));
			return;
		}
	}
}
