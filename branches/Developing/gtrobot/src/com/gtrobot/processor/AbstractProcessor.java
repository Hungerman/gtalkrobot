package com.gtrobot.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ErrorType;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.utils.MessageHelper;
import com.gtrobot.utils.UserChatUtil;

/**
 * 业务处理的基类。 提供业务处理的基本操作接口。 处理流程： 1. process方法：
 * 设置用户ThreadLocal情报。调用beforeProcess处理，如果没有错误，则调用internalProcess方法。，清除ThreadLocale信息。
 * 
 * @author Joey
 * 
 */
public abstract class AbstractProcessor implements Processor {
    protected static final transient Log log = LogFactory
            .getLog(AbstractProcessor.class);

    protected static final String endl = "\n";

    protected static final String seperator = "~~~~~~~~~~~~~~~~~~~~~~~~";

    private static final ThreadLocal<UserEntry> userEntryHolder = new ThreadLocal<UserEntry>();

    public void process(final BaseCommand abCmd) {
        try {
            AbstractProcessor.setUserEntryHolder(abCmd.getUserEntry());

            this.beforeProcess(abCmd);
            if (abCmd.getError() == null) {
                this.internalProcess(abCmd);
            } else {
                this.processInvalidCommandFormat(abCmd);
            }
            AbstractProcessor.clearUserEntryHolder();
        } catch (final Exception e) {
            AbstractProcessor.log.error("Exception in process.", e);
        }
    }

    protected void beforeProcess(final BaseCommand abCmd) throws XMPPException {
    }

    protected abstract void internalProcess(BaseCommand abCmd)
            throws XMPPException;

    protected void sendBackMessage(final BaseCommand abCmd, final String message)
            throws XMPPException {
        final UserEntry userEntry = abCmd.getUserEntry();
        this.sendMessage(message, userEntry);
    }

    protected void sendMessage(final String message, final UserEntry userEntry)
            throws XMPPException {
        final Chat chat = UserChatUtil.getChat(userEntry.getJid());
        chat.sendMessage(message);
    }

    // protected void broadcastMessage(BaseCommand abCmd, String message)
    // throws XMPPException {
    // UserEntry sender = abCmd.getUserEntry();
    // StringBuffer msgBuf = new StringBuffer();
    // msgBuf.append(sender.getNickName());
    // msgBuf.append(" #> ");
    // msgBuf.append(message);
    // message = msgBuf.toString();
    //
    // UserEntryService userEntryService = GTRobotContextHelper
    // .getUserEntryService();
    // Iterator userList = userEntryService.getAllActiveUsers().iterator();
    // while (userList.hasNext()) {
    // String jid = (String) userList.next();
    // if (jid.equals(sender.getJid()) && !sender.isEchoable()) {
    // continue;
    // }
    // UserEntry userEntry = userEntryService.getUserEntry(jid);
    // sendMessage(message, userEntry);
    // }
    // }

    protected void processInvalidCommandFormat(final BaseCommand abCmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        final ErrorType error = abCmd.getError();
        msgBuf.append(AbstractProcessor.getI18NMessage("error.prompt"));
        msgBuf
                .append(AbstractProcessor.getI18NMessage("error."
                        + error.name()));
        msgBuf.append(AbstractProcessor.endl);
        if (!error.isAbnormal()) {
            msgBuf.append(AbstractProcessor.getI18NMessage(abCmd
                    .getCommandType()
                    + ".format"));
            msgBuf.append(AbstractProcessor.endl);
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("error.origin.prompt"));
            msgBuf.append(abCmd.getOriginMessage());
            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(abCmd, msgBuf.toString());
    }

    protected static void clearUserEntryHolder() {
        AbstractProcessor.userEntryHolder.set(null);
    }

    protected static UserEntry getUserEntryHolder() {
        return AbstractProcessor.userEntryHolder.get();
    }

    protected static void setUserEntryHolder(final UserEntry userEntry) {
        AbstractProcessor.userEntryHolder.set(userEntry);
    }

    public static String getI18NMessage(final String key) {
        return MessageHelper.getMessage(key, AbstractProcessor
                .getUserEntryHolder().getLocale());
    }

    public static String getI18NMessage(final String key, final Object[] args) {
        return MessageHelper.getMessage(key, args, AbstractProcessor
                .getUserEntryHolder().getLocale());
    }
}
