package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 */
public class UserFailedWordInfoKey extends BaseObject {
	private static final long serialVersionUID = -9143906055746598355L;

	private Long userId;

	private WordUnit wordUnit = new WordUnit();

	private WordEntry wordEntry = new WordEntry();

	/**
	 * @hibernate.key-property column="USER_ENTRY_ID"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.key-many-to-one class="com.gtrobot.model.word.WordUnit"
	 *                            column="WORD_UNIT_ID"
	 */
	public WordUnit getWordUnit() {
		return wordUnit;
	}

	public void setWordUnit(WordUnit wordUnit) {
		this.wordUnit = wordUnit;
	}

	/**
	 * @hibernate.key-many-to-one class="com.gtrobot.model.word.WordEntry"
	 *                            column="WORD_ENTRY_ID"
	 */
	public WordEntry getWordEntry() {
		return wordEntry;
	}

	public void setWordEntry(WordEntry wordEntry) {
		this.wordEntry = wordEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserFailedWordInfoKey))
			return false;

		final UserFailedWordInfoKey target = (UserFailedWordInfoKey) o;

		if (userId != null) {
			if (!userId.equals(target.userId))
				return false;
		} else {
			if (target.userId != null)
				return false;
		}
		if (wordUnit != null) {
			if (!wordUnit.equals(target.wordUnit))
				return false;
		} else {
			if (target.wordUnit != null)
				return false;
		}
		if (wordEntry != null) {
			if (!wordEntry.equals(target.wordEntry))
				return false;
		} else {
			if (target.wordEntry != null)
				return false;
		}

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int hc1 = (userId != null ? userId.hashCode() : 0);
		int hc2 = (wordUnit != null ? wordUnit.hashCode() : 0);
		int hc3 = (wordEntry != null ? wordEntry.hashCode() : 0);
		return new Long(hc1 + hc2 + hc3).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.userId).append(this.wordUnit).append(this.wordEntry)
				.toString();
	}
}
