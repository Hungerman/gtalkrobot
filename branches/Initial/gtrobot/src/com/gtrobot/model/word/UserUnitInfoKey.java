package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 */
public class UserUnitInfoKey extends BaseObject {
	private static final long serialVersionUID = 8960886446147844559L;

	private Long userId;

	private WordUnit wordUnit = new WordUnit();

	/**
	 * @hibernate.key-property column="USER_ENTRY_ID"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long wordEntryId) {
		this.userId = wordEntryId;
	}

	/**
	 * @hibernate.key-many-to-one class="com.gtrobot.model.word.WordUnit"
	 *                            column="WORD_UNIT_ID"
	 */
	public WordUnit getWordUnit() {
		return wordUnit;
	}

	public void setWordUnit(WordUnit wordUnitId) {
		this.wordUnit = wordUnitId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserUnitInfoKey))
			return false;

		final UserUnitInfoKey target = (UserUnitInfoKey) o;

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
		return new Long(hc1 + hc2).hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.userId).append(this.wordUnit).toString();
	}
}
