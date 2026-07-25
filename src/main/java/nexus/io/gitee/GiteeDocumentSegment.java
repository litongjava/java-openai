package nexus.io.gitee;

/**
 * 文档解析接口返回的分段内容。
 */
public class GiteeDocumentSegment {
  private Integer index;
  private String content;

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
