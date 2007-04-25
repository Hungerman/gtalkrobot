package com.gtrobot.service.common;

import java.util.List;

import com.gtrobot.model.common.UserEntry;

/**
 * UserEntryManagerインタフェース. このクラスは、ユーザ対象を対応処理.
 * 
 * <p>
 * <a href="UserEntryManager.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy: sunyuxin $ $LastChangedRevision: 172 $
 *          $LastChangedDate: 2006-08-04 10:39:28 +0900 (金, 04 8 2006) $
 */
public interface UserEntryManager {

	/**
	 * ユーザ対象取得.
	 * 
	 * @param UserEntryId
	 *            ユーザイド
	 * @return UserEntry ユーザobject
	 */
	public UserEntry getUserEntry(String jid);

	public UserEntry getUserEntryByNickName(String newNickname);

	/**
	 * ユーザ対象リスト取得.
	 * 
	 * @param UserEntry
	 *            ユーザobject
	 * @return List ユーザList情報
	 */
	public List getUserEntrys(UserEntry userEntry);

	/**
	 * ユーザ対象更新.
	 * 
	 * @param UserEntry
	 *            ユーザobject
	 * @throws UserEntryExistsException
	 */
	public void saveUserEntry(UserEntry userEntry);

	/**
	 * ユーザ対象削除.
	 * 
	 * @param UserEntryId
	 *            ユーザイド
	 */
	public void removeUserEntry(UserEntry userEntry);

}
