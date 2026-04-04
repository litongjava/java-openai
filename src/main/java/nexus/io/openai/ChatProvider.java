package nexus.io.openai;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nexus.io.openrouter.OpenRouterProvider;

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
