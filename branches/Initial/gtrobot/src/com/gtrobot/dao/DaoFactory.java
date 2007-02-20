package com.gtrobot.dao;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.gtrobot.context.CacheContext;
import com.gtrobot.dao.common.UserEntryDao;
import com.gtrobot.dao.word.WordEntryDao;

public class DaoFactory {
	public static UserEntryDao getUserEntryDao() {
		return (UserEntryDao) getCachedObject(UserEntryDao.class);
	}

	public static WordEntryDao getWordEntryDao() {
		return (WordEntryDao) getCachedObject(WordEntryDao.class);
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
