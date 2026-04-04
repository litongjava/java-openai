package com.litongjava.claude;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeClient;

public class ClaudeClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    ClaudeChatResponse chat = ClaudeClient.chat("Hi");
    System.out.println(JsonUtils.toJson(chat));
  }
}
