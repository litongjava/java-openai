package com.litongjava.gitee;

import org.junit.Test;

import com.litongjava.openai.chat.OpenAiChatResponse;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class GiteeConstTest {

  @Test
  public void test() {
    EnvUtils.load();
    String apiKey = EnvUtils.get("GITEE_API_KEY");
    OpenAiChatResponse chatResponse = OpenAiClient.chatWithModel(GiteeConst.API_PREFIX_URL, apiKey, GiteeModels.QWEN2_7B_INSTRUCT, "user", "什么是向量");
    System.out.println(JsonUtils.toSkipNullJson(chatResponse));
  }

}
