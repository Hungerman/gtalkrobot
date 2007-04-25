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

	private static ThreadLocal<UserEntry> userEntryHolder = new ThreadLocal<UserEntry>();

	public void process(BaseCommand abCmd) {
		try {
			setUserEntryHolder(abCmd.getUserEntry());

			beforeProcess(abCmd);
			if (abCmd.getError() == null) {
				internalProcess(abCmd);
			} else {
				processInvalidCommandFormat(abCmd);
			}
			clearUserEntryHolder();
		} catch (Exception e) {
			log.error("Exception in process.", e);
		}
	}

	protected void beforeProcess(BaseCommand abCmd) throws XMPPException {
	}

	protected abstract void internalProcess(BaseCommand abCmd)
			throws XMPPException;

	protected void sendBackMessage(BaseCommand abCmd, String message)
			throws XMPPException {
		UserEntry userEntry = abCmd.getUserEntry();
		sendMessage(message, userEntry);
	}

	protected void sendMessage(String message, UserEntry userEntry)
			throws XMPPException {
		Chat chat = UserChatUtil.getChat(userEntry.getJid());
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

	protected void processInvalidCommandFormat(BaseCommand abCmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		ErrorType error = abCmd.getError();
		msgBuf.append(getI18NMessage("error.prompt"));
		msgBuf.append(getI18NMessage("error." + error.name()));
		msgBuf.append(endl);
		if (!error.isAbnormal()) {
			msgBuf.append(getI18NMessage(abCmd.getCommandType() + ".format"));
			msgBuf.append(endl);
			msgBuf.append(getI18NMessage("error.origin.prompt"));
			msgBuf.append(abCmd.getOriginMessage());
			msgBuf.append(endl);
		}

		sendBackMessage(abCmd, msgBuf.toString());
	}

	protected static void clearUserEntryHolder() {
		userEntryHolder.set(null);
	}

	protected static UserEntry getUserEntryHolder() {
		return (UserEntry) userEntryHolder.get();
	}

	protected static void setUserEntryHolder(UserEntry userEntry) {
		userEntryHolder.set(userEntry);
	}

	public static String getI18NMessage(String key) {
		return MessageHelper.getMessage(key, getUserEntryHolder().getLocale());
	}

	public static String getI18NMessage(String key, Object[] args) {
		return MessageHelper.getMessage(key, args, getUserEntryHolder()
				.getLocale());
	}
}
