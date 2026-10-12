package nexus.io.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Source returned by a search tool or cited in the answer. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniWebSearchSource {
  private String url;
  private String title;
}
