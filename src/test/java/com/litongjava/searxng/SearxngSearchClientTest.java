package com.litongjava.searxng;

import org.junit.Test;

import com.litongjava.tio.utils.json.JsonUtils;

public class SearxngSearchClientTest {

  @Test
  public void test() {
    SearxngSearchResponse response = SearxngSearchClient.search("KaiZhao at SJSU");
    System.out.println(JsonUtils.toJson(response));
  }
}
