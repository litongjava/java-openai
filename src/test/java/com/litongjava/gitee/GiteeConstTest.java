package com.litongjava.gitee;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import nexus.io.gitee.GiteeConst;
import nexus.io.gitee.GiteeModels;
import nexus.io.openai.chat.OpenAiChatResponse;
import nexus.io.openai.client.OpenAiClient;

public class GiteeConstTest {

  @Test
  public void test() {
    EnvUtils.load();
    String apiKey = EnvUtils.get("GITEE_API_KEY");
    OpenAiChatResponse chatResponse = OpenAiClient.chatWithModel(GiteeConst.API_PREFIX_URL, apiKey, GiteeModels.QWEN2_7B_INSTRUCT, "user", "什么是向量");
    System.out.println(JsonUtils.toSkipNullJson(chatResponse));
  }

}
