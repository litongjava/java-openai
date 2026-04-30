package nexus.io.gemini;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.http.OkHttpClientPool;
import nexus.io.tio.utils.hutool.StrUtil;
import nexus.io.tio.utils.json.Json;
import nexus.io.tio.utils.json.JsonUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Google Gemini TTS 客户端
 *
 * 对接 generateContent 接口, 使用 responseModalities=["AUDIO"] + speechConfig 完成文本转语音.
 * 模型示例: gemini-3.1-flash-preview-tts / gemini-2.5-flash-preview-tts
 *
 * 返回的 inlineData.data 是 base64 编码的 PCM (L16, 默认 24000 Hz, 单声道, 16-bit).
 * 如果需要直接得到可播放的 WAV 文件, 调用 {@link #pcmToWav(byte[], int, int, int)}
 * 或 {@link #saveAsWav(GeminiTTSResult, String)}.
 */
public class GeminiTTSClient {
  private static final Logger log = LoggerFactory.getLogger(GeminiTTSClient.class);

  public static boolean debug;
  public static final OkHttpClient httpClient = OkHttpClientPool.get1000HttpClient();
  public static final String GEMINI_API_URL = EnvUtils.get("GEMINI_API_URL", GeminiConsts.GEMINI_API_MODEL_BASE);

  /** 默认 TTS 模型名称 */
  public static final String DEFAULT_MODEL = "gemini-3.1-flash-preview-tts";
  /** Gemini 默认输出采样率 */
  public static final int DEFAULT_SAMPLE_RATE = 24000;

  // ====================== 单说话人 ======================

  /**
   * 单说话人合成 (使用环境变量 GEMINI_API_KEY)
   *
   * @param model     模型名称, 例如 "gemini-3.1-flash-preview-tts"
   * @param text      要合成的文本
   * @param voiceName 预置语音名称, 例如 "Kore", "Puck", "Charon"
   */
  public static GeminiTTSResult tts(String model, String text, String voiceName) {
    String apiKey = requireApiKey();
    return tts(GEMINI_API_URL, apiKey, model, text, voiceName);
  }

  public static GeminiTTSResult tts(String apiKey, String model, String text, String voiceName) {
    return tts(GEMINI_API_URL, apiKey, model, text, voiceName);
  }

  public static GeminiTTSResult tts(String baseUrl, String apiKey, String model, String text, String voiceName) {
    GeminiChatRequest request = buildSingleSpeakerRequest(text, voiceName);
    return generate(baseUrl, apiKey, model, request);
  }

  // ====================== 多说话人 ======================

  /**
   * 多说话人合成 (使用环境变量 GEMINI_API_KEY)
   *
   * @param model               模型名称
   * @param text                要合成的文本(其中可以包含 "Joe: ..." / "Jane: ..." 等说话人标记)
   * @param speakerVoiceConfigs 每个说话人对应的语音配置
   */
  public static GeminiTTSResult ttsMultiSpeaker(String model, String text,
      List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    String apiKey = requireApiKey();
    return ttsMultiSpeaker(GEMINI_API_URL, apiKey, model, text, speakerVoiceConfigs);
  }

  public static GeminiTTSResult ttsMultiSpeaker(String apiKey, String model, String text,
      List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    return ttsMultiSpeaker(GEMINI_API_URL, apiKey, model, text, speakerVoiceConfigs);
  }

  public static GeminiTTSResult ttsMultiSpeaker(String baseUrl, String apiKey, String model, String text,
      List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    GeminiChatRequest request = buildMultiSpeakerRequest(text, speakerVoiceConfigs);
    return generate(baseUrl, apiKey, model, request);
  }

  // ====================== 通用底层方法 ======================

  /**
   * 直接传入完整 GeminiChatRequest (调用方需自行设置 generationConfig)
   */
  public static GeminiTTSResult generate(String model, GeminiChatRequest request) {
    String apiKey = requireApiKey();
    return generate(GEMINI_API_URL, apiKey, model, request);
  }

  public static GeminiTTSResult generate(String apiKey, String model, GeminiChatRequest request) {
    return generate(GEMINI_API_URL, apiKey, model, request);
  }

  public static GeminiTTSResult generate(String baseUrl, String apiKey, String model, GeminiChatRequest request) {
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not be empty");
    }
    if (baseUrl == null) {
      baseUrl = GEMINI_API_URL;
    }
    String url = baseUrl + "/" + model + ":generateContent";

    String requestJson = Json.getSkipNullJson().toJson(request);
    if (debug) {
      log.info("{} {}", url, requestJson);
    }

    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request httpRequest = new Request.Builder().url(url).post(body).addHeader("x-goog-api-key", apiKey).build();

    try (Response response = httpClient.newCall(httpRequest).execute()) {
      String responseBody = response.body().string();

      if (!response.isSuccessful()) {
        String truncated = requestJson.length() > 1024 ? requestJson.substring(0, 1024) : requestJson;
        throw new GenerateException(ModelPlatformName.GOOGLE, "Gemini TTS generate failed", url, truncated,
            response.code(), responseBody);
      }
      GeminiChatResponse chatResponse = JsonUtils.parse(responseBody, GeminiChatResponse.class);
      return parseTTSResult(chatResponse, responseBody);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // ====================== 请求体构造 ======================

  public static GeminiChatRequest buildSingleSpeakerRequest(String text, String voiceName) {
    GeminiSpeechConfig speechConfig = GeminiSpeechConfig.prebuilt(voiceName);
    return buildRequest(text, speechConfig);
  }

  public static GeminiChatRequest buildMultiSpeakerRequest(String text,
      List<GeminiSpeakerVoiceConfig> speakerVoiceConfigs) {
    GeminiMultiSpeakerVoiceConfig multi = new GeminiMultiSpeakerVoiceConfig(speakerVoiceConfigs);
    GeminiSpeechConfig speechConfig = new GeminiSpeechConfig(multi);
    return buildRequest(text, speechConfig);
  }

  public static GeminiChatRequest buildRequest(String text, GeminiSpeechConfig speechConfig) {
    GeminiPart part = new GeminiPart(text);
    GeminiContent content = new GeminiContent("user", Collections.singletonList(part));

    GeminiGenerationConfig generationConfig = new GeminiGenerationConfig();
    generationConfig.setResponseModalities(Collections.singletonList("AUDIO"));
    generationConfig.setSpeechConfig(speechConfig);

    GeminiChatRequest request = new GeminiChatRequest(Collections.singletonList(content));
    request.setGenerationConfig(generationConfig);
    return request;
  }

  // ====================== 响应解析 ======================

  public static GeminiTTSResult parseTTSResult(GeminiChatResponse chatResponse, String rawData) {
    if (chatResponse == null || chatResponse.getCandidates() == null || chatResponse.getCandidates().isEmpty()) {
      throw new RuntimeException("Gemini TTS response has no candidates: " + rawData);
    }
    GeminiCandidate candidate = chatResponse.getCandidates().get(0);
    if (candidate.getContent() == null || candidate.getContent().getParts() == null
        || candidate.getContent().getParts().isEmpty()) {
      throw new RuntimeException("Gemini TTS response has no parts: " + rawData);
    }

    GeminiInlineData inlineData = null;
    for (GeminiPart part : candidate.getContent().getParts()) {
      if (part.getInlineData() != null) {
        inlineData = part.getInlineData();
        break;
      }
    }
    if (inlineData == null || StrUtil.isBlank(inlineData.getData())) {
      throw new RuntimeException("Gemini TTS response has no inlineData: " + rawData);
    }

    GeminiTTSResult result = new GeminiTTSResult();
    result.setMimeType(inlineData.getMimeType());
    result.setBase64Data(inlineData.getData());
    result.setPcmBytes(Base64.getDecoder().decode(inlineData.getData()));
    result.setSampleRate(parseSampleRate(inlineData.getMimeType()));
    result.setRawData(rawData);
    return result;
  }

  /**
   * 从 mimeType 中解析采样率, 例如:
   *   "audio/L16;codec=pcm;rate=24000" -> 24000
   *   解析失败时返回 {@link #DEFAULT_SAMPLE_RATE}
   */
  public static int parseSampleRate(String mimeType) {
    if (StrUtil.isBlank(mimeType)) {
      return DEFAULT_SAMPLE_RATE;
    }
    String lower = mimeType.toLowerCase();
    int idx = lower.indexOf("rate=");
    if (idx < 0) {
      return DEFAULT_SAMPLE_RATE;
    }
    String tail = mimeType.substring(idx + "rate=".length());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < tail.length(); i++) {
      char c = tail.charAt(i);
      if (c >= '0' && c <= '9') {
        sb.append(c);
      } else {
        break;
      }
    }
    if (sb.length() == 0) {
      return DEFAULT_SAMPLE_RATE;
    }
    try {
      return Integer.parseInt(sb.toString());
    } catch (NumberFormatException e) {
      return DEFAULT_SAMPLE_RATE;
    }
  }

  // ====================== WAV 工具 ======================

  /**
   * 将 PCM (L16, little-endian) 数据封装为 WAV 字节数组
   */
  public static byte[] pcmToWav(byte[] pcm, int sampleRate, int channels, int bitsPerSample) {
    int byteRate = sampleRate * channels * bitsPerSample / 8;
    int blockAlign = channels * bitsPerSample / 8;
    int dataSize = pcm.length;
    int totalSize = 36 + dataSize;

    ByteBuffer buffer = ByteBuffer.allocate(44 + dataSize).order(ByteOrder.LITTLE_ENDIAN);
    buffer.put("RIFF".getBytes());
    buffer.putInt(totalSize);
    buffer.put("WAVE".getBytes());
    buffer.put("fmt ".getBytes());
    buffer.putInt(16);
    buffer.putShort((short) 1); // PCM
    buffer.putShort((short) channels);
    buffer.putInt(sampleRate);
    buffer.putInt(byteRate);
    buffer.putShort((short) blockAlign);
    buffer.putShort((short) bitsPerSample);
    buffer.put("data".getBytes());
    buffer.putInt(dataSize);
    buffer.put(pcm);
    return buffer.array();
  }

  public static byte[] toWavBytes(GeminiTTSResult result) {
    return pcmToWav(result.getPcmBytes(), result.getSampleRate(), result.getChannels(), result.getBitsPerSample());
  }

  /**
   * 直接将 TTS 结果保存为 WAV 文件
   */
  public static Path saveAsWav(GeminiTTSResult result, String filePath) {
    byte[] wav = toWavBytes(result);
    Path path = Paths.get(filePath);
    try {
      if (path.getParent() != null) {
        Files.createDirectories(path.getParent());
      }
      Files.write(path, wav);
      return path;
    } catch (IOException e) {
      throw new RuntimeException("save wav failed: " + e.getMessage(), e);
    }
  }

  // ====================== private ======================

  private static String requireApiKey() {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    return apiKey;
  }
}
