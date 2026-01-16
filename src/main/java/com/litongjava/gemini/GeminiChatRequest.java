package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.chat.UniChatMessage;
import com.litongjava.tio.utils.hutool.StrUtil;

/**
 * 顶层请求体 - 修改后
 */
public class GeminiChatRequest {

  /**
   * -- 新增字段 -- system_instruction, tools, tool_config 来对应你示例中的 JSON 结构： {
   * "system_instruction": {...}, "tools": [...], "tool_config": {...},
   * "contents": {...或list...} }
   */
  private GeminiSystemInstruction system_instruction;

  private List<GeminiTool> tools;

  private GeminiToolConfig tool_config;

  private List<GeminiContent> contents;

  /**
   * 可选：安全策略
   */
  private List<GeminiSafetySetting> safetySettings;

  /**
   * 可选：文本生成配置
   */
  private GeminiGenerationConfig generationConfig;

  private String responseMimeType;

  private String cachedContent;

  public GeminiChatRequest(List<GeminiContent> contents) {
    this.contents = contents;
  }

  public GeminiChatRequest(String prompt) {
    GeminiPart part = new GeminiPart(prompt);
    GeminiContent content = new GeminiContent("user", Collections.singletonList(part));
    List<GeminiContent> contents = Collections.singletonList(content);
    this.contents = contents;
  }

  public void setChatMessages(List<UniChatMessage> messages) {
    List<GeminiContent> contents = new ArrayList<>(messages.size());
    for (UniChatMessage chatMessage : messages) {
      String role = chatMessage.getRole();
      String content = chatMessage.getContent();

      if (role.equals("assistant")) {
        role = "model";
      } else if (role.equals("system")) {
        GeminiPart part = new GeminiPart(content);
        GeminiSystemInstruction geminiSystemInstructionVo = new GeminiSystemInstruction(part);
        this.setSystem_instruction(geminiSystemInstructionVo);
        continue;
      }
      GeminiContent vo = new GeminiContent(role, chatMessage);
      contents.add(vo);
    }
    this.contents = contents;
  }

  public GeminiChatRequest setSystemPrompt(String systemPrompt) {
    if (StrUtil.isNotBlank(systemPrompt)) {
      GeminiSystemInstruction geminiSystemInstructionVo = new GeminiSystemInstruction(systemPrompt);
      this.system_instruction = geminiSystemInstructionVo;
    }
    return this;
  }

  public GeminiChatRequest setUserPrompts(String... prompts) {
    List<GeminiPart> parts = new ArrayList<>(prompts.length);
    for (String prompt : prompts) {
      if (StrUtil.isNotBlank(prompt)) {
        GeminiPart part = new GeminiPart(prompt);
        parts.add(part);
      }
    }
    List<GeminiContent> contents = new ArrayList<>();
    GeminiContent content = new GeminiContent("user", parts);
    contents.add(content);
    this.contents = contents;
    return this;
  }

  public GeminiChatRequest setUserPrompt(String userPrompt) {
    GeminiPart part = new GeminiPart(userPrompt);
    List<GeminiPart> parts = new ArrayList<>();
    parts.add(part);
    List<GeminiContent> contents = new ArrayList<>();
    GeminiContent content = new GeminiContent("user", parts);
    contents.add(content);
    this.contents = contents;
    return this;
  }

  public GeminiChatRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GeminiChatRequest(GeminiSystemInstruction system_instruction, List<GeminiTool> tools,
      GeminiToolConfig tool_config, List<GeminiContent> contents, List<GeminiSafetySetting> safetySettings,
      GeminiGenerationConfig generationConfig, String responseMimeType, String cachedContent) {
    super();
    this.system_instruction = system_instruction;
    this.tools = tools;
    this.tool_config = tool_config;
    this.contents = contents;
    this.safetySettings = safetySettings;
    this.generationConfig = generationConfig;
    this.responseMimeType = responseMimeType;
    this.cachedContent = cachedContent;
  }

  public GeminiSystemInstruction getSystem_instruction() {
    return system_instruction;
  }

  public void setSystem_instruction(GeminiSystemInstruction system_instruction) {
    this.system_instruction = system_instruction;
  }

  public List<GeminiTool> getTools() {
    return tools;
  }

  public void setTools(List<GeminiTool> tools) {
    this.tools = tools;
  }

  public GeminiToolConfig getTool_config() {
    return tool_config;
  }

  public void setTool_config(GeminiToolConfig tool_config) {
    this.tool_config = tool_config;
  }

  public List<GeminiContent> getContents() {
    return contents;
  }

  public void setContents(List<GeminiContent> contents) {
    this.contents = contents;
  }

  public List<GeminiSafetySetting> getSafetySettings() {
    return safetySettings;
  }

  public void setSafetySettings(List<GeminiSafetySetting> safetySettings) {
    this.safetySettings = safetySettings;
  }

  public GeminiGenerationConfig getGenerationConfig() {
    return generationConfig;
  }

  public void setGenerationConfig(GeminiGenerationConfig generationConfig) {
    this.generationConfig = generationConfig;
  }

  public String getResponseMimeType() {
    return responseMimeType;
  }

  public void setResponseMimeType(String responseMimeType) {
    this.responseMimeType = responseMimeType;
  }

  public String getCachedContent() {
    return cachedContent;
  }

  public void setCachedContent(String cachedContent) {
    this.cachedContent = cachedContent;
  }

}
