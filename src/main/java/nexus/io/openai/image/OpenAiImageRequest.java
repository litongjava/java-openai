package nexus.io.openai.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import nexus.io.image.UniImageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiImageRequest {
  private String model;
  private String prompt;
  private String size;
  private String user;
  private Integer n;
  private String response_format;

  public OpenAiImageRequest(String model, String prompt) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    // this.response_format = "url";
  }

  public OpenAiImageRequest(String model, String prompt, String size) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
  }

  public OpenAiImageRequest(String model, String prompt, String size, String response_format) {
    this.model = model;
    this.prompt = prompt;
    this.n = 1;
    this.size = size;
    this.response_format = response_format;
  }

  public OpenAiImageRequest(UniImageRequest request) {
    this.model = request.getModel();
    this.prompt = request.getPrompt();
    this.n = 1;
    this.user = request.getUser();
    this.size = request.getSize();
    this.response_format = request.getResponse_format();
  }
}
