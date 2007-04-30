package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;
import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.MailSenderCommand;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.service.common.MailEngine;

public class MailSenderProcessor extends AbstractProcessor {
	private MailEngine mailEngine;

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		MailSenderCommand cmd = (MailSenderCommand) abCmd;
		SimpleMailMessage mailMessage = cmd.getMailMessage();
		if (mailMessage == null)
			return;
		try {
			if (cmd.getMailTemplateName() == null) {
				mailEngine.send(mailMessage);
			} else {
				mailEngine.sendMessage(mailMessage, "invitation.vm", cmd
						.getMailTemplateModel());
			}
		} catch (Exception e) {
			log.error("Exception in sending mail to:" + mailMessage.getCc());
		}
	}
}
