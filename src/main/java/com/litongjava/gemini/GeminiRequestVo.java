package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顶层请求体 - 修改后
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestVo {

  /**
   * -- 新增字段 --
   * system_instruction, tools, tool_config
   * 来对应你示例中的 JSON 结构：
   * {
   *   "system_instruction": {...},
   *   "tools": [...],
   *   "tool_config": {...},
   *   "contents": {...或list...}
   * }
   */
  private GeminiSystemInstructionVo system_instruction;

  private List<GeminiToolVo> tools;

  private GeminiToolConfigVo tool_config;

  private List<GeminiContentVo> contents;

  /**
   * 可选：安全策略
   */
  private List<GeminiSafetySettingVo> safetySettings;

  /**
   * 可选：文本生成配置
   */
  private GeminiGenerationConfigVo generationConfig;

  public GeminiRequestVo(List<GeminiContentVo> contents) {
    this.contents = contents;
  }
}
