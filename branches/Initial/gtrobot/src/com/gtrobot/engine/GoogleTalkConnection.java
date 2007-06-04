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
        return this.host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(final String resource) {
        this.resource = resource;
    }

    public boolean isSendPresence() {
        return this.sendPresence;
    }

    public void setSendPresence(final boolean sendPresence) {
        this.sendPresence = sendPresence;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public XMPPConnection getXMPPConnection() {
        return this.xMPPConnection;
    }

    /**
     * 构造Connection需要的对象，并进行初始化和连接。然后添加GTRobotConnectionListener，GTRobotMessageListener和GTRobotRosterListener。
     * 
     * @throws XMPPException
     */
    public void init() throws XMPPException {
        final ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(
                this.host, this.port, this.serviceName);
        this.xMPPConnection = new XMPPConnection(connectionConfiguration);
        this.xMPPConnection.connect();
        this.xMPPConnection.login(this.username, this.password, this.resource,
                this.sendPresence);
        this.updatePresence(this.xMPPConnection);

        // Register the listener with connection
        this.xMPPConnection
                .addConnectionListener(new GTRobotConnectionListener());

        // Create the filter for all Message information
        final PacketFilter filter = new PacketTypeFilter(Message.class);

        // Register the listener with filter
        this.xMPPConnection.addPacketListener(new GTRobotMessageListener(),
                filter);

        final Roster roster = this.xMPPConnection.getRoster();
        roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
        roster.addRosterListener(new GTRobotRosterListener(roster));
    }

    public void destroy() {
        if (this.xMPPConnection != null) {
            this.xMPPConnection.disconnect();
        }
    }

    /**
     * 发送Robot的状态信息。
     * 
     * @param conn
     * @throws XMPPException
     */
    private void updatePresence(final XMPPConnection conn) throws XMPPException {
        // Create a new presence.
        final Presence presence = new Presence(Presence.Type.available);
        presence.setMode(Presence.Mode.available);
        presence.setStatus(MessageHelper.getMessage("status.prompt"));

        // Send the packet
        conn.sendPacket(presence);
    }
}
