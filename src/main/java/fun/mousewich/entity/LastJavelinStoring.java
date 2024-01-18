package fun.mousewich.entity;

import fun.mousewich.entity.projectile.JavelinEntity;

public interface LastJavelinStoring {
	JavelinEntity getLastJavelin();
	void setLastJavelin(JavelinEntity entity);
}
