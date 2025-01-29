package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.tio.utils.json.Json;

public class OpenAiChatRequestVoTest {

  @Test
  public void testToJson() {
    ChatRequestImage chatRequestImage = new ChatRequestImage("1234", "1234");

    List<ChatMesageContent> multiContents = new ArrayList<>();
    multiContents.add(new ChatMesageContent("1234"));
    multiContents.add(new ChatMesageContent(chatRequestImage));

    OpenAiChatMessage openAiChatMessage = new OpenAiChatMessage();
    openAiChatMessage.role("user");
    openAiChatMessage.multiContents(multiContents);

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(openAiChatMessage);

    OpenAiChatRequestVo chatRequestVo = new OpenAiChatRequestVo();
    chatRequestVo.setModel("4o");
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    System.out.println(Json.getSkipNullJson().toJson(chatRequestVo));
  }

}
