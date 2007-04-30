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

		mailMessage = (SimpleMailMessage) GTRobotContextHelper
				.getBean("mailMessage");
		mailEngine = (MailEngine) GTRobotContextHelper
				.getBean("mailEngine");
	}
	private void testSend() {
		mailMessage.setCc("reshine@gmail.com");
		mailMessage.setSubject("GTRobot's Invitation");
		
		Map<String, String> model = new Hashtable<String, String>();
		model.put("message", "Hello, this is a test from GTRobot.111");
		mailEngine.sendMessage(mailMessage, "invitation.vm",
                model);		
	}
	
	public static final void main(String[] argv) throws IOException {
		MailSender mailSender = new MailSender();

		mailSender.testSend();
		
		System.exit(0);
	}	
}
