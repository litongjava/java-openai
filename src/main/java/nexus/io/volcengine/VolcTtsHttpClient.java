package nexus.io.volcengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

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
 * Volcengine/Bytedance TTS HTTP v1 client.
 *
 * Endpoint: https://openspeech.bytedance.com/api/v1/tts
 */
public class VolcTtsHttpClient {
  private static final Logger log = LoggerFactory.getLogger(VolcTtsHttpClient.class);

  public static boolean debug;
  public static final OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();
  public static final String API_URL = EnvUtils.get("VOLC_TTS_API_URL", VolcConst.BYTE_DANCE_BASE_URL + "/api/v1/tts");

  public static final String DEFAULT_CLUSTER = "volcano_tts";
  public static final String DEFAULT_UID = "uid";
  public static final String DEFAULT_ENCODING = "mp3";
  public static final String DEFAULT_VOICE_TYPE = "zh_male_beijingxiaoye_moon_bigtts";
  public static final int SUCCESS_CODE = 3000;

  public static VolcTtsResult tts(String text) {
    return tts(text, DEFAULT_VOICE_TYPE);
  }

  public static VolcTtsResult tts(String text, String voiceType) {
    String appid = requireAppId();
    String accessToken = requireAccessToken();
    return tts(appid, accessToken, text, voiceType);
  }

  public static VolcTtsResult tts(String appid, String accessToken, String text, String voiceType) {
    VolcTtsRequest request = buildRequest(appid, accessToken, text, voiceType);
    return generate(accessToken, request);
  }

  public static VolcTtsResult tts(String appid, String accessToken, String text, String voiceType, String encoding) {
    VolcTtsRequest request = buildRequest(appid, accessToken, text, voiceType, encoding);
    return generate(accessToken, request);
  }

  public static byte[] ttsBytes(String text) {
    return tts(text).getAudioBytes();
  }

  public static byte[] ttsBytes(String text, String voiceType) {
    return tts(text, voiceType).getAudioBytes();
  }

  public static VolcTtsResult generate(String accessToken, VolcTtsRequest request) {
    return generate(API_URL, accessToken, request);
  }

  public static VolcTtsResult generate(String apiUrl, String accessToken, VolcTtsRequest request) {
    if (StrUtil.isBlank(accessToken)) {
      throw new RuntimeException("VOLC_ACCESS_TOKEN is empty");
    }
    if (request == null) {
      throw new RuntimeException("VolcTtsRequest can not be null");
    }
    if (StrUtil.isBlank(apiUrl)) {
      apiUrl = API_URL;
    }

    String requestJson = Json.getSkipNullJson().toJson(request);
    if (debug) {
      log.info("{} {}", apiUrl, requestJson);
    }

    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request httpRequest = new Request.Builder().url(apiUrl).post(body)
        .addHeader("Authorization", "Bearer;" + accessToken).build();

    try (Response response = httpClient.newCall(httpRequest).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        String truncated = truncate(requestJson);
        throw new GenerateException(ModelPlatformName.VOLC_ENGINE, "Volc TTS HTTP request failed", apiUrl, truncated,
            response.code(), responseBody);
      }

      VolcTtsResponse ttsResponse = JsonUtils.parse(responseBody, VolcTtsResponse.class);
      return parseTTSResult(ttsResponse, responseBody, getEncoding(request));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static VolcTtsRequest buildRequest(String appid, String accessToken, String text, String voiceType) {
    return buildRequest(appid, accessToken, text, voiceType, DEFAULT_ENCODING);
  }

  public static VolcTtsRequest buildRequest(String appid, String accessToken, String text, String voiceType,
      String encoding) {
    if (StrUtil.isBlank(appid)) {
      throw new RuntimeException("VOLC_APP_ID is empty");
    }
    if (StrUtil.isBlank(accessToken)) {
      throw new RuntimeException("VOLC_ACCESS_TOKEN is empty");
    }
    if (StrUtil.isBlank(text)) {
      throw new RuntimeException("text can not be empty");
    }
    if (StrUtil.isBlank(voiceType)) {
      voiceType = DEFAULT_VOICE_TYPE;
    }
    if (StrUtil.isBlank(encoding)) {
      encoding = DEFAULT_ENCODING;
    }
    VolcAudio audio = VolcAudio.builder().voice_type(voiceType).encoding(encoding).build();
    VolcRequest request = VolcRequest.builder().reqid(UUID.randomUUID().toString()).operation("query").text(text)
        .build();

    return VolcTtsRequest.builder()
        .app(VolcApp.builder().appid(appid).token(accessToken).cluster(DEFAULT_CLUSTER).build())
        .user(VolcUser.builder().uid(DEFAULT_UID).build()).audio(audio).request(request).build();
  }

  public static VolcTtsResult parseTTSResult(VolcTtsResponse response, String rawData, String encoding) {
    if (response == null) {
      throw new RuntimeException("Volc TTS response is empty: " + rawData);
    }
    Integer code = response.getCode();
    if (code == null || code.intValue() != SUCCESS_CODE) {
      throw new RuntimeException("Volc TTS failed, code:" + code + ", message:" + response.getMessage());
    }
    if (StrUtil.isBlank(response.getData())) {
      throw new RuntimeException("Volc TTS response has no audio data: " + rawData);
    }

    VolcTtsResult result = new VolcTtsResult();
    result.setReqid(response.getReqid());
    result.setEncoding(encoding);
    result.setBase64Data(response.getData());
    result.setAudioBytes(Base64.getDecoder().decode(response.getData()));
    result.setResponse(response);
    result.setRawData(rawData);
    return result;
  }

  public static Path saveAudio(VolcTtsResult result, String filePath) {
    Path path = Paths.get(filePath);
    try {
      if (path.getParent() != null) {
        Files.createDirectories(path.getParent());
      }
      Files.write(path, result.getAudioBytes());
      return path;
    } catch (IOException e) {
      throw new RuntimeException("save audio failed: " + e.getMessage(), e);
    }
  }

  private static String requireAppId() {
    String appid = EnvUtils.getStr("VOLC_APP_ID");
    if (StrUtil.isBlank(appid)) {
      throw new RuntimeException("VOLC_APP_ID is empty");
    }
    return appid;
  }

  private static String requireAccessToken() {
    String accessToken = EnvUtils.getStr("VOLC_ACCESS_TOKEN");
    if (StrUtil.isBlank(accessToken)) {
      throw new RuntimeException("VOLC_ACCESS_TOKEN is empty");
    }
    return accessToken;
  }

  private static String getEncoding(VolcTtsRequest request) {
    if (request == null || request.getAudio() == null || StrUtil.isBlank(request.getAudio().getEncoding())) {
      return DEFAULT_ENCODING;
    }
    return request.getAudio().getEncoding();
  }

  private static String truncate(String text) {
    if (text == null || text.length() <= 1024) {
      return text;
    }
    return text.substring(0, 1024);
  }
}
