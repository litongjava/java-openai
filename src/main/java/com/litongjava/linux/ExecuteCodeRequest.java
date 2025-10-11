package com.litongjava.linux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExecuteCodeRequest {
  private Long id;
  private Long sessionId;
  private String code;
  private String figure;
  private Integer timeout;
  // l low,m medium,h high
  private String quality;
  private Long sessionPrt;
  private String m3u8Path;

  public ExecuteCodeRequest(String code) {
    this.code = code;
  }

  public ExecuteCodeRequest(long id, String code) {
    this.id = id;
    this.code = code;
  }

  public ExecuteCodeRequest(long id, String code, Integer timeout) {
    this.id = id;
    this.code = code;
    this.timeout = timeout;
  }

  public ExecuteCodeRequest(Long sessionId, String code, int timeout, Long sessionPrt, String m3u8Path) {
    this.sessionId = sessionId;
    this.code = code;
    this.timeout = timeout;
    this.sessionPrt = sessionPrt;
    this.m3u8Path = m3u8Path;
  }

  public ExecuteCodeRequest(Long id, Long sessionId, String code, Integer timeout, Long sessionPrt, String m3u8Path) {
    this.sessionId = sessionId;
    this.id = id;
    this.code = code;
    this.timeout = timeout;
    this.sessionPrt = sessionPrt;
    this.m3u8Path = m3u8Path;
  }

  public ExecuteCodeRequest(Long sessionId, String code, String figure, int timeout, Long sessionPrt, String m3u8Path) {
    this.sessionId = sessionId;
    this.code = code;
    this.figure = figure;
    this.timeout = timeout;
    this.sessionPrt = sessionPrt;
    this.m3u8Path = m3u8Path;
  }
}
