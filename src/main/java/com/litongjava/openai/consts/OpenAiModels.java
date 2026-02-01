package com.litongjava.openai.consts;

public interface OpenAiModels {

  String OPENAI = "openai";

  // =========================
  // GPT-5 SERIES
  // =========================
  String GPT_5 = "gpt-5";
  String GPT_5_2025_08_07 = "gpt-5-2025-08-07";
  String GPT_5_CHAT_LATEST = "gpt-5-chat-latest";

  String GPT_5_MINI = "gpt-5-mini";
  String GPT_5_MINI_2025_08_07 = "gpt-5-mini-2025-08-07";

  String GPT_5_NANO = "gpt-5-nano";
  String GPT_5_NANO_2025_08_07 = "gpt-5-nano-2025-08-07";

  String GPT_5_PRO = "gpt-5-pro";
  String GPT_5_PRO_2025_10_06 = "gpt-5-pro-2025-10-06";

  String GPT_5_CODEX = "gpt-5-codex";

  String GPT_5_SEARCH_API = "gpt-5-search-api";
  String GPT_5_SEARCH_API_2025_10_14 = "gpt-5-search-api-2025-10-14";

  String GPT_5_1 = "gpt-5.1";
  String GPT_5_1_2025_11_13 = "gpt-5.1-2025-11-13";
  String GPT_5_1_CHAT_LATEST = "gpt-5.1-chat-latest";
  String GPT_5_1_CODEX = "gpt-5.1-codex";
  String GPT_5_1_CODEX_MINI = "gpt-5.1-codex-mini";
  String GPT_5_1_CODEX_MAX = "gpt-5.1-codex-max";

  String GPT_5_2 = "gpt-5.2";
  String GPT_5_2_2025_12_11 = "gpt-5.2-2025-12-11";
  String GPT_5_2_CHAT_LATEST = "gpt-5.2-chat-latest";
  String GPT_5_2_PRO = "gpt-5.2-pro";
  String GPT_5_2_PRO_2025_12_11 = "gpt-5.2-pro-2025-12-11";

  String GPT_5_2_CODEX = "gpt-5.2-codex";

  // =========================
  // GPT-4.1 SERIES
  // =========================
  String GPT_4_1 = "gpt-4.1";
  String GPT_4_1_2025_04_14 = "gpt-4.1-2025-04-14";

  String GPT_4_1_MINI = "gpt-4.1-mini";
  String GPT_4_1_MINI_2025_04_14 = "gpt-4.1-mini-2025-04-14";

  String GPT_4_1_NANO = "gpt-4.1-nano";
  String GPT_4_1_NANO_2025_04_14 = "gpt-4.1-nano-2025-04-14";

  // =========================
  // GPT-4 / GPT-4 TURBO / GPT-4o
  // =========================
  String GPT_4 = "gpt-4";
  String GPT_4_0613 = "gpt-4-0613";

  String GPT_4_1106_PREVIEW = "gpt-4-1106-preview";
  String GPT_4_0125_PREVIEW = "gpt-4-0125-preview";

  String GPT_4_TURBO = "gpt-4-turbo";
  String GPT_4_TURBO_PREVIEW = "gpt-4-turbo-preview";
  String GPT_4_TURBO_2024_04_09 = "gpt-4-turbo-2024-04-09";

  String GPT_4O = "gpt-4o";
  String GPT_4O_2024_05_13 = "gpt-4o-2024-05-13";
  String GPT_4O_2024_08_06 = "gpt-4o-2024-08-06";
  String GPT_4O_2024_11_20 = "gpt-4o-2024-11-20";
  String GPT_4O_LATEST = "chatgpt-4o-latest";

  String GPT_4O_MINI = "gpt-4o-mini";
  String GPT_4O_MINI_2024_07_18 = "gpt-4o-mini-2024-07-18";

  // =========================
  // o SERIES
  // =========================
  String O1 = "o1";
  String O1_2024_12_17 = "o1-2024-12-17";
  String O1_PRO = "o1-pro";
  String O1_PRO_2025_03_19 = "o1-pro-2025-03-19";

  String O3 = "o3";
  String O3_2025_01_31 = "o3-mini-2025-01-31";
  String O3_2025_04_16 = "o3-2025-04-16";

  String O4_MINI = "o4-mini";
  String O4_MINI_2025_04_16 = "o4-mini-2025-04-16";

  String O4_MINI_DEEP_RESEARCH = "o4-mini-deep-research";
  String O4_MINI_DEEP_RESEARCH_2025_06_26 = "o4-mini-deep-research-2025-06-26";

  // =========================
  // GPT-3.5 / LEGACY
  // =========================
  String GPT_3_5_TURBO = "gpt-3.5-turbo";
  String GPT_3_5_TURBO_1106 = "gpt-3.5-turbo-1106";
  String GPT_3_5_TURBO_0125 = "gpt-3.5-turbo-0125";
  String GPT_3_5_TURBO_16K = "gpt-3.5-turbo-16k";

  String GPT_3_5_TURBO_INSTRUCT = "gpt-3.5-turbo-instruct";
  String GPT_3_5_TURBO_INSTRUCT_0914 = "gpt-3.5-turbo-instruct-0914";

  String DAVINCI_002 = "davinci-002";
  String BABBAGE_002 = "babbage-002";

  // =========================
  // CODEX
  // =========================
  String CODEX_MINI_LATEST = "codex-mini-latest";

  // =========================
  // EMBEDDING
  // =========================
  String TEXT_EMBEDDING_ADA_002 = "text-embedding-ada-002";
  String TEXT_EMBEDDING_3_SMALL = "text-embedding-3-small";
  String TEXT_EMBEDDING_3_LARGE = "text-embedding-3-large";

  // =========================
  // AUDIO / TTS / REALTIME / ASR
  // =========================
  String GPT_AUDIO = "gpt-audio";
  String GPT_AUDIO_2025_08_28 = "gpt-audio-2025-08-28";

  String GPT_AUDIO_MINI = "gpt-audio-mini";
  String GPT_AUDIO_MINI_2025_10_06 = "gpt-audio-mini-2025-10-06";

  String GPT_4O_AUDIO_PREVIEW = "gpt-4o-audio-preview";
  String GPT_4O_AUDIO_PREVIEW_2024_12_17 = "gpt-4o-audio-preview-2024-12-17";
  String GPT_4O_AUDIO_PREVIEW_2025_06_03 = "gpt-4o-audio-preview-2025-06-03";

  String GPT_4O_MINI_AUDIO_PREVIEW = "gpt-4o-mini-audio-preview";
  String GPT_4O_MINI_AUDIO_PREVIEW_2024_12_17 = "gpt-4o-mini-audio-preview-2024-12-17";

  String GPT_4O_TRANSCRIBE = "gpt-4o-transcribe";
  String GPT_4O_TRANSCRIBE_DIARIZE = "gpt-4o-transcribe-diarize";

  String GPT_4O_MINI_TRANSCRIBE = "gpt-4o-mini-transcribe";
  String GPT_4O_MINI_TRANSCRIBE_2025_03_20 = "gpt-4o-mini-transcribe-2025-03-20";
  String GPT_4O_MINI_TRANSCRIBE_2025_12_15 = "gpt-4o-mini-transcribe-2025-12-15";

  String TTS_1 = "tts-1";
  String TTS_1_1106 = "tts-1-1106";
  String TTS_1_HD = "tts-1-hd";
  String TTS_1_HD_1106 = "tts-1-hd-1106";

  String GPT_4O_MINI_TTS = "gpt-4o-mini-tts";
  String GPT_4O_MINI_TTS_2025_03_20 = "gpt-4o-mini-tts-2025-03-20";
  String GPT_4O_MINI_TTS_2025_12_15 = "gpt-4o-mini-tts-2025-12-15";

  String WHISPER_1 = "whisper-1";

  // =========================
  // IMAGE / VIDEO
  // =========================
  String GPT_IMAGE_1 = "gpt-image-1";
  String GPT_IMAGE_1_MINI = "gpt-image-1-mini";
  String GPT_IMAGE_1_5 = "gpt-image-1.5";

  String DALL_E_2 = "dall-e-2";
  String DALL_E_3 = "dall-e-3";

  String SORA_2 = "sora-2";
  String SORA_2_PRO = "sora-2-pro";

  // =========================
  // MODERATION
  // =========================
  String OMNI_MODERATION_LATEST = "omni-moderation-latest";
  String OMNI_MODERATION_2024_09_26 = "omni-moderation-2024-09-26";
}