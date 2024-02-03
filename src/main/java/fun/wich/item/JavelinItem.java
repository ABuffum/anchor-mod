package fun.wich.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import fun.wich.entity.LastJavelinStoring;
import fun.wich.entity.projectile.JavelinEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class JavelinItem extends Item implements Vanishable {
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	protected final Identifier texture;
	public Identifier getTexture() { return this.texture; }

	protected interface JavelinFactory {
		JavelinEntity get(World world, PlayerEntity playerEntity, ItemStack stack);
	}
	protected JavelinFactory FACTORY = (world, playerEntity, stack) -> new JavelinEntity(world, playerEntity, stack, this);

	public JavelinItem(double attackDamage, float attackSpeed, Identifier texture, Settings settings) {
		super(settings);
		this.texture = texture;
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) { return !miner.isCreative(); }
	@Override
	public UseAction getUseAction(ItemStack stack) { return UseAction.SPEAR; }
	@Override
	public int getMaxUseTime(ItemStack stack) { return 72000; }

	public boolean decrementWhenThrown() { return true; }

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!(user instanceof PlayerEntity playerEntity)) return;
		int i = this.getMaxUseTime(stack) - remainingUseTicks;
		if (i < 10) return;
		int j = EnchantmentHelper.getRiptide(stack);
		if (j > 0 && !playerEntity.isTouchingWaterOrRain()) return;
		if (!world.isClient) {
			stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
			if (j == 0) {
				JavelinEntity entity = FACTORY.get(world, playerEntity, stack);
				entity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f + (float)j * 0.5f, 1.0f);
				if (decrementWhenThrown()) {
					if (playerEntity.getAbilities().creativeMode) {
						entity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
					}
				}
				else entity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
				world.spawnEntity(entity);
				world.playSoundFromEntity(null, entity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
				if (!playerEntity.getAbilities().creativeMode && decrementWhenThrown()) {
					playerEntity.getInventory().removeOne(stack);
				}
				if (playerEntity instanceof LastJavelinStoring lastJavelinStore && entity.shouldStoreLast()) {
					JavelinEntity last = lastJavelinStore.getLastJavelin();
					if (last != null && last.isAlive()) last.discard();
					lastJavelinStore.setLastJavelin(entity);
				}
			}
		}
		playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		if (j > 0) {
			float f = playerEntity.getYaw();
			float g = playerEntity.getPitch();
			float h = -MathHelper.sin(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
			float k = -MathHelper.sin(g * ((float)Math.PI / 180));
			float l = MathHelper.cos(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
			float m = MathHelper.sqrt(h * h + k * k + l * l);
			float n = 3.0f * ((1.0f + (float)j) / 4.0f);
			playerEntity.addVelocity(h *= n / m, k *= n / m, l *= n / m);
			playerEntity.setRiptideTicks(20);
			if (playerEntity.isOnGround()) {
				playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
			}
			SoundEvent soundEvent = j >= 3 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_3 : (j == 2 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_2 : SoundEvents.ITEM_TRIDENT_RIPTIDE_1);
			world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) return TypedActionResult.fail(itemStack);
		if (EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain()) return TypedActionResult.fail(itemStack);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if ((double)state.getHardness(world, pos) != 0.0) {
			stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) return this.attributeModifiers;
		return super.getAttributeModifiers(slot);
	}

	@Override
	public int getEnchantability() { return 1; }

	public boolean acceptsChanneling() { return false; }
	public boolean acceptsImpaling() { return true; }
	public boolean acceptsLoyalty() { return true; }
	public boolean acceptsRiptide() { return false; }
}