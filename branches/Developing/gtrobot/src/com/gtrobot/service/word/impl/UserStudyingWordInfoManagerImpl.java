package com.gtrobot.service.word.impl;

import java.util.List;

import com.gtrobot.dao.word.UserStudyingWordInfoDao;
import com.gtrobot.model.word.UserWordStudyingInfo;
import com.gtrobot.service.impl.BaseManager;
import com.gtrobot.service.word.UserStudyingWordInfoManager;

public class UserStudyingWordInfoManagerImpl extends BaseManager implements
        UserStudyingWordInfoManager {
    private UserStudyingWordInfoDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setUserStudyingWordInfoDao(final UserStudyingWordInfoDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.gtrobot.model.service.UserStudyingWordInfoManager#getUserWordStudyingInfos(com.gtrobot.model.word.UserWordStudyingInfo)
     */
    public List getUserWordStudyingInfos() {
        return this.dao.getUserWordStudyingInfos();
    }

    /**
     * @see com.gtrobot.model.service.UserStudyingWordInfoManager#getUserWordStudyingInfo(String
     *      userId)
     */
    public UserWordStudyingInfo getUserWordStudyingInfo(final Long userId) {
        return this.dao.getUserWordStudyingInfo(userId);
    }

    /**
     * @see com.gtrobot.model.service.UserStudyingWordInfoManager#saveUserWordStudyingInfo(UserWordStudyingInfo
     *      UserWordStudyingInfo)
     */
    public void saveUserWordStudyingInfo(
            final UserWordStudyingInfo userWordStudyingInfo) {
        this.dao.saveUserWordStudyingInfo(userWordStudyingInfo);
    }

    /**
     * @see com.gtrobot.model.service.UserStudyingWordInfoManager#removeUserWordStudyingInfo(String
     *      userId)
     */
    public void removeUserWordStudyingInfo(final Long userId) {
        this.dao.removeUserWordStudyingInfo(userId);
    }
}
