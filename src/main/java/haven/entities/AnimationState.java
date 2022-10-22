package haven.entities;

import net.minecraft.util.math.MathHelper;

import java.util.function.Consumer;

public class AnimationState {
	private static final long STOPPED = Long.MAX_VALUE;
	private long updatedAt = STOPPED;
	private long timeRunning;

	public void start(int age) {
		this.updatedAt = (long)age * 1000L / 20L;
		this.timeRunning = 0L;
	}

	public void startIfNotRunning(int age) { if (!this.isRunning()) this.start(age); }

	public void stop() { this.updatedAt = STOPPED; }

	public void run(Consumer<AnimationState> consumer) { if (this.isRunning()) consumer.accept(this); }

	public void update(float animationProgress, float speedMultiplier) {
		if (!this.isRunning()) return;
		long l = MathHelper.lfloor(animationProgress * 1000.0f / 20.0f);
		this.timeRunning += (long)((float)(l - this.updatedAt) * speedMultiplier);
		this.updatedAt = l;
	}

	public long getTimeRunning() { return this.timeRunning; }

	public boolean isRunning() { return this.updatedAt != STOPPED; }
}