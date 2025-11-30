package com.litongjava.langfuse;

import java.util.List;

public class PromptPayload {

  private String type;
  private List<PromptChatMessage> prompt;
  private String name;
  private int version;
  private List<String> labels;
  private List<String> tags;
  private String commitMessage;

  public PromptPayload() {
  }

  public String getType() {
    return type;
  }

  public List<PromptChatMessage> getPrompt() {
    return prompt;
  }

  public String getName() {
    return name;
  }

  public int getVersion() {
    return version;
  }

  public List<String> getLabels() {
    return labels;
  }

  public List<String> getTags() {
    return tags;
  }

  public String getCommitMessage() {
    return commitMessage;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setPrompt(List<PromptChatMessage> prompt) {
    this.prompt = prompt;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setCommitMessage(String commitMessage) {
    this.commitMessage = commitMessage;
  }
}
