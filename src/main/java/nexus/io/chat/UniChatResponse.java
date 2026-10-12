package nexus.io.chat;

import java.util.List;

import nexus.io.openai.chat.ChatResponseDelta;
import nexus.io.openai.chat.ChatResponseMessage;
import nexus.io.openai.chat.ChatResponseUsage;

public class UniChatResponse {

  private transient String rawData;
  private String model;
  private ChatResponseMessage message;
  private ChatResponseDelta delta;
  private ChatResponseUsage usage;
  private List<String> citations;
  private List<UniWebSearchSource> searchSources;
  private List<String> searchErrors;
  private String finishReason;
  private Integer webSearchRequests;

  public List<UniWebSearchSource> getSearchSources() {
    return searchSources;
  }

  public void setSearchSources(List<UniWebSearchSource> searchSources) {
    this.searchSources = searchSources;
  }

  public List<String> getSearchErrors() {
    return searchErrors;
  }

  public void setSearchErrors(List<String> searchErrors) {
    this.searchErrors = searchErrors;
  }

  public String getFinishReason() {
    return finishReason;
  }

  public void setFinishReason(String finishReason) {
    this.finishReason = finishReason;
  }

  public Integer getWebSearchRequests() {
    return webSearchRequests;
  }

  public void setWebSearchRequests(Integer webSearchRequests) {
    this.webSearchRequests = webSearchRequests;
  }

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
