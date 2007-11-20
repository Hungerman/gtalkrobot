package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.PrivateMessageCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class PrivateMessageProcessor extends AbstractProcessor {

    @Override
    protected void internalProcess(final BaseCommand abCmd)
            throws XMPPException {
        final PrivateMessageCommand cmd = (PrivateMessageCommand) abCmd;
        final StringBuffer msgBuf = new StringBuffer();

        final UserEntry targetUserEntry = GTRobotContextHelper
                .getUserEntryService().getUserEntry(cmd.getTargetJid());
        if (targetUserEntry == null) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("privatemessage.error.targetusernotfound"));
            msgBuf.append(cmd.getTargetJid());
            msgBuf.append(AbstractProcessor.endl);
            this.sendBackMessage(abCmd, msgBuf.toString());
        } else {
            final UserEntry sender = cmd.getUserEntry();
            msgBuf.append("[");
            msgBuf.append(StringUtils.parseName(sender.getJid()));
            msgBuf.append("]");
            msgBuf.append(sender.getNickName());
            msgBuf.append(" *pm> ");
            msgBuf.append(cmd.getMessageContent());

            this.sendMessage(msgBuf.toString(), targetUserEntry);
        }
    }

}
