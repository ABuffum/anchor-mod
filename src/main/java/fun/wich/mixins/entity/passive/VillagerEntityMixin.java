package fun.wich.mixins.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.origins.power.VillagerPriceModifierPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer, EntityWithBloodType {
	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) { super(entityType, world); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.VILLAGER_BLOOD_TYPE; }

	@Inject(method="prepareOffersFor", at=@At("TAIL"))
	private void GetSpecialPriceWithPowers(PlayerEntity player, CallbackInfo ci) {
		int factor = VillagerPriceModifierPower.getFactor(player);
		if (factor != 0) {
			double d = 0.3 + (0.0625 * factor);
			for (TradeOffer tradeOffer : this.getOffers()) {
				int k = (int)Math.floor(d * (double)tradeOffer.getOriginalFirstBuyItem().getCount());
				tradeOffer.increaseSpecialPrice(k);
			}
		}
	}
}
