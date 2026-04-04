package nexus.io.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatModelEntity {
  private String id;
  private String object;
  private Long created;
  private String owned_by;
}
