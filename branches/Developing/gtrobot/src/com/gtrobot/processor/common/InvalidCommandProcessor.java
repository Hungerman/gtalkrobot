package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;

public class InvalidCommandProcessor extends HelpProcessor {

    @Override
    public void internalProcess(final BaseCommand cmd) throws XMPPException {

        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.getI18NMessage("invalid.command"));
        msgBuf.append(cmd.getOriginMessage());
        msgBuf.append(AbstractProcessor.endl);
        this.sendBackMessage(cmd, msgBuf.toString());

        super.internalProcess(cmd);
    }

}
