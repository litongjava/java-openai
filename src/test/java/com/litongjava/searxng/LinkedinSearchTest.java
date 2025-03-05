package com.litongjava.searxng;

import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.json.JsonUtils;

public class LinkedinSearchTest {

  @Test
  public void test() {
    SearxngSearchResponse response = LinkedinSearch.person("Nicolaus Hilleary", "SJSU");
    List<SearxngResult> results = response.getResults();
    System.out.println(JsonUtils.toJson(results));
  }

}
