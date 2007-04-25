package com.gtrobot.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.processor.Processor;

/**
 * 提供了根据用户JID进行Session操作的接口。内部是根据Cache内容进行实现。
 * 
 * @author Joey
 * 
 */
public class UserSessionUtil {
	private static final String PREVIOUS_COMMAND_KEY = "-preCommand";

	public static String isInInteractiveOperation(String jid) {
		BaseCommand previousCommand = retrievePreviousCommand(jid);
		if (previousCommand == null) {
			return null;
		}
		Processor processor = previousCommand.getProcessor();
		if (processor == null) {
			return null;
		}
		if (processor instanceof InteractiveProcessor) {
			return previousCommand.getCommandType();
		}
		return null;
	}

	public static final void storePreviousCommand(BaseCommand abCmd) {
		putSession(abCmd.getUserEntry().getJid(), PREVIOUS_COMMAND_KEY, abCmd);
	}

	public static final void removePreviousCommand(BaseCommand abCmd) {
		removeSession(abCmd.getUserEntry().getJid(), PREVIOUS_COMMAND_KEY);
	}

	public static final BaseCommand retrievePreviousCommand(String jid) {
		BaseCommand result = (BaseCommand) getSession(jid, PREVIOUS_COMMAND_KEY);
		return result;
	}

	public static final Object getSession(String jid, String key) {
		Cache sessionCache = GTRobotContextHelper.getSessionCache();
		Element element = sessionCache.get(getSessionKey(jid, key));
		if (element == null)
			return null;
		return element.getObjectValue();
	}

	public static final void putSession(String jid, String key, Object obj) {
		Cache sessionCache = GTRobotContextHelper.getSessionCache();
		String sessionKey = getSessionKey(jid, key);
		Element element = sessionCache.get(sessionKey);
		if (element != null)
			sessionCache.remove(sessionKey);

		element = new Element(sessionKey, obj);

		sessionCache.put(element);
	}

	public static final void removeSession(String jid, String key) {
		Cache sessionCache = GTRobotContextHelper.getSessionCache();
		String sessionKey = getSessionKey(jid, key);
		sessionCache.remove(sessionKey);
	}

	private static final String getSessionKey(String jid, String key) {
		return new StringBuffer().append("Session: ").append(jid).append(key)
				.toString();
	}
}
