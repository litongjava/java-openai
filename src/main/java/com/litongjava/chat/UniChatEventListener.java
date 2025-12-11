package com.litongjava.chat;

import java.util.List;

import com.litongjava.claude.ClaudeChatResponse;
import com.litongjava.claude.ClaudeChatUsage;
import com.litongjava.consts.ModelPlatformName;
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
    return null;
  }

  private UniChatResponse openai(String data) {
    // 1. 解析 JSON
    OpenAiChatResponse chatResp = FastJson2Utils.parse(data, OpenAiChatResponse.class);
    String model = chatResp.getModel();

    // 2. 第一次发 citations
    List<String> citations = chatResp.getCitations();

    // 3. 拿到 delta
    Choice choice = chatResp.getChoices().get(0);
    ChatResponseDelta delta = choice.getDelta();

    ChatResponseUsage usage = chatResp.getUsage();

    UniChatResponse uniChatResponse = new UniChatResponse();
    uniChatResponse.setModel(model);
    uniChatResponse.setCitations(citations);
    uniChatResponse.setDelta(delta);
    uniChatResponse.setRawData(data);
    uniChatResponse.setUsage(usage);
    return uniChatResponse;
  }
}
