package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.service.Manager;

public interface UserFailedWordInfoManager extends Manager {
	/**
	 * Retrieves all of the UserFailedWordInfos
	 */
	public List getUserFailedWordInfos();

	public List getUserFailedWordInfos(final Long userId);

	public List getUserFailedWordInfos(final Long userId, final Long wordUnitId);

	public List getUserFailedWordInfosByWord(final Long userId,
			final Long wordEntryId);

	public long getFailedWordCount(final Long userId, final Long wordUnitId);

	/**
	 * Gets UserFailedWordInfos's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the UserFailedWordInfos's id
	 * @return UserFailedWordInfos populated UserFailedWordInfos object
	 */
	public UserFailedWordInfo getUserFailedWordInfo(
			final UserFailedWordInfoKey userFailedWordInfoKey);

	public UserFailedWordInfo getUserFailedWordInfos(final Long userId,
			final Long wordUnitId, final Long wordEntryId);

	/**
	 * Saves a UserFailedWordInfos's information
	 * 
	 * @param userFailedWordInfo
	 *            the object to be saved
	 */
	public void saveUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo);

	/**
	 * Removes a UserFailedWordInfos from the database by id
	 * 
	 * @param id
	 *            the UserFailedWordInfos's id
	 */
	public void removeUserFailedWordInfo(
			final UserFailedWordInfo userFailedWordInfo);

}
