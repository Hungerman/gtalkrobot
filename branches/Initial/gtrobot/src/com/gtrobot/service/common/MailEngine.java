package com.gtrobot.service.common;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 * 
 * <p>
 * <a href="MailEngine.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Matt Raible
 */
public class MailEngine {
    protected static final Log log = LogFactory.getLog(MailEngine.class);

    private MailSender mailSender;

    private VelocityEngine velocityEngine;

    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * Send a simple message based on a Velocity template.
     * 
     * @param msg
     * @param templateName
     * @param model
     */
    public void sendMessage(final SimpleMailMessage msg,
            final String templateName, final Map model) {
        String result = null;

        try {
            result = VelocityEngineUtils.mergeTemplateIntoString(
                    this.velocityEngine, templateName, model);
        } catch (final VelocityException e) {
            e.printStackTrace();
        }

        msg.setText(result);
        this.send(msg);
    }

    /**
     * Send a simple message with pre-populated values.
     * 
     * @param msg
     */
    public void send(final SimpleMailMessage msg) {
        try {
            this.mailSender.send(msg);
        } catch (final MailException ex) {
            // log it and go on
            MailEngine.log.error(ex.getMessage());
        }
    }

    /**
     * Convenience method for sending messages with attachments.
     * 
     * @param emailAddresses
     * @param resource
     * @param bodyText
     * @param subject
     * @param attachmentName
     * @throws MessagingException
     * @author Ben Gill
     */
    public void sendMessage(final String[] emailAddresses,
            final ClassPathResource resource, final String bodyText,
            final String subject, final String attachmentName)
            throws MessagingException {
        final MimeMessage message = ((JavaMailSenderImpl) this.mailSender)
                .createMimeMessage();

        // use the true flag to indicate you need a multipart message
        final MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailAddresses);
        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) this.mailSender).send(message);
    }
}
