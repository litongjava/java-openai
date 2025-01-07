package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示请求中的单条对话
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContentVo {
  /**
   * 角色：user 或 model 等
   */
  private String role;

  /**
   * 内容拆分成多个 part
   */
  private List<GeminiPartVo> parts;
}
