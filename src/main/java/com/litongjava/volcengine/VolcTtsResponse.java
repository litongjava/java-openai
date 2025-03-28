package com.litongjava.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcTtsResponse {
  private Integer code, sequence;
  private String reqid, operation, message;
  private String data;
  private VolcTtsAddition addition;
}
