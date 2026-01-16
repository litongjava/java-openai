package com.litongjava.bailian.image;

public class MultiModalResponse {
  private String rawResponse;
  private String request_id;
  private MultiModalUsage usage;
  private MultiModalOutput output;

  public String getImageUrl() {
    return output.getChoices().get(0).getMessage().getContent().get(0).getImage();
  }

  public MultiModalResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalResponse(String rawResponse, String request_id, MultiModalUsage usage, MultiModalOutput output) {
    super();
    this.rawResponse = rawResponse;
    this.request_id = request_id;
    this.usage = usage;
    this.output = output;
  }

  public String getRawResponse() {
    return rawResponse;
  }

  public void setRawResponse(String rawResponse) {
    this.rawResponse = rawResponse;
  }

  public String getRequest_id() {
    return request_id;
  }

  public void setRequest_id(String request_id) {
    this.request_id = request_id;
  }

  public MultiModalUsage getUsage() {
    return usage;
  }

  public void setUsage(MultiModalUsage usage) {
    this.usage = usage;
  }

  public MultiModalOutput getOutput() {
    return output;
  }

  public void setOutput(MultiModalOutput output) {
    this.output = output;
  }

}
