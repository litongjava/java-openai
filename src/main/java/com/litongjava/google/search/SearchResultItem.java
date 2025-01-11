package com.litongjava.google.search;

import lombok.Data;
import java.util.Map;

@Data
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
}
