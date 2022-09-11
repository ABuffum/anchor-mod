package haven.util;

import haven.HavenMod;
import haven.items.blood.SyringeItem;
import haven.origins.powers.BloodTypePower;
import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodType {
	private final String name;
	public String getName() { return name; }
	private final Identifier id;
	public Identifier getIdentifier() { return id; }

	public static final Map<Identifier, BloodType> BLOOD_TYPES = new HashMap<Identifier, BloodType>();

	public static final BloodType PLAYER = new BloodType("player");
	public static final BloodType ALLAY = new BloodType("allay");
	public static final BloodType AVIAN = new BloodType("avian");
	public static final BloodType AXOLOTL = new BloodType("axolotl");
	public static final BloodType BAT = new BloodType("bat");
	public static final BloodType BEAR = new BloodType("bear");
	public static final BloodType BEE = new BloodType("bee");
	public static final BloodType BEE_ENDERMAN = new BloodType("bee_enderman") {
		@Override
		public boolean IsWaterVulnerable() { return true; }
		@Override
		public boolean IsPoisonVulnerable() { return false; }
	};
	public static final BloodType CANINE = new BloodType("canine");
	public static final BloodType COW = new BloodType("cow");
	public static final BloodType CREEPER = new BloodType("creeper");
	public static final BloodType DISEASED_FELINE = new BloodType("diseased_feline");
	public static final BloodType DOLPHIN = new BloodType("dolphin");
	public static final BloodType DRAGON = new BloodType("dragon");
	public static final BloodType ENDERMAN = new BloodType("enderman") {
		@Override
		public boolean IsWaterVulnerable() { return true; }
	};
	public static final BloodType EQUINE = new BloodType("equine");
	public static final BloodType FELINE = new BloodType("feline");
	public static final BloodType FISH = new BloodType("fish");
	public static final BloodType GOAT = new BloodType("goat");
	public static final BloodType HONEY = new BloodType("honey");
	public static final BloodType ICHOR = new BloodType("ichor") {
		@Override
		public boolean IsFireVulnerable() { return false; }
	};
	public static final BloodType INSECT = new BloodType("insect");
	public static final BloodType LAVA = new BloodType("lava") {
		@Override
		public boolean IsFireVulnerable() { return false; }
	};
	public static final BloodType LLAMA = new BloodType("llama");
	public static final BloodType MAGMA = new BloodType("magma") {
		@Override
		public boolean IsFireVulnerable() { return false; }
	};
	public static final BloodType NEPHAL = new BloodType("nephal");
	public static final BloodType NETHER = new BloodType("nether") {
		@Override
		public boolean IsFireVulnerable() { return false; }
		@Override
		public boolean IsWaterVulnerable() { return true; }
	};
	public static final BloodType PHANTOM = new BloodType("phantom");
	public static final BloodType PIG = new BloodType("pig");
	public static final BloodType RABBIT = new BloodType("rabbit");
	public static final BloodType RAVAGER = new BloodType("ravager");
	public static final BloodType SHEEP = new BloodType("sheep");
	public static final BloodType SLIME = new BloodType("slime");
	public static final BloodType SLUDGE = new BloodType("sludge") {
		@Override
		public boolean IsPoisonVulnerable() { return false; }
		@Override
		public boolean IsWitherVulnerable() { return false; }
	};
	public static final BloodType SPIDER = new BloodType("spider");
	public static final BloodType SQUID = new BloodType("squid");
	public static final BloodType STRIDER = new BloodType("strider") {
		@Override
		public boolean IsFireVulnerable() { return false; }
		@Override
		public boolean IsWaterVulnerable() { return true; }
	};
	public static final BloodType SUGAR_WATER = new BloodType("sugar_water");
	public static final BloodType TURTLE = new BloodType("turtle");
	public static final BloodType VAMPIRE = new BloodType("vampire");
	public static final BloodType VEX = new BloodType("vex");
	public static final BloodType VILLAGER = new BloodType("villager");
	public static final BloodType WARDEN = new BloodType("warden");
	public static final BloodType WATER = new BloodType("water");
	public static final BloodType ZOMBIE = new BloodType("zombie");
	public static final BloodType NONE = new BloodType("none");

	public BloodType(String name) {
		this.name = name;
		id = HavenMod.ID(name);
		BLOOD_TYPES.put(id, this);
	}

	public static BloodType Get(LivingEntity entity) {
		List<BloodTypePower> powers = PowerHolderComponent.KEY.get(entity).getPowers(BloodTypePower.class);
		if (!powers.isEmpty()) {
			boolean active = false;
			BloodType bloodType = null;
			for (BloodTypePower power : powers) {
				if (power.isActive()) {
					if (active)
						Apoli.LOGGER.warn("Entity " + entity.getDisplayName().toString() + " has more than one instance of BloodTypePower.");
					active = true;
					bloodType = BLOOD_TYPES.get(power.bloodType);
				}
			}
			if (bloodType != null) return bloodType;
		}
		if (entity instanceof PlayerEntity) return PLAYER;
		else if (entity instanceof ChickenEntity || entity instanceof ParrotEntity) return AVIAN;
		else if (entity instanceof AxolotlEntity) return AXOLOTL;
		else if (entity instanceof BatEntity) return BAT;
		else if (entity instanceof PolarBearEntity || entity instanceof PandaEntity) return BEAR;
		else if (entity instanceof RabbitEntity) return RABBIT;
		else if (entity instanceof FoxEntity || entity instanceof WolfEntity) return CANINE;
		else if (entity instanceof CowEntity) return COW;
		else if (entity instanceof CreeperEntity) return CREEPER;
		else if (entity instanceof DolphinEntity) return DOLPHIN;
		else if (entity instanceof EnderDragonEntity) return DRAGON;
		else if (entity instanceof EndermanEntity) return ENDERMAN;
		else if (entity instanceof LlamaEntity) return LLAMA;
		else if (entity instanceof HorseBaseEntity) return EQUINE;
		else if (entity instanceof CatEntity || entity instanceof OcelotEntity) return FELINE;
		else if (entity instanceof FishEntity || entity instanceof GuardianEntity) return FISH;
		else if (entity instanceof GoatEntity) return GOAT;
		else if (entity instanceof EndermiteEntity || entity instanceof SilverfishEntity) return INSECT;
		else if (entity instanceof MagmaCubeEntity) return MAGMA;
		else if (entity instanceof PhantomEntity) return PHANTOM;
		else if (entity instanceof PigEntity || entity instanceof AbstractPiglinEntity
				|| entity instanceof ZombifiedPiglinEntity || entity instanceof HoglinEntity
				|| entity instanceof ZoglinEntity) return PIG;
		else if (entity instanceof RavagerEntity) return RAVAGER;
		else if (entity instanceof SheepEntity) return SHEEP;
		else if (entity instanceof SlimeEntity) return SLIME;
		else if (entity instanceof SpiderEntity) return SPIDER;
		else if (entity instanceof SnowGolemEntity) return WATER;
		else if (entity instanceof SquidEntity) return SQUID;
		else if (entity instanceof StriderEntity) return STRIDER;
		else if (entity instanceof TurtleEntity) return TURTLE;
		else if (entity instanceof VexEntity) return VEX;
		else if (entity instanceof VillagerEntity || entity instanceof PillagerEntity || entity instanceof WitchEntity) return VILLAGER;
		else if (entity instanceof ZombieEntity || entity instanceof GiantEntity) return ZOMBIE;
		else return NONE;
	}

	public static Item GetSyringe(LivingEntity livingEntity) { return GetSyringe(Get(livingEntity)); }
	public static Item GetSyringe(BloodType type) {
		if (type == PLAYER) return HavenMod.BLOOD_SYRINGE;
		else if (type == ALLAY) return HavenMod.ALLAY_BLOOD_SYRINGE;
		else if (type == AVIAN) return HavenMod.AVIAN_BLOOD_SYRINGE;
		else if (type == AXOLOTL) return HavenMod.AXOLOTL_BLOOD_SYRINGE;
		else if (type == BAT) return HavenMod.BAT_BLOOD_SYRINGE;
		else if (type == BEAR) return HavenMod.BEAR_BLOOD_SYRINGE;
		else if (type == BEE) return HavenMod.BEE_BLOOD_SYRINGE;
		else if (type == BEE_ENDERMAN) return HavenMod.BEE_ENDERMAN_BLOOD_SYRINGE;
		else if (type == CANINE) return HavenMod.CANINE_BLOOD_SYRINGE;
		else if (type == COW) return HavenMod.COW_BLOOD_SYRINGE;
		else if (type == CREEPER) return HavenMod.CREEPER_BLOOD_SYRINGE;
		else if (type == DISEASED_FELINE) return HavenMod.DISEASED_FELINE_BLOOD_SYRINGE;
		else if (type == DOLPHIN) return HavenMod.DOLPHIN_BLOOD_SYRINGE;
		else if (type == DRAGON) return HavenMod.DRAGON_BLOOD_SYRINGE;
		else if (type == ENDERMAN) return HavenMod.ENDERMAN_BLOOD_SYRINGE;
		else if (type == EQUINE) return HavenMod.EQUINE_BLOOD_SYRINGE;
		else if (type == FELINE) return HavenMod.FELINE_BLOOD_SYRINGE;
		else if (type == FISH) return HavenMod.FISH_BLOOD_SYRINGE;
		else if (type == GOAT) return HavenMod.GOAT_BLOOD_SYRINGE;
		else if (type == HONEY) return HavenMod.HONEY_SYRINGE;
		else if (type == ICHOR) return HavenMod.ICHOR_SYRINGE;
		else if (type == INSECT) return HavenMod.INSECT_BLOOD_SYRINGE;
		else if (type == LAVA) return HavenMod.LAVA_SYRINGE;
		else if (type == LLAMA) return HavenMod.LLAMA_BLOOD_SYRINGE;
		else if (type == MAGMA) return HavenMod.MAGMA_CREAM_SYRINGE;
		else if (type == NEPHAL) return HavenMod.NEPHAL_BLOOD_SYRINGE;
		else if (type == NETHER) return HavenMod.NETHER_BLOOD_SYRINGE;
		else if (type == PHANTOM) return HavenMod.PHANTOM_BLOOD_SYRINGE;
		else if (type == PIG) return HavenMod.PIG_BLOOD_SYRINGE;
		else if (type == RABBIT) return HavenMod.RABBIT_BLOOD_SYRINGE;
		else if (type == RAVAGER) return HavenMod.RAVAGER_BLOOD_SYRINGE;
		else if (type == SHEEP) return HavenMod.SHEEP_BLOOD_SYRINGE;
		else if (type == SLIME) return HavenMod.SLIME_SYRINGE;
		else if (type == SLUDGE) return HavenMod.SLUDGE_SYRINGE;
		else if (type == SPIDER) return HavenMod.SPIDER_BLOOD_SYRINGE;
		else if (type == SQUID) return HavenMod.SQUID_BLOOD_SYRINGE;
		else if (type == STRIDER) return HavenMod.STRIDER_BLOOD_SYRINGE;
		else if (type == SUGAR_WATER) return HavenMod.SUGAR_WATER_SYRINGE;
		else if (type == TURTLE) return HavenMod.TURTLE_BLOOD_SYRINGE;
		else if (type == VAMPIRE) return HavenMod.VAMPIRE_BLOOD_SYRINGE;
		else if (type == VEX) return HavenMod.VEX_BLOOD_SYRINGE;
		else if (type == VILLAGER) return HavenMod.VILLAGER_BLOOD_SYRINGE;
		else if (type == WARDEN) return HavenMod.WARDEN_BLOOD_SYRINGE;
		else if (type == WATER) return HavenMod.WATER_SYRINGE;
		else if (type == ZOMBIE) return HavenMod.ZOMBIE_BLOOD_SYRINGE;
		return HavenMod.DIRTY_SYRINGE;
	}

	public boolean IsWaterVulnerable() { return false; }
	public boolean IsFireVulnerable() { return true; }
	public boolean IsPoisonVulnerable() { return true; }
	public boolean IsWitherVulnerable() { return true; }
}
