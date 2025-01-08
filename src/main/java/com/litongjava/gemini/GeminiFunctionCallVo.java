package com.litongjava.gemini;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiFunctionCallVo {
  private String name;
  // 可以是任意类型，这里用 Map<String,Object> 来通用存储
  private Map<String, Object> args;
}
