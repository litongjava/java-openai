package nexus.io.gitee;

public class GiteeDocumentParseRequest {
  private String model;
  private Boolean include_image;
  private Boolean include_image_base64;
  private Integer end_pages;
  private String output_format;
  private String prompt;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Boolean getInclude_image() {
    return include_image;
  }

  public void setInclude_image(Boolean include_image) {
    this.include_image = include_image;
  }

  public Boolean getInclude_image_base64() {
    return include_image_base64;
  }

  public void setInclude_image_base64(Boolean include_image_base64) {
    this.include_image_base64 = include_image_base64;
  }

  public Integer getEnd_pages() {
    return end_pages;
  }

  public void setEnd_pages(Integer end_pages) {
    this.end_pages = end_pages;
  }

  public String getOutput_format() {
    return output_format;
  }

  public void setOutput_format(String output_format) {
    this.output_format = output_format;
  }

  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }
}
