package fun.wich.util.dye;

import net.minecraft.entity.passive.AnimalEntity;

public interface ModDyedSheep {
	ModDyeColor GetModColor();
	void SetModColor(ModDyeColor color);
	ModDyeColor GetChildModColor(AnimalEntity firstParent, AnimalEntity secondParent);
}
