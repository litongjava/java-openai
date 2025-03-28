package com.litongjava.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcRequest {
  private String reqid;
  private String text;
  private String text_type;
  private String operation;
  private Integer silence_duration;
}