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

    private boolean hasError;

    /**
     * @hibernate.property column="COMMENTS" length="2000"
     */
    public String getComments() {
        return this.comments;
    }

    /**
     * @hibernate.id column="WORD_ENTRY_ID" generator-class="native"
     * @hibernate.generator-param name="sequence" value="SEQ_WORD_ENTRY"
     * @hibernate.generator-param name="identity"
     */
    public Long getWordEntryId() {
        return this.wordEntryId;
    }

    /**
     * @hibernate.property column="MEANING" not-null="true" length="1000"
     */
    public String getMeaning() {
        return this.meaning;
    }

    /**
     * @hibernate.property column="PRONOUNCE" not-null="true" length="100"
     */
    public String getPronounce() {
        return this.pronounce;
    }

    /**
     * @hibernate.property column="PRONOUNCE_TYPE" length="50"
     */
    public String getPronounceType() {
        return this.pronounceType;
    }

    public void setPronounceType(final String pronounceType) {
        this.pronounceType = pronounceType;
    }

    /**
     * @hibernate.property column="SENTENCE" length="2000"
     */
    public String getSentence() {
        return this.sentence;
    }

    /**
     * @hibernate.property column="WORD" length="100" not-null="true"
     *                     unique="true"
     */
    public String getWord() {
        return this.word;
    }

    /**
     * @hibernate.property column="WORD_TYPE" length="100"
     */
    public String getWordType() {
        return this.wordType;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setWordEntryId(final Long id) {
        this.wordEntryId = id;
    }

    public void setMeaning(final String meaning) {
        this.meaning = meaning;
    }

    public void setPronounce(final String pronounce) {
        this.pronounce = pronounce;
    }

    public void setSentence(final String sentence) {
        this.sentence = sentence;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public void setWordType(final String wordType) {
        this.wordType = wordType;
    }

    /**
     * @hibernate.property column="HAS_ERROR" not-null="true"
     */
    public boolean isHasError() {
        return this.hasError;
    }

    public void setHasError(final boolean hasError) {
        this.hasError = hasError;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordEntry)) {
            return false;
        }

        final WordEntry target = (WordEntry) o;

        return !(this.wordEntryId != null ? !this.wordEntryId
                .equals(target.wordEntryId) : target.wordEntryId != null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (this.wordEntryId != null ? this.wordEntryId.hashCode() : 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.word).toString();
    }
}
