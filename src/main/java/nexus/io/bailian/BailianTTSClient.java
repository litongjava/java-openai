package nexus.io.bailian;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nexus.io.bailian.tts.BailianTTSRequest;
import nexus.io.bailian.tts.BailianTTSResponse;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.http.OkHttpClientPool;
import nexus.io.tio.utils.hutool.StrUtil;
import nexus.io.tio.utils.json.JsonUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BailianTTSClient {
  private static final Logger log = LoggerFactory.getLogger(BaiLianClient.class);

  public static boolean debug;
  public static final OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();
  public static final String BASE_URL = EnvUtils.get("BAILIEN_BASE_URL", BaiLianConst.BAILIEN_BASE_URL);
  public static final String API_URL = BASE_URL + "/api/v1/services/audio/tts/SpeechSynthesizer";
  public static final String API_KEY = EnvUtils.get("BAILIAN_API_KEY");

  public BailianTTSResponse tts(String model, String voice, String text) {
    BailianTTSRequest request = new BailianTTSRequest(model, voice, text);
    return generate(API_URL, API_KEY, request);
  }

  public BailianTTSResponse tts(BailianTTSRequest request) {
    return generate(API_URL, API_KEY, request);
  }

  public BailianTTSResponse tts(String apiKey, BailianTTSRequest request) {
    return generate(API_URL, apiKey, request);
  }

  public static BailianTTSResponse generate(String apiUrl, String accessToken, BailianTTSRequest request) {
    if (StrUtil.isBlank(accessToken)) {
      throw new RuntimeException("BAILIEN_API_KEY is empty");
    }
    if (request == null) {
      throw new RuntimeException("request can not be null");
    }
    if (StrUtil.isBlank(apiUrl)) {
      apiUrl = API_URL;
    }

    String requestJson = JsonUtils.toSkipNullJson(request);
    if (debug) {
      log.info("{} {}", apiUrl, requestJson);
    }

    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request httpRequest = new Request.Builder().url(apiUrl).post(body)
        .addHeader("Authorization", "Bearer " + accessToken).build();

    try (Response response = httpClient.newCall(httpRequest).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        log.error(responseBody);
        throw new GenerateException(ModelPlatformName.BAILIAN, "TTS HTTP request failed", apiUrl, requestJson,
            response.code(), responseBody);
      }

      return JsonUtils.parse(responseBody, BailianTTSResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
