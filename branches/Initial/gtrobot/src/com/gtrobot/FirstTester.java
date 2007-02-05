package com.gtrobot;

import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.GoogleTalkConnection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class FirstTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GoogleTalkConnection con;
		try {
			// XMPPConnection.DEBUG_ENABLED = true;
			con = createConnection();
			addRosterListener(con);
			addPacketListener(con);

			 Chat chat = con.createChat("sheena.lv@gmail.com");
			 chat.sendMessage("Howdy!");
			 Message message = chat.nextMessage();
			 System.out.println(message.getBody());

			while (true) {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static GoogleTalkConnection createConnection() throws XMPPException {
		GoogleTalkConnection con;
		con = new GoogleTalkConnection();
		con.login("joey.at.japan", "mengyang");
		return con;
	}

	private static void addRosterListener(GoogleTalkConnection con)
			throws XMPPException {
		final Roster roster = con.getRoster();
		roster.setSubscriptionMode(Roster.SUBSCRIPTION_ACCEPT_ALL);

		roster.addRosterListener(new RosterListener() {
			public void presenceChanged(String user) {
				// If the presence is unavailable then "null" will be printed,
				// which is fine for this example.
				System.out.println("Presence changed: " + user + " : "
						+ roster.getPresence(user));
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

			/**
			 * @param userList
			 */
			private void showMessage(Collection userList, String message) {
				if (userList == null)
					return;
				Iterator it = userList.iterator();
				{
					Object user = it.next();
					System.out.println(message + " :" + user);
				}
			}
		});
	}

	private static void addPacketListener(GoogleTalkConnection connection) {
		// Create a packet filter to listen for new messages from a particular
		// user. We use an AndFilter to combine two other filters.
		// PacketFilter filter = new AndFilter(
		// new PacketTypeFilter(Message.class), new FromContainsFilter(
		// "mary@jivesoftware.com"));
		PacketFilter filter = new PacketTypeFilter(Message.class);
		// Assume we've created an XMPPConnection name "connection".

		// First, register a packet collector using the filter we created.
		// PacketCollector myCollector =
		// connection.createPacketCollector(filter);
		// Normally, you'd do something with the collector, like wait for new
		// packets.

		// Next, create a packet listener. We use an anonymous inner class for
		// brevity.
		PacketListener myListener = new PacketListener() {
			public void processPacket(Packet packet) {
				Message message = (Message) packet;
				System.out.println("Message       from: " + message.getFrom());
				System.out.println("                to: " + message.getTo());
				System.out
						.println("          threadId: " + message.getThread());
				System.out.println("          packetId: "
						+ message.getPacketID());
				System.out.println("              type: "
						+ message.getType().toString());
				Iterator propertyNames = message.getPropertyNames();
				while (propertyNames.hasNext()) {
					String name = (String) propertyNames.next();
					System.out.println("          property: " + name + " : "
							+ message.getProperty(name));
				}
				System.out.println("             class: "
						+ message.getClass().getName());
				System.out.println("             error: " + message.getError());
				System.out.println("           subject: "
						+ message.getSubject());
				System.out.println("              body: " + message.getBody());
			}
		};

		// Register the listener.
		connection.addPacketListener(myListener, filter);
	}

}
