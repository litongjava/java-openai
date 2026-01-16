package com.litongjava.image;

import java.util.List;

/**
 * https://ai.gitee.com/docs/openapi/v1#tag/%E5%9B%BE%E5%83%8F%E7%94%9F%E6%88%90/post/async/images/edits
 * 
 * @author Tong Li
 *
 */

public class UniImageResponse {
  private String rawResponse;
  private Long created;
  private List<UniImageData> data;

  public String getImageUrl() {
    if (this.data != null && data.size() > 0) {
      return data.get(0).getUrl();
    }
    return null;
  }

  public String getB64Data() {
    if (this.data != null && data.size() > 0) {
      return data.get(0).getB64_json();
    }
    return null;
  }

  public UniImageResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniImageResponse(String rawResponse, Long created, List<UniImageData> data) {
    super();
    this.rawResponse = rawResponse;
    this.created = created;
    this.data = data;
  }

  public String getRawResponse() {
    return rawResponse;
  }

  public void setRawResponse(String rawResponse) {
    this.rawResponse = rawResponse;
  }

  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public List<UniImageData> getData() {
    return data;
  }

  public void setData(List<UniImageData> data) {
    this.data = data;
  }
}
