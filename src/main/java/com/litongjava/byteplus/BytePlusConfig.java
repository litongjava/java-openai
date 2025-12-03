package com.litongjava.byteplus;

import com.litongjava.tio.utils.environment.EnvUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BytePlusConfig {
  private String base_url = "https://voice.ap-southeast-1.bytepluses.com";
  private String tts_url = base_url + "/api/v3/tts/unidirectional";
  private String resource_id = "volc.service_type.1000009";

  private String app_id;
  private String access_key;

  public BytePlusConfig() {
    app_id = EnvUtils.getStr("BYTE_PLUS_APP_ID");
    if (app_id == null) {
      throw new RuntimeException("app_id can not be null");
    }
    access_key = EnvUtils.getStr("BYTE_PLUS_ACCESS_KEY");
    if (access_key == null) {
      throw new RuntimeException("access_key can not be null");
    }
  }
}
