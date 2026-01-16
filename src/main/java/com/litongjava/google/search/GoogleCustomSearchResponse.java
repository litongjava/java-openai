package com.litongjava.google.search;

import java.util.List;

public class GoogleCustomSearchResponse {
  private String kind;
  private SearchUrl url;
  private Queries queries;
  private Context context;
  private SearchInformation searchInformation;
  private List<SearchResultItem> items;

  public GoogleCustomSearchResponse() {
    super();
    // TODO Auto-generated constructor stub
  }

  public GoogleCustomSearchResponse(String kind, SearchUrl url, Queries queries, Context context,
      SearchInformation searchInformation, List<SearchResultItem> items) {
    super();
    this.kind = kind;
    this.url = url;
    this.queries = queries;
    this.context = context;
    this.searchInformation = searchInformation;
    this.items = items;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public SearchUrl getUrl() {
    return url;
  }

  public void setUrl(SearchUrl url) {
    this.url = url;
  }

  public Queries getQueries() {
    return queries;
  }

  public void setQueries(Queries queries) {
    this.queries = queries;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public SearchInformation getSearchInformation() {
    return searchInformation;
  }

  public void setSearchInformation(SearchInformation searchInformation) {
    this.searchInformation = searchInformation;
  }

  public List<SearchResultItem> getItems() {
    return items;
  }

  public void setItems(List<SearchResultItem> items) {
    this.items = items;
  }

}
