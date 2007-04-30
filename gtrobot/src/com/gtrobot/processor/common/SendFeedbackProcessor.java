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
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.thread.WorkerDispatcher;

public class SendFeedbackProcessor extends InteractiveProcessor {
	private SimpleMailMessage mailMessage;

	private String gtrobotUsername;

	private String gtrobotserviceName;

	private static final int STEP_TO_SEND_FEEDBACK = 1000;

	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setGtrobotUsername(String gtrobotUsername) {
		this.gtrobotUsername = gtrobotUsername;
	}

	public void setGtrobotserviceName(String gtrobotserviceName) {
		this.gtrobotserviceName = gtrobotserviceName;
	}

	protected int interactiveProcessPrompt_10(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(getI18NMessage("sendfeedback.welcome"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_SEND_FEEDBACK;
	}

	protected StringBuffer interactiveOnlineHelper_1000(ProcessableCommand cmd) {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(getI18NMessage("sendfeedback.onlineHelper")).append(endl);

		return msgBuf;
	}

	protected int interactiveOnlineProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> cmds = cmd.getInteractiveCommands();
		if (cmds == null) {
			return CONTINUE;
		}
		String cmdMsg = cmds.get(0);

		if (cmds.size() == 1) {
			if (".s".equalsIgnoreCase(cmdMsg)) {
				String content = (String) getSession();
				if (content != null && content.length() != 0) {
					mailMessage.setCc(gtrobotUsername + "@"
							+ gtrobotserviceName);
					mailMessage.setSubject("[GTRobot's Feedback] from "
							+ cmd.getUserEntry().getNickName() + "<"
							+ cmd.getUserEntry().getJid() + ">");
					mailMessage.setReplyTo(cmd.getUserEntry().getJid());

					Map<String, Object> model = new Hashtable<String, Object>();
					model.put("fromNickName", cmd.getUserEntry().getNickName());
					model.put("fromJid", cmd.getUserEntry().getJid());
					model.put("content", content);
					model.put("date", new Date());

					MailSenderCommand mailSenderCommand = GTRobotContextHelper
							.getMailSenderCommand();
					mailSenderCommand.setMailMessage(mailMessage);
					mailSenderCommand.setMailTemplateName("feedback.vm");
					mailSenderCommand.setMailTemplateModel(model);

					WorkerDispatcher workerDispatcher = GTRobotContextHelper
							.getWorkerDispatcher();
					workerDispatcher.triggerEvent(mailSenderCommand);

					msgBuf.append(getI18NMessage("sendfeedback.sent")).append(
							endl);
					sendBackMessage(cmd, msgBuf.toString());
					return STEP_TO_EXIT_MAIN_MENU;
				} else {
					msgBuf.append(getI18NMessage("sendfeedback.nosent"))
							.append(endl);
					sendBackMessage(cmd, msgBuf.toString());
				}
			}
		}

		return CONTINUE;
	}

	/**
	 * STEP_TO_SEND_FEEDBACK
	 */
	protected int interactiveProcessPrompt_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();

		String content = (String) getSession();
		if (content != null) {
			msgBuf.append(getI18NMessage("sendfeedback.prompt.content"));
			msgBuf.append(endl);
			msgBuf.append(content);
			msgBuf.append(endl);
			msgBuf.append(getI18NMessage("sendfeedback.prompt.withcontent"));
			msgBuf.append(endl);
		} else {
			msgBuf.append(getI18NMessage("sendfeedback.prompt.nocontent"));
			msgBuf.append(endl);
		}

		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {

		setSession(cmd.getOriginMessage());

		return CONTINUE;
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		return STEP_TO_SEND_FEEDBACK;
	}
}
