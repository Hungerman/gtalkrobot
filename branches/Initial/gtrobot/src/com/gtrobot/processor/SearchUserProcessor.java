package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.SearchUserCommand;
import com.gtrobot.context.UserEntry;
import com.gtrobot.exception.CommandMatchedException;
import com.gtrobot.utils.UserSearchFilter;
import com.gtrobot.utils.UserSession;

public class SearchUserProcessor extends AbstractProcessor {
	private static final int ROWS_IN_PAGE = 2;

	protected void beforeProcess(AbstractCommand abCmd)
			throws CommandMatchedException, XMPPException {
		if (!(abCmd instanceof SearchUserCommand)) {
			throw new CommandMatchedException(abCmd, this);
		}
		StringBuffer msgBuf = new StringBuffer();

		String error = abCmd.getErrorMessage();
		if (error != null) {
			msgBuf.append(abCmd.getI18NMessage("searchuser.error.title"));
			msgBuf.append(error);
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("searchuser.command.format"));
			msgBuf.append(endl);
			msgBuf.append(abCmd.getI18NMessage("searchuser.origin.prompt"));
			msgBuf.append(abCmd.getOriginMessage());
			msgBuf.append(endl);
			sendBackMessage(abCmd, msgBuf.toString());
		}
	}

	protected void internalProcess(AbstractCommand abCmd) throws XMPPException {
		SearchUserCommand cmd = (SearchUserCommand) abCmd;
		StringBuffer msgBuf = new StringBuffer();

		String sessionKey = "-searchuser." + cmd.getCondition();
		UserSearchFilter userSearchFilter = (UserSearchFilter) UserSession
				.getSession(cmd.getUserEntry().getJid(), sessionKey);
		if (userSearchFilter == null) {
			userSearchFilter = new UserSearchFilter(ctx.getActiveUserList(),
					cmd.getCondition());
			UserSession.putSession(abCmd.getUserEntry().getJid(), sessionKey,
					userSearchFilter);
		}

		int start = userSearchFilter.getCount();
		int end = start + ROWS_IN_PAGE;
		if (end >= userSearchFilter.size()) {
			end = userSearchFilter.size();
			UserSession
					.removeSession(abCmd.getUserEntry().getJid(), sessionKey);
		}

		msgBuf.append(cmd.getI18NMessage("searchuser.result.title"));
		msgBuf.append(endl);
		for (int i = start; i < end; i++) {
			msgBuf.append("$");
			msgBuf.append(i);
			msgBuf.append("  ");
			String user = userSearchFilter.getUser(i);
			UserEntry userEntry = ctx.getUser(user);
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
