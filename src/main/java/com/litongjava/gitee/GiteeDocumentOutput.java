package com.litongjava.gitee;

import java.util.List;

/**
 * 对应任务输出中的整体文档结果
 */
public class GiteeDocumentOutput {

  /**
   * 页面列表
   */
  private List<GiteeDocumentPage> pages;

  /**
   * 调用时的 prompt
   */
  private String prompt;

  /**
   * 模型规格，例如 Gundam
   */
  private String model_size;

  /**
   * 文本整体结果（部分接口会在这里给 markdown 或摘要）
   */
  private String text_result;

  public List<GiteeDocumentPage> getPages() {
    return pages;
  }

  public void setPages(List<GiteeDocumentPage> pages) {
    this.pages = pages;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public String getModel_size() {
    return model_size;
  }

  public void setModel_size(String model_size) {
    this.model_size = model_size;
  }

  public String getText_result() {
    return text_result;
  }

  public void setText_result(String text_result) {
    this.text_result = text_result;
  }
}
