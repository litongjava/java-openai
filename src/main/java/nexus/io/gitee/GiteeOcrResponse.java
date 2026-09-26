package nexus.io.gitee;

/** Response from the synchronous /v1/images/ocr endpoint. */
public class GiteeOcrResponse {
  private String text;
  private String text_result;
  private String prompt;

  public String getText() {
    return text != null ? text : text_result;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getText_result() {
    return text_result;
  }

  public void setText_result(String text_result) {
    this.text_result = text_result;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }
}
