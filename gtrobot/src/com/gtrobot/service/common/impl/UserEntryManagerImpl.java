package com.gtrobot.service.common.impl;

import java.util.List;

import com.gtrobot.dao.common.UserEntryDao;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.service.common.UserEntryManager;
import com.gtrobot.service.impl.BaseManager;

/**
 * UserEntryManagerImpl クラス. UserEntryManagerインタフェースの実装.
 * </p>
 * 
 * <p>
 * <a href="UserEntryManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy: meibing $ $LastChangedRevision: 1446 $
 *          $LastChangedDate: 2006-10-23 18:25:37 +0900 (月, 23 10 2006) $
 */
public class UserEntryManagerImpl extends BaseManager implements
        UserEntryManager {
    private UserEntryDao dao;

    /**
     * Daoセット.
     * 
     * @param dao
     */
    public void setUserEntryDao(final UserEntryDao dao) {
        this.dao = dao;
    }

    /**
     * @see jp.co.softbrain.service.UserEntryEntryManager#getUserEntry(java.lang.String)
     */
    public UserEntry getUserEntry(final String jid) {
        return this.dao.getUserEntry(jid);
    }

    public UserEntry getUserEntryByNickName(final String newNickname) {
        return this.dao.getUserEntryByNickName(newNickname);
    }

    /**
     * @see jp.co.softbrain.service.UserEntryEntryManager#getUserEntrys(jp.co.softbrain.model.UserEntry)
     */
    public List getUserEntrys(final UserEntry UserEntry) {
        return this.dao.getUserEntrys(UserEntry);
    }

    /**
     * @see jp.co.softbrain.service.UserEntryEntryManager#saveUserEntry(jp.co.softbrain.model.UserEntry)
     */
    public void saveUserEntry(final UserEntry UserEntry) {
        this.dao.saveUserEntry(UserEntry);
    }

    /**
     * @see jp.co.softbrain.service.UserEntryEntryManager#removeUserEntry(java.lang.String)
     */
    public void removeUserEntry(final UserEntry UserEntry) {
        this.dao.removeUserEntry(UserEntry);
    }
}
