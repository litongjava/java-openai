package com.litongjava.apify;

import java.util.ArrayList;
import java.util.List;

public class ApiFyLinkedProfileReqest {
  public List<String> profileUrls;

  public ApiFyLinkedProfileReqest(String profileUrl) {
    this.profileUrls = new ArrayList<>();
    this.profileUrls.add(profileUrl);
  }

  public ApiFyLinkedProfileReqest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ApiFyLinkedProfileReqest(List<String> profileUrls) {
    super();
    this.profileUrls = profileUrls;
  }

  public List<String> getProfileUrls() {
    return profileUrls;
  }

  public void setProfileUrls(List<String> profileUrls) {
    this.profileUrls = profileUrls;
  }
}
