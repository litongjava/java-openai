package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nexus.io.openai.chat.ChatMessageContent;
import nexus.io.openai.chat.ChatRequestImage;
import nexus.io.openai.chat.OpenAiChatMessage;
import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.tio.utils.json.Json;

public class OpenAiChatRequestVoTest {

  @Test
  public void testToJson() {
    ChatRequestImage chatRequestImage = new ChatRequestImage("1234", "1234");

    List<ChatMessageContent> multiContents = new ArrayList<>();
    multiContents.add(new ChatMessageContent("1234"));
    multiContents.add(new ChatMessageContent(chatRequestImage));

    OpenAiChatMessage openAiChatMessage = new OpenAiChatMessage();
    openAiChatMessage.role("user");
    openAiChatMessage.multiContents(multiContents);

    List<OpenAiChatMessage> messages = new ArrayList<>();
    messages.add(openAiChatMessage);

    OpenAiChatRequest chatRequestVo = new OpenAiChatRequest();
    chatRequestVo.setModel("4o");
    chatRequestVo.setStream(false);
    chatRequestVo.setMessages(messages);
    System.out.println(Json.getSkipNullJson().toJson(chatRequestVo));
  }

}
