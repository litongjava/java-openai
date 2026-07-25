package nexus.io.gitee;

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
   * 分段内容列表（PaddleOCR-VL 等文档模型会使用该字段）
   */
  private List<GiteeDocumentSegment> segments;

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

  /**
   * 文本结果（部分音频任务会使用该字段）
   */
  private String text;

  /**
   * 内容结果（部分异步任务会使用该字段）
   */
  private String content;

  public List<GiteeDocumentPage> getPages() {
    return pages;
  }

  public void setPages(List<GiteeDocumentPage> pages) {
    this.pages = pages;
  }

  public List<GiteeDocumentSegment> getSegments() {
    return segments;
  }

  public void setSegments(List<GiteeDocumentSegment> segments) {
    this.segments = segments;
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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
