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
  private Long sessionId;
  private Long id;
  private String name;
  private String code;
  private Integer timeout;
  private String figure;
  // l low,m medium,h high
  private String quality;
  private String storage_platform;

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

  public ExecuteCodeRequest(Long sessionId, String code, int timeout) {
    this.sessionId = sessionId;
    this.code = code;
    this.timeout = timeout;

  }

  public ExecuteCodeRequest(Long sessionId, Long id, String code, Integer timeout) {
    this.sessionId = sessionId;
    this.id = id;
    this.code = code;
    this.timeout = timeout;

  }

  public ExecuteCodeRequest(Long sessionId, Long id, String name, String code, String figure, int timeout) {
    this.sessionId = sessionId;
    this.id = id;
    this.name = name;
    this.code = code;
    this.figure = figure;
    this.timeout = timeout;
  }

  public ExecuteCodeRequest(Long sessionId, String name, String code, String figure, int timeout) {
    this.sessionId = sessionId;
    this.name = name;
    this.code = code;
    this.figure = figure;
    this.timeout = timeout;
  }

  public ExecuteCodeRequest(Long sessionId, String name, String code, int timeout) {
    this.sessionId = sessionId;
    this.name = name;
    this.code = code;
    this.timeout = timeout;
  }
}
