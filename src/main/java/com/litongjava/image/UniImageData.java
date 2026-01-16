package com.litongjava.image;

public class UniImageData {
  private String b64_json;
  private String url;
  private String type;

  public UniImageData() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniImageData(String b64_json, String url, String type) {
    super();
    this.b64_json = b64_json;
    this.url = url;
    this.type = type;
  }

  public String getB64_json() {
    return b64_json;
  }

  public void setB64_json(String b64_json) {
    this.b64_json = b64_json;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
