package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;
import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.MailSenderCommand;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.service.common.MailEngine;

public class MailSenderProcessor extends AbstractProcessor {
    private MailEngine mailEngine;

    public void setMailEngine(final MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Override
    protected void internalProcess(final BaseCommand abCmd)
            throws XMPPException {
        final MailSenderCommand cmd = (MailSenderCommand) abCmd;
        final SimpleMailMessage mailMessage = cmd.getMailMessage();
        if (mailMessage == null) {
            return;
        }
        try {
            if (cmd.getMailTemplateName() == null) {
                this.mailEngine.send(mailMessage);
            } else {
                this.mailEngine.sendMessage(mailMessage, "invitation.vm", cmd
                        .getMailTemplateModel());
            }
        } catch (final Exception e) {
            AbstractProcessor.log.error("Exception in sending mail to:"
                    + mailMessage.getCc());
        }
    }
}
