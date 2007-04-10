package com.gtrobot.processor;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;

public abstract class AbstractProcessor implements Processor {
	protected static final transient Log log = LogFactory
			.getLog(AbstractProcessor.class);

	protected static final String endl = "\n";

	protected static final String seperator = "~~~~~~~~~~~~~~~~~~~~~~~~";

	private static ThreadLocal userEntryHolder = new ThreadLocal();

	protected GlobalContext ctx;

	public AbstractProcessor() {
		ctx = GlobalContext.getInstance();
	}

	public void process(BaseCommand abCmd) throws CommandMatchedException,
			XMPPException {
		setUserEntryHolder(abCmd.getUserEntry());

		beforeProcess(abCmd);
		if (abCmd.getErrorMessage() == null) {
			internalProcess(abCmd);
		}

		// boolean flag = CommandProcessorMapping.BROADCAST_COMMAND.equals(abCmd
		// .getCommandType())
		// || CommandProcessorMapping.INVALID_COMMAND.equals(abCmd
		// .getCommandType());
		// if (!flag) {
		// UserSession.storePreviousCommand(abCmd);
		// }

		clearUserEntryHolder();
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
		Chat chat = GlobalContext.getInstance().getChat(userEntry);
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

		Iterator userList = GlobalContext.getInstance().getActiveUserList()
				.iterator();
		while (userList.hasNext()) {
			String jid = (String) userList.next();
			if (jid.equals(sender.getJid()) && !sender.isEchoable()) {
				continue;
			}
			UserEntry userEntry = GlobalContext.getInstance().getUser(jid);
			if (userEntry.isChattableInPublicRoom()) {
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
		msgBuf.append(abCmd.getI18NMessage("format.prompt"));
		msgBuf.append(abCmd.getI18NMessage(abCmd.getCommandType() + ".format"));
		msgBuf.append(endl);
		msgBuf.append(abCmd.getI18NMessage("error.origin.prompt"));
		msgBuf.append(abCmd.getOriginMessage());
		msgBuf.append(endl);

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
