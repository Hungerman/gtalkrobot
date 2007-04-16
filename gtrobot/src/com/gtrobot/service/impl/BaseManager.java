package com.gtrobot.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.dao.Dao;
import com.gtrobot.service.Manager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p>
 * <a href="BaseManager.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy: kei $ $LastChangedRevision: 1190 $
 *          $LastChangedDate: 2006-10-05 17:37:42 +0900 (木, 05 10 2006) $
 */
public class BaseManager implements Manager {
	protected final Log log = LogFactory.getLog(getClass());

	protected Dao dao = null;

	/**
	 * @see jp.co.softbrain.service.Manager#setDao(jp.co.softbrain.dao.Dao)
	 */
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	/**
	 * @see jp.co.softbrain.service.Manager#getObject(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public Object getObject(Class clazz, Serializable id) {
		return dao.getObject(clazz, id);
	}

	/**
	 * @see jp.co.softbrain.service.Manager#getObjects(java.lang.Class)
	 */
	public List getObjects(Class clazz) {
		return dao.getObjects(clazz);
	}

	/**
	 * @see jp.co.softbrain.service.Manager#removeObject(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public void removeObject(Class clazz, Serializable id) {
		dao.removeObject(clazz, id);
	}

	/**
	 * @see jp.co.softbrain.service.Manager#saveObject(java.lang.Object)
	 */
	public void saveObject(Object o) {
		dao.saveObject(o);
	}

}
