package com.gtrobot.processor;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.service.common.UserEntryService;
import com.gtrobot.utils.UserChatUtil;

public abstract class AbstractProcessor implements Processor {
	protected static final transient Log log = LogFactory
			.getLog(AbstractProcessor.class);

	protected static final String endl = "\n";

	protected static final String seperator = "~~~~~~~~~~~~~~~~~~~~~~~~";

	private static ThreadLocal<UserEntry> userEntryHolder = new ThreadLocal<UserEntry>();

	public AbstractProcessor() {
		// ctx = GlobalContext.getInstance();
	}

	public void process(BaseCommand abCmd) {
		try {
			setUserEntryHolder(abCmd.getUserEntry());

			beforeProcess(abCmd);
			if (abCmd.getErrorMessage() == null) {
				internalProcess(abCmd);
			}
			clearUserEntryHolder();
		} catch (Exception e) {
			log.error("Exception in process.", e);
		}
	}

	protected void beforeProcess(BaseCommand abCmd)
			throws CommandMatchedException, XMPPException {
		String error = abCmd.getErrorMessage();
		if (error != null) {
			log.error("Error in AbstractProcessor.beforeProcess: " + error);

			processInvalidCommandFormat(abCmd);
		}
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

	protected void broadcastMessage(BaseCommand abCmd, String message)
			throws XMPPException {
		UserEntry sender = abCmd.getUserEntry();
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(sender.getNickName());
		msgBuf.append(" #> ");
		msgBuf.append(message);
		message = msgBuf.toString();

		UserEntryService userEntryService = GTRobotContextHelper
				.getUserEntryService();
		Iterator userList = userEntryService.getAllActiveUsers().iterator();
		while (userList.hasNext()) {
			String jid = (String) userList.next();
			if (jid.equals(sender.getJid()) && !sender.isEchoable()) {
				continue;
			}
			UserEntry userEntry = userEntryService.getUserEntry(jid);
			if (userEntry.isChattable()) {
				sendMessage(message, userEntry);
			}
		}
	}

	protected void processInvalidCommandFormat(BaseCommand abCmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		msgBuf.append(abCmd.getI18NMessage("error.prompt"));
		msgBuf.append(abCmd.getErrorMessage());
		msgBuf.append(endl);
		if (BaseCommand.ErrorType.normal.equals(abCmd.getErrorType())) {
			msgBuf.append(abCmd.getI18NMessage("format.prompt"));
			msgBuf.append(abCmd.getI18NMessage(abCmd.getCommandType()
					+ ".format"));
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("error.origin.prompt"));
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
}
