package com.gtrobot.context;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheContext {

	private static final CacheContext instance = new CacheContext();

	private CacheManager manager;

	private Cache userCache;

	private Cache chatCache;

	private Cache messageCache;

	private Cache sessionCache;

	private CacheContext() {
		initializeCache();
	}

	public static CacheContext getInstance() {
		return instance;
	}

	private void initializeCache() {
		CacheManager manager = new CacheManager();
		userCache = manager.getCache("userCache");
		chatCache = manager.getCache("chatCache");
		messageCache = manager.getCache("messageCache");
		sessionCache = manager.getCache("sessionCache");

		try {
			chatCache.removeAll();
			messageCache.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}
	
	public void shutdown()
	{
		if(manager != null)
		{
			manager.shutdown();
			manager = null;
		}
	}

	public Cache getChatCache() {
		return chatCache;
	}

	public Cache getMessageCache() {
		return messageCache;
	}

	public Cache getSessionCache() {
		return sessionCache;
	}

	public Cache getUserCache() {
		return userCache;
	}
}
