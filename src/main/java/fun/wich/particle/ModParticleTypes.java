package fun.wich.particle;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;

import static fun.wich.registry.ModRegistry.Register;

public class ModParticleTypes {
	//<editor-fold desc="Torch">
	public static final DefaultParticleType COPPER_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType ENDER_FIRE_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType GOLD_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType IRON_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType NETHERITE_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType PRISMARINE_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType SMALL_ENDER_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType SMALL_NETHERITE_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType SMALL_SOUL_FLAME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType UNDERWATER_TORCH_GLOW = FabricParticleTypes.simple(false);
	//</editor-fold>
	//<editor-fold desc="Blood (Bleeding Obsidian)">
	public static final DefaultParticleType LANDING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_OBSIDIAN_BLOOD = FabricParticleTypes.simple(false);
	//</editor-fold>
	//<editor-fold desc="Blood (Liquid)">
	public static final DefaultParticleType BLOOD_BUBBLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType BLOOD_SPLASH = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_BLOOD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_DRIPSTONE_BLOOD = FabricParticleTypes.simple(false);
	//</editor-fold>
	public static final DefaultParticleType CHERRY_LEAVES = FabricParticleTypes.simple(false);
	//<editor-fold desc="Sculk">
	public static final DefaultParticleType SCULK_SOUL = FabricParticleTypes.simple(false);
	public static final ParticleType<SculkChargeParticleEffect> SCULK_CHARGE = new ParticleType<>(true, SculkChargeParticleEffect.FACTORY) { @Override public Codec<SculkChargeParticleEffect> getCodec() { return SculkChargeParticleEffect.CODEC; } };
	public static final DefaultParticleType SCULK_CHARGE_POP = FabricParticleTypes.simple(true);
	public static final ParticleType<ShriekParticleEffect> SHRIEK = new ParticleType<>(false, ShriekParticleEffect.FACTORY) { @Override public Codec<ShriekParticleEffect> getCodec() { return ShriekParticleEffect.CODEC; } };
	//Warden
	public static final DefaultParticleType SONIC_BOOM = FabricParticleTypes.simple(true);
	//Sniffer
	public static final DefaultParticleType EGG_CRACK = FabricParticleTypes.simple(false);
	//</editor-fold>
	//<editor-fold desc="Slime">
	public static final DefaultParticleType ITEM_BLUE_SLIME = FabricParticleTypes.simple(false);
	public static final DefaultParticleType ITEM_PINK_SLIME = FabricParticleTypes.simple(false);
	//</editor-fold>
	//<editor-fold desc="Mud (Liquid)">
	public static final DefaultParticleType MUD_BUBBLE = FabricParticleTypes.simple(false);
	public static final DefaultParticleType MUD_SPLASH = FabricParticleTypes.simple(false);
	public static final DefaultParticleType DRIPPING_MUD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_MUD = FabricParticleTypes.simple(false);
	public static final DefaultParticleType FALLING_DRIPSTONE_MUD = FabricParticleTypes.simple(false);
	//</editor-fold>
	public static final DefaultParticleType TOMATO = FabricParticleTypes.simple();

	public static void RegisterParticles() {
		//<editor-fold desc="Torch">
		Register("copper_flame", ModParticleTypes.COPPER_FLAME);
		Register("ender_fire_flame", ModParticleTypes.ENDER_FIRE_FLAME);
		Register("gold_flame", ModParticleTypes.GOLD_FLAME);
		Register("iron_flame", ModParticleTypes.IRON_FLAME);
		Register("netherite_flame", ModParticleTypes.NETHERITE_FLAME);
		Register("prismarine_flame", ModParticleTypes.PRISMARINE_FLAME);
		Register("small_ender_flame", ModParticleTypes.SMALL_ENDER_FLAME);
		Register("small_netherite_flame", ModParticleTypes.SMALL_NETHERITE_FLAME);
		Register("small_soul_flame", ModParticleTypes.SMALL_SOUL_FLAME);
		Register("glow_flame", ModParticleTypes.UNDERWATER_TORCH_GLOW);
		//</editor-fold>
		//<editor-fold desc="Blood (Bleeding Obsidian)">
		Register("landing_obsidian_blood", ModParticleTypes.LANDING_OBSIDIAN_BLOOD);
		Register("falling_obsidian_blood", ModParticleTypes.FALLING_OBSIDIAN_BLOOD);
		Register("dripping_obsidian_blood", ModParticleTypes.DRIPPING_OBSIDIAN_BLOOD);
		//</editor-fold>
		//<editor-fold desc="Blood (Liquid)">
		Register("blood_bubble", ModParticleTypes.BLOOD_BUBBLE);
		Register("blood_splash", ModParticleTypes.BLOOD_SPLASH);
		Register("dripping_blood", ModParticleTypes.DRIPPING_BLOOD);
		Register("falling_blood", ModParticleTypes.FALLING_BLOOD);
		Register("falling_dripstone_blood", ModParticleTypes.FALLING_DRIPSTONE_BLOOD);
		//</editor-fold>
		Register("minecraft:cherry_leaves", ModParticleTypes.CHERRY_LEAVES);
		//<editor-fold desc="Sculk">
		Register("minecraft:sculk_soul", ModParticleTypes.SCULK_SOUL);
		Register("minecraft:sculk_charge", ModParticleTypes.SCULK_CHARGE);
		Register("minecraft:sculk_charge_pop", ModParticleTypes.SCULK_CHARGE_POP);
		Register("minecraft:shriek", ModParticleTypes.SHRIEK);
		//Warden
		Register("minecraft:sonic_boom", ModParticleTypes.SONIC_BOOM);
		//Sniffer
		Register("minecraft:egg_crack", ModParticleTypes.EGG_CRACK);
		//</editor-fold>
		//<editor-fold desc="Slime">
		Register("item_blue_slime", ModParticleTypes.ITEM_BLUE_SLIME);
		Register("item_pink_slime", ModParticleTypes.ITEM_PINK_SLIME);
		//</editor-fold>
		//<editor-fold desc="Mud (Liquid)">
		Register("mud_bubble", ModParticleTypes.MUD_BUBBLE);
		Register("mud_splash", ModParticleTypes.MUD_SPLASH);
		Register("dripping_mud", ModParticleTypes.DRIPPING_MUD);
		Register("falling_mud", ModParticleTypes.FALLING_MUD);
		Register("falling_dripstone_mud", ModParticleTypes.FALLING_DRIPSTONE_MUD);
		//</editor-fold>
		Register("thrown_tomato", ModParticleTypes.TOMATO);
	}
}
