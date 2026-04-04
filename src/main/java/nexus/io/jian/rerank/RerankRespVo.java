package nexus.io.jian.rerank;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import nexus.io.openai.chat.ChatResponseUsage;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class RerankRespVo {
  private String model;
  private ChatResponseUsage usage;
  private List<RerankResult> results;
}
