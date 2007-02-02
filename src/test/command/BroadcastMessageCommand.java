package test.command;

public class BroadcastMessageCommand extends AbstractCommand {
	private String messageContent;

	public BroadcastMessageCommand(String user, String messageContent) {
		super(user);
		this.messageContent = messageContent;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
