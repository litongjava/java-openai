package com.litongjava.gitee.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiteeImageData {
  private String b64_json;
  private String url;
  private String type;
}
