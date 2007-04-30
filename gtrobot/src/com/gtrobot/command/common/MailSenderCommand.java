package com.gtrobot.command.common;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.command.ProcessableCommand;

/**
 * 设置Locale的命令对象。
 * 
 * @author Joey
 * 
 */
public class MailSenderCommand extends ProcessableCommand {
	private SimpleMailMessage mailMessage;

	private String mailTemplateName;
	
	private Map mailTemplateModel;

	public SimpleMailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getMailTemplateName() {
		return mailTemplateName;
	}

	public void setMailTemplateName(String templateName) {
		this.mailTemplateName = templateName;
	}

	public Map getMailTemplateModel() {
		return mailTemplateModel;
	}

	public void setMailTemplateModel(Map model) {
		this.mailTemplateModel = model;
	}

}
