package com.gtrobot.dao.common;

import java.util.List;

import com.gtrobot.dao.Dao;
import com.gtrobot.model.common.UserEntry;

public interface UserEntryDao extends Dao {

	public UserEntry getUserEntry(String jid);

	public UserEntry getUserEntryByNickName(String newNickname);

	public List getUserEntrys(UserEntry userEntry);

	public void saveUserEntry(UserEntry userEntry);

	public void removeUserEntry(UserEntry userEntry);

}
