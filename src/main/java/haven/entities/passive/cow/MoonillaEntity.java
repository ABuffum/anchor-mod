package haven.entities.passive.cow;

import haven.ModBase;
import haven.blocks.cake.CakeContainer;
import haven.items.buckets.BucketProvided;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class MoonillaEntity extends CowEntity implements FlavoredCowEntity {

	@Override
	public CakeContainer.Flavor getFlavor() { return CakeContainer.Flavor.VANILLA; }

	public MoonillaEntity(EntityType<? extends MoonillaEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, MoonillaEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowcoaEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, CowfeeEntity.class));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, StrawbovineEntity.class));
		this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.WHEAT, ModBase.VANILLA), false));
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) ? 10.0F : world.getBrightness(pos) - 0.5F;
	}

	public static boolean canSpawn(EntityType<MoonillaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) && world.getBaseLightLevel(pos, 0) > 8;
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack != null && !itemStack.isEmpty() && !this.isBaby()) {
			Item item = itemStack.getItem(), outItem = null;
			if (item instanceof BucketProvided bp) outItem = bp.getBucketProvider().getChocolateMilkBucket();
			else if (item == Items.BOWL) outItem = ModBase.VANILLA_MILK_BOWL;
			if (outItem != null) {
				player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
				ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, outItem.getDefaultStack());
				player.setStackInHand(hand, itemStack2);
				return ActionResult.success(this.world.isClient);
			}
		}
		return super.interactMob(player, hand);
	}

	public MoonillaEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModBase.MOONILLA_ENTITY.create(serverWorld);
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass()
				&& otherClass != CowEntity.class
				&& otherClass != CowcoaEntity.class
				&& otherClass != CowfeeEntity.class
				&& otherClass != StrawbovineEntity.class
		) return false;
		else return this.isInLove() && other.isInLove();
	}
}
