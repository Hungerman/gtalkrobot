package com.gtrobot.processor;

import java.util.Iterator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;


public abstract class AbstractProcessor implements Processor {
	protected static final String endl = "\n";

	public void process(AbstractCommand abstractCommand)
			throws CommandMatchedException, XMPPException {
		beforeProcess(abstractCommand);
		internalProcess(abstractCommand);
	}

	protected void beforeProcess(AbstractCommand abstractCommand)
			throws CommandMatchedException {
		return;
	}

	protected abstract void internalProcess(AbstractCommand abstractCommand)
			throws XMPPException;

	protected void sendBackMessage(AbstractCommand abstractCommand,
			String message) throws XMPPException {
		UserEntry userEntry = abstractCommand.getUserEntry();
		sendMessage(message, userEntry);
	}

	private void sendMessage(String message, UserEntry userEntry)
			throws XMPPException {
		Chat chat = GlobalContext.getInstance().getChat(userEntry);
		chat.sendMessage(message);
	}

	protected void broadcastMessage(AbstractCommand abstractCommand,
			String message) throws XMPPException {
		Iterator userList = GlobalContext.getInstance().getUserList();
		while (userList.hasNext()) {
			String user = (String) userList.next();
			UserEntry sender = abstractCommand.getUserEntry();
			if (user.equals(sender.getUser())  && !sender.isEchoable()) {
				continue;
			}
			UserEntry userEntry = GlobalContext.getInstance().getUser(user);
			if (userEntry.isChattable()) {
				sendMessage(message, userEntry);
			}
		}
	}
}
