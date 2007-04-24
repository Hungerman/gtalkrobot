package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="WORD_ENTRY"
 */
public class WordEntry extends BaseObject {
	private static final long serialVersionUID = -3908805687733951263L;

	private Long wordEntryId;

	private String word;

	private String pronounce;

	private String pronounceType;

	private String wordType;

	private String meaning;

	private String sentence;

	private String comments;

	/**
	 * @hibernate.property column="COMMENTS" length="2000"
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @hibernate.id column="WORD_ENTRY_ID" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="SEQ_WORD_ENTRY"
	 * @hibernate.generator-param name="identity"
	 */
	public Long getWordEntryId() {
		return wordEntryId;
	}

	/**
	 * @hibernate.property column="MEANING" not-null="true" length="1000"
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * @hibernate.property column="PRONOUNCE" not-null="true" length="100"
	 */
	public String getPronounce() {
		return pronounce;
	}

	/**
	 * @hibernate.property column="PRONOUNCE_TYPE" length="50"
	 */
	public String getPronounceType() {
		return pronounceType;
	}

	public void setPronounceType(String pronounceType) {
		this.pronounceType = pronounceType;
	}

	/**
	 * @hibernate.property column="SENTENCE" length="2000"
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * @hibernate.property column="WORD" length="100" not-null="true"
	 *                     unique="true"
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @hibernate.property column="WORD_TYPE" length="100"
	 */
	public String getWordType() {
		return wordType;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setWordEntryId(Long id) {
		this.wordEntryId = id;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof WordEntry))
			return false;

		final WordEntry target = (WordEntry) o;

		return !(wordEntryId != null ? !wordEntryId.equals(target.wordEntryId)
				: target.wordEntryId != null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (wordEntryId != null ? wordEntryId.hashCode() : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.word).toString();
	}
}
