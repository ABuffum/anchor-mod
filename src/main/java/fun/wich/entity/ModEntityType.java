package fun.wich.entity;

import fun.wich.entity.cloud.ConfettiCloudEntity;
import fun.wich.entity.hostile.PiranhaEntity;
import fun.wich.entity.hostile.RedPhantomEntity;
import fun.wich.entity.hostile.SlimeCreeperEntity;
import fun.wich.entity.hostile.illager.IceologerEntity;
import fun.wich.entity.hostile.illager.MageEntity;
import fun.wich.entity.hostile.illager.MountaineerEntity;
import fun.wich.entity.hostile.skeleton.MossySkeletonEntity;
import fun.wich.entity.hostile.skeleton.SlimySkeletonEntity;
import fun.wich.entity.hostile.skeleton.SunkenSkeletonEntity;
import fun.wich.entity.hostile.slime.PinkSlimeEntity;
import fun.wich.entity.hostile.slime.TropicalSlimeEntity;
import fun.wich.entity.hostile.spider.BoneSpiderEntity;
import fun.wich.entity.hostile.spider.IcySpiderEntity;
import fun.wich.entity.hostile.spider.JumpingSpiderEntity;
import fun.wich.entity.hostile.spider.SlimeSpiderEntity;
import fun.wich.entity.hostile.warden.WardenEntity;
import fun.wich.entity.hostile.zombie.FrozenZombieEntity;
import fun.wich.entity.hostile.zombie.JungleZombieEntity;
import fun.wich.entity.hostile.zombie.SlimeZombieEntity;
import fun.wich.entity.neutral.golem.MelonGolemEntity;
import fun.wich.entity.passive.*;
import fun.wich.entity.passive.camel.CamelEntity;
import fun.wich.entity.passive.chicken.FancyChickenEntity;
import fun.wich.entity.passive.chicken.SlimeChickenEntity;
import fun.wich.entity.passive.cow.*;
import fun.wich.entity.passive.frog.FrogEntity;
import fun.wich.entity.passive.frog.TadpoleEntity;
import fun.wich.entity.passive.sheep.MossySheepEntity;
import fun.wich.entity.passive.sheep.RainbowSheepEntity;
import fun.wich.entity.passive.sniffer.SnifferEntity;
import fun.wich.entity.projectile.*;
import fun.wich.entity.tnt.PowderKegEntity;
import fun.wich.entity.vehicle.ChestBoatEntity;
import fun.wich.entity.vehicle.DispenserMinecartEntity;
import fun.wich.entity.vehicle.ModBoatEntity;
import fun.wich.entity.vehicle.ModChestBoatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class ModEntityType {
	//<editor-fold desc="Vehicles">
	public static final EntityType<ModBoatEntity> MOD_BOAT_ENTITY = FabricEntityTypeBuilder.<ModBoatEntity>create(SpawnGroup.MISC, ModBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).trackRangeChunks(10).build();
	public static final EntityType<ChestBoatEntity> CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	public static final EntityType<ModChestBoatEntity> MOD_CHEST_BOAT_ENTITY = FabricEntityTypeBuilder.<ModChestBoatEntity>create(SpawnGroup.MISC, ModChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).trackRangeChunks(10).build();
	public static final EntityType<DispenserMinecartEntity> DISPENSER_MINECART_ENTITY = FabricEntityTypeBuilder.<DispenserMinecartEntity>create(SpawnGroup.MISC, DispenserMinecartEntity::new).dimensions(EntityDimensions.fixed(0.98f, 0.7f)).trackRangeChunks(8).build();
	//</editor-fold>
	//<editor-fold desc="Block Entities">
	public static final EntityType<IceChunkEntity> ICE_CHUNK_ENTITY = FabricEntityTypeBuilder.<IceChunkEntity>create(SpawnGroup.MISC, IceChunkEntity::new).dimensions(EntityDimensions.fixed(2f, 1.5f)).trackRangeChunks(8).build();
	public static final EntityType<PowderKegEntity> POWDER_KEG_ENTITY = FabricEntityTypeBuilder.<PowderKegEntity>create(SpawnGroup.MISC, PowderKegEntity::new).dimensions(EntityDimensions.fixed(0.98F, 0.98F)).fireImmune().trackRangeChunks(10).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Projectile">
	public static final EntityType<BoneShardEntity> BONE_SHARD_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<BoneShardEntity>create(SpawnGroup.MISC, BoneShardEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	public static final EntityType<BottledConfettiEntity> BOTTLED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<BottledConfettiEntity>create(SpawnGroup.MISC, BottledConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<BottledLightningEntity> BOTTLED_LIGHTNING_ENTITY = FabricEntityTypeBuilder.<BottledLightningEntity>create(SpawnGroup.MISC, BottledLightningEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<DroppedConfettiEntity> DROPPED_CONFETTI_ENTITY = FabricEntityTypeBuilder.<DroppedConfettiEntity>create(SpawnGroup.MISC, DroppedConfettiEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<DroppedDragonBreathEntity> DROPPED_DRAGON_BREATH_ENTITY = FabricEntityTypeBuilder.<DroppedDragonBreathEntity>create(SpawnGroup.MISC, DroppedDragonBreathEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<MelonSeedProjectileEntity> MELON_SEED_PROJECTILE_ENTITY = FabricEntityTypeBuilder.<MelonSeedProjectileEntity>create(SpawnGroup.MISC, MelonSeedProjectileEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<PinkSlimeBallEntity> PINK_SLIME_BALL_ENTITY = FabricEntityTypeBuilder.<PinkSlimeBallEntity>create(SpawnGroup.MISC, PinkSlimeBallEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<PurpleEyeOfEnderEntity> PURPLE_EYE_OF_ENDER_ENTITY = FabricEntityTypeBuilder.<PurpleEyeOfEnderEntity>create(SpawnGroup.MISC, PurpleEyeOfEnderEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(4).build();
	public static final EntityType<SlowingSnowballEntity> SLOWING_SNOWBALL_ENTITY = FabricEntityTypeBuilder.<SlowingSnowballEntity>create(SpawnGroup.MISC, SlowingSnowballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeChunks(4).trackedUpdateRate(10).build();
	public static final EntityType<ThrownTomatoEntity> THROWABLE_TOMATO_ENTITY = FabricEntityTypeBuilder.<ThrownTomatoEntity>create(SpawnGroup.MISC, ThrownTomatoEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeChunks(4).trackedUpdateRate(10).build();
	//</editor-fold>
	//<editor-fold desc="Tridents">
	public static final EntityType<JavelinEntity> JAVELIN_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, JavelinEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	public static final EntityType<JavelinEntity> AMETHYST_TRIDENT_ENTITY = FabricEntityTypeBuilder.<JavelinEntity>create(SpawnGroup.MISC, AmethystTridentEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(4).trackedUpdateRate(20).build();
	//</editor-fold>
	//<editor-fold desc="Cloud">
	public static final EntityType<ConfettiCloudEntity> CONFETTI_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();
	public static final EntityType<ConfettiCloudEntity> DRAGON_BREATH_CLOUD_ENTITY = FabricEntityTypeBuilder.<ConfettiCloudEntity>create(SpawnGroup.MISC, ConfettiCloudEntity::new).build();
	//</editor-fold>

	//<editor-fold desc="Backport Mobs">
	public static final EntityType<CamelEntity> CAMEL_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CamelEntity::new).dimensions(EntityDimensions.fixed(1.7f, 2.375f)).trackRangeChunks(10).build();
	public static final EntityType<FrogEntity> FROG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FrogEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(10).build();
	public static final EntityType<TadpoleEntity> TADPOLE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TadpoleEntity::new).dimensions(EntityDimensions.fixed(TadpoleEntity.WIDTH, TadpoleEntity.HEIGHT)).trackRangeChunks(10).build();
	public static final EntityType<SnifferEntity> SNIFFER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnifferEntity::new).dimensions(EntityDimensions.fixed(1.9f, 1.75f)).trackRangeChunks(10).build();
	public static final EntityType<WardenEntity> WARDEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WardenEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.9f)).trackRangeChunks(16).fireImmune().build();
	//</editor-fold>
	//<editor-fold desc="Mod Mobs">
	public static final EntityType<BlueMooshroomEntity> BLUE_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlueMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final EntityType<BoneSpiderEntity> BONE_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BoneSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final EntityType<FancyChickenEntity> FANCY_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FancyChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeChunks(10).build();
	public static final EntityType<FrozenZombieEntity> FROZEN_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<FrozenZombieEntity>create(SpawnGroup.MONSTER, FrozenZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<HedgehogEntity> HEDGEHOG_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HedgehogEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.45F)).trackRangeChunks(8).build();
	public static final EntityType<IceologerEntity> ICEOLOGER_ENTITY = FabricEntityTypeBuilder.<IceologerEntity>create(SpawnGroup.MONSTER, IceologerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<IcySpiderEntity> ICY_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IcySpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final EntityType<JollyLlamaEntity> JOLLY_LLAMA_ENTITY = FabricEntityTypeBuilder.<JollyLlamaEntity>create(SpawnGroup.CREATURE, JollyLlamaEntity::new).dimensions(EntityDimensions.fixed(0.9f, 1.87f)).trackRangeChunks(10).build();
	public static final EntityType<JumpingSpiderEntity> JUMPING_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, JumpingSpiderEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.5f)).trackRangeChunks(8).build();
	public static final EntityType<JungleZombieEntity> JUNGLE_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<JungleZombieEntity>create(SpawnGroup.MONSTER, JungleZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<MageEntity> MAGE_ENTITY = FabricEntityTypeBuilder.<MageEntity>create(SpawnGroup.MONSTER, MageEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<MelonGolemEntity> MELON_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MelonGolemEntity::new).dimensions(EntityType.SNOW_GOLEM.getDimensions()).trackRangeChunks(8).build();
	public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final EntityType<MooblossomEntity> MOOBLOSSOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MooblossomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final EntityType<MoolipEntity> MOOLIP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoolipEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final EntityType<MossySheepEntity> MOSSY_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MossySheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeChunks(10).build();
	public static final EntityType<MossySkeletonEntity> MOSSY_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MossySkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final EntityType<MountaineerEntity> MOUNTAINEER_ENTITY = FabricEntityTypeBuilder.<MountaineerEntity>create(SpawnGroup.MONSTER, MountaineerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<NetherMooshroomEntity> NETHER_MOOSHROOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NetherMooshroomEntity::new).dimensions(EntityDimensions.fixed(0.9F, 1.4F)).trackRangeChunks(10).build();
	public static final EntityType<PinkSlimeEntity> PINK_SLIME_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PinkSlimeEntity::new).dimensions(EntityDimensions.changing(2.04f, 2.04f)).trackRangeChunks(10).build();
	public static final EntityType<PiranhaEntity> PIRANHA_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT, PiranhaEntity::new).dimensions(EntityDimensions.fixed(0.7F, 0.7F)).trackRangeChunks(4).build();
	public static final EntityType<RaccoonEntity> RACCOON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RaccoonEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeChunks(8).build();
	public static final EntityType<RainbowSheepEntity> RAINBOW_SHEEP_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainbowSheepEntity::new).dimensions(EntityType.SHEEP.getDimensions()).trackRangeChunks(10).build();
	public static final EntityType<RedPandaEntity> RED_PANDA_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RedPandaEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeChunks(8).build();
	public static final EntityType<RedPhantomEntity> RED_PHANTOM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RedPhantomEntity::new).dimensions(EntityDimensions.fixed(0.9f, 0.5f)).trackRangeChunks(8).build();
	public static final EntityType<SlimeChickenEntity> SLIME_CHICKEN_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeChickenEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.7F)).trackRangeChunks(10).build();
	public static final EntityType<SlimeCowEntity> SLIME_COW_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeCowEntity::new).dimensions(EntityDimensions.fixed(0.9f, 1.4f)).trackRangeChunks(10).build();
	public static final EntityType<SlimeCreeperEntity> SLIME_CREEPER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeCreeperEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.7f)).trackRangeChunks(8).build();
	public static final EntityType<SlimeHorseEntity> SLIME_HORSE_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeHorseEntity::new).dimensions(EntityDimensions.fixed(1.3964844f, 1.6f)).trackRangeChunks(10).build();
	public static final EntityType<SlimeSpiderEntity> SLIME_SPIDER_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeSpiderEntity::new).dimensions(EntityDimensions.fixed(1.4f, 0.9f)).trackRangeChunks(8).build();
	public static final EntityType<SlimeZombieEntity> SLIME_ZOMBIE_ENTITY = FabricEntityTypeBuilder.<SlimeZombieEntity>create(SpawnGroup.MONSTER, SlimeZombieEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).trackRangeChunks(8).build();
	public static final EntityType<SlimySkeletonEntity> SLIMY_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimySkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final EntityType<SunkenSkeletonEntity> SUNKEN_SKELETON_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SunkenSkeletonEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.6f)).trackRangeChunks(10).build();
	public static final EntityType<TropicalSlimeEntity> TROPICAL_SLIME_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropicalSlimeEntity::new).dimensions(EntityDimensions.changing(2.04f, 2.04f)).trackRangeChunks(10).build();
	//</editor-fold>
}
