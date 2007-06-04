package com.gtrobot.test;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.service.common.MailEngine;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public class MailSender {
    protected static final Log log = LogFactory.getLog(MailSender.class);

    private SimpleMailMessage mailMessage;

    private MailEngine mailEngine;

    public MailSender() {
        GTRobotContextHelper.initApplicationContext();

        this.mailMessage = (SimpleMailMessage) GTRobotContextHelper
                .getBean("mailMessage");
        this.mailEngine = (MailEngine) GTRobotContextHelper
                .getBean("mailEngine");
    }

    private void testSend() {
        this.mailMessage.setCc("reshine@gmail.com");
        this.mailMessage.setSubject("GTRobot's Invitation");

        final Map<String, String> model = new Hashtable<String, String>();
        model.put("message", "Hello, this is a test from GTRobot.111");
        this.mailEngine.sendMessage(this.mailMessage, "invitation.vm", model);
    }

    public static final void main(final String[] argv) throws IOException {
        final MailSender mailSender = new MailSender();

        mailSender.testSend();

        System.exit(0);
    }
}
