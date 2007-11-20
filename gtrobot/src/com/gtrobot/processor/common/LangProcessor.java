package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.LangCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;

public class LangProcessor extends AbstractProcessor {

    @Override
    protected void internalProcess(final BaseCommand abCmd)
            throws XMPPException {
        final LangCommand cmd = (LangCommand) abCmd;
        final StringBuffer msgBuf = new StringBuffer();

        final UserEntry userEntry = cmd.getUserEntry();
        if (!cmd.getOperationLocale().equals(userEntry.getLocale())) {
            userEntry.setLocale(cmd.getOperationLocale());
            GTRobotContextHelper.getUserEntryService().saveUserEntry(userEntry);
        }
        msgBuf.append(AbstractProcessor.getI18NMessage("lang.success"));
        msgBuf.append(userEntry.getLocale().getDisplayLanguage(
                userEntry.getLocale()));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(abCmd, msgBuf.toString());
    }

}
