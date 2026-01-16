package com.litongjava.claude;

public class ClaudeChatUsage {
  private Integer input_tokens;
  private Integer output_tokens;
  private Integer cache_creation_input_tokens;
  private Integer cache_read_input_tokens;

  public ClaudeChatUsage() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ClaudeChatUsage(Integer input_tokens, Integer output_tokens, Integer cache_creation_input_tokens,
      Integer cache_read_input_tokens) {
    super();
    this.input_tokens = input_tokens;
    this.output_tokens = output_tokens;
    this.cache_creation_input_tokens = cache_creation_input_tokens;
    this.cache_read_input_tokens = cache_read_input_tokens;
  }

  public Integer getInput_tokens() {
    return input_tokens;
  }

  public void setInput_tokens(Integer input_tokens) {
    this.input_tokens = input_tokens;
  }

  public Integer getOutput_tokens() {
    return output_tokens;
  }

  public void setOutput_tokens(Integer output_tokens) {
    this.output_tokens = output_tokens;
  }

  public Integer getCache_creation_input_tokens() {
    return cache_creation_input_tokens;
  }

  public void setCache_creation_input_tokens(Integer cache_creation_input_tokens) {
    this.cache_creation_input_tokens = cache_creation_input_tokens;
  }

  public Integer getCache_read_input_tokens() {
    return cache_read_input_tokens;
  }

  public void setCache_read_input_tokens(Integer cache_read_input_tokens) {
    this.cache_read_input_tokens = cache_read_input_tokens;
  }

}
