package com.litongjava.searchapi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchapiResult {
  private SearchapiMetadata search_metadata;
  private SearchapiParameters search_parameters;
  private SearchapiInformation search_information;
  private List<SearchapiOrganicResult> organic_results;
  private List<SearchapiRelatedQuestion> related_questions;
  private SearchapiPagination pagination;
}
