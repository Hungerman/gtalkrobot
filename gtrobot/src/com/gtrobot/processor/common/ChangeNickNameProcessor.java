package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.ChangeNickNameCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.processor.AbstractProcessor;

public class ChangeNickNameProcessor extends AbstractProcessor {

    @Override
    protected void internalProcess(final BaseCommand abCmd)
            throws XMPPException {
        final ChangeNickNameCommand cmd = (ChangeNickNameCommand) abCmd;
        final StringBuffer msgBuf = new StringBuffer();

        final boolean result = GTRobotContextHelper.getUserEntryService()
                .updateNickname(cmd.getUserEntry(), cmd.getNewNickName());
        if (result == true) {
            msgBuf.append(AbstractProcessor.getI18NMessage(
                    "changenickname.result", new Object[] { cmd
                            .getNewNickName() }));

        } else {
            msgBuf.append(AbstractProcessor.getI18NMessage(
                    "changenickname.fail",
                    new Object[] { cmd.getNewNickName() }));
        }
        msgBuf.append(AbstractProcessor.endl);
        this.sendBackMessage(abCmd, msgBuf.toString());
    }

}
