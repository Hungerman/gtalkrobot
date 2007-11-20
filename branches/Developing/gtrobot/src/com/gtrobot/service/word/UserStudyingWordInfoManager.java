package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.service.Manager;

public interface UserStudyingWordInfoManager extends Manager {
    /**
     * Retrieves all of the UserWordInfos
     */
    public List getUserWordStudyingInfos();

    /**
     * Gets UserWordStudyingInfo's information based on userId.
     * 
     * @param userId
     *            the UserWordStudyingInfo's userId
     * @return UserWordStudyingInfo populated UserWordStudyingInfo object
     */
    public UserWordStudyingInfo getUserWordStudyingInfo(final Long userId);

    /**
     * Saves a UserWordStudyingInfo's information
     * 
     * @param userWordStudyingInfo
     *            the object to be saved
     */
    public void saveUserWordStudyingInfo(
            UserWordStudyingInfo userWordStudyingInfo);

    /**
     * Removes a UserWordStudyingInfo from the database by userId
     * 
     * @param userId
     *            the UserWordStudyingInfo's userId
     */
    public void removeUserWordStudyingInfo(final Long userId);
}
