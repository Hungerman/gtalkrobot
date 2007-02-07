package com.gtrobot;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.GoogleTalkConnection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;


import com.gtrobot.command.AbstractCommand;
import com.gtrobot.context.GlobalContext;
import com.gtrobot.utils.MessageUtil;

public class GTRobot {
	protected static final transient Log log = LogFactory.getLog(GTRobot.class);

	public static void main(String[] args) {
		GoogleTalkConnection con;
		try {
			// XMPPConnection.DEBUG_ENABLED = true;
			con = createConnection();
			GlobalContext.getInstance().setConnection(con);

			initRosterListener(con);
			addPacketListener(con);

			while (true) {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					log.error("InterruptedException!", e);
				}
			}
		} catch (XMPPException e) {
			log.error("System error!", e);
		}
	}

	private static GoogleTalkConnection createConnection() throws XMPPException {
		MessageUtil messageUtil = MessageUtil.getInstance();
		String username = messageUtil.getMessage("gtrobot.username");
		String password = messageUtil.getMessage("gtrobot.password");

		GoogleTalkConnection con;
		con = new GoogleTalkConnection();
		con.login(username, password);
		return con;
	}

	private static void initRosterListener(GoogleTalkConnection con)
			throws XMPPException {
		final Roster roster = con.getRoster();
		roster.setSubscriptionMode(Roster.SUBSCRIPTION_ACCEPT_ALL);

		roster.addRosterListener(new RosterListener() {
			public void presenceChanged(String user) {
				log.info("Presence changed: " + user + " : "
						+ roster.getPresence(user));

				// Cache the user information
				GlobalContext.getInstance().updateUser(user);
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
					Object user = it.next();
					log.info(message + " :" + user);

					// Cache the user information
					GlobalContext.getInstance().updateUser((String) user);
				}
			}
		});
	}

	private static void addPacketListener(GoogleTalkConnection connection) {
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
