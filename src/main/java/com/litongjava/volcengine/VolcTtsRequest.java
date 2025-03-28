package com.litongjava.volcengine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VolcTtsRequest {
  private  VolcApp app;
  private VolcUser user;
  private VolcAudio audio;
  private VolcRequest request;
}