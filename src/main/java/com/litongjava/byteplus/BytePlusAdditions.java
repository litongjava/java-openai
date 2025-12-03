package com.litongjava.byteplus;

public class BytePlusAdditions {
  private Boolean enable_language_detector;
  private Boolean disable_markdown_filter;
  private Boolean enable_latex_tn;
  private Integer max_length_to_filter_parenthesis;
  private String explicit_language;
  private String context_language;
  private Boolean disable_default_bit_rate;
  private BytePlusCacheConfig cache_config;

  public Boolean getEnable_language_detector() {
    return enable_language_detector;
  }

  public void setEnable_language_detector(Boolean enable_language_detector) {
    this.enable_language_detector = enable_language_detector;
  }

  public Boolean getDisable_markdown_filter() {
    return disable_markdown_filter;
  }

  public void setDisable_markdown_filter(Boolean disable_markdown_filter) {
    this.disable_markdown_filter = disable_markdown_filter;
  }

  public Boolean getEnable_latex_tn() {
    return enable_latex_tn;
  }

  public void setEnable_latex_tn(Boolean enable_latex_tn) {
    this.enable_latex_tn = enable_latex_tn;
  }

  public Integer getMax_length_to_filter_parenthesis() {
    return max_length_to_filter_parenthesis;
  }

  public void setMax_length_to_filter_parenthesis(Integer max_length_to_filter_parenthesis) {
    this.max_length_to_filter_parenthesis = max_length_to_filter_parenthesis;
  }

  public String getExplicit_language() {
    return explicit_language;
  }

  public void setExplicit_language(String explicit_language) {
    this.explicit_language = explicit_language;
  }

  public String getContext_language() {
    return context_language;
  }

  public void setContext_language(String context_language) {
    this.context_language = context_language;
  }

  public Boolean getDisable_default_bit_rate() {
    return disable_default_bit_rate;
  }

  public void setDisable_default_bit_rate(Boolean disable_default_bit_rate) {
    this.disable_default_bit_rate = disable_default_bit_rate;
  }

  public BytePlusCacheConfig getCache_config() {
    return cache_config;
  }

  public void setCache_config(BytePlusCacheConfig cache_config) {
    this.cache_config = cache_config;
  }
}
