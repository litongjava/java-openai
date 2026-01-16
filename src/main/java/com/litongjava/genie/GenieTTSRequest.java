package com.litongjava.genie;

public class GenieTTSRequest {

  private String character_name;
  private String text;
  private boolean split_sentence = true;

  public GenieTTSRequest(String character_name, String text) {
    this.character_name = character_name;
    this.text = text;
  }

  public GenieTTSRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GenieTTSRequest(String character_name, String text, boolean split_sentence) {
    super();
    this.character_name = character_name;
    this.text = text;
    this.split_sentence = split_sentence;
  }

  public String getCharacter_name() {
    return character_name;
  }

  public void setCharacter_name(String character_name) {
    this.character_name = character_name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isSplit_sentence() {
    return split_sentence;
  }

  public void setSplit_sentence(boolean split_sentence) {
    this.split_sentence = split_sentence;
  }

}
