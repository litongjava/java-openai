package com.litongjava.langfuse;

import org.junit.Test;

import nexus.io.langfuse.LangfuseClient;
import nexus.io.langfuse.PromptPayload;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class LangfuseClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    LangfuseClient client = new LangfuseClient();
    PromptPayload payload = client.getPrompt("Static Scene Write");
    System.out.println(JsonUtils.toJson(payload));
  }
}
