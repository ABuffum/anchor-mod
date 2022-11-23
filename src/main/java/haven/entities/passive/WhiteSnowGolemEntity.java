package haven.entities.passive;

import haven.ModBase;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class WhiteSnowGolemEntity extends SnowGolemEntity implements Shearable {

	public WhiteSnowGolemEntity(EntityType<? extends SnowGolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		if (!this.world.isClient()) {
			this.setHasPumpkin(false);
			this.dropStack(new ItemStack(ModBase.WHITE_PUMPKIN.getCarved().getItem()), 1.7F);
		}
	}
}
