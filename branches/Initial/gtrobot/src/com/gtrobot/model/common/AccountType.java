package com.gtrobot.model.common;

/**
 * An enum to represent the account type.
 */
public enum AccountType {
	/**
	 * Administrator
	 */
	admin(9999),

	/**
	 * Normal user.
	 */
	user(0),

	/**
	 * Blocked user.
	 */
	blocked(-9999);

	private int code;

	AccountType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static AccountType fromCode(int code) {
		for (AccountType t : AccountType.values()) {
			if (t.getCode() == code)
				return t;
		}
		return null;
	}
}
