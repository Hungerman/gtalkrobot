package com.gtrobot.model.word;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="USER_UNIT_INFO"
 */
public class UserUnitInfo extends BaseObject {
	private static final long serialVersionUID = 3782501438376669985L;

	private UserUnitInfoKey pk = new UserUnitInfoKey();

	private long studiedTimes = 0;

	private boolean finished = false;

	private Date lastStudied = new Date();

	/**
	 * @hibernate.composite-id
	 */
	public UserUnitInfoKey getPk() {
		return pk;
	}

	public void setPk(UserUnitInfoKey key) {
		this.pk = key;
	}

	/**
	 * @hibernate.property column="STUDIED_TIMES"
	 */
	public long getStudiedTimes() {
		return studiedTimes;
	}

	public void setStudiedTimes(long finishedUnits) {
		this.studiedTimes = finishedUnits;
	}

	/**
	 * @hibernate.property column="FINISHED"
	 */
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * @hibernate.property column="LAST_STUDIED"
	 */
	public Date getLastStudied() {
		return lastStudied;
	}

	public void setLastStudied(Date lastStudied) {
		this.lastStudied = lastStudied;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserUnitInfo))
			return false;

		final UserUnitInfo target = (UserUnitInfo) o;

		return !(pk != null ? !pk.equals(target.pk) : target.pk != null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (pk != null ? pk.hashCode() : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.pk).toString();
	}

}
