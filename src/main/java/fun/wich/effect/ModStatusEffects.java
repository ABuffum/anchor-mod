package fun.wich.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

import java.util.List;

import static fun.wich.ModBase.EN_US;
import static fun.wich.registry.ModRegistry.Register;

public class ModStatusEffects {
	public static final StatusEffect BOO = new ModStatusEffect(StatusEffectType.NEUTRAL,0xBE331F);
	public static final StatusEffect DARKNESS = new ModStatusEffect(StatusEffectType.HARMFUL, 2696993); //Minecraft 1.19
	public static final StatusEffect BLEEDING = new BleedingEffect().milkImmune();
	public static final StatusEffect DENSE = new ModStatusEffect(StatusEffectType.BENEFICIAL, 0x8D989A)
			.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF5",
					0.5f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF6",
					1, EntityAttributeModifier.Operation.ADDITION); //Minecraft Dungeons
	public static final StatusEffect FLASHBANGED = new ModStatusEffect(StatusEffectType.HARMFUL, 0xFFFFFF);
	public static final StatusEffect FREEZING_RESISTANCE = new ModStatusEffect(StatusEffectType.BENEFICIAL, 14981690); //Minecraft Dungeons
	public static final StatusEffect FRENZIED = new ModStatusEffect(StatusEffectType.BENEFICIAL, 0xBE371F)
			.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF4",
					0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL); //Minecraft Dungeons
	public static final StatusEffect FROZEN = new ModStatusEffect(StatusEffectType.HARMFUL, 0x74FBFF)
			.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF6",
					-0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF7",
					-0.2f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL); //Minecraft Dungeons
	public static final StatusEffect KILLJOY = new ModStatusEffect(StatusEffectType.NEUTRAL,0x1F8B33);
	public static final StatusEffect OAKWOOD_ARMOR = new ModStatusEffect(StatusEffectType.BENEFICIAL, 0x513525)
			.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF7",
					2, EntityAttributeModifier.Operation.ADDITION); //Minecraft Dungeons
	public static final StatusEffect SILENT = new ModStatusEffect(StatusEffectType.NEUTRAL, 0x7F7F7F);
	public static final StatusEffect STICKY = new StickyEffect();
	public static final StatusEffect SWEET_BREW = new ModStatusEffect(StatusEffectType.BENEFICIAL, 0xAA3384);
	//Goggles
	public static final StatusEffect RUBY_GOGGLES = new GogglesEffect(0xA00A34).milkImmune();
	public static final StatusEffect SAPPHIRE_GOGGLES = new GogglesEffect(0x1870C7).milkImmune();
	public static final StatusEffect TINTED_GOGGLES = new GogglesEffect(0x6F4FAB).milkImmune();

	public static void RegisterEffects() {
		Register("flashbanged", FLASHBANGED, List.of(EN_US.Flashbanged()));
		Register("bleeding", BLEEDING, List.of(EN_US.Bleeding()));
		Register("silent", SILENT, List.of(EN_US.Silent()));
		Register("sticky", STICKY, List.of(EN_US.Sticky()));
		//Throwable Tomato
		Register("boo", BOO, List.of(EN_US.Boo()));
		Register("killjoy", KILLJOY, List.of(EN_US.Killjoy()));
		//Goggles
		Register("tinted_goggles", TINTED_GOGGLES, List.of(EN_US.Goggles(EN_US.Tinted())));
		Register("ruby_goggles", RUBY_GOGGLES, List.of(EN_US.Goggles(EN_US.Ruby())));
		Register("sapphire_goggles", SAPPHIRE_GOGGLES, List.of(EN_US.Goggles(EN_US.Sapphire())));
		//Minecraft
		Register("minecraft:darkness", DARKNESS, List.of(EN_US.Darkness())); //1.19
		Register("dense", DENSE, List.of(EN_US.Dense())); //Dungeons
		Register("freezing_resistance", FREEZING_RESISTANCE, List.of(EN_US.Resistance(EN_US.Freezing()))); //Dungeons
		Register("frenzied", FRENZIED, List.of(EN_US.Frenzied())); //Dungeons
		Register("frozen", FROZEN, List.of(EN_US.Frozen())); //Dungeons
		Register("oakwood_armor", OAKWOOD_ARMOR, List.of(EN_US.Armor(EN_US.Oakwood()))); //Dungeons
		Register("sweet_brew", SWEET_BREW, List.of(EN_US.Brew(EN_US.Sweet()))); //Dungeons
	}
}
