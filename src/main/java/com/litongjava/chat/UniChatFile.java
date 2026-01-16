package com.litongjava.chat;

public class UniChatFile {
  private String type;
  private ChatImageFile image_url;

  public UniChatFile() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniChatFile(String type, ChatImageFile image_url) {
    super();
    this.type = type;
    this.image_url = image_url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ChatImageFile getImage_url() {
    return image_url;
  }

  public void setImage_url(ChatImageFile image_url) {
    this.image_url = image_url;
  }

}
