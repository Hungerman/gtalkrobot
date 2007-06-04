package com.gtrobot.processor.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.XMPPException;
import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.command.common.MailSenderCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.thread.WorkerDispatcher;

public class InviteFriendsProcessor extends InteractiveProcessor {
    private SimpleMailMessage mailMessage;

    private String gtrobotUsername;

    private String gtrobotserviceName;

    private static final int STEP_TO_INVITE = 1000;

    public void setMailMessage(final SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setGtrobotUsername(final String gtrobotUsername) {
        this.gtrobotUsername = gtrobotUsername;
    }

    public void setGtrobotserviceName(final String gtrobotserviceName) {
        this.gtrobotserviceName = gtrobotserviceName;
    }

    protected int interactiveProcessPrompt_10(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.getI18NMessage("invite.welcome"));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InviteFriendsProcessor.STEP_TO_INVITE;
    }

    /**
     * STEP_TO_INVITE
     */
    protected int interactiveProcessPrompt_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(AbstractProcessor.getI18NMessage("invite.prompt"));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final List<String> argv = cmd.getArgv();

        final List<String> ccList = new ArrayList<String>();
        // final List<String> existedList = new ArrayList<String>();
        final List<String> badList = new ArrayList<String>();
        for (int i = 0; i < argv.size(); i++) {
            final String target = argv.get(i).toLowerCase();
            if (target.indexOf('@') >= 0) {
                if ((target.indexOf('@') != target.lastIndexOf('@'))
                        || !target.endsWith("@" + this.gtrobotserviceName)) {
                    badList.add(target);
                } else {
                    ccList.add(target);
                }
            } else {
                ccList.add(target + "@" + this.gtrobotserviceName);
            }
        }
        // TODO check the user has been in GTRObot
        if (ccList.size() > 0) {
            final String[] cc = new String[ccList.size()];
            ccList.toArray(cc);
            this.mailMessage.setCc(cc);
            this.mailMessage.setSubject("[GTRobot's Invitation] from "
                    + cmd.getUserEntry().getNickName() + "<"
                    + cmd.getUserEntry().getJid() + ">");
            this.mailMessage.setReplyTo(cmd.getUserEntry().getJid());

            final Map<String, String> model = new Hashtable<String, String>();
            model.put("fromNickName", cmd.getUserEntry().getNickName());
            model.put("fromJid", cmd.getUserEntry().getJid());
            model.put("GTRobotGmail", this.gtrobotUsername + "@"
                    + this.gtrobotserviceName);

            final MailSenderCommand mailSenderCommand = GTRobotContextHelper
                    .getMailSenderCommand();
            mailSenderCommand.setMailMessage(this.mailMessage);
            mailSenderCommand.setMailTemplateName("invitation.vm");
            mailSenderCommand.setMailTemplateModel(model);

            final WorkerDispatcher workerDispatcher = GTRobotContextHelper
                    .getWorkerDispatcher();
            workerDispatcher.triggerEvent(mailSenderCommand);

            msgBuf.append(
                    AbstractProcessor.getI18NMessage("invite.result.sent"))
                    .append(AbstractProcessor.endl);
            for (final String string : ccList) {
                msgBuf.append(string).append("; ");
            }
            msgBuf.append(AbstractProcessor.endl);
        } else {
            msgBuf.append(
                    AbstractProcessor.getI18NMessage("invite.result.notsent"))
                    .append(AbstractProcessor.endl);
        }
        if (badList.size() > 0) {
            msgBuf
                    .append(
                            AbstractProcessor
                                    .getI18NMessage("invite.result.bad"))
                    .append(AbstractProcessor.endl);
            for (final String string : badList) {
                msgBuf.append(string).append("; ");
            }
            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.CONTINUE;
    }

    protected int interactiveProcess_1001(final ProcessableCommand cmd)
            throws XMPPException {
        return InviteFriendsProcessor.STEP_TO_INVITE;
    }
}
