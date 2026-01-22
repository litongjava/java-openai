package com.litongjava.openrouter;

/**
 * <PROVIDER>_<MODEL_NAME>[_FREE][_EXACTO][_THINKING]
 */
public interface OpenRouterModels {
  String AUTO = "auto";

  String DEEPSEEK_V3_250324 = "deepseek/deepseek-v3-250324";
  String DEEPSEEK_V3_250324_FREE = "deepseek/deepseek-chat-v3-0324:free";
  String DEEPSEEK_V3_241226 = "deepseek/deepseek-v3-241226";
  String DEEPSEEK_R1_250528 = "deepseek/deepseek-r1-0528";
  String DEEPSEEK_R1_250120 = "deepseek/deepseek-r1-250120";
  String KIMI_DEV_72B_FREE = "moonshotai/kimi-dev-72b:free";

  String GROK_CODE_FAST_1 = "x-ai/grok-code-fast-1";

  String GEMINI_2_5_FLASH_IMAGE_PREVIEW = "google/gemini-2.5-flash-image-preview";

  // ------------------ Alibaba / Qwen ------------------
  String QWEN_QWEN3_4B = "qwen/qwen3-4b";
  String QWEN_QWEN3_4B_FREE = "qwen/qwen3-4b:free";
  String QWEN_QWEN3_8B = "qwen/qwen3-8b";
  String QWEN_QWEN3_14B = "qwen/qwen3-14b";
  String QWEN_QWEN3_32B = "qwen/qwen3-32b";
  String QWEN_QWEN3_30B_A3B = "qwen/qwen3-30b-a3b";
  String QWEN_QWEN3_30B_A3B_INSTRUCT_2507 = "qwen/qwen3-30b-a3b-instruct-2507";
  String QWEN_QWEN3_30B_A3B_THINKING_2507 = "qwen/qwen3-30b-a3b-thinking-2507";
  String QWEN_QWEN3_235B_A22B = "qwen/qwen3-235b-a22b";
  String QWEN_QWEN3_235B_A22B_2507 = "qwen/qwen3-235b-a22b-2507";
  String QWEN_QWEN3_235B_A22B_INSTRUCT = "qwen/qwen3-235b-a22b-instruct";
  String QWEN_QWEN3_235B_A22B_INSTRUCT_FREE = "qwen/qwen3-235b-a22b-instruct:free";
  String QWEN_QWEN3_235B_A22B_THINKING = "qwen/qwen3-235b-a22b-thinking";
  String QWEN_QWEN3_NEXT_80B_A3B_INSTRUCT = "qwen/qwen3-next-80b-a3b-instruct";
  String QWEN_QWEN3_NEXT_80B_A3B_INSTRUCT_FREE = "qwen/qwen3-next-80b-a3b-instruct:free";
  String QWEN_QWEN3_NEXT_80B_A3B_THINKING = "qwen/qwen3-next-80b-a3b-thinking";
  String QWEN_QWEN3_VL_8B_INSTRUCT = "qwen/qwen3-vl-8b-instruct";
  String QWEN_QWEN3_VL_8B_THINKING = "qwen/qwen3-vl-8b-thinking";
  String QWEN_QWEN3_VL_32B_INSTRUCT = "qwen/qwen3-vl-32b-instruct";
  String QWEN_QWEN3_VL_30B_A3B_INSTRUCT = "qwen/qwen3-vl-30b-a3b-instruct";
  String QWEN_QWEN3_VL_30B_A3B_THINKING = "qwen/qwen3-vl-30b-a3b-thinking";
  String QWEN_QWEN3_CODER = "qwen/qwen3-coder";
  String QWEN_QWEN3_CODER_FREE = "qwen/qwen3-coder:free";
  String QWEN_QWEN3_CODER_EXACTO = "qwen/qwen3-coder:exacto";
  String QWEN_QWEN3_CODER_PLUS = "qwen/qwen3-coder-plus";
  String QWEN_QWEN3_CODER_FLASH = "qwen/qwen3-coder-flash";
  String QWEN_QWEN3_CODER_30B_A3B_INSTRUCT = "qwen/qwen3-coder-30b-a3b-instruct";
  String QWEN_QWEN2_5_CODER_7B_INSTRUCT = "qwen/qwen2.5-coder-7b-instruct";
  String QWEN_QWEN_2_5_CODER_32B_INSTRUCT = "qwen/qwen-2.5-coder-32b-instruct";
  String QWEN_QWEN2_5_VL_7B_INSTRUCT = "qwen/qwen-2.5-vl-7b-instruct";
  String QWEN_QWEN2_5_VL_7B_INSTRUCT_FREE = "qwen/qwen-2.5-vl-7b-instruct:free";
  String QWEN_QWEN2_5_VL_32B_INSTRUCT = "qwen/qwen2.5-vl-32b-instruct";
  String QWEN_QWEN2_5_VL_72B_INSTRUCT = "qwen/qwen2.5-vl-72b-instruct";
  String QWEN_QWEN2_5_7B_INSTRUCT = "qwen/qwen-2.5-7b-instruct";
  String QWEN_QWEN2_5_72B_INSTRUCT = "qwen/qwen-2.5-72b-instruct";
  String QWEN_QWQ_32B = "qwen/qwq-32b";
  String QWEN_QWEN_TURBO = "qwen/qwen-turbo";
  String QWEN_QWEN_PLUS = "qwen/qwen-plus";
  String QWEN_QWEN_PLUS_2025_07_28 = "qwen/qwen-plus-2025-07-28";
  String QWEN_QWEN_PLUS_2025_07_28_THINKING = "qwen/qwen-plus-2025-07-28:thinking";
  String QWEN_QWEN_MAX = "qwen/qwen-max";
  String QWEN_QWEN_VL_PLUS = "qwen/qwen-vl-plus";
  String QWEN_QWEN_VL_MAX = "qwen/qwen-vl-max";
  String QWEN_QWEN3_MAX = "qwen/qwen3-max";

  // ------------------ OpenAI ------------------
  String OPENAI_GPT_5 = "openai/gpt-5";
  String OPENAI_GPT_5_MINI = "openai/gpt-5-mini";
  String OPENAI_GPT_5_NANO = "openai/gpt-5-nano";
  String OPENAI_GPT_5_CHAT = "openai/gpt-5-chat";
  String OPENAI_GPT_5_PRO = "openai/gpt-5-pro";
  String OPENAI_GPT_5_CODEx = "openai/gpt-5-codex";
  String OPENAI_GPT_5_1 = "openai/gpt-5.1";
  String OPENAI_GPT_5_1_CHAT = "openai/gpt-5.1-chat";
  String OPENAI_GPT_5_1_CODEX = "openai/gpt-5.1-codex";
  String OPENAI_GPT_5_1_CODEX_MINI = "openai/gpt-5.1-codex-mini";
  String OPENAI_GPT_5_1_CODEX_MAX = "openai/gpt-5.1-codex-max";
  String OPENAI_GPT_5_2 = "openai/gpt-5.2";
  String OPENAI_GPT_5_2_CHAT = "openai/gpt-5.2-chat";
  String OPENAI_GPT_5_2_PRO = "openai/gpt-5.2-pro";
  String OPENAI_GPT_5_2_CODEX = "openai/gpt-5.2-codex";
  String OPENAI_GPT_4O = "openai/gpt-4o";
  String OPENAI_GPT_4O_EXTENDED = "openai/gpt-4o:extended";
  String OPENAI_GPT_4O_2024_05_13 = "openai/gpt-4o-2024-05-13";
  String OPENAI_GPT_4O_2024_08_06 = "openai/gpt-4o-2024-08-06";
  String OPENAI_GPT_4O_2024_11_20 = "openai/gpt-4o-2024-11-20";
  String OPENAI_GPT_4O_MINI = "openai/gpt-4o-mini";
  String OPENAI_GPT_4O_MINI_2024_07_18 = "openai/gpt-4o-mini-2024-07-18";
  String OPENAI_GPT_4O_AUDIO_PREVIEW = "openai/gpt-4o-audio-preview";
  String OPENAI_GPT_4O_MINI_SEARCH_PREVIEW = "openai/gpt-4o-mini-search-preview";
  String OPENAI_GPT_4O_SEARCH_PREVIEW = "openai/gpt-4o-search-preview";
  String OPENAI_GPT_4_TURBO = "openai/gpt-4-turbo";
  String OPENAI_GPT_4_TURBO_PREVIEW = "openai/gpt-4-turbo-preview";
  String OPENAI_GPT_4_1106_PREVIEW = "openai/gpt-4-1106-preview";
  String OPENAI_GPT_4_0314 = "openai/gpt-4-0314";
  String OPENAI_GPT_4 = "openai/gpt-4";
  String OPENAI_GPT_3_5_TURBO = "openai/gpt-3.5-turbo";
  String OPENAI_GPT_3_5_TURBO_16K = "openai/gpt-3.5-turbo-16k";
  String OPENAI_GPT_3_5_TURBO_0613 = "openai/gpt-3.5-turbo-0613";
  String OPENAI_GPT_3_5_TURBO_INSTRUCT = "openai/gpt-3.5-turbo-instruct";
  String OPENAI_CHATGPT_4O_LATEST = "openai/chatgpt-4o-latest";
  String OPENAI_GPT_OSS_20B = "openai/gpt-oss-20b";
  String OPENAI_GPT_OSS_20B_FREE = "openai/gpt-oss-20b:free";
  String OPENAI_GPT_OSS_120B = "openai/gpt-oss-120b";
  String OPENAI_GPT_OSS_120B_FREE = "openai/gpt-oss-120b:free";
  String OPENAI_GPT_OSS_120B_EXACTO = "openai/gpt-oss-120b:exacto";
  String OPENAI_GPT_OSS_SAFEGUARD_20B = "openai/gpt-oss-safeguard-20b";
  String OPENAI_O1 = "openai/o1";
  String OPENAI_O1_PRO = "openai/o1-pro";
  String OPENAI_O3 = "openai/o3";
  String OPENAI_O3_PRO = "openai/o3-pro";
  String OPENAI_O3_MINI = "openai/o3-mini";
  String OPENAI_O3_MINI_HIGH = "openai/o3-mini-high";
  String OPENAI_O4_MINI = "openai/o4-mini";
  String OPENAI_O4_MINI_HIGH = "openai/o4-mini-high";
  String OPENAI_O3_DEEP_RESEARCH = "openai/o3-deep-research";
  String OPENAI_O4_MINI_DEEP_RESEARCH = "openai/o4-mini-deep-research";
  String OPENAI_GPT_AUDIO = "openai/gpt-audio";
  String OPENAI_GPT_AUDIO_MINI = "openai/gpt-audio-mini";
  String OPENAI_GPT_5_IMAGE = "openai/gpt-5-image";
  String OPENAI_GPT_5_IMAGE_MINI = "openai/gpt-5-image-mini";

  // ------------------ Anthropic ------------------
  String ANTHROPIC_CLAUDE_OPUS_4 = "anthropic/claude-opus-4";
  String ANTHROPIC_CLAUDE_OPUS_4_1 = "anthropic/claude-opus-4.1";
  String ANTHROPIC_CLAUDE_OPUS_4_5 = "anthropic/claude-opus-4.5";
  String ANTHROPIC_CLAUDE_SONNET_4 = "anthropic/claude-sonnet-4";
  String ANTHROPIC_CLAUDE_SONNET_4_5 = "anthropic/claude-sonnet-4.5";
  String ANTHROPIC_CLAUDE_HAIKU_4_5 = "anthropic/claude-haiku-4.5";
  String ANTHROPIC_CLAUDE_3_5_SONNET = "anthropic/claude-3.5-sonnet";
  String ANTHROPIC_CLAUDE_3_5_HAIKU = "anthropic/claude-3.5-haiku";
  String ANTHROPIC_CLAUDE_3_7_SONNET = "anthropic/claude-3.7-sonnet";
  String ANTHROPIC_CLAUDE_3_7_SONNET_THINKING = "anthropic/claude-3.7-sonnet:thinking";
  String ANTHROPIC_CLAUDE_3_HAIKU = "anthropic/claude-3-haiku";

  // ------------------ Google / Gemini ------------------
  String GOOGLE_GEMINI_2_0_FLASH_001 = "google/gemini-2.0-flash-001";
  String GOOGLE_GEMINI_2_0_FLASH_LITE_001 = "google/gemini-2.0-flash-lite-001";
  String GOOGLE_GEMINI_2_0_FLASH_EXP = "google/gemini-2.0-flash-exp";
  String GOOGLE_GEMINI_2_0_FLASH_EXP_FREE = "google/gemini-2.0-flash-exp:free";
  String GOOGLE_GEMINI_2_5_FLASH = "google/gemini-2.5-flash";
  String GOOGLE_GEMINI_2_5_FLASH_LITE = "google/gemini-2.5-flash-lite";
  String GOOGLE_GEMINI_2_5_FLASH_LITE_PREVIEW_09_2025 = "google/gemini-2.5-flash-lite-preview-09-2025";
  String GOOGLE_GEMINI_2_5_FLASH_PREVIEW = "google/gemini-2.5-flash-preview";
  String GOOGLE_GEMINI_2_5_FLASH_PREVIEW_09_2025 = "google/gemini-2.5-flash-preview-09-2025";
  String GOOGLE_GEMINI_2_5_PRO = "google/gemini-2.5-pro";
  String GOOGLE_GEMINI_2_5_PRO_PREVIEW = "google/gemini-2.5-pro-preview";
  String GOOGLE_GEMINI_2_5_PRO_PREVIEW_05_06 = "google/gemini-2.5-pro-preview-05-06";
  String GOOGLE_GEMINI_3_FLASH_PREVIEW = "google/gemini-3-flash-preview";
  String GOOGLE_GEMINI_3_PRO_PREVIEW = "google/gemini-3-pro-preview";
  String GOOGLE_GEMINI_3_PRO_IMAGE_PREVIEW = "google/gemini-3-pro-image-preview";
  String GOOGLE_GEMINI_2_5_FLASH_IMAGE = "google/gemini-2.5-flash-image";
  String GOOGLE_GEMINI_2_5_FLASH_IMAGE_PREVIEW = "google/gemini-2.5-flash-image-preview";

  // ------------------ Mistral AI ------------------
  String MISTRALAI_MISTRAL_TINY = "mistralai/mistral-tiny";
  String MISTRALAI_MISTRAL_SMALL = "mistralai/mistral-small";
  String MISTRALAI_MISTRAL_SMALL_24B_INSTRUCT_2501 = "mistralai/mistral-small-24b-instruct-2501";
  String MISTRALAI_MISTRAL_SMALL_3_1_24B_INSTRUCT = "mistralai/mistral-small-3.1-24b-instruct";
  String MISTRALAI_MISTRAL_SMALL_3_1_24B_INSTRUCT_FREE = "mistralai/mistral-small-3.1-24b-instruct:free";
  String MISTRALAI_MISTRAL_SMALL_3_2_24B_INSTRUCT = "mistralai/mistral-small-3.2-24b-instruct";
  String MISTRALAI_MISTRAL_SMALL_CREATIVE = "mistralai/mistral-small-creative";
  String MISTRALAI_MISTRAL_MEDIUM = "mistralai/mistral-medium";
  String MISTRALAI_MISTRAL_MEDIUM_3 = "mistralai/mistral-medium-3";
  String MISTRALAI_MISTRAL_MEDIUM_3_1 = "mistralai/mistral-medium-3.1";
  String MISTRALAI_MISTRAL_LARGE = "mistralai/mistral-large";
  String MISTRALAI_MISTRAL_LARGE_2407 = "mistralai/mistral-large-2407";
  String MISTRALAI_MISTRAL_LARGE_2411 = "mistralai/mistral-large-2411";
  String MISTRALAI_MISTRAL_LARGE_2512 = "mistralai/mistral-large-2512";
  String MISTRALAI_MISTRAL_7B_INSTRUCT = "mistralai/mistral-7b-instruct";
  String MISTRALAI_MISTRAL_7B_INSTRUCT_V0_1 = "mistralai/mistral-7b-instruct-v0.1";
  String MISTRALAI_MISTRAL_7B_INSTRUCT_V0_2 = "mistralai/mistral-7b-instruct-v0.2";
  String MISTRALAI_MISTRAL_7B_INSTRUCT_V0_3 = "mistralai/mistral-7b-instruct-v0.3";
  String MISTRALAI_MIXTRAL_8X7B_INSTRUCT = "mistralai/mixtral-8x7b-instruct";
  String MISTRALAI_MIXTRAL_8X22B_INSTRUCT = "mistralai/mixtral-8x22b-instruct";
  String MISTRALAI_MISTRAL_NEMO = "mistralai/mistral-nemo";
  String MISTRALAI_MINISTRAL_3B = "mistralai/ministral-3b";
  String MISTRALAI_MINISTRAL_3B_2512 = "mistralai/ministral-3b-2512";
  String MISTRALAI_MINISTRAL_8B = "mistralai/ministral-8b";
  String MISTRALAI_MINISTRAL_8B_2512 = "mistralai/ministral-8b-2512";
  String MISTRALAI_MINISTRAL_14B_2512 = "mistralai/ministral-14b-2512";
  String MISTRALAI_DEVSTRAL_SMALL = "mistralai/devstral-small";
  String MISTRALAI_DEVSTRAL_MEDIUM = "mistralai/devstral-medium";
  String MISTRALAI_DEVSTRAL_2512 = "mistralai/devstral-2512";
  String MISTRALAI_DEVSTRAL_2512_FREE = "mistralai/devstral-2512:free";
  String MISTRALAI_PIXTRAL_12B = "mistralai/pixtral-12b";
  String MISTRALAI_PIXTRAL_LARGE_2411 = "mistralai/pixtral-large-2411";
  String MISTRALAI_VOXTAL_SMALL_24B_2507 = "mistralai/voxtral-small-24b-2507";
  String MISTRALAI_CODestRAL_2508 = "mistralai/codestral-2508";
  String MISTRALAI_MISTRAL_SABA = "mistralai/mistral-saba";

  // ------------------ Z-AI / GLM ------------------
  String Z_AI_GLM_4_5 = "z-ai/glm-4.5";
  String Z_AI_GLM_4_5_AIR = "z-ai/glm-4.5-air";
  String Z_AI_GLM_4_5_AIR_FREE = "z-ai/glm-4.5-air:free";
  String Z_AI_GLM_4_5V = "z-ai/glm-4.5v";
  String Z_AI_GLM_4_6 = "z-ai/glm-4.6";
  String Z_AI_GLM_4_6_EXACTO = "z-ai/glm-4.6:exacto";
  String Z_AI_GLM_4_6V = "z-ai/glm-4.6v";
  String Z_AI_GLM_4_7 = "z-ai/glm-4.7";
  String Z_AI_GLM_4_7_FLASH = "z-ai/glm-4.7-flash";
  String Z_AI_GLM_4_32B = "z-ai/glm-4-32b";

  // ------------------ DeepSeek ------------------
  String DEEPSEEK_DEEPSEEK_V3_2 = "deepseek/deepseek-v3.2";
  String DEEPSEEK_DEEPSEEK_V3_2_SPECIALE = "deepseek/deepseek-v3.2-speciale";
  String DEEPSEEK_DEEPSEEK_V3_2_EXP = "deepseek/deepseek-v3.2-exp";
  String DEEPSEEK_DEEPSEEK_V3_1 = "deepseek/deepseek-v3.1";
  String DEEPSEEK_DEEPSEEK_V3_1_TERMINUS = "deepseek/deepseek-v3.1-terminus";
  String DEEPSEEK_DEEPSEEK_V3_1_TERMINUS_EXACTO = "deepseek/deepseek-v3.1-terminus:exacto";
  String DEEPSEEK_DEEPSEEK_CHAT_V3_1 = "deepseek/deepseek-chat-v3.1";
  String DEEPSEEK_DEEPSEEK_CHAT_V3_250324 = "deepseek/deepseek-v3-250324";
  String DEEPSEEK_DEEPSEEK_CHAT_V3_0324 = "deepseek/deepseek-chat-v3-0324";
  String DEEPSEEK_DEEPSEEK_CHAT_V3_0324_FREE = "deepseek/deepseek-chat-v3-0324:free";
  String DEEPSEEK_DEEPSEEK_V3_241226 = "deepseek/deepseek-v3-241226";
  String DEEPSEEK_DEEPSEEK_R1 = "deepseek/deepseek-r1";
  String DEEPSEEK_DEEPSEEK_R1_0528 = "deepseek/deepseek-r1-0528";
  String DEEPSEEK_DEEPSEEK_R1_0528_FREE = "deepseek/deepseek-r1-0528:free";
  String DEEPSEEK_DEEPSEEK_R1_250120 = "deepseek/deepseek-r1-250120";
  String DEEPSEEK_DEEPSEEK_R1_DISTILL_LLAMA_70B = "deepseek/deepseek-r1-distill-llama-70b";
  String DEEPSEEK_DEEPSEEK_R1_DISTILL_QWEN_32B = "deepseek/deepseek-r1-distill-qwen-32b";
  String DEEPSEEK_DEEPSEEK_R1T_CHIMERA = "tngtech/deepseek-r1t-chimera";
  String DEEPSEEK_DEEPSEEK_R1T_CHIMERA_FREE = "tngtech/deepseek-r1t-chimera:free";
  String DEEPSEEK_DEEPSEEK_R1T2_CHIMERA = "tngtech/deepseek-r1t2-chimera";
  String DEEPSEEK_DEEPSEEK_R1T2_CHIMERA_FREE = "tngtech/deepseek-r1t2-chimera:free";

  // ------------------ Moonshot AI / Kimi ------------------
  String MOONSHOTAI_KIMI_K2 = "moonshotai/kimi-k2";
  String MOONSHOTAI_KIMI_K2_FREE = "moonshotai/kimi-k2:free";
  String MOONSHOTAI_KIMI_K2_0905 = "moonshotai/kimi-k2-0905";
  String MOONSHOTAI_KIMI_K2_0905_EXACTO = "moonshotai/kimi-k2-0905:exacto";
  String MOONSHOTAI_KIMI_K2_THINKING = "moonshotai/kimi-k2-thinking";
  String MOONSHOTAI_KIMI_DEV_72B = "moonshotai/kimi-dev-72b";
  String MOONSHOTAI_KIMI_DEV_72B_FREE = "moonshotai/kimi-dev-72b:free";

  // ------------------ NVIDIA ------------------
  String NVIDIA_NEMOTRON_NANO_9B_V2 = "nvidia/nemotron-nano-9b-v2";
  String NVIDIA_NEMOTRON_NANO_9B_V2_FREE = "nvidia/nemotron-nano-9b-v2:free";
  String NVIDIA_NEMOTRON_NANO_12B_V2_VL = "nvidia/nemotron-nano-12b-v2-vl";
  String NVIDIA_NEMOTRON_NANO_12B_V2_VL_FREE = "nvidia/nemotron-nano-12b-v2-vl:free";
  String NVIDIA_NEMOTRON_3_NANO_30B_A3B = "nvidia/nemotron-3-nano-30b-a3b";
  String NVIDIA_NEMOTRON_3_NANO_30B_A3B_FREE = "nvidia/nemotron-3-nano-30b-a3b:free";
  String NVIDIA_LLAMA_3_1_NEMOTRON_70B_INSTRUCT = "nvidia/llama-3.1-nemotron-70b-instruct";
  String NVIDIA_LLAMA_3_3_NEMOTRON_SUPER_49B_V1_5 = "nvidia/llama-3.3-nemotron-super-49b-v1.5";

  // ------------------ Meta / Llama ------------------
  String META_LLAMA_LLAMA_3_1_8B_INSTRUCT = "meta-llama/llama-3.1-8b-instruct";
  String META_LLAMA_LLAMA_3_1_70B_INSTRUCT = "meta-llama/llama-3.1-70b-instruct";
  String META_LLAMA_LLAMA_3_1_405B = "meta-llama/llama-3.1-405b";
  String META_LLAMA_LLAMA_3_1_405B_INSTRUCT = "meta-llama/llama-3.1-405b-instruct";
  String META_LLAMA_LLAMA_3_1_405B_INSTRUCT_FREE = "meta-llama/llama-3.1-405b-instruct:free";
  String META_LLAMA_LLAMA_3_2_1B_INSTRUCT = "meta-llama/llama-3.2-1b-instruct";
  String META_LLAMA_LLAMA_3_2_3B_INSTRUCT = "meta-llama/llama-3.2-3b-instruct";
  String META_LLAMA_LLAMA_3_2_3B_INSTRUCT_FREE = "meta-llama/llama-3.2-3b-instruct:free";
  String META_LLAMA_LLAMA_3_2_11B_VISION_INSTRUCT = "meta-llama/llama-3.2-11b-vision-instruct";
  String META_LLAMA_LLAMA_3_3_70B_INSTRUCT = "meta-llama/llama-3.3-70b-instruct";
  String META_LLAMA_LLAMA_3_3_70B_INSTRUCT_FREE = "meta-llama/llama-3.3-70b-instruct:free";
  String META_LLAMA_LLAMA_3_8B_INSTRUCT = "meta-llama/llama-3-8b-instruct";
  String META_LLAMA_LLAMA_3_70B_INSTRUCT = "meta-llama/llama-3-70b-instruct";
  String META_LLAMA_LLAMA_4_SCOUT = "meta-llama/llama-4-scout";
  String META_LLAMA_LLAMA_4_MAVERICK = "meta-llama/llama-4-maverick";
  String META_LLAMA_LLAMA_GUARD_2_8B = "meta-llama/llama-guard-2-8b";
  String META_LLAMA_LLAMA_GUARD_3_8B = "meta-llama/llama-guard-3-8b";
  String META_LLAMA_LLAMA_GUARD_4_12B = "meta-llama/llama-guard-4-12b";

  // ------------------ AllenAI ------------------
  String ALLENAI_OLMO_3_7B_INSTRUCT = "allenai/olmo-3-7b-instruct";
  String ALLENAI_OLMO_3_7B_THINK = "allenai/olmo-3-7b-think";
  String ALLENAI_OLMO_3_32B_THINK = "allenai/olmo-3-32b-think";
  String ALLENAI_OLMO_3_1_32B_INSTRUCT = "allenai/olmo-3.1-32b-instruct";
  String ALLENAI_OLMO_3_1_32B_THINK = "allenai/olmo-3.1-32b-think";
  String ALLENAI_OLMO_2_0325_32B_INSTRUCT = "allenai/olmo-2-0325-32b-instruct";
  String ALLENAI_MOLMO_2_8B = "allenai/molmo-2-8b";
  String ALLENAI_MOLMO_2_8B_FREE = "allenai/molmo-2-8b:free";

  // ------------------ xAI / Grok ------------------
  String X_AI_GROK_3 = "x-ai/grok-3";
  String X_AI_GROK_3_BETA = "x-ai/grok-3-beta";
  String X_AI_GROK_3_MINI = "x-ai/grok-3-mini";
  String X_AI_GROK_3_MINI_BETA = "x-ai/grok-3-mini-beta";
  String X_AI_GROK_4 = "x-ai/grok-4";
  String X_AI_GROK_4_FAST = "x-ai/grok-4-fast";
  String X_AI_GROK_4_1_FAST = "x-ai/grok-4.1-fast";
  String X_AI_GROK_CODE_FAST_1 = "x-ai/grok-code-fast-1";

  // ------------------ Baidu / ERNIE ------------------
  String BAIDU_ERNIE_4_5_21B_A3B = "baidu/ernie-4.5-21b-a3b";
  String BAIDU_ERNIE_4_5_21B_A3B_THINKING = "baidu/ernie-4.5-21b-a3b-thinking";
  String BAIDU_ERNIE_4_5_300B_A47B = "baidu/ernie-4.5-300b-a47b";
  String BAIDU_ERNIE_4_5_VL_28B_A3B = "baidu/ernie-4.5-vl-28b-a3b";
  String BAIDU_ERNIE_4_5_VL_424B_A47B = "baidu/ernie-4.5-vl-424b-a47b";

  // ------------------ Perplexity / Sonar ------------------
  String PERPLEXITY_SONAR = "perplexity/sonar";
  String PERPLEXITY_SONAR_PRO = "perplexity/sonar-pro";
  String PERPLEXITY_SONAR_PRO_SEARCH = "perplexity/sonar-pro-search";
  String PERPLEXITY_SONAR_REASONING_PRO = "perplexity/sonar-reasoning-pro";
  String PERPLEXITY_SONAR_DEEP_RESEARCH = "perplexity/sonar-deep-research";

  // ------------------ Cohere ------------------
  String COHERE_COMMAND_R_08_2024 = "cohere/command-r-08-2024";
  String COHERE_COMMAND_R_PLUS_08_2024 = "cohere/command-r-plus-08-2024";
  String COHERE_COMMAND_R7B_12_2024 = "cohere/command-r7b-12-2024";
  String COHERE_COMMAND_A = "cohere/command-a";

  // ------------------ Amazon / Nova ------------------
  String AMAZON_NOVA_MICRO_V1 = "amazon/nova-micro-v1";
  String AMAZON_NOVA_LITE_V1 = "amazon/nova-lite-v1";
  String AMAZON_NOVA_2_LITE_V1 = "amazon/nova-2-lite-v1";
  String AMAZON_NOVA_PRO_V1 = "amazon/nova-pro-v1";
  String AMAZON_NOVA_PREMIER_V1 = "amazon/nova-premier-v1";

  // ------------------ Microsoft ------------------
  String MICROSOFT_PHI_4 = "microsoft/phi-4";
  String MICROSOFT_WIZARDLM_2_8X22B = "microsoft/wizardlm-2-8x22b";

  // ------------------ IBM ------------------
  String IBM_GRANITE_GRANITE_4_0_H_MICRO = "ibm-granite/granite-4.0-h-micro";

  // ------------------ Tencent / HunYuan ------------------
  String TENCENT_HUNYUAN_A13B_INSTRUCT = "tencent/hunyuan-a13b-instruct";

  // ------------------ Bytedance / Seed / UI-Tars ------------------
  String BYTEDANCE_SEED_SEED_1_6 = "bytedance-seed/seed-1.6";
  String BYTEDANCE_SEED_SEED_1_6_FLASH = "bytedance-seed/seed-1.6-flash";
  String BYTEDANCE_UI_TARS_1_5_7B = "bytedance/ui-tars-1.5-7b";

  // ------------------ Liquid ------------------
  String LIQUID_LFM_2_2_6B = "liquid/lfm-2.2-6b";
  String LIQUID_LFM_2_5_1_2B_INSTRUCT = "liquid/lfm-2.5-1.2b-instruct";
  String LIQUID_LFM_2_5_1_2B_INSTRUCT_FREE = "liquid/lfm-2.5-1.2b-instruct:free";
  String LIQUID_LFM_2_5_1_2B_THINKING = "liquid/lfm-2.5-1.2b-thinking";
  String LIQUID_LFM_2_5_1_2B_THINKING_FREE = "liquid/lfm-2.5-1.2b-thinking:free";
  String LIQUID_LFM2_8B_A1B = "liquid/lfm2-8b-a1b";

  // ------------------ Writer / Palmyra ------------------
  String WRITER_PALMYRA_X5 = "writer/palmyra-x5";

  // ------------------ Nous Research ------------------
  String NOUSRESEARCH_HERMES_2_PRO_LLAMA_3_8B = "nousresearch/hermes-2-pro-llama-3-8b";
  String NOUSRESEARCH_HERMES_3_LLAMA_3_1_70B = "nousresearch/hermes-3-llama-3.1-70b";
  String NOUSRESEARCH_HERMES_3_LLAMA_3_1_405B = "nousresearch/hermes-3-llama-3.1-405b";
  String NOUSRESEARCH_HERMES_3_LLAMA_3_1_405B_FREE = "nousresearch/hermes-3-llama-3.1-405b:free";
  String NOUSRESEARCH_HERMES_4_70B = "nousresearch/hermes-4-70b";
  String NOUSRESEARCH_HERMES_4_405B = "nousresearch/hermes-4-405b";
  String NOUSRESEARCH_DEEPHERMES_3_MISTRAL_24B_PREVIEW = "nousresearch/deephermes-3-mistral-24b-preview";

  // ------------------ DeepCogito ------------------
  String DEEPCOGITO_COGITO_V2_1_671B = "deepcogito/cogito-v2.1-671b";
  String DEEPCOGITO_COGITO_V2_PREVIEW_LLAMA_70B = "deepcogito/cogito-v2-preview-llama-70b";
  String DEEPCOGITO_COGITO_V2_PREVIEW_LLAMA_109B_MOE = "deepcogito/cogito-v2-preview-llama-109b-moe";
  String DEEPCOGITO_COGITO_V2_PREVIEW_LLAMA_405B = "deepcogito/cogito-v2-preview-llama-405b";

  // ------------------ Arcee AI ------------------
  String ARCEE_AI_TRINITY_MINI = "arcee-ai/trinity-mini";
  String ARCEE_AI_TRINITY_MINI_FREE = "arcee-ai/trinity-mini:free";
  String ARCEE_AI_SPOTLIGHT = "arcee-ai/spotlight";
  String ARCEE_AI_MAESTRO_REASONING = "arcee-ai/maestro-reasoning";
  String ARCEE_AI_VIRTUOSO_LARGE = "arcee-ai/virtuoso-large";
  String ARCEE_AI_CODER_LARGE = "arcee-ai/coder-large";

  // ------------------ Inception ------------------
  String INCEPTION_MERCURY = "inception/mercury";
  String INCEPTION_MERCURY_CODER = "inception/mercury-coder";

  // ------------------ Essential AI ------------------
  String ESSENTIALAI_RNJ_1_INSTRUCT = "essentialai/rnj-1-instruct";

  // ------------------ TheDrummer ------------------
  String THEDRUMMER_SKYFALL_36B_V2 = "thedrummer/skyfall-36b-v2";
  String THEDRUMMER_ROCINANTE_12B = "thedrummer/rocinante-12b";
  String THEDRUMMER_CYDONIA_24B_V4_1 = "thedrummer/cydonia-24b-v4.1";
  String THEDRUMMER_UNSLOPNEMO_12B = "thedrummer/unslopnemo-12b";

  // ------------------ Relace ------------------
  String RELACE_RELACE_APPLY_3 = "relace/relace-apply-3";
  String RELACE_RELACE_SEARCH = "relace/relace-search";

  // ------------------ Aion Labs ------------------
  String AION_LABS_AION_1_0 = "aion-labs/aion-1.0";
  String AION_LABS_AION_1_0_MINI = "aion-labs/aion-1.0-mini";
  String AION_LABS_AION_RP_LLAMA_3_1_8B = "aion-labs/aion-rp-llama-3.1-8b";

  // ------------------ Prime Intellect ------------------
  String PRIME_INTELLECT_INTELLECT_3 = "prime-intellect/intellect-3";

  // ------------------ Inflection ------------------
  String INFLECTION_INFLECTION_3_PI = "inflection/inflection-3-pi";
  String INFLECTION_INFLECTION_3_PRODUCTIVITY = "inflection/inflection-3-productivity";

  // ------------------ Neversleep ------------------
  String NEVERSLEEP_LLAMA_3_1_LUMIMAID_8B = "neversleep/llama-3.1-lumimaid-8b";
  String NEVERSLEEP_NOROMAID_20B = "neversleep/noromaid-20b";

  // ------------------ Alpindale ------------------
  String ALPINDALE_GOLIATH_120B = "alpindale/goliath-120b";

  // ------------------ EleutherAI ------------------
  String ELEUTHERAI_LEMMA_7B = "eleutherai/llemma_7b";

  // ------------------ AlfredPros ------------------
  String ALFREDPROS_CODELLAMA_7B_INSTRUCT_SOLIDITY = "alfredpros/codellama-7b-instruct-solidity";

  // ------------------ Meituan ------------------
  String MEITUAN_LONGCAT_FLASH_CHAT = "meituan/longcat-flash-chat";

  // ------------------ OpenGVLab ------------------
  String OPENGVLAB_INTERNVL3_78B = "opengvlab/internvl3-78b";

  // ------------------ StepFun AI ------------------
  String STEPFUN_AI_STEP3 = "stepfun-ai/step3";

  // ------------------ Raifle ------------------
  String RAIFLE_SORCERERLM_8X22B = "raifle/sorcererlm-8x22b";

  // ------------------ Sao10k ------------------
  String SAO10K_L3_1_70B_HANAMI_X1 = "sao10k/l3.1-70b-hanami-x1";
  String SAO10K_L3_1_EURYALE_70B = "sao10k/l3.1-euryale-70b";
  String SAO10K_L3_EURYALE_70B = "sao10k/l3-euryale-70b";
  String SAO10K_L3_3_EURYALE_70B = "sao10k/l3.3-euryale-70b";
  String SAO10K_L3_LUNARIS_8B = "sao10k/l3-lunaris-8b";

  // ------------------ Kwaipilot ------------------
  String KWAIPILLOT_KAT_CODER_PRO = "kwaipilot/kat-coder-pro";

  // ------------------ Morph ------------------
  String MORPH_MORPH_V3_LARGE = "morph/morph-v3-large";
  String MORPH_MORPH_V3_FAST = "morph/morph-v3-fast";

  // ------------------ Switchpoint ------------------
  String SWITCHPOINT_ROUTER = "switchpoint/router";

  // ------------------ OpenRouter ------------------
  String OPENROUTER_AUTO = "openrouter/auto";
  String OPENROUTER_BODYBUILDER = "openrouter/bodybuilder";

  // ------------------ Cognitive Computations ------------------
  String COGNITIVECOMPUTATIONS_DOLPHIN_MISTRAL_24B_VENICE_EDITION = "cognitivecomputations/dolphin-mistral-24b-venice-edition";
  String COGNITIVECOMPUTATIONS_DOLPHIN_MISTRAL_24B_VENICE_EDITION_FREE = "cognitivecomputations/dolphin-mistral-24b-venice-edition:free";

  // ------------------ Undi95 ------------------
  String UNDI95_REMM_SLERP_L2_13B = "undi95/remm-slerp-l2-13b";

  // ------------------ Gryphe ------------------
  String GRYPHE_MYTHOMAX_L2_13B = "gryphe/mythomax-l2-13b";

  // ------------------ Mancer ------------------
  String MANCER_WEAVER = "mancer/weaver";

  // ------------------ Google Gemma ------------------
  String GOOGLE_GEMMA_2_9B_IT = "google/gemma-2-9b-it";
  String GOOGLE_GEMMA_2_27B_IT = "google/gemma-2-27b-it";
  String GOOGLE_GEMMA_3_4B_IT = "google/gemma-3-4b-it";
  String GOOGLE_GEMMA_3_4B_IT_FREE = "google/gemma-3-4b-it:free";
  String GOOGLE_GEMMA_3_12B_IT = "google/gemma-3-12b-it";
  String GOOGLE_GEMMA_3_12B_IT_FREE = "google/gemma-3-12b-it:free";
  String GOOGLE_GEMMA_3_27B_IT = "google/gemma-3-27b-it";
  String GOOGLE_GEMMA_3_27B_IT_FREE = "google/gemma-3-27b-it:free";
  String GOOGLE_GEMMA_3N_E2B_IT = "google/gemma-3n-e2b-it";
  String GOOGLE_GEMMA_3N_E2B_IT_FREE = "google/gemma-3n-e2b-it:free";
  String GOOGLE_GEMMA_3N_E4B_IT = "google/gemma-3n-e4b-it";
  String GOOGLE_GEMMA_3N_E4B_IT_FREE = "google/gemma-3n-e4b-it:free";

  String XIAOMI_MIMO_V2_FLASH_FREE = "xiaomi/mimo-v2-flash:free";
  String XIAOMI_MIMO_V2_FLASH = "xiaomi/mimo-v2-flash";

}