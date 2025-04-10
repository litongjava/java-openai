package com.litongjava.linux;

import java.io.IOException;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LinuxClient {

  private static OkHttpClient client = OkHttpClientPool.get1000HttpClient();

  public static ProcessResult executePythonCode(String code) {
    String apiBase = EnvUtils.getStr("LINUX_BASE_URL");
    String key = EnvUtils.getStr("LINUX_API_KEY");

    String targetUrl = apiBase + "/python";
    return post(key, targetUrl, code);
  }

  public static ProcessResult executeMainmCode(String apiBase, String key, String code) {
    String targetUrl = apiBase + "/manim";
    return post(key, targetUrl, code);
  }

  public static ProcessResult executeMainmCode(String apiBase, String code) {
    String key = EnvUtils.getStr("LINUX_API_KEY");
    String targetUrl = apiBase + "/manim";
    return post(key, targetUrl, code);
  }

  public static ProcessResult executeMainmCode(String code) {
    String apiBase = EnvUtils.getStr("LINUX_BASE_URL");
    String key = EnvUtils.getStr("LINUX_API_KEY");

    String targetUrl = apiBase + "/manim";
    return post(key, targetUrl, code);
  }

  private static ProcessResult post(String key, String targetUrl, String code) {
    MediaType mediaType = MediaType.parse("text/plain");

    RequestBody body = RequestBody.create(code, mediaType);
    Request request = new Request.Builder().url(targetUrl).method("POST", body).addHeader("authorization", "Bearer " + key).build();
    Call call = client.newCall(request);
    try (Response response = call.execute()) {
      String string = response.body().string();
      int resposneCode = response.code();
      if (response.isSuccessful()) {
        return JsonUtils.parse(string, ProcessResult.class);
      } else {
        throw new RuntimeException("code:" + resposneCode + " response:" + string);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl + " body:" + code, e);
    }
  }

}
