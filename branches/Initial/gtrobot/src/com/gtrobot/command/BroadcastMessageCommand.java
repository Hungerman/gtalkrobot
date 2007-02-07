package com.gtrobot.command;

public class BroadcastMessageCommand extends AbstractCommand {
	private String messageContent;

	public BroadcastMessageCommand(String jid, String messageContent) {
		super(jid);
		this.messageContent = messageContent;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
