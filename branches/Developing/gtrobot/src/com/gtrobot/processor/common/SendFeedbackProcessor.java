package com.gtrobot.processor.common;

import java.util.Date;
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

public class SendFeedbackProcessor extends InteractiveProcessor {
    private SimpleMailMessage mailMessage;

    private String gtrobotUsername;

    private String gtrobotserviceName;

    private static final int STEP_TO_SEND_FEEDBACK = 1000;

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
        msgBuf.append(AbstractProcessor.getI18NMessage("sendfeedback.welcome"));
        msgBuf.append(AbstractProcessor.endl);

        this.sendBackMessage(cmd, msgBuf.toString());
        return SendFeedbackProcessor.STEP_TO_SEND_FEEDBACK;
    }

    protected StringBuffer interactiveOnlineHelper_1000(
            final ProcessableCommand cmd) {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(
                AbstractProcessor.getI18NMessage("sendfeedback.onlineHelper"))
                .append(AbstractProcessor.endl);

        return msgBuf;
    }

    protected int interactiveOnlineProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();
        final List<String> cmds = cmd.getInteractiveCommands();
        if (cmds == null) {
            return InteractiveProcessor.CONTINUE;
        }
        final String cmdMsg = cmds.get(0);

        if (cmds.size() == 1) {
            if (".s".equalsIgnoreCase(cmdMsg)) {
                final String content = (String) this.getSession();
                if ((content != null) && (content.length() != 0)) {
                    this.mailMessage.setCc(this.gtrobotUsername + "@"
                            + this.gtrobotserviceName);
                    this.mailMessage.setSubject("[GTRobot's Feedback] from "
                            + cmd.getUserEntry().getNickName() + "<"
                            + cmd.getUserEntry().getJid() + ">");
                    this.mailMessage.setReplyTo(cmd.getUserEntry().getJid());

                    final Map<String, Object> model = new Hashtable<String, Object>();
                    model.put("fromNickName", cmd.getUserEntry().getNickName());
                    model.put("fromJid", cmd.getUserEntry().getJid());
                    model.put("content", content);
                    model.put("date", new Date());

                    final MailSenderCommand mailSenderCommand = GTRobotContextHelper
                            .getMailSenderCommand();
                    mailSenderCommand.setMailMessage(this.mailMessage);
                    mailSenderCommand.setMailTemplateName("feedback.vm");
                    mailSenderCommand.setMailTemplateModel(model);

                    final WorkerDispatcher workerDispatcher = GTRobotContextHelper
                            .getWorkerDispatcher();
                    workerDispatcher.triggerEvent(mailSenderCommand);

                    msgBuf.append(
                            AbstractProcessor
                                    .getI18NMessage("sendfeedback.sent"))
                            .append(AbstractProcessor.endl);
                    this.sendBackMessage(cmd, msgBuf.toString());
                    return InteractiveProcessor.STEP_TO_EXIT_MAIN_MENU;
                } else {
                    msgBuf.append(
                            AbstractProcessor
                                    .getI18NMessage("sendfeedback.nosent"))
                            .append(AbstractProcessor.endl);
                    this.sendBackMessage(cmd, msgBuf.toString());
                }
            }
        }

        return InteractiveProcessor.CONTINUE;
    }

    /**
     * STEP_TO_SEND_FEEDBACK
     */
    protected int interactiveProcessPrompt_1000(final ProcessableCommand cmd)
            throws XMPPException {
        final StringBuffer msgBuf = new StringBuffer();

        final String content = (String) this.getSession();
        if (content != null) {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("sendfeedback.prompt.content"));
            msgBuf.append(AbstractProcessor.endl);
            msgBuf.append(content);
            msgBuf.append(AbstractProcessor.endl);
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("sendfeedback.prompt.withcontent"));
            msgBuf.append(AbstractProcessor.endl);
        } else {
            msgBuf.append(AbstractProcessor
                    .getI18NMessage("sendfeedback.prompt.nocontent"));
            msgBuf.append(AbstractProcessor.endl);
        }

        this.sendBackMessage(cmd, msgBuf.toString());
        return InteractiveProcessor.WAIT_INPUT;
    }

    protected int interactiveProcess_1000(final ProcessableCommand cmd)
            throws XMPPException {

        this.setSession(cmd.getOriginMessage());

        return InteractiveProcessor.CONTINUE;
    }

    protected int interactiveProcess_1001(final ProcessableCommand cmd)
            throws XMPPException {
        return SendFeedbackProcessor.STEP_TO_SEND_FEEDBACK;
    }
}
