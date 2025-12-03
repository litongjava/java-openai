package com.litongjava.byteplus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

@Slf4j
public class BytePlusTTSHttpStreamClient {

  private BytePlusConfig bytePlusConfig;

  public BytePlusTTSHttpStreamClient() {
    this.bytePlusConfig = new BytePlusConfig();
  }

  public BytePlusTTSHttpStreamClient(BytePlusConfig bytePlusConfig) {
    this.bytePlusConfig = bytePlusConfig;
  }

  public BytePlusTTSAudio tts(String input, String speaker) {
    BytePlusCacheConfig cacheConfig = new BytePlusCacheConfig();
    cacheConfig.setText_type(1);
    cacheConfig.setUse_cache(true);

    BytePlusAdditions additionsObj = new BytePlusAdditions();
    additionsObj.setDisable_markdown_filter(true);
    additionsObj.setEnable_language_detector(true);
    additionsObj.setEnable_latex_tn(true);
    additionsObj.setDisable_default_bit_rate(true);
    additionsObj.setMax_length_to_filter_parenthesis(0);
    additionsObj.setCache_config(cacheConfig);

    String additionsJson = JsonUtils.toJson(additionsObj);

    BytePlusAudioParams audioParams = new BytePlusAudioParams();
    audioParams.setFormat("mp3");
    audioParams.setSample_rate(24000);

    BytePlusReqParams reqParams = new BytePlusReqParams();
    reqParams.setText(input);
    reqParams.setSpeaker(speaker);
    reqParams.setAdditions(additionsJson);
    reqParams.setAudio_params(audioParams);

    BytePlusTTSRequest rootRequest = new BytePlusTTSRequest();
    rootRequest.setReq_params(reqParams);

    String bodyJson = JsonUtils.toJson(rootRequest);

    RequestBody requestBody = RequestBody.create(bodyJson, MediaType.parse("application/json; charset=utf-8"));

    Request request = new Request.Builder().url(bytePlusConfig.getTts_url())
        //
        .post(requestBody)
        //
        .addHeader("X-Api-App-Id", bytePlusConfig.getApp_id()).addHeader("X-Api-Access-Key", bytePlusConfig.getAccess_key())
        //
        .addHeader("X-Api-Resource-Id", bytePlusConfig.getResource_id())
        //
        .addHeader("X-Api-App-Key", "aGjiRDfUWi").addHeader("Content-Type", "application/json")
        //
        .addHeader("Connection", "keep-alive").build();

    try {
      return post(request);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  private BytePlusTTSAudio post(Request request) throws IOException {
    OkHttpClient client = OkHttpClientPool.get60HttpClient();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        log.error("http error: " + response.code() + " " + response.message());
        return null;
      }

      String logId = response.header("X-Tt-Logid");
      if (logId != null) {
        log.info("X-Tt-Logid: " + logId);
      }

      ResponseBody responseBody = response.body();
      if (responseBody == null) {
        log.error("empty response body");
        return null;
      }

      BufferedSource source = responseBody.source();
      ByteArrayOutputStream audioOutput = new ByteArrayOutputStream();
      Base64.Decoder base64Decoder = Base64.getDecoder();

      String line;
      while ((line = source.readUtf8Line()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        BytePlusTTSResponse ttsResponse = JsonUtils.parse(line, BytePlusTTSResponse.class);

        int code = ttsResponse.getCode();

        if (code == 0) {
          if (ttsResponse.getData() != null && !ttsResponse.getData().isEmpty()) {
            byte[] chunk = base64Decoder.decode(ttsResponse.getData());
            audioOutput.write(chunk);
          }
          continue;
        }

        if (code == 20000000) {
          break;
        }

        if (code > 0) {
          log.error("error response: " + line);
          break;
        }
      }

      byte[] audioBytes = audioOutput.toByteArray();
      return new BytePlusTTSAudio(audioBytes);
    }
  }
}
