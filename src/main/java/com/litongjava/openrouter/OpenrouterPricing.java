package com.litongjava.openrouter;

public class OpenrouterPricing {

  private String prompt;
  private String completion;
  private String request;
  private String image;
  private String web_search;
  private String internal_reasoning;

  public OpenrouterPricing() {
  }

  public OpenrouterPricing(String prompt, String completion, String request, String image, String web_search,
      String internal_reasoning) {
    this.prompt = prompt;
    this.completion = completion;
    this.request = request;
    this.image = image;
    this.web_search = web_search;
    this.internal_reasoning = internal_reasoning;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public String getCompletion() {
    return completion;
  }

  public void setCompletion(String completion) {
    this.completion = completion;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getWeb_search() {
    return web_search;
  }

  public void setWeb_search(String web_search) {
    this.web_search = web_search;
  }

  public String getInternal_reasoning() {
    return internal_reasoning;
  }

  public void setInternal_reasoning(String internal_reasoning) {
    this.internal_reasoning = internal_reasoning;
  }
}
