package fun.mousewich.item.syringe;

import fun.mousewich.ModBase;
import fun.mousewich.ModConfig;
import fun.mousewich.command.ChorusCommand;
import fun.mousewich.damage.InjectedBloodDamageSource;
import fun.mousewich.damage.ModDamageSource;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.haven.HavenMod;
import fun.mousewich.mixins.entity.LivingEntityAccessor;
import fun.mousewich.origins.power.ChorusImmunePower;
import fun.mousewich.origins.power.PowersUtil;
import fun.mousewich.ryft.RyftMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BloodSyringeItem extends BaseSyringeItem {
	private final SyringeEffect applyEffect;
	public BloodSyringeItem() { this(null); }
	public BloodSyringeItem(SyringeEffect applyEffect) {
		super();
		this.applyEffect = applyEffect;
	}

	public static ItemStack getForBloodType(BloodType type) {
		ItemStack stack = new ItemStack(ModBase.BLOOD_SYRINGE);
		stack.setSubNbt(ModNbtKeys.BLOOD_TYPE, NbtString.of(type.getIdentifier().toString()));
		return stack;
	}
	public static BloodType getBloodType(ItemStack stack) {
		NbtCompound nbt = stack.getNbt();
		if (nbt != null && nbt.contains(ModNbtKeys.BLOOD_TYPE, NbtElement.STRING_TYPE)) {
			Identifier id = ModBase.ID(nbt.getString(ModNbtKeys.BLOOD_TYPE));
			if (BloodType.BLOOD_TYPES.containsKey(id)) return BloodType.BLOOD_TYPES.get(id);
		}
		return BloodType.PLAYER;
	}

	public static int getHealingAmount(BloodType bloodType, BloodType entityType, LivingEntity entity) {
		if (bloodType == BloodType.PLAYER)  {
			if (bloodType == entityType || entityType == ModBase.ZOMBIE_BLOOD_TYPE) return 1;
			else if (entity instanceof PlayerEntity && entityType != BloodType.NONE) return 1;
		}
		return -1;
	}

	@Override
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		if (applyEffect != null) applyEffect.ApplyEffect(user, stack, entity);
		else {
			BloodType bloodType = getBloodType(stack);
			BloodType entityType = BloodType.Get(entity);
			InjectedBloodDamageSource source = ModDamageSource.Injected(user, bloodType);
			if (bloodType == entityType) heal(entity, 1);
			else if (entityType == BloodType.NONE) entity.damage(source, 1);
			else if (bloodType == BloodType.PLAYER) {
				if (entityType == ModBase.ZOMBIE_BLOOD_TYPE || entity instanceof PlayerEntity) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ZOMBIE_BLOOD_TYPE) {
				if (entityType == BloodType.PLAYER || entity instanceof PlayerEntity) entity.heal(1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
			else if (bloodType == ModBase.HORSE_BLOOD_TYPE) {
				if (entityType == ModBase.ZOMBIE_HORSE_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ZOMBIE_HORSE_BLOOD_TYPE) {
				if (entityType == ModBase.HORSE_BLOOD_TYPE) entity.heal(1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
			else if (bloodType == ModBase.VILLAGER_BLOOD_TYPE) {
				if (entityType == ModBase.ZOMBIE_VILLAGER_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ZOMBIE_VILLAGER_BLOOD_TYPE) {
				if (entityType == ModBase.VILLAGER_BLOOD_TYPE) entity.heal(1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
			else if (bloodType == ModBase.PIGLIN_BLOOD_TYPE) {
				if (entityType == ModBase.ZOMBIFIED_PIGLIN_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ZOMBIFIED_PIGLIN_BLOOD_TYPE) {
				if (entityType == ModBase.PIGLIN_BLOOD_TYPE) entity.heal(1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
			else if (bloodType == ModBase.HOGLIN_BLOOD_TYPE) {
				if (entityType == ModBase.ZOGLIN_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ZOGLIN_BLOOD_TYPE) {
				if (entityType == ModBase.HOGLIN_BLOOD_TYPE) entity.heal(1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
			}
			else if (bloodType == ModBase.BEE_BLOOD_TYPE) {
				if (ModConfig.REGISTER_HAVEN_MOD && entityType == HavenMod.BEE_ENDERMAN_BLOOD_TYPE) heal(entity, 1);
				else {
					entity.damage(source, 1);
					entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
				}
			}
			else if (bloodType == ModBase.ENDERMAN_BLOOD_TYPE) {
				if (ModConfig.REGISTER_HAVEN_MOD && entityType == HavenMod.BEE_ENDERMAN_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ENDERMITE_BLOOD_TYPE) {
				entity.damage(source, 1);
				if (!PowersUtil.Active(user, ChorusImmunePower.class)) ChorusCommand.TeleportEntity(entity);
			}
			else if (bloodType == ModBase.CAT_BLOOD_TYPE || bloodType == ModBase.OCELOT_BLOOD_TYPE) {
				if (entityType == ModBase.CAT_BLOOD_TYPE || entityType == ModBase.OCELOT_BLOOD_TYPE) heal(entity, 1);
				else if (ModConfig.REGISTER_HAVEN_MOD && entityType == HavenMod.DISEASED_CAT_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.ENDER_DRAGON_BLOOD_TYPE || (ModConfig.REGISTER_RYFT_MOD && bloodType == RyftMod.DRACONIC_BLOOD_TYPE)) {
				if (entityType == ModBase.ENDER_DRAGON_BLOOD_TYPE || entityType == RyftMod.DRACONIC_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.GOAT_BLOOD_TYPE || bloodType == ModBase.SHEEP_BLOOD_TYPE) {
				if (entityType == ModBase.GOAT_BLOOD_TYPE || entityType == ModBase.SHEEP_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (bloodType == ModBase.SQUID_BLOOD_TYPE || bloodType == ModBase.GLOW_SQUID_BLOOD_TYPE) {
				if (entityType == ModBase.SQUID_BLOOD_TYPE || entityType == ModBase.GLOW_SQUID_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
			}
			else if (ModConfig.REGISTER_HAVEN_MOD && bloodType == HavenMod.DISEASED_CAT_BLOOD_TYPE) {
				if (entityType == ModBase.CAT_BLOOD_TYPE || entityType == ModBase.OCELOT_BLOOD_TYPE) heal(entity, 1);
				else entity.damage(source, 1);
				entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
			}
			else entity.damage(source, 1);
		}
	}

	public static void heal(LivingEntity entity, float amount) {
		if (amount > 0) entity.heal(amount);
		if (!entity.getEntityWorld().isClient) {
			Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();
			LivingEntityAccessor lea = (LivingEntityAccessor)entity;
			for(boolean bl = false; iterator.hasNext(); bl = true) {
				StatusEffectInstance effect = iterator.next();
				StatusEffect type = effect.getEffectType();
				if (type == ModBase.BLEEDING_EFFECT) lea.OnStatusEffectRemoved(effect);
				iterator.remove();
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		BloodType type = getBloodType(stack);
		if (type != null && type != BloodType.NONE) {
			Identifier id = type.getIdentifier();
			tooltip.add(new TranslatableText("blood_type." + id.getNamespace() + "." + id.getPath()));
		}
	}
}
