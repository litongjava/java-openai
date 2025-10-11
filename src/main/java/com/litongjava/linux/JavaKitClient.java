package com.litongjava.linux;

import java.io.IOException;

import com.litongjava.tio.utils.commandline.ProcessResult;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JavaKitClient {

  private static OkHttpClient client = OkHttpClientPool.get1000HttpClient();

  public static ProcessResult executePythonCode(ExecuteCodeRequest codeRequest) {
    String apiBase = EnvUtils.getStr("JAVA_KIT_BASE_URL");
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");

    String targetUrl = apiBase + "/python";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeMainmCode(ExecuteCodeRequest codeRequest) {
    String apiBase = EnvUtils.getStr("JAVA_KIT_BASE_URL");
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");

    String targetUrl = apiBase + "/manim";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeManimImageCode(String apiBase, String key, ExecuteCodeRequest codeRequest) {
    String targetUrl = apiBase + "/manim/image";
    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult startMainmSession(String apiBase, String key, long sessionId) {
    String targetUrl = apiBase + "/manim/start?session-id=" + sessionId;
    return get(targetUrl, key);
  }

  public static ProcessResult startMainmSession(String apiBase, String key) {
    String targetUrl = apiBase + "/manim/start";
    return get(targetUrl, key);
  }

  public static ProcessResult finishMainmSession(String apiBase, String key, long sessionPrt, String m3u8Path,
      String videos) {
    String targetUrl = apiBase + "/manim/finish?session_prt=%d&m3u8_path=%s&videos=%s";
    targetUrl = String.format(targetUrl, sessionPrt, m3u8Path, videos);
    return get(targetUrl, key);
  }

  public static ProcessResult executeMainmCode(String apiBase, String key, ExecuteCodeRequest codeRequest) {
    Long sessionPrt = codeRequest.getSessionPrt();
    String m3u8Path = codeRequest.getM3u8Path();
    String targetUrl = null;
    if (sessionPrt != null && m3u8Path != null) {
      targetUrl = apiBase + "/manim?session_prt=%d&m3u8_path=%s";
      targetUrl = String.format(targetUrl, sessionPrt, m3u8Path);
    } else {
      targetUrl = apiBase + "/manim";
    }

    return post(targetUrl, key, codeRequest);
  }

  public static ProcessResult executeMainmCode(String apiBase, ExecuteCodeRequest codeRequest) {
    String key = EnvUtils.getStr("JAVA_KIT_API_KEY");
    String targetUrl = apiBase + "/manim";
    return post(targetUrl, key, codeRequest);
  }

  private static ProcessResult get(String targetUrl, String key) {
    Request request = new Request.Builder().url(targetUrl).addHeader("authorization", "Bearer " + key).get().build();
    Call call = client.newCall(request);
    long start = System.currentTimeMillis();
    try (Response response = call.execute()) {
      String string = response.body().string();
      int resposneCode = response.code();
      if (response.isSuccessful()) {
        long end = System.currentTimeMillis();
        ProcessResult result = JsonUtils.parse(string, ProcessResult.class);
        if (result != null) {
          result.setElapsed(end - start);
        }
        return result;
      } else {
        throw new RuntimeException("code:" + resposneCode + " response:" + string);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl, e);
    }
  }

  private static ProcessResult post(String targetUrl, String key, ExecuteCodeRequest codeRequest) {
    Long sessionId = codeRequest.getSessionId();
    Long id = codeRequest.getId();
    String code = codeRequest.getCode();
    String figure = codeRequest.getFigure();
    Integer timeout = codeRequest.getTimeout();
    String quality = codeRequest.getQuality();

    Request.Builder builder = new Request.Builder();
    builder.url(targetUrl).addHeader("authorization", "Bearer " + key);
    if (sessionId != null) {
      builder.addHeader("session-id", sessionId.toString());
    }
    if (id != null) {
      builder.addHeader("code-id", id.toString());
    }
    if (timeout != null) {
      builder.addHeader("code-timeout", timeout.toString());
    }
    if (quality != null) {
      builder.addHeader("quality", quality);
    }

    if (figure == null) {
      MediaType mediaType = MediaType.parse("text/plain");
      RequestBody body = RequestBody.create(code, mediaType);
      builder.method("POST", body);
    } else {

      okhttp3.MultipartBody.Builder formBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
      // Create the request body with file and image media type
      RequestBody fileBody = getFileBody("main.py", code);
      // Create MultipartBody
      formBuilder.addFormDataPart("code", "main.py", fileBody);

      RequestBody figureBody = getFileBody("pgdp-output.json", code);
      // Create MultipartBody
      formBuilder.addFormDataPart("figure", "pgdp-output.json", figureBody);

      RequestBody requestBody = formBuilder.build();
      builder.method("POST", requestBody);
    }

    Request request = builder.build();
    long start = System.currentTimeMillis();
    Call call = client.newCall(request);
    try (Response response = call.execute()) {
      String string = response.body().string();
      int resposneCode = response.code();
      if (response.isSuccessful()) {
        long end = System.currentTimeMillis();
        ProcessResult result = JsonUtils.parse(string, ProcessResult.class);
        if (result != null) {
          result.setElapsed(end - start);
        }
        return result;
      } else {
        throw new RuntimeException("code:" + resposneCode + " response:" + string);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + targetUrl + " body:" + code, e);
    }
  }

  private static RequestBody getFileBody(String filename, String content) {
    String contentType = ContentTypeUtils.getContentType(filename);
    RequestBody fileBody = RequestBody.create(content.getBytes(), MediaType.parse(contentType));
    return fileBody;
  }

}
