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

	public UserSearchFilter(Collection<String> sourceUserList, String condition) {
		userList = new ArrayList<String>();
		if (condition == null || condition.trim().length() == 0) {
			copy(sourceUserList, userList);
		} else {
			filterCopy(sourceUserList, userList, condition.trim());
		}
		resetCount();
	}

	public String getUser(int pos) {
		return (String) userList.get(pos);
	}

	public int size() {
		return userList.size();
	}

	public void resetCount() {
		count = 0;
	}

	public int getCount() {
		return count;
	}

	public void storeCount(int count) {
		this.count = count;
	}

	private static void filterCopy(Collection<String> sourceUserList,
			List<String> targetUserList, String condition) {
		Iterator it = sourceUserList.iterator();
		condition = condition.toLowerCase();
		while (it.hasNext()) {
			String jid = (String) it.next();
			UserEntry userEntry = GTRobotContextHelper.getUserEntryService()
					.getUserEntry(jid);
			int pos1 = jid.toLowerCase().indexOf(condition);
			int pos2 = userEntry.getNickName().toLowerCase().indexOf(condition);
			if (pos1 != -1 || pos2 != -1) {
				targetUserList.add(jid);
			}
		}
	}

	private static void copy(Collection<String> sourceUserList,
			List<String> targetUserList) {
		Iterator it = sourceUserList.iterator();
		while (it.hasNext()) {
			targetUserList.add((String) it.next());
		}
	}
}
