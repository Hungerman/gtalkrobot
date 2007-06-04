package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.utils.UserSessionUtil;

public class StatusProcessor extends AbstractProcessor {

    @Override
    protected void internalProcess(final BaseCommand cmd) throws XMPPException {
        final UserEntry userEntry = cmd.getUserEntry();

        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.getI18NMessage("status.welcome"));
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("status.item.jid"));
        msgBuf.append(userEntry.getJid());
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("status.item.nickname"));
        msgBuf.append(userEntry.getNickName());
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("status.item.lang"));
        msgBuf.append(userEntry.getLocale().getDisplayLanguage(
                userEntry.getLocale()));
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("status.item.echoable"));
        msgBuf.append(userEntry.isEchoable());
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.endl);
        final String commandType = UserSessionUtil
                .isInInteractiveOperation(userEntry.getJid());
        if (commandType != null) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("status.item.interactive." + commandType));
            msgBuf.append(AbstractProcessor.endl);
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("status.item.interactive.mainmenu"));
            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(cmd, msgBuf.toString());
    }
}
