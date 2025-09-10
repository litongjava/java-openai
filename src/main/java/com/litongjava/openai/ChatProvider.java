package com.litongjava.openai;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.openrouter.OpenRouterProvider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatProvider {
  public List<String> only;

  public static ChatProvider cerebras() {
    ChatProvider chatProvider = new ChatProvider();
    List<String> only = new ArrayList<>();
    only.add(OpenRouterProvider.Cerebras);
    chatProvider.setOnly(only);
    return chatProvider;
  }
}
