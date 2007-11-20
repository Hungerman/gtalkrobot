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

    public static String isInInteractiveOperation(final String jid) {
        final BaseCommand previousCommand = UserSessionUtil
                .retrievePreviousCommand(jid);
        if (previousCommand == null) {
            return null;
        }
        final Processor processor = previousCommand.getProcessor();
        if (processor == null) {
            return null;
        }
        if (processor instanceof InteractiveProcessor) {
            return previousCommand.getCommandType();
        }
        return null;
    }

    public static final void storePreviousCommand(final BaseCommand abCmd) {
        UserSessionUtil.putSession(abCmd.getUserEntry().getJid(),
                UserSessionUtil.PREVIOUS_COMMAND_KEY, abCmd);
    }

    public static final void removePreviousCommand(final BaseCommand abCmd) {
        UserSessionUtil.removeSession(abCmd.getUserEntry().getJid(),
                UserSessionUtil.PREVIOUS_COMMAND_KEY);
    }

    public static final BaseCommand retrievePreviousCommand(final String jid) {
        final BaseCommand result = (BaseCommand) UserSessionUtil.getSession(
                jid, UserSessionUtil.PREVIOUS_COMMAND_KEY);
        return result;
    }

    public static final Object getSession(final String jid, final String key) {
        final Cache sessionCache = GTRobotContextHelper.getSessionCache();
        final Element element = sessionCache.get(UserSessionUtil.getSessionKey(
                jid, key));
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
    }

    public static final void putSession(final String jid, final String key,
            final Object obj) {
        final Cache sessionCache = GTRobotContextHelper.getSessionCache();
        final String sessionKey = UserSessionUtil.getSessionKey(jid, key);
        Element element = sessionCache.get(sessionKey);
        if (element != null) {
            sessionCache.remove(sessionKey);
        }

        element = new Element(sessionKey, obj);

        sessionCache.put(element);
    }

    public static final void removeSession(final String jid, final String key) {
        final Cache sessionCache = GTRobotContextHelper.getSessionCache();
        final String sessionKey = UserSessionUtil.getSessionKey(jid, key);
        sessionCache.remove(sessionKey);
    }

    private static final String getSessionKey(final String jid, final String key) {
        return new StringBuffer().append("Session: ").append(jid).append(key)
                .toString();
    }
}
