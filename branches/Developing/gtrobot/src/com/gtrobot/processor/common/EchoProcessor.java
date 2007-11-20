package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.SwitchCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class EchoProcessor extends AbstractProcessor {

    @Override
    protected void internalProcess(final BaseCommand abCmd)
            throws XMPPException {
        final SwitchCommand cmd = (SwitchCommand) abCmd;
        final StringBuffer msgBuf = new StringBuffer();

        final UserEntry userEntry = cmd.getUserEntry();

        if (cmd.isOperationON() != userEntry.isEchoable()) {
            userEntry.setEchoable(cmd.isOperationON());
            GTRobotContextHelper.getUserEntryService().saveUserEntry(userEntry);
        }
        if (cmd.isOperationON()) {
            msgBuf.append(AbstractProcessor.getI18NMessage("echo.success.on"));
        } else {
            msgBuf.append(AbstractProcessor.getI18NMessage("echo.success.off"));
        }
        this.sendBackMessage(abCmd, msgBuf.toString());
    }

}
