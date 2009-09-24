package com.dictone.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Word {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private User author;

    @Persistent
    private String word;

    @Persistent
    private String pronounce;

    @Persistent
    private String pronounceType;

    @Persistent
    private String wordType;

    @Persistent
    private String meaning;

    @Persistent
    private String sentence;

    @Persistent
    private String comments;

    public Word(User author, String word, String pronounce,
            String pronounceType, String wordType, String meaning,
            String sentence, String comments) {
        super();
        this.author = author;
        this.word = word;
        this.pronounce = pronounce;
        this.pronounceType = pronounceType;
        this.wordType = wordType;
        this.meaning = meaning;
        this.sentence = sentence;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getWord() {
        return word;
    }

    public String getPronounce() {
        return pronounce;
    }

    public String getPronounceType() {
        return pronounceType;
    }

    public String getWordType() {
        return wordType;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getSentence() {
        return sentence;
    }

    public String getComments() {
        return comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public void setPronounceType(String pronounceType) {
        this.pronounceType = pronounceType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
