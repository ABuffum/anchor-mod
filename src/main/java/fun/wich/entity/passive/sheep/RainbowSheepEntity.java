package fun.wich.entity.passive.sheep;

import fun.wich.ModBase;
import fun.wich.ModId;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RainbowSheepEntity extends SheepEntity implements Shearable {
	public RainbowSheepEntity(EntityType<? extends SheepEntity> entityType, World world) { super(entityType, world); }

	public void sheared(SoundCategory shearedSoundCategory) {
		this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
		this.setSheared(true);
		int i = 1 + this.random.nextInt(3);
		for(int j = 0; j < i; ++j) {
			ItemEntity itemEntity = this.dropItem(ModBase.RAINBOW_WOOL.asItem(), 1);
			if (itemEntity != null) {
				itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1, this.random.nextFloat() * 0.05, (this.random.nextFloat() - this.random.nextFloat()) * 0.1));
			}
		}
	}
	@Override
	public Identifier getLootTableId() {
		if (this.isSheared()) return EntityType.SHEEP.getLootTableId();
		else return ModId.ID("entities/sheep/rainbow");
	}
	@Override
	public DyeColor getColor() { return DyeColor.WHITE; }
	@Override
	public RainbowSheepEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) { return ModBase.RAINBOW_SHEEP_ENTITY.create(serverWorld); }
	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		EntityType<?> type = other.getType();
		if (!(type == EntityType.SHEEP || type == getType())) return false;
		else return this.isInLove() && other.isInLove();
	}
}
