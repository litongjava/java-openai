package com.litongjava.claude;

public class ClaudeChatRequestImage {
  private String type;
  private String media_type;
  private String data;
  private String url;

  public ClaudeChatRequestImage() {
    super();
  }

  public ClaudeChatRequestImage(String type, String media_type, String data, String url) {
    super();
    this.type = type;
    this.media_type = media_type;
    this.data = data;
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMedia_type() {
    return media_type;
  }

  public void setMedia_type(String media_type) {
    this.media_type = media_type;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}