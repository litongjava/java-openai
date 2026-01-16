package com.litongjava.gemini;

import java.util.Map;

/**
 * 表示模型返回的 Function Call。
 * 例如:
 * {
 *   "name": "stop_lights",
 *   "args": {
 *     "dummy": true
 *   }
 * }
 */

public class GeminiFunctionCallVo {
  private String name;
  // 可以是任意类型，这里用 Map<String,Object> 来通用存储
  private Map<String, Object> args;
  public GeminiFunctionCallVo() {
    super();
    // TODO Auto-generated constructor stub
  }
  public GeminiFunctionCallVo(String name, Map<String, Object> args) {
    super();
    this.name = name;
    this.args = args;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Map<String, Object> getArgs() {
    return args;
  }
  public void setArgs(Map<String, Object> args) {
    this.args = args;
  }
}
