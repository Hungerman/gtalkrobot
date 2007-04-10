package com.gtrobot.model;

import java.util.List;
import java.util.Locale;

/**
 * Word meaning model.
 * 
 * @author sunyuxin
 * 
 */
public class WordMeaning extends BaseModel {
	private static final long serialVersionUID = -6579311429091739228L;

	private Long id;

	private String meaning;

	private Locale locale;

	private List sentences;

	private List localizations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;

		this.setModified(true);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List getSentences() {
		return sentences;
	}

	public void setSentences(List wordMeanings) {
		this.sentences = wordMeanings;
	}

	public List getLocalizations() {
		return localizations;
	}

	public void setLocalizations(List localizations) {
		this.localizations = localizations;
	}
}
