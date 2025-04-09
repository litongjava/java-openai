package com.litongjava.gemini;

/**
 * 列出 Google Gemini & PaLM 等模型名称常量
 * 对应 JSON 中的 "name" 字段
 */
public interface GoogleGeminiModels {

  // PaLM 系列
  String CHAT_BISON_001 = "chat-bison-001";
  String TEXT_BISON_001 = "text-bison-001";
  String EMBEDDING_GECKO_001 = "embedding-gecko-001";

  // Gemini 1.0
  String GEMINI_1_0_PRO_LATEST = "gemini-1.0-pro-latest";
  String GEMINI_1_0_PRO = "gemini-1.0-pro";
  String GEMINI_PRO = "gemini-pro";
  String GEMINI_1_0_PRO_001 = "gemini-1.0-pro-001";
  String GEMINI_1_0_PRO_VISION_LATEST = "gemini-1.0-pro-vision-latest";
  String GEMINI_PRO_VISION = "gemini-pro-vision";

  // Gemini 1.5 Pro
  String GEMINI_1_5_PRO_LATEST = "gemini-1.5-pro-latest";
  String GEMINI_1_5_PRO_001 = "gemini-1.5-pro-001";
  String GEMINI_1_5_PRO_002 = "gemini-1.5-pro-002";
  String GEMINI_1_5_PRO = "gemini-1.5-pro";
  String GEMINI_1_5_PRO_EXP_0801 = "gemini-1.5-pro-exp-0801";
  String GEMINI_1_5_PRO_EXP_0827 = "gemini-1.5-pro-exp-0827";

  // Gemini 1.5 Flash
  String GEMINI_1_5_FLASH_LATEST = "gemini-1.5-flash-latest";
  String GEMINI_1_5_FLASH_001 = "gemini-1.5-flash-001";
  String GEMINI_1_5_FLASH_001_TUNING = "gemini-1.5-flash-001-tuning";
  String GEMINI_1_5_FLASH = "gemini-1.5-flash";
  String GEMINI_1_5_FLASH_EXP_0827 = "gemini-1.5-flash-exp-0827";
  String GEMINI_1_5_FLASH_002 = "gemini-1.5-flash-002";

  // Gemini 1.5 Flash-8B
  String GEMINI_1_5_FLASH_8B = "gemini-1.5-flash-8b";
  String GEMINI_1_5_FLASH_8B_001 = "gemini-1.5-flash-8b-001";
  String GEMINI_1_5_FLASH_8B_LATEST = "gemini-1.5-flash-8b-latest";
  String GEMINI_1_5_FLASH_8B_EXP_0827 = "gemini-1.5-flash-8b-exp-0827";
  String GEMINI_1_5_FLASH_8B_EXP_0924 = "gemini-1.5-flash-8b-exp-0924";

  // Gemini 2.0
  String GEMINI_2_0_FLASH = "gemini-2.0-flash";
  String GEMINI_2_0_FLASH_EXP = "gemini-2.0-flash-exp";
  String GEMINI_2_0_FLASH_THINKING_EXP = "gemini-2.0-flash-thinking-exp";
  String GEMINI_2_0_FLASH_THINKING_EXP_1219 = "gemini-2.0-flash-thinking-exp-1219";
  String GEMINI_2_5_PRO_EXP_03_25 = "gemini-2.5-pro-exp-03-25";
  String GEMINI_2_5_PRO_PREVIEW_03_25 = "gemini-2.5-pro-preview-03-25";
  

  // Gemini Experimental
  String GEMINI_EXP_1206 = "gemini-exp-1206";
  String GEMINI_EXP_1121 = "gemini-exp-1121";
  String GEMINI_EXP_1114 = "gemini-exp-1114";

  // LearnLM
  String LEARNLM_1_5_PRO_EXPERIMENTAL = "learnlm-1.5-pro-experimental";

  // Embedding
  String EMBEDDING_001 = "embedding-001";
  String TEXT_EMBEDDING_004 = "text-embedding-004";

  // AQA
  String AQA = "aqa";
}
