package fun.wich.item.projectile;

import fun.wich.ModFactory;
import fun.wich.entity.projectile.ModArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModArrowItem extends ArrowItem {
	private final ShotFactory shot;
	private final DispensedFactory dispensed;
	public ModArrowItem(ShotFactory shot, DispensedFactory dispensed) { this(shot, dispensed, ModFactory.ItemSettings()); }
	public ModArrowItem(ShotFactory shot, DispensedFactory dispensed, Settings settings) {
		super(settings);
		this.shot = shot;
		this.dispensed = dispensed;
	}
	public interface ShotFactory {
		ModArrowEntity create(World world, LivingEntity shooter);
	}
	public interface DispensedFactory {
		ModArrowEntity create(World world, double x, double y, double z);
	}
	@Override
	public ModArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
		return this.shot.create(world, shooter);
	}
	public ModArrowEntity createArrow(World world, double x, double y, double z) {
		return this.dispensed.create(world, x, y, z);
	}
}