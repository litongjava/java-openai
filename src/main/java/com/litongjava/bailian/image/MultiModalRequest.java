package com.litongjava.bailian.image;

public class MultiModalRequest {
  private String model;
  private MultiModalInput input;
  private MultiModalConversationParam parameters;

  public static MultiModalRequest build(String text) {
    MultiModalInput input = MultiModalInput.build(text);
    MultiModalConversationParam parameters = new MultiModalConversationParam();
    return build(input, parameters);
  }

  public static MultiModalRequest build(MultiModalInput input, MultiModalConversationParam parameters) {
    return new MultiModalRequest("qwen-image-plus", input, parameters);
  }

  public MultiModalRequest() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiModalRequest(String model, MultiModalInput input, MultiModalConversationParam parameters) {
    super();
    this.model = model;
    this.input = input;
    this.parameters = parameters;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public MultiModalInput getInput() {
    return input;
  }

  public void setInput(MultiModalInput input) {
    this.input = input;
  }

  public MultiModalConversationParam getParameters() {
    return parameters;
  }

  public void setParameters(MultiModalConversationParam parameters) {
    this.parameters = parameters;
  }

}
