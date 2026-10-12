package nexus.io.claude;

import lombok.Data;

/** Search errors can be embedded in an HTTP 200 Messages response. */
@Data
public class ClaudeWebSearchError {
  private String type;
  private String error_code;
}
