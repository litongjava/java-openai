# Bailian TTS

[Online java-openai guide (tio-boot.cn)](https://tio-boot.cn/zh/54_java-openai/20.html) · [Online java-openai guide (tio-boot.com)](https://tio-boot.com/zh/54_java-openai/20.html)


`BailianTTSClient` wraps Alibaba Cloud Bailian / DashScope text-to-speech through the
`/api/v1/services/audio/tts/SpeechSynthesizer` endpoint.

## Configuration

Load `app.properties` or environment variables before calling the client:

```properties
BAILIAN_API_KEY=your-bailian-api-key

# Optional. Defaults to https://dashscope.aliyuncs.com
BAILIEN_BASE_URL=https://dashscope.aliyuncs.com
```

The base URL environment variable is currently named `BAILIEN_BASE_URL` in code.

## Quick Start

This example follows `nexus.io.bailian.BailianTTSClientTest.test()`:

```java
import nexus.io.bailian.BailianTTSClient;
import nexus.io.bailian.tts.BailianTTSResponse;
import nexus.io.exception.GenerateException;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class BailianTTSExample {
  public static void main(String[] args) {
    EnvUtils.load();

    BailianTTSClient client = new BailianTTSClient();
    String model = "cosyvoice-v3.5-plus";
    String voice = "cosyvoice-v3.5-plus-bailian-e46c4292e6504f808be470fe6f9c8b93";

    try {
      BailianTTSResponse response = client.tts(model, voice, "How is the weather today?");
      System.out.println(JsonUtils.toJson(response));
    } catch (GenerateException e) {
      System.out.println(e.getRequestBody());
      System.out.println(e.getResponseBody());
    }
  }
}
```

The shortcut constructor sends MP3 audio with a `16000` Hz sample rate by default.

## Custom Request

Use `BailianTTSRequest` and `BailianTTSInput` when you need to control format or
sample rate:

```java
import nexus.io.bailian.BailianTTSClient;
import nexus.io.bailian.tts.BailianTTSInput;
import nexus.io.bailian.tts.BailianTTSRequest;
import nexus.io.bailian.tts.BailianTTSResponse;

BailianTTSInput input = new BailianTTSInput();
input.setVoice("cosyvoice-v3.5-plus-bailian-e46c4292e6504f808be470fe6f9c8b93");
input.setText("How is the weather today?");
input.setFormat("mp3");
input.setSample_rate(16000);

BailianTTSRequest request = new BailianTTSRequest();
request.setModel("cosyvoice-v3.5-plus");
request.setInput(input);

BailianTTSResponse response = new BailianTTSClient().tts(request);
```

You can also pass a key explicitly:

```java
BailianTTSResponse response = new BailianTTSClient().tts(apiKey, request);
```

## Request Fields

| Field | Type | Description |
| --- | --- | --- |
| `model` | `String` | Bailian TTS model name, for example `cosyvoice-v3.5-plus`. |
| `input.voice` | `String` | Voice ID to synthesize with. |
| `input.text` | `String` | Text to synthesize. |
| `input.format` | `String` | Audio format. The shortcut constructor defaults to `mp3`. |
| `input.sample_rate` | `Integer` | Audio sample rate. The shortcut constructor defaults to `16000`. |

## Response Fields

| Field | Type | Description |
| --- | --- | --- |
| `request_id` | `String` | Provider request ID. |
| `output.finish_reason` | `String` | Provider finish reason. |
| `output.audio.id` | `String` | Audio asset ID when returned by Bailian. |
| `output.audio.url` | `String` | Remote audio URL when returned by Bailian. |
| `output.audio.data` | `String` | Inline audio data when returned by Bailian. |
| `output.audio.expires_at` | `Integer` | Expiration timestamp for URL-based audio. |
| `usage.characters` | `Integer` | Number of synthesized characters reported by the provider. |

## Error Handling

`BailianTTSClient.generate(...)` throws `GenerateException` for non-2xx HTTP
responses. The exception includes the translated request JSON and provider response
body:

```java
try {
  BailianTTSResponse response = client.tts(model, voice, text);
} catch (GenerateException e) {
  log.error("Bailian TTS failed: status={}, request={}, response={}",
      e.getStatusCode(), e.getRequestBody(), e.getResponseBody(), e);
}
```
