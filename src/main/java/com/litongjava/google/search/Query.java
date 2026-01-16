package com.litongjava.google.search;

public class Query {
  private String title;
  private String totalResults;
  private String searchTerms;
  private int count;
  private int startIndex;
  private String inputEncoding;
  private String outputEncoding;
  private String safe;
  private String cx;

  public Query() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Query(String title, String totalResults, String searchTerms, int count, int startIndex, String inputEncoding,
      String outputEncoding, String safe, String cx) {
    super();
    this.title = title;
    this.totalResults = totalResults;
    this.searchTerms = searchTerms;
    this.count = count;
    this.startIndex = startIndex;
    this.inputEncoding = inputEncoding;
    this.outputEncoding = outputEncoding;
    this.safe = safe;
    this.cx = cx;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(String totalResults) {
    this.totalResults = totalResults;
  }

  public String getSearchTerms() {
    return searchTerms;
  }

  public void setSearchTerms(String searchTerms) {
    this.searchTerms = searchTerms;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public String getInputEncoding() {
    return inputEncoding;
  }

  public void setInputEncoding(String inputEncoding) {
    this.inputEncoding = inputEncoding;
  }

  public String getOutputEncoding() {
    return outputEncoding;
  }

  public void setOutputEncoding(String outputEncoding) {
    this.outputEncoding = outputEncoding;
  }

  public String getSafe() {
    return safe;
  }

  public void setSafe(String safe) {
    this.safe = safe;
  }

  public String getCx() {
    return cx;
  }

  public void setCx(String cx) {
    this.cx = cx;
  }

}
