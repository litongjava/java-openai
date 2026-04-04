package nexus.io.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.tio.utils.environment.EnvUtils;

import nexus.io.aiapi.AiApiConst;
import nexus.io.bailian.BaiLianConst;
import nexus.io.cerebras.CerebrasConst;
import nexus.io.claude.ClaudeClient;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exchangetoken.ExchangetokenConst;
import nexus.io.gemini.GeminiClient;
import nexus.io.gitee.GiteeConst;
import nexus.io.minimax.MiniMaxConst;
import nexus.io.moonshot.MoonshotConst;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.openai.consts.OpenAiConst;
import nexus.io.openai.embedding.EmbeddingResponse;
import nexus.io.openrouter.OpenRouterConst;
import nexus.io.tencent.TencentConst;
import nexus.io.vertexai.VertexAiConsts;
import nexus.io.volcengine.VolcEngineConst;
import nexus.io.zenmux.ZenmuxConst;

public class UniEmbeddingClient {
  private static final Logger log = LoggerFactory.getLogger(UniEmbeddingClient.class);

  public static final String GEMINI_API_URL = GeminiClient.GEMINI_API_URL;
  public static final String GEMINI_API_KEY = GeminiClient.GEMINI_API_KEY;

  public static final String VERTEX_AI_API_KEY = EnvUtils.get(VertexAiConsts.VERTEX_AI_API_KEY_NAME);
  public static final String VERTEX_AI_API_URL = EnvUtils.get(VertexAiConsts.VERTEX_AI_API_URL_NAME,
      VertexAiConsts.API_MODEL_BASE);

  public static final String CLAUDE_API_URL = ClaudeClient.CLAUDE_API_KEY;
  public static final String CLAUDE_API_KEY = ClaudeClient.CLAUDE_API_KEY;

  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
  public static final String OPENAI_API_KEY = EnvUtils.get("OPENAI_API_KEY");

  public static final String VOLCENGINE_API_URL = EnvUtils.get("VOLCENGINE_API_URL", VolcEngineConst.API_PREFIX_URL);
  public static final String VOLCENGINE_API_KEY = EnvUtils.get("VOLCENGINE_API_KEY");

  public static final String OPENROUTER_API_URL = EnvUtils.get("OPENROUTER_API_URL", OpenRouterConst.API_PREFIX_URL);
  public static final String OPENROUTER_API_KEY = EnvUtils.get("OPENROUTER_API_KEY");

  public static final String ZENMUX_API_URL = EnvUtils.get("ZENMUX_API_URL", ZenmuxConst.API_PREFIX_URL);
  public static final String ZENMUX_API_KEY = EnvUtils.get("ZENMUX_API_KEY");

  public static final String BAILIAN_API_URL = EnvUtils.get("BAILIAN_API_URL", BaiLianConst.API_PERFIX_URL);
  public static final String BAILIAN_API_KEY = EnvUtils.get("BAILIAN_API_KEY");

  public static final String TENCENT_API_URL = EnvUtils.get("TENCENT_API_URL", TencentConst.API_PERFIX_URL);
  public static final String TENCENT_API_KEY = EnvUtils.get("TENCENT_API_KEY");

  public static final String MOONSHOT_API_URL = EnvUtils.get("MOONSHOT_API_URL", MoonshotConst.API_PERFIX_URL);
  public static final String MOONSHOT_API_KEY = EnvUtils.get("MOONSHOT_API_KEY");

  public static final String MINIMAX_API_URL = EnvUtils.get("MINIMAX_API_URL", MiniMaxConst.API_PREFIX_URL);
  public static final String MINIMAX_API_KEY = EnvUtils.get("MINIMAX_API_KEY");

  public static final String CEREBRAS_API_URL = EnvUtils.get("CEREBRAS_API_URL", CerebrasConst.API_PREFIX_URL);
  public static final String CEREBRAS_API_KEY = EnvUtils.get("CEREBRAS_API_KEY");

  public static final String GITEE_API_URL = EnvUtils.get("GITEE_API_URL", GiteeConst.API_PREFIX_URL);
  public static final String GITEE_API_KEY = EnvUtils.get("GITEE_API_KEY");

  public static final String OLLAMA_API_URL = EnvUtils.get("OLLAMA_API_URL");
  public static final String OLLAMA_API_KEY = EnvUtils.get("OLLAMA_API_KEY");

  public static final String LLAMACPP_API_URL = EnvUtils.get("LLAMACPP_API_URL");
  public static final String LLAMACPP_API_KEY = EnvUtils.get("LLAMACPP_API_KEY");

  public static final String VLLM_API_URL = EnvUtils.get("VLLM_API_URL");
  public static final String VLLM_API_KEY = EnvUtils.get("VLLM_API_KEY");

  public static final String SWIFT_API_URL = EnvUtils.get("SWIFT_API_URL");
  public static final String SWIFT_API_KEY = EnvUtils.get("SWIFT_API_KEY");

  public static final String TITANIUM_API_KEY = EnvUtils.get("TITANIUM_API_KEY");
  public static final String TITANIUM_API_URL = EnvUtils.get("TITANIUM_API_URL");

  public static final String EXCHANGE_TOKEN_API_KEY = EnvUtils.get("EXCHANGE_TOKEN_API_KEY");
  public static final String EXCHANGE_TOKEN_API_URL = EnvUtils.get("EXCHANGE_TOKEN_API_URL",
      ExchangetokenConst.BASE_URL);

  public static final String EXCHANGE_TOKEN_US_API_URL = EnvUtils.get("EXCHANGE_TOKEN_US_API_URL",
      ExchangetokenConst.US_BASE_URL);

  public static final String EXCHANGE_TOKEN_GOOGLE_API_URL = EnvUtils.get("EXCHANGE_TOKEN_GOOGLE_API_URL",
      ExchangetokenConst.GOOGLE_BASE_URL);

  public static final String EXCHANGE_TOKEN_US_GOOGLE_API_URL = EnvUtils.get("EXCHANGE_TOKEN_US_GOOGLE_API_URL",
      ExchangetokenConst.US_GOOGLE_BASE_URL);

  public static final String AIAPI_API_KEY = EnvUtils.get("AIAPI_API_KEY");
  public static final String AIAPI_API_URL = EnvUtils.get("AIAPI_API_URL", AiApiConst.V1_BASE_URL);

  public static EmbeddingResponse embeddings(PlatformInput platformInput, String text) {
    return embeddings(platformInput, null, text);
  }

  public static EmbeddingResponse embeddings(PlatformInput platformInput, String apiKey, String text) {
    String platform = platformInput.getPlatform();
    String model = platformInput.getModel();
    log.info("platform:{},model:{}", platform, model);
    if (ModelPlatformName.GOOGLE.equals(platform)) {
      if (apiKey == null) {
        apiKey = GEMINI_API_KEY;
      }
      return useGoogle(apiKey, model, text);
    } else if (ModelPlatformName.VERTEX_AI.equals(platform)) {
      if (apiKey == null) {
        apiKey = VERTEX_AI_API_KEY;
      }
      return useVertexAi(apiKey, model, text);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenGoogle(apiKey, model, text);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_GOOGLE.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsGoogle(apiKey, model, text);

    } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
      if (apiKey == null) {
        apiKey = CLAUDE_API_KEY;
      }
      return useClaude(apiKey, model, text);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenClaude(apiKey, model, text);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsClaude(apiKey, model, text);
    } else if (ModelPlatformName.VOLC_ENGINE.equals(platform)) {
      if (apiKey == null) {
        apiKey = VOLCENGINE_API_KEY;
      }

      return useVolcEngine(apiKey, model, text);

    } else if (ModelPlatformName.OPENROUTER.equals(platform)) {
      if (apiKey == null) {
        apiKey = OPENROUTER_API_KEY;
      }
      return useOpenRouter(apiKey, model, text);

    } else if (ModelPlatformName.ZENMUX.equals(platform)) {
      if (apiKey == null) {
        apiKey = ZENMUX_API_KEY;
      }
      return useZenmux(apiKey, model, text);

    } else if (ModelPlatformName.BAILIAN.equals(platform)) {
      if (apiKey == null) {
        apiKey = BAILIAN_API_KEY;
      }
      return useBailian(apiKey, model, text);

    } else if (ModelPlatformName.TENCENT.equals(platform)) {
      if (apiKey == null) {
        apiKey = TENCENT_API_KEY;
      }
      return useTencent(apiKey, model, text);

    } else if (ModelPlatformName.MINIMAX.equals(platform)) {
      if (apiKey == null) {
        apiKey = MINIMAX_API_KEY;
      }
      return useMiniMax(apiKey, model, text);

    } else if (ModelPlatformName.MOONSHOT.equals(platform)) {
      if (apiKey == null) {
        apiKey = MOONSHOT_API_KEY;
      }
      return useMoonshot(apiKey, model, text);

    } else if (ModelPlatformName.CEREBRAS.equals(platform)) {
      if (apiKey == null) {
        apiKey = CEREBRAS_API_KEY;
      }
      return useCerebras(apiKey, model, text);

    } else if (ModelPlatformName.OLLAMA.equals(platform)) {
      if (apiKey == null) {
        apiKey = OLLAMA_API_KEY;
      }
      return useOllama(apiKey, model, text);

    } else if (ModelPlatformName.LLAMACPP.equals(platform)) {
      if (apiKey == null) {
        apiKey = LLAMACPP_API_KEY;
      }
      return useLlamacpp(apiKey, model, text);

    } else if (ModelPlatformName.VLLM.equals(platform)) {
      if (apiKey == null) {
        apiKey = VLLM_API_KEY;
      }
      return useVllm(apiKey, model, text);

    } else if (ModelPlatformName.SWIFT.equals(platform)) {
      if (apiKey == null) {
        apiKey = SWIFT_API_KEY;
      }
      return useSwift(apiKey, model, text);

    } else if (ModelPlatformName.TITANIUM.equals(platform)) {
      if (apiKey == null) {
        apiKey = TITANIUM_API_KEY;
      }
      return useTitanium(apiKey, model, text);

    } else if (ModelPlatformName.GITEE.equals(platform)) {
      if (apiKey == null) {
        apiKey = GITEE_API_KEY;
      }
      return useGitee(apiKey, model, text);

    } else if (ModelPlatformName.EXCHANGE_TOKEN.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetoken(apiKey, model, text);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_US.equals(platform)) {
      if (apiKey == null) {
        apiKey = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetokenUs(apiKey, model, text);

    } else if (ModelPlatformName.AIAPI.equals(platform)) {
      if (apiKey == null) {
        apiKey = AIAPI_API_KEY;
      }
      return useAiApi(apiKey, model, text);

    } else {
      if (apiKey == null) {
        apiKey = OPENAI_API_KEY;
      }
      return useOpenAi(apiKey, model, text);
    }

  }

  private static EmbeddingResponse useOpenAi(String key, String model, String text) {
    return OpenAiClient.embeddings(OPENAI_API_URL, key, model, text);
  }

  private static EmbeddingResponse useAiApi(String key, String model, String text) {
    return OpenAiClient.embeddings(AIAPI_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangetokenUs(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_US_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangetoken(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_API_URL, key, model, text);
  }

  private static EmbeddingResponse useGitee(String key, String model, String text) {
    return OpenAiClient.embeddings(GITEE_API_URL, key, model, text);
  }

  private static EmbeddingResponse useTitanium(String key, String model, String text) {
    return OpenAiClient.embeddings(TITANIUM_API_URL, key, model, text);
  }

  private static EmbeddingResponse useSwift(String key, String model, String text) {
    return OpenAiClient.embeddings(SWIFT_API_URL, key, model, text);
  }

  private static EmbeddingResponse useVllm(String key, String model, String text) {
    return OpenAiClient.embeddings(VLLM_API_URL, key, model, text);
  }

  private static EmbeddingResponse useLlamacpp(String key, String model, String text) {
    return OpenAiClient.embeddings(LLAMACPP_API_URL, key, model, text);
  }

  private static EmbeddingResponse useOllama(String key, String model, String text) {
    return OpenAiClient.embeddings(OLLAMA_API_URL, key, model, text);
  }

  private static EmbeddingResponse useCerebras(String key, String model, String text) {
    return OpenAiClient.embeddings(OLLAMA_API_URL, key, model, text);
  }

  private static EmbeddingResponse useMoonshot(String key, String model, String text) {
    return OpenAiClient.embeddings(MOONSHOT_API_URL, key, model, text);
  }

  private static EmbeddingResponse useMiniMax(String key, String model, String text) {
    return OpenAiClient.embeddings(MINIMAX_API_URL, key, model, text);
  }

  private static EmbeddingResponse useTencent(String key, String model, String text) {
    return OpenAiClient.embeddings(TENCENT_API_URL, key, model, text);

  }

  private static EmbeddingResponse useBailian(String key, String model, String text) {
    return OpenAiClient.embeddings(BAILIAN_API_URL, key, model, text);
  }

  private static EmbeddingResponse useZenmux(String key, String model, String text) {
    return OpenAiClient.embeddings(ZENMUX_API_URL, key, model, text);
  }

  private static EmbeddingResponse useOpenRouter(String key, String model, String text) {
    return OpenAiClient.embeddings(OPENROUTER_API_URL, key, model, text);
  }

  private static EmbeddingResponse useVolcEngine(String key, String model, String text) {
    return OpenAiClient.embeddings(VOLCENGINE_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangeTokenUsClaude(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_US_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangeTokenClaude(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_API_URL, key, model, text);
  }

  private static EmbeddingResponse useClaude(String key, String model, String text) {
    return OpenAiClient.embeddings(CLAUDE_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangeTokenUsGoogle(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_US_GOOGLE_API_URL, key, model, text);
  }

  private static EmbeddingResponse useExchangeTokenGoogle(String key, String model, String text) {
    return OpenAiClient.embeddings(EXCHANGE_TOKEN_GOOGLE_API_URL, key, model, text);
  }

  private static EmbeddingResponse useVertexAi(String key, String model, String text) {
    return OpenAiClient.embeddings(VERTEX_AI_API_URL, key, model, text);
  }

  private static EmbeddingResponse useGoogle(String key, String model, String text) {
    return OpenAiClient.embeddings(GEMINI_API_URL, key, model, text);
  }
}
