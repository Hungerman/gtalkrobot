package com.gtrobot.model;

/**
 * JP Word entry model.
 * 
 * @author sunyuxin
 * 
 */
public class JPWordEntry extends BaseModel {
	private static final long serialVersionUID = 5907470364421362614L;

	private Long id;

	private String word;

	private String pronounce;

	private String wordType;

	private String meaning;

	private String sentence;

	private String comments;

	public String getComments() {
		return comments;
	}

	public Long getId() {
		return id;
	}

	public String getMeaning() {
		return meaning;
	}

	public String getPronounce() {
		return pronounce;
	}

	public String getSentence() {
		return sentence;
	}

	public String getWord() {
		return word;
	}

	public String getWordType() {
		return wordType;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public void setPronounce(String pronounce) {
		this.pronounce = pronounce;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setWordType(String wordType) {
		this.wordType = wordType;
	}

}
