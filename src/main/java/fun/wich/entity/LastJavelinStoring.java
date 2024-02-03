package fun.wich.entity;

import fun.wich.entity.projectile.JavelinEntity;

public interface LastJavelinStoring {
	JavelinEntity getLastJavelin();
	void setLastJavelin(JavelinEntity entity);
}
