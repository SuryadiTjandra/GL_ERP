package ags.goldenlionerp.entities;

public abstract class AbstractCustomRepository<T extends DatabaseEntity> {

	protected void setCreationInfo(T entity) {
		DatabaseEntityUtil.setCreationInfo(entity);
	}
	
	protected void setUpdateInfo(T entity) {
		DatabaseEntityUtil.setUpdateInfo(entity);
	}
	
}
