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
        return this.mailMessage;
    }

    public void setMailMessage(final SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public String getMailTemplateName() {
        return this.mailTemplateName;
    }

    public void setMailTemplateName(final String templateName) {
        this.mailTemplateName = templateName;
    }

    public Map getMailTemplateModel() {
        return this.mailTemplateModel;
    }

    public void setMailTemplateModel(final Map model) {
        this.mailTemplateModel = model;
    }

}
