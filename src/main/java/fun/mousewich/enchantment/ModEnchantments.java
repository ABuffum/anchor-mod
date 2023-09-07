package fun.mousewich.enchantment;

import fun.mousewich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

import java.util.List;

import static fun.mousewich.ModBase.EN_US;
import static fun.mousewich.registry.ModRegistry.Register;

public class ModEnchantments {
	public static final Enchantment COMMITTED = new CommittedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment CRUSHING = new ShieldBreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND) {
		@Override public boolean isAcceptableItem(ItemStack stack) { return stack.getItem() instanceof HammerItem; }
	};
	public static final Enchantment EXPERIENCE_SIPHON = new ExperienceSiphonEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment FRENZY = new FrenzyEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.CHEST);
	public static final Enchantment GRAVITY = new GravityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment LEECHING = new LeechingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment RECYLING = new RecyclingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
	public static final Enchantment RUSH = new RushEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET);
	public static final Enchantment SERRATED = new SerratedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment THUNDERING = new ThunderingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment WATER_SHOT = new WaterShotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
	public static final Enchantment WEAKENING = new WeakeningEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND);
	//Minecraft
	public static final Enchantment CLEAVING = new ShieldBreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND) {
		@Override public boolean isAcceptableItem(ItemStack stack) { return stack.getItem() instanceof AxeItem; }
	}; //Combat Test
	public static final Enchantment SWIFT_SNEAK = new SwiftSneakEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.LEGS); //1.19

	public static void RegisterEnchantments() {
		Register("committed", COMMITTED, List.of(EN_US.Committed()));
		Register("crushing", CRUSHING, List.of(EN_US.Crushing()));
		Register("experience_siphon", EXPERIENCE_SIPHON, List.of(EN_US.Siphon(EN_US.Experience())));
		Register("frenzy", FRENZY, List.of(EN_US.Frenzy()));
		Register("gravity", GRAVITY, List.of(EN_US.Gravity()));
		Register("leeching", LEECHING, List.of(EN_US.Leeching()));
		Register("recyling", RECYLING, List.of(EN_US.Recycling()));
		Register("rush", RUSH, List.of(EN_US.Rush()));
		Register("serrated", SERRATED, List.of(EN_US.Serrated()));
		Register("thundering", THUNDERING, List.of(EN_US.Thundering()));
		Register("water_shot", WATER_SHOT, List.of(EN_US.Shot(EN_US.Water())));
		Register("weakening", WEAKENING, List.of(EN_US.Weakening()));
		//Minecraft
		Register("minecraft:cleaving", CLEAVING, List.of(EN_US.Cleaving())); //Dungeons
		Register("minecraft:swift_sneak", SWIFT_SNEAK, List.of(EN_US.Sneak(EN_US.Swift()))); //1.19
	}
}
