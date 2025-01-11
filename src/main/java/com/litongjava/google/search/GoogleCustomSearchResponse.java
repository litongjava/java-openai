package com.litongjava.google.search;

import java.util.List;

import lombok.Data;

@Data
public class GoogleCustomSearchResponse {
  private String kind;
  private SearchUrl url;
  private Queries queries;
  private Context context;
  private SearchInformation searchInformation;
  private List<SearchResultItem> items;
}
