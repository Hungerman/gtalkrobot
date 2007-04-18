package com.gtrobot.dao.word;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.word.UserWordStudyingInfo;

public interface UserStudyingWordInfoDao extends Dao {

	/**
	 * Retrieves all of the user WordInfos
	 */
	public List getUserWordStudyingInfos();

	/**
	 * Gets user WordInfo's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param userId
	 *            the user WordInfo's userId
	 * @return user WordInfo populated user WordInfo object
	 */
	public UserWordStudyingInfo getUserWordStudyingInfo(final Long userId);

	/**
	 * Saves a user WordInfo's information
	 * 
	 * @param userWordStudyingInfo
	 *            the object to be saved
	 */
	public void saveUserWordStudyingInfo(
			UserWordStudyingInfo userWordStudyingInfo);

	/**
	 * Removes a user WordInfo from the database by userId
	 * 
	 * @param userId
	 *            the user WordInfo's userId
	 */
	public void removeUserWordStudyingInfo(final Long userId);
}
