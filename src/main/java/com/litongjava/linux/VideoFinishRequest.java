package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFinishRequest {
  private Long sessionPrt;
  private String m3u8Path;
  private String videos;
  private String watermark;
}
