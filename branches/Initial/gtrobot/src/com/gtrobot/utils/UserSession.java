package com.gtrobot.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.context.CacheContext;

public class UserSession {
	private static final String PREVIOUS_COMMAND_KEY = "-preCommand";

	public static final void storePreviousCommand(AbstractCommand abCmd) {
		putSession(abCmd.getUserEntry().getJid(), PREVIOUS_COMMAND_KEY, abCmd);
	}

	public static final AbstractCommand retrievePreviousCommand(String jid) {
		AbstractCommand result = (AbstractCommand) getSession(jid,
				PREVIOUS_COMMAND_KEY);
		return result;
	}

	public static final Object getSession(String jid, String key) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		Element element = sessionCache.get(getSessionKey(jid, key));
		if (element == null)
			return null;
		return element.getObjectValue();
	}

	public static final void putSession(String jid, String key, Object obj) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		String sessionKey = getSessionKey(jid, key);
		Element element = sessionCache.get(sessionKey);
		if (element != null)
			sessionCache.remove(sessionKey);

		element = new Element(sessionKey, obj);
		sessionCache.put(element);
	}

	public static final void removeSession(String jid, String key) {
		Cache sessionCache = CacheContext.getInstance().getSessionCache();
		String sessionKey = getSessionKey(jid, key);
		sessionCache.remove(sessionKey);
	}

	private static final String getSessionKey(String jid, String key) {
		return jid + key;
	}
}
