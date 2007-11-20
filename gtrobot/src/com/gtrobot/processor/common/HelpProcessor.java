package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.utils.UserSessionUtil;

public class HelpProcessor extends AbstractProcessor {

    @Override
    public void internalProcess(final BaseCommand cmd) throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        HelpProcessor.showHelpInfo(msgBuf);

        final String commandType = UserSessionUtil.isInInteractiveOperation(cmd
                .getUserEntry().getJid());
        if (commandType != null) {
            msgBuf.append(AbstractProcessor.endl);

            msgBuf.append(AbstractProcessor
                    .getI18NMessage("status.item.interactive." + commandType));
            msgBuf.append(AbstractProcessor.endl);

            msgBuf.append(AbstractProcessor
                    .getI18NMessage("help.interactive.help"));
            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(cmd, msgBuf.toString());
    }

    public static void showHelpInfo(final StringBuffer msgBuf) {
        msgBuf.append(AbstractProcessor.getI18NMessage("help.welcome",
                new Object[] { AbstractProcessor.getUserEntryHolder()
                        .getNickName() }));

        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.global.prompt"));
        msgBuf
                .append(AbstractProcessor
                        .getI18NMessage("help.immediate.prompt"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.command.help"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.command.lang"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.command.status"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.privatemessage"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.changenickname"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.command.echo"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.searchuser"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.global.prompt"));
        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.interactive.prompt"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.getI18NMessage("help.command.invite"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.sendfeedback"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf
                .append(AbstractProcessor
                        .getI18NMessage("help.command.roomchat"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.studyword"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor
                .getI18NMessage("help.command.wordmanagement"));
        msgBuf.append(AbstractProcessor.endl);

        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("help.message"));
        msgBuf.append(AbstractProcessor.endl);
    }

}
