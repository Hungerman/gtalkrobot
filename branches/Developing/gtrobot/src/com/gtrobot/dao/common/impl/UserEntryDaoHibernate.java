package com.gtrobot.dao.common.impl;

import java.util.List;

import com.gtrobot.dao.common.UserEntryDao;
import com.gtrobot.dao.impl.BaseDaoHibernate;
import com.gtrobot.model.common.UserEntry;

/**
 * UserDaoHibernate クラス.
 * このクラスは、HibernateTemplateによるHibernate連係して、ユーザ対象を更新して、削除して、または検索する.
 * 
 * <p>
 * <a href="UserDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy: sunyuxin $ $LastChangedRevision: 172 $
 *          $LastChangedDate: 2006-08-04 10:39:28 +0900 (金, 04 8 2006) $
 */
public class UserEntryDaoHibernate extends BaseDaoHibernate implements
        UserEntryDao {
    public UserEntry getUserEntry(final String jid) {
        final List ls = this.getHibernateTemplate().find(
                "from UserEntry where jid=?", new Object[] { jid });
        if (ls.size() <= 0) {
            return null;
        }
        return (UserEntry) ls.get(0);
    }

    public UserEntry getUserEntryByNickName(final String newNickName) {
        final List ls = this.getHibernateTemplate()
                .find("from UserEntry where nickName=?",
                        new Object[] { newNickName });
        if (ls.size() <= 0) {
            return null;
        }
        return (UserEntry) ls.get(0);
    }

    public List getUserEntrys(final UserEntry userEntry) {
        return this.getHibernateTemplate().find("from UserEntry");
    }

    public void removeUserEntry(final UserEntry userEntry) {
        this.getHibernateTemplate().delete(userEntry);
    }

    public void saveUserEntry(final UserEntry userEntry) {
        this.getHibernateTemplate().saveOrUpdate(userEntry);
    }

}
