package com.litongjava.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.gemini.GroundingChunk;
import com.litongjava.gemini.GroundingChunkWeb;
import com.litongjava.gemini.GroundingMetadata;
import com.litongjava.model.web.WebPageContent;

public class UniSources {

  private List<WebPageContent> sources;
  private List<String> webSearchQueries;

  public UniSources(GroundingMetadata groundingMetadata) {
    this.webSearchQueries = groundingMetadata.getWebSearchQueries();
    List<GroundingChunk> groundingChunks = groundingMetadata.getGroundingChunks();
    sources = new ArrayList<>(groundingChunks.size());

    for (GroundingChunk groundingChunk : groundingChunks) {
      GroundingChunkWeb web = groundingChunk.getWeb();
      String uri = web.getUri();
      String title = web.getTitle();
      sources.add(new WebPageContent(title, uri));
    }
  }

  public UniSources() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniSources(List<WebPageContent> sources, List<String> webSearchQueries) {
    super();
    this.sources = sources;
    this.webSearchQueries = webSearchQueries;
  }

  public List<WebPageContent> getSources() {
    return sources;
  }

  public void setSources(List<WebPageContent> sources) {
    this.sources = sources;
  }

  public List<String> getWebSearchQueries() {
    return webSearchQueries;
  }

  public void setWebSearchQueries(List<String> webSearchQueries) {
    this.webSearchQueries = webSearchQueries;
  }

}
