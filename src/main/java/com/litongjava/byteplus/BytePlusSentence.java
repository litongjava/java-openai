package com.litongjava.byteplus;

import java.util.List;

public class BytePlusSentence {
  private List<Object> phonemes;
  private String text;
  private List<Object> words;

  public List<Object> getPhonemes() {
    return phonemes;
  }

  public void setPhonemes(List<Object> phonemes) {
    this.phonemes = phonemes;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<Object> getWords() {
    return words;
  }

  public void setWords(List<Object> words) {
    this.words = words;
  }
}
