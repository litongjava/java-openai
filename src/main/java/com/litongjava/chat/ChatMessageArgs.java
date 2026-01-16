package com.litongjava.chat;

public class ChatMessageArgs {
  private Long id;
  private String type;
  private String name;
  private String institution;
  private String url;
  private String[] urls;

  public ChatMessageArgs() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ChatMessageArgs(Long id, String type, String name, String institution, String url, String[] urls) {
    super();
    this.id = id;
    this.type = type;
    this.name = name;
    this.institution = institution;
    this.url = url;
    this.urls = urls;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String[] getUrls() {
    return urls;
  }

  public void setUrls(String[] urls) {
    this.urls = urls;
  }

}
