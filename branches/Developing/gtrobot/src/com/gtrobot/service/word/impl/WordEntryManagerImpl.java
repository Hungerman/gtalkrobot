package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.WordEntryDao;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.WordEntryManager;

public class WordEntryManagerImpl extends BaseManager implements
        WordEntryManager {
    private WordEntryDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setWordEntryDao(final WordEntryDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.gtrobot.model.service.WordEntryManager#get
     *      WordEntrys(com.gtrobot.model.word.WordEntry)
     */
    public List getWordEntries() {
        return this.dao.getWordEntries();
    }

    public List searchWordEntries(final String keyword, final int maxResults) {
        return this.dao.searchWordEntries(keyword, maxResults);
    }

    /**
     * @see com.gtrobot.model.service.WordEntryManager#get WordEntry(String
     *      wordEntryId)
     */
    public WordEntry getWordEntry(final Long wordEntryId) {
        return this.dao.getWordEntry(wordEntryId);
    }

    public WordEntry getWordEntry(final String word) {
        return this.dao.getWordEntry(word);
    }

    /**
     * @see com.gtrobot.model.service.WordEntryManager#save WordEntry(WordEntry
     *      wordEntry)
     */
    public void saveWordEntry(final WordEntry wordEntry) {
        this.dao.saveWordEntry(wordEntry);
    }

    /**
     * @see com.gtrobot.model.service.WordEntryManager#remove WordEntry(String
     *      wordEntryId)
     */
    public void removeWordEntry(final Long wordEntryId) {
        this.dao.removeWordEntry(wordEntryId);
    }
}
