package com.gtrobot.model;

import java.io.Serializable;

/**
 * 
 * @author sunyuxin
 * 
 */
public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = -2014982627687820047L;

	private boolean modified;

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

}
