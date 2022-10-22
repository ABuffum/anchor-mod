package haven.entities;

import java.util.function.Supplier;

public interface ExtraPosedEntity {
	public int GetPose();
	public void SetPose(Supplier<Integer> pose);
	public default boolean IsInPose(Supplier<Integer> pose) { return GetPose() == pose.get(); }
}
