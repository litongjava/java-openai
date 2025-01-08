package com.litongjava.gemini;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 顶层请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestVo {

  /**
   * 必填参数：对话内容
   */
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
