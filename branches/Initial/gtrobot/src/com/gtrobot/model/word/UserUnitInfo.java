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

	private long studiedWordCounts = 0;

	private long failedWordCounts = 0;

	private long reviewTimes = 0;

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
	 * @hibernate.property column="STUDIED_WORDS"
	 */
	public long getStudiedWordCounts() {
		return studiedWordCounts;
	}

	public void setStudiedWordCounts(long studiedWordCounts) {
		this.studiedWordCounts = studiedWordCounts;
	}

	/**
	 * @hibernate.property column="FAILED_WORD_COUNTS"
	 */
	public long getFailedWordCounts() {
		return failedWordCounts;
	}

	public void setFailedWordCounts(long failedWordCounts) {
		this.failedWordCounts = failedWordCounts;
	}

	/**
	 * @hibernate.property column="REVIEW_TIMES"
	 */
	public long getReviewTimes() {
		return reviewTimes;
	}

	public void setReviewTimes(long finishedUnits) {
		this.reviewTimes = finishedUnits;
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
