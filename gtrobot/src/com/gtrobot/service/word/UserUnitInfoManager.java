package com.gtrobot.service.word;

import java.util.List;

import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;
import com.gtrobot.service.Manager;

public interface UserUnitInfoManager extends Manager {
    /**
     * Retrieves all of the UserUnitInfos
     */
    public List getUserUnitInfos(final Long userEntryId);

    public List getNotFinishedUserUnitInfos(final Long userEntryId);

    /**
     * Gets UserUnitInfo's information based on id.
     * 
     * @param id
     *            the UserUnitInfo's id
     * @return UserUnitInfo populated UserUnitInfo object
     */
    public UserUnitInfo getUserUnitInfo(final UserUnitInfoKey key);

    public UserUnitInfo getUserUnitInfo(final Long userId, final Long wordUnitId);

    /**
     * Saves a UserUnitInfo's information
     * 
     * @param userUnitInfo
     *            the object to be saved
     */
    public void saveUserUnitInfo(UserUnitInfo userUnitInfo);

    /**
     * Removes a UserUnitInfo from the database by id
     * 
     * @param id
     *            the UserUnitInfo's id
     */
    public void removeUserUnitInfo(final UserUnitInfo userUnitInfo);
}
