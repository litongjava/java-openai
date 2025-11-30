package com.litongjava.langfuse;

import com.litongjava.tio.utils.environment.EnvUtils;

public class LangfuseConfig {

  private final String secretKey;
  private final String publicKey;
  private final String baseURL;
  private final String label;

  public LangfuseConfig(String secretKey, String publicKey, String baseURL, String label) {
    this.secretKey = secretKey;
    this.publicKey = publicKey;
    this.baseURL = baseURL;
    this.label = label;
  }

  public static LangfuseConfig fromEnv() {
    String secretKey = EnvUtils.getStr("LANGFUSE_SECRET_KEY");
    if (secretKey == null || secretKey.isEmpty()) {
      throw new IllegalStateException("LANGFUSE_SECRET_KEY missing");
    }

    String publicKey = EnvUtils.getStr("LANGFUSE_PUBLIC_KEY");
    if (publicKey == null || publicKey.isEmpty()) {
      throw new IllegalStateException("LANGFUSE_PUBLIC_KEY missing");
    }

    String baseURL = EnvUtils.getStr("LANGFUSE_BASE_URL");
    if (baseURL == null || baseURL.isEmpty()) {
      baseURL = LangfuseConst.baseURL;
    }

    // 和 Kotlin 版本保持一致，默认 production
    String label = "production";

    return new LangfuseConfig(secretKey, publicKey, baseURL, label);
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public String getBaseURL() {
    return baseURL;
  }

  public String getLabel() {
    return label;
  }
}
