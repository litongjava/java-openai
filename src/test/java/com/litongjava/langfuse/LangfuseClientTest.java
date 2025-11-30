package com.litongjava.langfuse;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class LangfuseClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    LangfuseClient client = new LangfuseClient();
    PromptPayload payload = client.getPrompt("Static Scene Write");
    System.out.println(JsonUtils.toJson(payload));
  }
}
