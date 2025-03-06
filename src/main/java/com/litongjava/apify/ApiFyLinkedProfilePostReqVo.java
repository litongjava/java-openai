package com.litongjava.apify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiFyLinkedProfilePostReqVo {
  public String username;
  private int page_number = 1;

  public ApiFyLinkedProfilePostReqVo(String username) {
    this.username = username;
  }
}
