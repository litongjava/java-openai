# java-openai

A unified Java client for working with large language model providers — OpenAI, Google (Gemini & Vertex AI), Anthropic Claude, OpenRouter, and many more — through a single, stable abstraction.

The core idea: **business code should describe intent, not protocol details**. The library translates a unified request/response model to and from each provider's native protocol, so adding or switching platforms doesn't ripple through your code.

- Unified entry point: `UniChatClient`
- Unified request: `UniChatRequest`
- Unified response: `UniChatResponse`
- Unified message: `UniChatMessage`
- Built on top of [OkHttp](https://square.github.io/okhttp/) and [FastJSON2](https://github.com/alibaba/fastjson)

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Quick Start](#quick-start)
- [Supported Platforms](#supported-platforms)
- [UniChatRequest](#unichatrequest)
- [UniChatResponse](#unichatresponse)
- [Streaming Generation](#streaming-generation)
- [Image / Multimodal Input](#image--multimodal-input)
- [Function / Tool Calling](#function--tool-calling)
- [Google Gemini](#google-gemini)
- [Google Vertex AI](#google-vertex-ai)
- [Anthropic Claude](#anthropic-claude)
- [Error Handling](#error-handling)
- [Project Layout](#project-layout)
- [License](#license)

## Installation

```xml
<dependency>
  <groupId>nexus.io</groupId>
  <artifactId>java-openai</artifactId>
  <version>1.3.1</version>
</dependency>
```

## Configuration

`UniChatClient` resolves API keys and base URLs from environment variables (or `app.properties` loaded via `EnvUtils.load()`). Set only the ones you need:

```properties
# OpenAI / OpenAI-compatible
OPENAI_API_KEY=...
OPENAI_API_URL=https://api.openai.com/v1

# Google Gemini
GEMINI_API_KEY=...

# Google Vertex AI (use API key, not Service Account — see Vertex AI section)
VERTEX_AI_API_KEY=...
VERTEX_AI_API_URL=https://aiplatform.googleapis.com/v1/publishers/google/models

# Anthropic Claude
CLAUDE_API_KEY=...

# Aggregators / regional platforms
OPENROUTER_API_KEY=...
ZENMUX_API_KEY=...
VOLCENGINE_API_KEY=...
BAILIAN_API_KEY=...
TENCENT_API_KEY=...
MOONSHOT_API_KEY=...
MINIMAX_API_KEY=...
CEREBRAS_API_KEY=...
GITEE_API_KEY=...

# Self-hosted inference engines (only the URL is usually required)
OLLAMA_API_URL=...
LLAMACPP_API_URL=...
VLLM_API_URL=...
SWIFT_API_URL=...

# Exchange-token relay channels
EXCHANGE_TOKEN_API_KEY=...
```

Never commit API keys. Use a Secret Manager, Kubernetes Secret, or CI variables in production.

## Quick Start

```java
import java.util.ArrayList;
import java.util.List;

import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.openrouter.OpenRouterModels;
import nexus.io.tio.utils.environment.EnvUtils;

public class QuickStart {
  public static void main(String[] args) {
    EnvUtils.load();

    PlatformInput platform = new PlatformInput(
        ModelPlatformName.OPENROUTER,
        OpenRouterModels.XIAOMI_MIMO_V2_FLASH_FREE
    );

    List<UniChatMessage> messages = new ArrayList<>();
    messages.add(UniChatMessage.buildUser("just say hi"));

    UniChatRequest request = new UniChatRequest(platform);
    request.setMessages(messages);

    UniChatResponse response = UniChatClient.generate(request);
    System.out.println(response.getMessage().getContent());
  }
}
```

For single-shot user prompts there's a shortcut:

```java
UniChatRequest request = new UniChatRequest(platform);
request.setUserPrompts("how are you?");
UniChatResponse response = UniChatClient.generate(request);
```

## Supported Platforms

`ModelPlatformName` enumerates every platform the unified client can route to. Switching providers usually means changing only the `PlatformInput`.

| Category | Constants |
| --- | --- |
| Native protocols | `OPENAI`, `GOOGLE`, `ANTHROPIC` |
| Google Cloud | `VERTEX_AI` |
| Aggregators (overseas) | `OPENROUTER`, `ZENMUX`, `CEREBRAS` |
| Aggregators / enterprise (regional) | `VOLC_ENGINE`, `BAILIAN`, `TENCENT`, `MOONSHOT`, `MINIMAX`, `SILICONFLOW`, `CHAT_GLM`, `GITEE` |
| Self-hosted | `OLLAMA`, `LLAMACPP`, `VLLM`, `SWIFT`, `TITANIUM` |
| Relay / exchange-token | `EXCHANGE_TOKEN`, `EXCHANGE_TOKEN_GOOGLE`, `EXCHANGE_TOKEN_ANTHROPIC`, `EXCHANGE_TOKEN_US`, `EXCHANGE_TOKEN_US_GOOGLE`, `EXCHANGE_TOKEN_US_ANTHROPIC` |
| Other | `AIAPI`, `ELEVEN_LABS`, `AUTO` |

Each platform module ships its own model constant class (`OpenAiModels`, `GoogleModels`, `ClaudeModels`, `OpenRouterModels`, `DeepSeekModels`, `MoonshotModels`, …) so model names are typed, not stringly.

## UniChatRequest

```java
public class UniChatRequest {
  private String domain;
  private String apiPrefixUrl;
  private Long groupId;
  private String groupName;
  private Long taskId;
  private String taskName;

  private boolean useSystemPrompt = true;
  private String apiKey;
  private String platform;
  private String model;
  private String systemPrompt;
  private boolean cacheSystemPrompt;
  private Boolean stream;
  private List<UniChatMessage> messages;
  private Float temperature;
  private String cachedId;
  private Integer max_tokens;
  private Boolean enable_thinking;
  private UniThinkingConfig thinkingConfig;
  private String responseFormat;
  private UniResponseSchema responseSchema;
  private ChatProvider provider;
  private List<String> responseModalities;
  private Boolean enable_search;
  private List<ChatRequestTool> tools;
}
```

Field groups:

- **Business identifiers** (never sent to the model): `domain`, `groupId`, `groupName`, `taskId`, `taskName` — used for routing, logging, billing, and audit.
- **Platform & auth**: `platform`, `model`, `apiKey`, `apiPrefixUrl`, `provider`.
- **Prompt & messages**: `useSystemPrompt`, `systemPrompt`, `cacheSystemPrompt`, `messages`.
- **Generation**: `temperature`, `max_tokens`, `responseFormat`, `responseSchema`.
- **Reasoning**: `enable_thinking`, `thinkingConfig`.
- **Streaming / multimodal / search**: `stream`, `responseModalities`, `enable_search`.
- **Cache reuse**: `cachedId`.
- **Tools**: `tools` (function/tool calling — see below).

## UniChatResponse

```java
public class UniChatResponse {
  private transient String rawData;        // original platform JSON, useful for debugging
  private String model;                    // actual model that served the request
  private ChatResponseMessage message;     // full message — use for non-streaming
  private ChatResponseDelta delta;         // incremental chunk — use for streaming
  private ChatResponseUsage usage;         // unified token usage
  private List<String> citations;          // RAG / search citations when available
}
```

- For `UniChatClient.generate(...)`, read `response.getMessage().getContent()`.
- For `UniChatClient.stream(...)`, read `response.getDelta().getContent()` inside `onData`.

## Streaming Generation

Streaming uses the same `UniChatRequest` plus a `UniChatEventListener`. Below is a Tio-Boot SSE handler that pipes deltas straight to the browser:

```java
import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatEventListener;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.openrouter.OpenRouterModels;
import nexus.io.tio.boot.http.TioRequestContext;
import nexus.io.tio.core.ChannelContext;
import nexus.io.tio.core.Tio;
import nexus.io.tio.http.common.HttpRequest;
import nexus.io.tio.http.common.HttpResponse;
import nexus.io.tio.http.common.sse.SsePacket;
import nexus.io.tio.http.server.handler.HttpRequestHandler;
import nexus.io.tio.http.server.util.SseEmitter;

import okhttp3.Response;
import okhttp3.sse.EventSource;

public class PredictStreamHandler implements HttpRequestHandler {

  @Override
  public HttpResponse handle(HttpRequest httpRequest) throws Exception {
    PlatformInput platform = new PlatformInput(
        ModelPlatformName.OPENROUTER,
        OpenRouterModels.XIAOMI_MIMO_V2_FLASH_FREE
    );

    UniChatRequest request = new UniChatRequest(platform);
    request.setUserPrompts("just say hi");

    HttpResponse httpResponse = TioRequestContext.getResponse();
    httpResponse.setSend(false);

    ChannelContext ctx = httpRequest.channelContext;

    UniChatEventListener listener = new UniChatEventListener() {
      @Override
      public void onOpen(EventSource eventSource, Response response) {
        httpResponse.addServerSentEventsHeader();
        Tio.send(ctx, httpResponse);
      }

      @Override
      public void onData(UniChatResponse chatResponse) {
        // streaming output lives on delta, not message
        String content = chatResponse.getDelta().getContent();
        Tio.bSend(ctx, new SsePacket(content));
      }

      @Override
      public void onClosed(EventSource eventSource) {
        SseEmitter.closeSeeConnection(ctx);
      }
    };

    UniChatClient.stream(request, listener);
    return httpResponse;
  }
}
```

> **Important.** In streaming mode each `onData` callback only carries the new chunk. Always read `chatResponse.getDelta()`, not `getMessage()`.

## Image / Multimodal Input

Images are attached to a `UniChatMessage` via `ChatImageFile`. The unified client maps the image to whatever each platform expects (`image_url`, `inline_data`, vision blocks, etc.).

```java
import java.util.ArrayList;
import java.util.List;

import nexus.io.chat.ChatImageFile;
import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.openrouter.OpenRouterModels;
import nexus.io.tio.utils.base64.Base64Utils;
import nexus.io.tio.utils.http.ContentTypeUtils;
import nexus.io.tio.utils.hutool.FilenameUtils;

public class ImageOcrExample {

  public String recognize(byte[] data, String filename) {
    String suffix = FilenameUtils.getSuffix(filename);
    String mimeType = ContentTypeUtils.getContentType(suffix);
    String encoded = Base64Utils.encodeImage(data, mimeType);

    List<ChatImageFile> files = new ArrayList<>();
    files.add(ChatImageFile.base64(mimeType, encoded));

    UniChatMessage message = new UniChatMessage();
    message.setContent("image");
    message.setFiles(files);

    PlatformInput platform = new PlatformInput(
        ModelPlatformName.OPENROUTER,
        OpenRouterModels.QWEN_QWEN2_5_VL_7B_INSTRUCT_FREE
    );

    UniChatRequest request = new UniChatRequest(platform);
    request.setSystemPrompt("Extract the text from the image.");

    List<UniChatMessage> messages = new ArrayList<>();
    messages.add(message);
    request.setMessages(messages);

    UniChatResponse response = UniChatClient.generate(request);
    return response.getMessage().getContent();
  }
}
```

You can also pass a remote image with `ChatImageFile.url("https://...")`.

## Function / Tool Calling

Declare tools via `ChatRequestTool` and assemble assistant/tool messages with `UniChatMessage.buildAssistantWithToolCalls(...)` and `UniChatMessage.buildTool(...)`.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatMessage;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.openai.chat.ChatRequestFunctionParameter;
import nexus.io.openai.chat.ChatRequestFunctionProperty;
import nexus.io.openai.chat.ChatRequestTool;
import nexus.io.openai.chat.ChatRequestToolCallFunction;
import nexus.io.openai.constants.OpenAiModels;

public class ToolCallExample {
  public static void main(String[] args) {
    ChatRequestFunctionParameter parameter = new ChatRequestFunctionParameter();
    parameter.setType("object");
    Map<String, ChatRequestFunctionProperty> properties = new HashMap<>();
    properties.put("sql", new ChatRequestFunctionProperty("string", "The SQL statement to execute."));
    parameter.setProperties(properties);
    parameter.setRequired(List.of("sql"));

    ChatRequestToolCallFunction function = new ChatRequestToolCallFunction();
    function.setName("find");
    function.setDescription("Execute SQL query on the database.");
    function.setParameters(parameter);

    ChatRequestTool tool = new ChatRequestTool("function", function);

    List<UniChatMessage> messages = new ArrayList<>();
    messages.add(UniChatMessage.buildSystem("You are a database assistant. The database is PostgreSQL."));
    messages.add(UniChatMessage.buildUser("How many tables are in my database?"));

    UniChatRequest request = new UniChatRequest(
        new PlatformInput(ModelPlatformName.OPENAI, OpenAiModels.GPT_4O_MINI));
    request.setMessages(messages);
    request.setTools(List.of(tool));

    UniChatResponse response = UniChatClient.generate(request);
    System.out.println(response.getMessage().getToolCalls());
  }
}
```

## Google Gemini

```java
PlatformInput platform = new PlatformInput(ModelPlatformName.GOOGLE, GoogleModels.GEMINI_2_5_FLASH);

UniChatRequest request = new UniChatRequest(platform);
request.setSystemPrompt("You summarize Chinese math concepts.");
request.setUserPrompts("Input: 什么是线性齐次递推\nSummary:");

UniChatResponse response = UniChatClient.generate(request);
System.out.println(response.getMessage().getContent());
```

The unified request is automatically translated into Gemini's `system_instruction` + `contents` / `parts` shape, and the response's nested `candidates[0].content.parts[0].text` is mapped back into `response.getMessage().getContent()`. Token usage from `usageMetadata` is unified into `response.getUsage()`.

Enable Google Search grounding with `request.setEnable_search(true)` — citations land in `response.getMessage()` via `UniSources` and in `response.getCitations()` when available.

## Google Vertex AI

`java-openai` does not currently sign Service Account credentials. To use Vertex AI, create an API Key bound to the target Service Account in the Google Cloud console and pass it via `VERTEX_AI_API_KEY`. The protocol family stays Gemini — only the platform entry changes.

```java
import nexus.io.chat.PlatformInput;
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.gemini.GoogleModels;
import nexus.io.tio.utils.environment.EnvUtils;

public class VertexAiExample {
  public static void main(String[] args) {
    EnvUtils.load();
    PlatformInput platform = new PlatformInput(
        ModelPlatformName.VERTEX_AI,
        GoogleModels.GEMINI_2_5_FLASH);

    UniChatRequest request = new UniChatRequest(platform);
    request.setUserPrompts("how are you?");

    try {
      UniChatResponse response = UniChatClient.generate(request);
      System.out.println(response.getMessage().getContent());
    } catch (GenerateException e) {
      System.out.println(e.getResponseBody());
    }
  }
}
```

## Anthropic Claude

```java
PlatformInput platform = new PlatformInput(
    ModelPlatformName.ANTHROPIC, ClaudeModels.CLAUDE_3_7_SONNET_20250219);

UniChatRequest request = new UniChatRequest(platform);
request.setSystemPrompt(systemPrompt);
request.setCacheSystemPrompt(true);   // adds Anthropic prompt-cache breakpoint
request.setMessages(messages);
request.setMax_tokens(64000);

UniChatResponse response = UniChatClient.generate(request);
```

`cacheSystemPrompt = true` automatically attaches Anthropic's `cache_control` block to the system message. To send images, attach `ChatImageFile` files exactly as in the multimodal section — they are translated into Claude's `image` content blocks.

## Error Handling

When a provider rejects the request, `UniChatClient` throws `GenerateException`. The exception preserves the **already-translated** request body and the raw response — invaluable for diagnosing provider-side failures.

```java
import nexus.io.chat.UniChatClient;
import nexus.io.chat.UniChatRequest;
import nexus.io.chat.UniChatResponse;
import nexus.io.exception.GenerateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniPredictService {

  public UniChatResponse generate(UniChatRequest request) {
    for (int i = 0; i < 10; i++) {
      try {
        return UniChatClient.generate(request);
      } catch (GenerateException e) {
        log.error("provider call failed: status={}, body={}, request={}",
            e.getStatusCode(), e.getResponseBody(), e.getRequestBody(), e);
      }
    }
    return null;
  }
}
```

For successful calls the original platform JSON is still available on `UniChatResponse.getRawData()`.

## Project Layout

Code is organised by capability domain rather than by client class. Selected modules:

| Module | Purpose |
| --- | --- |
| `chat` | Unified abstractions: `UniChatClient`, `UniChatRequest`, `UniChatResponse`, `UniChatMessage`, `UniChatEventListener`, `ChatImageFile`, `PlatformInput`. |
| `openai` / `claude` / `gemini` / `google` | Native protocol adapters used by the unified client. |
| `vertexai` | Vertex AI platform constants. |
| `openrouter` / `groq` / `deepseek` / `moonshot` / `minimax` / `tencent` / `volcengine` / `byteplus` / `siliconflow` / `cerebras` / `bailian` / `gitee` / `zenmux` / `aiapi` | Aggregator / regional / self-hosted adapters and model constants. |
| `consts` | `ModelPlatformName`, model name catalogs, table names. |
| `exception` | `GenerateException` and friends. |
| `image` / `tts` / `fishaudio` / `textin` | Multimodal extensions: image generation, TTS (including Gemini TTS via `GeminiTTSClient`), OCR. |
| `search` / `searchapi` / `searxng` / `tavily` / `supadata` | Search-augmented generation backends. |
| `prompt` | Prompt template engine. |
| `mcp` | Tool-protocol integrations. |
| `proxy` / `linux` / `libreoffice` | Operational glue (proxy, OS utilities, document conversion). |

## License

Apache License 2.0. See [LICENSE](LICENSE).
