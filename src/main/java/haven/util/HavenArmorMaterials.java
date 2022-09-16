package haven.util;

import haven.HavenMod;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum HavenArmorMaterials implements ArmorMaterial {
	AMETHYST("amethyst", 33, new int[]{3, 5, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.0F, () -> {
		return Ingredient.ofItems(Items.AMETHYST_SHARD);
	}),
	COPPER("copper",11, new int[]{1, 4, 5, 2}, 17, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, () -> {
		return Ingredient.ofItems(Items.COPPER_INGOT);
	}),
	DARK_IRON("dark_iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
		return Ingredient.ofItems(HavenMod.DARK_IRON_INGOT);
	}),
	EMERALD("emerald", 27, new int[]{3, 5, 6, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.0F, () -> {
		return Ingredient.ofItems(Items.EMERALD);
	});

	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;

	private HavenArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy(repairIngredientSupplier);
	}

	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}

	public int getProtectionAmount(EquipmentSlot slot) {
		return this.protectionAmounts[slot.getEntitySlotId()];
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	public Ingredient getRepairIngredient() {
		return (Ingredient)this.repairIngredientSupplier.get();
	}

	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
