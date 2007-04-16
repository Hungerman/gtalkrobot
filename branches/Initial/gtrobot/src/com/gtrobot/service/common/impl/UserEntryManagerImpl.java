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
	public void setUserEntryDao(UserEntryDao dao) {
		this.dao = dao;
	}

	/**
	 * @see jp.co.softbrain.service.UserEntryEntryManager#getUserEntry(java.lang.String)
	 */
	public UserEntry getUserEntry(String jid) {
		return dao.getUserEntry(jid);
	}

	/**
	 * @see jp.co.softbrain.service.UserEntryEntryManager#getUserEntrys(jp.co.softbrain.model.UserEntry)
	 */
	public List getUserEntrys(UserEntry UserEntry) {
		return dao.getUserEntrys(UserEntry);
	}

	/**
	 * @see jp.co.softbrain.service.UserEntryEntryManager#saveUserEntry(jp.co.softbrain.model.UserEntry)
	 */
	public void saveUserEntry(UserEntry UserEntry) {
		dao.saveUserEntry(UserEntry);
	}

	/**
	 * @see jp.co.softbrain.service.UserEntryEntryManager#removeUserEntry(java.lang.String)
	 */
	public void removeUserEntry(UserEntry UserEntry) {
		dao.removeUserEntry(UserEntry);
	}
}
