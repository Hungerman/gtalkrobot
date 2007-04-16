package com.gtrobot.model.word;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="USER_WORD_STUDYING_INFO"
 */
public class UserWordStudyingInfo extends BaseObject {
	private static final long serialVersionUID = 6954430467776366044L;

	private Long userId;

	private WordUnit studyingWordUnit = new WordUnit();

	private long finishedUnits;

	private long studiedWordCounts;

	private long failedWordCounts;

	private Date lastStudied;

	/**
	 * @hibernate.id column="USER_ENTRY_ID" generator-class="assigned"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property column="FINISHED_UNITS" not-null="true"
	 */
	public long getFinishedUnits() {
		return finishedUnits;
	}

	public void setFinishedUnits(long finishedUnits) {
		this.finishedUnits = finishedUnits;
	}

	/**
	 * @hibernate.property column="STUDIED_WORDS" not-null="true"
	 */
	public long getStudiedWordCounts() {
		return studiedWordCounts;
	}

	public void setStudiedWordCounts(long studiedWordCounts) {
		this.studiedWordCounts = studiedWordCounts;
	}

	/**
	 * @hibernate.property column="FAILED_WORD_COUNTS" not-null="true"
	 */
	public long getFailedWordCounts() {
		return failedWordCounts;
	}

	public void setFailedWordCounts(long failedWordCounts) {
		this.failedWordCounts = failedWordCounts;
	}

	/**
	 * @hibernate.property column="LAST_STUDIED" not-null="true"
	 */
	public Date getLastStudied() {
		return lastStudied;
	}

	public void setLastStudied(Date lastStudied) {
		this.lastStudied = lastStudied;
	}

	/**
	 * @hibernate.many-to-one class="com.gtrobot.model.word.WordUnit"
	 *                        column="STUDYING_WORD_UNIT_ID" not-null="true"
	 */
	public WordUnit getStudyingWordUnit() {
		return studyingWordUnit;
	}

	public void setStudyingWordUnit(WordUnit studyingWordUnit) {
		this.studyingWordUnit = studyingWordUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserWordStudyingInfo))
			return false;

		final UserWordStudyingInfo target = (UserWordStudyingInfo) o;

		return !(userId != null ? !userId.equals(target.userId)
				: target.userId != null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (userId != null ? userId.hashCode() : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.userId).toString();
	}
}
