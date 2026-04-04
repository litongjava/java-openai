package nexus.io.openai.embedding;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nexus.io.openai.chat.ChatResponseUsage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingResponse {
  private String object;
  private String model;
  private List<EmbeddingData> data;
  private ChatResponseUsage usage;
}
