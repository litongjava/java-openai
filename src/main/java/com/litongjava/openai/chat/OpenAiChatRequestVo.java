package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.chat.UniChatMessage;
import com.litongjava.claude.ClaudeMessageContent;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.openai.ChatProvider;

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
  private List<ClaudeMessageContent> system;
  private List<OpenAiChatMessage> messages;
  private List<ChatRequestTool> tools;
  private Integer max_tokens;
  private Float temperature;
  private Float top_p;
  private Float frequency_penalty;
  private Float prescene_penalty;

  private ChatResponseFormat response_format;
  private String stop;
  private Boolean stream;
  private ChatStreamOptions stream_options;
  private Boolean enable_thinking;

  // https://inference-docs.cerebras.ai/resources/openrouter-cerebras
  private ChatProvider provider;

  public void fromMessages(List<UniChatMessage> messages) {
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

  public OpenAiChatRequestVo setChatMessages(List<UniChatMessage> messages) {
    List<OpenAiChatMessage> openAiMessages = new ArrayList<>();
    for (UniChatMessage message : messages) {
      openAiMessages.add(new OpenAiChatMessage(message));
    }
    this.messages = openAiMessages;
    return this;
  }

  public OpenAiChatRequestVo setChatMessages(List<UniChatMessage> messages, String provider) {
    if (ModelPlatformName.ANTHROPIC.equals(provider)) {
      List<OpenAiChatMessage> openAiMessages = new ArrayList<>();
      for (UniChatMessage message : messages) {
        openAiMessages.add(new OpenAiChatMessage(message, provider));
      }
      this.messages = openAiMessages;
      return this;

    } else {
      setChatMessages(messages);
    }
    return this;
  }

  public void setSystemChatMessage(ClaudeMessageContent claudeChatMessage) {
    List<ClaudeMessageContent> system = new ArrayList<>(1);
    system.add(claudeChatMessage);
    this.system = system;
  }
}
