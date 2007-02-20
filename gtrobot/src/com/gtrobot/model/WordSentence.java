package com.gtrobot.model;

import java.util.List;
import java.util.Locale;

/**
 * Word entry model.
 * 
 * @author sunyuxin
 * 
 */
public class WordSentence extends BaseModel {
	private static final long serialVersionUID = -7673123201283903495L;

	private Long id;

	private String sentence;

	private Locale locale;

	private List localizations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;

		this.setModified(true);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List getLocalizations() {
		return localizations;
	}

	public void setLocalizations(List localizations) {
		this.localizations = localizations;
	}
}
