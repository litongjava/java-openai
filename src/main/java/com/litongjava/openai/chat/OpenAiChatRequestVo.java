package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

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
  private Boolean stream;
  private Boolean return_images;
  private List<OpenAiChatMessage> messages;
  private List<ChatRequestTool> tools;
  private Integer max_tokens;
  private Float temperature;
  private Float top_p;
  private Integer top_k;
  private Integer frequency_penalty;
  private String stop;

  public void fromMessages(List<ChatMessage> messages) {
    List<OpenAiChatMessage> openAimessages = new ArrayList<>(messages.size());
    for (int i = 0; i < messages.size(); i++) {
      openAimessages.add(new OpenAiChatMessage(messages.get(i)));
    }
    this.messages = openAimessages;
  }
}
