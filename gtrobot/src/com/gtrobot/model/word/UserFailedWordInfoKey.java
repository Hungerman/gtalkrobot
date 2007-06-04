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
        return this.userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @hibernate.key-many-to-one class="com.gtrobot.model.word.WordUnit"
     *                            column="WORD_UNIT_ID"
     */
    public WordUnit getWordUnit() {
        return this.wordUnit;
    }

    public void setWordUnit(final WordUnit wordUnit) {
        this.wordUnit = wordUnit;
    }

    /**
     * @hibernate.key-many-to-one class="com.gtrobot.model.word.WordEntry"
     *                            column="WORD_ENTRY_ID"
     */
    public WordEntry getWordEntry() {
        return this.wordEntry;
    }

    public void setWordEntry(final WordEntry wordEntry) {
        this.wordEntry = wordEntry;
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
        if (!(o instanceof UserFailedWordInfoKey)) {
            return false;
        }

        final UserFailedWordInfoKey target = (UserFailedWordInfoKey) o;

        if (this.userId != null) {
            if (!this.userId.equals(target.userId)) {
                return false;
            }
        } else {
            if (target.userId != null) {
                return false;
            }
        }
        if (this.wordUnit != null) {
            if (!this.wordUnit.equals(target.wordUnit)) {
                return false;
            }
        } else {
            if (target.wordUnit != null) {
                return false;
            }
        }
        if (this.wordEntry != null) {
            if (!this.wordEntry.equals(target.wordEntry)) {
                return false;
            }
        } else {
            if (target.wordEntry != null) {
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
        final int hc1 = (this.userId != null ? this.userId.hashCode() : 0);
        final int hc2 = (this.wordUnit != null ? this.wordUnit.hashCode() : 0);
        final int hc3 = (this.wordEntry != null ? this.wordEntry.hashCode() : 0);
        return new Long(hc1 + hc2 + hc3).hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                this.userId).append(this.wordUnit).append(this.wordEntry)
                .toString();
    }
}
