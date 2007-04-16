package com.gtrobot.processor.common;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.common.SearchUserCommand;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.AbstractProcessor;
import com.gtrobot.utils.UserSearchFilter;
import com.gtrobot.utils.UserSessionUtil;

public class SearchUserProcessor extends AbstractProcessor {
	private static final int ROWS_IN_PAGE = 2;

	protected void internalProcess(BaseCommand abCmd) throws XMPPException {
		SearchUserCommand cmd = (SearchUserCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		String sessionKey = "-searchuser." + cmd.getCondition();
		UserSearchFilter userSearchFilter = (UserSearchFilter) UserSessionUtil
				.getSession(cmd.getUserEntry().getJid(), sessionKey);
		if (userSearchFilter == null) {
			userSearchFilter = new UserSearchFilter(GTRobotContextHelper
					.getUserEntryService().getAllActiveUsers(), cmd
					.getCondition());
			UserSessionUtil.putSession(abCmd.getUserEntry().getJid(),
					sessionKey, userSearchFilter);
		}

		int start = userSearchFilter.getCount();
		int end = start + ROWS_IN_PAGE;
		if (end >= userSearchFilter.size()) {
			end = userSearchFilter.size();
			UserSessionUtil.removeSession(abCmd.getUserEntry().getJid(),
					sessionKey);
		}

		msgBuf.append(cmd.getI18NMessage("searchuser.result.title"));
		msgBuf.append(endl);
		for (int i = start; i < end; i++) {
			msgBuf.append("$");
			msgBuf.append(i);
			msgBuf.append("  ");
			String user = userSearchFilter.getUser(i);
			UserEntry userEntry = GTRobotContextHelper.getUserEntryService()
					.getUserEntry(user);
			msgBuf.append(userEntry.getNickName());
			msgBuf.append("(");
			msgBuf.append(user);
			msgBuf.append(")");
			msgBuf.append(endl);
		}
		userSearchFilter.storeCount(end);

		msgBuf.append(cmd.getI18NMessage("searchuser.result.prompt.total"));
		msgBuf.append(userSearchFilter.size());
		msgBuf.append(endl);
		msgBuf.append(cmd.getI18NMessage("searchuser.result.prompt.next"));

		sendBackMessage(abCmd, msgBuf.toString());
	}
}
