package com.gtrobot.model.word;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * @hibernate.class table="WORD_UNIT"
 */
public class WordUnit extends BaseObject {
    private static final long serialVersionUID = -1850074324912152967L;

    private Long wordUnitId;

    private String name;

    private long level = 0;

    private long wordCount = 0;

    private Long owner;

    private List wordEntries = new ArrayList();;

    /**
     * @hibernate.id column="WORD_UNIT_ID" generator-class="native"
     * @hibernate.generator-param name="sequence" value="SEQ_WORD_UNIT"
     * @hibernate.generator-param name="identity"
     */
    public Long getWordUnitId() {
        return this.wordUnitId;
    }

    /**
     * @hibernate.property column="NAME" length="100" not-null="true"
     *                     unique="true"
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setWordUnitId(final Long id) {
        this.wordUnitId = id;
    }

    /**
     * @hibernate.property column="OWNER"
     */
    public Long getOwner() {
        return this.owner;
    }

    public void setOwner(final Long owner) {
        this.owner = owner;
    }

    /**
     * @hibernate.property column="LEVEL" not-null="true"
     */
    public long getLevel() {
        return this.level;
    }

    public void setLevel(final long level) {
        this.level = level;
    }

    /**
     * @hibernate.bag table="WORD_UNIT_ENTRY" lazy="true"
     * @hibernate.key column="WORD_UNIT_ID"
     * @hibernate.one-to-many class="com.gtrobot.model.word.WordUnitEntry"
     */
    public List getWordEntries() {
        return this.wordEntries;
    }

    public void setWordEntries(final List wordEntries) {
        this.wordEntries = wordEntries;
    }

    /**
     * @hibernate.property column="WORD_COUNT" not-null="true"
     */
    public long getWordCount() {
        return this.wordCount;
    }

    public void setWordCount(final long wordCount) {
        this.wordCount = wordCount;
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
        if (!(o instanceof WordUnit)) {
            return false;
        }

        final WordUnit target = (WordUnit) o;

        return !(this.wordUnitId != null ? !this.wordUnitId
                .equals(target.wordUnitId) : target.wordUnitId != null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (this.wordUnitId != null ? this.wordUnitId.hashCode() : 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.name).toString();
    }
}
