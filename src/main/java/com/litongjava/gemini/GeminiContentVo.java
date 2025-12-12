package com.litongjava.gemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.chat.ChatImageFile;
import com.litongjava.chat.UniChatMessage;
import com.litongjava.tio.utils.hutool.StrUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示请求中的单条对话
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiContentVo {

  /**
   * 角色：user 或 model 等
   */
  private String role;

  /**
   * 内容拆分成多个 part
   */
  private List<GeminiPart> parts;

  public GeminiContentVo(UniChatMessage chatMessage) {
    this.role = chatMessage.getRole();
    this.parts = Collections.singletonList(new GeminiPart(chatMessage.getContent()));
  }

  public GeminiContentVo(String role, String content) {
    this.role = role;
    this.parts = Collections.singletonList(new GeminiPart(content));
  }

  public GeminiContentVo(String role, UniChatMessage chatMessage) {
    this.role = role;
    String content = chatMessage.getContent();
    List<GeminiPart> parts = new ArrayList<>();

    if (StrUtil.isNotBlank(content)) {
      GeminiPart geminiPartVo = new GeminiPart(content);
      parts.add(geminiPartVo);
    }
    List<ChatImageFile> files = chatMessage.getFiles();
    if (files != null) {
      for (ChatImageFile chatFile : files) {
        parts.add(new GeminiPart(chatFile));
      }
    }

    this.parts = parts;
  }

}
