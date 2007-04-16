package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.model.word.WordUnitEntryKey;
import com.gtrobot.service.Manager;

public interface WordUnitEntryManager extends Manager {
	/**
	 * Retrieves all of the wordUnitEntrys
	 */
	public List getWordUnitEntrys(WordUnitEntry wordUnitEntry);

	/**
	 * Gets wordUnitEntry's information based on key.
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
