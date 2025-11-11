package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionFinishRequest {
  private Long sessionId;
  private String[] sceneNames;
}
