package com.litongjava.claude;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class ClaudeClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    ClaudeChatResponseVo chat = ClaudeClient.chat("Hi");
    System.out.println(JsonUtils.toJson(chat));
    
  }

}
