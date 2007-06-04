package com.gtrobot.service.common;

import java.util.Collection;

import org.jivesoftware.smack.packet.Presence;

import com.gtrobot.model.common.UserEntry;

/**
 * Manager all user information. Mantenant the activeUserList. When user status
 * change event coming, this context will be invoked to update the user's
 * status.
 * 
 * 
 * @author sunyuxin
 * 
 */
public interface UserEntryService {
    public Collection<String> getAllActiveUsers();

    public UserEntry getUserEntry(String jid);

    public boolean updateNickname(UserEntry userEntry, String newNickname);

    public void saveUserEntry(UserEntry userEntry);

    public void updateUserEntryPresence(UserEntry userEntry, Presence presence);
}
