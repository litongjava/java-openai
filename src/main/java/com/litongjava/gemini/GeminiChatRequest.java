package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.chat.UniChatMessage;
import com.litongjava.tio.utils.hutool.StrUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 顶层请求体 - 修改后
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GeminiChatRequest {

  /**
   * -- 新增字段 -- system_instruction, tools, tool_config 来对应你示例中的 JSON 结构： {
   * "system_instruction": {...}, "tools": [...], "tool_config": {...},
   * "contents": {...或list...} }
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
  private GeminiGenerationConfig generationConfig;

  private String responseMimeType;

  private String cachedContent;

  public GeminiChatRequest(List<GeminiContentVo> contents) {
    this.contents = contents;
  }

  public GeminiChatRequest(String prompt) {
    GeminiPartVo part = new GeminiPartVo(prompt);
    GeminiContentVo content = new GeminiContentVo("user", Collections.singletonList(part));
    List<GeminiContentVo> contents = Collections.singletonList(content);
    this.contents = contents;
  }

  public void setChatMessages(List<UniChatMessage> messages) {
    List<GeminiContentVo> contents = new ArrayList<>(messages.size());
    for (UniChatMessage chatMessage : messages) {
      String role = chatMessage.getRole();
      String content = chatMessage.getContent();

      if (role.equals("assistant")) {
        role = "model";
      } else if (role.equals("system")) {
        GeminiPartVo part = new GeminiPartVo(content);
        GeminiSystemInstructionVo geminiSystemInstructionVo = new GeminiSystemInstructionVo(part);
        this.setSystem_instruction(geminiSystemInstructionVo);
        continue;
      }
      GeminiContentVo vo = new GeminiContentVo(role, chatMessage);
      contents.add(vo);
    }
    this.contents = contents;
  }

  public GeminiChatRequest setSystemPrompt(String systemPrompt) {
    if (StrUtil.isNotBlank(systemPrompt)) {
      GeminiSystemInstructionVo geminiSystemInstructionVo = new GeminiSystemInstructionVo(systemPrompt);
      this.system_instruction = geminiSystemInstructionVo;
    }
    return this;
  }

  public GeminiChatRequest setUserPrompts(String... prompts) {
    List<GeminiPartVo> parts = new ArrayList<>(prompts.length);
    for (String prompt : prompts) {
      if (StrUtil.isNotBlank(prompt)) {
        GeminiPartVo part = new GeminiPartVo(prompt);
        parts.add(part);
      }
    }
    List<GeminiContentVo> contents = new ArrayList<>();
    GeminiContentVo content = new GeminiContentVo("user", parts);
    contents.add(content);
    this.contents = contents;
    return this;
  }

  public GeminiChatRequest setUserPrompt(String userPrompt) {
    GeminiPartVo part = new GeminiPartVo(userPrompt);
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(part);
    List<GeminiContentVo> contents = new ArrayList<>();
    GeminiContentVo content = new GeminiContentVo("user", parts);
    contents.add(content);
    this.contents = contents;
    return this;
  }

}
