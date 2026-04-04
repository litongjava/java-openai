package nexus.io.cloudflare;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudflareModelInfo {
  private String object;
  private List<CloudflareModelEntity> data;
}
