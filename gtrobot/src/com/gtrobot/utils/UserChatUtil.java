package com.gtrobot.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;

import com.gtrobot.engine.GTRobotContextHelper;

public class UserChatUtil {
	public static final Chat getChat(String jid) {
		Chat chat = getChatFromCache(jid);
		if (chat == null) {
			ChatManager chatManager = GTRobotContextHelper
					.getGoogleTalkConnection().getXMPPConnection()
					.getChatManager();
			chat = chatManager.createChat(jid, null);
			putChatIntoCache(jid, chat);
		}
		return chat;
	}

	private static final Chat getChatFromCache(String jid) {
		Cache chatCache = GTRobotContextHelper.getChatCache();
		Element element = chatCache.get(getChatCacheKey(jid));
		if (element == null)
			return null;
		return (Chat) element.getObjectValue();
	}

	private static final void putChatIntoCache(String jid, Object obj) {
		Cache chatCache = GTRobotContextHelper.getChatCache();
		String chatCacheKey = getChatCacheKey(jid);
		Element element = chatCache.get(chatCacheKey);
		if (element != null)
			chatCache.remove(chatCacheKey);

		element = new Element(chatCacheKey, obj);

		chatCache.put(element);
	}

	public static final void removeChatFromCache(String jid) {
		Cache chatCache = GTRobotContextHelper.getChatCache();
		String chatCacheKey = getChatCacheKey(jid);
		chatCache.remove(chatCacheKey);
	}

	private static final String getChatCacheKey(String jid) {
		return new StringBuffer().append("Chat: ").append(jid).toString();
	}
}
