package nexus.io.chat;

import java.util.List;

import nexus.io.openai.chat.MessageRole;
import nexus.io.openai.chat.ToolCall;

public class UniChatMessage {

  private String role;
  private String content;

  // 图片等
  private List<ChatImageFile> files;

  private ChatMessageArgs args;
  private List<String> attachments;

  // ====== 新增（关键）======

  // assistant -> tool_calls
  private List<ToolCall> toolCalls;

  // tool -> tool_call_id
  private String toolCallId;

  // tool -> name（可选）
  private String name;

  // =======================

  public UniChatMessage() {
  }

  public UniChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public UniChatMessage(String content) {
    this.role = "user";
    this.content = content;
  }

  public static UniChatMessage buildSystem(String content) {
    return new UniChatMessage(MessageRole.system, content);
  }

  public static UniChatMessage buildUser(String content) {
    return new UniChatMessage(MessageRole.user, content);
  }

  public static UniChatMessage buildAssistant(String content) {
    return new UniChatMessage(MessageRole.assistant, content);
  }

  // assistant 带 tool_calls
  public static UniChatMessage buildAssistantWithToolCalls(List<ToolCall> toolCalls) {
    UniChatMessage msg = new UniChatMessage();
    msg.setRole(MessageRole.assistant);
    msg.setToolCalls(toolCalls);
    return msg;
  }

  // tool 返回结果
  public static UniChatMessage buildTool(String toolCallId, String name, String content) {
    UniChatMessage msg = new UniChatMessage();
    msg.setRole("tool");
    msg.setToolCallId(toolCallId);
    msg.setName(name);
    msg.setContent(content);
    return msg;
  }

  // ===== getter / setter =====

  public String getRole() {
    return role;
  }

  public UniChatMessage setRole(String role) {
    this.role = role;
    return this;
  }

  public String getContent() {
    return content;
  }

  public UniChatMessage setContent(String content) {
    this.content = content;
    return this;
  }

  public List<ChatImageFile> getFiles() {
    return files;
  }

  public UniChatMessage setFiles(List<ChatImageFile> files) {
    this.files = files;
    return this;
  }

  public ChatMessageArgs getArgs() {
    return args;
  }

  public UniChatMessage setArgs(ChatMessageArgs args) {
    this.args = args;
    return this;
  }

  public List<String> getAttachments() {
    return attachments;
  }

  public UniChatMessage setAttachments(List<String> attachments) {
    this.attachments = attachments;
    return this;
  }

  public List<ToolCall> getToolCalls() {
    return toolCalls;
  }

  public UniChatMessage setToolCalls(List<ToolCall> toolCalls) {
    this.toolCalls = toolCalls;
    return this;
  }

  public String getToolCallId() {
    return toolCallId;
  }

  public UniChatMessage setToolCallId(String toolCallId) {
    this.toolCallId = toolCallId;
    return this;
  }

  public String getName() {
    return name;
  }

  public UniChatMessage setName(String name) {
    this.name = name;
    return this;
  }

  public UniChatMessage role(String role) {
    this.role=role;
    return this;
  }

  public UniChatMessage content(String content) {
    this.content=content;
    return this;
  }
}