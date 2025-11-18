package com.litongjava.gitee;

import java.io.File;
import java.io.IOException;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * GiteeClient: 封装 Gitee AI 文档解析接口
 */
public class GiteeClient {

  private static final OkHttpClient client = OkHttpClientPool.get60HttpClient();

  private final String apiKey;
  private final String baseUrl;

  /**
   * 从环境变量获取配置：
   * GITEE_AI_API_KEY, GITEE_AI_BASE
   */
  public GiteeClient() {
    this(EnvUtils.getStr("GITEE_API_KEY"), EnvUtils.getStr("GITEE_BASE_URL", GiteeConst.BASE_URL));
  }

  public GiteeClient(String apiKey) {
    this(apiKey, GiteeConst.BASE_URL);
  }

  public GiteeClient(String apiKey, String baseUrl) {
    this.apiKey = apiKey;
    this.baseUrl = baseUrl != null ? baseUrl : GiteeConst.BASE_URL;
  }

  /**
   * 调用 /v1/async/documents/parse 接口（文件形式）
   */
  public GiteeTaskResponse parseDocument(File file, String model, String prompt) {
    String suffix = FilenameUtils.getSuffix(file.getName());
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(file, MediaType.get(contentType));
    return parseDocument(fileBody, file.getName(), model, prompt);
  }

  /**
   * 调用 /v1/async/documents/parse 接口（字节数组形式）
   */
  public GiteeTaskResponse parseDocument(byte[] data, String filename) {
    return parseDocument(data, filename, GiteeModels.DEEPSEEK_OCR, GiteePromptConst.pdf_to_markdown_prompt);
  }

  public GiteeTaskResponse parseDocument(byte[] data, String filename, String model, String prompt) {
    String suffix = FilenameUtils.getSuffix(filename);
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileBody = RequestBody.create(data, MediaType.get(contentType));
    return parseDocument(fileBody, filename, model, prompt);
  }

  /**
   * 获取任务状态 /api/v1/task/{task_id}
   */
  public GiteeTaskResponse getTask(String taskId) {
    String url = baseUrl + "/api/v1/task/" + taskId;

    Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + apiKey).get().build();

    return execute(request);
  }

  /**
   * 内部方法：构造并发送文档解析请求
   */
  private GiteeTaskResponse parseDocument(RequestBody fileBody, String filename, String model, String prompt) {
    String url = baseUrl + "/v1/async/documents/parse";

    MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
        //
        .addFormDataPart("file", filename, fileBody).addFormDataPart("model", model)
        //
        .addFormDataPart("prompt", prompt).build();

    Request request = new Request.Builder().url(url)
        //
        .addHeader("Authorization", "Bearer " + apiKey).addHeader("X-Failover-Enabled", "true").post(body).build();

    return execute(request);
  }

  /**
   * 统一执行 HTTP 请求并解析为 GiteeTaskResponse
   */
  private GiteeTaskResponse execute(Request request) {
    Call call = client.newCall(request);
    try (Response response = call.execute()) {
      if (response.isSuccessful()) {
        String json = response.body().string();
        return JsonUtils.parse(json, GiteeTaskResponse.class);
      } else {
        String msg = "code:" + response.code() + ",body:" + response.body().string();
        throw new RuntimeException(msg);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
