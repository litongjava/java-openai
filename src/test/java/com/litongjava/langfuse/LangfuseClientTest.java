package com.litongjava.langfuse;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class LangfuseClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    LangfuseClient client = new LangfuseClient();

    // 最新版本
    PromptPayload payload = client.getPrompt("Static Scene Write");
    System.out.println(JsonUtils.toJson(payload));


    // 填充模板
//    Map<String, String> data = Map.of("userName", "Alice");
//    var filledMessages = payload.getPrompt().fillIn(data);

  }

}
