package nexus.io.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatModelResponse {
  private List<ChatModelEntity> data;
  private String object;
  private boolean success;

}
