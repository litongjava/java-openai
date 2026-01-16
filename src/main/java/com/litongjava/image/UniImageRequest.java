package com.litongjava.image;

import com.litongjava.gitee.GiteeModels;
import com.litongjava.openai.consts.OpenAiModels;

public class UniImageRequest {
  private String apiKey;
  private String platform;

  private String model;
  private String prompt;
  private String size;
  private String user;
  private Integer n;
  private String response_format;

  public UniImageRequest(String model, String prompt) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    // this.response_format = "url";
  }

  public UniImageRequest(String model, String prompt, String size) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
  }

  public UniImageRequest(String model, String prompt, String size, String response_format) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
    this.response_format = response_format;
  }

  public static UniImageRequest buildGptImage1(String prompt) {
    return new UniImageRequest(OpenAiModels.GPT_IMAGE_1, prompt);
  }

  public static UniImageRequest buildQwenImage(String prompt) {
    return new UniImageRequest(GiteeModels.QWEN_IMAGE, prompt);
  }

  public UniImageRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniImageRequest(String apiKey, String platform, String model, String prompt, String size, String user,
      Integer n, String response_format) {
    super();
    this.apiKey = apiKey;
    this.platform = platform;
    this.model = model;
    this.prompt = prompt;
    this.size = size;
    this.user = user;
    this.n = n;
    this.response_format = response_format;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Integer getN() {
    return n;
  }

  public void setN(Integer n) {
    this.n = n;
  }

  public String getResponse_format() {
    return response_format;
  }

  public void setResponse_format(String response_format) {
    this.response_format = response_format;
  }

}
