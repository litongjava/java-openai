package com.litongjava.open.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequest;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class ChatExample {

  @Test
  public void simpleChatTest() {
    // load OPENAI_API_KEY from the config()
    EnvUtils.load();
    OpenAiChatMessage chatRequestMessage = new OpenAiChatMessage();
    chatRequestMessage.role("user");
    List<OpenAiChatMessage> messages = new ArrayList<>();

    OpenAiChatMessage message = new OpenAiChatMessage().role("user").content("How are you");

    messages.add(message);

    OpenAiChatRequest chatRequestVo = new OpenAiChatRequest();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println(json);

    OpenAiChatResponseVo chatCompletions = null;
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String string = response.body().string();
        chatCompletions = JsonUtils.parse(string, OpenAiChatResponseVo.class);
        System.out.println(JsonUtils.toJson(chatCompletions));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
