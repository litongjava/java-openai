package com.litongjava.groq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.ContentTypeUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.FilenameUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * GroqSpeechClient: Handles audio transcription via the Groq API.
 * <p>
 * Accepts audio MIME type and file name as parameters instead of relying on environment variables.
 */
public class GroqSpeechClient {
  private static final OkHttpClient client = OkHttpClientPool.get60HttpClient();

  // Request limiters for different models (requests per minute)
  private static final Map<String, GroqSpeechRequestLimiter> limiters = new HashMap<>();

  static {
    limiters.put("whisper-large-v3", new GroqSpeechRequestLimiter(100));
    limiters.put("whisper-large-v3-turbo", new GroqSpeechRequestLimiter(400));
    limiters.put("distil-whisper-large-v3-en", new GroqSpeechRequestLimiter(100));
  }

  /**
   * Transcription method to send audio data for processing.
   *
   * @param audioData     Converted audio data
   * @param model         Model ID
   * @param audioMimeType Audio MIME type (e.g., "audio/mpeg" or "audio/wav")
   * @param audioFileName File name for upload (e.g., "audio.mp3" or "audio.wav")
   * @param callback      Callback for processing the response
   */
  public static void transcribe(byte[] audioData, String model, String audioMimeType, String audioFileName, Consumer<String> callback) {
    GroqSpeechRequestLimiter limiter = limiters.getOrDefault(model, new GroqSpeechRequestLimiter(100));
    synchronized (limiter) {
      if (limiter.canSendRequest()) {
        sendTranscriptionRequest(audioData, model, audioMimeType, audioFileName, callback);
        limiter.recordRequest();
      } else {
        try {
          limiter.appendAudio(audioData);
        } catch (IOException e) {
          e.printStackTrace();
        }
        limiter.scheduleFlush(model, audioMimeType, audioFileName, callback);
      }
    }
  }

  /**
   * Max File Size: 25 MB
   * Supported File Types
   * `flac`, `mp3`, `mp4`, `mpeg`, `mpga`, `m4a`, `ogg`, `wav`, `webm` 
   * @param audioData
   * @param filename
   * @param reqVo
   * @return
   */
  public static TranscriptionsResponse transcriptions(byte[] audioData, String filename, TranscriptionsRequest reqVo) {

    String apiBase = EnvUtils.getStr("GROQ_API_BASE", GropConst.API_BASE);
    String endpoint = apiBase + "/openai/v1/audio/transcriptions";
    String apiKey = EnvUtils.getStr("GROQ_API_KEY");

    String suffix = FilenameUtils.getSuffix(filename);
    String contentType = ContentTypeUtils.getContentType(suffix);
    RequestBody fileRequestPart = RequestBody.create(audioData, MediaType.get(contentType));

    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
    bodyBuilder.setType(MultipartBody.FORM);

    bodyBuilder.addFormDataPart("file", filename, fileRequestPart);

    String model = reqVo.getModel();
    if (model != null) {
      model = GropModel.WHISPER_LARGE_V3_TURBO;
    }
    bodyBuilder.addFormDataPart("model", model);

    if (reqVo.getTemperature() != null) {
      bodyBuilder.addFormDataPart("temperature", reqVo.getTemperature().toString());
    }
    if (reqVo.getResponse_format() != null) {
      bodyBuilder.addFormDataPart("response_format", reqVo.getResponse_format());
    }

    MultipartBody multipartBody = bodyBuilder.build();

    Request request = new Request.Builder().url(endpoint)
        //
        .method("POST", multipartBody).addHeader("Authorization", "Bearer " + apiKey).build();
    Call newCall = client.newCall(request);

    try (Response response = newCall.execute()) {
      if (response.isSuccessful()) {
        String string = response.body().string();
        TranscriptionsResponse transcriptionsResponse = JsonUtils.parse(string, TranscriptionsResponse.class);
        return transcriptionsResponse;
      } else {
        String message = "code:" + response.code() + ",body:" + response.body().string();
        throw new RuntimeException(message);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Sends a merged request when called by the RequestLimiter.
   */
  public static void sendMergedRequest(String model, byte[] audioData, String audioMimeType, String audioFileName, Consumer<String> callback) {
    sendTranscriptionRequest(audioData, model, audioMimeType, audioFileName, callback);
  }

  /**
   * Sends a transcription request via OkHttp.
   */
  private static void sendTranscriptionRequest(byte[] audioData, String model, String audioMimeType, String audioFileName, Consumer<String> callback) {
    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("model", model).addFormDataPart("temperature", "0")
        .addFormDataPart("response_format", "json");

    RequestBody fileBody = RequestBody.create(audioData, MediaType.parse(audioMimeType));
    multipartBuilder.addFormDataPart("file", audioFileName, fileBody);

    String apiBase = EnvUtils.getStr("GROQ_API_BASE", GropConst.API_BASE);
    String endpoint = apiBase + "/openai/v1/audio/transcriptions";

    String apiKey = EnvUtils.getStr("GROQ_API_KEY");
    Request request = new Request.Builder().url(endpoint).header("Authorization", "bearer " + apiKey).post(multipartBuilder.build()).build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        callback.accept("Transcription failed: " + e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
          callback.accept("Transcription error: code:" + response.code() + " body:" + response.body().string());
        } else {
          String responseBody = response.body().string();
          String transcript = parseTranscript(responseBody);
          callback.accept(transcript);
        }
      }
    });
  }

  /**
   * Parses the "text" field from the response.
   */
  private static String parseTranscript(String responseBody) {
    int index = responseBody.indexOf("\"text\":");
    if (index != -1) {
      int start = responseBody.indexOf("\"", index + 7) + 1;
      int end = responseBody.indexOf("\"", start);
      if (start != -1 && end != -1) {
        return responseBody.substring(start, end);
      }
    }
    return responseBody;
  }
}
