package com.gtrobot.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;

/**
 * Create a local user list copy of all user list. Also, it can be applied a
 * search condition to filter the user list.
 * 
 * @author sunyuxin
 * 
 */
public class UserSearchFilter {
    private List<String> userList;

    private int count;

    public UserSearchFilter(final Collection<String> sourceUserList,
            final String condition) {
        this.userList = new ArrayList<String>();
        if ((condition == null) || (condition.trim().length() == 0)) {
            UserSearchFilter.copy(sourceUserList, this.userList);
        } else {
            UserSearchFilter.filterCopy(sourceUserList, this.userList,
                    condition.trim());
        }
        this.resetCount();
    }

    public String getUser(final int pos) {
        return this.userList.get(pos);
    }

    public int size() {
        return this.userList.size();
    }

    public void resetCount() {
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    public void storeCount(final int count) {
        this.count = count;
    }

    private static void filterCopy(final Collection<String> sourceUserList,
            final List<String> targetUserList, String condition) {
        final Iterator it = sourceUserList.iterator();
        condition = condition.toLowerCase();
        while (it.hasNext()) {
            final String jid = (String) it.next();
            final UserEntry userEntry = GTRobotContextHelper
                    .getUserEntryService().getUserEntry(jid);
            final int pos1 = jid.toLowerCase().indexOf(condition);
            final int pos2 = userEntry.getNickName().toLowerCase().indexOf(
                    condition);
            if ((pos1 != -1) || (pos2 != -1)) {
                targetUserList.add(jid);
            }
        }
    }

    private static void copy(final Collection<String> sourceUserList,
            final List<String> targetUserList) {
        final Iterator it = sourceUserList.iterator();
        while (it.hasNext()) {
            targetUserList.add((String) it.next());
        }
    }
}
