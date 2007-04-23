package com.gtrobot.processor.common;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.InteractiveProcessor;

public class RoomChatProcessor extends InteractiveProcessor {
	private static final int STEP_TO_NORMAL_CHAT = 1000;

	private static SortedSet<String> activeUserList = new TreeSet<String>();

	// The interactiveProcess_0 are skipped
	protected int interactiveProcess_0(ProcessableCommand cmd)
			throws XMPPException {

		activeUserList.add(cmd.getUserEntry().getJid());

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(seperator);
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("roomchat.welcome"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());

		msgBuf = new StringBuffer();
		msgBuf.append("## #> ");
		msgBuf.append(cmd.getUserEntry().getNickName());
		msgBuf.append(cmd.getI18NMessage("roomchat.came.in"));
		broadcastMessage(cmd.getUserEntry(), msgBuf.toString());
		return super.interactiveProcess(cmd);
	}

	protected int interactiveProcess_10(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(cmd.getI18NMessage("roomchat.menu.welcome"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_11(ProcessableCommand cmd)
			throws XMPPException {
		return STEP_TO_NORMAL_CHAT;
	}

	/**
	 * STEP_TO_NORMAL_CHAT
	 */
	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		UserEntry sender = cmd.getUserEntry();
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(sender.getNickName());
		msgBuf.append(" #> ");
		msgBuf.append(cmd.getOriginMessage());

		broadcastMessage(cmd.getUserEntry(), msgBuf.toString());
		return CONTINUE;
	}

	protected void broadcastMessage(UserEntry sender, String message)
			throws XMPPException {
		Iterator userList = activeUserList.iterator();
		while (userList.hasNext()) {
			String jid = (String) userList.next();
			if (jid.equals(sender.getJid()) && !sender.isEchoable()) {
				continue;
			}
			UserEntry userEntry = GTRobotContextHelper.getUserEntryService()
					.getUserEntry(jid);
			if (userEntry.isChattable()) {
				sendMessage(message, userEntry);
			}
		}
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		return STEP_TO_NORMAL_CHAT;
	}

	protected int interactiveProcess_9999(BaseCommand cmd) throws XMPPException {
		String jid = cmd.getUserEntry().getJid();
		activeUserList.remove(jid);

		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append("## #> ");
		msgBuf.append(cmd.getUserEntry().getNickName());
		msgBuf.append(cmd.getI18NMessage("roomchat.left"));
		broadcastMessage(cmd.getUserEntry(), msgBuf.toString());

		return super.interactiveProcess_9999(cmd);
	}
}
