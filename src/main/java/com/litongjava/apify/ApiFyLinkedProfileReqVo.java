package com.litongjava.apify;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiFyLinkedProfileReqVo {
  public List<String> profileUrls;

  public ApiFyLinkedProfileReqVo(String profileUrl) {
    this.profileUrls = new ArrayList<>();
    this.profileUrls.add(profileUrl);
  }
}
