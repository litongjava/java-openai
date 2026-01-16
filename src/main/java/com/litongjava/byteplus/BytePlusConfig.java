package com.litongjava.byteplus;

import com.litongjava.tio.utils.environment.EnvUtils;

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

  public BytePlusConfig(String base_url, String tts_url, String resource_id, String app_id, String access_key) {
    super();
    this.base_url = base_url;
    this.tts_url = tts_url;
    this.resource_id = resource_id;
    this.app_id = app_id;
    this.access_key = access_key;
  }

  public String getBase_url() {
    return base_url;
  }

  public void setBase_url(String base_url) {
    this.base_url = base_url;
  }

  public String getTts_url() {
    return tts_url;
  }

  public void setTts_url(String tts_url) {
    this.tts_url = tts_url;
  }

  public String getResource_id() {
    return resource_id;
  }

  public void setResource_id(String resource_id) {
    this.resource_id = resource_id;
  }

  public String getApp_id() {
    return app_id;
  }

  public void setApp_id(String app_id) {
    this.app_id = app_id;
  }

  public String getAccess_key() {
    return access_key;
  }

  public void setAccess_key(String access_key) {
    this.access_key = access_key;
  }

}
