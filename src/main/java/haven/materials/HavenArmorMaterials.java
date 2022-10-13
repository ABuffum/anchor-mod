package haven.materials;

import haven.HavenMod;
import haven.HavenTags;
import haven.containers.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.Arrays;
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
	ECHO("echo", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.5F, () -> {
		return Ingredient.ofItems(HavenMod.ECHO_SHARD);
	}),
	EMERALD("emerald", 27, new int[]{3, 5, 6, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5F, 0.0F, () -> {
		return Ingredient.ofItems(Items.EMERALD);
	}),
	FLEECE("fleece", 4, new int[]{1, 1, 2, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
		return Ingredient.fromTag(HavenTags.Items.FLEECE);
	}),
	OBSIDIAN("obsidian", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.5F, 0.0F, () -> {
		return Ingredient.ofItems(Items.OBSIDIAN);
	}),
	QUARTZ("quartz", 20, new int[]{2, 5, 5, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 0.0F, () -> {
		return Ingredient.ofItems(Items.QUARTZ);
	}),
	STUDDED_LEATHER("studded_leather", 5, new int[]{2, 3, 4, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
		return Ingredient.ofItems(Items.IRON_NUGGET);
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
