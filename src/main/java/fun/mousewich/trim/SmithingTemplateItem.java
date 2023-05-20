package fun.mousewich.trim;

import fun.mousewich.ModFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.List;

public class SmithingTemplateItem extends Item {
	private static final Formatting TITLE_FORMATTING = Formatting.GRAY;
	private static final Formatting DESCRIPTION_FORMATTING = Formatting.BLUE;
	private static final String TRANSLATION_KEY = Util.createTranslationKey("item", new Identifier("smithing_template"));
	private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = new Identifier("item/empty_armor_slot_helmet");
	private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = new Identifier("item/empty_armor_slot_chestplate");
	private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = new Identifier("item/empty_armor_slot_leggings");
	private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = new Identifier("item/empty_armor_slot_boots");
	private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = new Identifier("item/empty_slot_ingot");
	private final Text appliesToText;
	private final Text ingredientsText;
	private final Text titleText;
	private final Text baseSlotDescriptionText;
	private final Text additionsSlotDescriptionText;
	private final List<Identifier> emptyBaseSlotTextures;
	private final List<Identifier> emptyAdditionsSlotTextures;

	public SmithingTemplateItem(Text appliesToText, Text ingredientsText, Text titleText, Text baseSlotDescriptionText, Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures) {
		super(ModFactory.ItemSettings(ItemGroup.MISC));
		this.appliesToText = appliesToText;
		this.ingredientsText = ingredientsText;
		this.titleText = titleText;
		this.baseSlotDescriptionText = baseSlotDescriptionText;
		this.additionsSlotDescriptionText = additionsSlotDescriptionText;
		this.emptyBaseSlotTextures = emptyBaseSlotTextures;
		this.emptyAdditionsSlotTextures = emptyAdditionsSlotTextures;
	}
	public static SmithingTemplateItem ofArmorTrimPattern(String name) {
		return new SmithingTemplateItem(
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.armor_trim.applies_to"))).formatted(DESCRIPTION_FORMATTING),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.armor_trim.ingredients"))).formatted(DESCRIPTION_FORMATTING),
				new TranslatableText("trim_pattern." + name).formatted(TITLE_FORMATTING),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.armor_trim.base_slot_description"))),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.armor_trim.additions_slot_description"))),
				List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE),
				List.of(EMPTY_SLOT_INGOT_TEXTURE,
						new Identifier("item/empty_slot_redstone_dust"),
						new Identifier("item/empty_slot_lapis_lazuli"),
						new Identifier("item/empty_slot_quartz"),
						new Identifier("item/empty_slot_diamond"),
						new Identifier("item/empty_slot_emerald"),
						new Identifier("item/empty_slot_amethyst_shard")));
	}
	public static SmithingTemplateItem createNetheriteUpgrade() {
		return new SmithingTemplateItem(
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.netherite_upgrade.applies_to"))).formatted(DESCRIPTION_FORMATTING),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.netherite_upgrade.ingredients"))).formatted(DESCRIPTION_FORMATTING),
				new TranslatableText(Util.createTranslationKey("upgrade", new Identifier("netherite_upgrade"))).formatted(TITLE_FORMATTING),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.netherite_upgrade.base_slot_description"))),
				new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.netherite_upgrade.additions_slot_description"))),
				List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
						new Identifier("item/empty_slot_sword"),
						EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
						new Identifier("item/empty_slot_pickaxe"),
						EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
						new Identifier("item/empty_slot_axe"),
						EMPTY_ARMOR_SLOT_BOOTS_TEXTURE,
						new Identifier("item/empty_slot_hoe"),
						new Identifier("item/empty_slot_shovel")),
				List.of(EMPTY_SLOT_INGOT_TEXTURE));
	}
	private static final Text INGREDIENTS_TEXT = new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.ingredients"))).formatted(TITLE_FORMATTING);
	private static final Text APPLIES_TO_TEXT = new TranslatableText(Util.createTranslationKey("item", new Identifier("smithing_template.applies_to"))).formatted(TITLE_FORMATTING);
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(this.titleText);
		tooltip.add(Text.of(""));
		tooltip.add(APPLIES_TO_TEXT);
		tooltip.add(new LiteralText(" ").append(this.appliesToText));
		tooltip.add(INGREDIENTS_TEXT);
		tooltip.add(new LiteralText(" ").append(this.ingredientsText));
	}
	public Text getBaseSlotDescription() { return this.baseSlotDescriptionText; }
	public Text getAdditionsSlotDescription() { return this.additionsSlotDescriptionText; }
	public List<Identifier> getEmptyBaseSlotTextures() { return this.emptyBaseSlotTextures; }
	public List<Identifier> getEmptyAdditionsSlotTextures() { return this.emptyAdditionsSlotTextures; }
	@Override
	public String getTranslationKey() { return TRANSLATION_KEY; }
}
