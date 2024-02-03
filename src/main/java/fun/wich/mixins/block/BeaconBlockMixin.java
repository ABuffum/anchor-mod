package fun.wich.mixins.block;

import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModStainable;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BeaconBlock.class)
public abstract class BeaconBlockMixin extends BlockWithEntity implements Stainable, ModStainable {
	@Shadow public abstract DyeColor getColor();
	protected BeaconBlockMixin(Settings settings) { super(settings); }

	@Override
	public ModDyeColor GetModColor() { return ModDyeColor.byId(this.getColor().getId()); }
}
