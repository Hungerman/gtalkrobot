package com.gtrobot.model.word;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gtrobot.model.BaseObject;

/**
 * 
 * 
 * @author sunyuxin
 */
public class WordUnitEntryKey extends BaseObject {
    private static final long serialVersionUID = 8539014396817062030L;

    private Long wordEntryId;

    private Long wordUnitId;

    /**
     * @hibernate.key-property column="WORD_ENTRY_ID"
     */
    public Long getWordEntryId() {
        return this.wordEntryId;
    }

    public void setWordEntryId(final Long wordEntryId) {
        this.wordEntryId = wordEntryId;
    }

    /**
     * @hibernate.key-property column="WORD_UNIT_ID"
     */
    public Long getWordUnitId() {
        return this.wordUnitId;
    }

    public void setWordUnitId(final Long wordUnitId) {
        this.wordUnitId = wordUnitId;
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
        if (!(o instanceof WordUnitEntryKey)) {
            return false;
        }

        final WordUnitEntryKey target = (WordUnitEntryKey) o;

        if (this.wordEntryId != null) {
            if (!this.wordEntryId.equals(target.wordEntryId)) {
                return false;
            }
        } else {
            if (target.wordEntryId != null) {
                return false;
            }
        }
        if (this.wordUnitId != null) {
            if (!this.wordUnitId.equals(target.wordUnitId)) {
                return false;
            }
        } else {
            if (target.wordUnitId != null) {
                return false;
            }
        }

        return true;

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int hc1 = (this.wordEntryId != null ? this.wordEntryId.hashCode()
                : 0);
        final int hc2 = (this.wordUnitId != null ? this.wordUnitId.hashCode()
                : 0);
        return new Long(hc1 + hc2).hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.wordEntryId).append(this.wordUnitId).toString();
    }

}
