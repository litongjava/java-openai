package com.litongjava.searxng;

public class LinkedinSearch {
  public static SearxngSearchResponse person(String name, String company) {
    String q = "site:linkedin.com/in/ %s %s";
    q = String.format(q, name, company);
    return SearxngSearchClient.search(q);
  }
}
