package fun.mousewich.block;

public interface JukeboxBlockExtension {
	void startPlaying();
	void setPlaying(boolean value);
	long getRecordStartTick();
	int getTicksThisSecond();
	void setTicksThisSecond(int value);
	boolean isPlaying();
	long getTickCount();
	void setTickCount(long value);
}
