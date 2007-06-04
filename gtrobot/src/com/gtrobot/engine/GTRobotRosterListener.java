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

/**
 * 侦听用户的状态变化，更新用户的状态，发布通知。<br>
 * 需要继续完善。
 * 
 * 
 * @author Joey
 * 
 */
public class GTRobotRosterListener implements RosterListener {
    protected static final transient Log log = LogFactory
            .getLog(GTRobotRosterListener.class);

    private Roster roster = null;

    public GTRobotRosterListener(final Roster roster) {
        super();
        this.roster = roster;
    }

    public void entriesAdded(final Collection<String> userList) {
        this.showMessage(userList, "Entries added");
    }

    public void entriesDeleted(final Collection<String> userList) {
        this.showMessage(userList, "Entries deleted");
    }

    public void entriesUpdated(final Collection<String> userList) {
        this.showMessage(userList, "Entries updated");
    }

    public void presenceChanged(final Presence presence) {
        this.updateUserStatus(presence, "Presence changed");
        // TODO 需要继续完善。 例如用户在ChatRoom，需要通知，等等；相对需要一个复杂的逻辑流程
    }

    private void showMessage(final Collection userList, final String message) {
        try {
            if (userList == null) {
                return;
            }

            final Iterator it = userList.iterator();
            {
                String jid = (String) it.next();
                jid = StringUtils.parseBareAddress(jid);
                GTRobotRosterListener.log.info(message + " :" + jid);
                final Presence presence = this.roster.getPresence(jid);
                this.updateUserStatus(presence, message);
            }
        } catch (final Exception e) {
            GTRobotRosterListener.log.error(
                    "Exception in GTRobotRosterListener.showMessage.", e);
        }
    }

    private void updateUserStatus(final Presence presence, final String message) {
        try {
            final UserEntryService userEntryService = GTRobotContextHelper
                    .getUserEntryService();
            // Update the user information
            final UserEntry userEntry = userEntryService.getUserEntry(presence
                    .getFrom());

            userEntryService.updateUserEntryPresence(userEntry, presence);
        } catch (final Exception e) {
            GTRobotRosterListener.log.error(
                    "Exception in GTRobotRosterListener.updateUserStatus.", e);
        }
    }

}
