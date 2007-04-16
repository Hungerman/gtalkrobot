package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;

public interface WordUnitEntryDao extends Dao {

	/**
	 * Retrieves all of the wordUnitEntrys
	 */
	public List getWordUnitEntrys(WordUnitEntry wordUnitEntry);

	/**
	 * Gets wordUnitEntry's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param key
	 *            the wordUnitEntry's key
	 * @return wordUnitEntry populated wordUnitEntry object
	 */
	public WordUnitEntry getWordUnitEntry(final WordUnitEntryKey key);

	/**
	 * Saves a wordUnitEntry's information
	 * 
	 * @param wordUnitEntry
	 *            the object to be saved
	 */
	public void saveWordUnitEntry(WordUnitEntry wordUnitEntry);

	/**
	 * Removes a wordUnitEntry from the database by key
	 * 
	 * @param key
	 *            the wordUnitEntry's key
	 */
	public void removeWordUnitEntry(final WordUnitEntryKey key);
}
