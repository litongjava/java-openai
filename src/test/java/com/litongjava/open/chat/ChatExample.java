package com.litongjava.open.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.ChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class ChatExample {

  @Test
  public void simpleChatTest() {
    // load OPENAI_API_KEY from the config()
    EnvUtils.load();
    ChatMessage chatRequestMessage = new ChatMessage();
    chatRequestMessage.role("user");
    List<ChatMessage> messages = new ArrayList<>();

    ChatMessage message = new ChatMessage().role("user").content("How are you");

    messages.add(message);

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setStream(false);
    chatRequestVo.setModel("gpt-4o-2024-05-13");
    chatRequestVo.setMessages(messages);

    String json = JsonUtils.toJson(chatRequestVo);
    System.out.println(json);

    ChatResponseVo chatCompletions = null;
    try (Response response = OpenAiClient.chatCompletions(json)) {
      if (response.isSuccessful()) {
        String string = response.body().string();
        chatCompletions = JsonUtils.parse(string, ChatResponseVo.class);
        System.out.println(JsonUtils.toJson(chatCompletions));
      } else {
        System.out.println(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
