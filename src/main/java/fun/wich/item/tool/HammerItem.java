package fun.wich.item.tool;

import fun.wich.ModFactory;
import fun.wich.gen.data.loot.DropTable;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.material.ModToolMaterials;
import fun.wich.util.CrackedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HammerItem extends MiningToolItem {
	public static final Set<Enchantment> DISALLOWED_ENCHANTMENTS = new HashSet<>(Set.of(Enchantments.FORTUNE, Enchantments.SILK_TOUCH));
	public static Map<Block, DropTable> HAMMER_DROPS = new HashMap<>();

	public HammerItem(ModToolMaterials material) { this(material, material.getHammerDamage(), material.getHammerSpeed()); }
	public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public HammerItem(ModToolMaterials material, Settings settings) { this(material, material.getHammerDamage(), material.getHammerSpeed(), settings); }
	public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(attackDamage, attackSpeed, material, ModBlockTags.HAMMER_MINEABLE, settings.maxDamageIfAbsent(material.getDurability() * 4));
	}

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		Block block = state.getBlock();
		return !state.isIn(ModBlockTags.HAMMER_MINEABLE) && !HAMMER_DROPS.containsKey(block) ? super.getMiningSpeedMultiplier(stack, state) : this.miningSpeed;
	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		if (CrackedBlocks.Crack(world, blockPos)) {
			PlayerEntity playerEntity = context.getPlayer();
			if (playerEntity != null) {
				context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	}
}
