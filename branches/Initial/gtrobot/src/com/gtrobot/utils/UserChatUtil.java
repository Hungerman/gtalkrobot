package com.gtrobot.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;

import com.gtrobot.engine.GTRobotContextHelper;

/**
 * 管理同所有用户的Chat对象的Cache处理。
 * 
 * @author Joey
 * 
 */
public class UserChatUtil {
    public static final Chat getChat(final String jid) {
        Chat chat = UserChatUtil.getChatFromCache(jid);
        if (chat == null) {
            final ChatManager chatManager = GTRobotContextHelper
                    .getGoogleTalkConnection().getXMPPConnection()
                    .getChatManager();
            chat = chatManager.createChat(jid, null);
            UserChatUtil.putChatIntoCache(jid, chat);
        }
        return chat;
    }

    private static final Chat getChatFromCache(final String jid) {
        final Cache chatCache = GTRobotContextHelper.getChatCache();
        final Element element = chatCache
                .get(UserChatUtil.getChatCacheKey(jid));
        if (element == null) {
            return null;
        }
        return (Chat) element.getObjectValue();
    }

    private static final void putChatIntoCache(final String jid,
            final Object obj) {
        final Cache chatCache = GTRobotContextHelper.getChatCache();
        final String chatCacheKey = UserChatUtil.getChatCacheKey(jid);
        Element element = chatCache.get(chatCacheKey);
        if (element != null) {
            chatCache.remove(chatCacheKey);
        }

        element = new Element(chatCacheKey, obj);

        chatCache.put(element);
    }

    public static final void removeChatFromCache(final String jid) {
        final Cache chatCache = GTRobotContextHelper.getChatCache();
        final String chatCacheKey = UserChatUtil.getChatCacheKey(jid);
        chatCache.remove(chatCacheKey);
    }

    private static final String getChatCacheKey(final String jid) {
        return new StringBuffer().append("Chat: ").append(jid).toString();
    }
}
