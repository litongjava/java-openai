package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFinishRequest {
  private Long session_id;
  private String watermark;
  private String storage_platform;

  public VideoFinishRequest(long session_id, String watermark) {
    this.session_id = session_id;
    this.watermark = watermark;
  }
}
