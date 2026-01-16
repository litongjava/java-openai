package com.litongjava.chat;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseDelta;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;

public class UniChatResponse {

  private transient String rawData;
  private String model;
  private ChatResponseMessage message;
  private ChatResponseDelta delta;
  private ChatResponseUsage usage;
  private List<String> citations;

  public UniChatResponse(String model, ChatResponseMessage message, ChatResponseUsage usage, String rawData) {
    this.model = model;
    this.message = message;
    this.usage = usage;
    this.rawData = rawData;
  }

  public UniChatResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniChatResponse(String rawData, String model, ChatResponseMessage message, ChatResponseDelta delta,
      ChatResponseUsage usage, List<String> citations) {
    super();
    this.rawData = rawData;
    this.model = model;
    this.message = message;
    this.delta = delta;
    this.usage = usage;
    this.citations = citations;
  }

  public String getRawData() {
    return rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public ChatResponseMessage getMessage() {
    return message;
  }

  public void setMessage(ChatResponseMessage message) {
    this.message = message;
  }

  public ChatResponseDelta getDelta() {
    return delta;
  }

  public void setDelta(ChatResponseDelta delta) {
    this.delta = delta;
  }

  public ChatResponseUsage getUsage() {
    return usage;
  }

  public void setUsage(ChatResponseUsage usage) {
    this.usage = usage;
  }

  public List<String> getCitations() {
    return citations;
  }

  public void setCitations(List<String> citations) {
    this.citations = citations;
  }

}
