package com.gtrobot.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gtrobot.dao.Dao;

/**
 * This class serves as the Base class for all other Daos - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.
 * </p>
 * 
 * <p>
 * <a href="BaseDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy: sunyuxin $ $LastChangedRevision: 172 $
 *          $LastChangedDate: 2006-08-04 10:39:28 +0900 (金, 04 8 2006) $
 */
public class BaseDaoHibernate extends HibernateDaoSupport implements Dao {
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @see jp.co.softbrain.dao.Dao#saveObject(java.lang.Object)
     */
    public void saveObject(final Object o) {
        this.getHibernateTemplate().saveOrUpdate(o);
    }

    /**
     * @see jp.co.softbrain.dao.Dao#getObject(java.lang.Class,
     *      java.io.Serializable)
     */
    public Object getObject(final Class clazz, final Serializable id) {
        final Object o = this.getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }

    /**
     * @see jp.co.softbrain.dao.Dao#getObjects(java.lang.Class)
     */
    public List getObjects(final Class clazz) {
        return this.getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see jp.co.softbrain.dao.Dao#removeObject(java.lang.Class,
     *      java.io.Serializable)
     */
    public void removeObject(final Class clazz, final Serializable id) {
        this.getHibernateTemplate().delete(this.getObject(clazz, id));
    }
}
