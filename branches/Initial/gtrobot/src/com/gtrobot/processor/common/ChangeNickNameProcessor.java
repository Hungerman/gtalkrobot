package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.ChangeNickNameCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.processor.AbstractProcessor;

public class ChangeNickNameProcessor extends AbstractProcessor {

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		ChangeNickNameCommand cmd = (ChangeNickNameCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		boolean result = GTRobotContextHelper.getUserEntryService()
				.updateNickname(cmd.getUserEntry(), cmd.getNewNickName());
		if (result == true) {
			msgBuf.append(getI18NMessage("changenickname.result",
					new Object[] { cmd.getNewNickName() }));

		} else {
			msgBuf.append(getI18NMessage("changenickname.fail",
					new Object[] { cmd.getNewNickName() }));
		}
		msgBuf.append(endl);
		sendBackMessage(abCmd, msgBuf.toString());
	}

}
