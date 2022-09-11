package haven.entities;

import haven.HavenMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class RainbowSheepEntity extends SheepEntity implements Shearable {
	public RainbowSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
		super(entityType, world);
	}

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		this.setSheared(true);
		int i = 1 + this.random.nextInt(3);
		for(int j = 0; j < i; ++j) {
			ItemEntity itemEntity = this.dropItem(HavenMod.RAINBOW_WOOL.ITEM, 1);
			if (itemEntity != null) {
				itemEntity.setVelocity(itemEntity.getVelocity().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
			}
		}
	}
}
