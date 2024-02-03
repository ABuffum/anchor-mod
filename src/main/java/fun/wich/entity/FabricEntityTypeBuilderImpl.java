package fun.wich.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class FabricEntityTypeBuilderImpl<T extends Entity> extends FabricEntityTypeBuilder<T> {
	public FabricEntityTypeBuilderImpl(SpawnGroup spawnGroup, EntityType.EntityFactory<T> factory) {
		super(spawnGroup, factory);
	}
}
