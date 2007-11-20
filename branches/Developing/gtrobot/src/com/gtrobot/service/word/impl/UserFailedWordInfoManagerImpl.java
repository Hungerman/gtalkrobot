package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.UserFailedWordInfoDao;
import com.gtrobot.model.word.UserFailedWordInfo;
import com.gtrobot.model.word.UserFailedWordInfoKey;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.UserFailedWordInfoManager;

public class UserFailedWordInfoManagerImpl extends BaseManager implements
        UserFailedWordInfoManager {
    private UserFailedWordInfoDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setUserFailedWordInfoDao(final UserFailedWordInfoDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.gtrobot.model.service.UserFailedWordInfoManager#getUserFailedWordInfos(com.gtrobot.model.word.UserFailedWordInfo)
     */
    public List getUserFailedWordInfos() {
        return this.dao.getUserFailedWordInfos();
    }

    public List getUserFailedWordInfos(final Long userId) {
        return this.dao.getUserFailedWordInfos(userId);
    }

    public List getUserFailedWordInfos(final Long userId, final Long wordUnitId) {
        return this.dao.getUserFailedWordInfos(userId, wordUnitId);
    }

    public long getFailedWordCount(final Long userId, final Long wordUnitId) {
        return this.dao.getFailedWordCount(userId, wordUnitId);
    }

    public List getUserFailedWordInfosByWord(final Long userId,
            final Long wordEntryId) {
        return this.dao.getUserFailedWordInfosByWord(userId, wordEntryId);
    }

    /**
     * @see com.gtrobot.model.service.UserFailedWordInfoManager#getUserFailedWordInfo(String
     *      id)
     */
    public UserFailedWordInfo getUserFailedWordInfo(
            final UserFailedWordInfoKey userFailedWordInfoKey) {
        return this.dao.getUserFailedWordInfo(userFailedWordInfoKey);
    }

    public UserFailedWordInfo getUserFailedWordInfos(final Long userId,
            final Long wordUnitId, final Long wordEntryId) {
        return this.dao.getUserFailedWordInfos(userId, wordUnitId, wordEntryId);
    }

    /**
     * @see com.gtrobot.model.service.UserFailedWordInfoManager#saveUserFailedWordInfo(UserFailedWordInfo
     *      UserFailedWordInfo)
     */
    public void saveUserFailedWordInfo(
            final UserFailedWordInfo userFailedWordInfo) {
        this.dao.saveUserFailedWordInfo(userFailedWordInfo);
    }

    /**
     * @see com.gtrobot.model.service.UserFailedWordInfoManager#removeUserFailedWordInfo(String
     *      id)
     */
    public void removeUserFailedWordInfo(
            final UserFailedWordInfo userFailedWordInfo) {
        this.dao.removeUserFailedWordInfo(userFailedWordInfo);
    }
}
