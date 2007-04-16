package com.gtrobot.engine;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.model.common.UserEntry;
import com.gtrobot.service.common.UserEntryService;

public class GTRobotRosterListener implements RosterListener {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotRosterListener.class);

	private Roster roster = null;

	public GTRobotRosterListener(Roster roster) {
		super();
		this.roster = roster;
	}

	public void entriesAdded(Collection<String> userList) {
		showMessage(userList, "Entries added");
	}

	public void entriesDeleted(Collection<String> userList) {
		showMessage(userList, "Entries deleted");
	}

	public void entriesUpdated(Collection<String> userList) {
		showMessage(userList, "Entries updated");
	}

	public void presenceChanged(Presence presence) {
		updateUserStatus(presence, "Presence changed");
	}

	private void showMessage(Collection userList, String message) {
		try {
			if (userList == null)
				return;

			Iterator it = userList.iterator();
			{
				String jid = (String) it.next();
				jid = StringUtils.parseBareAddress(jid);
				log.info(message + " :" + jid);
				Presence presence = roster.getPresence(jid);
				updateUserStatus(presence, message);
			}
		} catch (Exception e) {
			log.error("Exception in GTRobotRosterListener.showMessage.", e);
		}
	}

	private void updateUserStatus(Presence presence, String message) {
		try {
			UserEntryService userEntryService = GTRobotContextHelper
					.getUserEntryService();
			// Update the user information
			UserEntry userEntry = userEntryService.getUserEntry(presence
					.getFrom());

			userEntryService.updateUserEntryPresence(userEntry, presence);
		} catch (Exception e) {
			log
					.error(
							"Exception in GTRobotRosterListener.updateUserStatus.",
							e);
		}
	}

}
