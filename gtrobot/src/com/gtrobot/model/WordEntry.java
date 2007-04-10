package com.gtrobot.model;

import java.util.List;
import java.util.Locale;

/**
 * Word entry model.
 * <p>
 * WordEntry n --- n WordMeaning <br>
 * WordMeaning n --- n WordSentence <br>
 * WordEntry n --- n WordEntry <br>
 * 
 * @author sunyuxin
 * 
 */
public class WordEntry extends BaseModel {
	private static final long serialVersionUID = 8377846979059886101L;

	private Long id;

	private String word;

	private String pronounce;

	private Locale locale;

	private List meanings;

	private List localizations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;

		this.setModified(true);
	}

	public String getPronounce() {
		return pronounce;
	}

	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public List getMeanings() {
		return meanings;
	}

	public void setMeanings(List wordMeanings) {
		this.meanings = wordMeanings;
	}

	public List getLocalizations() {
		return localizations;
	}

	public void setLocalizations(List localizations) {
		this.localizations = localizations;
	}
}
