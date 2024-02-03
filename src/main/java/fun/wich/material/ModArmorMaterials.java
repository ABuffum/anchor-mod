package fun.wich.material;

import fun.wich.ModBase;
import fun.wich.gen.data.tag.ModItemTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
	AMETHYST("amethyst", 33, new int[]{ 3, 5, 7, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.0F,
			() -> Ingredient.ofItems(Items.AMETHYST_SHARD), 10),
	COPPER("copper",11, new int[] { 1, 4, 5, 2 }, 17, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.COPPER_INGOT), 6),
	EXPOSED_COPPER("exposed_copper",11, new int[] { 1, 4, 5, 2 }, 17, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.COPPER_INGOT), 6),
	WEATHERED_COPPER("weathered_copper",11, new int[] { 1, 4, 5, 2 }, 17, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.COPPER_INGOT), 6),
	OXIDIZED_COPPER("oxidized_copper",11, new int[] { 1, 4, 5, 2 }, 17, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.COPPER_INGOT), 6),
	DARK_IRON("dark_iron", 15, new int[]{ 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F,
			() -> Ingredient.ofItems(ModBase.DARK_IRON_INGOT), 5),
	ECHO("echo", 37, new int[] { 3, 6, 8, 3 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.5F,
			() -> Ingredient.ofItems(ModBase.ECHO_SHARD), 10),
	EMERALD("emerald", 27, new int[] { 3, 5, 6, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5F, 0.0F,
			() -> Ingredient.ofItems(Items.EMERALD), 9),
	FLEECE("fleece", 4, new int[] { 1, 1, 2, 1 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
			() -> Ingredient.fromTag(ModItemTags.FLEECE), 3),
	OBSIDIAN("obsidian", 33, new int[] { 3, 6, 8, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.5F, 0.0F,
			() -> Ingredient.ofItems(Items.OBSIDIAN), 10),
	QUARTZ("quartz", 20, new int[] { 2, 5, 5, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 0.0F,
			() -> Ingredient.ofItems(Items.QUARTZ), 8),
	RUBY_GOGGLES("ruby_goggles", 25, new int[]{ 3, 5, 6, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.LEATHER, ModBase.RUBY), 0),
	SAPPHIRE_GOGGLES("sapphire_goggles", 25, new int[]{ 3, 5, 6, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.LEATHER, ModBase.SAPPHIRE), 0),
	SHULKER("shulker", 30, new int[] { 3, 5, 6, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0F, 0.25F,
			() -> Ingredient.ofItems(Items.SHULKER_SHELL), 0),
	STUDDED_LEATHER("studded_leather", 5, new int[] { 2, 3, 4, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.IRON_NUGGET), 4),
	TINTED_GOGGLES("tinted_goggles", 25, new int[] { 3, 5, 6, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,
			() -> Ingredient.ofItems(Items.LEATHER, Items.AMETHYST_SHARD), 0),
	TURTLE("turtle", 25, new int[] { 2, 5, 6, 2 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0f, 0.25f,
			() -> Ingredient.ofItems(Items.SCUTE), 0),
	WILDFIRE("wildfire", 15, new int[]{ 2, 5, 6, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f,
			() -> Ingredient.ofItems(Items.GOLD_INGOT), 7),
	//Haven
	LUX_CROWN("lux_crown", 7, new int[]{ 1, 3, 5, 2 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f,
			() -> Ingredient.ofItems(Items.GOLD_INGOT), 7);

	private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;
	private final int horseProtectionAmount;

	ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier, int horseProtectionAmount) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy<>(repairIngredientSupplier);
		this.horseProtectionAmount = horseProtectionAmount;
	}

	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
	}
	public int getProtectionAmount(EquipmentSlot slot) { return this.protectionAmounts[slot.getEntitySlotId()]; }
	public int getEnchantability() { return this.enchantability; }
	public SoundEvent getEquipSound() { return this.equipSound; }
	public Ingredient getRepairIngredient() { return this.repairIngredientSupplier.get(); }
	public String getName() { return this.name; }
	public float getToughness() { return this.toughness; }
	public float getKnockbackResistance() { return this.knockbackResistance; }
	public int getHorseProtectionAmount() { return this.horseProtectionAmount; }
}
