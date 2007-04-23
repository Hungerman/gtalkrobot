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

/**
 * GoogleTalkConnection的管理类。<br>
 * 封装了初始化Connection需要的所有参数，这些参数通过Spring的Context进行传递过来。<br>
 * 具体定义在gtrobot-command-context.xml和gtrobot.properties中。
 * <p>
 * 在GTRobotStarter中会调用init方法来初始化这个Connection的相关内容。包括设定对应的Listener。
 * 
 * @author Joey
 */
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

	/**
	 * 构造Connection需要的对象，并进行初始化和连接。然后添加GTRobotConnectionListener，GTRobotMessageListener和GTRobotRosterListener。
	 * 
	 * @throws XMPPException
	 */
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

	/**
	 * 发送Robot的状态信息。
	 * 
	 * @param conn
	 * @throws XMPPException
	 */
	private void updatePresence(XMPPConnection conn) throws XMPPException {
		// Create a new presence.
		Presence presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.available);
		presence.setStatus(MessageHelper.getMessage("status.prompt"));

		// Send the packet
		conn.sendPacket(presence);
	}
}
