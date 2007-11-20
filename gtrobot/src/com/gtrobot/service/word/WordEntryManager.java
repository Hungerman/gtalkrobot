package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.WordEntry;
import com.gtrobot.service.Manager;

public interface WordEntryManager extends Manager {
    /**
     * Retrieves all of the wordEntrys
     */
    public List getWordEntries();

    public List searchWordEntries(final String keyword, final int maxResults);

    /**
     * Gets wordEntry's information based on wordEntryId.
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
