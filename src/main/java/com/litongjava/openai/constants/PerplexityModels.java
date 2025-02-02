package com.litongjava.openai.constants;

public interface PerplexityModels {
  String SONAR_REASONING = "sonar-reasoning";
  String SONAR_PRO = "sonar-pro";
  String SONAR = "sonar";

  // the follows models will be deprecated and will no longer be available to use after 2/22/2025
  // 32k  online
  String LLAMA_3_SONAR_SMALL_32K_ONLINE = "llama-3-sonar-small-32k-online";
  String LLAMA_3_SONAR_LARGE_32K_ONLINE = "llama-3-sonar-large-32k-online";

  // 32k search
  String LLAMA_3_SONAR_SMALL_32K_CHAT = "llama-3-sonar-small-32k-chat";
  String LLAMA_3_SONAR_LARGE_32K_CHAT = "llama-3-sonar-large-32k-chat";

  // 128K online
  String LLAMA_3_1_SONAR_SMALL_128K_ONLINE = "llama-3.1-sonar-small-128k-online";
  String LLAMA_3_1_SONAR_LARGE_128K_ONLINE = "llama-3.1-sonar-large-128k-online";
  String LLAMA_3_1_SONAR_HUGE_128K_ONLINE = "llama-3.1-sonar-huge-128k-online";

  // 128k chat
  String LLAMA_3_1_SONAR_SMALL_128K_CHAT = "llama-3.1-sonar-small-128k-chat";
  String LLAMA_3_1_SONAR_LARGE_128K_CHAT = "llama-3.1-sonar-large-128k-chat";

  String LLAMA_3_8B_INSTRUCT = "llama-3-8b-instruct";
  String LLAMA_3_70B_INSTRUCT = "llama-3-70b-instruct";
  String MIXTRAL_8X7B_INSTRUCT = "mixtral-8x7b-instruct";
  String LLAMA_3_1_8B_INSTRUCT = "llama-3.1-8b-instruct";
  String LLAMA_3_1_70B_INSTRUCT = "llama-3.1-70b-instruct";

}
