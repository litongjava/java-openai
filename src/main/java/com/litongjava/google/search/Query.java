package com.litongjava.google.search;

import lombok.Data;

@Data
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
}
