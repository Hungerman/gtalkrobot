package com.gtrobot;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.GoogleTalkConnection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.context.CacheContext;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.context.UserEntry;
import com.gtrobot.utils.GTRDataSource;
import com.gtrobot.utils.GTRobotConfiguration;

/**
 * Main enterance of GTRobot. <br>
 * Initialize configuration, cache, database connection, XMPPConnection, and
 * roster listener, packet listener.
 * 
 * 
 * @author sunyuxin
 * 
 */
public class GTRobot {
	protected static final transient Log log = LogFactory.getLog(GTRobot.class);

	public static void main(String[] args) {
		GTRobot gtrobot = new GTRobot();
		boolean success = gtrobot.startup();
		if (!success) {
			log.error("GTRobot startup failed!");
			return;
		}
		gtrobot.run();
	}

	private boolean startup() {
		try {
			// Load and check the system paramter
			GTRobotConfiguration gtrc = GTRobotConfiguration.getInstance();
			if (!gtrc.isInitialized())
				return false;

			// Setup cache manager
			CacheContext.getInstance();

			// Setup and check the data connection pool
			GTRDataSource ds = GTRDataSource.getInstance();
			ds.getConnection();
			ds.printDataSourceStats();

			// Setup the XMPP connection
			//XMPPConnection.DEBUG_ENABLED = true;
			GoogleTalkConnection gtConnection = createConnection();
			GlobalContext.getInstance().setConnection(gtConnection);

			updatePresence(gtConnection);
			initRosterListener(gtConnection);
			initPacketListener(gtConnection);
			return true;
		} catch (Exception e) {
			log.error("System error!", e);
			return false;
		}
	}

	private void run() {
		try {
			while (true) {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					log.error("InterruptedException!", e);
				}
			}
		} catch (Exception e) {
			log.error("System error!", e);
		}
	}

	private GoogleTalkConnection createConnection() throws XMPPException {
		GTRobotConfiguration gtrc = GTRobotConfiguration.getInstance();
		String username = gtrc.getUsername();
		String password = gtrc.getPassword();

		GoogleTalkConnection con;
		con = new GoogleTalkConnection();
		con.login(username, password);
		return con;
	}

	private void updatePresence(XMPPConnection conn) throws XMPPException {
		// Create a new presence.
		Presence presence = new Presence(Presence.Type.AVAILABLE);
		presence.setMode(Presence.Mode.AVAILABLE);
		presence.setStatus("Ready for chatting...");

		// Send the packet
		conn.sendPacket(presence);
	}

	private void initRosterListener(GoogleTalkConnection con)
			throws XMPPException {
		final Roster roster = con.getRoster();
		roster.setSubscriptionMode(Roster.SUBSCRIPTION_ACCEPT_ALL);

		roster.addRosterListener(new RosterListener() {
			public void presenceChanged(String jid) {
				updateUserStatus(jid, "Presence changed");
			}

			public void entriesAdded(Collection userList) {
				showMessage(userList, "Entries added");
			}

			public void entriesUpdated(Collection userList) {
				showMessage(userList, "Entries updated");
			}

			public void entriesDeleted(Collection userList) {
				showMessage(userList, "Entries deleted");
			}

			private void showMessage(Collection userList, String message) {
				if (userList == null)
					return;
				Iterator it = userList.iterator();
				{
					String jid = (String) it.next();
					updateUserStatus(jid, message);
				}
			}

			private void updateUserStatus(String jid, String message) {
				jid = StringUtils.parseBareAddress(jid);
				log.info(message + " :" + jid);

				// Update the user information
				Presence presence = roster.getPresence(jid);
				UserEntry userEntry = GlobalContext.getInstance().getUser(jid);
				if (presence == null
						|| Presence.Type.UNAVAILABLE.equals(presence.getType())) {
					// User status: AVAILABLE -> UNAVAILABLE
					if (userEntry.getStatus() == UserEntry.AVAILABLE) {
						userEntry.setStatus(UserEntry.UNAVAILABLE);
						GlobalContext.getInstance().updateUserStatus(userEntry);
					}
				} else {
					// User status: * -> AVAILABLE
					if (userEntry.getStatus() == UserEntry.UNAVAILABLE) {
						// User status: UNAVAILABLE -> AVAILABLE
						userEntry.setStatus(UserEntry.AVAILABLE);
						GlobalContext.getInstance().updateUserStatus(userEntry);
					}
				}
			}
		});
	}

	private void initPacketListener(GoogleTalkConnection connection) {
		// Create the filter for all Message information
		PacketFilter filter = new PacketTypeFilter(Message.class);

		// Only Message packet will come here
		PacketListener listener = new PacketListener() {
			public void processPacket(Packet packet) {
				Message message = (Message) packet;

				// Parse the message to command object
				AbstractCommand command = CommadParser.parser(message);
				// Process the command
				CommadProcessor.process(command);
			}
		};

		// Register the listener with filter
		connection.addPacketListener(listener, filter);
	}
}
