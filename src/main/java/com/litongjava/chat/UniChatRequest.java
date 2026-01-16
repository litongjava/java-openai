package com.litongjava.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.openai.ChatProvider;
import com.litongjava.tio.utils.hutool.StrUtil;

public class UniChatRequest {
  private String domain;
  private String apiPrefixUrl;
  private Long groupId;
  private String groupName;
  private Long taskId;
  private String taskName;

  private boolean useSystemPrompt = true;
  private String apiKey;
  private String platform;
  private String model;
  private String systemPrompt;;
  private boolean cacheSystemPrompt;
  private Boolean stream;
  private List<UniChatMessage> messages;
  private Float temperature;
  private String cachedId;
  private Integer max_tokens;
  private Boolean enable_thinking;
  private UniThinkingConfig thinkingConfig;
  // ChatResponseFormatType
  private String responseFormat;
  private UniResponseSchema responseSchema;
  private ChatProvider provider;
  private List<String> responseModalities;
  private Boolean enable_search;

  public UniChatRequest(String platform, String model) {
    this.platform = platform;
    this.model = model;
  }

  public UniChatRequest(PlatformInput platformInput) {
    this.platform = platformInput.getPlatform();
    this.model = platformInput.getModel();
  }

  public UniChatRequest(List<UniChatMessage> messages) {
    this.messages = messages;
  }

  public UniChatRequest(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public UniChatRequest(List<UniChatMessage> messages, Float temperature) {
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  public UniChatRequest(String systemPrompt, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.temperature = temperature;
  }

  public UniChatRequest(String systemPrompt, List<UniChatMessage> messages) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
  }

  public UniChatRequest(String systemPrompt, List<UniChatMessage> messages, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String provider, String model, List<UniChatMessage> messages) {
    this.platform = provider;
    this.model = model;
    this.messages = messages;
  }

  public UniChatRequest(String provider, String model, List<UniChatMessage> messages, Float temperature) {
    this.platform = provider;
    this.model = model;
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String provider, String model, String systemPrompt, List<UniChatMessage> messages,
      Float temperature) {
    this.systemPrompt = systemPrompt;
    this.platform = provider;
    this.model = model;
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest setUserPrompts(String... prompts) {
    List<UniChatMessage> messages = new ArrayList<>(prompts.length);
    for (String prompt : prompts) {
      if (StrUtil.isNotBlank(prompt)) {
        UniChatMessage part = new UniChatMessage(prompt);
        messages.add(part);
      }
    }

    this.messages = messages;
    return this;
  }

  public static UniChatRequest platform(String platform, String modelName) {
    UniChatRequest uniChatRequest = new UniChatRequest();
    uniChatRequest.setPlatform(platform);
    uniChatRequest.setModel(modelName);
    return uniChatRequest;
  }

  public static UniChatRequest platform(String platform, String modelName, ChatProvider provider) {
    UniChatRequest uniChatRequest = new UniChatRequest();
    uniChatRequest.setPlatform(platform);
    uniChatRequest.setModel(modelName);
    uniChatRequest.setProvider(provider);
    return uniChatRequest;
  }

  public UniChatRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniChatRequest(String domain, String apiPrefixUrl, Long groupId, String groupName, Long taskId,
      String taskName, boolean useSystemPrompt, String apiKey, String platform, String model, String systemPrompt,
      boolean cacheSystemPrompt, Boolean stream, List<UniChatMessage> messages, Float temperature, String cachedId,
      Integer max_tokens, Boolean enable_thinking, UniThinkingConfig thinkingConfig, String responseFormat,
      UniResponseSchema responseSchema, ChatProvider provider, List<String> responseModalities, Boolean enable_search) {
    super();
    this.domain = domain;
    this.apiPrefixUrl = apiPrefixUrl;
    this.groupId = groupId;
    this.groupName = groupName;
    this.taskId = taskId;
    this.taskName = taskName;
    this.useSystemPrompt = useSystemPrompt;
    this.apiKey = apiKey;
    this.platform = platform;
    this.model = model;
    this.systemPrompt = systemPrompt;
    this.cacheSystemPrompt = cacheSystemPrompt;
    this.stream = stream;
    this.messages = messages;
    this.temperature = temperature;
    this.cachedId = cachedId;
    this.max_tokens = max_tokens;
    this.enable_thinking = enable_thinking;
    this.thinkingConfig = thinkingConfig;
    this.responseFormat = responseFormat;
    this.responseSchema = responseSchema;
    this.provider = provider;
    this.responseModalities = responseModalities;
    this.enable_search = enable_search;
  }

  public String getDomain() {
    return domain;
  }

  public UniChatRequest setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public String getApiPrefixUrl() {
    return apiPrefixUrl;
  }

  public UniChatRequest setApiPrefixUrl(String apiPrefixUrl) {
    this.apiPrefixUrl = apiPrefixUrl;
    return this;
  }

  public Long getGroupId() {
    return groupId;
  }

  public UniChatRequest setGroupId(Long groupId) {
    this.groupId = groupId;
    return this;
  }

  public String getGroupName() {
    return groupName;
  }

  public UniChatRequest setGroupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  public Long getTaskId() {
    return taskId;
  }

  public UniChatRequest setTaskId(Long taskId) {
    this.taskId = taskId;
    return this;
  }

  public String getTaskName() {
    return taskName;
  }

  public UniChatRequest setTaskName(String taskName) {
    this.taskName = taskName;
    return this;
  }

  public boolean isUseSystemPrompt() {
    return useSystemPrompt;
  }

  public UniChatRequest setUseSystemPrompt(boolean useSystemPrompt) {
    this.useSystemPrompt = useSystemPrompt;
    return this;
  }

  public String getApiKey() {
    return apiKey;
  }

  public UniChatRequest setApiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  public String getPlatform() {
    return platform;
  }

  public UniChatRequest setPlatform(String platform) {
    this.platform = platform;
    return this;
  }

  public String getModel() {
    return model;
  }

  public UniChatRequest setModel(String model) {
    this.model = model;
    return this;
  }

  public String getSystemPrompt() {
    return systemPrompt;
  }

  public UniChatRequest setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
    return this;
  }

  public boolean isCacheSystemPrompt() {
    return cacheSystemPrompt;
  }

  public UniChatRequest setCacheSystemPrompt(boolean cacheSystemPrompt) {
    this.cacheSystemPrompt = cacheSystemPrompt;
    return this;
  }

  public Boolean getStream() {
    return stream;
  }

  public UniChatRequest setStream(Boolean stream) {
    this.stream = stream;
    return this;
  }

  public List<UniChatMessage> getMessages() {
    return messages;
  }

  public UniChatRequest setMessages(List<UniChatMessage> messages) {
    this.messages = messages;
    return this;
  }

  public Float getTemperature() {
    return temperature;
  }

  public UniChatRequest setTemperature(Float temperature) {
    this.temperature = temperature;
    return this;
  }

  public String getCachedId() {
    return cachedId;
  }

  public UniChatRequest setCachedId(String cachedId) {
    this.cachedId = cachedId;
    return this;
  }

  public Integer getMax_tokens() {
    return max_tokens;
  }

  public UniChatRequest setMax_tokens(Integer max_tokens) {
    this.max_tokens = max_tokens;
    return this;
  }

  public Boolean getEnable_thinking() {
    return enable_thinking;
  }

  public UniChatRequest setEnable_thinking(Boolean enable_thinking) {
    this.enable_thinking = enable_thinking;
    return this;
  }

  public UniThinkingConfig getThinkingConfig() {
    return thinkingConfig;
  }

  public UniChatRequest setThinkingConfig(UniThinkingConfig thinkingConfig) {
    this.thinkingConfig = thinkingConfig;
    return this;
  }

  public String getResponseFormat() {
    return responseFormat;
  }

  public UniChatRequest setResponseFormat(String responseFormat) {
    this.responseFormat = responseFormat;
    return this;
  }

  public UniResponseSchema getResponseSchema() {
    return responseSchema;
  }

  public UniChatRequest setResponseSchema(UniResponseSchema responseSchema) {
    this.responseSchema = responseSchema;
    return this;
  }

  public ChatProvider getProvider() {
    return provider;
  }

  public UniChatRequest setProvider(ChatProvider provider) {
    this.provider = provider;
    return this;
  }

  public List<String> getResponseModalities() {
    return responseModalities;
  }

  public UniChatRequest setResponseModalities(List<String> responseModalities) {
    this.responseModalities = responseModalities;
    return this;
  }

  public Boolean getEnable_search() {
    return enable_search;
  }

  public UniChatRequest setEnable_search(Boolean enable_search) {
    this.enable_search = enable_search;
    return this;
  }
}
