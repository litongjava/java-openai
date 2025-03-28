package com.litongjava.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcApp {
  private String appid;
  private String token;
  private String cluster;
}
