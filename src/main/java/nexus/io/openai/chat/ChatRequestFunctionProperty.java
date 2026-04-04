package nexus.io.openai.chat;

import java.util.List;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestFunctionProperty {

  private String type;
  private String description;

  @JSONField(name = "enum")
  private List<String> enumList;

  public ChatRequestFunctionProperty(String type, String description) {
    this.type = type;
    this.description = description;
  }
}