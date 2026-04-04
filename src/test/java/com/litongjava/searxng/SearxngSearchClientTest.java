package com.litongjava.searxng;

import org.junit.Test;

import nexus.io.searxng.SearxngSearchClient;
import nexus.io.searxng.SearxngSearchResponse;
import nexus.io.tio.utils.json.JsonUtils;

public class SearxngSearchClientTest {

  @Test
  public void test() {
    SearxngSearchResponse response = SearxngSearchClient.search("KaiZhao at SJSU");
    System.out.println(JsonUtils.toJson(response));
  }
}
