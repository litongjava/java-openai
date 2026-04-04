package com.litongjava.claude;

import org.junit.Test;

import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeClient;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class ClaudeClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    ClaudeChatResponse chat = ClaudeClient.chat("Hi");
    System.out.println(JsonUtils.toJson(chat));
  }
}
