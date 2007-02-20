package com.gtrobot.processor;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.common.BroadcastMessageCommand;
import com.gtrobot.command.common.InvalidCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.utils.UserSession;

public abstract class AbstractProcessor implements Processor {
	protected static final transient Log log = LogFactory
			.getLog(AbstractProcessor.class);

	protected static final String endl = "\n";

	protected GlobalContext ctx;

	public AbstractProcessor() {
		ctx = GlobalContext.getInstance();
	}

	public void process(AbstractCommand abCmd) throws CommandMatchedException,
			XMPPException {
		beforeProcess(abCmd);
		if (abCmd.getErrorMessage() == null) {
			internalProcess(abCmd);
		}

		if (!(abCmd instanceof InvalidCommand || abCmd instanceof BroadcastMessageCommand)) {
			UserSession.storePreviousCommand(abCmd);
		}
	}

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		String error = abCmd.getErrorMessage();
		if (error != null) {
			log
					.error("Error in AbstractProcessor.beforeProcess: "
							+ error);

			StringBuffer msgBuf = new StringBuffer();
			msgBuf.append(abCmd.getI18NMessage("error.prompt"));
			msgBuf.append(abCmd.getErrorMessage());

			sendBackMessage(abCmd, msgBuf.toString());
		}
	}

	protected abstract void internalProcess(AbstractCommand abCmd)
			throws XMPPException;

	protected void sendBackMessage(AbstractCommand abCmd, String message)
			throws XMPPException {
		UserEntry userEntry = abCmd.getUserEntry();
		sendMessage(message, userEntry);
	}

	protected void sendMessage(String message, UserEntry userEntry)
			throws XMPPException {
		Chat chat = GlobalContext.getInstance().getChat(userEntry);
		chat.sendMessage(message);
	}

	protected void broadcastMessage(AbstractCommand abCmd, String message)
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
			if (userEntry.isChattable()) {
				sendMessage(message, userEntry);
			}
		}
	}
}
