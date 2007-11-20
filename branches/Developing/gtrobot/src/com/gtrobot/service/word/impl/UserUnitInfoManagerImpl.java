package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.UserUnitInfoDao;
import com.gtrobot.model.word.UserUnitInfo;
import com.gtrobot.model.word.UserUnitInfoKey;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.UserUnitInfoManager;

public class UserUnitInfoManagerImpl extends BaseManager implements
        UserUnitInfoManager {
    private UserUnitInfoDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setUserUnitInfoDao(final UserUnitInfoDao dao) {
        this.dao = dao;
    }

    public List getUserUnitInfos(final Long userEntryId) {
        return this.dao.getUserUnitInfos(userEntryId);
    }

    public List getNotFinishedUserUnitInfos(final Long userEntryId) {
        return this.dao.getNotFinishedUserUnitInfos(userEntryId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gtrobot.service.word.UserUnitInfoManager#getUserUnitInfo(com.gtrobot.model.word.UserUnitInfoKey)
     */
    public UserUnitInfo getUserUnitInfo(final UserUnitInfoKey key) {
        return this.dao.getUserUnitInfo(key);
    }

    public UserUnitInfo getUserUnitInfo(final Long userId, final Long wordUnitId) {
        return this.dao.getUserUnitInfo(userId, wordUnitId);
    }

    /**
     * @see com.gtrobot.model.service.UserUnitInfoManager#saveUserUnitInfo(UserUnitInfo
     *      UserUnitInfo)
     */
    public void saveUserUnitInfo(final UserUnitInfo userUnitInfo) {
        this.dao.saveUserUnitInfo(userUnitInfo);
    }

    /**
     * @see com.gtrobot.model.service.UserUnitInfoManager#removeUserUnitInfo(String
     *      id)
     */
    public void removeUserUnitInfo(final UserUnitInfo userUnitInfo) {
        this.dao.removeUserUnitInfo(userUnitInfo);
    }
}
