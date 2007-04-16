package com.gtrobot.engine;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import com.gtrobot.utils.MessageHelper;

public class GoogleTalkConnection {
	private String serviceName;

	private String host;

	private int port;

	private String username;

	private String password;

	private String resource;

	private boolean sendPresence = true;

	private XMPPConnection xMPPConnection = null;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public boolean isSendPresence() {
		return sendPresence;
	}

	public void setSendPresence(boolean sendPresence) {
		this.sendPresence = sendPresence;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public XMPPConnection getXMPPConnection() {
		return xMPPConnection;
	}

	public void init() throws XMPPException {
		ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
				host, port, serviceName);
		xMPPConnection = new XMPPConnection(connectionConfiguration);
		xMPPConnection.connect();
		xMPPConnection.login(username, password, resource, sendPresence);
		updatePresence(xMPPConnection);

		// Register the listener with connection
		xMPPConnection.addConnectionListener(new GTRobotConnectionListener());

		// Create the filter for all Message information
		PacketFilter filter = new PacketTypeFilter(Message.class);

		// Register the listener with filter
		xMPPConnection.addPacketListener(new GTRobotMessageListener(), filter);

		Roster roster = xMPPConnection.getRoster();
		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
		roster.addRosterListener(new GTRobotRosterListener(roster));
	}

	public void destroy() {
		if (xMPPConnection != null)
			xMPPConnection.disconnect();
	}

	private void updatePresence(XMPPConnection conn) throws XMPPException {
		// Create a new presence.
		Presence presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.available);
		presence.setStatus(MessageHelper.getMessage("status.prompt"));

		// Send the packet
		conn.sendPacket(presence);

		// ChatManager chatmanager = conn.getChatManager();
		// chatmanager.
		// Chat newChat = chatmanager.createChat("dragonetail@gmail.com",
		// new MessageListener() {
		//
		// public void processMessage(Chat chat, Message message) {
		// System.out.println("Received message: " + message.getBody());
		// }
		// });
		// try {
		// newChat.sendMessage("Howdy!");
		// } catch (XMPPException e) {
		// System.out.println("Error Delivering block");
		// }
	}
}
