package com.gtrobot.processor.common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.XMPPException;
import org.springframework.mail.SimpleMailMessage;

import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.command.common.MailSenderCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.thread.WorkerDispatcher;

public class InviteFriendsProcessor extends InteractiveProcessor {
	private SimpleMailMessage mailMessage;

	private String gtrobotUsername;

	private String gtrobotserviceName;

	private static final int STEP_TO_INVITE = 1000;

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
		msgBuf.append(getI18NMessage("invite.welcome"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return STEP_TO_INVITE;
	}

	/**
	 * STEP_TO_INVITE
	 */
	protected int interactiveProcessPrompt_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		msgBuf.append(getI18NMessage("invite.prompt"));
		msgBuf.append(endl);

		sendBackMessage(cmd, msgBuf.toString());
		return WAIT_INPUT;
	}

	protected int interactiveProcess_1000(ProcessableCommand cmd)
			throws XMPPException {
		StringBuffer msgBuf = new StringBuffer();
		List<String> argv = cmd.getArgv();

		List<String> ccList = new ArrayList<String>();
		List<String> existedList = new ArrayList<String>();
		List<String> badList = new ArrayList<String>();
		for (int i = 0; i < argv.size(); i++) {
			String target = argv.get(i).toLowerCase();
			if (target.indexOf('@') >= 0) {
				if (target.indexOf('@') != target.lastIndexOf('@')
						|| !target.endsWith("@" + gtrobotserviceName)) {
					badList.add(target);
				} else {
					ccList.add(target);
				}
			} else {
				ccList.add(target + "@" + gtrobotserviceName);
			}
		}
		//TODO check the user has been in GTRObot
		if (ccList.size() > 0) {
			String[] cc = new String[ccList.size()];
			ccList.toArray(cc);
			mailMessage.setCc(cc);
			mailMessage.setSubject("[GTRobot's Invitation] from "
					+ cmd.getUserEntry().getNickName() + "<"
					+ cmd.getUserEntry().getJid() + ">");
			mailMessage.setReplyTo(cmd.getUserEntry().getJid());

			Map<String, String> model = new Hashtable<String, String>();
			model.put("fromNickName", cmd.getUserEntry().getNickName());
			model.put("fromJid", cmd.getUserEntry().getJid());
			model.put("GTRobotGmail", gtrobotUsername + "@"
					+ gtrobotserviceName);

			MailSenderCommand mailSenderCommand = GTRobotContextHelper
					.getMailSenderCommand();
			mailSenderCommand.setMailMessage(mailMessage);
			mailSenderCommand.setMailTemplateName("invitation.vm");
			mailSenderCommand.setMailTemplateModel(model);

			WorkerDispatcher workerDispatcher = GTRobotContextHelper
					.getWorkerDispatcher();
			workerDispatcher.triggerEvent(mailSenderCommand);

			msgBuf.append(getI18NMessage("invite.result.sent")).append(endl);
			for (Iterator<String> it = ccList.iterator(); it.hasNext();) {
				msgBuf.append(it.next()).append("; ");
			}
			msgBuf.append(endl);
		} else {
			msgBuf.append(getI18NMessage("invite.result.notsent")).append(endl);
		}
		if (badList.size() > 0) {
			msgBuf.append(getI18NMessage("invite.result.bad")).append(endl);
			for (Iterator<String> it = badList.iterator(); it.hasNext();) {
				msgBuf.append(it.next()).append("; ");
			}
			msgBuf.append(endl);
		}

		sendBackMessage(cmd, msgBuf.toString());
		return CONTINUE;
	}

	protected int interactiveProcess_1001(ProcessableCommand cmd)
			throws XMPPException {
		return STEP_TO_INVITE;
	}
}
