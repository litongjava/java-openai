package nexus.io.search;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nexus.io.model.web.WebPageContent;
import nexus.io.openai.chat.SearchReturnImage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchChatResponse {
  private String content;
  private List<WebPageContent> citations;
  private List<SearchReturnImage> images;
}
