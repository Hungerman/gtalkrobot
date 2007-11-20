package com.gtrobot.processor.common;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.processor.InteractiveProcessor;

public class RoomChatProcessor extends InteractiveProcessor {
    private static final int STEP_TO_NORMAL_CHAT = 1000;

    private static final SortedSet<String> activeUserList = new TreeSet<String>();

    // The interactiveProcess_0 are skipped
    protected int interactiveProcess_0(final ProcessableCommand cmd)
            throws XMPPException {

        RoomChatProcessor.activeUserList.add(cmd.getUserEntry().getJid());

        StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.seperator);
        msgBuf.append(AbstractProcessor.endl);
        msgBuf.append(AbstractProcessor.getI18NMessage("roomchat.welcome"));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());

        msgBuf = new StringBuffer();
        msgBuf.append("## #> ");
        msgBuf.append(cmd.getUserEntry().getNickName());
        msgBuf.append(AbstractProcessor.getI18NMessage("roomchat.came.in"));
        this.broadcastMessage(cmd.getUserEntry(), msgBuf.toString());
        return super.interactiveProcess(cmd);
    }

    protected int interactiveProcessPrompt_10(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf
                .append(AbstractProcessor
                        .getI18NMessage("roomchat.menu.welcome"));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return RoomChatProcessor.STEP_TO_NORMAL_CHAT;
    }

    /**
     * STEP_TO_NORMAL_CHAT
     */
    protected int interactiveProcessPrompt_1000(final ProcessableCommand cmd)
            throws XMPPException {
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final UserEntry sender = cmd.getUserEntry();
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("【");
        msgBuf.append(sender.getNickName());
        msgBuf.append("】 ");
        msgBuf.append(cmd.getOriginMessage());

        this.broadcastMessage(cmd.getUserEntry(), msgBuf.toString());
        return InteractiveProcessor.CONTINUE;
    }

    protected void broadcastMessage(final UserEntry sender, final String message)
            throws XMPPException {
        final Iterator userList = RoomChatProcessor.activeUserList.iterator();
        while (userList.hasNext()) {
            final String jid = (String) userList.next();
            if (jid.equals(sender.getJid()) && !sender.isEchoable()) {
                continue;
            }
            final UserEntry userEntry = GTRobotContextHelper
                    .getUserEntryService().getUserEntry(jid);
            this.sendMessage(message, userEntry);
        }
    }

    protected int interactiveProcess_1001(final ProcessableCommand cmd)
            throws XMPPException {
        return RoomChatProcessor.STEP_TO_NORMAL_CHAT;
    }

    @Override
    protected void exitInteractiveProcess(final BaseCommand cmd)
            throws XMPPException {
        final String jid = cmd.getUserEntry().getJid();
        RoomChatProcessor.activeUserList.remove(jid);

        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append("## #> ");
        msgBuf.append(cmd.getUserEntry().getNickName());
        msgBuf.append(AbstractProcessor.getI18NMessage("roomchat.left"));
        this.broadcastMessage(cmd.getUserEntry(), msgBuf.toString());

        return;
    }
}
