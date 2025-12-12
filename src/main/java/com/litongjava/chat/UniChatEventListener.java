package com.litongjava.chat;

import java.util.List;

import com.litongjava.claude.ClaudeChatResponse;
import com.litongjava.claude.ClaudeChatUsage;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.gemini.GeminiCandidate;
import com.litongjava.gemini.GeminiChatResponse;
import com.litongjava.gemini.GeminiContentResponse;
import com.litongjava.gemini.GeminiPart;
import com.litongjava.gemini.GeminiUsageMetadata;
import com.litongjava.openai.chat.ChatResponseDelta;
import com.litongjava.openai.chat.ChatResponseUsage;
import com.litongjava.openai.chat.Choice;
import com.litongjava.openai.chat.OpenAiChatResponse;
import com.litongjava.tio.utils.json.FastJson2Utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

@Slf4j
public abstract class UniChatEventListener extends EventSourceListener {

  private String platform;

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getPlatform() {
    return this.platform;
  }

  @Override
  public void onEvent(EventSource eventSource, String id, String type, String data) {
    // OpenAI 最后会推送一个 [DONE] 事件
    if ("[DONE]".equals(data)) {
      return;
    }
    try {
      UniChatResponse chatResposne = null;
      if (ModelPlatformName.GOOGLE.equals(platform)) {
        chatResposne = google(data);

      } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
        chatResposne = anthropic(data);

      } else {
        chatResposne = openai(data);
      }
      onData(chatResposne);
    } catch (Exception ex) {
      log.error("Error parsing SSE chunk: {}", ex.getMessage(), ex);
    }

  }

  public abstract void onData(UniChatResponse chatResposne);

  private UniChatResponse anthropic(String data) {
    ClaudeChatResponse chatResponse = FastJson2Utils.parse(data, ClaudeChatResponse.class);
    String model = chatResponse.getModel();
    ClaudeChatUsage usage = chatResponse.getUsage();
    ChatResponseDelta delta = chatResponse.getDelta();

    UniChatResponse uniChatResponse = new UniChatResponse();
    uniChatResponse.setRawData(data);

    uniChatResponse.setModel(model);
    if (usage != null) {
      uniChatResponse.setUsage(new ChatResponseUsage(usage));
    }

    uniChatResponse.setDelta(delta);

    return uniChatResponse;

  }

  private UniChatResponse google(String data) {
    UniChatResponse uniChatResponse = new UniChatResponse();

    GeminiChatResponse chatResp = FastJson2Utils.parse(data, GeminiChatResponse.class);

    // System.out.println(data);
    // 2. 第一次发 citations
    // List<String> citations = chatResp.get
//    uniChatResponse.setCitations(citations);

    GeminiUsageMetadata usageMetadata = chatResp.getUsageMetadata();
    ChatResponseUsage usage = new ChatResponseUsage(usageMetadata);
    String modelVersion = chatResp.getModelVersion();

    uniChatResponse.setUsage(usage).setRawData(chatResp.getRawData()).setModel(modelVersion);

    List<GeminiCandidate> candidates = chatResp.getCandidates();
    if (candidates != null && candidates.size() > 0) {
      GeminiCandidate geminiCandidateVo = candidates.get(0);
      GeminiContentResponse content = geminiCandidateVo.getContent();

      String role = content.getRole();
      List<GeminiPart> parts = content.getParts();

      ChatResponseDelta delta = new ChatResponseDelta(role, parts);
      uniChatResponse.setDelta(delta);
    }

    return uniChatResponse;
  }

  private UniChatResponse openai(String data) {
    UniChatResponse uniChatResponse = new UniChatResponse();

    // 1. 解析 JSON
    OpenAiChatResponse chatResp = FastJson2Utils.parse(data, OpenAiChatResponse.class);
    String model = chatResp.getModel();

    // 2. 第一次发 citations
    List<String> citations = chatResp.getCitations();

    // 3. 拿到 delta
    List<Choice> choices = chatResp.getChoices();
    if (choices != null && choices.size() > 0) {
      Choice choice = choices.get(0);
      ChatResponseDelta delta = choice.getDelta();
      uniChatResponse.setDelta(delta);
    }

    ChatResponseUsage usage = chatResp.getUsage();

    uniChatResponse.setModel(model);
    uniChatResponse.setCitations(citations);

    uniChatResponse.setRawData(data);
    uniChatResponse.setUsage(usage);
    return uniChatResponse;
  }
}
