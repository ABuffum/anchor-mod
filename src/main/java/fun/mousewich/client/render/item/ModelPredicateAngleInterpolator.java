package fun.mousewich.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class ModelPredicateAngleInterpolator {
	double value;
	public double getValue() { return value; }
	private double speed;
	private long lastUpdateTime;

	public ModelPredicateAngleInterpolator() { }

	public boolean shouldUpdate(long time) { return this.lastUpdateTime != time; }

	public void update(long time, double target) {
		this.lastUpdateTime = time;
		double d = target - this.value;
		d = MathHelper.floorMod(d + 0.5, 1.0) - 0.5;
		this.speed += d * 0.1;
		this.speed *= 0.8;
		this.value = MathHelper.floorMod(this.value + this.speed, 1.0);
	}
}
