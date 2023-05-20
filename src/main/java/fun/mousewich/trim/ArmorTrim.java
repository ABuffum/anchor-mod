package fun.mousewich.trim;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Optional;

public class ArmorTrim {
	public static final Codec<ArmorTrim> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("material").forGetter(ArmorTrim::getMaterialName),
			Codec.STRING.fieldOf("pattern").forGetter(ArmorTrim::getPatternName))
			.apply(instance, ArmorTrim::new));
	private static final Text UPGRADE_TEXT = new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.upgrade"))).formatted(Formatting.GRAY);
	private final ArmorTrimMaterial material;
	private final ArmorTrimPattern pattern;

	public ArmorTrim(String material, String pattern) {
		ArmorTrimMaterial assignMaterial = null;
		try { assignMaterial = Enum.valueOf(ArmorTrimMaterial.class, material); } catch (Exception ignored) { }
		this.material = assignMaterial;
		ArmorTrimPattern assignPattern = null;
		try { assignPattern = Enum.valueOf(ArmorTrimPattern.class, pattern); } catch (Exception ignored) { }
		this.pattern = assignPattern;
	}
	public ArmorTrim(ArmorTrimMaterial material, ArmorTrimPattern pattern) {
		this.material = material;
		this.pattern = pattern;
	}

	public static String getArmorMaterialName(ArmorMaterial armorMaterial) {
		String name = armorMaterial.getName();
		return name.contains(":") ? name.substring(name.indexOf(':')) : name;
	}
	private String getMaterialAssetNameFor(ArmorMaterial armorMaterial) {
		String name = this.material.getName();
		return name.equals(armorMaterial.getName()) ? name + "_darker" : name;
	}

	public boolean equals(ArmorTrimPattern pattern, ArmorTrimMaterial material) {
		return pattern == this.pattern && material == this.material;
	}
	public ArmorTrimPattern getPattern() { return this.pattern; }
	public String getPatternName() { return getPattern().getName(); }
	public ArmorTrimMaterial getMaterial() { return this.material; }
	public String getMaterialName() { return getMaterial().getName(); }
	public boolean equals(Object o) {
		if (!(o instanceof ArmorTrim armorTrim)) return false;
		return armorTrim.pattern == this.pattern && armorTrim.material == this.material;
	}
	public Identifier getLeggingsModelId(ArmorMaterial armorMaterial) {
		String name = this.getMaterialAssetNameFor(armorMaterial);
		return ModBase.ID("trims/models/armor/" + getArmorMaterialName(armorMaterial) + "_leggings_" + name);
	}
	public Identifier getGenericModelId(ArmorMaterial armorMaterial) {
		String name = this.getMaterialAssetNameFor(armorMaterial);
		return ModBase.ID("trims/models/armor/" + getArmorMaterialName(armorMaterial) + "_" + name);
	}

	public static boolean apply(ItemStack stack, ArmorTrim trim) {
		if (stack.isIn(ModItemTags.TRIMMABLE_ARMOR)) {
			stack.getOrCreateNbt().put(ModNbtKeys.TRIM, CODEC.encodeStart(NbtOps.INSTANCE, trim).result().orElseThrow());
			return true;
		}
		return false;
	}

	public static Optional<ArmorTrim> getTrim(ItemStack stack) {
		if (stack.isIn(ModItemTags.TRIMMABLE_ARMOR) && stack.getNbt() != null && stack.getNbt().contains(ModNbtKeys.TRIM)) {
			NbtCompound nbtCompound = stack.getSubNbt(ModNbtKeys.TRIM);
			ArmorTrim armorTrim = CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, nbtCompound)).resultOrPartial(ModBase.LOGGER::error).orElse(null);
			return Optional.ofNullable(armorTrim);
		}
		return Optional.empty();
	}

	public static void appendTooltip(ItemStack stack, List<Text> tooltip) {
		Optional<ArmorTrim> optional = ArmorTrim.getTrim(stack);
		if (optional.isPresent()) {
			ArmorTrim armorTrim = optional.get();
			tooltip.add(UPGRADE_TEXT);
			ArmorTrimMaterial material = armorTrim.getMaterial();
			tooltip.add(new LiteralText(" ").append(armorTrim.getPattern().getDescription(material.getColor())));
			tooltip.add(new LiteralText(" ").append(material.getDescription()));
		}
	}
}