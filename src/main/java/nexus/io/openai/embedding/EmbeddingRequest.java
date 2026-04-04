package nexus.io.openai.embedding;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbeddingRequest {
  private String model;
  private Object input;

  public EmbeddingRequest input(String input) {
    this.input = input;
    return this;
  }

  public EmbeddingRequest model(String model) {
    this.model = model;
    return this;
  }

  public EmbeddingRequest(String input) {
    this.input = input;
  }

  public EmbeddingRequest(List<String> input) {
    this.input = input;
  }

  public String getInputStr() {
    if (input != null) {
      return (String) input;
    }
    return null;
  }
}
