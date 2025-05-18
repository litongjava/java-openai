package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.chat.ChatMessage;
import com.litongjava.claude.ClaudeChatMessage;
import com.litongjava.consts.AiProviderName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiChatRequestVo {
  private String model;
  private Boolean return_images;
  private List<ClaudeChatMessage> system;
  private List<OpenAiChatMessage> messages;
  private List<ChatRequestTool> tools;
  private Integer max_tokens;
  private Float temperature;
  private Float top_p;
  private Float frequency_penalty;
  private Float presence_penalty;

  private ChatResponseFormat response_format;
  private String stop;
  private Boolean stream;
  private ChatStreamOptions stream_options;

  public void fromMessages(List<ChatMessage> messages) {
    List<OpenAiChatMessage> openAimessages = new ArrayList<>(messages.size());
    for (int i = 0; i < messages.size(); i++) {
      openAimessages.add(new OpenAiChatMessage(messages.get(i)));
    }
    this.messages = openAimessages;
  }

  public void setResponse_format(String type) {
    this.response_format = new ChatResponseFormat(type);
  }

  public void setStreamIncludeUsage(boolean b) {
    this.stream_options = new ChatStreamOptions(b);

  }

  public OpenAiChatRequestVo setChatMessages(List<ChatMessage> messages) {
    List<OpenAiChatMessage> openAiMessages = new ArrayList<>();
    for (ChatMessage message : messages) {
      openAiMessages.add(new OpenAiChatMessage(message));
    }
    this.messages = openAiMessages;
    return this;
  }

  public OpenAiChatRequestVo setChatMessages(List<ChatMessage> messages, String provider) {
    if (AiProviderName.CLAUDE.equals(provider)) {

    } else {
      List<OpenAiChatMessage> openAiMessages = new ArrayList<>();
      for (ChatMessage message : messages) {
        openAiMessages.add(new OpenAiChatMessage(message));
      }
      this.messages = openAiMessages;
    }
    return this;
  }

  public void setSystemChatMessage(ClaudeChatMessage claudeChatMessage) {
    List<ClaudeChatMessage> system = new ArrayList<>(1);
    system.add(claudeChatMessage);
    this.system = system;
  }
}
