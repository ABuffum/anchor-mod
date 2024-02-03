package fun.wich.mixins.block.entity;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor {
	@Accessor("level")
	int GetLevel();
	@Accessor("level")
	void SetLevel(int value);
	@Accessor("minY")
	int GetMinY();
	@Accessor("minY")
	void SetMinY(int value);
	@Accessor("beamSegments")
	List<BeaconBlockEntity.BeamSegment> GetBeamSegments();
	@Accessor("beamSegments")
	void SetBeamSegments(List<BeaconBlockEntity.BeamSegment> value);
	@Accessor("field_19178")
	List<BeaconBlockEntity.BeamSegment> GetSegments();
	@Accessor("field_19178")
	void SetSegments(List<BeaconBlockEntity.BeamSegment> value);

	@Accessor("primary")
	StatusEffect GetPrimary();
	@Accessor("secondary")
	StatusEffect GetSecondary();
}
