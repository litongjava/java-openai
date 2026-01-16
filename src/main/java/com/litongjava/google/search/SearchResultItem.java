package com.litongjava.google.search;

import java.util.Map;

public class SearchResultItem {
  private String kind;
  private String title;
  private String htmlTitle;
  private String link;
  private String displayLink;
  private String snippet;
  private String htmlSnippet;
  private String formattedUrl;
  private String htmlFormattedUrl;
  private Map<String, Object> pagemap;

  public SearchResultItem() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SearchResultItem(String kind, String title, String htmlTitle, String link, String displayLink, String snippet,
      String htmlSnippet, String formattedUrl, String htmlFormattedUrl, Map<String, Object> pagemap) {
    super();
    this.kind = kind;
    this.title = title;
    this.htmlTitle = htmlTitle;
    this.link = link;
    this.displayLink = displayLink;
    this.snippet = snippet;
    this.htmlSnippet = htmlSnippet;
    this.formattedUrl = formattedUrl;
    this.htmlFormattedUrl = htmlFormattedUrl;
    this.pagemap = pagemap;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHtmlTitle() {
    return htmlTitle;
  }

  public void setHtmlTitle(String htmlTitle) {
    this.htmlTitle = htmlTitle;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getDisplayLink() {
    return displayLink;
  }

  public void setDisplayLink(String displayLink) {
    this.displayLink = displayLink;
  }

  public String getSnippet() {
    return snippet;
  }

  public void setSnippet(String snippet) {
    this.snippet = snippet;
  }

  public String getHtmlSnippet() {
    return htmlSnippet;
  }

  public void setHtmlSnippet(String htmlSnippet) {
    this.htmlSnippet = htmlSnippet;
  }

  public String getFormattedUrl() {
    return formattedUrl;
  }

  public void setFormattedUrl(String formattedUrl) {
    this.formattedUrl = formattedUrl;
  }

  public String getHtmlFormattedUrl() {
    return htmlFormattedUrl;
  }

  public void setHtmlFormattedUrl(String htmlFormattedUrl) {
    this.htmlFormattedUrl = htmlFormattedUrl;
  }

  public Map<String, Object> getPagemap() {
    return pagemap;
  }

  public void setPagemap(Map<String, Object> pagemap) {
    this.pagemap = pagemap;
  }

}
