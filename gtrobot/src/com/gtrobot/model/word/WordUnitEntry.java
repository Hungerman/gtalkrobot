package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="WORD_UNIT_ENTRY"
 */
public class WordUnitEntry extends BaseObject {
	private static final long serialVersionUID = -8921629023230583323L;

	private WordUnitEntryKey pk = new WordUnitEntryKey();

	/**
	 * @hibernate.composite-id
	 */
	public WordUnitEntryKey getPk() {
		return pk;
	}

	public void setPk(WordUnitEntryKey key) {
		this.pk = key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof WordUnitEntry))
			return false;

		final WordUnitEntry target = (WordUnitEntry) o;

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
