package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.SwitchCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class PublicRoomProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		SwitchCommand cmd = (SwitchCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		UserEntry userEntry = cmd.getUserEntry();

		if (cmd.isOperationON() != userEntry.isEchoable()) {
			userEntry.setEchoable(cmd.isOperationON());
			GlobalContext.getInstance().saveUser(userEntry);
		}
		if (cmd.isOperationON()) {
			if (!userEntry.isChattableInPublicRoom()) {
				userEntry.setChattableInPublicRoom(true);
				ctx.updateUser(userEntry.getJid());
			}

			msgBuf.append(cmd.getI18NMessage("publicroom.available.title"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("publicroom.available.prompt"));
			msgBuf.append(endl);
		} else {
			if (userEntry.isChattableInPublicRoom()) {
				userEntry.setChattableInPublicRoom(false);
				ctx.updateUser(userEntry.getJid());
			}

			msgBuf.append(cmd.getI18NMessage("publicroom.away.title"));
			msgBuf.append(endl);
			msgBuf.append(cmd.getI18NMessage("publicroom.away.prompt"));
			msgBuf.append(endl);
		}
		sendBackMessage(abCmd, msgBuf.toString());
	}

}
