package nexus.io.openai.chat;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nexus.io.chat.ChatImageFile;
import nexus.io.chat.UniChatMessage;

public class OpenAiChatMessageVideoTest {

  @Test
  public void convertsVideoFilesToVideoUrlContent() {
    UniChatMessage source = new UniChatMessage("user", "describe");
    source.setFiles(Arrays.asList(ChatImageFile.base64("video/mp4", "data:video/mp4;base64,AAAA")));
    OpenAiChatMessage message = new OpenAiChatMessage(source);
    @SuppressWarnings("unchecked")
    List<ChatMessageContent> contents = (List<ChatMessageContent>) message.getContent();
    assertEquals("video_url", contents.get(1).getType());
    assertEquals("data:video/mp4;base64,AAAA", contents.get(1).getVideo_url().getUrl());
  }
}