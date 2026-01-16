package com.litongjava.gemini;

import java.util.List;

public class GroundingMetadata {
  private SearchEntryPoint searchEntryPoint;
  private List<GroundingChunk> groundingChunks;
  private List<GroundingSupport> groundingSupports;
  private List<String> webSearchQueries;

  public GroundingMetadata() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GroundingMetadata(SearchEntryPoint searchEntryPoint, List<GroundingChunk> groundingChunks,
      List<GroundingSupport> groundingSupports, List<String> webSearchQueries) {
    super();
    this.searchEntryPoint = searchEntryPoint;
    this.groundingChunks = groundingChunks;
    this.groundingSupports = groundingSupports;
    this.webSearchQueries = webSearchQueries;
  }

  public SearchEntryPoint getSearchEntryPoint() {
    return searchEntryPoint;
  }

  public void setSearchEntryPoint(SearchEntryPoint searchEntryPoint) {
    this.searchEntryPoint = searchEntryPoint;
  }

  public List<GroundingChunk> getGroundingChunks() {
    return groundingChunks;
  }

  public void setGroundingChunks(List<GroundingChunk> groundingChunks) {
    this.groundingChunks = groundingChunks;
  }

  public List<GroundingSupport> getGroundingSupports() {
    return groundingSupports;
  }

  public void setGroundingSupports(List<GroundingSupport> groundingSupports) {
    this.groundingSupports = groundingSupports;
  }

  public List<String> getWebSearchQueries() {
    return webSearchQueries;
  }

  public void setWebSearchQueries(List<String> webSearchQueries) {
    this.webSearchQueries = webSearchQueries;
  }

}
