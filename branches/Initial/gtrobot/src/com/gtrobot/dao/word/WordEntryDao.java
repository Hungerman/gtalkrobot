package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.WordEntry;

public interface WordEntryDao extends Dao {

    /**
     * Retrieves all of the wordEntrys
     */
    public List getWordEntries();

    public List searchWordEntries(final String keyword, final int maxResults);

    /**
     * Gets wordEntry's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     * 
     * @param wordEntryId
     *            the wordEntry's wordEntryId
     * @return wordEntry populated wordEntry object
     */
    public WordEntry getWordEntry(final Long wordEntryId);

    public WordEntry getWordEntry(final String word);

    /**
     * Saves a wordEntry's information
     * 
     * @param wordEntry
     *            the object to be saved
     */
    public void saveWordEntry(WordEntry wordEntry);

    /**
     * Removes a wordEntry from the database by wordEntryId
     * 
     * @param wordEntryId
     *            the wordEntry's wordEntryId
     */
    public void removeWordEntry(final Long wordEntryId);
}
