package com.litongjava.apify;

public class ApiFyLinkedProfilePostReqVo {
  public String username;
  private int page_number = 1;

  public ApiFyLinkedProfilePostReqVo(String username) {
    this.username = username;
  }

  public ApiFyLinkedProfilePostReqVo() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ApiFyLinkedProfilePostReqVo(String username, int page_number) {
    super();
    this.username = username;
    this.page_number = page_number;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getPage_number() {
    return page_number;
  }

  public void setPage_number(int page_number) {
    this.page_number = page_number;
  }
  
}
