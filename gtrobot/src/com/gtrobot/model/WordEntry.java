package com.gtrobot.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * 
 */
public class WordEntry implements Serializable {
	private static final long serialVersionUID = 8377846979059886101L;

	private long id;

	private String word;

	private Locale locale;

	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String nickName) {
		this.word = nickName;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
