package com.litongjava.image;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * https://ai.gitee.com/docs/openapi/v1#tag/%E5%9B%BE%E5%83%8F%E7%94%9F%E6%88%90/post/async/images/edits
 * 
 * @author Tong Li
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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
}
