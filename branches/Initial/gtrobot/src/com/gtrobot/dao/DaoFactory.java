package com.gtrobot.dao;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.gtrobot.context.CacheContext;

public class DaoFactory {
	public static UserEntryDao getUserEntryDao() {
		return (UserEntryDao) getCachedObject(UserEntryDao.class);
	}

	private static Object getCachedObject(Class clazz) {
		Cache objectCache = CacheContext.getInstance().getObjectCache();
		Object result = null;
		Element element = objectCache.get(clazz.getName());
		if (element == null) {
			try {
				result = clazz.newInstance();
				objectCache.put(new Element(clazz.getName(), result));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			result = element.getObjectValue();
		}
		return result;
	}
}
