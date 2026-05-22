package nexus.io.openai.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OpenAiResponsesInputContent {
  private String type;
  private String text;
  private String image_url;
  private String detail;

  public static OpenAiResponsesInputContent text(String text) {
    return new OpenAiResponsesInputContent().setType("input_text").setText(text);
  }

  public static OpenAiResponsesInputContent imageUrl(String imageUrl) {
    return new OpenAiResponsesInputContent().setType("input_image").setImage_url(imageUrl);
  }

  public static OpenAiResponsesInputContent imageUrl(String imageUrl, String detail) {
    return imageUrl(imageUrl).setDetail(detail);
  }
}
